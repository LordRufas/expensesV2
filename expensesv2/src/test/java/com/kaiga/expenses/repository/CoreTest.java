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
    void addToSheet() throws IOException {
        List<Object> values = new ArrayList<>();
        values.add(1);
        values.add("test1");
        values.add("Pass2");
        core.addToSheet(0,values);
        ExcelSheet result = core.readFromFile(SheetEnum.USERS.getId());
        assertNotNull(result);
        assertEquals("{ \"data\": [{\"id\":\"1\",\"username\":\"test1\",\"password\":\"Pass2\"}]}", result.sheetData());

    }


}