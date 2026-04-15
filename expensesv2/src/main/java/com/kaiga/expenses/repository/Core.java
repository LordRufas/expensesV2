package com.kaiga.expenses.repository;

import com.kaiga.expenses.entity.ExcelRow;
import com.kaiga.expenses.entity.ExcelSheet;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class Core {



    @Value("${app.repositoryFileName}")
    private String fileName;

    public ExcelSheet readFromFile(int sheetNumber){
        try{
            ExcelSheet excelSheet = new ExcelSheet();
            ClassPathResource res = new ClassPathResource(fileName);
            InputStream is = res.getInputStream();
            Workbook workbook = new XSSFWorkbook(is) ;

                Sheet sheet = workbook.getSheetAt(sheetNumber);

                for (Row row : sheet) {
                    ExcelRow excelRow = new ExcelRow();
                    for (Cell cell : row) {
                        switch (cell.getCellType()) {
                            case STRING:
                                excelRow.addData(cell.getStringCellValue());
                                break;
                            case NUMERIC:
                                excelRow.addData(String.valueOf(cell.getNumericCellValue()));
                                break;
                            case BOOLEAN:
                                excelRow.addData(String.valueOf(cell.getBooleanCellValue()));
                                break;
                            default:
                                break;
                        }
                    }
                    excelSheet.addRow(excelRow);


                }
                excelSheet.setHeaders();
            return excelSheet;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

    }


}
