package com.kaiga.expenses.controller;


import com.kaiga.expenses.entity.*;
import com.kaiga.expenses.repository.Core;
import com.kaiga.expenses.services.Totals;
import com.kaiga.expenses.services.Transaction;
import com.kaiga.expenses.services.Type;
import com.kaiga.expenses.services.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class Controller {

    private final User user;

    private final Type type;

    private final Transaction transaction;

    private final Totals total;

    @Autowired
    public Controller(Core core) {
        this.user = new User(core);
        this.type = new Type(core);
        this.transaction = new Transaction(core);
        this.total = new Totals(core);
    }


    @PostMapping("/createUser")
    public BaseResponse createUser(@RequestParam String username, @RequestParam String password) {
        return user.createNewUser(username, password);
    }

    @GetMapping("/getAllUsers")
    public BaseResponse getAllUsers() {
        return user.getAllUsers();
    }

    @GetMapping("/Login")
    public BaseResponse login(@RequestParam String username, @RequestParam String password) {
        return user.login(username, password);
    }

    @PostMapping("/addType")
    public BaseResponse addType(@RequestParam int userId, @RequestParam String name) {
        return type.addType(userId, name);
    }


    @GetMapping("/getAllTypes")
    public BaseResponse getAllTypes() {
        return type.getAllTypes();
    }


    @GetMapping("/getTypesByUser")
    public BaseResponse getTypesByUser(@RequestBody GetUserTypes getUserTypes) {
        return type.getTypes(getUserTypes.getId());
    }

    @PatchMapping("/updateTypesByUser")
    public BaseResponse updateTypesByUser(@RequestBody UpdateUserType updateUserTypes) {
        return type.updateType(updateUserTypes.getId(), updateUserTypes.getOldName(), updateUserTypes.getNewName());
    }

    @PatchMapping("/deleteTypesByUser")
    public BaseResponse deleteTypesByUser(@RequestBody DeleteUserType deleteUserType) {
        return type.deleteType(deleteUserType.getId(), deleteUserType.getName());
    }


    @PostMapping("/addTotal")
    public BaseResponse addTotal(@RequestParam int userId, @RequestParam String name, @RequestParam String date, @RequestParam Double value) {
        return total.addTotal(userId, date, name, value);
    }


    @GetMapping("/getAllTotals")
    public BaseResponse getAllTotals() {
        return total.getAllTotals();
    }


    @GetMapping("/getTotalsByUser")
    public BaseResponse getTotalsByUser(@RequestBody GetUserTotals getUserTotals) {
        return total.getTotal(getUserTotals.getId());
    }

    @PatchMapping("/updateTotalsByUser")
    public BaseResponse updateTotalsByUser(@RequestBody UpdateUserTotal updateUserTotals) {
        return total.updateTotal(updateUserTotals.getId(), updateUserTotals.getOldDate(), updateUserTotals.getOldName(), updateUserTotals.getOldValue(),
                updateUserTotals.getNewDate(), updateUserTotals.getNewName(), updateUserTotals.getNewValue());
    }

    @PatchMapping("/deleteTotalsByUser")
    public BaseResponse deleteTotalsByUser(@RequestBody DeleteUserTotal deleteUserType) {
        return total.deleteTotal(deleteUserType.getId(),  deleteUserType.getDate(), deleteUserType.getName(),deleteUserType.getValue());
    }


    @PostMapping("/addTransaction")
    public BaseResponse addTransaction(@RequestParam int userId, @RequestParam String date, @RequestParam String typeName, @RequestParam double value, @RequestParam boolean isRevenue) {
        return transaction.addTransaction(userId, date, typeName, value, isRevenue);
    }


    @GetMapping("/getAllTransaction")
    public BaseResponse getAllTransaction() {
        return transaction.getAllTransactions();
    }


    @GetMapping("/getTransactionByUser")
    public BaseResponse getTransactionByUser(@RequestParam int id) {
        return transaction.getTransaction(id);
    }

    @PatchMapping("/updateTransactionByUser")
    public BaseResponse updateTransactionByUser(@RequestBody UpdateUserTransactions updateUserTransactions) {
        return transaction.updateTransaction(updateUserTransactions);
    }

    @PatchMapping("/deleteTransactionByUser")
    public BaseResponse deleteTransactionByUser(@RequestBody DeleteUserTransactions deleteUserTransactions) {
        return transaction.deleteTransaction(deleteUserTransactions.getId(), deleteUserTransactions.getDate(),deleteUserTransactions.getTypeName(), deleteUserTransactions.getValue(), deleteUserTransactions.isRevenue());
    }




}
