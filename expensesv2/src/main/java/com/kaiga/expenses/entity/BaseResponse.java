package com.kaiga.expenses.entity;

import tools.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BaseResponse {

    private int statusCode;

    private String statusMessage;

    private Map<String, Object>  response;


    public BaseResponse(String statusMessage,int statusCode,  Map<String, Object>  response) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.response = response;
    }

    public BaseResponse( String statusMessage,int statusCode) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public BaseResponse() {
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Map<String, Object>  getResponse() {
        return response;
    }

    public void setResponse(Map<String, Object>  response) {
        this.response = response;
    }
}
