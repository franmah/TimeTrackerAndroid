package com.fmahieu.timetracker.logic;

import android.util.Log;

import com.fmahieu.timetracker.application.App;
import com.fmahieu.timetracker.database.DAO.TaskDao;
import com.fmahieu.timetracker.database.DAO.TimeDayDao;
import com.fmahieu.timetracker.database.sqlite.TasksSqliteDao;
import com.fmahieu.timetracker.database.sqlite.TimeDaysSqliteDao;
import com.fmahieu.timetracker.logic.TimeDateLogic.DateTimeOperationLogic;
import com.fmahieu.timetracker.models.Task;
import com.fmahieu.timetracker.models.singletons.Tasks;

public class StopwatchLogic {

    private final String TAG = "__StopwatchLogic";

    private DateTimeOperationLogic timeOperationLogic;
    private TaskDao taskDao;
    private TimeDayDao timeDayDao;
    private Tasks tasks;

    public StopwatchLogic() {
        timeOperationLogic = new DateTimeOperationLogic();
        tasks = Tasks.getInstance();
        taskDao = new TasksSqliteDao();
        timeDayDao = new TimeDaysSqliteDao(App.getContext());
    }


    public void startStopwatchForTask(String taskName) {
        Log.i(TAG, "starting stopwatch for: " + taskName);

        String currentDateTime = timeOperationLogic.getCurrentDateTime();

        tasks.setStartTime(taskName, currentDateTime);
    }

    public void stopStopwatchForTask(String taskName) {
        Log.i(TAG, "stopping stopwatch for: " + taskName);

        // 1. update task in Tasks cache
        String endDateTime = timeOperationLogic.getCurrentDateTime();
        String initialDateTime = tasks.getStartTime(taskName);
        tasks.resetStartTime(taskName);

        // find how long the timer has been running
        String duration = timeOperationLogic.getDurationBetweenTwoDateTimes(initialDateTime, endDateTime);

        String totalTime = tasks.getTotalTime(taskName);
        String newTotalDuration = timeOperationLogic.sumDurations(totalTime, duration);
        tasks.setTotalTime(taskName, newTotalDuration);

        // 2. update database
        Task task = tasks.getTask(taskName);
        updateTaskDatabase(task);
        updateTimeDayDatabase(task);
    }

    private void updateTaskDatabase(Task task){
    }

    private void updateTimeDayDatabase(Task task){
        // TODO: pull previous time from TimeDay dao
        // TODO: update task
    }
}
