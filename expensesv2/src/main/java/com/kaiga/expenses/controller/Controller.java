package com.kaiga.expenses.controller;


import com.kaiga.expenses.entity.BaseResponse;
import com.kaiga.expenses.entity.DeleteUserType;
import com.kaiga.expenses.entity.GetUserTypes;
import com.kaiga.expenses.entity.UpdateUserType;
import com.kaiga.expenses.repository.Core;
import com.kaiga.expenses.services.Type;
import com.kaiga.expenses.services.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class Controller {

    private final User user;

    private final Type type;

    @Autowired
    public Controller(Core core, Type type) {
        this.user = new User(core);
        this.type = type;
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
        return type.getTypesByUser(getUserTypes.getId());
    }

    @PatchMapping("/updateTypesByUser")
    public BaseResponse updateTypesByUser(@RequestBody UpdateUserType updateUserTypes) {
        return type.updateTypeByUser(updateUserTypes.getId(), updateUserTypes.getOldName(), updateUserTypes.getNewName());
    }

    @PatchMapping("/deleteTypesByUser")
    public BaseResponse deleteTypesByUser(@RequestBody DeleteUserType deleteUserType) {
        return type.deleteTypeByUser(deleteUserType.getId(), deleteUserType.getName());
    }




}
