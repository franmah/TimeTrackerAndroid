package com.fmahieu.timetracker.models.singletons;

import com.fmahieu.timetracker.models.TimeDay;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds TimeDay objects displayed in the stats page
 */
public class StatsCache {

    private static StatsCache instance;

    public static StatsCache getInstance(){
        if(instance == null){
            instance = new StatsCache();
        }

        return instance;
    }

    private List<TimeDay> timeDays;

    private StatsCache(){
        timeDays = new ArrayList<>();
    }

    public List<TimeDay> getTimeDays() {
        return timeDays;
    }

    public void setTimeDays(List<TimeDay> timeDays) {
        this.timeDays = timeDays;
    }
}
