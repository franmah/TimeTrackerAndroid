package com.fmahieu.timetracker.logic;

import com.fmahieu.timetracker.application.App;
import com.fmahieu.timetracker.database.DAO.TimeDayDao;
import com.fmahieu.timetracker.database.sqlite.TimeDaysSqliteDao;
import com.fmahieu.timetracker.models.TimeDay;
import com.fmahieu.timetracker.models.singletons.StatsCache;

import java.util.List;

public class TimeDayLogic {

    StatsCache statsCache = StatsCache.getInstance();

    public void updateStatsCache(String from, String to){
        TimeDayDao dao = new TimeDaysSqliteDao(App.getContext());
        List<TimeDay> timeDays = dao.getAllTimeDayBetweenDates(from, to);
        statsCache.setTimeDays(timeDays);
    }

    public List<TimeDay> getTimeDaysFromCache(){
        return statsCache.getTimeDays();
    }

}
