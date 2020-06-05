package com.fmahieu.timetracker.database.sqlite;

import android.util.Log;

import com.fmahieu.timetracker.logger.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateAdapter {

    private static final String TAG = "__DateAdapter";
    private Logger logger = new Logger();

    private DateTimeFormatter appFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private DateTimeFormatter databaseFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public String formatDateForDatabase(String date){
        try{
            LocalDate dateTime = LocalDate.parse(date, appFormat);
            dateTime.format(databaseFormat);
            return dateTime.toString();
        }
        catch (Exception e){
            logger.logException(TAG, "Error when converting date to database format: ", e);
            return null;
        }
    }

    public String formatDateForApp(String date){
        try{
            LocalDate dateTime = LocalDate.parse(date, databaseFormat);
            dateTime.format(appFormat); //TODO: error, date is not formatted
            return dateTime.toString();
        }
        catch (Exception e){
            logger.logException(TAG, "Error when converting date to app format: ", e);
            return null;
        }
    }
}
