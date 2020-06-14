package com.fmahieu.timetracker.presenters;

import com.fmahieu.timetracker.logic.TimeDateLogic.DateOperationLogic;

public class StatsSwipePresenter {

    private DateOperationLogic dateOperationLogic = new DateOperationLogic();

    public StatsSwipePresenter(){
    }

    /**
     * Fetch data (on another thread) for tasks stats
     * @param from from date
     * @param to to date
     */
    public boolean updateStatsWindow(String from, String to){
        // TODO: date should be verified when user modifies it (avoid testing both dates at the same time
        if(!dateOperationLogic.verifyDateFormat(from) || !dateOperationLogic.verifyDateFormat(to)){
            return false;
        }

        //new TimeDayLogic().updateStatsCache(from, to);
        return true;
    }
}
