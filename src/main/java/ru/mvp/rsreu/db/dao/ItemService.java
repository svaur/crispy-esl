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
        String sql = "SELECT * FROM Item";
        Query query = session.createNativeQuery(sql).addEntity(Item.class);
        List<Item> itemList = query.list();
        session.close();
        return itemList;
    }
}
