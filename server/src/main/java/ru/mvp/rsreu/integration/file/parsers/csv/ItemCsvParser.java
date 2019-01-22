package ru.mvp.rsreu.integration.file.parsers.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mvp.database.entities.Items;
import ru.mvp.rsreu.integration.file.parsers.IParser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

/**
 * Created by Art on 15.11.2018.
 */
public class ItemCsvParser implements IParser {

    private final static Logger LOGGER = LoggerFactory.getLogger(ItemCsvParser.class);

    @Override
    public List<Items> parse(Path data) {
        List<Items> items = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(data);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            StreamSupport.stream(csvParser.spliterator(), false)
                    .forEach(e -> {
                        String itemCode = e.get(0);
                        String itemName = e.get(1);
                        double price = Double.parseDouble(e.get(2));
                        String storageUnit = e.get(3);

                        Items item = new Items();
                        item.setCode(itemCode);
                        item.setName(itemName);
                        item.setPrice(price);
                        item.setStorageUnit(storageUnit);

                        items.add(item);
                    });
        } catch (IOException e) {
            LOGGER.error("Catch error: ", e);
        }
        return items;
    }
}
