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
        assert(true);
    }

    @Test
    void getAllUsers() {
        user.purgeUsers();
        assertEquals("{\"data\":[]}",user.getAllUsers());
    }
    @Test
    void createNewUser() {
        user.purgeUsers();
        assertEquals("{\"data\":[]}",user.getAllUsers());
        user.createNewUser("user", "pass");
        assertEquals("{\"data\":[{\"id\":\"1\",\"username\":\"user\",\"password\":\"pass\"}]}",user.getAllUsers());
    }

    @Test
    void create2NewUser() {
        user.purgeUsers();
        assertEquals("{\"data\":[]}",user.getAllUsers());
        user.createNewUser("user", "pass");
        assertEquals("{\"data\":[{\"id\":\"1\",\"username\":\"user\",\"password\":\"pass\"}]}",user.getAllUsers());
        user.createNewUser("user1", "pass1");
        assertEquals("{\"data\":[{\"id\":\"1\",\"username\":\"user\",\"password\":\"pass\"},{\"id\":\"2\",\"username\":\"user1\",\"password\":\"pass1\"}]}",user.getAllUsers());
    }


}