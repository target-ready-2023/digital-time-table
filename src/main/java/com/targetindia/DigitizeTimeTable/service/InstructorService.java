package com.targetindia.DigitizeTimeTable.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.targetindia.DigitizeTimeTable.repository.InstructorDao;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class InstructorService {
    @Autowired
    InstructorDao dao;

    @Autowired
    GetDow getDow;

    @SneakyThrows
    public String getInstructorDayTimeTable(int id, String date_str) {
        String day = getDow.getDowFromDate(date_str);
        if(day.equals("sunday")){
            return "sunday";
        }
        else if(day.equals("saturday")){
            return "saturday";
        }
        ResultSet resultset = dao.getInstructorDayTimeTable(id,day);


        HashMap<String, ArrayList<String>> daytimetable = new HashMap<>();
        while(resultset.next())
        {
            ArrayList<String> data = new ArrayList<>();
            //slot, class, section, location

            String slot = resultset.getString(1);
            data.add(String.valueOf(resultset.getInt(2)));
            data.add(resultset.getString(3));
            data.add(resultset.getString(4));
            daytimetable.put(slot,data);
        }

        if(daytimetable.isEmpty()){
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String jacksonData = objectMapper.writeValueAsString(daytimetable);

        return jacksonData;
    }

    @SneakyThrows
    public String getInstructorWeeklyTimeTable(int classId){
        ResultSet resultset = dao.getInstructorWeeklyTimeTable(classId);

        if(!resultset.next()){
            return null;
        }

        HashMap<String, HashMap<String, ArrayList<String>>> weektimetable = new HashMap<>();

        //week, slot, class, section, location

        while(resultset.next())
        {
            String week, slot;
            week = resultset.getString(1);
            slot = resultset.getString(2);

           
            ArrayList<String> data = new ArrayList<>();
            data.add(String.valueOf(resultset.getInt(3)));
            data.add(resultset.getString(4));
            data.add(resultset.getString(5));
            if(weektimetable.get(week) == null){
                HashMap<String, ArrayList<String>> inner_hm = new HashMap<>();
                inner_hm.put(slot,data);
                weektimetable.put(week, inner_hm);
            }
            else{
                HashMap<String, ArrayList<String>> inner_hm = weektimetable.get(week);
                inner_hm.put(slot, data);
                weektimetable.put(week,inner_hm);
            }
        }

        if(weektimetable.isEmpty()){
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String jacksonData = objectMapper.writeValueAsString(weektimetable);

        return jacksonData;
    }
}
