package com.fmahieu.timetracker.views.swipeViews;

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
import androidx.viewpager2.widget.ViewPager2;

import com.fmahieu.timetracker.R;
import com.fmahieu.timetracker.logic.TimeDateLogic.DateOperationLogic;

public class StatsSwipeFragment extends Fragment {

    private ViewPager2 viewPager;
    private ChartFragmentAdapter chartFragmentAdapter;

    private ImageView leftArrowView;
    private ImageView rightArrowView;

    private DatePickerDialog.OnDateSetListener fromDateSetListener;
    private DatePickerDialog.OnDateSetListener toDateSetListener;
    private Button fromDateView;
    private Button toDateView;

    DateOperationLogic dateOperationLogic;

    int currentChartView;

    private final String TAG = "__StatsSwipeFragment";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.stats_swipe_fragment_layout, container, false);

        this.dateOperationLogic = new DateOperationLogic();

        setViewPager(view);
        setDateSelectorViews(view);
        setPagerAdapter();
        setArrowWidgets(view);

        return view;
    }

    private void setArrowWidgets(View view) {

        leftArrowView = view.findViewById(R.id.leftArrow_imageView);
        rightArrowView = view.findViewById(R.id.rightArrow_imageView);
        leftArrowView.setImageResource(R.mipmap.ic_left_arrow_gray);

        leftArrowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentChartView == 1){
                    currentChartView = 0;
                    leftArrowView.setImageResource(R.mipmap.ic_left_arrow_gray);
                    leftArrowView.setClickable(false);

                    rightArrowView.setImageResource(R.mipmap.ic_right_arrow);
                    rightArrowView.setClickable(true);
                    viewPager.setCurrentItem(0, true);

                }
            }
        });

        rightArrowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentChartView == 0){
                    currentChartView = 1;
                    rightArrowView.setImageResource(R.mipmap.ic_right_arrow_gray);
                    rightArrowView.setClickable(false);
                    leftArrowView.setImageResource(R.mipmap.ic_left_arrow);
                    leftArrowView.setClickable(true);

                    viewPager.setCurrentItem(1, true);
                }
            }
        });
    }

    private void setDateSelectorViews(View view){
        this.fromDateView = view.findViewById(R.id.fromDate_statsFragment);
        this.toDateView = view.findViewById(R.id.toDate_statsFragment);

        // Set up initial dates : from "1 month ago" to "today"
        String fromDateText = dateOperationLogic.getDateOneMonthAgo();
        fromDateView.setText(fromDateText);

        String currentDate = dateOperationLogic.getCurrentDateAsString();
        toDateView.setText(currentDate);

        // Set up listeners
        fromDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Log.i(TAG, "date received: " + dateOperationLogic.createDate(year, month, day));
                String date = dateOperationLogic.createDate(year, month, day);
                fromDateView.setText(date);
            }
        };

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

        toDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Log.i(TAG, "date received: " + dateOperationLogic.createDate(year, month, day));
                String date = dateOperationLogic.createDate(year, month, day);
                toDateView.setText(date);
            }
        };

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
     * Notify the current chart fragment to reload the graph with updated data.
     */
    private void reloadFragmentChart(){
    }

    private void setViewPager(View view){
        this.currentChartView = 0;
        this.viewPager = view.findViewById(R.id.viewPager_statsSwipeFrag);

        this.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if(position == 1){
                    currentChartView = 1;
                    rightArrowView.setImageResource(R.mipmap.ic_right_arrow_gray);
                    rightArrowView.setClickable(false);
                    leftArrowView.setImageResource(R.mipmap.ic_left_arrow);
                    leftArrowView.setClickable(true);
                }

                else if(position == 0){
                    currentChartView = 0;
                    leftArrowView.setImageResource(R.mipmap.ic_left_arrow_gray);
                    leftArrowView.setClickable(false);
                    rightArrowView.setImageResource(R.mipmap.ic_right_arrow);
                    rightArrowView.setClickable(true);
                }
            }
        });
    }

    private void setPagerAdapter(){
        chartFragmentAdapter = new ChartFragmentAdapter(getActivity());
        viewPager.setAdapter(chartFragmentAdapter);
    }

}
