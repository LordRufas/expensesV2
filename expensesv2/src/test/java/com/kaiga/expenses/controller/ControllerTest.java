package com.kaiga.expenses.controller;

import com.kaiga.expenses.entity.BaseResponse;
import com.kaiga.expenses.entity.DeleteUserType;
import com.kaiga.expenses.entity.GetUserTypes;
import com.kaiga.expenses.entity.UpdateUserType;
import com.kaiga.expenses.services.Type;
import com.kaiga.expenses.services.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(properties = "app.repositoryFileName=data/Repository_Test.xlsx")
class ControllerTest {

    @Autowired
    private Controller userController;

    @Autowired
    private User user;

    @Autowired
    private Type type;


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
        assertEquals("User not found", response.getStatusMessage());
    }


    @Test
    void addType() {
        user.purgeUsers();
        type.purgeTypes();
        BaseResponse response = userController.getAllUsers();
        assertEquals("{data=[]}", response.getResponse().toString());
        response = userController.createUser("user",  "pass");
        assertEquals(200, response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        response = userController.addType(1, "test");
        assertEquals("Type added with success", response.getStatusMessage());
        response = userController.getTypesByUser(new GetUserTypes(1));
        assertEquals(200, response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        assertEquals("{types=[test]}", response.getResponse().toString());
    }

    @Test
    void getAllTypes() {
        user.purgeUsers();
        type.purgeTypes();
        BaseResponse response = userController.getAllUsers();
        assertEquals("{data=[]}", response.getResponse().toString());
        response = userController.createUser("user",  "pass");
        assertEquals(200, response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        type.addType(1, "test");
        type.addType(1, "test1");
        response = userController.getAllTypes();
        assertEquals(200, response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        assertEquals("{data=[{name=test, userId=1}, {name=test1, userId=1}]}", response.getResponse().toString());
    }

    @Test
    void updateTypesByUser() {
        user.purgeUsers();
        type.purgeTypes();
        userController.createUser("user",  "pass");
        type.addType(1, "test");
        BaseResponse response = userController.getTypesByUser(new GetUserTypes(1));
        assertEquals(200, response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        assertEquals("{types=[test]}", response.getResponse().toString());
        response = userController.updateTypesByUser(new UpdateUserType(1, "test", "test1")) ;
        assertEquals("Type updated with success", response.getStatusMessage() );
        assertEquals(200, response.getStatusCode() );
    }

    @Test
    void deleteTypesByUser() {

        user.purgeUsers();
        type.purgeTypes();
        BaseResponse response = userController.getAllUsers();
        assertEquals("{data=[]}", response.getResponse().toString());
        response = userController.createUser("user",  "pass");
        assertEquals(200, response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        type.addType(1, "test");
        type.addType(1, "test1");
        response = userController.getTypesByUser(new GetUserTypes(1));
        assertEquals(200, response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        assertEquals("{types=[test, test1]}", response.getResponse().toString());
        response = userController.deleteTypesByUser(new DeleteUserType(1, "test") ) ;
        assertEquals("Type deleted with success", response.getStatusMessage() );
        assertEquals(200, response.getStatusCode() );

    }
}
