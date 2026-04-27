package com.kaiga.expenses.entity;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExcelRowTest {

    @Test
    void getData() {
        ExcelRow row = new ExcelRow();
        assertNotNull(row.getData());
        assertTrue(row.getData().isEmpty());
    }

    @Test
    void setData() {
        ExcelRow row = new ExcelRow();
        List<String> newList = new ArrayList<>();
        newList.add("Test 1");
        newList.add("Test 2");

        row.setData(newList);

        assertEquals(2, row.getData().size());
        assertEquals("Test 1", row.getData().get(0));
        assertEquals(newList, row.getData());
    }

    @Test
    void addData() {
        ExcelRow row = new ExcelRow();

        row.addData("Test");
        assertEquals(1, row.getData().size());
        assertEquals("Test", row.getData().get(0));
        assertNotNull(row.getData());

    }
}