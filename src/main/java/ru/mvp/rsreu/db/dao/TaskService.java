package ru.mvp.rsreu.db.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import ru.mvp.rsreu.db.entity.Task;
import ru.mvp.rsreu.db.util.HibernateUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class TaskService implements TaskDao {
    @Override
    public List<Task> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT * FROM TASKS";
        Query query = session.createNativeQuery(sql).addEntity(Task.class);
        List<Task> taskList = query.list();
        session.close();
        return taskList;
    }

    @Override
    public List<Task> getAll(int limit) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT * FROM TASKS LIMIT :limit";
        Query query = session.createNativeQuery(sql).addEntity(Task.class);
        query.setParameter("limit", limit);
        List<Task> taskList = query.list();
        session.close();
        return taskList;
    }

    @Override
    public Task searchByTaskName(String taskName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT * FROM TASKS WHERE taskname = :taskname";
        Query query = session.createNativeQuery(sql).addEntity(Task.class);
        query.setParameter("taskname", Integer.valueOf(taskName));//todo разобраться что за ссанина с типом переменной
        Task task = (Task) query.uniqueResult();
        session.close();
        return task;
    }

    @Override
    public List<Task> searchByValue(String value, int showSize) {
        List<Task> fullList = getAll();
        List<Task> resultList = new ArrayList<>(showSize);
        Iterator<Task> iterator = fullList.iterator();
        int i = 0;
        while (iterator.hasNext() && i < showSize) {
            Task tempTask = iterator.next();
            if (tempTask.getTaskType().contains(value.toLowerCase()) || tempTask.getTaskName().toLowerCase().contains(value.toLowerCase())) {
                resultList.add(tempTask);
                i++;
            }
        }
        return resultList;
    }
}
