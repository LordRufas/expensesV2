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
        assertEquals("{\"data\":[]}",userController.getAllUsers());
    }
    @Test
    void createNewUser() {
        user.purgeUsers();
        assertEquals("{\"data\":[]}",userController.getAllUsers());
        userController.createUser("user", "pass");
        assertEquals("{\"data\":[{\"id\":\"1\",\"username\":\"user\",\"password\":\"pass\"}]}",userController.getAllUsers());
    }
}