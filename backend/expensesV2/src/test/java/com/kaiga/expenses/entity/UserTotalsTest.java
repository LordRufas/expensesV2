package com.kaiga.expenses.entity;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTotalsTest {


    @Test
    void setName() {
        UserTotals totals = new UserTotals("test", 1);
        assertEquals("test", totals.getName());
        totals.setName("test1");
        assertEquals("test1", totals.getName());
    }

    @Test
    void setValue() {
        UserTotals totals = new UserTotals("test", 1);
        assertEquals(1, totals.getValue());
        totals.setValue(1.1);
        assertEquals(1.1, totals.getValue());
    }
}