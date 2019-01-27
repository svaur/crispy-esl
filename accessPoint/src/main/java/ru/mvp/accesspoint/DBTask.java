package ru.mvp.accesspoint;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.mvp.database.entities.Tasks;
import ru.mvp.database.repositories.TasksRepository;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
public class DBTask {
    TasksRepository tasksRepository;

    public DBTask(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @Scheduled(fixedRate = 5000)
    public void sheduledQuery() {
        List<Tasks> tasks = tasksRepository.findAllByStatusAndStartDateBefore(0, new Timestamp(new Date().getTime()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        tasks.stream().filter(e-> e.getCronExpression().charAt(dayOfWeek)=='1').forEach(this::doSomeThing);
    }
    private void doSomeThing(Tasks tasks){
        System.out.println("DO UPDATE BEACH "+ tasks.getTaskName());
    }
}