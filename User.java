package com.example.ordering.dto;

public class User {
    private String username = "unknownUsername";
    private int tableNumber;
    private int consumptionAmount;
    private int state;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getConsumptionAmount() {
        return consumptionAmount;
    }

    public void setConsumptionAmount(int consumptionAmount) {
        this.consumptionAmount = consumptionAmount;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
