package org.demo.demopaymentservice.DTO;

import org.demo.demopaymentservice.Type.State;
import org.demo.demopaymentservice.Type.Type;

import java.time.LocalDate;

public class BillDTO {
    private Type type;
    private Long amount;
    private LocalDate dueDate;
    private State state;
    private String provider;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
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

    public BillDTO(Type type, Long amount, LocalDate dueDate, State state, String provider) {
        this.type = type;
        this.amount = amount;
        this.dueDate = dueDate;
        this.state = state;
        this.provider = provider;
    }

    public BillDTO() {
    }
}
