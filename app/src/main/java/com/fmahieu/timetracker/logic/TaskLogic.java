package com.fmahieu.timetracker.logic;

import android.util.Log;

import com.fmahieu.timetracker.application.App;
import com.fmahieu.timetracker.database.DAO.TaskDao;
import com.fmahieu.timetracker.database.DAO.TimeDayDao;
import com.fmahieu.timetracker.database.sqlite.TasksSqliteDao;
import com.fmahieu.timetracker.database.sqlite.TimeDaysSqliteDao;
import com.fmahieu.timetracker.logger.Logger;
import com.fmahieu.timetracker.logic.TimeDateLogic.DateOperationLogic;
import com.fmahieu.timetracker.models.Task;
import com.fmahieu.timetracker.models.singletons.Tasks;

import java.util.List;

import com.fmahieu.timetracker.logic.TimeDateLogic.DateTimeOperationLogic;

/**
 * Handles logic to manipulate tasks (called activities within app)
 */
public class TaskLogic {
    final private String TAG = "__TaskLogic";

    private DateTimeOperationLogic timeOperationLogic;
    private DateOperationLogic dateOperationLogic;
    private Tasks tasks;
    private TaskDao taskDao;
    private Logger logger = new Logger();

    public TaskLogic(){
        try {
            this.timeOperationLogic = new DateTimeOperationLogic();
            this.dateOperationLogic = new DateOperationLogic();
            this.taskDao = new TasksSqliteDao();
            this.tasks = Tasks.getInstance();
        }
        catch (Exception err) {
            logger.logException(TAG, "error in constructor", err);
        }
    }

    public String addTask(String newTaskName) {
        try {
            Log.i(TAG, "adding task: " + newTaskName);
            String dateCreated = dateOperationLogic.getCurrentDate();
            String initialTotalDuration = timeOperationLogic.getDefaultDuration();

            Task task = new Task(newTaskName, dateCreated, initialTotalDuration);
            this.tasks.addTask(task);
            taskDao.addTask(task);
            return null;
        }
        catch (Exception err) {
            logger.logException(TAG, "error adding task", err);
            return "Error while adding task";
        }
    }

    public boolean doesTaskExist(String taskName){
        try {
            return tasks.doesTaskExist(taskName);
        }
        catch (Exception err) {
            logger.logException(TAG, "error checking if task exists", err);
            return false;
        }
    }

    public List<String> getTasksNames() {
        try {
            return this.tasks.getTaskNames();
        }
        catch (Exception err) {
            logger.logException(TAG, "error get task names", err);
            return null;
        }
    }

    public int getNumberOfTasks() {
        try {
            return this.tasks.getNumberOfTasks();
        }
        catch (Exception err) {
            logger.logException(TAG, "error get number of tasks", err);
            return -1;
        }
    }

    public String deleteTask(String taskName){
        try {
            taskDao.deleteTask(taskName);
            new TimeDaysSqliteDao(App.getContext()).deleteTimeDay(taskName);
            tasks.removeTask(taskName);
            new TimeDaysSqliteDao(App.getContext()).deleteTimeDay(taskName);
            return null;
        }
        catch (Exception err) {
            logger.logException(TAG, "error deleting task: " +taskName, err);
            return "An error occurred while deleting the activity";
        }
    }

    public String editTaskName(String oldName, String newName) {
        try {
            Tasks.getInstance().changeTaskName(oldName, newName);
            new TimeDaysSqliteDao(App.getContext()).changeTaskName(oldName, newName);
            new TasksSqliteDao().updateTaskName(oldName, newName);
             return null;
        }
        catch (Exception err) {
            logger.logException(TAG, "error when editing task name for task: " + oldName, err);
            return "An error occurred while renaming the activity";
        }
    }
}
