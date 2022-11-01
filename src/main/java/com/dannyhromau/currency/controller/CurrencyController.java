package com.dannyhromau.currency.controller;

import com.dannyhromau.currency.dto.ValueData;
import com.dannyhromau.currency.model.DailyCurrency;
import com.dannyhromau.currency.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CurrencyController {
    @Autowired
    private  InfoService infoService;
    @GetMapping("/currencies")
    public List<String> getAllCurrencies(){

        return infoService.getValuesNames();
    }

    @PostMapping("/dynamics")
    public List<DailyCurrency> getCurrencyFromPeriod(@RequestBody ValueData valueData){
        return infoService.getDailyCurrencies(valueData);
    }

}
