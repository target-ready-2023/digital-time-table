package com.target_india.digitize_time_table.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.target_india.digitize_time_table.exceptions.ResourceNotFoundException;
import com.target_india.digitize_time_table.model.TimeTable;
import com.target_india.digitize_time_table.repository.TimeTableDao;
import com.target_india.digitize_time_table.utils.DayOfWeekConverter;
import org.hibernate.loader.ast.spi.SqlArrayMultiKeyLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.*;

import static org.hibernate.internal.util.collections.ArrayHelper.indexOf;

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


    public List<TimeTable> getInstructorDayTimeTable(int id, String dateString) {
        String day = null;
        try {
            day = dayOfWeekConverter.getDowFromDate(dateString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ResultSet resultSet = timeTableDao.getInstructorDayTimeTable(id,day);
        return getTimeTableDataList(resultSet);
    }
    public List<TimeTable> getInstructorWeeklyTimeTable(int id){
        ResultSet resultSet = timeTableDao.getInstructorWeeklyTimeTable(id);
        return getTimeTableDataList(resultSet);
    }
    public List<TimeTable> getStudentDayTimeTable(int classId, String dateString) {
        String day = null;
        try {
            day = dayOfWeekConverter.getDowFromDate(dateString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ResultSet resultSet = timeTableDao.getStudentDayTimeTable(classId, day);

        return getTimeTableDataList(resultSet);
    }

    public List<TimeTable> getStudentWeeklyTimeTable(int classId) {
        ResultSet resultSet = timeTableDao.getStudentWeeklyTimeTable(classId);
        return getTimeTableDataList(resultSet);
    }
    public List<TimeTable> sortTimeTable(List<TimeTable> result){

        Comparator<TimeTable> customComparator = (a, b) -> {
            // Order by week
            String[] weekOrder = { "monday", "tuesday", "wednesday", "thursday", "friday" };
            int weekAIndex = indexOf(weekOrder, a.getWeek());
            int weekBIndex = indexOf(weekOrder, b.getWeek());

            if (weekAIndex != weekBIndex) {
                return Integer.compare(weekAIndex, weekBIndex);
            }

            // Order by slot
            String[] slotOrder = {
                    "9:00am-10:00am", "10:00am-11:00am", "11:00am-12:00pm",
                    "1:00pm-2:00pm", "2:00pm-3:00pm", "3:00pm-4:00pm", "4:00pm-5:00pm"
            };

            return Integer.compare(indexOf(slotOrder, a.getSlot()), indexOf(slotOrder, b.getSlot()));
        };

        // Sort the schedules using the custom comparator
        Collections.sort(result, customComparator);
        return result;
    }
    public List<TimeTable> getTimeTableDataList(ResultSet resultSet){
        List<TimeTable> dayTimeTable = new ArrayList<>();
        try{
            while(resultSet.next())
            {
                String week=resultSet.getString(1);
                String slot=resultSet.getString(2);
                int classId=resultSet.getInt(3);
                int className=resultSet.getInt(4);
                String section=resultSet.getString(5);
                int courseId=resultSet.getInt(6);
                String courseName=resultSet.getString(7);
                int instructorId=resultSet.getInt(8);
                String instructorName=resultSet.getString(9);
                String location=resultSet.getString(10);
                TimeTable timeTable = new TimeTable(week,slot,classId,className,section,
                        courseId,courseName,instructorId,instructorName,location);
                dayTimeTable.add(timeTable);
            }
            if(dayTimeTable.isEmpty()){
                System.out.println("hellooooBindu");
                throw new ResourceNotFoundException("No data found");
            }
            dayTimeTable=sortTimeTable(dayTimeTable);
            return dayTimeTable;
        }
        catch (SQLException exception){
            throw new ResourceNotFoundException("No data found");
        }
    }
    public String deleteTimeTableById(int classId,String week, String slot) {
        return timeTableDao.deleteTimeTableById(classId,week,slot);
    }
    public String updateTimeTableSlot(int classId,String week,String slot, String subject,String location){
        return timeTableDao.updateTimeTableSlot(classId, week, slot,subject,location);
    }
}