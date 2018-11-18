package ru.mvp.rsreu.db.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.mvp.rsreu.db.entity.ESL;
import ru.mvp.rsreu.db.entity.Item;
import ru.mvp.rsreu.db.util.HibernateUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Art on 30.09.2018.
 */
@Component
public class ESLService implements ESLDao {

    private final static Logger LOGGER = LoggerFactory.getLogger(ESLService.class);

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
        String sql = "SELECT * FROM ESLS WHERE esls.eslcode = :elscode";
        Query query = session.createNativeQuery(sql).addEntity(ESL.class);
        query.setParameter("elscode", eslCode);
        ESL esl = (ESL) query.getSingleResult();
        session.close();
        return esl;
    }

    @Override
    public boolean unAssignItem(ESL esl) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        boolean result;
        try {
            esl.setItem(null);
            session.saveOrUpdate(esl);
        } catch (Exception e) {
            LOGGER.error("Catch error: ", e);
            session.getTransaction().rollback();
        } finally {
            session.getTransaction().commit();
            result = session.getTransaction().getStatus().isOneOf(TransactionStatus.COMMITTED);
            session.close();
        }
        return result;
    }

    @Override
    public boolean assignItem(ESL esl, Item item) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        boolean result;
        try {
            esl.setItem(item);
            session.saveOrUpdate(esl);
        } catch (Exception e) {
            LOGGER.error("Catch error: ", e);
            session.getTransaction().rollback();
        } finally {
            session.getTransaction().commit();
            result = session.getTransaction().getStatus().isOneOf(TransactionStatus.COMMITTED);
            session.close();
        }
        return result;
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
            if (tempEsl.getEslCode().contains(value) || tempEsl.getEslType().toLowerCase().contains(value.toLowerCase()) ||
                    item.getItemCode().contains(value) ||
                    item.getItemName().toLowerCase().contains(value.toLowerCase())) {
                resultList.add(tempEsl);
                i++;
            }
        }
        return resultList;
    }
}
