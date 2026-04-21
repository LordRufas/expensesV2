package com.kaiga.expenses.controller;

import com.kaiga.expenses.entity.BaseResponse;
import com.kaiga.expenses.services.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(properties = "app.repositoryFileName=data/Repository_Test.xlsx")
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private User user;


    @Test
    void getAllUsers() {
        user.purgeUsers();
        assertEquals("{data=[]}", userController.getAllUsers().getResponse().toString());
    }
    @Test
    void createNewUser() {
        user.purgeUsers();
        assertEquals("{data=[]}", userController.getAllUsers().getResponse().toString());
        userController.createUser("user",  "pass");
        assertEquals("{data=[{password=pass, id=1, username=user}]}", userController.getAllUsers().getResponse().toString());
    }

    @Test
    void loginTestShouldWork() {
        user.purgeUsers();
        BaseResponse response = userController.getAllUsers();
        assertEquals(200,response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        assertEquals("{data=[]}",response.getResponse().toString());

        response =userController.createUser("user", "pass");
        assertEquals(200,response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());

        response = userController.getAllUsers();
        assertEquals(200,response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        assertEquals("{data=[{password=pass, id=1, username=user}]}", userController.getAllUsers().getResponse().toString());

        response = userController.login("user",  "pass");

        assertEquals(200,response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        assertEquals("{userId=1}", response.getResponse().toString());

    }

    @Test
    void loginIncorrectPassword() {
        user.purgeUsers();
        BaseResponse response = userController.getAllUsers();
        assertEquals("{data=[]}", response.getResponse().toString());
        response = userController.createUser("user",  "pass");
        assertEquals(200, response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        response = userController.login("user",  "pass1");
        assertEquals(401, response.getStatusCode());
        assertEquals("Password incorrect", response.getStatusMessage());
    }

    @Test
    void loginUserNotFound() {
        user.purgeUsers();
        BaseResponse response = userController.getAllUsers();
        assertEquals("{data=[]}", response.getResponse().toString());
        response = userController.createUser("user",  "pass");
        assertEquals(200, response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        response = userController.login("user1",  "pass1");
        assertEquals(404, response.getStatusCode());
        assertEquals("User not found", response.getStatusMessage());}
}