package com.kaiga.expenses.services;

import com.kaiga.expenses.entity.BaseResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "app.repositoryFileName=data/Repository_Test.xlsx")
class TypeTest {

    @Autowired
    private Type type;

    @Autowired
    private User user;

    @Test
    void addType() {
        user.purgeUsers();
        type.purgeTypes();
        assertEquals("{data=[]}", user.getAllUsers().getResponse().toString());
        user.createNewUser("user",  "pass");
        assertEquals("{data=[{password=pass, id=1, username=user}]}", user.getAllUsers().getResponse().toString());
        type.addType(1, "test");
        assertEquals("{types=[{name=test, userId=1}]}",type.getTypes(1).getResponse().toString());
    }

    @Test
    void addTypeShouldFail() {
        user.purgeUsers();
        type.purgeTypes();
        assertEquals("{data=[]}", user.getAllUsers().getResponse().toString());
        user.createNewUser("user",  "pass");
        assertEquals("{data=[{password=pass, id=1, username=user}]}", user.getAllUsers().getResponse().toString());
        BaseResponse  response = type.addType(2, "test");
        assertEquals("User doesn't exist",response.getStatusMessage());
    }

    @Test
    void addTypeShouldFail2() {
        user.purgeUsers();
        type.purgeTypes();
        assertEquals("{data=[]}", user.getAllUsers().getResponse().toString());
        user.createNewUser("user",  "pass");
        assertEquals("{data=[{password=pass, id=1, username=user}]}", user.getAllUsers().getResponse().toString());
        type.addType(1, "test");
        BaseResponse  response = type.addType(1, "test");
        assertEquals("Type already exist",response.getStatusMessage());}

    @Test
    void getAllTypes() {
        user.purgeUsers();
        type.purgeTypes();
        assertEquals("{data=[]}", user.getAllUsers().getResponse().toString());
        user.createNewUser("user",  "pass");
        assertEquals("{data=[{password=pass, id=1, username=user}]}", user.getAllUsers().getResponse().toString());
        user.createNewUser("user1",  "pass1");
        type.addType(1, "test");
        type.addType(1, "test1");
        type.addType(2, "test1");
        assertEquals("{data=[{name=test, userId=1}, {name=test1, userId=1}, {name=test1, userId=2}]}",type.getAllTypes().getResponse().toString());
    }

    @Test
    void getTypesByUser() {
        user.purgeUsers();
        type.purgeTypes();
        assertEquals("{data=[]}", user.getAllUsers().getResponse().toString());
        user.createNewUser("user",  "pass");
        assertEquals("{data=[{password=pass, id=1, username=user}]}", user.getAllUsers().getResponse().toString());
        type.addType(1, "test");
        type.addType(1, "test1");
        type.addType(2, "test2");
        assertEquals("{types=[{name=test, userId=1}, {name=test1, userId=1}]}",type.getTypes(1).getResponse().toString());

    }

    @Test
    void getTypesByUserShouldFail() {
        user.purgeUsers();
        type.purgeTypes();
        assertEquals("{data=[]}", user.getAllUsers().getResponse().toString());
        user.createNewUser("user",  "pass");
        assertEquals("{data=[{password=pass, id=1, username=user}]}", user.getAllUsers().getResponse().toString());
        type.addType(1, "test");
        assertEquals("User doesn't exist",type.getTypes(2).getStatusMessage());

    }


    @Test
    void testDeleteTypeByUser() {
        user.purgeUsers();
        type.purgeTypes();
        assertEquals("{data=[]}", user.getAllUsers().getResponse().toString());
        user.createNewUser("user",  "pass");
        assertEquals("{data=[{password=pass, id=1, username=user}]}", user.getAllUsers().getResponse().toString());
        type.addType(1, "test");
        type.addType(1, "test1");
        type.addType(2, "test2");
        assertEquals("{types=[{name=test, userId=1}, {name=test1, userId=1}]}",type.getTypes(1).getResponse().toString());
        type.deleteType(1,"test");
        assertEquals("{types=[{name=test1, userId=1}]}",type.getTypes(1).getResponse().toString());
    }

    @Test
    void testDeleteTypeByUserShouldFailUserNotFound() {
        user.purgeUsers();
        type.purgeTypes();
        assertEquals("{data=[]}", user.getAllUsers().getResponse().toString());
        user.createNewUser("user",  "pass");
        assertEquals("{data=[{password=pass, id=1, username=user}]}", user.getAllUsers().getResponse().toString());
        type.addType(1, "test");
        assertEquals("{types=[{name=test, userId=1}]}",type.getTypes(1).getResponse().toString());
        BaseResponse response = type.deleteType(2,"test");
        assertEquals("User doesn't exist",response.getStatusMessage());
        assertEquals(404,response.getStatusCode());
    }

    @Test
    void testDeleteTypeByUserShouldFailTypeNotFound() {
        user.purgeUsers();
        type.purgeTypes();
        assertEquals("{data=[]}", user.getAllUsers().getResponse().toString());
        user.createNewUser("user",  "pass");
        assertEquals("{data=[{password=pass, id=1, username=user}]}", user.getAllUsers().getResponse().toString());
        type.addType(1, "test");
        assertEquals("{types=[{name=test, userId=1}]}",type.getTypes(1).getResponse().toString());
        BaseResponse response = type.deleteType(1,"test1");
        assertEquals("Type doesn't exist",response.getStatusMessage());
        assertEquals(404,response.getStatusCode());
    }

    @Test
    void testUpdateTypeByUser() {
        user.purgeUsers();
        type.purgeTypes();
        assertEquals("{data=[]}", user.getAllUsers().getResponse().toString());
        user.createNewUser("user",  "pass");
        assertEquals("{data=[{password=pass, id=1, username=user}]}", user.getAllUsers().getResponse().toString());
        type.addType(1, "test");
        assertEquals("{types=[{name=test, userId=1}]}",type.getTypes(1).getResponse().toString());
        BaseResponse response = type.updateType(1, "test", "test1");
        assertEquals("Type updated with success",response.getStatusMessage());
        assertEquals(200,response.getStatusCode());
    }

    @Test
    void testUpdateTypeByUserShouldFailUserNotFound() {
        user.purgeUsers();
        type.purgeTypes();
        assertEquals("{data=[]}", user.getAllUsers().getResponse().toString());
        user.createNewUser("user",  "pass");
        assertEquals("{data=[{password=pass, id=1, username=user}]}", user.getAllUsers().getResponse().toString());
        type.addType(1, "test");
        assertEquals("{types=[{name=test, userId=1}]}",type.getTypes(1).getResponse().toString());
        BaseResponse response = type.updateType(3,"test1", "test");
        assertEquals("User doesn't exist",response.getStatusMessage());
        assertEquals(404,response.getStatusCode());
    }

    @Test
    void testUpdateTypeByUserShouldFailTypeNotFound() {
        user.purgeUsers();
        type.purgeTypes();
        assertEquals("{data=[]}", user.getAllUsers().getResponse().toString());
        user.createNewUser("user",  "pass");
        assertEquals("{data=[{password=pass, id=1, username=user}]}", user.getAllUsers().getResponse().toString());
        type.addType(1, "test");
        assertEquals("{types=[{name=test, userId=1}]}",type.getTypes(1).getResponse().toString());
        BaseResponse response = type.updateType(1,"test1", "test");
        assertEquals("Type doesn't exist",response.getStatusMessage());
        assertEquals(404,response.getStatusCode());
    }

    @Test
    void testUpdateTypeByUserShouldFailTypeFound() {
        user.purgeUsers();
        type.purgeTypes();
        assertEquals("{data=[]}", user.getAllUsers().getResponse().toString());
        user.createNewUser("user",  "pass");
        assertEquals("{data=[{password=pass, id=1, username=user}]}", user.getAllUsers().getResponse().toString());
        type.addType(1, "test");
        type.addType(1, "test1");
        assertEquals("{types=[{name=test, userId=1}, {name=test1, userId=1}]}",type.getTypes(1).getResponse().toString());
        BaseResponse response = type.updateType(1,"test", "test1");
        assertEquals("Type already exist",response.getStatusMessage());
        assertEquals(200,response.getStatusCode());
    }
}