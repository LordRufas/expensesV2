package com.kaiga.expenses.controller;



import com.kaiga.expenses.repository.Core;
import com.kaiga.expenses.services.Teste;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CoreController {

    private final Teste teste;

    @Autowired
    public CoreController(Core core) {
        this.teste = new Teste(core);
    }


    @GetMapping("/helloworld")
    public String helloWorld(){
        return teste.read();
    }

}
