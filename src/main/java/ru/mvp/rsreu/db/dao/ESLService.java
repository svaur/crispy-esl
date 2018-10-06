package ru.mvp.rsreu.db.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.mvp.rsreu.db.entity.ESL;
import ru.mvp.rsreu.db.util.HibernateUtil;

import java.util.List;

/**
 * Created by Art on 30.09.2018.
 */
public class ESLService implements ESLDao {
    @Override
    public List<ESL> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT * FROM ESL";
        Query query = session.createNativeQuery(sql).addEntity(ESL.class);
        List<ESL> eslList = query.list();
        session.close();
        return eslList;
    }
}
