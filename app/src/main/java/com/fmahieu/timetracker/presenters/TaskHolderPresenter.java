package com.fmahieu.timetracker.presenters;

import com.fmahieu.timetracker.logic.TimeDateLogic.TimeOperationLogic;
import com.fmahieu.timetracker.models.TimeHolder;
import com.fmahieu.timetracker.models.singletons.Tasks;

public class TaskHolderPresenter {

    private Tasks tasks = Tasks.getInstance();
    private TimeOperationLogic timeOperation = new TimeOperationLogic();

    public TaskHolderPresenter(){}

    public TimeHolder getStartTimeForTask(String taskName){
        if(tasks.isTaskRunning(taskName)){
            String startTime = tasks.getStartTime(taskName);
            return timeOperation.convertTimeToTimeHolder(startTime);
        }
        else{
            return null;
        }
    }

    public long getCurrentTimeInMilliseconds(){
        String time = timeOperation.getCurrentTime();
        TimeHolder timeHolder = timeOperation.convertTimeToTimeHolder(time);
        return convertTimeToMilliseconds(timeHolder);
    }

    public long convertTimeToMilliseconds(TimeHolder timeHolder) {
        return timeHolder.getHours() * 3600000 +
                timeHolder.getMinutes() * 60000 +
                timeHolder.getSeconds() * 1000;

    }


}
