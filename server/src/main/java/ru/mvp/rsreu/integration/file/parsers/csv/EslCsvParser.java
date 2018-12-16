package ru.mvp.rsreu.integration.file.parsers.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mvp.rsreu.db.entity.ESL;
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
public class EslCsvParser implements IParser {

    private final static Logger LOGGER = LoggerFactory.getLogger(EslCsvParser.class);

    @Override
    public List<ESL> parse(Path data) {
        List<ESL> esls = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(data);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            StreamSupport.stream(csvParser.spliterator(), false)
                    .forEach(e -> {
                        String eslCode = e.get(0);
                        String eslType = e.get(1);
                        String eslFirmWare = e.get(2);

                        ESL esl = new ESL();
                        esl.setEslCode(eslCode);
                        esl.setEslType(eslType);
                        esl.setFirmWare(eslFirmWare);

                        esls.add(esl);
                    });
        } catch (IOException e) {
            LOGGER.error("Catch error: ", e);
        }
        return esls;
    }
}
