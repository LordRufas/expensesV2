package com.kaiga.expenses.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeleteUserTransactionsTest {

    @Test
    void setId() {
        DeleteUserTransactions transactions = new DeleteUserTransactions(1,"01/01/1990","test", 1.0, false);
        assertEquals(1,transactions.getId());
        transactions.setId(2);
        assertEquals(2,transactions.getId());
    }

    @Test
    void setDate() {
        DeleteUserTransactions transactions = new DeleteUserTransactions(1,"01/01/1990","test", 1.0, false);
        assertEquals("01/01/1990",transactions.getDate());
        transactions.setDate("01/01/1991");
        assertEquals("01/01/1991",transactions.getDate());
    }

    @Test
    void setTypeName() {
        DeleteUserTransactions transactions = new DeleteUserTransactions(1,"01/01/1990","test", 1.0, false);
        assertEquals("test",transactions.getTypeName());
        transactions.setTypeName("test1");
        assertEquals("test1",transactions.getTypeName());
    }

    @Test
    void setValue() {
        DeleteUserTransactions transactions = new DeleteUserTransactions(1,"01/01/1990","test", 1.0, false);
        assertEquals(1.0,transactions.getValue());
        transactions.setValue(2.0);
        assertEquals(2.0,transactions.getValue());
    }

    @Test
    void setRevenue() {
        DeleteUserTransactions transactions = new DeleteUserTransactions(1,"01/01/1990","test", 1.0, false);
        assertEquals(false,transactions.isRevenue());
        transactions.setRevenue(true);
        assertEquals(true,transactions.isRevenue());
    }
}