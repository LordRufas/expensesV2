package com.kaiga.expenses.services;

import com.kaiga.expenses.entity.ExcelRow;
import com.kaiga.expenses.entity.ExcelSheet;
import com.kaiga.expenses.repository.Core;
import com.kaiga.expenses.utilities.Utilities;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.*;

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

        String response =  core.add(USERS.getId(), values);
        if(response.equals("Success"))
            return Utilities.createJsonResponse("OK", "200",  null);
        else
            return Utilities.createJsonResponse("An error occurred", "400", null);

    }

    public String getAllUsers(){
        Map<String, Object> response = core.read(USERS.getId()).sheetData();
        return Utilities.createJsonResponse("OK", "200",  response);
    }

    public void purgeUsers(){
        core.purge(USERS.getId());
    }


    public String login(String username, String password) {
        String response = "User not found";
        boolean found = false;
        ExcelSheet sheet = core.read(USERS.getId());
        ExcelRow userRow = new ExcelRow();
        for(ExcelRow row : sheet.getExcelRows()){
            if(row.getData().get(1).equals(username)){
                found = true;
                if(row.getData().get(2).equals(password)){
                    response = "Success";
                    userRow = row;
                }
                else
                    response = "Password incorrect";
            }
            if(found)
                break;
        }

        if(response.equals("User not found"))
            return Utilities.createJsonResponse(response, "404", null);
        else if(response.equals("Password incorrect"))
            return Utilities.createJsonResponse(response, "401", null);
        else {

            List<Map<String, String>> dataList = new ArrayList<>();
            Map<String, String> info = new HashMap<>();
            info.put("userId", userRow.getData().get(0));
            dataList.add(info);

            Map<String, Object> root = new HashMap<>();
            root.put("response", "ok");
            root.put("data", dataList);

            return Utilities.createJsonResponse("OK", "200",root );
        }

    }
}
