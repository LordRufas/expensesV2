package com.kaiga.expenses.controller;

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
        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[]}",userController.getAllUsers());
    }
    @Test
    void createNewUser() {
        user.purgeUsers();
        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[]}",userController.getAllUsers());
        userController.createUser("user", "pass");
        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"password\":\"pass\",\"id\":\"1\",\"username\":\"user\"}]}",userController.getAllUsers());
    }

    @Test
    void loginTestShouldWork() {
        user.purgeUsers();
        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[]}",userController.getAllUsers());
        userController.createUser("user", "pass");
        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"password\":\"pass\",\"id\":\"1\",\"username\":\"user\"}]}",userController.getAllUsers());
        assertEquals("{\"response\":\"Success\",\"statusCode\":\"200\",\"userId\":\"1\"}",userController.login("user", "pass"));
    }

    @Test
    void loginIncorrectPassword() {
        user.purgeUsers();
        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[]}",userController.getAllUsers());
        userController.createUser("user", "pass");
        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"password\":\"pass\",\"id\":\"1\",\"username\":\"user\"}]}",userController.getAllUsers());
        assertEquals("{\"response\":\"Password incorrect\",\"statusCode\":\"401\"}",userController.login("user", "pass1"));
    }

    @Test
    void loginUserNotFound() {
        user.purgeUsers();
        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[]}",userController.getAllUsers());
        userController.createUser("user", "pass");
        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"password\":\"pass\",\"id\":\"1\",\"username\":\"user\"}]}",userController.getAllUsers());
        assertEquals("{\"response\":\"User not found\",\"statusCode\":\"404\"}",userController.login("user1", "pass"));
    }
}