package com.fmahieu.timetracker.views;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fmahieu.timetracker.R;
import com.fmahieu.timetracker.logic.TaskLogic;

public class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final String TAG = "__TaskHolder";

    private TextView taskNameView;
    private TextView stopwatchTimeTextView;
    private ImageView stopwatchControlImageView;

    private TaskLogic taskLogic;

    private Context context;

    private final int PLAY_ICON_RES = R.mipmap.ic_play_timer_black_bgk;
    private final int STOP_ICON_RES = R.mipmap.ic_stop_timer_black_bgk;

    private String taskName;

    NotificationCompat.Builder notificationBuilder;
    int notificationId;


    // FOR STOPWATCH TIME
    private boolean isTimerRunning;
    private long millisecondTime, startTime, timeBuff, updateTime = 0L ;
    private int seconds, minutes, hours, milliSeconds;
    private Handler handler = new Handler();

    public TaskHolder(LayoutInflater inflater, ViewGroup parent, Context context){
        super(inflater.inflate(R.layout.task_holder_test1, parent, false));
        //super(inflater.inflate(R.layout.test_task_holder, parent, false));

        this.taskLogic = new TaskLogic();
        this.context = context;
        this.taskNameView = itemView.findViewById(R.id.task_name_textView);
        this.stopwatchTimeTextView = itemView.findViewById(R.id.timer_stopwatch_text_view);
        this.stopwatchControlImageView = itemView.findViewById(R.id.play_pause_timer_image_view);

        this.stopwatchTimeTextView.setText("        ");
        this.stopwatchControlImageView.setImageResource(PLAY_ICON_RES);
    }

    public void bind(final String taskName, int notificationId, NotificationCompat.Builder notificationBuilder){

        this.taskNameView.setText(taskName);
        this.isTimerRunning = false;
        this.taskName = taskName;
        this.notificationBuilder = notificationBuilder;
        this.notificationId = notificationId;

        this.stopwatchControlImageView.setOnClickListener(this);
    }


    private Runnable runnable = new Runnable() {
        public void run() {
            if (isTimerRunning) {
                millisecondTime = SystemClock.uptimeMillis() - startTime;
                updateTime = timeBuff + millisecondTime;
                seconds = (int) (updateTime / 1000);
                minutes = seconds / 60;
                hours = minutes / 60;
                seconds = seconds % 60;
                milliSeconds = (int) (updateTime % 1000);

                String newTime = "" + String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":"
                        + String.format("%02d", seconds);

                stopwatchTimeTextView.setText(newTime);
                handler.postDelayed(this, 0);
            }
        }

    };

    private Runnable startTimerForTask = new Runnable() {
        @Override
        public void run() {
            taskLogic.startTimerForTask(taskName);
        }
    };

    private Runnable stopTimerForTask = new Runnable() {
        @Override
        public void run() {
            taskLogic.stopTimerForTask(taskName);
        }
    };

    @Override
    public void onClick(View view) {
        if(isTimerRunning) {  // pressed stop button

            isTimerRunning = false;
            handler.postDelayed(runnable, 0);
            stopwatchControlImageView.setImageResource(PLAY_ICON_RES);

            // Update stopwatch
            millisecondTime = 0L;
            startTime = 0L;
            timeBuff = 0L;
            updateTime = 0L;
            seconds = 0;
            minutes = 0;
            hours = 0;
            milliSeconds = 0;

            stopwatchTimeTextView.setText("        ");
            stopTimerForTask.run();
        }
        else {  // pressed play button
            startNotification();

            startTime = SystemClock.uptimeMillis();
            isTimerRunning = true;
            stopwatchControlImageView.setImageResource(STOP_ICON_RES);
            handler.postDelayed(runnable, 0);
            startTimerForTask.run();
        }
    }

    private void startNotification(){
        this.notificationBuilder.setContentText(this.taskNameView.getText() + " running");

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(this.notificationId, this.notificationBuilder.build());
    }
}
