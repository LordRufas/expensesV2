package com.kaiga.expenses.repository;

import com.kaiga.expenses.entity.ExcelSheet;
import com.kaiga.expenses.entity.SheetEnum;
import com.kaiga.expenses.utilities.Utilities;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "app.repositoryFileName=data/Repository_Test.xlsx")
class CoreTest {


    @Autowired
    private Core core;

    @Test
    void readUsersFromFile() {
        ExcelSheet result = core.read(SheetEnum.USERS.getId());
        assertNotNull(result);
    }

    @Test
    void readUsersFromFileShouldFail()  {
        assertNull(core.read(-1));
    }

    @Test
    void readTotalsFromFile() {
        ExcelSheet result = core.read(SheetEnum.TOTALS.getId());
        assertNotNull(result);
    }
    @Test
    void readTransactionFromFile() {
        ExcelSheet result = core.read(SheetEnum.TRANSACTION.getId());
        assertNotNull(result);

        core.purge(SheetEnum.TRANSACTION.getId());
        core.read(SheetEnum.TRANSACTION.getId());
        

        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[]}", Utilities.createJsonResponse("OK", "200",  result.sheetData()));

        List<Object> values = new ArrayList<>();
        values.add(1);
        values.add("01/01/1990");
        values.add(1);
        values.add(1.0);
        values.add(Boolean.TRUE);

        core.add(SheetEnum.TRANSACTION.getId(),values);
        result = core.read(SheetEnum.TRANSACTION.getId());

        assertNotNull(result);
        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"date\":\"01/01/1990\",\"typeId\":\"1\",\"userId\":\"1\",\"value\":\"1.0\",\"isRevenue\":\"true\"}]}", Utilities.createJsonResponse("OK", "200",  result.sheetData()));

    }
    @Test
    void readTypeFromFile() {
        ExcelSheet result = core.read(SheetEnum.TYPE.getId());
        assertNotNull(result);
    }

    @Test
    void purgeUsers() {
        String response = core.purge(SheetEnum.USERS.getId());
        assertEquals("Success", response);
    }

    @Test
    void purgeUsersShouldFail() {
        assertNotEquals("Success", core.purge(-1));
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
    void addUsersToSheet() {
        core.purge(SheetEnum.USERS.getId());
        ExcelSheet result = core.read(SheetEnum.USERS.getId());
        

        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[]}", Utilities.createJsonResponse("OK", "200",  result.sheetData()));

        List<Object> values = new ArrayList<>();
        values.add(1);
        values.add("test1");
        values.add("Pass2");

        core.add(SheetEnum.USERS.getId(),values);
        result = core.read(SheetEnum.USERS.getId());

        assertNotNull(result);
        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"password\":\"Pass2\",\"id\":\"1\",\"username\":\"test1\"}]}", Utilities.createJsonResponse("OK", "200",  result.sheetData()));
    }

    @Test
    void addUsersToSheetShouldFail()  {
        List<Object> values = new ArrayList<>();
        values.add(1);
        values.add("test1");
        values.add("Pass2");
        assertNotEquals("Success", core.add(-1,values));
    }

    @Test
    void addTotalsToSheet() {
        core.purge(SheetEnum.TOTALS.getId());
        ExcelSheet result = core.read(SheetEnum.TOTALS.getId());
        

        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[]}",  Utilities.createJsonResponse("OK", "200",  result.sheetData()));

        List<Object> values = new ArrayList<>();
        values.add(1);
        values.add("01/01/1990");
        values.add("Test");
        values.add(1.0);

        core.add(SheetEnum.TOTALS.getId(),values);
        result = core.read(SheetEnum.TOTALS.getId());

        assertNotNull(result);
        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"date\":\"01/01/1990\",\"name\":\"Test\",\"userId\":\"1\",\"value\":\"1.0\"}]}", Utilities.createJsonResponse("OK", "200",  result.sheetData()));
    }

    @Test
    void addTransactionToSheet() {
        core.purge(SheetEnum.TRANSACTION.getId());
        
        Map<String, Object> response = core.read(SheetEnum.TRANSACTION.getId()).sheetData();
        String actualJson =  Utilities.createJsonResponse("OK", "200",  response);

        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[]}", actualJson);

        List<Object> values = new ArrayList<>();
        values.add(1);
        values.add("01/01/1990");
        values.add(1);
        values.add(1.0);
        values.add(Boolean.TRUE);

        core.add(SheetEnum.TRANSACTION.getId(),values);
        actualJson = Utilities.createJsonResponse("OK", "200",  core.read(SheetEnum.TRANSACTION.getId()).sheetData());

        assertNotNull(actualJson);
        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"date\":\"01/01/1990\",\"typeId\":\"1\",\"userId\":\"1\",\"value\":\"1.0\",\"isRevenue\":\"true\"}]}", actualJson);
    }

    @Test
    void addTypeToSheet() {
        core.purge(SheetEnum.TYPE.getId());
        ExcelSheet result = core.read(SheetEnum.TYPE.getId());
        

        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[]}", Utilities.createJsonResponse("OK", "200",  result.sheetData()));

        List<Object> values = new ArrayList<>();
        values.add(1);
        values.add("Test");

        core.add(SheetEnum.TYPE.getId(),values);
        result = core.read(SheetEnum.TYPE.getId());

        assertNotNull(result);
        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"name\":\"Test\",\"userId\":\"1\"}]}", Utilities.createJsonResponse("OK", "200",  result.sheetData()));
    }


    @Test
    void deleteUserRow() {
        core.purge(SheetEnum.USERS.getId());
        List<Object> values = new ArrayList<>();
        values.add(1);
        values.add("Test");
        values.add("Pass");

        core.add(SheetEnum.USERS.getId(),values);
        ExcelSheet result = core.read(SheetEnum.USERS.getId());
        assertNotNull(result);
        

        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"password\":\"Pass\",\"id\":\"1\",\"username\":\"Test\"}]}", Utilities.createJsonResponse("OK", "200",  result.sheetData()));
        List<Object> values1 = new ArrayList<>();
        values1.add(2);
        values1.add("Test2");
        values1.add("Pass2");

        core.add(SheetEnum.USERS.getId(),values1);
        result = core.read(SheetEnum.USERS.getId());

        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"password\":\"Pass\",\"id\":\"1\",\"username\":\"Test\"},{\"password\":\"Pass2\",\"id\":\"2\",\"username\":\"Test2\"}]}", Utilities.createJsonResponse("OK", "200",  result.sheetData()));

        core.delete(SheetEnum.USERS.getId(),values1);

        result = core.read(SheetEnum.USERS.getId());

        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"password\":\"Pass\",\"id\":\"1\",\"username\":\"Test\"}]}", Utilities.createJsonResponse("OK", "200",  result.sheetData()));

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

        core.add(SheetEnum.TRANSACTION.getId(),values);
        ExcelSheet result = core.read(SheetEnum.TRANSACTION.getId());
        assertNotNull(result);
        

        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"date\":\"01/01/1990\",\"typeId\":\"1\",\"userId\":\"1\",\"value\":\"1.0\",\"isRevenue\":\"true\"}]}", Utilities.createJsonResponse("OK", "200",  result.sheetData()));
        List<Object> values1 = new ArrayList<>();
        values1.add(2);
        values1.add("02/01/1990");
        values1.add(2);
        values1.add(2.0);
        values1.add(Boolean.FALSE);

        core.add(SheetEnum.TRANSACTION.getId(),values1);

        result = core.read(SheetEnum.TRANSACTION.getId());

        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"date\":\"01/01/1990\",\"typeId\":\"1\",\"userId\":\"1\",\"value\":\"1.0\",\"isRevenue\":\"true\"},{\"date\":\"02/01/1990\",\"typeId\":\"2\",\"userId\":\"2\",\"value\":\"2.0\",\"isRevenue\":\"false\"}]}", Utilities.createJsonResponse("OK", "200",  result.sheetData()));

        core.delete(SheetEnum.TRANSACTION.getId(),values1);

        result = core.read(SheetEnum.TRANSACTION.getId());

        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"date\":\"01/01/1990\",\"typeId\":\"1\",\"userId\":\"1\",\"value\":\"1.0\",\"isRevenue\":\"true\"}]}", Utilities.createJsonResponse("OK", "200",  result.sheetData()));

    }

    @Test
    void deleteTotalsRow() {
        core.purge(SheetEnum.TOTALS.getId());
        List<Object> values = new ArrayList<>();
        values.add(1);
        values.add("01/01/1990");
        values.add("cenas");
        values.add(1.0);
        core.add(SheetEnum.TOTALS.getId(),values);

        ExcelSheet result = core.read(SheetEnum.TOTALS.getId());
        assertNotNull(result);
        

        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"date\":\"01/01/1990\",\"name\":\"cenas\",\"userId\":\"1\",\"value\":\"1.0\"}]}", Utilities.createJsonResponse("OK", "200",  result.sheetData()));

        List<Object> values1 = new ArrayList<>();
        values1.add(2);
        values1.add("02/01/1990");
        values1.add("cenas2");
        values1.add(2.0);
        core.add(SheetEnum.TOTALS.getId(),values1);

        result = core.read(SheetEnum.TOTALS.getId());

        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"date\":\"01/01/1990\",\"name\":\"cenas\",\"userId\":\"1\",\"value\":\"1.0\"},{\"date\":\"02/01/1990\",\"name\":\"cenas2\",\"userId\":\"2\",\"value\":\"2.0\"}]}", Utilities.createJsonResponse("OK", "200",  result.sheetData()));

        core.delete(SheetEnum.TOTALS.getId(),values1);

        result = core.read(SheetEnum.TOTALS.getId());

        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"date\":\"01/01/1990\",\"name\":\"cenas\",\"userId\":\"1\",\"value\":\"1.0\"}]}", Utilities.createJsonResponse("OK", "200",  result.sheetData()));

    }

    @Test
    void deleteTypeRow() {
        core.purge(SheetEnum.TYPE.getId());
        List<Object> values = new ArrayList<>();
        values.add(1);
        values.add("Test");

        core.add(SheetEnum.TYPE.getId(),values);
        ExcelSheet result = core.read(SheetEnum.TYPE.getId());
        assertNotNull(result);
        

        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"name\":\"Test\",\"userId\":\"1\"}]}", Utilities.createJsonResponse("OK", "200",  result.sheetData()));

        List<Object> values1 = new ArrayList<>();
        values1.add(2);
        values1.add("Test1");


        core.add(SheetEnum.TYPE.getId(),values1);

        result = core.read(SheetEnum.TYPE.getId());

        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"name\":\"Test\",\"userId\":\"1\"},{\"name\":\"Test1\",\"userId\":\"2\"}]}", Utilities.createJsonResponse("OK", "200",  result.sheetData()));

        core.delete(SheetEnum.TYPE.getId(),values1);

        result = core.read(SheetEnum.TYPE.getId());

        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"name\":\"Test\",\"userId\":\"1\"}]}", Utilities.createJsonResponse("OK", "200",  result.sheetData()));

    }

    @Test
    void deleteRowShouldFail()  {
        List<Object> values = new ArrayList<>();
        values.add(1);
        values.add("test1");
        values.add("Pass2");
        assertNotEquals("Success", core.delete(-1,values));
    }

    @Test
    void updateUserRow() {
        core.purge(SheetEnum.USERS.getId());
        List<Object> values = new ArrayList<>();
        values.add(1);
        values.add("Test");
        values.add("Pass");

        core.add(SheetEnum.USERS.getId(),values);
        ExcelSheet result = core.read(SheetEnum.USERS.getId());
        assertNotNull(result);
        
        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"password\":\"Pass\",\"id\":\"1\",\"username\":\"Test\"}]}", Utilities.createJsonResponse("OK", "200",  result.sheetData()));
        List<Object> newValues = new ArrayList<>();
        newValues.add(2);
        newValues.add("Test2");
        newValues.add("Pass2");

        core.update(SheetEnum.USERS.getId(),values,newValues);
        result = core.read(SheetEnum.USERS.getId());

        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"password\":\"Pass2\",\"id\":\"2\",\"username\":\"Test2\"}]}", Utilities.createJsonResponse("OK", "200",  result.sheetData()));

    }

    @Test
    void updateTransactionRow() {
        core.purge(SheetEnum.TRANSACTION.getId());
        List<Object> values = new ArrayList<>();
        values.add(1);
        values.add("01/01/1990");
        values.add(1);
        values.add(1.0);
        values.add(Boolean.TRUE);

        core.add(SheetEnum.TRANSACTION.getId(),values);
        ExcelSheet result = core.read(SheetEnum.TRANSACTION.getId());
        assertNotNull(result);

        

        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"date\":\"01/01/1990\",\"typeId\":\"1\",\"userId\":\"1\",\"value\":\"1.0\",\"isRevenue\":\"true\"}]}", Utilities.createJsonResponse("OK", "200",  result.sheetData()));
        List<Object> values1 = new ArrayList<>();
        values1.add(2);
        values1.add("02/01/1990");
        values1.add(2);
        values1.add(2.0);
        values1.add(Boolean.FALSE);

        core.update(SheetEnum.TRANSACTION.getId(),values, values1);

        result = core.read(SheetEnum.TRANSACTION.getId());

        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"date\":\"02/01/1990\",\"typeId\":\"2\",\"userId\":\"2\",\"value\":\"2.0\",\"isRevenue\":\"false\"}]}", Utilities.createJsonResponse("OK", "200",  result.sheetData()));
 }

    @Test
    void updateTotalsRow() {
        core.purge(SheetEnum.TOTALS.getId());
        List<Object> values = new ArrayList<>();
        values.add(1);
        values.add("01/01/1990");
        values.add("cenas");
        values.add(1.0);
        core.add(SheetEnum.TOTALS.getId(),values);

        ExcelSheet result = core.read(SheetEnum.TOTALS.getId());
        assertNotNull(result);
        

        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"date\":\"01/01/1990\",\"name\":\"cenas\",\"userId\":\"1\",\"value\":\"1.0\"}]}", Utilities.createJsonResponse("OK", "200",  result.sheetData()));

        List<Object> values1 = new ArrayList<>();
        values1.add(2);
        values1.add("02/01/1990");
        values1.add("cenas2");
        values1.add(2.0);
        core.update(SheetEnum.TOTALS.getId(),values, values1);

        result = core.read(SheetEnum.TOTALS.getId());

        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"date\":\"02/01/1990\",\"name\":\"cenas2\",\"userId\":\"2\",\"value\":\"2.0\"}]}", Utilities.createJsonResponse("OK", "200",  result.sheetData()));

    }

    @Test
    void updateTypeRow() {
        core.purge(SheetEnum.TYPE.getId());
        List<Object> values = new ArrayList<>();
        values.add(1);
        values.add("Test");

        core.add(SheetEnum.TYPE.getId(),values);
        ExcelSheet result = core.read(SheetEnum.TYPE.getId());
        assertNotNull(result);
        

        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"name\":\"Test\",\"userId\":\"1\"}]}", Utilities.createJsonResponse("OK", "200",  result.sheetData()));

        List<Object> values1 = new ArrayList<>();
        values1.add(2);
        values1.add("Test1");

        core.update(SheetEnum.TYPE.getId(),values, values1);

        result = core.read(SheetEnum.TYPE.getId());

        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"name\":\"Test1\",\"userId\":\"2\"}]}", Utilities.createJsonResponse("OK", "200",  result.sheetData()));

    }

    @Test
    void updateShouldFail()  {
        List<Object> values = new ArrayList<>();
        values.add(1);
        values.add("test1");
        values.add("Pass2");

        List<Object> values1 = new ArrayList<>();
        values1.add(2);
        values1.add("Test1");
        assertNotEquals("Success", core.update(-1,values, values1));
    }

}