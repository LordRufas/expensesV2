package com.kaiga.expenses.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetUserTypesTest {

    @Test
    void testGetUserTypesTestTest() {
        GetUserTypes type = new GetUserTypes(1);
        assertEquals(1, type.getId());
    }

    @Test
    void testSetUserTypesTestTest() {
        GetUserTypes type = new GetUserTypes(1);
        type.setId(2);
        assertEquals(2, type.getId());
    }
}