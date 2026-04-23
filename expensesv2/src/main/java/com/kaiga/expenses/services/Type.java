package com.kaiga.expenses.services;

import com.kaiga.expenses.entity.BaseResponse;
import com.kaiga.expenses.entity.ExcelRow;
import com.kaiga.expenses.entity.ExcelSheet;
import com.kaiga.expenses.repository.Core;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.kaiga.expenses.entity.DataBaseResponse.*;
import static com.kaiga.expenses.entity.SheetEnum.TYPE;
import static com.kaiga.expenses.entity.SheetEnum.USERS;

@Component
public class Type {

    private final Core core;
    private static final String USER_NOT_FOUND = "User doesn't exist";

    private static final String TYPE_NOT_FOUND ="Type doesn't exist";

    private static final String TYPE_ALREADY_EXISTS ="Type already exist";

    private static final String ERROR_MESSAGE = "An error has occurred ";

    public Type(Core core) {
        this.core = core;
    }

    private boolean doesUserExist(int userId){
        boolean exist = false;
        ExcelSheet sheet = core.read(USERS.getId());
        for(ExcelRow row : sheet.getExcelRows()){
            if(Integer.parseInt(row.getData().get(0)) == userId){
                exist = true;
                break;
            }
        }

        return exist;
    }

    private boolean doesTypeExist(int userId, String name){
        boolean exist = false;
        ExcelSheet sheet = core.read(TYPE.getId());
        for(ExcelRow row : sheet.getExcelRows()){
            if(Integer.parseInt(row.getData().get(0)) == userId
                    && row.getData().get(1).equals(name)){
                exist = true;
                break;
            }
        }

        return exist;
    }


    public BaseResponse addType(int userId, String name) {
        if(!doesUserExist(userId))
            return new BaseResponse(USER_NOT_FOUND, 404);
        if(doesTypeExist(userId,name))
            return new BaseResponse(TYPE_ALREADY_EXISTS, 200);
        List<Object> values = new ArrayList<>();
        values.add(userId);
        values.add(name);
        String response =  core.add(TYPE.getId(), values);
        if(response.equals(SUCCESS))
            return new BaseResponse("Type added with success", 200);
        else
            return new BaseResponse(ERROR_MESSAGE + response, 400);

    }

    public BaseResponse getAllTypes() {
        Map<String, Object> response =  core.read(TYPE.getId()).sheetData();
        return new BaseResponse("OK", 200,  response);
    }



    public BaseResponse getTypesByUser(int userId) {
        if(!doesUserExist(userId))
            return new BaseResponse(USER_NOT_FOUND, 404);

        Map<String, Object> response = new LinkedHashMap<>();
        List<String> types = new ArrayList<>();

        ExcelSheet sheet =  core.read(TYPE.getId());
        for(ExcelRow row : sheet.getExcelRows()){
            if(row.getData().get(0).equals(String.valueOf(userId)))
                types.add(row.getData().get(1));
        }
        if(!types.isEmpty())
            response.put("types", types);
        return new BaseResponse("OK", 200,  response);
    }


    public BaseResponse deleteTypeByUser(int userId, String name) {
        if(!doesUserExist(userId))
            return new BaseResponse(USER_NOT_FOUND, 404);
        if(!doesTypeExist(userId, name))
            return new BaseResponse(TYPE_NOT_FOUND, 404);
        List<Object> params = new ArrayList<>();
        params.add(String.valueOf( userId));
        params.add(name);
        String response = core.delete(TYPE.getId(), params);
        if(response.equals(SUCCESS))
            return new BaseResponse("Type deleted with success", 200);
        else
            return new BaseResponse(ERROR_MESSAGE + response, 400);


    }

    public BaseResponse updateTypeByUser(int userId, String oldName, String newName) {
        if(!doesUserExist(userId))
            return new BaseResponse(USER_NOT_FOUND, 404);
        if(!doesTypeExist(userId, oldName))
            return new BaseResponse(TYPE_NOT_FOUND, 404);
        if(doesTypeExist(userId,newName))
            return new BaseResponse(TYPE_ALREADY_EXISTS, 200);
        List<Object> oldParams = new ArrayList<>();
        oldParams.add(String.valueOf( userId));
        oldParams.add(oldName);

        List<Object> newParams = new ArrayList<>();
        newParams.add(String.valueOf( userId));
        newParams.add(newName);
        String response = core.update(TYPE.getId(), oldParams, newParams);
        if(response.equals(SUCCESS))
            return new BaseResponse("Type updated with success", 200);
        else
            return new BaseResponse(ERROR_MESSAGE + response, 400);

    }

    public void purgeTypes(){
        core.purge(TYPE.getId());
    }


}
