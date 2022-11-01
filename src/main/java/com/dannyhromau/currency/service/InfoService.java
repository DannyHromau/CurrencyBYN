package com.dannyhromau.currency.service;

import com.dannyhromau.currency.dto.ValueData;
import com.dannyhromau.currency.externalapi.DataManager;
import com.dannyhromau.currency.model.DailyCurrency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class InfoService {
    @Autowired
    private DataManager dataManager;

    public List<String> getValuesNames() {
        List<String> valuesNames = new ArrayList<>();
        Object[] currenciesArray = DataManager.getCurrencyArray();
        for (Object object : currenciesArray) {
            StringBuilder builder = new StringBuilder();
            LinkedHashMap<String, Integer> linkedHashMap = (LinkedHashMap<String, Integer>) object;
            builder.append(linkedHashMap.get("Cur_Name")).append(" (").append(linkedHashMap.get("Cur_Abbreviation")).append(")");
            valuesNames.add(builder.toString());
        }
        return valuesNames;
    }

    public List<DailyCurrency> getDailyCurrencies(ValueData valueData) {
        valueData.setCurName(valueData.getCurName().replaceAll("\\(.*\\)", ""));
        valueData.setCurName(valueData.getCurName().trim());
        List<DailyCurrency> dailyCurrencyList = new ArrayList<>();
        Object[] rateShortArray = dataManager.getShortRateArray(valueData);
        for (Object object : rateShortArray) {
            DailyCurrency dailyCurrency = new DailyCurrency();
            LinkedHashMap<String, Integer> linkedHashMap = (LinkedHashMap<String, Integer>) object;
            dailyCurrency.setDate(String.valueOf(linkedHashMap.get("Date")));
            dailyCurrency.setDailyCurr(String.valueOf(linkedHashMap.get("Cur_OfficialRate")));
            dailyCurrencyList.add(dailyCurrency);
        }
        return dailyCurrencyList;
    }
}
