package com.fmahieu.timetracker.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.fmahieu.timetracker.R;
import com.fmahieu.timetracker.logger.Logger;
import com.fmahieu.timetracker.views.statsFragments.StatsFragment;
import com.fmahieu.timetracker.views.stopwatchViews.StopwatchFragment;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private final String TAG = "__MainActivity";

    private FragmentManager fragmentManager = getSupportFragmentManager();

    private Logger logger = new Logger();

    // Result from AddTaskActivity
    public static String AddTaskResult = "com.fmahieu.MainActivity.AddTaskResult";
    private final int ADD_TASK_RESULT_CODE = 1;

    // Widgets
    private BottomNavigationView menuView;
    private FloatingActionButton addTaskButton;
    private AdView adView;
    private ProgressBar adProgressBar;

    // tell fragmentManager which fragment should be displayed
    public enum MenuViews {Stopwatch, Stats }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "MainActivity started");

       setContentView(R.layout.main_activity);

        // Initialize ADS
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.i(TAG, "ads initialized");
            }
        });

        setTitle(R.string.stopwatch_title);

        setAds();
        setViews();
        setFragment(MenuViews.Stopwatch);
    }

    private void setViews(){
        menuView = findViewById(R.id.bottom_menu_layout);
        menuView.setOnNavigationItemSelectedListener(this);

        addTaskButton = findViewById(R.id.floating_button_addTask);
        addTaskButton.setOnClickListener(this);
    }

    private void setAds() {
        adProgressBar = findViewById(R.id.ad_progressBar_mainActivity);
        adView = findViewById(R.id.adView_mainActivity);
        //adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111"); // TODO: change test id to real
        //adView.setAdUnitId("ca-app-pub-7820725826893212/8947669813"); // real id
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

    public void setFragment(MenuViews viewToShow){
        Fragment fragment = fragmentManager.findFragmentById(R.id.main_view_frameLayout);
        switch(viewToShow){
            case Stopwatch:
                logger.logDebug(TAG, "Getting stopwatch fragment");
                fragment = new StopwatchFragment();
                break;
            case Stats:
                logger.logDebug(TAG, "Getting stats fragment");
                fragment = new StatsFragment();
                break;
        }
        fragmentManager.beginTransaction().replace(R.id.main_view_frameLayout,fragment).commit();
    }

    @Override
    public void onClick(View view) {
        this.addTaskButton.setClickable(false); // avoid multiple clicking
        Intent intent = new Intent(this, NewTaskActivity.class);
       // startActivityForResult(intent, ADD_TASK_RESULT_CODE);
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
            case R.id.add_menu_item:
                Intent intent = new Intent(this, NewTaskActivity.class);
                startActivityForResult(intent, ADD_TASK_RESULT_CODE);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
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
