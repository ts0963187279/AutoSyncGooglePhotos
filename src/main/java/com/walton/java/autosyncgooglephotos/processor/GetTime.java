package com.walton.java.autosyncgooglephotos.processor;

import poisondog.core.Mission;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetTime implements Mission<Date> {
    public String execute(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String strDate = simpleDateFormat.format(date);
        return strDate;
    }
}
