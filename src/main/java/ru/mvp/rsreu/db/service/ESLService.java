package ru.mvp.rsreu.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.mvp.rsreu.db.entity.ESL;
import ru.mvp.rsreu.db.entity.Item;
import ru.mvp.rsreu.db.repository.ESLRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service("esl")
public class ESLService  {

    @Autowired
    private ESLRepository eslRepository;

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


}
