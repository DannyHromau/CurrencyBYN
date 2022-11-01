package com.dannyhromau.currency.model;

import lombok.Data;

@Data
public class ShortRate {
    private int cur_id;
    private String startdate;
    private String enddate;
}
