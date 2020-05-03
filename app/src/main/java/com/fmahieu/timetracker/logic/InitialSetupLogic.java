package com.fmahieu.timetracker.logic;

import android.util.Log;

import com.fmahieu.timetracker.database.DAO.TaskDao;
import com.fmahieu.timetracker.models.singletons.Tasks;

public class InitialSetupLogic {

    private final String TAG = "__InitialSetupLogic";

    private Tasks tasks;
    private TaskDao taskDao;

    public InitialSetupLogic(){
        tasks = Tasks.getInstance();
        taskDao = new TaskDao();
    }

    public void loadTasks(){
        Log.i(TAG, "loading tasks");
        tasks.loadTasks(taskDao.getAllTasks());
    }
}
