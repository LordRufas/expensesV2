package com.kaiga.expensesV2.entity;

public enum SheetEnum{

    USERS(0),
    TOTALS(1),
    TRANSACTION(2),
    TYPE(3);

    private final int id;


    SheetEnum(int id) {
        this.id = id;
    }

    public int getId(){return this.id;}
}
