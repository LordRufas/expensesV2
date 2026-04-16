package com.kaiga.expenses.services;

import com.kaiga.expenses.entity.ExcelRow;
import com.kaiga.expenses.entity.ExcelSheet;
import com.kaiga.expenses.repository.Core;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.kaiga.expenses.entity.SheetEnum.*;

@Component
public class User {


    private final Core core;

    public User(Core core) {
        this.core = core;
    }

    private int getLatestId() {
        ExcelSheet sheet = core.read(USERS.getId());
        if(sheet.getExcelRows().isEmpty())
            return 0;
        int id = 0 ;
        for(ExcelRow row : sheet.getExcelRows()){
            if( id < Integer.parseInt(row.getData().get(0)) )
                id = Integer.parseInt(row.getData().get(0));
        }
        return id;
    }

    public String createNewUser(String username, String password) {
        int id = getLatestId();

        List<Object> values = new ArrayList<>();
        values.add(id + 1);
        values.add(username);
        values.add(password);

        return core.add(USERS.getId(), values);
    }

    public String getAllUsers(){
        return core.read(USERS.getId()).sheetData();
    }

    public void purgeUsers(){
        core.purge(USERS.getId());
    }



}
