package com.kaiga.expenses.services;

import com.kaiga.expenses.entity.*;
import com.kaiga.expenses.repository.Core;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.*;
import java.util.*;

import static com.kaiga.expenses.entity.GeneralResponses.*;
import static com.kaiga.expenses.entity.SheetEnum.*;

@Component
public class Transaction {

    private final Core core;

    public Transaction(Core core) {
        this.core = core;
    }

    private String searchData(String userId, String type) {
        List<String> elements = new ArrayList<>();
        List<String> headerNames = new ArrayList<>();

        if (userId != null) {
            headerNames.add("id");
            elements.add(userId);
            if (!core.rowExists(USERS.getId(), elements, headerNames))
                return USER_NOT_FOUND;
        }
        if (type != null) {
            elements.clear();
            headerNames.clear();

            headerNames.add("userId");
            headerNames.add("name");

            elements.add(userId);
            elements.add(type);
            if (!core.rowExists(TYPE.getId(), elements, headerNames))
                return TYPE_NOT_FOUND;
        }
        return SUCCESS;
    }

    public BaseResponse addTransaction(int userId, String date, String typeName, double value, boolean isRevenue) {
        if (searchData(String.valueOf(userId), null).equals(USER_NOT_FOUND))
            return new BaseResponse(USER_NOT_FOUND, 404);
        if (searchData(String.valueOf(userId), typeName).equals(TYPE_NOT_FOUND))
            return new BaseResponse(TYPE_NOT_FOUND, 404);


        List<Object> values = new ArrayList<>();
        values.add(userId);
        values.add(date);
        values.add(typeName);
        values.add(value);
        values.add(isRevenue);

        String response = core.add(TRANSACTION.getId(), values);

        if (response.equals(SUCCESS))
            return new BaseResponse(TRANSACTION_ADDED, 200);
        else
            return new BaseResponse(ERROR_MESSAGE + response, 400);
    }

    public BaseResponse getTransaction(int userId) {
        if (searchData(String.valueOf(userId), null).equals(USER_NOT_FOUND))
            return new BaseResponse(USER_NOT_FOUND, 404);

        Map<String, Object> response = new LinkedHashMap<>();
        List<Map<String, String>> transactions = new ArrayList<>();

        ExcelSheet sheet = core.read(TRANSACTION.getId());

        for (ExcelRow row : sheet.getExcelRows()) {
            if (row.getData().get(0).equals(String.valueOf(userId))) {
                Map<String, String> info = new HashMap<>();
                info.put("userId", row.getData().get(0));
                info.put("date", row.getData().get(1));
                info.put("typeName", row.getData().get(2));
                info.put("value", row.getData().get(3));
                info.put("isRevenue", row.getData().get(4));
                transactions.add(info);
            }
        }

        if (!transactions.isEmpty())
            response.put("transactions", transactions);

        return new BaseResponse("OK", 200, response);

    }

    public BaseResponse getAllTransactions() {
        Map<String, Object> response = core.read(TRANSACTION.getId()).sheetData();
        return new BaseResponse("OK", 200, response);
    }

    public BaseResponse updateTransaction(UpdateUserTransactions updateUserTransactions) {
        if (searchData(String.valueOf(updateUserTransactions.getId()), null).equals(USER_NOT_FOUND))
            return new BaseResponse(USER_NOT_FOUND, 404);
        if (searchData(String.valueOf(updateUserTransactions.getId()), updateUserTransactions.getNewType()).equals(TYPE_NOT_FOUND))
            return new BaseResponse(TYPE_NOT_FOUND, 404);

        List<Object> oldParams = new ArrayList<>();
        oldParams.add(String.valueOf(updateUserTransactions.getId()));
        oldParams.add(updateUserTransactions.getOldDate());
        oldParams.add(updateUserTransactions.getOldType());
        oldParams.add(updateUserTransactions.getOldValue());
        oldParams.add(updateUserTransactions.isOldIsRevenue());

        List<Object> newParams = new ArrayList<>();
        newParams.add(String.valueOf(updateUserTransactions.getId()));
        newParams.add(updateUserTransactions.getNewDate());
        newParams.add(updateUserTransactions.getNewType());
        newParams.add(updateUserTransactions.getNewValue());
        newParams.add(updateUserTransactions.isNewIsRevenue());

        String response = core.update(TRANSACTION.getId(), oldParams, newParams);

        if (response.equals(SUCCESS))
            return new BaseResponse(TRANSACTION_UPDATED, 200);
        else
            return new BaseResponse(ERROR_MESSAGE + response, 400);
    }

    public BaseResponse deleteTransaction(int userId, String date, String typeName, double value, boolean isRevenue) {
        if (searchData(String.valueOf(userId), null).equals(USER_NOT_FOUND))
            return new BaseResponse(USER_NOT_FOUND, 404);

        List<Object> params = new ArrayList<>();
        params.add(String.valueOf(userId));
        params.add(date);
        params.add(typeName);
        params.add(value);
        params.add(isRevenue);

        String response = core.delete(TRANSACTION.getId(), params);

        if (response.equals(SUCCESS))
            return new BaseResponse(TRANSACTION_DELETED, 200);
        else
            return new BaseResponse(ERROR_MESSAGE + response, 400);


    }

    public void purgeTransactions() {
        core.purge(TRANSACTION.getId());
    }


    public byte[] getFile(int userId, String date) {
        List<UserTransaction> userTransactions = getUserTransactions(userId, date) ;
        List<UserTotals> userTotals = getUserTotals(userId);

        return generateFile(userTransactions,userTotals);
    }

    private List<UserTransaction> getUserTransactions(int userId, String date){
        List<HashMap<String, String>> transactions = (List<HashMap<String, String>>) getTransaction(userId).getResponse().get("transactions");
        List<UserTransaction> userTransactions = new ArrayList<>();

        for (Map<String, String> transaction : transactions) {
            boolean belongsToUser = false;
            for (Map.Entry<String, String> entry : transaction.entrySet()) {
                if (entry.getKey().equals("userId") && Integer.parseInt(entry.getValue()) == userId) {
                    belongsToUser = true;
                    break;
                }
            }

            if (belongsToUser) {
                if (transaction.get("date").equals(date))
                    userTransactions.add(new UserTransaction(transaction.get("typeName"), Double.parseDouble(transaction.get("value"))
                            , Boolean.parseBoolean(transaction.get("isRevenue"))));
            }

        }
        return userTransactions;
    }

    private  List<UserTotals>  getUserTotals(int userId){
        List<ExcelRow> totals = core.read(TOTALS.getId()).getExcelRows();
        List<UserTotals> userTotals = new ArrayList<>();

        for (ExcelRow row : totals)
            if (row.getData().get(0).equals(String.valueOf(userId)))
                userTotals.add(new UserTotals(row.getData().get(2),Double.parseDouble(row.getData().get(3))));
        return userTotals;

    }

    private byte[] generateFile(List<UserTransaction> userTransactions, List<UserTotals> userTotals) {
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Totals");


            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("name");
            header.createCell(1).setCellValue("value");

            for(int i = 0 ; i< userTotals.size(); i++) {
                Row row = sheet.createRow(i+1);
                row.createCell(0).setCellValue(userTotals.get(i).getName());
                row.createCell(1).setCellValue(userTotals.get(i).getValue());
            }

            sheet = workbook.createSheet("transactions");


            header = sheet.createRow(0);
            header.createCell(0).setCellValue("type");
            header.createCell(1).setCellValue("value");
            header.createCell(2).setCellValue("isRevenue");

            for(int i = 0 ; i< userTransactions.size(); i++) {
                Row row = sheet.createRow(i+1);
                row.createCell(0).setCellValue(userTransactions.get(i).getType());
                row.createCell(1).setCellValue(userTransactions.get(i).getValue());
                row.createCell(2).setCellValue(userTransactions.get(i).isRevenue());
            }
            workbook.write(out);

            return out.toByteArray();

        } catch (Exception e) {
            return null;
        }

    }

}
