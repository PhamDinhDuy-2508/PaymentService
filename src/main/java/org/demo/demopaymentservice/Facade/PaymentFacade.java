package org.demo.demopaymentservice.Facade;

import org.demo.demopaymentservice.DTO.BillDTO;
import org.demo.demopaymentservice.DTO.UserDTO;
import org.demo.demopaymentservice.Model.BillModel;
import org.demo.demopaymentservice.Model.PaymentModel;
import org.demo.demopaymentservice.Type.Field;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public interface PaymentFacade {
    List<BillModel> listBill();

    UserDTO getAmount();

    UserDTO cashIn(Long amount, CountDownLatch countDownLatch);

    UserDTO pay(CountDownLatch countDownLatch, List<Integer> id);

    List<BillModel> dueDateList();

    boolean schedule(int id, String date);

    List<PaymentModel> listPayment();

    List<BillModel> searchBill(Field field, String pattern);

     BillModel createBill(BillDTO billDTO);

     boolean deleteBill(List<Integer> id);

}
