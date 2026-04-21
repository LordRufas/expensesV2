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
        assertEquals("{data=[]}", user.getAllUsers().getResponse().toString());
    }

    @Test
    void createNewUser() {
        user.purgeUsers();
        assertEquals("{data=[]}", user.getAllUsers().getResponse().toString());
        user.createNewUser("user",  "pass");
        assertEquals("{data=[{password=pass, id=1, username=user}]}", user.getAllUsers().getResponse().toString());
    }

    @Test
    void create2NewUser() {
        user.purgeUsers();
        assertEquals("{data=[]}", user.getAllUsers().getResponse().toString());
        user.createNewUser("user",  "pass");
        assertEquals("{data=[{password=pass, id=1, username=user}]}", user.getAllUsers().getResponse().toString());
        user.createNewUser("user1",  "pass1");
        assertEquals("{data=[{password=pass, id=1, username=user}, {password=pass1, id=2, username=user1}]}", user.getAllUsers().getResponse().toString());
    }


}