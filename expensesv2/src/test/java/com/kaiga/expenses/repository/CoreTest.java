package com.kaiga.expenses.repository;

import com.kaiga.expenses.entity.ExcelSheet;
import com.kaiga.expenses.entity.SheetEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "app.repositoryFileName=data/Repository_Test.xlsx")
class CoreTest {


    @Autowired
    private Core core;

    @Test
    void readUsersFromFile() throws IOException {
        ExcelSheet result = core.readFromFile(SheetEnum.USERS.getId());
        assertNotNull(result);
    }

    @Test
    void readTotalsFromFile() throws IOException {
        ExcelSheet result = core.readFromFile(SheetEnum.TOTALS.getId());
        assertNotNull(result);
    }
    @Test
    void readTranscationFromFile() throws IOException {
        ExcelSheet result = core.readFromFile(SheetEnum.TRANSACTION.getId());
        assertNotNull(result, "The transaction sheet should not be null");
    }
    @Test
    void readTypeFromFile() throws IOException {
        ExcelSheet result = core.readFromFile(SheetEnum.TYPE.getId());
        assertNotNull(result);
    }

    @Test
    void purgeUsers() {
        String response = core.purge(SheetEnum.USERS.getId());
        assertEquals("Success", response);
    }

    @Test
    void purgeTransactions() {
        String response = core.purge(SheetEnum.TRANSACTION.getId());
        assertEquals("Success", response);
    }

    @Test
    void purgeType() {
        String response = core.purge(SheetEnum.TYPE.getId());
        assertEquals("Success", response);
    }

    @Test
    void purgeTotals() {
        String response = core.purge(SheetEnum.TOTALS.getId());
        assertEquals("Success", response);
    }

    @Test
    void addUsersToSheet() throws IOException {
        core.purge(SheetEnum.USERS.getId());
        ExcelSheet result = core.readFromFile(SheetEnum.USERS.getId());

        assertEquals("{ \"data\": []}", result.sheetData());

        List<Object> values = new ArrayList<>();
        values.add(1);
        values.add("test1");
        values.add("Pass2");

        core.addToSheet(SheetEnum.USERS.getId(),values);
        result = core.readFromFile(SheetEnum.USERS.getId());

        assertNotNull(result);
        assertEquals("{ \"data\": [{\"id\":\"1\",\"username\":\"test1\",\"password\":\"Pass2\"}]}", result.sheetData());
    }

    @Test
    void addTotalsToSheet() throws IOException {
        core.purge(SheetEnum.TOTALS.getId());
        ExcelSheet result = core.readFromFile(SheetEnum.TOTALS.getId());

        assertEquals("{ \"data\": []}", result.sheetData());

        List<Object> values = new ArrayList<>();
        values.add(1);
        values.add("01/01/1990");
        values.add("Test");
        values.add(1.0);

        core.addToSheet(SheetEnum.TOTALS.getId(),values);
        result = core.readFromFile(SheetEnum.TOTALS.getId());

        assertNotNull(result);
        assertEquals("{ \"data\": [{\"userId\":\"1\",\"date\":\"01/01/1990\",\"name\":\"Test\",\"value\":\"1.0\"}]}", result.sheetData());
    }

    @Test
    void addTransactionToSheet() throws IOException {
        core.purge(SheetEnum.TRANSACTION.getId());
        ExcelSheet result = core.readFromFile(SheetEnum.TRANSACTION.getId());

        assertEquals("{ \"data\": []}", result.sheetData());

        List<Object> values = new ArrayList<>();
        values.add(1);
        values.add("01/01/1990");
        values.add(1);
        values.add(1.0);
        values.add(Boolean.TRUE);

        core.addToSheet(SheetEnum.TRANSACTION.getId(),values);
        result = core.readFromFile(SheetEnum.TRANSACTION.getId());

        assertNotNull(result);
        assertEquals("{ \"data\": [{\"userId\":\"1\",\"date\":\"01/01/1990\",\"typeid\":\"1\",\"value\":\"1.0\",\"isRevenue\":\"true\"}]}", result.sheetData());
    }

    @Test
    void addTypeToSheet() throws IOException {
        core.purge(SheetEnum.TYPE.getId());
        ExcelSheet result = core.readFromFile(SheetEnum.TYPE.getId());

        assertEquals("{ \"data\": []}", result.sheetData());

        List<Object> values = new ArrayList<>();
        values.add(1);
        values.add("Test");

        core.addToSheet(SheetEnum.TYPE.getId(),values);
        result = core.readFromFile(SheetEnum.TYPE.getId());

        assertNotNull(result);
        assertEquals("{ \"data\": [{\"userId\":\"1\",\"name\":\"Test\"}]}", result.sheetData());
    }


    @Test
    void deleteUserRow() {
        core.purge(SheetEnum.USERS.getId());
        List<Object> values = new ArrayList<>();
        values.add(1);
        values.add("Test");
        values.add("Pass");

        core.addToSheet(SheetEnum.USERS.getId(),values);
        ExcelSheet result = core.readFromFile(SheetEnum.USERS.getId());
        assertNotNull(result);

        assertEquals("{ \"data\": [{\"id\":\"1\",\"username\":\"Test\",\"password\":\"Pass\"}]}", result.sheetData());
        List<Object> values1 = new ArrayList<>();
        values1.add(2);
        values1.add("Test2");
        values1.add("Pass2");

        core.addToSheet(SheetEnum.USERS.getId(),values1);
        result = core.readFromFile(SheetEnum.USERS.getId());

        assertEquals("{ \"data\": [{\"id\":\"1\",\"username\":\"Test\",\"password\":\"Pass\"},{\"id\":\"2\",\"username\":\"Test2\",\"password\":\"Pass2\"}]}", result.sheetData());

        core.deleteRow(SheetEnum.USERS.getId(),values1);

        result = core.readFromFile(SheetEnum.USERS.getId());

        assertEquals("{ \"data\": [{\"id\":\"1\",\"username\":\"Test\",\"password\":\"Pass\"}]}", result.sheetData());

    }

    @Test
    void deleteTransactionRow() {
        core.purge(SheetEnum.TRANSACTION.getId());
        List<Object> values = new ArrayList<>();
        values.add(1);
        values.add("01/01/1990");
        values.add(1);
        values.add(1.0);
        values.add(Boolean.TRUE);

        core.addToSheet(SheetEnum.TRANSACTION.getId(),values);
        ExcelSheet result = core.readFromFile(SheetEnum.TRANSACTION.getId());
        assertNotNull(result);

        assertEquals("{ \"data\": [{\"userId\":\"1\",\"date\":\"01/01/1990\",\"typeid\":\"1\",\"value\":\"1.0\",\"isRevenue\":\"true\"}]}", result.sheetData());
        List<Object> values1 = new ArrayList<>();
        values1.add(2);
        values1.add("02/01/1990");
        values1.add(2);
        values1.add(2.0);
        values1.add(Boolean.FALSE);

        core.addToSheet(SheetEnum.TRANSACTION.getId(),values1);

        result = core.readFromFile(SheetEnum.TRANSACTION.getId());

        assertEquals("{ \"data\": [{\"userId\":\"1\",\"date\":\"01/01/1990\",\"typeid\":\"1\",\"value\":\"1.0\",\"isRevenue\":\"true\"},{\"userId\":\"2\",\"date\":\"02/01/1990\",\"typeid\":\"2\",\"value\":\"2.0\",\"isRevenue\":\"false\"}]}", result.sheetData());

        core.deleteRow(SheetEnum.TRANSACTION.getId(),values1);

        result = core.readFromFile(SheetEnum.TRANSACTION.getId());

        assertEquals("{ \"data\": [{\"userId\":\"1\",\"date\":\"01/01/1990\",\"typeid\":\"1\",\"value\":\"1.0\",\"isRevenue\":\"true\"}]}", result.sheetData());

    }

    @Test
    void deleteTotalsRow() {
        core.purge(SheetEnum.TOTALS.getId());
        List<Object> values = new ArrayList<>();
        values.add(1);
        values.add("01/01/1990");
        values.add("cenas");
        values.add(1.0);
        core.addToSheet(SheetEnum.TOTALS.getId(),values);

        ExcelSheet result = core.readFromFile(SheetEnum.TOTALS.getId());
        assertNotNull(result);
        assertEquals("{ \"data\": [{\"userId\":\"1\",\"date\":\"01/01/1990\",\"name\":\"cenas\",\"value\":\"1.0\"}]}", result.sheetData());

        List<Object> values1 = new ArrayList<>();
        values1.add(2);
        values1.add("02/01/1990");
        values1.add("cenas2");
        values1.add(2.0);
        core.addToSheet(SheetEnum.TOTALS.getId(),values1);

        result = core.readFromFile(SheetEnum.TOTALS.getId());

        assertEquals("{ \"data\": [{\"userId\":\"1\",\"date\":\"01/01/1990\",\"name\":\"cenas\",\"value\":\"1.0\"},{\"userId\":\"2\",\"date\":\"02/01/1990\",\"name\":\"cenas2\",\"value\":\"2.0\"}]}", result.sheetData());

        core.deleteRow(SheetEnum.TOTALS.getId(),values1);

        result = core.readFromFile(SheetEnum.TOTALS.getId());

        assertEquals("{ \"data\": [{\"userId\":\"1\",\"date\":\"01/01/1990\",\"name\":\"cenas\",\"value\":\"1.0\"}]}", result.sheetData());

    }

    @Test
    void deleteTypeRow() {
        core.purge(SheetEnum.TYPE.getId());
        List<Object> values = new ArrayList<>();
        values.add(1);
        values.add("Test");

        core.addToSheet(SheetEnum.TYPE.getId(),values);
        ExcelSheet result = core.readFromFile(SheetEnum.TYPE.getId());
        assertNotNull(result);
        assertEquals("{ \"data\": [{\"userId\":\"1\",\"name\":\"Test\"}]}", result.sheetData());

        List<Object> values1 = new ArrayList<>();
        values1.add(2);
        values1.add("Test1");

        core.addToSheet(SheetEnum.TYPE.getId(),values1);

        result = core.readFromFile(SheetEnum.TYPE.getId());

        assertEquals("{ \"data\": [{\"userId\":\"1\",\"name\":\"Test\"},{\"userId\":\"2\",\"name\":\"Test1\"}]}", result.sheetData());

        core.deleteRow(SheetEnum.TYPE.getId(),values1);

        result = core.readFromFile(SheetEnum.TYPE.getId());

        assertEquals("{ \"data\": [{\"userId\":\"1\",\"name\":\"Test\"}]}", result.sheetData());

    }
}