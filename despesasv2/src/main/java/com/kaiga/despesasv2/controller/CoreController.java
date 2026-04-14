package com.kaiga.despesasv2.controller;



import org.springframework.web.bind.annotation.*;

@RestController
public class CoreController {


    @GetMapping("/helloworld")
    public String helloWorld(){
    return "hello world";
    }

}
