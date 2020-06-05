package com.fmahieu.timetracker.presenters;

import android.util.Log;

import com.fmahieu.timetracker.logic.TaskLogic;
import com.fmahieu.timetracker.logic.TimeDateLogic.DateTimeOperationLogic;
import com.fmahieu.timetracker.models.TimeHolder;
import com.fmahieu.timetracker.models.singletons.Tasks;

public class TaskHolderPresenter {

    // TODO: replace tasks by a call to logic layer
    private Tasks tasks = Tasks.getInstance();
    private DateTimeOperationLogic timeOperation = new DateTimeOperationLogic();

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
        String time = timeOperation.getCurrentDateTime();
        TimeHolder timeHolder = timeOperation.convertTimeToTimeHolder(time);
        return convertTimeToMilliseconds(timeHolder);
    }

    public long convertTimeToMilliseconds(TimeHolder timeHolder) {
        return timeHolder.getHours() * 3600000 +
                timeHolder.getMinutes() * 60000 +
                timeHolder.getSeconds() * 1000;

    }


    public void deleteTask(String taskName) {
        Log.i("__TaskHolderPresenter", "deleting task: " + taskName);
        new TaskLogic().deleteTask(taskName);
    }
}
