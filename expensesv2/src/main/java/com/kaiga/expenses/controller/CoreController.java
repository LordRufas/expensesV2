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


    @GetMapping("/read")
    public String read(@RequestParam int id){
        return teste.read(id);
    }

    @PostMapping("/insert")
    public String insert(){
        return teste.insert();
    }

    @PatchMapping("/delete")
    public String delete(){
        return teste.delete();
    }

    @PatchMapping("/purge")
    public String purge(){
        return teste.purge();
    }

    @PatchMapping("/update")
    public String update(){
        return teste.update();
    }
}
