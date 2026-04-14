package com.kaiga.expensesV2.entity;

import java.util.ArrayList;



public class ExcelSheet {

    private ArrayList<ExcelRow> excelRows;
    private ExcelRow headers;

    public ExcelSheet(ArrayList<ExcelRow> excelRows, ExcelRow headers) {
        this.excelRows = excelRows;
        this.headers= headers;
    }

    public ExcelSheet() {
        this.excelRows = new ArrayList<ExcelRow>();
        this.headers = new ExcelRow();

    }

    public void setRows(ArrayList<ExcelRow> excelRows) {
        this.excelRows = excelRows;
    }

    public void addRow(ExcelRow excelRow) {
        this.excelRows.add(excelRow);
    }

    public ArrayList<ExcelRow> getExcelRows() {
        return excelRows;
    }

    public void setExcelRows(ArrayList<ExcelRow> excelRows) {
        this.excelRows = excelRows;
    }

    public ExcelRow getHeaders() {
        return headers;
    }

    public void setHeaders() {
        if(excelRows.isEmpty())
            return;
        this.headers.setData(excelRows.get(0).getData());
        this.excelRows.removeFirst();
    }



    public String SheetData(){
        StringBuilder data = new StringBuilder();
        data.append("{ \"data\": [");
        if(this.headers.getData().size() > 0) {
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
