package com.fmahieu.timetracker.views.statsFragments.taskSummary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fmahieu.timetracker.application.App;
import com.fmahieu.timetracker.database.sqlite.TimeDaysSqliteDao;
import com.fmahieu.timetracker.logic.TimeDateLogic.DateOperationLogic;
import com.fmahieu.timetracker.models.TimeDay;

import java.util.List;

public class TaskSummaryAdapter extends RecyclerView.Adapter<TaskSummaryHolder> {

    private String fromDate;
    private String toDate;
    private long timeFrame;
    private Context context;

    private List<TimeDay> timeDays;


    public TaskSummaryAdapter(Context context, String fromDate, String toDate, long timeFrame){
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.timeFrame = timeFrame;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskSummaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        return new TaskSummaryHolder(layoutInflater ,parent);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskSummaryHolder holder, int position) {
        holder.bind(timeDays.get(position), timeFrame);
    }

    @Override
    public int getItemCount() {
        if(timeDays == null) {
            timeDays = new TimeDaysSqliteDao(App.getContext()).getAllTimeDayBetweenDates(fromDate, toDate);
        }
        return timeDays.size();
    }
}
