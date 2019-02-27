package ru.mvp.accesspoint;

import org.springframework.stereotype.Component;
import ru.mvp.database.LoggerDBTools;
import ru.mvp.database.entities.Esls;
import ru.mvp.database.repositories.EslsRepository;

import java.io.*;
import java.sql.Timestamp;
import java.util.Date;

import static com.sun.javafx.font.PrismFontFactory.isWindows;

@Component
public class ConsoleTools {
    EslsRepository eslsRepository;
    LoggerDBTools loggerDBTools;

    public ConsoleTools(EslsRepository eslsRepository, LoggerDBTools loggerDBTools) {
        this.eslsRepository = eslsRepository;
        this.loggerDBTools = loggerDBTools;
    }

    public String runConsoleCommand(String command) throws IOException {
        ProcessBuilder builder = new ProcessBuilder();
        if (isWindows) {
            builder.command("cmd.exe", "/c", command);
        } else {
            builder.command("sh", "-c", command);
        }
        builder.directory(new File(System.getProperty("user.home")));
        Process process = builder.start();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        StringBuilder output = new StringBuilder();
        while ((line = reader.readLine())!= null) {
            output.append(line + "<br>");
        }
        return output.toString();
    }
    public void getByteImage(String eslStr){
        //todo тупо влепим поле с айдишниками ценников для обновления в сущность таски. потом доработаем

        Esls esl = eslsRepository.findByCode(eslStr);
        try {
            byte[] data = esl.getNextImage();

            FileOutputStream fos = new FileOutputStream(eslStr+".exe");
            fos.write(data, 0, data.length);
            fos.flush();
            fos.close();
            loggerDBTools.log(new Timestamp(new Date().getTime()),"task", "run", "успешно обновлен ценник <br>" + esl.toString() + "<br> ручное обновление <br>" , "integration");
        }catch (Exception e){
            System.out.println("обработать ошибку"+e);
            loggerDBTools.log(new Timestamp(new Date().getTime()),"task", "run", "ошибка обновления <br>" + esl.toString() + "<br>" + e.getLocalizedMessage(), "integration");
        }
    }
}
