package com.kaiga.expenses.services;

import com.kaiga.expenses.entity.BaseResponse;
import com.kaiga.expenses.entity.ExcelRow;
import com.kaiga.expenses.entity.ExcelSheet;
import com.kaiga.expenses.entity.UpdateUserTransactions;
import com.kaiga.expenses.repository.Core;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.kaiga.expenses.entity.GeneralResponses.*;
import static com.kaiga.expenses.entity.SheetEnum.*;

@Component
public class Transaction {

    private final Core core;
    public Transaction(Core core){
        this.core = core;
    }

    private String searchData(String userId, String type){
        List<String> elements = new ArrayList<>();
        List<String> headerNames = new ArrayList<>();

        if(userId != null) {
            headerNames.add("id");
            elements.add(userId);
            if (!core.rowExists(USERS.getId(), elements,headerNames))
                return USER_NOT_FOUND;
        }
        if(type != null) {
            elements.clear();
            headerNames.clear();

            headerNames.add("userId");
            headerNames.add("name");

            elements.add(userId);
            elements.add(type);
            if (!core.rowExists(TYPE.getId(), elements,headerNames))
                return TYPE_NOT_FOUND;
        }
        return SUCCESS;
    }
    public BaseResponse addTransaction(int userId, String date, String typeName, double value, boolean isRevenue){
        if(searchData(String.valueOf(userId), null).equals(USER_NOT_FOUND))
            return new BaseResponse(USER_NOT_FOUND, 404);
        if(searchData(String.valueOf(userId), typeName).equals(TYPE_NOT_FOUND))
            return new BaseResponse(TYPE_NOT_FOUND, 404);


        List<Object> values = new ArrayList<>();
        values.add(userId);
        values.add(date);
        values.add(typeName);
        values.add(value);
        values.add(isRevenue);

        String response = core.add(TRANSACTION.getId(), values);

        if(response.equals(SUCCESS))
            return new BaseResponse(TRANSACTION_ADDED, 200);
        else
            return new BaseResponse(ERROR_MESSAGE + response, 400);
    }

    public BaseResponse getTransaction(int userId){
        if(searchData(String.valueOf(userId), null).equals(USER_NOT_FOUND))
            return new BaseResponse(USER_NOT_FOUND, 404);

        Map<String, Object> response = new LinkedHashMap<>();
        List<Map<String, String>> transactions = new ArrayList<>();

        ExcelSheet sheet =  core.read(TRANSACTION.getId());

        for(ExcelRow row : sheet.getExcelRows()){
            if(row.getData().get(0).equals(String.valueOf(userId))) {
                Map<String, String> info = new HashMap<>();
                info.put("userId",row.getData().get(0));
                info.put("date",row.getData().get(1));
                info.put("typeName",row.getData().get(2));
                info.put("value",row.getData().get(3));
                info.put("isRevenue",row.getData().get(4));
                transactions.add(info);
            }
        }

        if(!transactions.isEmpty())
            response.put("transactions", transactions);

        return new BaseResponse("OK", 200,  response);

    }

    public BaseResponse getAllTransactions() {
        Map<String, Object> response =  core.read(TRANSACTION.getId()).sheetData();
        return new BaseResponse("OK", 200,  response);
    }
    public BaseResponse updateTransaction(UpdateUserTransactions updateUserTransactions){
        if(searchData(String.valueOf(updateUserTransactions.getId()), null).equals(USER_NOT_FOUND))
            return new BaseResponse(USER_NOT_FOUND, 404);
        if(searchData(String.valueOf(updateUserTransactions.getId()), updateUserTransactions.getNewType()).equals(TYPE_NOT_FOUND))
            return new BaseResponse(TYPE_NOT_FOUND, 404);

        List<Object> oldParams = new ArrayList<>();
        oldParams.add(String.valueOf( updateUserTransactions.getId()));
        oldParams.add(updateUserTransactions.getOldDate());
        oldParams.add(updateUserTransactions.getOldType());
        oldParams.add(updateUserTransactions.getOldValue());
        oldParams.add(updateUserTransactions.isOldIsRevenue());

        List<Object> newParams = new ArrayList<>();
        newParams.add(String.valueOf( updateUserTransactions.getId()));
        newParams.add(updateUserTransactions.getNewDate());
        newParams.add(updateUserTransactions.getNewType());
        newParams.add(updateUserTransactions.getNewValue());
        newParams.add(updateUserTransactions.isNewIsRevenue());

        String response = core.update(TRANSACTION.getId(), oldParams, newParams);

        if(response.equals(SUCCESS))
            return new BaseResponse(TRANSACTION_UPDATED, 200);
        else
            return new BaseResponse(ERROR_MESSAGE + response, 400);
    }

    public BaseResponse deleteTransaction(int userId, String date, String typeName, double value, boolean isRevenue){
        if(searchData(String.valueOf(userId), null).equals(USER_NOT_FOUND))
            return new BaseResponse(USER_NOT_FOUND, 404);

        List<Object> params = new ArrayList<>();
        params.add(String.valueOf( userId));
        params.add(date);
        params.add(typeName);
        params.add(value);
        params.add(isRevenue);

        String response = core.delete(TRANSACTION.getId(), params);

        if(response.equals(SUCCESS))
            return new BaseResponse(TRANSACTION_DELETED, 200);
        else
            return new BaseResponse(ERROR_MESSAGE + response, 400);


    }

    public void purgeTransactions(){
        core.purge(TRANSACTION.getId());
    }


}
