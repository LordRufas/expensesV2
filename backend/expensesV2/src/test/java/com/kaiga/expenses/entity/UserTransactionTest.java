package com.kaiga.expenses.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTransactionTest {

    @Test
    void setType() {
        UserTransaction transaction = new UserTransaction("test",1,false );
        assertEquals("test",transaction.getType());
        transaction.setType("test1");
        assertEquals("test1",transaction.getType());
    }

    @Test
    void setValue() {
        UserTransaction transaction = new UserTransaction("test",1,false );
        assertEquals(1,transaction.getValue());
        transaction.setValue(1.1);
        assertEquals(1.1,transaction.getValue());
    }

    @Test
    void setRevenue() {
        UserTransaction transaction = new UserTransaction("test",1,false );
        assertFalse(transaction.isRevenue());
        transaction.setRevenue(true);
        assertTrue(transaction.isRevenue());
    }
}