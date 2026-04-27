package com.kaiga.expenses.entity;

public class UpdateUserTransactions {

    private int id;
    private String oldDate;
    private String oldType;
    private double oldValue;
    private boolean oldIsRevenue;
    private String newDate;
    private String newType;
    private double newValue;
    private boolean newIsRevenue;

    public UpdateUserTransactions(int id, String oldDate, String oldType, double oldValue, boolean oldIsRevenue, String newDate, String newType, double newValue, boolean newIsRevenue) {
        this.id = id;
        this.oldDate = oldDate;
        this.oldType = oldType;
        this.oldValue = oldValue;
        this.oldIsRevenue = oldIsRevenue;
        this.newDate = newDate;
        this.newType = newType;
        this.newValue = newValue;
        this.newIsRevenue = newIsRevenue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOldDate() {
        return oldDate;
    }

    public void setOldDate(String oldDate) {
        this.oldDate = oldDate;
    }

    public String getOldType() {
        return oldType;
    }

    public void setOldType(String oldType) {
        this.oldType = oldType;
    }

    public double getOldValue() {
        return oldValue;
    }

    public void setOldValue(double oldValue) {
        this.oldValue = oldValue;
    }

    public boolean isOldIsRevenue() {
        return oldIsRevenue;
    }

    public void setOldIsRevenue(boolean oldIsRevenue) {
        this.oldIsRevenue = oldIsRevenue;
    }

    public String getNewDate() {
        return newDate;
    }

    public void setNewDate(String newDate) {
        this.newDate = newDate;
    }

    public String getNewType() {
        return newType;
    }

    public void setNewType(String newType) {
        this.newType = newType;
    }

    public double getNewValue() {
        return newValue;
    }

    public void setNewValue(double newValue) {
        this.newValue = newValue;
    }

    public boolean isNewIsRevenue() {
        return newIsRevenue;
    }

    public void setNewIsRevenue(boolean newIsRevenue) {
        this.newIsRevenue = newIsRevenue;
    }
}
