package ru.mvp.rsreu.integration.file.witers.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mvp.rsreu.db.entity.ESL;
import ru.mvp.rsreu.integration.file.witers.IWriter;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by Art on 18.11.2018.
 */
public class CsvESLWriter implements IWriter {

    private final static Logger LOGGER = LoggerFactory.getLogger(CsvESLWriter.class);

    @Override
    public void write(List<ESL> eslList, Path path) {
        try (PrintWriter writer = new PrintWriter(path.toFile(), "UTF-8");
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            eslList.stream().forEach(e -> {
                try {
                    csvPrinter.printRecord(e.getEslCode(),
                            e.getEslType(),
                            e.getConnectivity(),
                            e.getStatus(),
                            e.getBatteryLevel(),
                            e.getFirmWare(),
                            e.getRegistrationDate(),
                            e.getStartDate(),
                            e.getEslPattern(),
                            e.getLastUpdate(),
                            e.getItem() != null ? e.getItem().getItemCode() : "");
                } catch (IOException e1) {
                    LOGGER.error("Catch error: ", e);
                }
            });

            csvPrinter.flush();
        } catch (IOException e) {
            LOGGER.error("Catch error: ", e);
        }
    }
}
