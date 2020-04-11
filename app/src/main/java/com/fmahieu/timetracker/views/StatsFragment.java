package com.fmahieu.timetracker.views;

import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.fmahieu.timetracker.R;

public class StatsFragment extends Fragment {

    private static final String TAG = "__StatsFragment";

    private FragmentManager fragmentManager;

    private ImageView leftArrowView;
    private ImageView rightArrowView;

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
        setUpWidgets(view);
        currentChartView = ChartViews.Pie;
        setFragment();
        return view;

    }

    private void setUpWidgets(View view){

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

    private void setFragment(){
        Log.i(TAG, "switching fragment");

        Fragment fragment = fragmentManager.findFragmentById(R.id.stats_fragment_holder_frameLayout);
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


