package com.fmahieu.timetracker.logic;

public class DisplayTaskLogic {

    private TimeOperationLogic timeOperationLogic;

    public DisplayTaskLogic(){
        this.timeOperationLogic = new TimeOperationLogic();
    }

    public long getTotalTimeAsSeconds(String durationStr){
        return timeOperationLogic.getSecondsInDuration(durationStr);
    }

}
