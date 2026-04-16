package com.kaiga.expenses.services;

import com.kaiga.expenses.entity.ExcelSheet;
import com.kaiga.expenses.repository.Core;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.kaiga.expenses.entity.SheetEnum.*;

@Component
public class Teste {


    private final Core core;

    public Teste(Core core) {
        this.core = core;
    }

    public String read(int id) {
        ExcelSheet sheet = core.readFromFile(id);
        return sheet.sheetData();
    }

    public String insert() {
        List<Object> values = new ArrayList<>();
        values.add(1);
        values.add("test");
        values.add("Pass");

        return core.addToSheet(USERS.getId(), values);

    }

    public String delete() {
        List<Object> values = new ArrayList<>();
        values.add(1.0);
        values.add("teste");
        values.add("password");
        return core.deleteRow(USERS.getId(), values);
    }

    public String purge() {
        String response = "Success";
        String deleteUsers = core.purge(USERS.getId());
        String deleteTransaction = core.purge(TRANSACTION.getId());
        String deleteType = core.purge(TYPE.getId());
        String deleteTotals = core.purge(TOTALS.getId());
        if (deleteUsers.equals(response)
                && deleteTransaction.equals(response)
                && deleteType.equals(response)
                && deleteTotals.equals(response))
            return "Done";
        else
            return "Error";
    }


    public String update() {
        List<Object> oldValues = new ArrayList<>();
        oldValues.add(1);
        oldValues.add("test");
        oldValues.add("Pass");
        List<Object> newValues = new ArrayList<>();
        newValues.add(1);
        newValues.add("test");
        newValues.add("password");
        return core.update(USERS.getId(),oldValues, newValues);

    }


}
