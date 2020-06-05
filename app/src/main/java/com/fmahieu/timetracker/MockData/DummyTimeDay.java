package com.fmahieu.timetracker.MockData;

import com.fmahieu.timetracker.application.App;
import com.fmahieu.timetracker.database.DAO.TimeDayDao;
import com.fmahieu.timetracker.database.sqlite.TimeDaysSqliteDao;
import com.fmahieu.timetracker.models.TimeDay;
import com.fmahieu.timetracker.models.singletons.Tasks;

public class DummyTimeDay {

    public void addDummyData() {
        TimeDay timeDay;
        TimeDayDao dao = new TimeDaysSqliteDao(App.getContext());
        timeDay = new TimeDay("02/01/2020", "long", 200);
        dao.addTimeDayEntry(timeDay);
        timeDay = new TimeDay("01/01/2020", "long", 100);
        dao.addTimeDayEntry(timeDay);

        timeDay = new TimeDay("01/20/2020","short" , 2);
        dao.addTimeDayEntry(timeDay);
        timeDay = new TimeDay("02/20/2020", "short" , 1);
        dao.addTimeDayEntry(timeDay);
    }
}