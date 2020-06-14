package com.fmahieu.timetracker.logic;

import com.fmahieu.timetracker.application.App;
import com.fmahieu.timetracker.database.DAO.TaskDao;
import com.fmahieu.timetracker.database.DAO.TimeDayDao;
import com.fmahieu.timetracker.database.sqlite.TasksSqliteDao;
import com.fmahieu.timetracker.database.sqlite.TimeDaysSqliteDao;
import com.fmahieu.timetracker.logger.Logger;
import com.fmahieu.timetracker.logic.TimeDateLogic.DateOperationLogic;
import com.fmahieu.timetracker.logic.TimeDateLogic.DateTimeOperationLogic;
import com.fmahieu.timetracker.models.Task;
import com.fmahieu.timetracker.models.TimeDay;
import com.fmahieu.timetracker.models.singletons.Tasks;

public class StopwatchLogic {

    private final String TAG = "__StopwatchLogic";
    private Logger logger = new Logger();

    private DateTimeOperationLogic timeOperationLogic;
    private TaskDao taskDao;
    private TimeDayDao timeDayDao;
    private Tasks tasks;

    public StopwatchLogic() {
        try {
            timeOperationLogic = new DateTimeOperationLogic();
            tasks = Tasks.getInstance();
            taskDao = new TasksSqliteDao();
            timeDayDao = new TimeDaysSqliteDao(App.getContext());
        }
        catch (Exception err) {
            logger.logException(TAG, "error in constructor", err);
        }
    }


    public String startStopwatchForTask(String taskName) {
        try {
            logger.logMessage(TAG, "starting stopwatch for: " + taskName);
            String currentDateTime = timeOperationLogic.getCurrentDateTime();
            tasks.setStartTime(taskName, currentDateTime);
            return null;
        }
        catch (Exception err) {
            logger.logException(TAG, "error when starting stopwatch", err);
            return "error when starting stopwatch";
        }
    }

    public String stopStopwatchForTask(String taskName) {
        try {
            logger.logMessage(TAG, "stopping stopwatch for: " + taskName);
            // 1. update task in Tasks cache
            String endDateTime = timeOperationLogic.getCurrentDateTime();
            String initialDateTime = tasks.getStartTime(taskName);
            tasks.resetStartTime(taskName);

            // find how long the timer has been running
            String duration = timeOperationLogic.getDurationBetweenTwoDateTimes(initialDateTime, endDateTime);
            logger.logDebug(TAG, "timer has been running for duration for: " + duration);

            // update task's total duration
            String totalTime = tasks.getTotalTime(taskName);
            logger.logDebug(TAG, "previous total time: " + totalTime);
            String newTotalDuration = timeOperationLogic.sumDurations(totalTime, duration);
            logger.logDebug(TAG, "new duration: " + newTotalDuration);
            tasks.setTotalTime(taskName, newTotalDuration);

            // 2. update database
            Task task = tasks.getTask(taskName);
            if(task == null) {
                logger.logTest(TAG, "task is null");
            }
            updateTaskDatabase(task);
            updateTimeDayDatabase(taskName, duration);

            return null;
        }
        catch (Exception err) {
            logger.logException(TAG, "error stopping stopwatch", err);
            return "Error while trying to stop stopwatch";
        }
    }

    private String updateTaskDatabase(Task task){
        try {
            taskDao.updateTask(task);
            return null;
        }
        catch (Exception err) {
            logger.logException(TAG, "error when updating database", err);
            return "error during update";
        }
    }

    private String updateTimeDayDatabase(String taskName, String duration){
        try {
            long seconds = timeOperationLogic.getDurationAsSeconds(duration);
            String date = new DateOperationLogic().getCurrentDate();
            timeDayDao.addTimeDayEntry(new TimeDay(date, taskName, seconds));
            return null;
        }
        catch (Exception err) {
            logger.logException(TAG, "error when updating time day db", err);
            return "error during update";
        }
    }
}
