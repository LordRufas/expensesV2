package com.kaiga.expenses.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpdateUserTransactionsTest {

    @Test
    void setId() {
        UpdateUserTransactions transactions =
                new UpdateUserTransactions(1, "01/01/1990", "test", 1.0, false
                        , "01/01/1990", "test", 1.0, true);
        assertEquals(1, transactions.getId());
        transactions.setId(2);
        assertEquals(2, transactions.getId());
    }

    @Test
    void setOldDate() {
        UpdateUserTransactions transactions =
                new UpdateUserTransactions(1, "01/01/1990", "test", 1.0, false
                        , "01/01/1990", "test", 1.0, true);
        assertEquals("01/01/1990", transactions.getOldDate());
        transactions.setOldDate("01/01/1991");
        assertEquals("01/01/1991", transactions.getOldDate());
    }

    @Test
    void setOldType() {
        UpdateUserTransactions transactions =
                new UpdateUserTransactions(1, "01/01/1990", "test", 1.0, false
                        , "01/01/1990", "test", 1.0, true);
        assertEquals("test", transactions.getOldType());
        transactions.setOldType("test1");
        assertEquals("test1", transactions.getOldType());
    }

    @Test
    void setOldValue() {
        UpdateUserTransactions transactions =
                new UpdateUserTransactions(1, "01/01/1990", "test", 1.0, false
                        , "01/01/1990", "test", 1.0, true);
        assertEquals(1.0, transactions.getOldValue());
        transactions.setOldValue(2.0);
        assertEquals(2.0, transactions.getOldValue());
    }

    @Test
    void setOldIsRevenue() {
        UpdateUserTransactions transactions =
                new UpdateUserTransactions(1, "01/01/1990", "test", 1.0, false
                        , "01/01/1990", "test", 1.0, true);
        assertFalse(transactions.isOldIsRevenue());
        transactions.setOldIsRevenue(true);
        assertTrue(transactions.isOldIsRevenue());
    }

    @Test
    void setNewDate() {
        UpdateUserTransactions transactions =
                new UpdateUserTransactions(1, "01/01/1990", "test", 1.0, false
                        , "01/01/1990", "test", 1.0, true);
        assertEquals("01/01/1990", transactions.getNewDate());
        transactions.setNewDate("01/01/1991");
        assertEquals("01/01/1991", transactions.getNewDate());
    }

    @Test
    void setNewType() {
        UpdateUserTransactions transactions =
                new UpdateUserTransactions(1, "01/01/1990", "test", 1.0, false
                        , "01/01/1990", "test", 1.0, true);
        assertEquals("test", transactions.getNewType());
        transactions.setNewType("test1");
        assertEquals("test1", transactions.getNewType());
    }

    @Test
    void setNewValue() {
        UpdateUserTransactions transactions =
                new UpdateUserTransactions(1, "01/01/1990", "test", 1.0, false
                        , "01/01/1990", "test", 1.0, true);
        assertEquals(1.0, transactions.getNewValue());
        transactions.setNewValue(2.0);
        assertEquals(2.0, transactions.getNewValue());
    }

    @Test
    void setNewIsRevenue() {
        UpdateUserTransactions transactions =
                new UpdateUserTransactions(1, "01/01/1990", "test", 1.0, false
                        , "01/01/1990", "test", 1.0, true);
        assertTrue(transactions.isNewIsRevenue());
        transactions.setOldIsRevenue(false);
        assertTrue(transactions.isNewIsRevenue());
    }
}