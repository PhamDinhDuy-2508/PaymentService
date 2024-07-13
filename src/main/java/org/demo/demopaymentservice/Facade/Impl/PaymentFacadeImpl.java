package org.demo.demopaymentservice.Facade.Impl;

import org.demo.demopaymentservice.DTO.BillDTO;
import org.demo.demopaymentservice.DTO.UserDTO;
import org.demo.demopaymentservice.Facade.PaymentFacade;
import org.demo.demopaymentservice.Model.BillModel;
import org.demo.demopaymentservice.Model.PaymentModel;
import org.demo.demopaymentservice.Model.UserModel;
import org.demo.demopaymentservice.Service.PaymentService;
import org.demo.demopaymentservice.Type.Field;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class PaymentFacadeImpl implements PaymentFacade {
    private PaymentService paymentService;

    public PaymentFacadeImpl(PaymentService paymentService) {
        this.paymentService =  paymentService;
    }


    @Override
    public List<BillModel> listBill() {
        return paymentService.listBill();
    }

    @Override
    public UserDTO getAmount() {
        UserModel userModel = paymentService.getCurrentUser();
        return new UserDTO(userModel.getAmount());
    }

    @Override
    public UserDTO cashIn(Long amount, CountDownLatch countDownLatch) {
        UserModel userModel  = paymentService.cashIn(amount);
        countDownLatch.countDown();
        return new UserDTO(userModel.getAmount());
    }

    @Override
    public UserDTO pay(CountDownLatch countDownLatch, List<Integer> id) {
        UserModel userModel = paymentService.pay(id);
        countDownLatch.countDown();
        return new UserDTO(userModel.getAmount());
    }

    @Override
    public List<BillModel> dueDateList() {
        return paymentService.dueDateList();
    }

    @Override
    public boolean schedule(int id, String date) {
        try{
            paymentService.addSchedule(id,date);
            return true;
        }
        catch (Exception e) {
            return  false;
        }
    }

    @Override
    public List<PaymentModel> listPayment() {
        return paymentService.listPayment();
    }

    @Override
    public List<BillModel> searchBill(Field field, String pattern) {
        return paymentService.searchBill(field,pattern);
    }

    @Override
    public BillModel createBill(BillDTO billDTO) {
        BillModel billModel = new BillModel();
        billModel.setAmount(billDTO.getAmount());
        billModel.setState(billDTO.getState());
        billModel.setType(billDTO.getType());
        billModel.setProvider(billDTO.getProvider());
        return paymentService.createBiil(billModel);
    }

    @Override
    public boolean deleteBill(List<Integer> ids) {
        return paymentService.deleteBill(ids);
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
