package ru.mvp.rsreu.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.mvp.rsreu.db.entity.ESL;
import ru.mvp.rsreu.db.entity.Item;
import ru.mvp.rsreu.db.repository.ESLRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Service
public class ESLService implements IService<ESL>  {

    private final ESLRepository eslRepository;

    @Autowired
    public ESLService(ESLRepository eslRepository) {
        this.eslRepository = eslRepository;
    }

    public Page<ESL> findAll(Pageable pageable) {
        return eslRepository.findAll(pageable);
    }

    public List<ESL> searchByValue(String value, Pageable pageable) {
        List<ESL> fullList = eslRepository.findAll();
        List<ESL> resultList = new ArrayList<>(pageable.getPageSize());
        Iterator<ESL> iterator = fullList.iterator();
        int i = 0;
        while (iterator.hasNext() && i < pageable.getPageSize()) {
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

    public ESL searchByESLCode(String eslCode) {
        return eslRepository.findByEslCode(eslCode);
    }

    public void saveEsl(ESL esl) {
        eslRepository.saveAndFlush(esl);
    }

    @Override
    public HashMap<String, String> fillEntityData(ESL e) {
        HashMap<String, String> hashMap = new HashMap<>();
        Item item = e.getItem();
        hashMap.put("eslCode", e.getEslCode());
        hashMap.put("eslType", e.getEslType());
        hashMap.put("itemCode", item == null ? "" : item.getItemCode());
        hashMap.put("itemName", item == null ? "" : item.getItemName());
        hashMap.put("price", String.valueOf(item == null ? "" : item.getPromotionPrice()));
        hashMap.put("lastUpdate", String.valueOf(e.getLastUpdate()));
        hashMap.put("connectivity", e.getConnectivity());
        hashMap.put("batteryLevel", String.valueOf(e.getBatteryLevel()));
        hashMap.put("status", e.getStatus());
        return hashMap;
    }

}
