package com.fmahieu.timetracker.database.DAO;

import android.content.Context;
import android.util.Log;

import com.fmahieu.timetracker.database.sqlite.TasksDao;
import com.fmahieu.timetracker.models.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Call the right functions from the database dao/handlers/repository
 * Handles basic functions, shouldn't have to change if sql technology/library is changed
 */
public class TaskDao {

    private final String TAG = "__TaskDao";
    private TasksDao tasksDao;

    public TaskDao(){
        tasksDao = new TasksDao();
    }

    public void addTask(Task task){
        tasksDao.addTask(task);
    }

    public List<Task> getAllTasks(){
        Log.i(TAG, "getting all tasks from database");
        try{
            return tasksDao.getAllTasks();
        }
        catch (Exception e){
            Log.i(TAG, "Error when getting all tasks from database: " + e.toString());
        }

        return new ArrayList<>();
    }
}
