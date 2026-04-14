package com.kaiga.expensesV2.entity;

import java.util.ArrayList;

public class ExcelRow {

    private ArrayList<String> data;

    public ExcelRow(ArrayList<String> data) {
        this.data = data;
    }

    public ExcelRow() {
        this.data = new ArrayList<String>();
    }

    public ArrayList<String> getData() {
        return data;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }

    public void addData(String data) {
        this.data.add(data);
    }


}
