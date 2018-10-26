package ru.mvp.rsreu;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.mvp.rsreu.db.entity.ESL;
import ru.mvp.rsreu.db.entity.Item;
import ru.mvp.rsreu.db.util.HibernateUtil;

import java.sql.Date;
import java.util.List;

/**
 * Created by Art on 06.10.2018.
 */
public class Q
{

    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();


        for(int i = 0; i < 40; i++) {
            ESL esl1 = new ESL();
            esl1.setBatteryLevel("Нормальный");
            esl1.setConnectivity(true);
            esl1.setElsCode("115582" + i);
            esl1.setElsType("15D4S1-1v2");
            esl1.setEslPattern("STANDART");
            esl1.setFirmWare("12v2");
            esl1.setLastUpdate(new Date(System.currentTimeMillis()));
            esl1.setRegistrationDate(new Date(System.currentTimeMillis()));
            esl1.setStatus(false);

            ESL esl2 = new ESL();
            esl2.setBatteryLevel("Высокий");
            esl2.setConnectivity(true);
            esl2.setElsCode("115581" + i);
            esl2.setElsType("15D4S1-1v2");
            esl2.setEslPattern("STANDART");
            esl2.setFirmWare("12v2");
            esl2.setLastUpdate(new Date(System.currentTimeMillis()));
            esl2.setRegistrationDate(new Date(System.currentTimeMillis()));
            esl2.setStatus(false);

            Item item1 = new Item();
            item1.setItemCode("1122" + i);
            item1.setItemName("Шапка из мышиных шкур");
            item1.setLastUpdated(new Date(System.currentTimeMillis()));
            item1.setPrice(1999999.0);
            item1.setPromotionPrice(10000.5);
            item1.setQuantity(10);
            item1.setStorageUnit("ШТ");
            item1.setEsl(esl1);
            Item item2 = new Item();
            item2.setItemCode("2115" + i);
            item2.setItemName("Трусы из шкур тушканчиков");
            item2.setLastUpdated(new Date(System.currentTimeMillis()));
            item2.setPrice(199.0);
            item2.setPromotionPrice(100.5);
            item2.setQuantity(100);
            item2.setStorageUnit("ШТ");
            item2.setEsl(esl2);


            esl1.setItem(item1);
            esl2.setItem(item2);
            session.save(esl1);
            session.save(esl2);

            session.save(item1);
            session.save(item2);


        }

        transaction.commit();
        session.close();
    }
}
