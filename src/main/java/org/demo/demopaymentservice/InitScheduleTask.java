package org.demo.demopaymentservice;

import org.demo.demopaymentservice.Controller.PaymentController;
import org.demo.demopaymentservice.Service.PaymentService;

import java.util.concurrent.TimeUnit;

public class InitScheduleTask  {
    private PaymentService paymentService;

    public InitScheduleTask(PaymentService paymentService) {
        this.paymentService = paymentService;
        init();
    }
    public void init() {
        final Runnable task = new Runnable() {
            public void run() {
                try {
                    paymentService.schedule();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Initialize.getSchedulePoolInstance().scheduleAtFixedRate(task, 1, 10, TimeUnit.SECONDS);
    }

}
