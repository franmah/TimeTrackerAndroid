package com.fmahieu.timetracker;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private final String TAG = "__MainActivity";

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView menuView;

    private enum MenuViews { Timer, Edit, Stats, Settings, Info }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "MainActivity started");

        setContentView(R.layout.main_activity);

        menuView = findViewById(R.id.bottom_menu_layout);
        menuView.setOnNavigationItemSelectedListener(this);

        setFragment(MenuViews.Timer);
    }

    public void setFragment(MenuViews viewToShow){
        Log.i(TAG, "getting main fragment");

        Fragment fragment = fragmentManager.findFragmentById(R.id.main_view_frameLayout);

        if(fragment == null){
            fragment = new TasksListFragment();
            fragmentManager.beginTransaction().add(R.id.main_view_frameLayout,fragment)
                                                .commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.timer_menu_item:
                makeToast("clicked timer");
                return true;
            case R.id.edit_menu_item:
                makeToast("clicked edit");
                return true;
            case R.id.stats_menu_item:
                makeToast("clicked stats");
                return true;
            case R.id.settings_menu_item:
                makeToast("clicked settings");
                return true;
            case R.id.info_menu_item:
                makeToast("clicked info");
                return true;
        }
        return false;
    }

    private void makeToast(String message){
        Log.i(TAG, "making toast: " + message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        setFragment(MenuViews.Timer); // will be called when returning to the activity.
    }
}
