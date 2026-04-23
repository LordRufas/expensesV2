package com.kaiga.expenses.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataBaseResponseTest {



    @Test
    void testDataBaseResponse(){
        DataBaseResponse test = new DataBaseResponse();
        assertNotNull(test);
    }
}