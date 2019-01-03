//package ru.mvp.rsreu.integration.file.parsers.excel;
//
//import org.apache.commons.io.FilenameUtils;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import ru.mvp.rsreu.db.entity.Item;
//import ru.mvp.rsreu.integration.file.parsers.IParser;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.nio.file.Path;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.StreamSupport;
//
///**
// * Created by Art on 16.11.2018.
// */
//public class ExcelParser implements IParser {
//
//    private final static Logger LOGGER = LoggerFactory.getLogger(ExcelParser.class);
//
//    @Override
//    public List<Item> parse(Path data) {
//        List<Item> items = new ArrayList<>();
//        try {
//            File f = data.toFile();
//            String extension = FilenameUtils.getExtension(f.getName());
//            Workbook workBook;
//            if ("xlsx".equalsIgnoreCase(extension)) {
//                workBook = new XSSFWorkbook(new FileInputStream(data.toFile()));
//                LOGGER.debug("XSSFWorkbook changed");
//            } else {
//                workBook = new HSSFWorkbook(new FileInputStream(data.toFile()));
//                LOGGER.debug("HSSFWorkbook changed");
//            }
//            int sheetsSize = workBook.getNumberOfSheets();
//            for (int i = 0; i < sheetsSize; i++) {
//                Sheet sheet = workBook.getSheetAt(i);
//                StreamSupport.stream(sheet.spliterator(), false)
//                        .forEach(e -> {
//                            String itemCode = String.valueOf(e.getCell(0).getNumericCellValue());
//                            String itemName = e.getCell(1).getStringCellValue();
//                            double price = e.getCell(2).getNumericCellValue();
//                            double promotionPrice = e.getCell(3).getNumericCellValue();
//                            String storageUnit = e.getCell(4).getStringCellValue();
//
//                            Item item = new Item();
//                            item.setItemCode(itemCode);
//                            item.setItemName(itemName);
//                            item.setPrice(price);
//                            item.setPromotionPrice(promotionPrice);
//                            item.setStorageUnit(storageUnit);
//
//                            items.add(item);
//                        });
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return items;
//    }
//}
