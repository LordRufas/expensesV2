package com.kaiga.expenses.entity;

import java.util.ArrayList;
import java.util.List;


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

    public String sheetData() {
        StringBuilder data = new StringBuilder();
        data.append("{\"data\":[");
        if (!this.headers.getData().isEmpty()) {
            for (ExcelRow excelRow : this.excelRows) {
                data.append("{");
                int iterator = 0;
                for (String row : excelRow.getData()) {
                    data.append("\"").append(headers.getData().get(iterator)).append("\"").append(":");
                    iterator++;
                    data.append("\"").append(row).append("\"");
                    if (excelRow.getData().indexOf(row) != excelRow.getData().size() - 1)
                        data.append(",");
                }
                data.append("}");
                if (excelRows.indexOf(excelRow) != excelRows.size() - 1)
                    data.append(",");
            }
        }
        data.append("]}");
        return data.toString();
    }

}
