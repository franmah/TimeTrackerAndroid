package com.fmahieu.timetracker.views.stopwatchViews;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.fmahieu.timetracker.R;
import com.fmahieu.timetracker.logic.StopwatchLogic;
import com.fmahieu.timetracker.logic.TaskLogic;
import com.fmahieu.timetracker.logic.TimeDateLogic.DateTimeOperationLogic;
import com.fmahieu.timetracker.models.TimeHolder;
import com.fmahieu.timetracker.models.singletons.Tasks;
import com.fmahieu.timetracker.presenters.TaskHolderPresenter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final String TAG = "__TaskHolder";

    /** STOPWATCH VIEWS */
    private TextView taskNameView;
    private TextView stopwatchTimeTextView;
    private ImageView stopwatchControlImageView;
    private TextView totalTimeTextView;
    private LinearLayout taskLayout;
    private LinearLayout stopwatchLayout;

    /** EDIT NAME VIEWS **/
    private ConstraintLayout editLayout;
    private TextInputLayout newNameTextInputLayout;
    private TextInputEditText newNameEditText;
    private Button deleteButton;
    private ImageView cancelImg;
    private ImageView okImg;

    private final String NAME_TOO_LONG_ERROR = "Name too long (should be less than 20 character)";
    private final String NAME_ALREADY_EXISTS_ERROR = "Name already exists";
    private final String EMPTY_NAME = "Name cannot be empty";

    private TaskLogic taskLogic;
    private StopwatchLogic stopwatchLogic;
    private TaskHolderPresenter presenter;

    private TaskRecyclerAdapter adapter;

    /** ICONS **/
    private final int PLAY_ICON_RES = R.mipmap.ic_play_timer_black_bgk;
    private final int STOP_ICON_RES = R.mipmap.ic_stop_timer_black_bgk;

    private String taskName;

    /** STOPWATCH visual **/
    private boolean isStopwatchRunning;
    private long millisecondTime, startTime = 0L ;
    private int seconds, minutes, hours;
    private Handler handler = new Handler();
    private Context context;

    public TaskHolder(LayoutInflater inflater, ViewGroup parent, TaskRecyclerAdapter adapter) {
        super(inflater.inflate(R.layout.task_holder_list, parent, false));

        this.taskLogic = new TaskLogic();
        this.stopwatchLogic = new StopwatchLogic();
        this.presenter = new TaskHolderPresenter();
        this.adapter = adapter;
    }

    public void bind(final String taskName, Context context) {
        setupStopwatchViews();
        setupEditNameViews();
        setLongPress();

        taskNameView.setText(taskName);
        isStopwatchRunning = false;
        this.taskName = taskName;
        this.context = context;

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

    private void setupStopwatchViews() {
        this.stopwatchLayout = itemView.findViewById(R.id.taskHolder_stopwatch_layout);
        this.taskNameView = itemView.findViewById(R.id.task_name_textView);
        this.stopwatchTimeTextView = itemView.findViewById(R.id.timer_stopwatch_text_view);
        this.stopwatchControlImageView = itemView.findViewById(R.id.play_pause_timer_image_view);
        this.stopwatchTimeTextView.setText(R.string.default_stopwatch_text);
        this.stopwatchControlImageView.setImageResource(PLAY_ICON_RES);
        this.totalTimeTextView = itemView.findViewById(R.id.timer_totalTime_textView);
        this.newNameTextInputLayout = itemView.findViewById(R.id.taskHolder_textInputLayout);
        this.stopwatchControlImageView.setOnClickListener(this);
    }

    private void setupEditNameViews() {
        this.editLayout = itemView.findViewById(R.id.taskHolder_edit_layout);
        this.newNameEditText = itemView.findViewById(R.id.taskHolder_textInputEdit);
        this.taskLayout = itemView.findViewById(R.id.holder_layout);
        this.deleteButton = itemView.findViewById(R.id.taskHolder_delete_btn);
        this.cancelImg = itemView.findViewById(R.id.taskHolder_cancel_img);
        this.okImg = itemView.findViewById(R.id.taskHolder_ok_img);

        this.deleteButton.setOnClickListener(this);
        this.cancelImg.setOnClickListener(this);
        this.okImg.setOnClickListener(this);

        this.newNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isTaskNameValid(charSequence.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    /** LISTENERS **/

    private void setLongPress() {
        this.taskLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // hide stopwatch views and show edit views
                stopwatchLayout.setVisibility(View.GONE);
                newNameTextInputLayout.setHint(taskName);
                editLayout.setVisibility(View.VISIBLE);
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.taskHolder_delete_btn:
                deleteButtonClick();
                break;
            case R.id.taskHolder_cancel_img:
                hideEditMenu();
                break;
            case R.id.taskHolder_ok_img:
                changeTaskName();
                break;
            case R.id.play_pause_timer_image_view:
                timerClick();
                break;
        }
    }

    private void deleteButtonClick() {
        Log.i(TAG, "deleting task: " + taskName);
        if (taskName != null) {
            new android.os.Handler().post(
                    new Runnable() {
                        public void run() {

                            String error = taskLogic.deleteTask(taskName);
                            if (error != null) {
                               makeToast(error);
                            }
                        }
                    });
            hideEditMenu();
            adapter.notifyDataSetChanged();
        }
    }

    private void hideEditMenu() {
        newNameEditText.setText("");
        editLayout.setVisibility(View.GONE);
        stopwatchLayout.setVisibility(View.VISIBLE);
    }

    private void changeTaskName() {
        String newName = newNameEditText.getText().toString().trim();
        Log.i(TAG, "taskName: " + taskName);
        if(!taskName.equals(newName) && isTaskNameValid(newName)) {
            // Change name in persistent memory (db)
            final String name = newName;
            final String oldName = taskName;
            new android.os.Handler().post(
                    new Runnable() {
                        public void run() {
                            Log.i(TAG, "taskName in thread:" + oldName);
                            new TaskLogic().editTaskName(oldName, name);
                        }
                    });
            taskName = newName;
            taskNameView.setText(taskName);
            hideEditMenu();
        }
    }

    private void timerClick() {
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

    /** HELPER **/

    private boolean isTaskNameValid(String newTaskName){
        if(newTaskName == null) {
            return false;
        }
        else if (newTaskName.trim().equals("")) {
            this.newNameTextInputLayout.setError(EMPTY_NAME);
            return false;
        }
        else if(taskLogic.doesTaskExist(newTaskName.trim())){
            this.newNameTextInputLayout.setError(NAME_ALREADY_EXISTS_ERROR);
            return false;
        }
        else if(newTaskName.trim().length() > 20){
            this.newNameTextInputLayout.setError(NAME_TOO_LONG_ERROR);
            return false;
        }
        else{
            this.newNameTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    private void makeToast(String message){
        Log.i(TAG, "making toast: " + message);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /*** Handle updating visual for stopwatch ***/

    private Runnable stopwatchRunnable = new Runnable() {
        public void run() {
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
            String error = stopwatchLogic.startStopwatchForTask(taskName);
            if (error != null) {
                makeToast(error);
            }
        }
    };

    private Runnable stopTimerForTask = new Runnable() {
        @Override
        public void run() {
            String error = stopwatchLogic.stopStopwatchForTask(taskName);
            if (error != null) {
                makeToast(error);
            }
            updateTotalTimeView();
        }
    };

    public void pauseStopwatch() {
        isStopwatchRunning = false;
    }


}
