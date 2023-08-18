package com.target_india.digitize_time_table.utils;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class DayOfWeekConverter {
    Date date;
    Calendar calendar;

    public String getDowFromDate(String dateString) throws Exception {

        date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        //dow - monday or tuesday..
        String dayOfWeek= new SimpleDateFormat("EEEE").format(date);
        return dayOfWeek.toLowerCase();
    }
}
