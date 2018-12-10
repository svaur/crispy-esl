package ru.mvp.rsreu.integration.file.witers.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mvp.rsreu.db.entity.ESL;
import ru.mvp.rsreu.integration.file.witers.IWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by Art on 18.11.2018.
 */
public class ExcelESLWriter implements IWriter {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExcelESLWriter.class);

    @Override
    public void write(List<ESL> eslList, Path path) {
        try (Workbook book = new HSSFWorkbook()) {
            Sheet sheet = book.createSheet("EslList");
            for (int i = 0; i < eslList.size(); i++) {
                ESL esl = eslList.get(i);
                Row tempRow = sheet.createRow(i);
                Cell eslCode = tempRow.createCell(0);
                eslCode.setCellValue(esl.getEslCode());
                Cell eslType = tempRow.createCell(1);
                eslType.setCellValue(esl.getEslType());
                Cell connectivity = tempRow.createCell(2);
                connectivity.setCellValue(esl.getConnectivity());
                Cell status = tempRow.createCell(3);
                status.setCellValue(esl.getStatus());
                Cell batteryLevel = tempRow.createCell(4);
                batteryLevel.setCellValue(esl.getBatteryLevel());
                Cell firmWare = tempRow.createCell(5);
                firmWare.setCellValue(esl.getFirmWare());
                Cell registrationDate = tempRow.createCell(6);
                registrationDate.setCellValue(String.valueOf(esl.getRegistrationDate()));
                Cell startDate = tempRow.createCell(7);
                startDate.setCellValue(String.valueOf(esl.getStartDate()));
                Cell eslPattern = tempRow.createCell(8);
                eslPattern.setCellValue(esl.getEslPattern());
                Cell lastUpdate = tempRow.createCell(9);
                lastUpdate.setCellValue(String.valueOf(esl.getLastUpdate()));
                Cell itemCode = tempRow.createCell(10);
                if (esl.getItem() != null) {
                    itemCode.setCellValue(esl.getItem().getItemCode());
                } else {
                    itemCode.setCellValue("");
                }
            }
            try (FileOutputStream fos = new FileOutputStream(path.toFile())) {
                book.write(fos);
            }
        } catch (IOException e) {
            LOGGER.error("Catch error: ", e);
        }
    }
}
