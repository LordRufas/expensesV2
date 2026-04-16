package com.kaiga.expenses.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExcelSheetTest {

    @Test
    void addRow() {
        ExcelSheet excelSheet = new ExcelSheet();
        ExcelRow row = new ExcelRow();
        row.addData("teste");
        excelSheet.addRow(row);
        assertNotNull(excelSheet.getExcelRows());
        assertEquals(excelSheet.getExcelRows().get(0), row);
    }

    @Test
    void setHeaders() {
        ExcelSheet excelSheet = new ExcelSheet();
        ExcelRow row = new ExcelRow();
        row.addData("teste");
        excelSheet.addRow(row);
        excelSheet.setHeaders();
        assertNotNull(excelSheet.getHeaders());
        assertEquals(excelSheet.getHeaders().getData().get(0), row.getData().get(0));
    }

    @Test
    void testSheetData() {
        ExcelSheet excelSheet = new ExcelSheet();
        ExcelRow row = new ExcelRow();
        row.addData("userId");
        row.addData("date");
        row.addData("typeid");
        row.addData("value");
        row.addData("isRevenue");
        ExcelRow row1 = new ExcelRow();
        row1.addData("1.0");
        row1.addData("12.0");
        row1.addData("1.0");
        row1.addData("1.0");
        row1.addData("true");
        excelSheet.addRow(row);
        excelSheet.addRow(row1);
        excelSheet.setHeaders();
        assertEquals("{\"data\":[{\"userId\":\"1.0\",\"date\":\"12.0\",\"typeid\":\"1.0\",\"value\":\"1.0\",\"isRevenue\":\"true\"}]}",excelSheet.sheetData());
    }

    @Test
    void testAddRow() {
        ExcelSheet excelSheet = new ExcelSheet();
        ExcelRow row = new ExcelRow();
        row.addData("userid");
        row.addData("date");
        row.addData("typeid");
        row.addData("value");
        row.addData("isRevenue");
        excelSheet.addRow(row);
        assertFalse(excelSheet.getExcelRows().isEmpty());
        assertEquals(excelSheet.getExcelRows().get(0).getData().get(0), row.getData().get(0));

    }

    @Test
    void testSetHeaders() {
        ExcelSheet excelSheet = new ExcelSheet();
        ExcelRow row = new ExcelRow();
        row.addData("userid");
        row.addData("date");
        row.addData("typeid");
        row.addData("value");
        row.addData("isRevenue");
        excelSheet.addRow(row);
        excelSheet.setHeaders();
        assertFalse(excelSheet.getHeaders().getData().isEmpty());
        assertEquals(excelSheet.getHeaders().getData().get(0), row.getData().get(0));

    }

    @Test
    void testSetHeadersEmpty() {
        ExcelSheet excelSheet = new ExcelSheet();
        excelSheet.setHeaders();
        assertTrue(excelSheet.getHeaders().getData().isEmpty());
    }


}