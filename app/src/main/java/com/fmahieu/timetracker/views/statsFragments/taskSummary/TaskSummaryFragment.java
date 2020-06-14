package com.fmahieu.timetracker.views.statsFragments.taskSummary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fmahieu.timetracker.R;
import com.fmahieu.timetracker.logger.Logger;
import com.fmahieu.timetracker.logic.TimeDateLogic.DateOperationLogic;

public class TaskSummaryFragment extends Fragment {

    private final String TAG = "__TaskSummaryFragment";
    private Logger logger = new Logger();

    /** VIEWS **/
    private RecyclerView recycler;
    private TaskSummaryAdapter adapter;
    private TextView timeFrameTextView;

    private String fromDate;
    private String toDate;
    private long timeFrame; // number of days between from and to dates

    public TaskSummaryFragment(String fromDate, String toDate){
        this.fromDate = fromDate;
        this.toDate =toDate;
        this.timeFrame = setTimeFrame();
    }

    private long setTimeFrame() {
        return new DateOperationLogic().getNumberOfDaysBetweenDates(fromDate, toDate);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_stats_summary, container, false);
        this.timeFrameTextView = view.findViewById(R.id.timeFrame_textView);
        String timeFrameText = "period: " + timeFrame + " days";
        this.timeFrameTextView.setText(timeFrameText);

        this.recycler = view.findViewById(R.id.task_summary_recyclerView);
        this.adapter = new TaskSummaryAdapter(getContext(), fromDate, toDate, timeFrame);
        this.recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        this.recycler.setAdapter(adapter);
        return view;
    }
}
