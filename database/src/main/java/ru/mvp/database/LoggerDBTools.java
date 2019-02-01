package ru.mvp.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mvp.database.entities.EntityLog;
import ru.mvp.database.repositories.EntityLogRepository;

import java.sql.Timestamp;

@Component
public class LoggerDBTools {
    EntityLogRepository entityLogRepository;

    @Autowired
    public LoggerDBTools(EntityLogRepository entityLogRepository) {
        this.entityLogRepository = entityLogRepository;
    }

    public void  log(Timestamp time, String name, String type, String event, String source ){
        EntityLog entityLog = new EntityLog();
        entityLog.setTime(time);
        entityLog.setEvent(event);
        entityLog.setName(name);
        entityLog.setType(type);
        entityLog.setSource(source);
        entityLogRepository.saveAndFlush(entityLog);
    }
}
