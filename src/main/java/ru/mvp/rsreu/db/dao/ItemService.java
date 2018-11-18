package ru.mvp.rsreu.db.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.mvp.rsreu.db.entity.Item;
import ru.mvp.rsreu.db.util.HibernateUtil;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    public Item searchByItemCode(String itemCode) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT * FROM ITEMS WHERE itemcode = :itemcode";
        Query query = session.createNativeQuery(sql).addEntity(Item.class);
        query.setParameter("itemcode", itemCode);
        Item item = (Item) query.uniqueResult();
        session.close();
        return item;
    }

    @Override
    public void insertOrUpdateItems(List<Item> itemList) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            itemList.stream()
                    .forEach(e -> {
                        //проверяем наличие идентичной по ключевым полям записи, если есть то не апдейтим
                        CriteriaBuilder builder = session.getCriteriaBuilder();
                        CriteriaQuery<Item> query = builder.createQuery(Item.class);
                        Root<Item> acc = query.from(Item.class);
                        Predicate cond = builder.and(
                                builder.equal(acc.get("itemCode"), e.getItemCode()),
                                builder.equal(acc.get("itemName"), e.getItemName()),
                                builder.equal(acc.get("price"), e.getPrice()),
                                builder.equal(acc.get("promotionPrice"), e.getPromotionPrice()),
                                builder.equal(acc.get("storageUnit"), e.getStorageUnit())
                        );
                        query.where(cond);
                        TypedQuery<Item> q = session.createQuery(query);
                        List<Item> result = q.getResultList();
                        if (result.size() == 0) {
                            session.saveOrUpdate(e);
                        }
                    });
        } catch (Exception e) {
            LOGGER.error("Catch error: ", e);
            session.getTransaction().rollback();
        } finally {
            session.getTransaction().commit();
            session.close();
        }
    }
}
