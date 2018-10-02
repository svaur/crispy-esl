package ru.mvp.rsreu.db.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.mvp.rsreu.db.dao.SensorsDao;
import ru.mvp.rsreu.db.entity.Sensors;
import ru.mvp.rsreu.db.util.HibernateUtil;

import java.util.List;

/**
 * Created by Art on 30.09.2018.
 */
public class SensorsService implements SensorsDao {
    @Override
    public List<Sensors> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT * FROM SENSORS";
        Query query = session.createNativeQuery(sql).addEntity(Sensors.class);
        List<Sensors> sensorsList = query.list();
        session.close();
        return sensorsList;
    }
}
