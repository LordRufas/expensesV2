package com.kaiga.expenses.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetUserTotalsTest {

    @Test
    void setId() {
        GetUserTotals totals = new GetUserTotals(1);
        assertEquals(1,totals.getId());
        totals.setId(2);
        assertEquals(2,totals.getId());
    }
}