package com.fmahieu.timetracker.database.DAO;

import com.fmahieu.timetracker.models.TimeDay;

import java.util.List;

public interface TimeDayDao {

    public void addTimeDayEntry(TimeDay timeDay);

    public void deleteTimeDayAtDate(String taskName, String date);

    public TimeDay getTimeDayForTaskForToday(String taskName);

    public TimeDay getTimeDayForTaskBetweenDates(String taskName, String from, String to);

    public List<TimeDay> getAllTimeDayBetweenDates(String from, String to);
}
