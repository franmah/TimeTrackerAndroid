package com.fmahieu.timetracker.logic.chartlogic;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.fmahieu.timetracker.logic.TaskLogic;
import com.fmahieu.timetracker.logic.TimeDateLogic.DateTimeOperationLogic;
import com.fmahieu.timetracker.logic.TimeDayLogic;
import com.fmahieu.timetracker.models.TimeDay;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class PieChart {

    private DateTimeOperationLogic timeOperationLogic = new DateTimeOperationLogic();

    public Pie getPieChart(){
        List<DataEntry> dataEntries = getDataTimeDayCache();

        Pie pieChart = AnyChart.pie();

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

    private List<DataEntry> getDataTaskCache(){
        TaskLogic taskLogic = new TaskLogic();
        List<DataEntry> dataEntries = new ArrayList<>();
        List<String> taskNames = taskLogic.getTasksNames();

        String totalTime;
        long totalTimeInSeconds;

        for(int i = 0; i < taskNames.size(); i++){
            totalTime = taskLogic.getTaskTotalDurationAsReadableString(taskNames.get(i));
            totalTimeInSeconds = timeOperationLogic.getDurationAsSeconds(totalTime);
            dataEntries.add(new ValueDataEntry(taskNames.get(i), totalTimeInSeconds));
        }

        return dataEntries;
    }

    private List<DataEntry> getDataTimeDayCache(){
        TimeDayLogic timeDayLogic = new TimeDayLogic();
        List<TimeDay> timeDays = timeDayLogic.getTimeDaysFromCache();
        List<DataEntry> dataEntries = new ArrayList<>();

        for(int i = 0; i < timeDays.size(); i++){
            TimeDay timeDay = timeDays.get(i);
            dataEntries.add(new ValueDataEntry(timeDay.getTaskName(), timeDay.getTotalTime()));
        }
        return dataEntries;
    }
}

 /* Example of a click listener for pie chart
        pieChart.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(getContext(), event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });
        */