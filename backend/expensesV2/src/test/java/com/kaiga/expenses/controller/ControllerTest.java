package com.kaiga.expenses.controller;

import com.kaiga.expenses.entity.*;
import com.kaiga.expenses.services.Totals;
import com.kaiga.expenses.services.Transaction;
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

    @Autowired
    private Transaction transaction;

    @Autowired
    private Totals totals;


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
        assertEquals("User doesn't exist", response.getStatusMessage());
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
        assertEquals("{types=[{name=test, userId=1}]}", response.getResponse().toString());
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
        assertEquals("{types=[{name=test, userId=1}]}", response.getResponse().toString());
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
        assertEquals("{types=[{name=test, userId=1}, {name=test1, userId=1}]}", response.getResponse().toString());
        response = userController.deleteTypesByUser(new DeleteUserType(1, "test") ) ;
        assertEquals("Type deleted with success", response.getStatusMessage() );
        assertEquals(200, response.getStatusCode() );

    }

    @Test
    void addTotal() {
        user.purgeUsers();
        totals.purgeTotals();
        userController.createUser("user",  "pass");
        BaseResponse response = userController.addTotal(1, "test","01/01/1990",1.0);
        assertEquals("Total added with success", response.getStatusMessage());
        response = userController.getTotalsByUser(new GetUserTotals(1));
        assertEquals(200, response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        assertEquals("{totals=[{date=01/01/1990, name=test, userId=1, value=1.0}]}", response.getResponse().toString());
    }

    @Test
    void getAllTotals() {
        user.purgeUsers();
        totals.purgeTotals();
        userController.createUser("user",  "pass");
        BaseResponse response = userController.addTotal(1, "test","01/01/1990",1.0);
        assertEquals("Total added with success", response.getStatusMessage());
        response = userController.getAllTotals();
        assertEquals(200, response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        assertEquals("{data=[{date=01/01/1990, name=test, userId=1, value=1.0}]}", response.getResponse().toString());
    }


    @Test
    void updateTotalsByUser() {
        user.purgeUsers();
        totals.purgeTotals();
        userController.createUser("user",  "pass");
        BaseResponse response = userController.addTotal(1, "test","01/01/1990",1.0);
        assertEquals("Total added with success", response.getStatusMessage());
        response = userController.getTotalsByUser(new GetUserTotals(1));
        assertEquals(200, response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        assertEquals("{totals=[{date=01/01/1990, name=test, userId=1, value=1.0}]}", response.getResponse().toString());
        response = userController.updateTotalsByUser(new UpdateUserTotal(1,"01/01/1990","test", 1.0,"01/01/1990","test", 2.0 ));
        assertEquals("Total updated with success", response.getStatusMessage());
        response = userController.getTotalsByUser(new GetUserTotals(1));
        assertEquals(200, response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        assertEquals("{totals=[{date=01/01/1990, name=test, userId=1, value=2.0}]}", response.getResponse().toString());

    }

    @Test
    void deleteTotalsByUser() {
        user.purgeUsers();
        totals.purgeTotals();
        userController.createUser("user",  "pass");
        BaseResponse response = userController.addTotal(1, "test","01/01/1990",1.0);
        assertEquals("Total added with success", response.getStatusMessage());
        response = userController.getAllTotals();
        assertEquals(200, response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        assertEquals("{data=[{date=01/01/1990, name=test, userId=1, value=1.0}]}", response.getResponse().toString());
        response = userController.deleteTotalsByUser(new DeleteUserTotal(1, "test","01/01/1990",1.0));
        assertEquals(200, response.getStatusCode());
        assertEquals("Total deleted with success", response.getStatusMessage());
        response = userController.getAllTotals();
        assertEquals(200, response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        assertEquals("{data=[]}", response.getResponse().toString());

    }

    @Test
    void addTransaction() {
        user.purgeUsers();
        type.purgeTypes();
        transaction.purgeTransactions();
        userController.createUser("user",  "pass");
        userController.addType(1, "test");
        BaseResponse response = userController.addTransaction(1,"01/01/1990", "test", 1.0, false );
        assertEquals(200, response.getStatusCode());
        assertEquals("Transaction added with success", response.getStatusMessage());
        response = userController.getAllTransaction();
        assertEquals(200, response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        assertEquals("{data=[{date=01/01/1990, typeName=test, userId=1, value=1.0, isRevenue=false}]}", response.getResponse().toString());



    }

    @Test
    void getTransactionByUser() {
        user.purgeUsers();
        type.purgeTypes();
        transaction.purgeTransactions();
        userController.createUser("user",  "pass");
        userController.addType(1, "test");
        BaseResponse response = userController.addTransaction(1,"01/01/1990", "test", 1.0, false );
        assertEquals(200, response.getStatusCode());
        assertEquals("Transaction added with success", response.getStatusMessage());
        response = userController.getTransactionByUser(1);
        assertEquals(200, response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        assertEquals("{transactions=[{date=01/01/1990, typeName=test, userId=1, value=1.0, isRevenue=false}]}", response.getResponse().toString());

    }

    @Test
    void updateTransactionByUser() {
        user.purgeUsers();
        type.purgeTypes();
        transaction.purgeTransactions();
        userController.createUser("user",  "pass");
        userController.addType(1, "test");
        BaseResponse response = userController.addTransaction(1,"01/01/1990", "test", 1.0, false );
        assertEquals(200, response.getStatusCode());
        assertEquals("Transaction added with success", response.getStatusMessage());
        response = userController.getAllTransaction();
        assertEquals(200, response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        assertEquals("{data=[{date=01/01/1990, typeName=test, userId=1, value=1.0, isRevenue=false}]}", response.getResponse().toString());
        userController.updateTransactionByUser(new UpdateUserTransactions(1,"01/01/1990", "test", 1.0, false,"01/01/1990", "test", 1.0, true));
        response = userController.getAllTransaction();
        assertEquals(200, response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        assertEquals("{data=[{date=01/01/1990, typeName=test, userId=1, value=1.0, isRevenue=true}]}", response.getResponse().toString());

    }

    @Test
    void deleteTransactionByUser() {
        user.purgeUsers();
        type.purgeTypes();
        transaction.purgeTransactions();
        userController.createUser("user",  "pass");
        userController.addType(1, "test");
        BaseResponse response = userController.addTransaction(1,"01/01/1990", "test", 1.0, false );
        assertEquals(200, response.getStatusCode());
        assertEquals("Transaction added with success", response.getStatusMessage());
        response = userController.getAllTransaction();
        assertEquals(200, response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        assertEquals("{data=[{date=01/01/1990, typeName=test, userId=1, value=1.0, isRevenue=false}]}", response.getResponse().toString());
        userController.deleteTransactionByUser(new DeleteUserTransactions(1,"01/01/1990", "test", 1.0, false));
        response = userController.getAllTransaction();
        assertEquals(200, response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        assertEquals("{data=[]}", response.getResponse().toString());

    }
}
