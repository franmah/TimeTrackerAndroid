package com.fmahieu.timetracker.views;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anychart.AnyChartView;
import com.anychart.charts.Cartesian;
import com.fmahieu.timetracker.R;
import com.fmahieu.timetracker.chartlogic.ColumnChart;

public class ColumnChartFragment extends Fragment {

    private AnyChartView anyChartView;
    private LinearLayout progressLoadingView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.column_chart_fragment, container, false);

        this.anyChartView = view.findViewById(R.id.anyChart_view_columnChartFrag);
        this.anyChartView.setVisibility(View.GONE);
        this.progressLoadingView = view.findViewById(R.id.progress_layout_columnChartFragment);
        new LoadColumnChartView().execute();
        return view;

    }

    private void load(){
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        anyChartView.setVisibility(View.VISIBLE);
                        progressLoadingView.setVisibility(View.GONE);
                    }
                },
                1000);
    }

    private class LoadColumnChartView extends AsyncTask<Void, Void, Cartesian> {

        protected Cartesian doInBackground(Void... voids) {
            return new ColumnChart().getColumnChart();
        }

        @Override
        protected void onPostExecute(Cartesian columnChart) {
            anyChartView.setChart(columnChart);
            load();
        }
    }
}
