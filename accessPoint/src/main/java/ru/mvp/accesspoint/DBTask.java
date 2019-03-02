package ru.mvp.accesspoint;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.mvp.database.LoggerDBTools;
import ru.mvp.database.entities.Esls;
import ru.mvp.database.entities.Tasks;
import ru.mvp.database.repositories.EslsRepository;
import ru.mvp.database.repositories.TasksRepository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.util.*;
import java.util.List;

@Component
@EnableScheduling
public class DBTask {
    TasksRepository tasksRepository;
    EslsRepository eslsRepository;
    LoggerDBTools loggerDBTools;

    public DBTask(TasksRepository tasksRepository, EslsRepository eslsRepository, LoggerDBTools loggerDBTools) {
        this.tasksRepository = tasksRepository;
        this.eslsRepository = eslsRepository;
        this.loggerDBTools = loggerDBTools;
    }

//    @Scheduled(fixedRate = 5000)
//    public void sheduledQuery() {
//        List<Tasks> tasks = tasksRepository.findAllByStatusAndStartDateBefore(0, new Timestamp(new Date().getTime()));
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
//        tasks.stream().filter(e-> e.getCronExpression().charAt(dayOfWeek)=='1').forEach(this::getBMPImage);
//    }
    private void getBMPImage(Tasks tasks){
        //todo тупо влепим поле с айдишниками ценников для обновления в сущность таски. потом доработаем
        String barcodes = tasks.getBarcodes();
        Arrays.asList(barcodes.split(",")).forEach(s -> {
            Esls esl = eslsRepository.findByCode(s);
            try {
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(esl.getNextImage()));
                ImageIO.write(image, "bmp", new File(s + ".bmp"));
                //тут пойдет запрос на драйвер на обновление
//
                FileWriter fileWriter = new FileWriter(s + ".txt");
                fileWriter.write(new String(esl.getNextImage()));
                fileWriter.flush();
                fileWriter.close();
                //todo пока костыль
                tasks.setStatus(1);
                tasksRepository.saveAndFlush(tasks);

                loggerDBTools.log(new Timestamp(new Date().getTime()),"task", "run", "успешно обновлен ценник <br>" + esl.toString() + "<br> по таске <br>" + tasks.toString(), "integration");
            }catch (Exception e){
                System.out.println("обработать ошибку"+e);
                loggerDBTools.log(new Timestamp(new Date().getTime()),"task", "run", "ошибка обновления <br>" + esl.toString() + "<br>" + e.getLocalizedMessage(), "integration");
            }
            //а тут будет запись в результат
        });

    }
}