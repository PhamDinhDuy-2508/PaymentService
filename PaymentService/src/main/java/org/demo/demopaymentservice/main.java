package org.demo.demopaymentservice;

import org.demo.demopaymentservice.Controller.PaymentController;
import org.demo.demopaymentservice.Facade.Impl.PaymentFacadeImpl;
import org.demo.demopaymentservice.Facade.PaymentFacade;
import org.demo.demopaymentservice.Service.Impl.PaymentServiceImpl;
import org.demo.demopaymentservice.Service.PaymentService;
import org.demo.demopaymentservice.Type.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        PaymentService paymentService = new PaymentServiceImpl();
        InitScheduleTask initScheduleTask = new InitScheduleTask(paymentService);
        PaymentFacade paymentFacade = new PaymentFacadeImpl(paymentService);
        PaymentController paymentController = new PaymentController(paymentFacade);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please enter a command (type 'exit' to exit):");
            String[] command = scanner.nextLine().split(" ");
            switch (command[0].toLowerCase()) {
                case "cash_in":
                    Long amount = Long.parseLong(command[1]);
                    paymentController.cashIn(amount);
                    break;
                case "list_bill":
                    paymentController.listBills();
                    break;
                case "pay":
                    List<Integer> ids = new ArrayList<>();
                    for (int i = 1; i < command.length; i++) {
                        ids.add(Integer.parseInt(command[i]));
                    }
                    paymentController.pay(ids);
                    break;
                case "due_date":
                    paymentController.dueDate();
                    break;
                case "schedule":
                    int id = Integer.parseInt(command[1]);
                    paymentController.schedule(id, command[2]);
                    break;
                case "list_payment":
                    paymentController.listPayment();
                    break;
                case "search":
                    command[1] = command[1].toLowerCase();
                    command[2] = command[2].toLowerCase();
                    paymentController.search(command[1], command[2]);
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("You entered: " + command);
                    break;
            }

//            if (command.equalsIgnoreCase("EXIT") || command.equalsIgnoreCase("exit")) {
//                System.out.println("Exiting the program...");
//                break; // Exit the loop and terminate the program
//            } else {
//                System.out.println("You entered: " + command);
//            }
        }

//        paymentController.cashIn(2000L);
//        paymentController.listBills();
////        paymentController.pay(1,2);
//        paymentController.listBills();
//        paymentController.getUserAmount();
//        paymentController.search(Field.PROVIDER,"VNPT");
//        paymentController.listPayment();


    }
}
