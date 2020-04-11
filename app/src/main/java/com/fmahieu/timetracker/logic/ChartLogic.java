package com.fmahieu.timetracker.logic;

import com.anychart.charts.Pie;
import com.fmahieu.timetracker.models.singletons.ChartSettings;

public class ChartLogic {

    private ChartSettings chartSettings;

    public ChartLogic(){
        this.chartSettings = ChartSettings.getInstance();
    }

    public void updateChart(){
        this.chartSettings.updateChart();
    }

    public Pie getPieChart(){
        return this.chartSettings.getPieChart();
    }
}
