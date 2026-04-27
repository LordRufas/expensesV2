package com.kaiga.expenses.entity;

public class UpdateUserType {
    private int id;

    private String oldName;

    private String newName;

    public UpdateUserType(int id, String name, String newName) {
        this.id = id;
        this.oldName = name;
        this.newName = newName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}
