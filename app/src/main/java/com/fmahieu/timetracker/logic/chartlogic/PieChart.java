package com.fmahieu.timetracker.logic.chartlogic;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.fmahieu.timetracker.logic.TaskLogic;
import com.fmahieu.timetracker.logic.TimeDateLogic.TimeOperationLogic;

import java.util.ArrayList;
import java.util.List;

public class PieChart {
    private TaskLogic taskLogic = new TaskLogic();
    private TimeOperationLogic timeOperationLogic = new TimeOperationLogic();

    public Pie getPieChart(){
        List<DataEntry> dataEntries = new ArrayList<>();
        List<String> taskNames = taskLogic.getTasksNames();

        String totalTime;
        long totalTimeInSeconds;

        for(int i = 0; i < taskNames.size(); i++){
            totalTime = taskLogic.getTaskTotalDurationAsReadableString(taskNames.get(i));
            totalTimeInSeconds = timeOperationLogic.getDurationAsSeconds(totalTime);
            dataEntries.add(new ValueDataEntry(taskNames.get(i), totalTimeInSeconds));
        }

        Pie pieChart = AnyChart.pie();

        /* Example of a click listener for pie chart
        pieChart.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(getContext(), event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });
        */

        pieChart.data(dataEntries);
        pieChart.labels().position("outside");

        //pieChart.legend().title().enabled(true);
        pieChart.legend().title()
                .text("Retail channels")
                .padding(0d, 0d, 10d, 0d);

        pieChart.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        return pieChart;
    }

}
