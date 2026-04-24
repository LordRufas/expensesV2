package com.kaiga.expenses.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeleteUserTotalTest {

    @Test
    void setId() {
        DeleteUserTotal userTotal = new DeleteUserTotal(1,"test","01/01/1990",1.0);
        assertEquals(1, userTotal.getId());
        userTotal.setId(2);
        assertEquals(2, userTotal.getId());
    }

    @Test
    void setName() {
        DeleteUserTotal userTotal = new DeleteUserTotal(1,"test","01/01/1990",1.0);
        assertEquals("test", userTotal.getName());
        userTotal.setName("test1");
        assertEquals("test1", userTotal.getName());
    }

    @Test
    void setDate() {
        DeleteUserTotal userTotal = new DeleteUserTotal(1,"test","01/01/1990",1.0);
        assertEquals("01/01/1990", userTotal.getDate());
        userTotal.setDate("01/02/1990");
        assertEquals("01/02/1990", userTotal.getDate());
    }

    @Test
    void setValue() {
        DeleteUserTotal userTotal = new DeleteUserTotal(1,"test","01/01/1990",1.0);
        assertEquals(1.0, userTotal.getValue());
        userTotal.setValue(2.0);
        assertEquals(2.0, userTotal.getValue());
    }
}