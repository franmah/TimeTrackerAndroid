package com.fmahieu.timetracker;

import com.fmahieu.timetracker.TimeDateLogic.TimeOperationLogic;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalTime;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testTime(){
        LocalTime initial = LocalTime.of(6,30, 40);
        LocalTime end = LocalTime.of(7,0,0);

        Duration duration = Duration.between(initial, end);

        long durationAsHours = duration.toHours();
        long durationAsMinutes = duration.toMinutes();
        long durationAsSeconds = duration.getSeconds();

        System.out.println("hours: " + durationAsHours + ", minutes: " + durationAsMinutes + ", seconds: " + durationAsSeconds + ", duration: " + duration.toString());

        long hours = duration.toHours();
        duration = duration.minusHours(hours);
        long minutes = duration.toMinutes();
        duration = duration.minusMinutes(minutes);
        long seconds = duration.getSeconds();

        System.out.println(hours + ":" + minutes + ":" + seconds);
    }

    @Test
    public void TimeAsString(){
        String time = LocalTime.now().toString();
        System.out.println("time as string: " + time);
        LocalTime localTime = LocalTime.parse(time);
        localTime = localTime.plusHours(1);

        System.out.println("local time from string + 1 hour: " + localTime.toString());
    }

    @Test
    public void TestDurationAddition(){
        TimeOperationLogic timeOperationLogic = new TimeOperationLogic();
        String currentTime = timeOperationLogic.getCurrentTime();
        String timePlusHour = LocalTime.parse(currentTime).plusHours(1).toString();

        System.out.println("currentTime: " + currentTime + ", current plus an hour: " + timePlusHour);

        String duration = timeOperationLogic.getDurationTime(currentTime, timePlusHour);
        String zeroDuration = Duration.ZERO.toString();

        String newTotal = timeOperationLogic.addDurationToTime(zeroDuration, duration);

        System.out.println(timeOperationLogic.showDurationAsString(newTotal));


    }

}