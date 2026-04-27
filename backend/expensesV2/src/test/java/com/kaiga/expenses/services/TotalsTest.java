package com.kaiga.expenses.services;

import com.kaiga.expenses.entity.BaseResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "app.repositoryFileName=data/Repository_Test.xlsx")
class TotalsTest {

    @Autowired
    private Totals total;

    @Autowired
    private User user;


    @Test
    void addTotal() {
        user.purgeUsers();
        total.purgeTotals();
        assertEquals("{data=[]}", user.getAllUsers().getResponse().toString());
        user.createNewUser("user",  "pass");
        assertEquals("{data=[{password=pass, id=1, username=user}]}", user.getAllUsers().getResponse().toString());
        total.addTotal(1,"01/01/1990","test",1.0);
        assertEquals("{data=[{date=01/01/1990, name=test, userId=1, value=1.0}]}", total.getAllTotals().getResponse().toString());
    }

    @Test
    void addTotalUserNotFound() {
        user.purgeUsers();
        total.purgeTotals();
        assertEquals("{data=[]}", user.getAllUsers().getResponse().toString());
        BaseResponse response = total.addTotal(1,"01/01/1990","test",1.0);
        assertEquals("User doesn't exist", response.getStatusMessage());
    }

    @Test
    void addTotalTotalAlreadyExists() {
        user.purgeUsers();
        total.purgeTotals();
        assertEquals("{data=[]}", user.getAllUsers().getResponse().toString());
        user.createNewUser("user",  "pass");
        assertEquals("{data=[{password=pass, id=1, username=user}]}", user.getAllUsers().getResponse().toString());
        total.addTotal(1,"01/01/1990","test",1.0);
        assertEquals("{data=[{date=01/01/1990, name=test, userId=1, value=1.0}]}", total.getAllTotals().getResponse().toString());
        BaseResponse response = total.addTotal(1,"01/01/1990","test",1.0);
        assertEquals("Total already exist", response.getStatusMessage());
    }

    @Test
    void getTotal() {
        user.purgeUsers();
        total.purgeTotals();
        assertEquals("{data=[]}", user.getAllUsers().getResponse().toString());
        BaseResponse response = total.getTotal(1);
        assertEquals("User doesn't exist", response.getStatusMessage());

    }

    @Test
    void updateTotal() {
        user.purgeUsers();
        total.purgeTotals();
        user.createNewUser("user",  "pass");
        BaseResponse response = total.addTotal(1,"01/01/1990","test",1.0);
        assertEquals("Total added with success", response.getStatusMessage());
        response = total.updateTotal(1,"01/01/1990","test",1.0
                ,"01/01/1990","test",2.0);
        assertEquals("Total updated with success", response.getStatusMessage());

    }

    @Test
    void updateTotalUserNotFound() {
        user.purgeUsers();
        total.purgeTotals();
        user.createNewUser("user",  "pass");
        BaseResponse response = total.addTotal(1,"01/01/1990","test",1.0);
        assertEquals("Total added with success", response.getStatusMessage());
        response = total.updateTotal(2,"01/01/1990","test",1.0
                ,"01/01/1990","test",2.0);
        assertEquals("User doesn't exist", response.getStatusMessage());

    }

    @Test
    void updateTotalTotalNotFound() {
        user.purgeUsers();
        total.purgeTotals();
        user.createNewUser("user",  "pass");
        BaseResponse response = total.addTotal(1,"01/01/1990","test",1.0);
        assertEquals("Total added with success", response.getStatusMessage());
        response = total.updateTotal(1,"01/01/1990","test1",1.0
                ,"01/01/1990","test",2.0);
        assertEquals("Total doesn't exist", response.getStatusMessage());

    }

    @Test
    void updateTotalTotalAlreadyExists() {
        user.purgeUsers();
        total.purgeTotals();
        user.createNewUser("user",  "pass");
        BaseResponse response = total.addTotal(1,"01/01/1990","test",1.0);
        assertEquals("Total added with success", response.getStatusMessage());
        response = total.updateTotal(1,"01/01/1990","test",1.0
                ,"01/01/1990","test",1.0);
        assertEquals("Total already exist", response.getStatusMessage());

    }

    @Test
    void deleteTotal() {
        user.purgeUsers();
        total.purgeTotals();
        user.createNewUser("user",  "pass");
        BaseResponse response = total.addTotal(1,"01/01/1990","test",1.0);
        assertEquals("Total added with success", response.getStatusMessage());
        response = total.deleteTotal(1,"01/01/1990","test",1.0);
        assertEquals("Total deleted with success", response.getStatusMessage());
    }

    @Test
    void deleteTotalUserNotFound() {
        user.purgeUsers();
        total.purgeTotals();
        user.createNewUser("user",  "pass");
        BaseResponse response = total.addTotal(1,"01/01/1990","test",1.0);
        assertEquals("Total added with success", response.getStatusMessage());
        response = total.deleteTotal(2,"01/01/1990","test",1.0);
        assertEquals("User doesn't exist", response.getStatusMessage());
    }

    @Test
    void deleteTotalTotalNotFound() {
        user.purgeUsers();
        total.purgeTotals();
        user.createNewUser("user",  "pass");
        BaseResponse response = total.addTotal(1,"01/01/1990","test",1.0);
        assertEquals("Total added with success", response.getStatusMessage());
        response = total.deleteTotal(2,"01/01/1990","test1",1.0);
        assertEquals("User doesn't exist", response.getStatusMessage());
    }
}