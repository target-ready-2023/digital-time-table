package com.target_india.digitize_time_table.service;
import com.target_india.digitize_time_table.exceptions.InvalidDateException;
import org.springframework.stereotype.Service;

@Service
public class DateValidationService {

    public void validateDate(int day, int month, int year){
        int numOfDays = getDaysOfMonth(month, year);

        if(day > numOfDays || day < 1){
            throw new InvalidDateException("Invalid day provided");
        }
        else if(month > 12 || month < 1){
            throw new InvalidDateException("Invalid month provided");
        }
    }

    private int getDaysOfMonth(int month, int year) {
        switch(month){
            case 1,3,5,7,8,10,12:
                return 31;
            case 2:
                return isLeap(year)?29:28;
            default:
                return 30;
        }
    }

    private boolean isLeap(int year) {
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    }
}