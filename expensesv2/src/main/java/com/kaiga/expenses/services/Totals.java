package com.kaiga.expenses.services;

import com.kaiga.expenses.entity.BaseResponse;
import com.kaiga.expenses.entity.ExcelRow;
import com.kaiga.expenses.entity.ExcelSheet;
import com.kaiga.expenses.repository.Core;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.kaiga.expenses.entity.GeneralResponses.*;
import static com.kaiga.expenses.entity.SheetEnum.*;

@Component
public class Totals {

    private final Core core;

    public Totals(Core core){
        this.core = core;
    }

    public BaseResponse addTotal(int userId, String date, String name, double value){
        String preChecks = searchData(String.valueOf(userId), name, date,0.0);

        if(preChecks.equals(USER_NOT_FOUND))
            return new BaseResponse(USER_NOT_FOUND, 404);
        if(preChecks.equals(TOTAL_ALREADY_EXISTS))
            return new BaseResponse(TOTAL_ALREADY_EXISTS, 200);

        List<Object> values = new ArrayList<>();
        values.add(userId);
        values.add(date);
        values.add(name);
        values.add(value);

        String response =  core.add(TOTALS.getId(), values);

        if(response.equals(SUCCESS))
            return new BaseResponse(TOTAL_ADDED, 200);
        else
            return new BaseResponse(ERROR_MESSAGE + response, 400);


    }

    public BaseResponse getTotal(int userId){
        if(searchData(String.valueOf(userId), null, null,0.0).equals(USER_NOT_FOUND))
            return new BaseResponse(USER_NOT_FOUND, 404);

        Map<String, Object> response = new LinkedHashMap<>();

        List<Map<String, String>> totals = new ArrayList<>();

        ExcelSheet sheet =  core.read(TOTALS.getId());

        for(ExcelRow row : sheet.getExcelRows()){
            if(row.getData().get(0).equals(String.valueOf(userId))){
                Map<String, String> info = new HashMap<>();
                info.put("userId", row.getData().get(0));
                info.put("date",row.getData().get(1));
                info.put("name",row.getData().get(2));
                info.put("value",row.getData().get(3));
                totals.add(info);
            }

        }

        if(!totals.isEmpty())
            response.put("totals", totals);

        return new BaseResponse("OK", 200,  response);

    }

    public BaseResponse getAllTotals() {
        Map<String, Object> response =  core.read(TOTALS.getId()).sheetData();
        return new BaseResponse("OK", 200,  response);
    }

    public BaseResponse updateTotal(int userId, String oldDate, String oldName, double oldValue,
                                    String newDate, String newName, double newValue){
        if(searchData(String.valueOf(userId), null, null, 0.0).equals(USER_NOT_FOUND))
            return new BaseResponse(USER_NOT_FOUND, 404);
        if(searchData(String.valueOf(userId), oldName, oldDate, oldValue).equals(TOTAL_NOT_FOUND))
            return new BaseResponse(TOTAL_NOT_FOUND, 200);
        if(searchData(String.valueOf(userId), newName, newDate, newValue).equals(TOTAL_ALREADY_EXISTS))
            return new BaseResponse(TOTAL_ALREADY_EXISTS, 200);

        List<Object> oldParams = new ArrayList<>();
        oldParams.add(String.valueOf( userId));
        oldParams.add(oldDate);
        oldParams.add(oldName);
        oldParams.add(oldValue);

        List<Object> newParams = new ArrayList<>();
        newParams.add(String.valueOf( userId));
        newParams.add(newDate);
        newParams.add(newName);
        newParams.add(newValue);

        String response = core.update(TOTALS.getId(), oldParams, newParams);

        if(response.equals(SUCCESS))
            return new BaseResponse(TOTAL_UPDATED, 200);
        else
            return new BaseResponse(ERROR_MESSAGE + response, 400);
    }

    public BaseResponse deleteTotal(int userId,  String date,String name, double value){
        if(searchData(String.valueOf(userId), null, null,0.0).equals(USER_NOT_FOUND))
            return new BaseResponse(USER_NOT_FOUND, 404);
        if(searchData(String.valueOf(userId), name, date,0.0).equals(TOTAL_NOT_FOUND))
            return new BaseResponse(TOTAL_NOT_FOUND, 404);

        List<Object> params = new ArrayList<>();
        params.add(String.valueOf( userId));
        params.add(date);
        params.add(name);
        params.add(String.valueOf( value));

        String response = core.delete(TOTALS.getId(), params);

        if(response.equals(SUCCESS))
            return new BaseResponse(TOTAL_DELETED, 200);
        else
            return new BaseResponse(ERROR_MESSAGE + response, 400);

    }

    public void purgeTotals(){
        core.purge(TOTALS.getId());
    }


    private String searchData(String userId, String total, String date, double value){
        List<String> elements = new ArrayList<>();
        List<String> headerNames = new ArrayList<>();

        if(userId != null) {
            headerNames.add("id");
            elements.add(userId);
            if (!core.rowExists(USERS.getId(), elements,headerNames))
                return USER_NOT_FOUND;
        }
        if(total != null && date != null && value > 0.0) {
            headerNames.clear();
            elements.clear();

            headerNames.add("userId");
            headerNames.add("date");
            headerNames.add("name");
            headerNames.add("value");
            elements.add(userId);
            elements.add(date);
            elements.add(total);
            elements.add(String.valueOf(value));
            if (core.rowExists(TOTALS.getId(), elements,headerNames))
                return TOTAL_ALREADY_EXISTS;
            else
                return TOTAL_NOT_FOUND;
        }else  if(total != null && date != null) {
            headerNames.clear();
            elements.clear();

            headerNames.add("userId");
            headerNames.add("date");
            headerNames.add("name");
            elements.add(userId);
            elements.add(date);
            elements.add(total);
            if (core.rowExists(TOTALS.getId(), elements,headerNames))
                return TOTAL_ALREADY_EXISTS;
            else
                return TOTAL_NOT_FOUND;
        }

        return SUCCESS;
    }
}
