package com.kaiga.expenses.entity;

import java.util.*;


public class ExcelSheet {

    private final List<ExcelRow> excelRows;
    private final ExcelRow headers;

    public ExcelSheet() {
        this.excelRows = new ArrayList<>();
        this.headers = new ExcelRow();

    }

    public List<ExcelRow> getExcelRows() {
        return excelRows;
    }

    public ExcelRow getHeaders() {
        return headers;
    }

    public void addRow(ExcelRow excelRow) {
        this.excelRows.add(excelRow);
    }

    public void setHeaders() {
        if (excelRows.isEmpty())
            return;
        this.headers.setData(excelRows.get(0).getData());
        this.excelRows.removeFirst();
    }

    public Map<String, Object> sheetData() {

        List<Map<String, String>> dataList = new ArrayList<>();

        for (ExcelRow row : excelRows) {
            Map<String, String> info = new HashMap<>();
            for(int i = 0 ; i< row.getData().size(); i++)
                info.put(headers.getData().get(i), row.getData().get(i));
            dataList.add(info);

        }
        Map<String, Object> root = new HashMap<>();
        root.put("response", "ok");
        root.put("data", dataList);
        return root;
    }
}
