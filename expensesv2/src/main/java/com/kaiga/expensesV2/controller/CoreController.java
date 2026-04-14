package com.kaiga.expensesV2.controller;



import com.kaiga.expensesV2.repository.Core;
import com.kaiga.expensesV2.services.Teste;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CoreController {

    @Autowired
    private final Teste teste;

    @Autowired
    public CoreController(Core core) {
        this.teste = new Teste(core);
    }


    @GetMapping("/helloworld")
    public String helloWorld(){
        return teste.Read();
    }

}
