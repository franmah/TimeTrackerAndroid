package com.fmahieu.timetracker.views.swipeViews;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anychart.AnyChartView;
import com.anychart.charts.Pie;
import com.fmahieu.timetracker.R;
import com.fmahieu.timetracker.chartlogic.PieChart;

public class PieChartSwipeFragment extends Fragment {

    private final String TAG = "__PieChartSwipeFragment";

    private AnyChartView anyChartView;
    private LinearLayout progressLinearView; // show loading circle while chart is loading

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pie_chart_fragment, container, false);

        this.anyChartView = view.findViewById(R.id.anyChart_view_pieChartFrag);
        this.anyChartView.setVisibility(View.GONE);
        this.progressLinearView = view.findViewById(R.id.progress_layout_pieChartFragment);
        new LoadPieChartView().execute();


        return view;

    }
    private void load(){
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        Log.i(TAG, "running handler: setting chart visible and progressCircle gone");
                        anyChartView.setVisibility(View.VISIBLE);
                        progressLinearView.setVisibility(View.GONE);
                    }
                },
                500);
    }

    private class LoadPieChartView extends AsyncTask<Void, Void, Pie> {
        protected Pie doInBackground(Void... voids) {
            Log.i(TAG, "doInBackground");
            return new PieChart().getPieChart();
        }

        @Override
        protected void onPostExecute(Pie pieChart) {
            Log.i(TAG, "onPostExecute");
            anyChartView.setChart(pieChart);
            load();
        }
    }

}
