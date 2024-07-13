package org.demo.demopaymentservice.DTO;

public class UserDTO {
    private Long amount;

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public UserDTO(Long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return  "User : " + amount;
    }
}
