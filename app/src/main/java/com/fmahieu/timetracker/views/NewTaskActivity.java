package com.fmahieu.timetracker.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.fmahieu.timetracker.R;
import com.fmahieu.timetracker.logic.AddTaskLogic;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class NewTaskActivity extends AppCompatActivity {
    private final String TAG = "__NewTaskActivity";

    private AddTaskLogic addTaskLogic;

    private TextInputLayout taskNameTextInputLayout;
    private TextInputEditText taskNameEditText;
    private FloatingActionButton submitNewTaskButton;

    private final String NAME_TOO_LONG_ERROR = "Name too long (should be less than 20 character)";
    private final String NAME_ALREADY_EXISTS_ERROR = "Name already exists";
    private final String EMPTY_NAME = "Name cannot be empty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "NewTaskActivity started");

        setContentView(R.layout.new_task_activity);
        addTaskLogic = new AddTaskLogic();

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
                if(isTaskNameValid(taskNameEditText.getText().toString().trim())){
                    if(taskNameEditText.getText().toString().trim().length() == 0){
                        taskNameTextInputLayout.setError(EMPTY_NAME);
                    }
                    else{
                        addNewTask(taskNameEditText.getText().toString().trim());
                    }
                }
            }
        });
    }

    private void addNewTask(String taskName) {
        addTaskLogic.addTask(taskName);
        setResult();
    }

    private boolean isTaskNameValid(String newTaskName){
        if(newTaskName == null) {
            return false;
        }
        else if(addTaskLogic.doesTaskExist(newTaskName.trim())){
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
}
