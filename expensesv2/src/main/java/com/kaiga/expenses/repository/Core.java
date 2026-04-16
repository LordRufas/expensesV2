package com.kaiga.expenses.repository;

import com.kaiga.expenses.entity.ExcelRow;
import com.kaiga.expenses.entity.ExcelSheet;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Component
public class Core {

    private static final String SUCCESS = "Success";

    private static final String NOT_FOUND = "Data not found";
    private static final String ERROR_RESPONSE = "An error has occurred: ";


    @Value("${app.repositoryFileName}")
    private String fileName;

    public ExcelSheet read(int sheetNumber){

        ExcelSheet excelSheet = new ExcelSheet();
        File file = new File(fileName);
        try (
                FileInputStream fis = new FileInputStream(file);
                Workbook workbook = new XSSFWorkbook(fis)
        ) {
            Sheet sheet = workbook.getSheetAt(sheetNumber);

            for (Row row : sheet) {
                ExcelRow excelRow = getExcelRow(row);
                excelSheet.addRow(excelRow);
            }

            excelSheet.setHeaders();

            return excelSheet;
        } catch (Exception e) {
           return null;
        }
    }

    public String add(int sheetNumber, List<Object> values) {
        File file = new File(fileName);
        try (
                FileInputStream fis = new FileInputStream(file);
                Workbook workbook = new XSSFWorkbook(fis)
        ) {
            Sheet sheet = workbook.getSheetAt(sheetNumber);

            // Find the last row index (0-based)
            int lastRowNum = sheet.getLastRowNum();

            // Create a new row after the last one
            Row newRow = sheet.createRow(lastRowNum + 1);

            // Add data to the new row
            for (int i = 0; i < values.size(); i++) {
                newRow.createCell(i).setCellValue(String.valueOf(values.get(i)));
            }

            OutputStream outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);

            return SUCCESS;

        } catch (Exception e) {
            return ERROR_RESPONSE + e.getMessage();
        }
    }

    public String update(int sheetIndex, List<Object> oldValues, List<Object> newValues) {
        String response = NOT_FOUND;
        File file = new File(fileName);
        try (
                FileInputStream fis = new FileInputStream(file);
                Workbook workbook = new XSSFWorkbook(fis)
        ) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);

            for (int i = sheet.getLastRowNum(); i >= 0; i--) {
                Row row = sheet.getRow(i);
                if (row != null && updateRowIfExists(row, oldValues, newValues))
                    response = SUCCESS;

            }

            // Write changes back to file
            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
            }
            return response;

        } catch (Exception e) {
            return ERROR_RESPONSE + e.getMessage();
        }
    }



    public String delete(int sheetIndex, List<Object> parameters) {
        String response = NOT_FOUND;
        File file = new File(fileName);
        try (
                FileInputStream fis = new FileInputStream(file);
                Workbook workbook = new XSSFWorkbook(fis)
        ) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);

            for (int i = sheet.getLastRowNum(); i >= 0; i--) {
                Row row = sheet.getRow(i);
                if (row != null && deleteRowIfExists(sheet, row, parameters))
                    response = SUCCESS;

            }

            // Write changes back to file
            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
            }
            return response;

        } catch (Exception e) {
            return ERROR_RESPONSE + e.getMessage();
        }
    }

    public String purge(int sheetIndex){
        File file = new File(fileName);
        try (
                FileInputStream fis = new FileInputStream(file);
                Workbook workbook = new XSSFWorkbook(fis)
        ) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);


            for (int i = sheet.getLastRowNum(); i > 0; i--)
                deleteRowAndShift(sheet, sheet.getRow(i));


            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
            }
            return SUCCESS;

        } catch (Exception e) {
            return ERROR_RESPONSE + e.getMessage();
        }
    }

    private ExcelRow getExcelRow(Row row) {
        ExcelRow excelRow = new ExcelRow();
        for (int i = 0; i < row.getLastCellNum(); i++) {
            excelRow.addData(getCellType(row.getCell(i)));
        }
        return excelRow;
    }

    private String getCellType(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return null;
        }
    }


    private boolean deleteRowIfExists(Sheet sheet, Row row, List<Object> parameters) {
        boolean found = true;
        for (int i = 0; i < row.getLastCellNum(); i++) {
          if (!String.valueOf(parameters.get(i)).equals(getCellType(row.getCell(i)))) {
                found = false;
                break;
            }
        }


        if (found)
            deleteRowAndShift(sheet, row);

        return found;
    }

    private void deleteRowAndShift(Sheet sheet, Row row){
        int rowIndex = row.getRowNum();
        sheet.removeRow(row);
        if (rowIndex < sheet.getLastRowNum()) {
            sheet.shiftRows(rowIndex + 1, sheet.getLastRowNum(), -1);
        }
    }




    private boolean updateRowIfExists(Row row, List<Object> oldValues,List<Object> newValues) {
        boolean found = true;
        for (int i = 0; i < row.getLastCellNum(); i++) {
            if (!String.valueOf(oldValues.get(i)).equals(getCellType(row.getCell(i)))) {
                found = false;
                break;
            }
        }

        if (found){
            for (int i = 0; i < row.getLastCellNum(); i++) {
                Cell cell = row.getCell(i);
                cell.setCellValue(String.valueOf(newValues.get(i)));
            }
        }


        return found;
    }
}
