package com.fmahieu.timetracker.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.fmahieu.timetracker.R;
import com.fmahieu.timetracker.logic.InitialSetupLogic;
import com.fmahieu.timetracker.logic.TaskLogic;
import com.fmahieu.timetracker.views.swipeViews.StatsSwipeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private final String TAG = "__MainActivity";

    private FragmentManager fragmentManager = getSupportFragmentManager();
    Context context;

    // Result from AddTaskActivity
    public static String AddTaskResult = "com.fmahieu.MainActivity.AddTaskResult";
    private final int ADD_TASK_RESULT_CODE = 1;

    // Widgets
    private BottomNavigationView menuView;
    private FloatingActionButton addTaskButton;

    // tell fragmentManager which fragment should be displayed
    private enum MenuViews {Stopwatch, Stats, Settings }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "MainActivity started");

        setContentView(R.layout.main_activity);
        context = this;

        setTitle(R.string.stopwatch_title);

        setViews();
        setFragment(MenuViews.Stopwatch);
    }

    private void setViews(){
        menuView = findViewById(R.id.bottom_menu_layout);
        menuView.setOnNavigationItemSelectedListener(this);

        addTaskButton = findViewById(R.id.floating_button_addTask);
        addTaskButton.setOnClickListener(this);
    }

    public void setFragment(MenuViews viewToShow){
        Fragment fragment = fragmentManager.findFragmentById(R.id.main_view_frameLayout);
        switch(viewToShow){
            case Stopwatch:
                Log.i(TAG, "Getting stopwatch fragment");
                fragment = new StopwatchFragment();
                break;
            case Stats:
                Log.i(TAG, "Getting stats fragment");
                fragment = new StatsSwipeFragment();
                break;
        }
        fragmentManager.beginTransaction().replace(R.id.main_view_frameLayout,fragment).commit();
    }

    @Override
    public void onClick(View view) {
        this.addTaskButton.setClickable(false); // avoid multiple clicking
        Intent intent = new Intent(this, NewTaskActivity.class);
        startActivityForResult(intent, ADD_TASK_RESULT_CODE);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.timer_menu_item:
                this.addTaskButton.setClickable(true);
                this.addTaskButton.setVisibility(View.VISIBLE);
                setTitle(R.string.stopwatch_title);
                setFragment(MenuViews.Stopwatch);
                return true;
            case R.id.stats_menu_item:
                this.addTaskButton.setClickable(false);
                this.addTaskButton.setVisibility(View.GONE);
                setTitle(R.string.stats_title);
                setFragment(MenuViews.Stats);
                return true;
            case R.id.settings_menu_item:
                //makeToast("clicked settings");
                return true;
        }
        return false;
    }

    private void makeToast(String message){
        Log.i(TAG, "making toast: " + message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.addTaskButton.setClickable(true);

        if (requestCode == ADD_TASK_RESULT_CODE && resultCode == Activity.RESULT_OK) {
                setFragment(MenuViews.Stopwatch);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setFragment(MenuViews.Stopwatch); // will be called when returning to the activity.
        this.addTaskButton.setClickable(true);
    }


}
