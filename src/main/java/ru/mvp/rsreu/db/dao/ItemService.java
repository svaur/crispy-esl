package ru.mvp.rsreu.db.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import ru.mvp.rsreu.db.entity.Item;
import ru.mvp.rsreu.db.util.HibernateUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Art on 30.09.2018.
 */
@Component
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
    public List<Item> getAll(int limit) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT * FROM ITEMS LIMIT :limit";
        Query query = session.createNativeQuery(sql).addEntity(Item.class);
        query.setParameter("limit", limit);
        List<Item> ItemList = query.list();
        session.close();
        return ItemList;
    }

    @Override
    public Item searchByItemCode(String ItemCode) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT * FROM ITEMS WHERE itemcode = :itemcode";
        Query query = session.createNativeQuery(sql).addEntity(Item.class);
        query.setParameter("itemcode", Integer.valueOf(ItemCode));//todo разобраться что за ссанина с типом переменной
        Item Item = (Item) query.uniqueResult();
        session.close();
        return Item;
    }

    @Override
    public List<Item> searchByValue(String value, int showSize) {
        List<Item> fullList = getAll();
        List<Item> resultList = new ArrayList<>(showSize);
        Iterator<Item> iterator = fullList.iterator();
        int i = 0;
        while (iterator.hasNext() && i < showSize) {
            Item tempItem = iterator.next();
            if (tempItem.getItemCode().contains(value) || tempItem.getItemName().toLowerCase().contains(value.toLowerCase())) {
                resultList.add(tempItem);
                i++;
            }
        }
        return resultList;
    }
}
