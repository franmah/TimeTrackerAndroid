package com.fmahieu.timetracker.logic.chartlogic;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.fmahieu.timetracker.application.App;
import com.fmahieu.timetracker.database.DAO.TimeDayDao;
import com.fmahieu.timetracker.database.sqlite.TimeDaysSqliteDao;
import com.fmahieu.timetracker.logger.Logger;
import com.fmahieu.timetracker.models.TimeDay;

import java.util.ArrayList;
import java.util.List;

public class ChartLogic {
    private final String TAG = "__ChartLogic";
    private Logger logger = new Logger();
    private TimeDayDao timeDayDao = new TimeDaysSqliteDao(App.getContext());

    public List<DataEntry> getTimeDayData(String from, String to){
        try {
            List<TimeDay> timeDays = timeDayDao.getAllTimeDayBetweenDates(from, to);
            List<DataEntry> dataEntries = new ArrayList<>();
            for (int i = 0; i < timeDays.size(); i++) {
                dataEntries.add(new ValueDataEntry(timeDays.get(i).getTaskName(), timeDays.get(i).getTotalTime()));
            }
            return dataEntries;
        }
        catch (Exception e){
            logger.logException(TAG, "exception when getting timeDay data (returning empty array)", e);
            return new ArrayList<DataEntry>();
        }
    }
}
