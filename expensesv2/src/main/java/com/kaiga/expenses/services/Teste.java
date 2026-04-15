package com.kaiga.expenses.services;

import com.kaiga.expenses.entity.ExcelSheet;
import com.kaiga.expenses.repository.Core;
import org.springframework.stereotype.Component;

import static com.kaiga.expenses.entity.SheetEnum.*;

@Component
public class Teste {


    private final Core core;

    public Teste(Core core) {
        this.core = core;
    }

    public String read(){
        ExcelSheet sheet = core.readFromFile(TRANSACTION.getId());

        return sheet.sheetData();

    }
 /*
    public void Insert(){


    }

    public void Delete(){


    }


    public void Update(){


    }
    */

}
