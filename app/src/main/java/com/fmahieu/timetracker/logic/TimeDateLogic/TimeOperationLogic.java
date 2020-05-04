package com.fmahieu.timetracker.logic.TimeDateLogic;

import android.util.Log;

import com.fmahieu.timetracker.models.TimeHolder;

import java.time.Duration;
import java.time.LocalTime;

/**
 * Module handling time. This class uses java's LocalTime and Duration classes
 * Handles null string errors
 */
public class TimeOperationLogic {
    private final String TAG = "__TimeOperationLogic";

    // TODO: create another logic class for Duration
    public TimeOperationLogic(){}

    public String getCurrentTime(){
        return LocalTime.now().toString();
    }

    public String getDefaultDuration(){
        Duration duration = Duration.ZERO;
        return duration.toString();
    }

    /**
     * Get the duration between two LocalTime
     * @param beginTimeStr first time as a string
     * @param endTimeStr second time as a string
     * @return the duration between two times, not formatted for storage.
     */
    public String getDurationBetweenTwoTimes(String beginTimeStr, String endTimeStr){
        if(beginTimeStr == null || endTimeStr == null)
            return Duration.ZERO.toString();

        LocalTime beginTime = LocalTime.parse(beginTimeStr);
        LocalTime endTime = LocalTime.parse(endTimeStr);
        return Duration.between(beginTime, endTime).toString();
    }

    /**
     * Add a duration to a LocalTime
     * @param originalDurationStr a duration in Duration format (not readable)
     * @param durationStr a duration in Duration format (not readable)
     * @return
     */
    public String sumDurations(String originalDurationStr, String durationStr){
        Duration originalDuration = originalDurationStr == null ? Duration.ZERO : Duration.parse(originalDurationStr);
        Duration duration = durationStr == null ? Duration.ZERO : Duration.parse(durationStr);
        return originalDuration.plus(duration).toString();
    }

    /**
     * Format duration into human readable string
     * @param durationStr a duration not formatted as a readable string
     * @return a duration not formatted as readable that can be stored
     */
    public String convertDurationToReadableString(String durationStr){
        if(durationStr == null)
            return null;

        Duration duration = Duration.parse(durationStr);

        long hours = duration.toHours();
        duration = duration.minusHours(hours);
        long minutes = duration.toMinutes();
        duration = duration.minusMinutes(minutes);
        long seconds = duration.getSeconds();

        return hours + ":" + minutes + ":" + seconds;
    }

    /**
     *
     * @param durationStr a duration as Duration (not formatted to be readable)
     * @return
     */
    public long getDurationAsSeconds(String durationStr){
        if(durationStr == null){
            return 0;
        }

        Duration duration = Duration.parse(durationStr);
        return duration.getSeconds();
    }


    /**
     * Convert a duration formatted to be stored/read into a Duration to be used for calculation
     * @param durationStr human readable duration: hours:minutes:seconds:milliseconds
     * @return the duration as String in Duration format (not readable)
     */
    public String convertStringToDuration(String durationStr) {
        try{
            String[] elements = durationStr.split(":");
            long hours = Long.parseLong(elements[0]);
            long minutes = Long.parseLong(elements[1]);
            long seconds = Long.parseLong(elements[2]);
            long millis = 0;
            if(elements.length == 4){
                millis = Long.parseLong(elements[3]);
            }

            Duration duration = Duration.ZERO;
            duration = duration.plusHours(hours).plusMinutes(minutes).plusSeconds(seconds).plusMillis(millis);
            Log.i(TAG, "__TESTING: convert duration: " + duration.toString());
            return duration.toString();
        }
        catch (Exception e){
            Log.e(TAG, "error while parsing readable duration to Duration" ,e);
            return null;
        }
    }

    public TimeHolder convertTimeToTimeHolder(String time){
        int hours = getHoursFromTime(time);
        int minutes = getMinutesFromTime(time);
        int seconds = getSecondsFromTime(time);
        return new TimeHolder(hours, minutes, seconds);
    }


    public int getHoursFromTime(String time){
        return LocalTime.parse(time).getHour();
    }

    public int getMinutesFromTime(String time){
        return LocalTime.parse(time).getMinute();
    }

    public int getSecondsFromTime(String time){
        return LocalTime.parse(time).getSecond();
    }
}
