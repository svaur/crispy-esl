package ru.mvp.rsreu.db.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.mvp.rsreu.db.dao.MerchandiseDao;
import ru.mvp.rsreu.db.entity.Merchandise;
import ru.mvp.rsreu.db.util.HibernateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Art on 30.09.2018.
 */
public class MerchandiseService implements MerchandiseDao {
    @Override
    public List<Merchandise> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT * FROM MERCHANDISE";
        Query query = session.createNativeQuery(sql).addEntity(Merchandise.class);
        List<Merchandise> merchandiseList = query.list();
        session.close();
        return merchandiseList;
    }
}
