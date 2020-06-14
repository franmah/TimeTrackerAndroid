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
        timeDay = new TimeDay("06/01/2020", "long", 200);
        dao.addTimeDayEntry(timeDay);
        timeDay = new TimeDay("06/01/2020", "medium", 100);
        dao.addTimeDayEntry(timeDay);
        timeDay = new TimeDay("06/01/2020", "short", 50);
        dao.addTimeDayEntry(timeDay);

        timeDay = new TimeDay("06/20/2018","long" , 20);
        dao.addTimeDayEntry(timeDay);
        timeDay = new TimeDay("06/20/2019", "medium" , 10);
        dao.addTimeDayEntry(timeDay);
        timeDay = new TimeDay("06/01/2019", "short", 5);
        dao.addTimeDayEntry(timeDay);
    }
}