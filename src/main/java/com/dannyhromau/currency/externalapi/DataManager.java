package com.dannyhromau.currency.externalapi;

import com.dannyhromau.currency.dto.ValueData;
import com.dannyhromau.currency.model.ShortRate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Component
public class DataManager {

    public static Object[] getCurrencyArray() {
        String url = "https://www.nbrb.by/api/exrates/currencies";
        RestTemplate restTemplate = new RestTemplate();
        Object[] currenciesArray = restTemplate.getForObject(url, Object[].class);
        return currenciesArray;
    }

    public Object[] getShortRateArray(ValueData valueData) {
        String ratesUrl = "https://www.nbrb.by/api/exrates/rates/dynamics/";
        Object[] currenciesArray = getCurrencyArray();
        ShortRate shortRate = new ShortRate();
        for (Object object : currenciesArray) {
            LinkedHashMap<String, Integer> linkedHashMap = (LinkedHashMap<String, Integer>) object;
            if (valueData.getCurName().equals(String.valueOf(linkedHashMap.get("Cur_Name")))) {
                shortRate.setCur_id(linkedHashMap.get("Cur_ID"));
                shortRate.setStartdate(valueData.getStartDate());
                shortRate.setEnddate(valueData.getEndDate());
            }
        }
        RestTemplate restTemplate = new RestTemplate();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("startdate", shortRate.getStartdate());
        hashMap.put("enddate", shortRate.getEnddate());
        restTemplate.setDefaultUriVariables(hashMap);
        ratesUrl = ratesUrl + shortRate.getCur_id() + "?startDate=" + shortRate.getStartdate() + "&endDate=" + shortRate.getEnddate();
        Object[] rateShortArray = restTemplate.getForObject(ratesUrl, Object[].class);
        return rateShortArray;
    }


}
