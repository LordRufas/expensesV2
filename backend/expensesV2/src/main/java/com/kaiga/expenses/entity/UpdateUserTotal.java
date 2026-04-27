package com.kaiga.expenses.entity;

public class UpdateUserTotal {

    private int id;

    private String oldDate;

    private String oldName;

    private double oldValue;

    private String newDate;

    private String newName;

    private double newValue;

    public UpdateUserTotal(int id, String oldDate, String oldName, double oldValue, String newDate, String newName, double newValue) {
        this.id = id;
        this.oldDate = oldDate;
        this.oldName = oldName;
        this.oldValue = oldValue;
        this.newDate = newDate;
        this.newName = newName;
        this.newValue = newValue;
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

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public double getOldValue() {
        return oldValue;
    }

    public void setOldValue(double oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewDate() {
        return newDate;
    }

    public void setNewDate(String newDate) {
        this.newDate = newDate;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public double getNewValue() {
        return newValue;
    }

    public void setNewValue(double newValue) {
        this.newValue = newValue;
    }
}
