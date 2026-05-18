package com.kaiga.expenses.entity;

public class UserTransaction {

    private String type;
    private double value;
    private boolean isRevenue;


    public UserTransaction(String type, double value, boolean isRevenue) {
        this.type = type;
        this.value = value;
        this.isRevenue = isRevenue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRevenue() {
        return isRevenue;
    }

    public void setRevenue(boolean revenue) {
        isRevenue = revenue;
    }

}
