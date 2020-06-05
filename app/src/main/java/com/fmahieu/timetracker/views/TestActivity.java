package com.fmahieu.timetracker.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.fmahieu.timetracker.R;
import com.fmahieu.timetracker.test.TimeDayDatabaseTest;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "__TestActivity";

    private Button timeDayDatabaseTestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "activity started");

        setContentView(R.layout.test_activity);

        setTitle("Test Activity");

        setupButtons();
    }

    private void setupButtons() {
        timeDayDatabaseTestButton = findViewById(R.id.timeDayDatabase_test_button);
        timeDayDatabaseTestButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.timeDayDatabase_test_button:
                Log.i(TAG, "Start timeDayDatabase test");
                new TimeDayDatabaseTest().executeTests();
                break;
            default:
                break;
        }
    }
}
