package com.fmahieu.timetracker.logic;

import android.util.Log;

import com.fmahieu.timetracker.application.App;
import com.fmahieu.timetracker.database.DAO.TaskDao;
import com.fmahieu.timetracker.database.DAO.TimeDayDao;
import com.fmahieu.timetracker.database.sqlite.TasksSqliteDao;
import com.fmahieu.timetracker.database.sqlite.TimeDaysSqliteDao;
import com.fmahieu.timetracker.logic.TimeDateLogic.DateOperationLogic;
import com.fmahieu.timetracker.models.TimeDay;
import com.fmahieu.timetracker.models.singletons.StatsCache;
import com.fmahieu.timetracker.models.singletons.Tasks;

import java.util.List;

public class InitialSetupLogic {

    private final String TAG = "__InitialSetupLogic";

    private Tasks tasks;
    private TaskDao taskDao;

    public InitialSetupLogic(){
        tasks = Tasks.getInstance();
        taskDao = new TasksSqliteDao();
    }

    public void loadTasks(){
        Log.i(TAG, "loading tasks");
        tasks.loadTasks(taskDao.getAllTasks());
    }

    public void loadStatsCache(){
        DateOperationLogic dateOperationLogic = new DateOperationLogic();
        String from =  dateOperationLogic.getCurrentDate();
        String to = dateOperationLogic.getDateOneMonthAgo();

        TimeDayDao dao = new TimeDaysSqliteDao(App.getContext());
        List<TimeDay> timeDays = dao.getAllTimeDayBetweenDates(from, to);
        StatsCache.getInstance().setTimeDays(timeDays);
    }

    public void loadData(){
        loadTasks();
        loadStatsCache();
    }
}
