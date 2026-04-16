package com.kaiga.expenses.controller;


import com.kaiga.expenses.repository.Core;
import com.kaiga.expenses.services.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    private final User user;

    @Autowired
    public UserController(Core core) {
        this.user = new User(core);
    }


    @PostMapping("/createUser")
    public String createUser(@RequestParam String username, @RequestParam String password) {
        return user.createNewUser(username, password);
    }

    @GetMapping("/getAllUsers")
    public String getAllUsers() {
        return user.getAllUsers();
    }



}
