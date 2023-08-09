package com.target_india.digitize_time_table.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.target_india.digitize_time_table.repository.TimeTableDao;
import com.target_india.digitize_time_table.utils.DayOfWeekConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@Service
public class TimeTableService {
    private final TimeTableDao timeTableDao;

    private final DayOfWeekConverter dayOfWeekConverter;

    @Autowired
    public TimeTableService(TimeTableDao timeTableDao, DayOfWeekConverter dayOfWeekConverter)
    {
        this.timeTableDao =timeTableDao;
        this.dayOfWeekConverter=dayOfWeekConverter;
    }


    public Optional getInstructorDayTimeTable(int id, String dateString) {
        try{
            String day = dayOfWeekConverter.getDowFromDate(dateString);
            ResultSet resultSet = timeTableDao.getInstructorDayTimeTable(id,day);
            HashMap<String, ArrayList<String>> dayTimeTable = new HashMap<>();
            while(resultSet.next())
            {
                ArrayList<String> data = new ArrayList<>();
                String slot = resultSet.getString(1);
                data.add(String.valueOf(resultSet.getInt(2)));
                data.add(resultSet.getString(3));
                data.add(resultSet.getString(4));
                dayTimeTable.put(slot,data);
            }

            if(dayTimeTable.isEmpty()){
                return Optional.empty();
            }

            ObjectMapper objectMapper = new ObjectMapper();
            String jacksonData = objectMapper.writeValueAsString(dayTimeTable);

            return Optional.of(jacksonData);
        }
        catch (Exception exception){
            return Optional.empty();
        }
    }


    public Optional getInstructorWeeklyTimeTable(int classId){
        try{
            ResultSet resultSet = timeTableDao.getInstructorWeeklyTimeTable(classId);

            if(!resultSet.next()){
                return Optional.empty();
            }

            HashMap<String, HashMap<String, ArrayList<String>>> weekTimeTable = new HashMap<>();
            while(resultSet.next())
            {
                String week, slot;
                week = resultSet.getString(1);
                slot = resultSet.getString(2);
                ArrayList<String> data = new ArrayList<>();
                data.add(String.valueOf(resultSet.getInt(3)));
                data.add(resultSet.getString(4));
                data.add(resultSet.getString(5));
                if(weekTimeTable.get(week) == null){
                    HashMap<String, ArrayList<String>> slots = new HashMap<>();
                    slots.put(slot,data);
                    weekTimeTable.put(week, slots);
                }
                else{
                    HashMap<String, ArrayList<String>> slots = weekTimeTable.get(week);
                    slots.put(slot, data);
                    weekTimeTable.put(week,slots);
                }
            }

            if(weekTimeTable.isEmpty()){
                return Optional.empty();
            }

            ObjectMapper objectMapper = new ObjectMapper();
            String jacksonData = objectMapper.writeValueAsString(weekTimeTable);

            return Optional.of(jacksonData);
        }
        catch (Exception exception){
            return Optional.empty();
        }
    }
    public Optional getStudentDayTimeTable(int classId, String dateString) {
        try {
            String day = dayOfWeekConverter.getDowFromDate(dateString);
            ResultSet resultSet = timeTableDao.getStudentDayTimeTable(classId, day);

            HashMap<String, ArrayList<String>> dayTimeTable = new HashMap<>();
            while (resultSet.next()) {
                ArrayList<String> data = new ArrayList<>();
                String slot = resultSet.getString(1);
                data.add(resultSet.getString(2));
                data.add(resultSet.getString(3));
                data.add(resultSet.getString(4));
                dayTimeTable.put(slot, data);
            }
            if (dayTimeTable.isEmpty()) {
                return Optional.empty();
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String jacksonData = objectMapper.writeValueAsString(dayTimeTable);
            return Optional.of(jacksonData);
        } catch (Exception exception) {
            return Optional.empty();
        }

    }


    public Optional getStudentWeeklyTimeTable(int classId) {
        try{
            ResultSet resultSet = timeTableDao.getStudentWeeklyTimeTable(classId);
            HashMap<String, HashMap<String, ArrayList<String>>> weekTimeTable = new HashMap<>();

            while (resultSet.next()) {
                String week, slot;
                week = resultSet.getString(1);
                slot = resultSet.getString(2);
                ArrayList<String> data = new ArrayList<>();
                data.add(resultSet.getString(3));
                data.add(resultSet.getString(4));
                data.add(resultSet.getString(5));
                if (weekTimeTable.get(week) == null) {
                    HashMap<String, ArrayList<String>> slots = new HashMap<>();
                    slots.put(slot, data);
                    weekTimeTable.put(week, slots);
                } else {
                    HashMap<String, ArrayList<String>> slots = weekTimeTable.get(week);
                    slots.put(slot, data);
                    weekTimeTable.put(week, slots);
                }
            }

            if (weekTimeTable.isEmpty()) {
                return Optional.empty();
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String jacksonData = objectMapper.writeValueAsString(weekTimeTable);
            return Optional.of(jacksonData);
        }
        catch(Exception exception){
            return Optional.empty();
        }
    }
}
