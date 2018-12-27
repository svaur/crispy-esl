package ru.mvp.rsreu.integration.file.witers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.mvp.rsreu.integration.file.witers.csv.CsvESLWriter;
import ru.mvp.rsreu.integration.file.witers.excel.ExcelESLWriter;

/**
 * Created by Art on 18.11.2018.
 */
@Component
public class WriterFactory {
    private final static Logger LOGGER = LoggerFactory.getLogger(WriterFactory.class);
    private IWriter writer;

    public IWriter getWriter(String type) {
        if ("excel".equalsIgnoreCase(type)) {
            writer = new ExcelESLWriter();
            LOGGER.debug("Excel writer changed");
        } else {
            writer = new CsvESLWriter();
            LOGGER.debug("CSV writer changed");
        }
        return writer;
    }
}
