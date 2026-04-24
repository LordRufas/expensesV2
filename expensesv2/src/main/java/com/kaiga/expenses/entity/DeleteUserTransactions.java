package com.kaiga.expenses.entity;

public class DeleteUserTransactions {

    private int id;
    private String date;
    private String typeName;
    private double value;
    private boolean isRevenue;

    public DeleteUserTransactions(int id, String date, String typeName, double value, boolean isRevenue) {
        this.id = id;
        this.date = date;
        this.typeName = typeName;
        this.value = value;
        this.isRevenue = isRevenue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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
