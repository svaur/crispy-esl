package ru.mvp.rsreu.db.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.mvp.rsreu.db.entity.ESL;
import ru.mvp.rsreu.db.entity.Item;
import ru.mvp.rsreu.db.util.HibernateUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Art on 30.09.2018.
 */
public class ESLService implements ESLDao {
    @Override
    public List<ESL> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT * FROM ESLS";
        Query query = session.createNativeQuery(sql).addEntity(ESL.class);
        List<ESL> eslList = query.list();
        session.close();
        return eslList;
    }

    @Override
    public List<ESL> getAll(int limit) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT * FROM ESLS LIMIT :limit";
        Query query = session.createNativeQuery(sql).addEntity(ESL.class);
        query.setParameter("limit", limit);
        List<ESL> eslList = query.list();
        session.close();
        return eslList;
    }

    @Override
    public ESL searchByESLCode(String eslCode) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT * FROM ESLS WHERE elscode = :elscode";
        Query query = session.createNativeQuery(sql).addEntity(ESL.class);
        query.setParameter("elscode", Integer.valueOf(eslCode));//todo разобраться что за ссанина с типом переменной
        ESL eslList = (ESL) query.list().get(0);
        session.close();
        return eslList;
    }

    @Override
    public List<ESL> searchByValue(String value, int showSize) {
        List<ESL> fullList = getAll();
        List<ESL> resultList = new ArrayList<>(showSize);
        Iterator<ESL> iterator = fullList.iterator();
        int i = 0;
        while (iterator.hasNext() && i < showSize) {
            ESL tempEsl = iterator.next();
            Item item = tempEsl.getItem();
            if (tempEsl.getElsCode().contains(value) || tempEsl.getElsType().contains(value) ||
                    item.getItemCode().contains(value) ||
                    item.getItemName().contains(value)) {
                resultList.add(tempEsl);
                i++;
            }
        }
        return resultList;
    }
}
