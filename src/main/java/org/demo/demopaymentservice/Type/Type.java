package org.demo.demopaymentservice.Type;

public enum Type {
    INTERNET("INTERNET"), WATER("WATER"), ELECTRIC("ELECTRIC"), FOOD("FOOD"), DEPARTMENT("DEPARTMENT");
    private String value;

    Type(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
