package com.kaiga.expenses.services;

import com.kaiga.expenses.entity.BaseResponse;
import com.kaiga.expenses.entity.ExcelRow;
import com.kaiga.expenses.entity.ExcelSheet;
import com.kaiga.expenses.repository.Core;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.kaiga.expenses.entity.GeneralResponses.*;
import static com.kaiga.expenses.entity.SheetEnum.TYPE;
import static com.kaiga.expenses.entity.SheetEnum.USERS;

@Component
public class Type {

    private final Core core;


    public Type(Core core) {
        this.core = core;
    }



    public BaseResponse addType(int userId, String name) {

        String preChecks = searchData(String.valueOf(userId), name);

        if(preChecks.equals(USER_NOT_FOUND))
            return new BaseResponse(USER_NOT_FOUND, 404);
        if(preChecks.equals(TYPE_ALREADY_EXISTS))
            return new BaseResponse(TYPE_ALREADY_EXISTS, 200);

        List<Object> values = new ArrayList<>();
        values.add(userId);
        values.add(name);

        String response =  core.add(TYPE.getId(), values);

        if(response.equals(SUCCESS))
            return new BaseResponse(TYPE_ADDED, 200);
        else
            return new BaseResponse(ERROR_MESSAGE + response, 400);

    }

    public BaseResponse getAllTypes() {
        Map<String, Object> response =  core.read(TYPE.getId()).sheetData();
        return new BaseResponse("OK", 200,  response);
    }



    public BaseResponse getTypes(int userId) {
        if(searchData(String.valueOf(userId), null).equals(USER_NOT_FOUND))
            return new BaseResponse(USER_NOT_FOUND, 404);

        Map<String, Object> response = new LinkedHashMap<>();
        List<Map<String, String>> types = new ArrayList<>();

        ExcelSheet sheet =  core.read(TYPE.getId());

        for(ExcelRow row : sheet.getExcelRows()){
            if(row.getData().get(0).equals(String.valueOf(userId))) {
                Map<String, String> info = new HashMap<>();
                info.put("userId", row.getData().get(0));
                info.put("name", row.getData().get(1));
                types.add(info);
            }
        }

        if(!types.isEmpty())
            response.put("types", types);

        return new BaseResponse("OK", 200,  response);
    }


    public BaseResponse deleteType(int userId, String name) {
        if(searchData(String.valueOf(userId), null).equals(USER_NOT_FOUND))
            return new BaseResponse(USER_NOT_FOUND, 404);
        if(searchData(String.valueOf(userId), name).equals(TYPE_NOT_FOUND))
            return new BaseResponse(TYPE_NOT_FOUND, 404);

        List<Object> params = new ArrayList<>();
        params.add(String.valueOf( userId));
        params.add(name);

        String response = core.delete(TYPE.getId(), params);

        if(response.equals(SUCCESS))
            return new BaseResponse(TYPE_DELETED, 200);
        else
            return new BaseResponse(ERROR_MESSAGE + response, 400);


    }

    public BaseResponse updateType(int userId, String oldName, String newName) {

        if(searchData(String.valueOf(userId), null).equals(USER_NOT_FOUND))
            return new BaseResponse(USER_NOT_FOUND, 404);
        if(searchData(String.valueOf(userId), oldName).equals(TYPE_NOT_FOUND))
            return new BaseResponse(TYPE_NOT_FOUND, 404);
        if(searchData(String.valueOf(userId), newName).equals(TYPE_ALREADY_EXISTS))
            return new BaseResponse(TYPE_ALREADY_EXISTS, 200);

        List<Object> oldParams = new ArrayList<>();
        oldParams.add(String.valueOf( userId));
        oldParams.add(oldName);

        List<Object> newParams = new ArrayList<>();
        newParams.add(String.valueOf( userId));
        newParams.add(newName);

        String response = core.update(TYPE.getId(), oldParams, newParams);

        if(response.equals(SUCCESS))
            return new BaseResponse(TYPE_UPDATED, 200);
        else
            return new BaseResponse(ERROR_MESSAGE + response, 400);

    }

    public void purgeTypes(){
        core.purge(TYPE.getId());
    }

    private String searchData(String userId, String type){
        List<String> elements = new ArrayList<>();
        List<String> headerNames = new ArrayList<>();

        if(userId != null) {
            headerNames.add("id");
            elements.add(userId);
            if (!core.rowExists(USERS.getId(), elements, headerNames))
                return USER_NOT_FOUND;
        }
        if(type != null) {
            headerNames.clear();
            elements.clear();

            headerNames.add("userId");
            headerNames.add("name");

            elements.add(userId);
            elements.add(type);

            if (core.rowExists(TYPE.getId(), elements, headerNames))
                return TYPE_ALREADY_EXISTS;
            else
                return TYPE_NOT_FOUND;
        }
        return SUCCESS;
    }

}
