package com.kaiga.expenses.services;

import com.kaiga.expenses.entity.BaseResponse;
import com.kaiga.expenses.entity.UpdateUserTransactions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "app.repositoryFileName=data/Repository_Test.xlsx")
class TransactionTest {

    @Autowired
    private Type type;

    @Autowired
    private User user;

    @Autowired
    private Transaction transaction;

    @Test
    void addTransaction() {
        user.purgeUsers();
        type.purgeTypes();
        transaction.purgeTransactions();
        user.createNewUser("user",  "pass");
        type.addType(1, "test");
        BaseResponse response = transaction.addTransaction(1,"01/01/1990", "test", 1.0, false);
        assertEquals("Transaction added with success", response.getStatusMessage());
    }

    @Test
    void addTransactionUserNotFound() {
        user.purgeUsers();
        type.purgeTypes();
        transaction.purgeTransactions();
        user.createNewUser("user",  "pass");
        type.addType(1, "test");
        BaseResponse response = transaction.addTransaction(2,"01/01/1990", "test", 1.0, false);
        assertEquals("User doesn't exist", response.getStatusMessage());
    }

    @Test
    void addTransactionTypeNotFound() {
        user.purgeUsers();
        type.purgeTypes();
        transaction.purgeTransactions();
        user.createNewUser("user",  "pass");
        type.addType(1, "test");
        BaseResponse response = transaction.addTransaction(1,"01/01/1990", "test1", 1.0, false);
        assertEquals("Type doesn't exist", response.getStatusMessage());
    }

    @Test
    void getTransaction() {
        user.purgeUsers();
        type.purgeTypes();
        transaction.purgeTransactions();
        user.createNewUser("user",  "pass");
        type.addType(1, "test");
        transaction.addTransaction(1,"01/01/1990", "test", 1.0, false);
        BaseResponse response = transaction.getTransaction(1);
        assertEquals("{transactions=[{date=01/01/1990, typeName=test, userId=1, value=1.0, isRevenue=false}]}", response.getResponse().toString());
    }

    @Test
    void getTransactionUserNotFound() {
        user.purgeUsers();
        type.purgeTypes();
        transaction.purgeTransactions();
        user.createNewUser("user",  "pass");
        type.addType(1, "test");
        transaction.addTransaction(1,"01/01/1990", "test", 1.0, false);
        BaseResponse response = transaction.getTransaction(2);
        assertEquals("User doesn't exist", response.getStatusMessage());
    }

    @Test
    void updateTransaction() {
        user.purgeUsers();
        type.purgeTypes();
        transaction.purgeTransactions();
        user.createNewUser("user",  "pass");
        type.addType(1, "test");
        transaction.addTransaction(1,"01/01/1990", "test", 1.0, false);
        BaseResponse response = transaction.updateTransaction(new UpdateUserTransactions(1,"01/01/1990", "test", 1.0, false,"01/01/1990", "test", 1.0, true));
        assertEquals("Transaction updated with success", response.getStatusMessage());
        response = transaction.getAllTransactions();
        assertEquals("{data=[{date=01/01/1990, typeName=test, userId=1, value=1.0, isRevenue=true}]}", response.getResponse().toString());

    }

    @Test
    void updateTransactionUserNotFound() {
        user.purgeUsers();
        type.purgeTypes();
        transaction.purgeTransactions();
        user.createNewUser("user",  "pass");
        type.addType(1, "test");
        transaction.addTransaction(1,"01/01/1990", "test", 1.0, false);
        BaseResponse response = transaction.updateTransaction(new UpdateUserTransactions(2,"01/01/1990", "test", 1.0, false,"01/01/1990", "test", 1.0, true));
        assertEquals("User doesn't exist", response.getStatusMessage());

    }

    @Test
    void deleteTransaction() {
        user.purgeUsers();
        type.purgeTypes();
        transaction.purgeTransactions();
        user.createNewUser("user",  "pass");
        type.addType(1, "test");
        transaction.addTransaction(1,"01/01/1990", "test", 1.0, false);
        transaction.deleteTransaction(1,"01/01/1990", "test", 1.0, false);
        BaseResponse response = transaction.getTransaction(1);
        assertEquals("{}", response.getResponse().toString());
    }

    @Test
    void deleteTransactionUserNotFound() {
        user.purgeUsers();
        type.purgeTypes();
        transaction.purgeTransactions();
        user.createNewUser("user",  "pass");
        type.addType(1, "test");
        transaction.addTransaction(1,"01/01/1990", "test", 1.0, false);
        BaseResponse response =transaction.deleteTransaction(2,"01/01/1990", "test", 1.0, false);
        assertEquals("User doesn't exist", response.getStatusMessage());
    }

}