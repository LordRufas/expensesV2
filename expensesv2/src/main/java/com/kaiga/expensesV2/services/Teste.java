package com.kaiga.expensesV2.services;

import com.kaiga.expensesV2.entity.ExcelSheet;
import com.kaiga.expensesV2.entity.SheetEnum;
import com.kaiga.expensesV2.repository.Core;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Teste {

    @Autowired
    private Core core;


    private SheetEnum sheetEnum;

    public Teste(Core core) {
        this.core = core;
    }

    public String Read(){
        ExcelSheet sheet = core.ReadFromFile(sheetEnum.TRANSACTION.getId());

        return sheet.SheetData();

    }

    public void Insert(){


    }

    public void Delete(){


    }

    public void Update(){


    }
}
