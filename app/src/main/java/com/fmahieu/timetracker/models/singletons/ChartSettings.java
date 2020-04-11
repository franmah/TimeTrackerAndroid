package com.fmahieu.timetracker.models.singletons;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.fmahieu.timetracker.logic.DisplayTaskLogic;
import com.fmahieu.timetracker.logic.TaskLogic;

import java.util.ArrayList;
import java.util.List;

public class ChartSettings {
    private static ChartSettings chartSettings = null;

    public static ChartSettings getInstance(){
        if(chartSettings == null) {
            chartSettings = new ChartSettings();
        }
        return chartSettings;
    }

    private Pie pieChart;
    private TaskLogic taskLogic = new TaskLogic();
    private DisplayTaskLogic displayTaskLogic = new DisplayTaskLogic();

    private ChartSettings(){
        this.taskLogic = new TaskLogic();
        this.displayTaskLogic = new DisplayTaskLogic();
        this.pieChart = AnyChart.pie();
    }


    public void updateChart(){

        List<DataEntry> dataEntries = new ArrayList<>();
        List<String> taskNames = taskLogic.getTasksNames();

        String totalTime;
        long totalTimeInSeconds;

        for(int i = 0; i < taskNames.size(); i++){
            totalTime = taskLogic.getTaskTotalTime(taskNames.get(i));
            totalTimeInSeconds = displayTaskLogic.getTotalTimeAsSeconds(totalTime);
            dataEntries.add(new ValueDataEntry(taskNames.get(i), totalTimeInSeconds));
        }

        pieChart.data(dataEntries);
    }

    public Pie getPieChart(){
        return this.pieChart;
    }


}
