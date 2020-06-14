package com.fmahieu.timetracker.views.statsFragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.fmahieu.timetracker.R;
import com.fmahieu.timetracker.logger.Logger;
import com.fmahieu.timetracker.logic.TimeDateLogic.DateOperationLogic;
import com.fmahieu.timetracker.views.statsFragments.chartFragment.ChartFragment;
import com.fmahieu.timetracker.views.statsFragments.chartFragment.ColumnChartFragment;
import com.fmahieu.timetracker.views.statsFragments.chartFragment.PieChartFragment;
import com.fmahieu.timetracker.views.statsFragments.taskSummary.TaskSummaryFragment;

public class StatsFragment extends Fragment {

    private static final String TAG = "__StatsFragment";

    private FragmentManager fragmentManager = null;
    Fragment fragment = null;
    private DateOperationLogic dateOperationLogic;
    private Logger logger = new Logger();

    private ImageView leftArrowView;
    private ImageView rightArrowView;
    private Button fromDateView;
    private Button toDateView;
    private DatePickerDialog.OnDateSetListener fromDateSetListener;
    private DatePickerDialog.OnDateSetListener toDateSetListener;

    private String fromDate;
    private String toDate;

    private enum ChartViews { Pie, Column, Summary }
    private ChartViews currentChartView;

    private ChartFragment currentFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.stats_fragment_layout, container, false);
        fragmentManager = getChildFragmentManager();
        dateOperationLogic = new DateOperationLogic();

        currentChartView = ChartViews.Summary;
        setUpArrowWidgets(view);
        setUpDateSelectorWidgets(view);
        setDateSelectorListener();
        setDateSelectorChangeListener();


        setFragment();

        return view;
    }

    private void setUpArrowWidgets(View view){
        leftArrowView = view.findViewById(R.id.leftArrow_imageView);
        rightArrowView = view.findViewById(R.id.rightArrow_imageView);

        setLeftArrowClickable(false);
        setRightArrowClickable(true);

        leftArrowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (currentChartView){
                    case Summary:
                        break;
                    case Pie:
                        currentChartView = ChartViews.Summary;
                        setLeftArrowClickable(false);
                        setRightArrowClickable(true);
                        break;
                    case Column:
                        currentChartView = ChartViews.Pie;
                        setLeftArrowClickable(true);
                        setRightArrowClickable(true);
                        break;

                }
                setFragment();
            }
        });

        rightArrowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (currentChartView){
                    case Summary:
                        currentChartView = ChartViews.Pie;
                        setLeftArrowClickable(true);
                        setRightArrowClickable(true);
                        break;
                    case Pie:
                        currentChartView = ChartViews.Column;
                        setLeftArrowClickable(true);
                        setRightArrowClickable(false);
                        break;
                    case Column:
                        break;
                }
                setFragment();
            }
        });
    }

    public void setLeftArrowClickable(boolean clickable){
        if(clickable) {
            leftArrowView.setImageResource(R.mipmap.ic_left_arrow);
            leftArrowView.setClickable(true);
        }
        else {
            leftArrowView.setImageResource(R.mipmap.ic_left_arrow_gray);
            leftArrowView.setClickable(false);
        }
    }

    public void setRightArrowClickable(boolean clickable) {
        if(clickable) {
            rightArrowView.setImageResource(R.mipmap.ic_right_arrow);
            rightArrowView.setClickable(true);
        }
        else {
            rightArrowView.setImageResource(R.mipmap.ic_right_arrow_gray);
            rightArrowView.setClickable(false);
        }
    }

    private void setUpDateSelectorWidgets(View view){
        this.fromDateView = view.findViewById(R.id.fromDate_statsFragment);
        this.toDateView = view.findViewById(R.id.toDate_statsFragment);

        fromDate = dateOperationLogic.getDateOneMonthAgo();
        toDate = dateOperationLogic.getCurrentDate();

        fromDateView.setText(fromDate);
        toDateView.setText(toDate);
    }

    /**
     * Listen for clicks on the date buttons.
     * Display new window to let user select new date.
     */
    //TODO: when user clicks, show the current selected date rather than today's date
    private void setDateSelectorListener(){
        fromDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = dateOperationLogic.getCurrentYear();
                int month = dateOperationLogic.getCurrentMonth();
                int day = dateOperationLogic.getCurrentDay();

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        fromDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        toDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = dateOperationLogic.getCurrentYear();
                int month = dateOperationLogic.getCurrentMonth();
                int day = dateOperationLogic.getCurrentDay();

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        toDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
    }

    /**
     * Listen for changes in dates.
     * Once a date is changed the chart fragments are updated.
     */
    // TODO: add check to see if to date is after from date
    private void setDateSelectorChangeListener() {
        logger.logDebug(TAG, "setting up change listener");
        fromDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                logger.logDebug(TAG, "from date button has been changed");
                month += 1;
                String date = dateOperationLogic.createDate(year, month, day);
                fromDateView.setText(date);
                fromDate = date;
                setFragment();
            }
        };

        toDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                logger.logDebug(TAG, "to date button has been changed");
                month += 1;
                String date = dateOperationLogic.createDate(year, month, day);
                toDate = date;
                toDateView.setText(date);
                setFragment();
            }
        };
    }

    public String getFromDate(){
        return fromDate;
    }

    public String getToDate(){
        return toDate;
    }

    private void setFragment(){
        Log.i(TAG, "getting fragment fragment");
        fragment = fragmentManager.findFragmentById(R.id.stats_fragment_holder_frameLayout);
        switch (currentChartView){
            case Pie:
                fragment = new PieChartFragment(fromDate, toDate);
                break;
            case Column:
                fragment = new ColumnChartFragment(fromDate, toDate);
                break;
            case Summary:
                fragment = new TaskSummaryFragment(fromDate ,toDate);
                break;
        }
        try{
            fragmentManager.beginTransaction().replace(R.id.stats_fragment_holder_frameLayout,fragment).commit();
        }
        catch (Exception e) {
            fragmentManager.beginTransaction().add(R.id.stats_fragment_holder_frameLayout, fragment).commit();
        }
    }
}