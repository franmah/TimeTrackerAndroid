package com.fmahieu.timetracker;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.fmahieu.timetracker.database.sqlite.TimeDaysSqliteDao;
import com.fmahieu.timetracker.logic.TimeDateLogic.DateOperationLogic;
import com.fmahieu.timetracker.models.TimeDay;

import org.junit.Test;

import static org.junit.Assert.*;


public class TimeDayDatabaseTest {

    @Test
    public void checkIfTotalTimeUpdates(){
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        String taskName = "testJunitUpdate";
        String date = new DateOperationLogic().getCurrentDate();
        long totalTime = 1000;

        TimeDay timeDay = new TimeDay(date, taskName, totalTime);

        TimeDaysSqliteDao dao = new TimeDaysSqliteDao(context);
        dao.addTimeDayEntry(timeDay);

        TimeDay result = dao.getTimeDayForTaskForToday(taskName);
        long testTotalTime = result.getTotalTime();

        assertEquals(testTotalTime, totalTime);

        totalTime = 2000;

        TimeDay updatedTimeDay = new TimeDay(date, taskName, totalTime);
        dao.addTimeDayEntry(updatedTimeDay);

        result = dao.getTimeDayForTaskForToday(taskName);
        testTotalTime = result.getTotalTime();

        assertEquals(testTotalTime, totalTime);


    }
}
