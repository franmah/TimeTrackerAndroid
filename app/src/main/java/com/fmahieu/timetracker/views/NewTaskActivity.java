package com.fmahieu.timetracker.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fmahieu.timetracker.R;
import com.fmahieu.timetracker.logger.Logger;
import com.fmahieu.timetracker.logic.TaskLogic;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class NewTaskActivity extends AppCompatActivity {
    private final String TAG = "__NewTaskActivity";

    private TaskLogic taskLogic;
    private Logger logger = new Logger();

    /** VIEWS **/
    private TextInputLayout taskNameTextInputLayout;
    private TextInputEditText taskNameEditText;
    private FloatingActionButton submitNewTaskButton;
    private AdView adView;
    private ProgressBar adProgressBar;

    private final String NAME_TOO_LONG_ERROR = "Name too long (should be less than 20 character)";
    private final String NAME_ALREADY_EXISTS_ERROR = "Name already exists";
    private final String EMPTY_NAME = "Name cannot be empty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_task_activity);
        taskLogic = new TaskLogic();

        setupAdView();
        setUpWidgets();
    }

    private void setUpWidgets(){
        this.taskNameTextInputLayout = findViewById(R.id.task_name_TextInputLayout);
        this.taskNameEditText = findViewById(R.id.task_name_TextInputEdit);
        this.submitNewTaskButton = findViewById(R.id.submitNewTask_floating_button);

        this.taskNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isTaskNameValid(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        this.submitNewTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = taskNameEditText.getText().toString().trim();

                if(isTaskNameValid(text)){
                    if(text.length() == 0){
                        taskNameTextInputLayout.setError(EMPTY_NAME);
                    }
                    else{
                        addNewTask(text);
                    }
                }
            }
        });
    }

    private void setupAdView() {
        adProgressBar = findViewById(R.id.ad_progressBar_newActivity);
        adView = findViewById(R.id.adView_newActivity);
        //adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111"); // TODO: change test id to real
        //adView.setAdUnitId("ca-app-pub-7820725826893212/7152507882"); // real id
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                logger.logDebug(TAG, "ad loaded");
                adProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                logger.logError(TAG, "ad failed to load: error code: " + errorCode);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });
    }

    private void addNewTask(String taskName) {
        String error = taskLogic.addTask(taskName);
        if (error != null) {
            makeToast(error);
        }
        setResult();
    }

    private boolean isTaskNameValid(String newTaskName){
        if(newTaskName == null) {
            return false;
        }
        else if(taskLogic.doesTaskExist(newTaskName.trim())){
            this.taskNameTextInputLayout.setError(NAME_ALREADY_EXISTS_ERROR);
            return false;
        }
        else if(newTaskName.trim().length() > 20){
            this.taskNameTextInputLayout.setError(NAME_TOO_LONG_ERROR);
            return false;
        }
        else{
            this.taskNameTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    public void setResult(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra(MainActivity.AddTaskResult, true);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void onBackPressed(){
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void makeToast(String message){
        logger.logMessage(TAG, "making toast: " + message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
