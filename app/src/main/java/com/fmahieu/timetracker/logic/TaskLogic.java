package com.fmahieu.timetracker.logic;

import android.util.Log;

import com.fmahieu.timetracker.application.App;
import com.fmahieu.timetracker.database.DAO.TaskDao;
import com.fmahieu.timetracker.database.DAO.TimeDayDao;
import com.fmahieu.timetracker.database.sqlite.TasksSqliteDao;
import com.fmahieu.timetracker.database.sqlite.TimeDaysSqliteDao;
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

    public TaskLogic(){
        this.timeOperationLogic = new DateTimeOperationLogic();
        this.dateOperationLogic = new DateOperationLogic();
        this.taskDao = new TasksSqliteDao();
        this.tasks = Tasks.getInstance();
    }

    public void addTask(String newTaskName) {
        Log.i(TAG, "adding task: " + newTaskName);
        String dateCreated = dateOperationLogic.getCurrentDate();
        String initialTotalDuration = timeOperationLogic.getDefaultDuration();

        Task task = new Task(newTaskName, dateCreated, initialTotalDuration);
        this.tasks.addTask(task);
        taskDao.addTask(task);
    }

    public boolean doesTaskExist(String taskName){
        return tasks.doesTaskExist(taskName);
    }

    public List<String> getTasksNames() {
        return this.tasks.getTaskNames();
    }

    public int getNumberOfTasks() {
        return this.tasks.getNumberOfTasks();
    }

    public String getTaskTotalDurationAsReadableString(String taskName){
        return tasks.getTotalTime(taskName);
    }

    public void deleteTask(String taskName){
        taskDao.deleteTask(taskName);
        new TimeDaysSqliteDao(App.getContext()).deleteTimeDay(taskName);
        tasks.removeTask(taskName);
    }


}
