package com.fmahieu.timetracker.logic;

import com.fmahieu.timetracker.TimeDateLogic.TimeOperationLogic;

/**
 * Turn tasks' attributes into human readable format
 */
public class DisplayTaskLogic {

    private TimeOperationLogic timeOperationLogic;

    public DisplayTaskLogic(){
        this.timeOperationLogic = new TimeOperationLogic();
    }

    public long getTotalTimeAsSeconds(String durationStr){
        return timeOperationLogic.getSecondsFromWithinDuration(durationStr);
    }

}
