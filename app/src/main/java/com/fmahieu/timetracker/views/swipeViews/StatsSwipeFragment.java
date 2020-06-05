package com.fmahieu.timetracker.views.swipeViews;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.fmahieu.timetracker.R;
import com.fmahieu.timetracker.database.DAO.TimeDayDao;
import com.fmahieu.timetracker.logger.Logger;
import com.fmahieu.timetracker.logic.TimeDateLogic.DateOperationLogic;
import com.fmahieu.timetracker.presenters.StatsSwipePresenter;

public class StatsSwipeFragment extends Fragment {

    private final String TAG = "__StatsSwipeFragment";
    private Logger logger = new Logger();

    private StatsSwipePresenter presenter;

    private ViewPager2 viewPager;
    private ChartFragmentAdapter chartFragmentAdapter;

    private ImageView leftArrowView;
    private ImageView rightArrowView;

    private DatePickerDialog.OnDateSetListener fromDateSetListener;
    private DatePickerDialog.OnDateSetListener toDateSetListener;
    private Button fromDateView;
    private Button toDateView;

    private String fromDate;
    private String toDate;

    DateOperationLogic dateOperationLogic;

    int currentChartView;

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
        this.presenter = new StatsSwipePresenter();

        setViewPager(view);
        setDateSelectorViews(view);
        setPagerAdapter();
        setArrowViews(view);

        return view;
    }

    private void setArrowViews(View view) {

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

        // show stats for the last 30 days
        fromDate = dateOperationLogic.getCurrentDate();
        toDate = dateOperationLogic.getDateOneMonthAgo();

        // Set up initial dates : from "1 month ago" to "today"
        String fromDateText = dateOperationLogic.getDateOneMonthAgo();
        fromDateView.setText(fromDateText);

        String currentDate = dateOperationLogic.getCurrentDate();
        toDateView.setText(currentDate);

        // Set up listeners

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

        // listeners for when the date is set
        fromDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                Log.i(TAG, "date: " + year + "/" + month + "/" + day);
                String date = dateOperationLogic.createDate(year, month, day);
                Log.i(TAG, "date received: " + date);
                fromDateView.setText(date);
                updateStatsWindow();
            }
        };

        toDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                Log.i(TAG, "date: " + year + "/" + month + "/" + day);
                String date = dateOperationLogic.createDate(year, month, day);
                Log.i(TAG, "date received: " + date);
                toDateView.setText(date);
                updateStatsWindow();
            }
        };
    }

    /**
     * Notify the current chart fragment to reload the graph with updated data.
     */
    public void reloadFragmentChart(){
        //this.chartFragmentAdapter.notifyDataSetChanged();
        this.chartFragmentAdapter.notifyItemChanged(this.viewPager.getCurrentItem());
    }

    /**
     * Fetch data updated to fit the dates chosen by user.
     */
    private void updateStatsWindow(){
        new FetchWindowData().execute();
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

    @SuppressLint("StaticFieldLeak")
    private class FetchWindowData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            presenter.updateStatsWindow(fromDate, toDate);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            reloadFragmentChart();
        }
    }

}
