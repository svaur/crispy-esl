package ru.mvp.rsreu.db.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.mvp.rsreu.db.entity.Item;
import ru.mvp.rsreu.db.util.HibernateUtil;

import java.util.List;

/**
 * Created by Art on 30.09.2018.
 */
public class ItemService implements ItemDao {
    @Override
    public List<Item> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT * FROM Items";
        Query query = session.createNativeQuery(sql).addEntity(Item.class);
        List<Item> itemList = query.list();
        session.close();
        return itemList;
    }

    @Override
    public Item getByESLCode(int eslId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT * FROM Items WHERE items.esl_elscode = :eslCode";
        Query query = session.createNativeQuery(sql).addEntity(Item.class);
        query.setParameter("eslCode", eslId);
        Item item = (Item) query.getSingleResult();
        session.close();
        return item;
    }
}
