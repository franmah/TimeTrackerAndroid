package com.fmahieu.timetracker.logic.TimeDateLogic;

import android.util.Log;

import com.fmahieu.timetracker.logger.Logger;
import com.fmahieu.timetracker.models.TimeHolder;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Module handling time. This class uses java's LocalDateTime and Duration classes
 * Handles null string errors
 */
public class DateTimeOperationLogic {
    private final String TAG = "__TimeOperationLogic";
    private Logger logger = new Logger();

    public DateTimeOperationLogic(){}

    public String getCurrentDateTime(){
        return LocalDateTime.now().toString();
    }

    public String getDefaultDuration(){
        Duration duration = Duration.ZERO;
        return duration.toString();
    }

    /**
     * Get the duration between two LocalDateTime
     * @param beginTimeStr first time as a string
     * @param endTimeStr second time as a string
     * @return the duration between two times, not formatted for storage.
     */
    public String getDurationBetweenTwoDateTimes(String beginTimeStr, String endTimeStr){
        if(beginTimeStr == null || endTimeStr == null)
            return Duration.ZERO.toString();

        LocalDateTime beginTime = LocalDateTime.parse(beginTimeStr);
        LocalDateTime endTime = LocalDateTime.parse(endTimeStr);
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
        logger.logDebug(TAG, "duration: " + durationStr);
        Duration duration = Duration.parse(durationStr);

        long hours = duration.toHours();
        duration = duration.minusHours(hours);
        long minutes = duration.toMinutes();
        duration = duration.minusMinutes(minutes);
        long seconds = duration.getSeconds();

        // format to be xx:xx:xx rather than x:x:x
        String hoursStr = hours < 10 ? "0" + hours : Long.toString(hours);
        String minutesStr = minutes < 10 ? "0" + minutes : Long.toString(minutes);
        String secondsStr = seconds < 10 ? "0" + seconds : Long.toString(seconds);

        return hoursStr + ":" + minutesStr + ":" + secondsStr;
    }

    /**
     * Convert a duration into seconds.
     * Does not return the seconds part of the duration.
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
     * Parse a time as string to a TimeHolder.
     * @param time as a string
     * @return TimeHolder
     */
    public TimeHolder convertTimeToTimeHolder(String time){
        int hours = getHoursFromTime(time);
        int minutes = getMinutesFromTime(time);
        int seconds = getSecondsFromTime(time);
        return new TimeHolder(hours, minutes, seconds);
    }

    private int getHoursFromTime(String time){
        return LocalDateTime.parse(time).getHour();
    }

    private int getMinutesFromTime(String time){
        return LocalDateTime.parse(time).getMinute();
    }

    private int getSecondsFromTime(String time){
        return LocalDateTime.parse(time).getSecond();
    }
}
