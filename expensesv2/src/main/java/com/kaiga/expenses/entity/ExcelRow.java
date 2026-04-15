package com.kaiga.expenses.entity;

import java.util.ArrayList;
import java.util.List;

public class ExcelRow {

    private List<String> data;


    public ExcelRow() {
        this.data = new ArrayList<>();
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public void addData(String data) {
        this.data.add(data);
    }


}
