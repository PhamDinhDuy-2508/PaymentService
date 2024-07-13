package org.demo.demopaymentservice.Model;

import org.demo.demopaymentservice.Type.State;
import org.demo.demopaymentservice.Type.Type;

import java.time.LocalDate;

public class BillModel implements Comparable<BillModel> {
    private int no;
    private Type type;
    private Long amount;
    private LocalDate dueDate;
    private State state;
    private String provider;

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

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public BillModel(int no, Type type, Long amount, String dueDate, State state, String provider) {
        this.no = no;
        this.type = type;
        this.amount = amount;
        this.dueDate = LocalDate.parse(dueDate);
        this.state = state;
        this.provider = provider;
    }

    public BillModel() {
    }

    @Override
    public int compareTo(BillModel o) {
        if(this.dueDate.isBefore(o.getDueDate()))return 1;
        else if(this.dueDate.equals(o.getDueDate()))return 0;
        else return -1;
    }

    @Override
    public String toString() {
        return "BillModel{" +
                "no=" + no +
                ", type=" + type +
                ", amount=" + amount +
                ", dueDate=" + dueDate +
                ", state=" + state +
                ", provider='" + provider + '\'' +
                '}';
    }
}
