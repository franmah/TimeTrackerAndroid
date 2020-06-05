package com.fmahieu.timetracker.views;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.fmahieu.timetracker.R;
import com.fmahieu.timetracker.logic.StopwatchLogic;
import com.fmahieu.timetracker.logic.TaskLogic;
import com.fmahieu.timetracker.logic.TimeDateLogic.DateTimeOperationLogic;
import com.fmahieu.timetracker.models.TimeHolder;
import com.fmahieu.timetracker.models.singletons.Tasks;
import com.fmahieu.timetracker.presenters.TaskHolderPresenter;

public class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final String TAG = "__TaskHolder";

    /** VIEWS */
    private TextView taskNameView;
    private TextView stopwatchTimeTextView;
    private ImageView stopwatchControlImageView;
    private TextView totalTimeTextView;
    private LinearLayout taskLayout;

    private TaskLogic taskLogic;
    private StopwatchLogic stopwatchLogic;
    private TaskHolderPresenter presenter;

    private TaskRecyclerAdapter adapter;
    private Context context;

    private final int PLAY_ICON_RES = R.mipmap.ic_play_timer_black_bgk;
    private final int STOP_ICON_RES = R.mipmap.ic_stop_timer_black_bgk;

    private String taskName;

    // FOR STOPWATCH TIME
    private boolean isStopwatchRunning;
    private long millisecondTime, startTime = 0L ;
    private int seconds, minutes, hours;
    private Handler handler = new Handler();
    //

    public TaskHolder(LayoutInflater inflater, ViewGroup parent, Context context, TaskRecyclerAdapter adapter) {
        super(inflater.inflate(R.layout.task_holder_list, parent, false));

        this.taskLogic = new TaskLogic();
        this.stopwatchLogic = new StopwatchLogic();
        this.presenter = new TaskHolderPresenter();
        this.context = context;
        this.adapter = adapter;

        setupViews();
    }

    private void setupViews() {
        this.taskNameView = itemView.findViewById(R.id.task_name_textView);
        this.stopwatchTimeTextView = itemView.findViewById(R.id.timer_stopwatch_text_view);
        this.stopwatchControlImageView = itemView.findViewById(R.id.play_pause_timer_image_view);
        this.stopwatchTimeTextView.setText(R.string.default_stopwatch_text);
        this.stopwatchControlImageView.setImageResource(PLAY_ICON_RES);
        this.totalTimeTextView = itemView.findViewById(R.id.timer_totalTime_textView);

        this.taskLayout = itemView.findViewById(R.id.holder_layout);
        this.taskLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
            presenter.deleteTask(taskName);
            adapter.notifyDataSetChanged();
            return true;
            }
        });
    }

    public void bind(final String taskName) {
        taskNameView.setText(taskName);
        isStopwatchRunning = false;
        this.taskName = taskName;

        this.stopwatchControlImageView.setOnClickListener(this);

        updateTotalTimeView();

        // Check if task was running before app was paused
        TimeHolder timeHolder = presenter.getStartTimeForTask(taskName);
        if(timeHolder != null){ // Task is running
            seconds = timeHolder.getSeconds();
            minutes = timeHolder.getMinutes();
            hours = timeHolder.getHours();
            isStopwatchRunning = true;
            stopwatchControlImageView.setImageResource(STOP_ICON_RES);
            String text = hours + ":" + minutes + ":" + seconds;
            stopwatchTimeTextView.setText(text);
            startTime = presenter.convertTimeToMilliseconds(timeHolder);
            handler.postDelayed(stopwatchRunnable, 0);
        }
    }

    public void updateTotalTimeView(){
        String totalTime = Tasks.getInstance().getTotalTime(taskName);
        totalTime = new DateTimeOperationLogic().convertDurationToReadableString(totalTime);
        this.totalTimeTextView.setText(totalTime);
    }

    @Override
    public void onClick(View view) {
        if(isStopwatchRunning) {  // pressed stop button

            isStopwatchRunning = false;
            stopwatchControlImageView.setImageResource(PLAY_ICON_RES);

            // Update stopwatch
            millisecondTime = 0L;
            startTime = 0L;
            seconds = 0;
            minutes = 0;
            hours = 0;

            stopwatchTimeTextView.setText(R.string.default_stopwatch_text);
            stopTimerForTask.run();
        }
        else {  // pressed play button
            startTime = presenter.getCurrentTimeInMilliseconds();
            isStopwatchRunning = true;
            stopwatchControlImageView.setImageResource(STOP_ICON_RES);
            handler.postDelayed(stopwatchRunnable, 0);
            startTimerForTask.run();
        }
    }

    /*** Handle updating visual for stopwatch ***/

    private Runnable stopwatchRunnable = new Runnable() {
        public void run() {
            Log.i(TAG, "__TESTING: sw running");
            if (isStopwatchRunning) {
                millisecondTime = presenter.getCurrentTimeInMilliseconds() - startTime;
                seconds = (int) (millisecondTime / 1000);
                minutes = seconds / 60;
                hours = minutes / 60;
                seconds = seconds % 60;

                String newTime = "" + String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":"
                        + String.format("%02d", seconds);

                stopwatchTimeTextView.setText(newTime);
                if(isStopwatchRunning){
                    handler.postDelayed(this, 1000);
                }
            }
        }

    };

    private Runnable startTimerForTask = new Runnable() {
        @Override
        public void run() {
            stopwatchLogic.startStopwatchForTask(taskName);
        }
    };

    private Runnable stopTimerForTask = new Runnable() {
        @Override
        public void run() {
            stopwatchLogic.stopStopwatchForTask(taskName);
            updateTotalTimeView();
        }
    };

    public void pauseStopwatch() {
        isStopwatchRunning = false;
    }
}
