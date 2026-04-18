package com.kaiga.expenses.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "app.repositoryFileName=data/Repository_Test.xlsx")
class UserTest {

    @Autowired
    private User user;

    @Test
    void purgeUsers(){
        user.purgeUsers();
        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[]}",user.getAllUsers());
    }

    @Test
    void getAllUsers() {
        user.purgeUsers();
        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[]}",user.getAllUsers());
    }
    @Test
    void createNewUser() {
        user.purgeUsers();
        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[]}",user.getAllUsers());
        user.createNewUser("user", "pass");
        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"password\":\"pass\",\"id\":\"1\",\"username\":\"user\"}]}",user.getAllUsers());
    }

    @Test
    void create2NewUser() {
        user.purgeUsers();
        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[]}",user.getAllUsers());
        user.createNewUser("user", "pass");
        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"password\":\"pass\",\"id\":\"1\",\"username\":\"user\"}]}",user.getAllUsers());
        user.createNewUser("user1", "pass1");
        assertEquals("{\"response\":\"ok\",\"statusCode\":\"200\",\"data\":[{\"password\":\"pass\",\"id\":\"1\",\"username\":\"user\"},{\"password\":\"pass1\",\"id\":\"2\",\"username\":\"user1\"}]}",user.getAllUsers());
    }


}