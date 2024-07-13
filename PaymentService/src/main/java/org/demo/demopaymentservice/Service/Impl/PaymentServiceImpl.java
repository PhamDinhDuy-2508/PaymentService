package org.demo.demopaymentservice.Service.Impl;

import org.demo.demopaymentservice.Initialize;
import org.demo.demopaymentservice.Model.BillModel;
import org.demo.demopaymentservice.Model.PaymentModel;
import org.demo.demopaymentservice.Model.UserModel;
import org.demo.demopaymentservice.Service.PaymentService;
import org.demo.demopaymentservice.Type.Field;
import org.demo.demopaymentservice.Type.State;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class PaymentServiceImpl implements PaymentService {
    private final AtomicInteger paymentNo;

    {
        paymentNo = new AtomicInteger(0);
    }

    @Override
    public List<BillModel> listBill() {
        Map<Integer, BillModel> billModelMap = Initialize.getInstanceBillModel();
        List<BillModel> list = new ArrayList<>();
        for (Map.Entry<Integer, BillModel> entry : billModelMap.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

    @Override
    public UserModel getCurrentUser() {
        return Initialize.getInstanceUserModel();
    }

    @Override
    public synchronized UserModel cashIn(Long amount) {
        try {
            Long currentAmount = Initialize.getInstanceUserModel().getAmount();
            if (currentAmount >= 0) {
                Initialize.getInstanceUserModel().setAmount(amount + currentAmount);
            }
            return Initialize.getInstanceUserModel();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Initialize.getInstanceUserModel();
        }
    }

    @Override
    public UserModel pay(List<Integer> id) {
        try {
            boolean needToRollBack = false;
            Map<Integer, BillModel> billModelMap = Initialize.getInstanceBillModel();

            synchronized (this) {

                Long amount = Initialize.getInstanceUserModel().getAmount();

                List<BillModel> billModels = this.getUnPaidList(id);
                for (BillModel model : billModels) {
                    billModelMap.get(model.getNo()).setState(State.PROCESSED);

                    if (amount < 0) {
                        needToRollBack = true;
                        billModelMap.get(model.getNo()).setState(State.NOT_PAID);
                        continue;
                    }
                    amount -= model.getAmount();
                    if (amount < 0) {
                        needToRollBack = true;
                        billModelMap.get(model.getNo()).setState(State.NOT_PAID);
                    } else {
                        billModelMap.get(model.getNo()).setState(State.PAID);
                        createPaymentModel(model);
                    }
                }

                if (!needToRollBack) {
                    Initialize.getInstanceUserModel().setAmount(amount);
                    System.out.println("Pay successfully");
                    return Initialize.getInstanceUserModel();
                } else {
                    System.out.println("Pay Fail");
                }
            }
            return Initialize.getInstanceUserModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<BillModel> dueDateList() {
        return this.getUnPaidList();
    }

    @Override
    public boolean schedule() throws InterruptedException {
        Map<LocalDate, List<Integer>> scheduleMap = Initialize.getScheduleBill();
        LocalDate currentDate = LocalDate.now();
        if (scheduleMap.containsKey(currentDate)) {
            List<Integer> ids = scheduleMap.get(currentDate);
            Map<Integer, BillModel> billModelMap = Initialize.getInstanceBillModel();

            for(Integer id : ids) {
                if(billModelMap.containsKey(id)){
                    if(billModelMap.get(id).getState() != State.PAID) {
                        billModelMap.get(id).setState(State.PENDING);
                    }
                }
                else{
                    System.out.println("Not Found Bill with such Id");
                    return false;
                }
            }

            ExecutorService executorService = Initialize.getThreadPoolInstance();
            CountDownLatch countDownLatch = new CountDownLatch(1);
            CompletableFuture<Void> task = CompletableFuture.runAsync(() -> pay(ids), executorService);
            task.whenComplete((result, throwable) -> {
                if (throwable == null) {
                    scheduleMap.remove(currentDate);
                }
                countDownLatch.countDown();
            });
            countDownLatch.await();
            task.join();
        }
        return false;
    }

    @Override
    public boolean addSchedule(int id, String date) {
        try {
            LocalDate scheduleDate = LocalDate.parse(date);
            LocalDate currentDate = LocalDate.now();

            if (scheduleDate.isBefore(currentDate)) {
                System.out.println("Schedule date invalid");
                return false;
            }
            if(!Initialize.getInstanceBillModel().containsKey(id)){
                return false;
            }

            BillModel billModel = this.getBillById(id);
            Map<LocalDate, List<Integer>> scheduleMap = Initialize.getScheduleBill();

            scheduleMap.computeIfAbsent(scheduleDate, k -> new ArrayList<>()).add(billModel.getNo());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<PaymentModel> listPayment() {
        List<PaymentModel> paymentModels = new ArrayList<>();
        Map<Integer, PaymentModel> paymentModelMap = Initialize.getInstancePaymentModel();
        for (Map.Entry<Integer, PaymentModel> entry : paymentModelMap.entrySet()) {
            paymentModels.add(entry.getValue());
        }
        return paymentModels;
    }

    @Override
    public List<BillModel> searchBill(Field field, String pattern) {
        Map<String, List<BillModel>> recordValue = new LinkedHashMap<>();
        Map<Integer, BillModel> billModelMap = Initialize.getInstanceBillModel();
        switch (field) {
            case PROVIDER:
                billModelMap.forEach((key, billModel) -> {
                    String provider = billModel.getProvider();
                    recordValue.computeIfAbsent(provider, k -> new ArrayList<>()).add(billModel);
                });
                break;
            default:
                break;
        }
        return search(recordValue, pattern.toUpperCase());
    }

    @Override
    public BillModel createBiil(BillModel billModel) {
        Map<Integer, BillModel> billModelMap = Initialize.getInstanceBillModel();
        synchronized (this) {
            int size = billModelMap.size();
            billModel.setNo(size + 1);
            billModelMap.put(billModel.getNo(), billModel);
        }
        return billModelMap.get(billModel.getNo());
    }

    @Override
    public synchronized boolean deleteBill(List<Integer> ids) {
        Map<Integer, BillModel> billModelMap = Initialize.getInstanceBillModel();
        for (int id : ids) {
            if (billModelMap.containsKey(id)) {
                billModelMap.remove(id);
                return true;
            }
        }
        return false;
    }

    private List<BillModel> getUnPaidList(List<Integer> ids) {
        Map<Integer, BillModel> billModelMap = Initialize.getInstanceBillModel();
        List<BillModel> list = new ArrayList<>();
        Set<Integer> setIds = new HashSet<>(ids);

        for (int i : setIds) {
            if (billModelMap.containsKey(i) && billModelMap.get(i).getState() == State.NOT_PAID) {
                list.add(billModelMap.get(i));
            } else if (!billModelMap.containsKey(i)) {
                System.out.println("Not Found Bill witch such Id");
                return new ArrayList<>();
            }
        }
        Collections.sort(list);
        return list;
    }

    private List<BillModel> getUnPaidList() {
        Map<Integer, BillModel> billModelMap = Initialize.getInstanceBillModel();
        List<BillModel> list = new ArrayList<>();
        for (Map.Entry<Integer, BillModel> entry : billModelMap.entrySet()) {
            if (entry.getValue().getState() != State.PAID) {
                list.add(entry.getValue());
            }
        }
        Collections.sort(list);
        return list;
    }

    private void createPaymentModel(BillModel billModel) {
        if (billModel != null && billModel.getAmount() != null && State.PAID == billModel.getState()) {
            Map<Integer, PaymentModel> paymentModelMap = Initialize.getInstancePaymentModel();
            int no = paymentNo.addAndGet(1);
            PaymentModel paymentModel = new PaymentModel(no, billModel.getAmount(), billModel.getDueDate(), billModel.getState(), billModel.getNo());
            paymentModelMap.put(paymentModel.getNo(), paymentModel);
            System.out.println("Create Payment " + paymentModel);
            return;
        }
        System.out.println("Create payment failed");
    }

    private List<BillModel> search(Map<String, List<BillModel>> recordValue, String pattern) {
        List<BillModel> result = recordValue.get(pattern);
        if (result == null) return new ArrayList<>();
        return result;
    }

    private BillModel getBillById(int id) {
        Map<Integer, BillModel> billModelMap = Initialize.getInstanceBillModel();
        return billModelMap.get(id);
    }

}
