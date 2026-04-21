package com.kaiga.expenses.entity;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BaseResponseTest {

    @Test
    void getStatusCode() {
        BaseResponse response = new BaseResponse( "OK", 200);
        assertEquals(200, response.getStatusCode());
    }

    @Test
    void setStatusCode() {
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        assertEquals(200, response.getStatusCode());
    }

    @Test
    void getStatusMessage() {
        BaseResponse response = new BaseResponse( "OK", 200);
        assertEquals("OK", response.getStatusMessage());
    }

    @Test
    void setStatusMessage() {
        BaseResponse response = new BaseResponse( "OK", 200 );
        response.setStatusMessage("test");
        assertEquals("test", response.getStatusMessage());
    }

    @Test
    void getAndSetResponse() {
        BaseResponse response = new BaseResponse();
        Map<String, Object> test = new HashMap<>();
        response.setResponse(test);
        assertNotNull(response.getResponse());
    }
}