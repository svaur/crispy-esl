package ru.mvp.rsreu.db.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.mvp.rsreu.db.entity.ESL;
import ru.mvp.rsreu.db.entity.Item;
import ru.mvp.rsreu.db.util.HibernateUtil;

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
    public ESL searchByESLCode(String eslCode) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT * FROM ESLS WHERE elscode = "+eslCode;
        Query query = session.createNativeQuery(sql).addEntity(ESL.class);
        ESL eslList = (ESL)query.list().get(0);
        session.close();
        return eslList;
    }

    @Override
    public List<ESL> searchByESLCodeOrTypeOrItemCodeOrItemName(String value) {
        List<ESL> tempResult = getAll();
        Iterator<ESL> iterator = tempResult.iterator();
        while (iterator.hasNext()){
            ESL temp = iterator.next();
            Item item = temp.getItem();
            if(!value.equals(temp.getElsCode()) || !value.equals(temp.getElsType()) ||
                    !value.equals(item.getItemCode()) ||
                    !value.equals(item.getItemName())){
                iterator.remove();
            }
        }
        return tempResult;
    }
}
