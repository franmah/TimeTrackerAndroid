package com.fmahieu.timetracker.logic.TimeDateLogic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateOperationLogic {

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
}
