package com.kaiga.expenses.repository;

import com.kaiga.expenses.entity.ExcelSheet;
import com.kaiga.expenses.entity.SheetEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CoreTest {


    @Autowired
    private Core core;

    @Test
    void readUsersFromFile() {
        ExcelSheet result = core.readFromFile(SheetEnum.USERS.getId());
        assertNotNull(result);
    }

    @Test
    void readTotalsFromFile() {
        ExcelSheet result = core.readFromFile(SheetEnum.TOTALS.getId());
        assertNotNull(result);
    }
    @Test
    void readTranscationFromFile() {
        ExcelSheet result = core.readFromFile(SheetEnum.TRANSACTION.getId());
        assertNotNull(result);
    }
    @Test
    void readTypeFromFile() {
        ExcelSheet result = core.readFromFile(SheetEnum.TYPE.getId());
        assertNotNull(result);
    }
}