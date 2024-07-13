package org.demo.demopaymentservice.Controller;

import org.demo.demopaymentservice.DTO.UserDTO;
import org.demo.demopaymentservice.Facade.PaymentFacade;
import org.demo.demopaymentservice.Initialize;
import org.demo.demopaymentservice.Type.Field;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public class PaymentController {
    private PaymentFacade paymentFacade;


    public void listBills() {

        System.out.println(paymentFacade.listBill());
    }

    public void getUserAmount() {
        System.out.println(paymentFacade.getAmount());
    }

    public void cashIn(Long amount) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Initialize.getThreadPoolInstance();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        CompletableFuture<UserDTO> future1 = CompletableFuture.supplyAsync(() -> paymentFacade.cashIn(amount, countDownLatch), executorService);
        countDownLatch.await();
        System.out.println(future1.get());
    }

    public void  pay(List<Integer> id) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Initialize.getThreadPoolInstance();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        CompletableFuture<UserDTO> future1 = CompletableFuture.supplyAsync(() ->paymentFacade.pay(countDownLatch,id), executorService);
        countDownLatch.await();
        System.out.println(future1.get());
    }
    public void schedule(int id , String date){
        if(paymentFacade.schedule(id,date)){
            System.out.println("Scheduled Successfully");
        }
        else System.out.println("Scheduled Failed");
    }

    public void search(Field field , String text){
        System.out.println(paymentFacade.searchBill(field,text));
    }
    public void listPayment() {
        System.out.println(paymentFacade.listPayment());
    }

    public void dueDate(){
        System.out.println(paymentFacade.dueDateList());
    }
    public void search(String field , String text){
        switch (field.toLowerCase()){
            case "provider":
                System.out.println(paymentFacade.searchBill(Field.PROVIDER,text));
                break;
            default:
                break;
        }
    }

    public PaymentController(PaymentFacade paymentFacade) {
        this.paymentFacade = paymentFacade;
    }
}
