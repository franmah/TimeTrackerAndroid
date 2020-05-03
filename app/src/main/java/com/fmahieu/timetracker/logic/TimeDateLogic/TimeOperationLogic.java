package com.fmahieu.timetracker.logic.TimeDateLogic;

import java.time.Duration;
import java.time.LocalTime;

/**
 * Module handling time. This class uses java's LocalTime and Duration classes
 * Handles null string errors
 */
public class TimeOperationLogic {

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
     * @return
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
     * @param originalDurationStr
     * @param durationStr
     * @return
     */
    public String sumDurations(String originalDurationStr, String durationStr){
        Duration originalDuration = originalDurationStr == null ? Duration.ZERO : Duration.parse(originalDurationStr);
        Duration duration = durationStr == null ? Duration.ZERO : Duration.parse(durationStr);
        return originalDuration.plus(duration).toString();
    }

    /**
     * Format duration into human readable string
     * @param durationStr
     * @return
     */
    public String showDurationAsString(String durationStr){
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

    public long getDurationAsSeconds(String durationStr){
        if(durationStr == null){
            return 0;
        }

        Duration duration = Duration.parse(durationStr);
        return duration.getSeconds();
    }

}
