package com.kaiga.expenses.services;

import com.kaiga.expenses.entity.BaseResponse;
import com.kaiga.expenses.entity.ExcelRow;
import com.kaiga.expenses.entity.ExcelSheet;
import com.kaiga.expenses.repository.Core;
import org.springframework.stereotype.Component;
import static com.kaiga.expenses.entity.GeneralResponses.*;

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

    public BaseResponse createNewUser(String username, String password) {
        int id = getLatestId();

        List<Object> values = new ArrayList<>();
        values.add(id + 1);
        values.add(username);
        values.add(password);

        String response =  core.add(USERS.getId(), values);
        if(response.equals(SUCCESS))
            return new BaseResponse("OK", 200);
        else
            return new BaseResponse(ERROR_MESSAGE + response, 200);

    }

    public BaseResponse getAllUsers(){
        Map<String, Object> response = core.read(USERS.getId()).sheetData();
        return new BaseResponse("OK", 200,  response);
    }

    public void purgeUsers(){
        core.purge(USERS.getId());
    }


    public BaseResponse login(String username, String password) {
        String response = USER_NOT_FOUND;
        boolean found = false;
        ExcelSheet sheet = core.read(USERS.getId());
        ExcelRow userRow = new ExcelRow();
        for(ExcelRow row : sheet.getExcelRows()){
            if(row.getData().get(1).equals(username)){
                found = true;
                if(row.getData().get(2).equals(password)){
                    response = SUCCESS;
                    userRow = row;
                }
                else
                    response = PASSWORD_INCORRECT;
            }
            if(found)
                break;
        }

        if(response.equals(USER_NOT_FOUND))
            return new BaseResponse(response, 404, null);
        else if(response.equals(PASSWORD_INCORRECT))
            return new BaseResponse(response, 401, null);
        else {
            Map<String, Object> info = new HashMap<>();
            info.put("userId", userRow.getData().get(0));
            return new BaseResponse("OK", 200,info );
        }

    }
}
