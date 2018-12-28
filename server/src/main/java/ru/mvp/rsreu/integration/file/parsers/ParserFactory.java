package ru.mvp.rsreu.integration.file.parsers;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.mvp.rsreu.integration.file.parsers.csv.EslCsvParser;
import ru.mvp.rsreu.integration.file.parsers.csv.ItemCsvParser;
import ru.mvp.rsreu.integration.file.parsers.excel.ExcelParser;

import java.io.File;
import java.nio.file.Path;

/**
 * Created by Art on 16.11.2018.
 */

@Component
public class ParserFactory {

    private final static Logger LOGGER = LoggerFactory.getLogger(ParserFactory.class);
    private IParser parser;

    public IParser getParser(Path data, TypeEntity typeEntity){
        File f = data.toFile();
        String extension = FilenameUtils.getExtension(f.getName());
        if ("csv".equalsIgnoreCase(extension)) {
            switch (typeEntity) {
                case ESL:
                    parser = new EslCsvParser();
                    break;
                case ITEM:
                    parser = new ItemCsvParser();
                    break;
            }
            LOGGER.debug("CSV parser changed");
        } else {
            //parser = new ExcelParser();
            LOGGER.debug("ignore");
        }
        return parser;
    }

}
