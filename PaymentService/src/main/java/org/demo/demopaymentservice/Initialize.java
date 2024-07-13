package org.demo.demopaymentservice;

import org.demo.demopaymentservice.Model.BillModel;
import org.demo.demopaymentservice.Model.PaymentModel;
import org.demo.demopaymentservice.Model.UserModel;
import org.demo.demopaymentservice.Type.State;
import org.demo.demopaymentservice.Type.Type;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Initialize {
    private static volatile Map<Integer, BillModel> billModelMap;
    private static volatile Map<Integer, PaymentModel> paymentModelMap;
    private static volatile Map<LocalDate, List<Integer>> scheduleMap;

    private static volatile UserModel userModel;
    private static final Long DEFAULT_AMOUNT=20000L;
    private static volatile ExecutorService instance;
    private static volatile  ScheduledExecutorService scheduledInstance;
    
    private static final Integer N_THREADS = 4; 


    private Initialize() {
    }

    public static Map<Integer, BillModel> getInstanceBillModel() {
        if (billModelMap == null) {
            synchronized (Initialize.class) {
                if (billModelMap == null) {
                    billModelMap = new LinkedHashMap<>();
                    initializeBillModel(billModelMap);
                }
            }
        }
        return billModelMap;
    }
    public static UserModel getInstanceUserModel() {
        if (userModel == null) {
            synchronized (UserModel.class) {
                if (userModel == null) {
                    userModel = new UserModel();
                    userModel.setAmount(0L);
                }
            }
        }
        return userModel;
    }

    public static Map<Integer, PaymentModel> getInstancePaymentModel() {
        if (paymentModelMap == null) {
            synchronized (Initialize.class) {
                if (paymentModelMap == null) {
                    paymentModelMap = new LinkedHashMap<>();
                }
            }
        }
        return paymentModelMap;
    }

    public static ExecutorService getThreadPoolInstance() {
        if (instance == null) {
            synchronized (Initialize.class) {
                if (instance == null) {
                    instance = Executors.newFixedThreadPool(N_THREADS);
                }
            }
        }
        return instance;
    }
    public static ScheduledExecutorService getSchedulePoolInstance() {
        if (scheduledInstance == null) {
            synchronized (Initialize.class) {
                if (scheduledInstance == null) {
                    scheduledInstance = Executors.newScheduledThreadPool(N_THREADS);
                }
            }
        }
        return scheduledInstance;
    }

    public static  Map<LocalDate, List<Integer>>getScheduleBill() {
        if (scheduleMap == null) {
            synchronized (Initialize.class) {
                if (scheduleMap == null) {
                    scheduleMap = new LinkedHashMap<>();
                }
            }
        }
        return scheduleMap;
    }
    private static void initializeBillModel(Map<Integer, BillModel> billModelMap) {
        BillModel billModel1 = new BillModel(1, Type.INTERNET, 2000L,"2024-05-05", State.NOT_PAID,"VNPT");
        BillModel billModel2 = new BillModel(2, Type.WATER, 12000L,"2024-02-05", State.NOT_PAID,"SAVACO HCM");
        BillModel billModel3 = new BillModel(3, Type.ELECTRIC, 23000L,"2024-05-07", State.NOT_PAID,"EVN HCM");
        BillModel billModel4 = new BillModel(4, Type.FOOD, 31000L,"2024-06-05", State.NOT_PAID,"WINMART");
        BillModel billModel5 = new BillModel(5, Type.DEPARTMENT, 89000L,"2024-08-05", State.NOT_PAID,"VINHOME");
        billModelMap.put(billModel1.getNo(),billModel1);
        billModelMap.put(billModel2.getNo(),billModel2);
        billModelMap.put(billModel3.getNo(),billModel3);
        billModelMap.put(billModel4.getNo(),billModel4);
        billModelMap.put(billModel5.getNo(),billModel5);
    }

}
