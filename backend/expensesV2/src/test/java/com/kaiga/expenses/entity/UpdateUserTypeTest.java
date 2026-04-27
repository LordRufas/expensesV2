package com.kaiga.expenses.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpdateUserTypeTest {

    @Test
    void testUpdateUserTypeTest() {
        UpdateUserType type = new UpdateUserType(1,"test", "test1");
        assertEquals(1, type.getId());
        assertEquals("test", type.getOldName());
        assertEquals("test1", type.getNewName());
    }

    @Test
    void testSetUpdateUserTypeTest() {
        UpdateUserType type = new UpdateUserType(1,"test", "test1");
        type.setId(2);
        type.setOldName("test1");
        type.setNewName("test");
        assertEquals(2, type.getId());
        assertEquals("test1", type.getOldName());
        assertEquals("test", type.getNewName());
    }

}