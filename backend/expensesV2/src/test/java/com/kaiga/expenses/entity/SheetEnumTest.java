package com.kaiga.expenses.entity;

import org.apache.poi.ss.usermodel.Sheet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SheetEnumTest {

    @Test
    void getId() {
        assertEquals(0,SheetEnum.USERS.getId() );
    }
}