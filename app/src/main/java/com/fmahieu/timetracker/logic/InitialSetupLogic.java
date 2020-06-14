package com.fmahieu.timetracker.logic;

import android.util.Log;

import com.fmahieu.timetracker.database.DAO.TaskDao;
import com.fmahieu.timetracker.database.sqlite.TasksSqliteDao;
import com.fmahieu.timetracker.logger.Logger;
import com.fmahieu.timetracker.models.singletons.Tasks;

public class InitialSetupLogic {

    private final String TAG = "__InitialSetupLogic";

    private Tasks tasks;
    private TaskDao taskDao;
    private Logger logger = new Logger();

    public InitialSetupLogic(){
        try {
            tasks = Tasks.getInstance();
            taskDao = new TasksSqliteDao();
        }
        catch (Exception err) {
            logger.logException(TAG, "error in constructor", err);
        }
    }

    public String loadTasks(){
        try {
            logger.logMessage(TAG, "pre-loading tasks");
            tasks.loadTasks(taskDao.getAllTasks());
            return null;
        }
        catch (Exception err) {
            logger.logException(TAG, "Error when pre-loading tasks", err);
            return "error when loading activities";
        }
    }

    public String loadData(){
        return loadTasks();
    }
}
