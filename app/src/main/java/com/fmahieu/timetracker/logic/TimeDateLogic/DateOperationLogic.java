package com.fmahieu.timetracker.logic.TimeDateLogic;

import com.fmahieu.timetracker.logger.Logger;

import java.text.DateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateOperationLogic {

    private final String TAG = "__DateOperationLogic";
    private Logger logger = new Logger();
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public DateOperationLogic() {
    }

    public int getCurrentYear(){
        return LocalDate.now().getYear();
    }

    public int getCurrentMonth(){
        return LocalDate.now().getMonthValue() - 1;
    }

    public int getCurrentDay(){
        return LocalDate.now().getDayOfMonth();
    }

    /**
     * Create date from integers
     * @param year
     * @param month
     * @param day
     * @return the date as a string
     */
    public String createDate(int year, int month, int day){
        LocalDate date = LocalDate.of(year, month, day);
        return date.format(dateFormat);
    }

    public String getCurrentDate(){
        return LocalDate.now().format(dateFormat);
    }

    public String getDateOneMonthAgo(){
        return LocalDate.now().minusMonths(1).format(dateFormat);
    }

    public boolean verifyDateFormat(String date){
        try{
            LocalDate.parse(date, dateFormat);
            return true;
        }
        catch (Exception e){
            logger.logException(TAG, "date could not be verified", e);
            return false;
        }
    }

    public long getNumberOfDaysBetweenDates(String date1, String date2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate d1 = LocalDate.parse(date1, formatter);
        LocalDate d2 = LocalDate.parse(date2, formatter);

        long numDays = Duration.between(d1.atStartOfDay(), d2.atStartOfDay()).toDays();
        return numDays;
    }
}
