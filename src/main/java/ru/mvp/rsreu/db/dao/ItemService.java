package ru.mvp.rsreu.db.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.mvp.rsreu.db.entity.Item;
import ru.mvp.rsreu.db.util.HibernateUtil;

import java.util.List;

/**
 * Created by Art on 30.09.2018.
 */
@Component
public class ItemService implements ItemDao {
    private final static Logger LOGGER = LoggerFactory.getLogger(ItemService.class);

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
    public void insertOrUpdateItems(List<Item> itemList) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            itemList.stream()
                    .forEach(e -> session.saveOrUpdate(e));
        } catch (Exception e) {
            LOGGER.error("Catch error: ", e);
            session.getTransaction().rollback();
        } finally {
            session.getTransaction().commit();
            session.close();
        }
    }
}
