package ru.mvp.rsreu.integration.file.excel;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

/**
 * Created by Art on 15.11.2018.
 */
@Component
public class ExcelParser {

    public Object parseFile(Path path){

        try {
            Workbook myExcelBook = new XSSFWorkbook(new FileInputStream(path.toFile()));
            int sheetsSize = myExcelBook.getNumberOfSheets();
            for (int i = 0; i < sheetsSize; i++){
                Sheet myExcelSheet = myExcelBook.getSheetAt(i);
                StreamSupport.stream(myExcelSheet.spliterator(), false)
                        .forEach(e -> {
                            String itemCode = String.valueOf(e.getCell(0).getNumericCellValue());
                            String itemName = e.getCell(1).getStringCellValue();
                            double price = e.getCell(2).getNumericCellValue();
                            double promotionPrice = Double.parseDouble(e.getCell(3).getStringCellValue());
                            String storageUnit = e.getCell(4).getStringCellValue();
                            System.out.println(itemName);
                        });


            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Object();
    }

    public static void main(String[] args) {
        new ExcelParser().parseFile(Paths.get("C:\\Users\\Art\\Desktop\\projects\\src\\main\\resources\\tempDir\\items.xlsx"));
    }
}
