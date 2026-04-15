package com.kaiga.expenses.repository;

import com.kaiga.expenses.entity.ExcelRow;
import com.kaiga.expenses.entity.ExcelSheet;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Component
public class Core {


    @Value("${app.repositoryFileName}")
    private String fileName;

    public ExcelSheet readFromFile(int sheetNumber) {

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
        } catch (IOException e) {
            return null;
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


    public String addToSheet(int sheetNumber, List<Object> values) {

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


            return "OK";

        } catch (IOException e) {
            return "ERROR: " + e.getMessage();
        }
    }

    public String deleteRow(int sheetIndex, List<Object> parameters) {
        String response = "Not found";
        ExcelSheet excelSheet = new ExcelSheet();
        File file = new File(fileName);
        try (
                FileInputStream fis = new FileInputStream(file);
                Workbook workbook = new XSSFWorkbook(fis)
        ) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);


            for (int i = sheet.getLastRowNum(); i >= 0; i--) {
                Row row = sheet.getRow(i);
                if (row != null && deleteRowIfExists(sheet, row, parameters))
                    response = "Success";

            }

            // Write changes back to file
            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
            }
            return response;

        } catch (IOException e) {
            return "Error " + e.getMessage();
        }
    }


    private boolean deleteRowIfExists(Sheet sheet, Row row, List<Object> parameters) {
        boolean found = true;
        for (int i = 0; i < row.getLastCellNum(); i++) {
            System.out.println("Found + " + String.valueOf(parameters.get(i)));
            System.out.println("Found ++ " + getCellType(row.getCell(i)));

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
            return "Success";

        } catch (IOException e) {
            return "Error " + e.getMessage();
        }
    }


}
