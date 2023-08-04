package com.targetindia.DigitizeTimeTable.service;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class GetDow {
    Date date;
    Calendar calendar;

    public String getDowFromDate(String date_str) throws Exception {

        date = new SimpleDateFormat("dd-MM-yyyy").parse(date_str);
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        //week number (sunday-1)
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        //dow - monday or tuesday..
        String dow= new SimpleDateFormat("EEEE").format(date);
        return dow.toLowerCase();
    }
}
