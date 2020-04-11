package com.fmahieu.timetracker.views.swipeViews;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.fmahieu.timetracker.R;

public class StatsSwipeFragment extends Fragment {

    private ViewPager2 viewPager;
    private ChartFragmentAdapter chartFragmentAdapter;
    private ImageView leftArrowView;
    private ImageView rightArrowView;

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

        currentChartView = 0;

        setViewPager(view);
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

    private void setViewPager(View view){

        viewPager = view.findViewById(R.id.viewPager_statsSwipeFrag);


        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if(position == 1){
                    currentChartView = 1;
                    rightArrowView.setImageResource(R.mipmap.ic_right_arrow_gray);
                    rightArrowView.setClickable(false);
                    leftArrowView.setImageResource(R.mipmap.ic_left_arrow);
                    leftArrowView.setClickable(true);

                }
                if(position == 0){
                    currentChartView = 0;
                    leftArrowView.setImageResource(R.mipmap.ic_left_arrow_gray);
                    leftArrowView.setClickable(false);

                    rightArrowView.setImageResource(R.mipmap.ic_right_arrow);
                    rightArrowView.setClickable(true);
                }
            }
        });
/*
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 1){
                    currentChartView = 1;
                    rightArrowView.setImageResource(R.mipmap.ic_right_arrow_gray);
                    rightArrowView.setClickable(false);
                    leftArrowView.setImageResource(R.mipmap.ic_left_arrow);
                    leftArrowView.setClickable(true);

                }
                if(position == 0){
                    currentChartView = 0;
                    leftArrowView.setImageResource(R.mipmap.ic_left_arrow_gray);
                    leftArrowView.setClickable(false);

                    rightArrowView.setImageResource(R.mipmap.ic_right_arrow);
                    rightArrowView.setClickable(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
*/

    }


    private void setPagerAdapter(){
        chartFragmentAdapter = new ChartFragmentAdapter(getActivity());
        viewPager.setAdapter(chartFragmentAdapter);
    }

}
