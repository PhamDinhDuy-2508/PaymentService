package org.demo.demopaymentservice.Service;

import org.demo.demopaymentservice.Model.BillModel;
import org.demo.demopaymentservice.Model.PaymentModel;
import org.demo.demopaymentservice.Model.UserModel;
import org.demo.demopaymentservice.Type.Field;

import java.util.List;

public interface PaymentService {

    List<BillModel> listBill();

    UserModel getCurrentUser();

    UserModel cashIn(Long amount);

    UserModel pay(List<Integer> id);

    List<BillModel> dueDateList();

    boolean schedule() throws InterruptedException;

    boolean addSchedule(int id, String date);


    List<PaymentModel> listPayment();

    List<BillModel> searchBill(Field field, String pattern);

    BillModel createBiil(BillModel billModel);

    boolean deleteBill(List<Integer> id);
}
