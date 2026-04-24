package com.kaiga.expenses.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpdateUserTotalTest {

    @Test
    void setId() {
        UpdateUserTotal total = new UpdateUserTotal(1,"01/01/1990", "test", 1.0, "01/01/1991","test1",2.0);
        assertEquals(1,total.getId());
        total.setId(2);
        assertEquals(2,total.getId());
    }

    @Test
    void setOldDate() {
        UpdateUserTotal total = new UpdateUserTotal(1,"01/01/1990", "test", 1.0, "01/01/1991","test1",2.0);
        assertEquals("01/01/1990",total.getOldDate());
        total.setOldDate("01/01/1991");
        assertEquals("01/01/1991",total.getOldDate());
    }

    @Test
    void setOldName() {
        UpdateUserTotal total = new UpdateUserTotal(1,"01/01/1990", "test", 1.0, "01/01/1991","test1",2.0);
        assertEquals("test",total.getOldName());
        total.setOldName("test1");
        assertEquals("test1",total.getOldName());
    }

    @Test
    void setOldValue() {
        UpdateUserTotal total = new UpdateUserTotal(1,"01/01/1990", "test", 1.0, "01/01/1991","test1",2.0);
        assertEquals(1.0,total.getOldValue());
        total.setOldValue(2.0);
        assertEquals(2.0,total.getOldValue());
    }

    @Test
    void setNewDate() {
        UpdateUserTotal total = new UpdateUserTotal(1,"01/01/1990", "test", 1.0, "01/01/1991","test1",2.0);
        assertEquals("01/01/1991",total.getNewDate());
        total.setNewDate("01/01/1992");
        assertEquals("01/01/1992",total.getNewDate());
    }

    @Test
    void setNewName() {
        UpdateUserTotal total = new UpdateUserTotal(1,"01/01/1990", "test", 1.0, "01/01/1991","test1",2.0);
        assertEquals("test1",total.getNewName());
        total.setNewName("test2");
        assertEquals("test2",total.getNewName());
    }

    @Test
    void setNewValue() {
        UpdateUserTotal total = new UpdateUserTotal(1,"01/01/1990", "test", 1.0, "01/01/1991","test1",2.0);
        assertEquals(2.0,total.getNewValue());
        total.setNewValue(3.0);
        assertEquals(3.0,total.getNewValue());
    }
}