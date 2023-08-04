package com.targetindia.DigitizeTimeTable.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.targetindia.DigitizeTimeTable.repository.StudentDao;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class StudentService {
    @Autowired
    StudentDao dao;

    @Autowired
    GetDow getDow;

    @SneakyThrows
    public String getStudentDayTimeTable(int classId, String date_str) {
        String day = getDow.getDowFromDate(date_str);
        if(day.equals("sunday")){
            return "sunday";
        }
        else if(day.equals("saturday")){
            return "saturday";
        }
        ResultSet resultset = dao.getStudentDayTimeTable(classId,day);

        HashMap<String, ArrayList<String>> daytimetable = new HashMap<>();
        while(resultset.next())
        {
            ArrayList<String> data = new ArrayList<>();
            //slot, course_name, instructor_name, location

            String slot = resultset.getString(1);
            data.add(resultset.getString(2));
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
    public String getStudentWeeklyTimeTable(int classId){
        ResultSet resultset = dao.getStudentWeeklyTimeTable(classId);

        HashMap<String,HashMap<String, ArrayList<String>>> weektimetable = new HashMap<>();

        //week, slot, course_name, instructor_name, location

        while(resultset.next())
        {
            String week, slot;
            week = resultset.getString(1);
            slot = resultset.getString(2);
            ArrayList<String> data = new ArrayList<>();
            data.add(resultset.getString(3));
            data.add(resultset.getString(4));
            data.add(resultset.getString(5));
            if(weektimetable.get(week) == null){
                HashMap<String, ArrayList<String>> inner_hm = new HashMap<>();
                inner_hm.put(slot, data);
//                hm().add(inner_hm);
                weektimetable.put(week, inner_hm);
            }
            else{
                HashMap<String, ArrayList<String>> inner_hm = weektimetable.get(week);
                inner_hm.put(slot,data);
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

    //hashmap<dow ,slot, instructor_name, course_name, location> > >
//    @SneakyThrows
//    public HashMap<Integer, ArrayList<String>> get_student_table_weekly(int classId){
//        ResultSet resultset = dao.get_student_time_table_weekly(classId);
//        HashMap<Integer, ArrayList<String>> hm = new HashMap<>();
//
//        //week, slot, course_name, instructor_name, location
//        int i=0;
//        while(resultset.next())
//        {
//            i++;
//            ArrayList<String> data = new ArrayList<>();
//
//            data.add(resultset.getString(1));
//            data.add(resultset.getString(2));
//            data.add(resultset.getString(3));
//            data.add(resultset.getString(4));
//            data.add(resultset.getString(5));
//            hm.put(i,data);
//        }
//        return hm;
//    }

}
