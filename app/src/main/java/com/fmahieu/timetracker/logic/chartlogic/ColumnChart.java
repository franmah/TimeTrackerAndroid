package com.fmahieu.timetracker.logic.chartlogic;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;

import com.fmahieu.timetracker.logic.TaskLogic;
import com.fmahieu.timetracker.logic.TimeDateLogic.DateTimeOperationLogic;
import com.fmahieu.timetracker.logic.TimeDayLogic;
import com.fmahieu.timetracker.models.TimeDay;

import java.util.ArrayList;
import java.util.List;

public class ColumnChart {

    private TaskLogic taskLogic = new TaskLogic();
    private DateTimeOperationLogic timeOperationLogic = new DateTimeOperationLogic();

    public Cartesian getColumnChart(){
        List<DataEntry> dataEntries = getDataTimeDayCache();
        Cartesian cartesian = AnyChart.column();
        Column column = cartesian.column(dataEntries);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("s{%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.yScale().minimum(0d);
        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Activities");
        cartesian.yAxis(0).title("Seconds");

        return cartesian;
    }

    public List<DataEntry> getDataFromTaskCache(){
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
