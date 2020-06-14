package com.fmahieu.timetracker.views.statsFragments.taskSummary;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fmahieu.timetracker.R;
import com.fmahieu.timetracker.logger.Logger;
import com.fmahieu.timetracker.models.TimeDay;

public class TaskSummaryHolder extends RecyclerView.ViewHolder {

    private final String TAG = "__TaskSummaryHolder";
    private final Logger logger = new Logger();

    /** VIEWS **/
    private TextView taskNameTextView;
    private TextView totalTimeTextView;
    private TextView averageTimeTextView;

    public TaskSummaryHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.task_summary_holder, parent, false));
        setViews();
    }

    private void setViews() {
        taskNameTextView = itemView.findViewById(R.id.summaryHolder_task_name);
        totalTimeTextView = itemView.findViewById(R.id.summaryHolder_totalTime);
        averageTimeTextView = itemView.findViewById(R.id.summaryHolder_averageTime);
    }

    /**
     *
     * @param timeDay object with information about task and time spent
     * @param timeFrame number of days between dates
     */
    public void bind(TimeDay timeDay, long timeFrame) {
        long totalTime_seconds = timeDay.getTotalTime();
        setTaskName(timeDay.getTaskName());
        setTotalTime(totalTime_seconds);
        setAverageTime(totalTime_seconds, timeFrame);
    }

    private void setTaskName(String taskName) {
        taskNameTextView.setText(taskName);
    }

    private void setTotalTime(long totalTime_seconds) {
        long hours = (totalTime_seconds % 86400 ) / 3600;
        long minutes = ((totalTime_seconds % 86400 ) % 3600 ) / 60;
        long seconds = ((totalTime_seconds % 86400 ) % 3600 ) % 60;

        String text = hours + "h " + minutes + "m " + seconds + "s (total)";
        totalTimeTextView.setText(text);
    }

    private void setAverageTime(long totalTime_seconds, long timeFrame) {
        timeFrame = timeFrame == 0 ? 1 : timeFrame;
        long averageNumSeconds = totalTime_seconds / timeFrame;
        long hours = (averageNumSeconds % 86400 ) / 3600;
        long minutes = ((averageNumSeconds % 86400 ) % 3600 ) / 60;
        long seconds = ((averageNumSeconds % 86400 ) % 3600 ) % 60;

        String text = hours + "h " + minutes + "m " + seconds + "s/days";
        averageTimeTextView.setText(text);
    }
}
