package org.demo.demopaymentservice.Model;

import org.demo.demopaymentservice.Type.State;

import java.time.LocalDate;
import java.util.Date;

public class PaymentModel {
    private int no;
    private Long amount;
    private LocalDate dueDate;
    private State state;
    private int billId;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public PaymentModel(int no, Long amount, LocalDate dueDate, State state, int billId) {
        this.no = no;
        this.amount = amount;
        this.dueDate = dueDate;
        this.state = state;
        this.billId = billId;
    }

    public PaymentModel() {
    }

    @Override
    public String toString() {
        return "PaymentModel{" +
                "no=" + no +
                ", amount=" + amount +
                ", dueDate=" + dueDate +
                ", state=" + state +
                ", billId=" + billId +
                '}';
    }
}
