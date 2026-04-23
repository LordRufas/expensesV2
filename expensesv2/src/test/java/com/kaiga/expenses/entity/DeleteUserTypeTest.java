package com.kaiga.expenses.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeleteUserTypeTest {



    @Test
    void testGetDeleteUserTypeTest() {
        DeleteUserType type = new DeleteUserType(1,"test");
        assertEquals(1, type.getId());
        assertEquals("test", type.getName());
    }

    @Test
    void testSetDeleteUserTypeTest() {
        DeleteUserType type = new DeleteUserType(1,"test");
        type.setId(2);
        type.setName("test1");
        assertEquals(2, type.getId());
        assertEquals("test1", type.getName());
    }

}