package com.fmahieu.timetracker.views;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.fmahieu.timetracker.R;
import com.fmahieu.timetracker.logic.TimeDateLogic.DateOperationLogic;

public class StatsFragmentOLD extends Fragment {

    private static final String TAG = "__StatsFragment";

    private FragmentManager fragmentManager = null;
    Fragment fragment = null;
    private DateOperationLogic dateOperationLogic;

    private ImageView leftArrowView;
    private ImageView rightArrowView;
    private Button fromDateView;
    private Button toDateView;

    private DatePickerDialog.OnDateSetListener dateSetListener;

    private enum ChartViews { Pie, Column }
    private ChartViews currentChartView;

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

        setUpArrowWidgets(view);
        setUpDateSelectorWidgets(view);

        currentChartView = ChartViews.Pie;

        setFragment();

        return view;

    }

    private void setUpArrowWidgets(View view){

        leftArrowView = view.findViewById(R.id.leftArrow_imageView);
        rightArrowView = view.findViewById(R.id.rightArrow_imageView);

        leftArrowView.setImageResource(R.mipmap.ic_left_arrow_gray);

        leftArrowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentChartView != ChartViews.Pie){
                    currentChartView = ChartViews.Pie;
                    leftArrowView.setImageResource(R.mipmap.ic_left_arrow_gray);
                    leftArrowView.setClickable(false);

                    rightArrowView.setImageResource(R.mipmap.ic_right_arrow);
                    rightArrowView.setClickable(true);
                    setFragment();
                }
            }
        });

        rightArrowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentChartView != ChartViews.Column){
                    currentChartView = ChartViews.Column;
                    rightArrowView.setImageResource(R.mipmap.ic_right_arrow_gray);
                    rightArrowView.setClickable(false);
                    leftArrowView.setImageResource(R.mipmap.ic_left_arrow);
                    leftArrowView.setClickable(true);
                    setFragment();
                };
            }
        });
    }

    private void setUpDateSelectorWidgets(View view){
        this.fromDateView = view.findViewById(R.id.fromDate_statsFragment);
        this.toDateView = view.findViewById(R.id.toDate_statsFragment);

        fromDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = dateOperationLogic.getCurrentYear();
                int month = dateOperationLogic.getCurrentMonth();
                int day = dateOperationLogic.getCurrentDay();

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });


    }

    private void setFragment(){
        Log.i(TAG, "switching fragment");

        fragment = fragmentManager.findFragmentById(R.id.stats_fragment_holder_frameLayout);
        switch (currentChartView){
            case Pie:
                fragment = new PieChartFragment();
                break;
            case Column:
                fragment = new ColumnChartFragment();
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


