package com.fmahieu.timetracker.views;

import android.annotation.SuppressLint;
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
import com.fmahieu.timetracker.logger.Logger;
import com.fmahieu.timetracker.logic.chartlogic.PieChart;

public class PieChartFragment extends Fragment {

    private final String TAG = "__PieChartFragment";

    private AnyChartView anyChartView;
    private LinearLayout progressLinearView; // show loading circle while chart is loading

    private Logger logger = new Logger();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pie_chart_fragment, container, false);

        this.anyChartView = view.findViewById(R.id.anyChart_view_pieChartFrag);
        this.progressLinearView = view.findViewById(R.id.progress_layout_pieChartFragment);
        loadGraph(view);
        return view;
    }

    public void loadGraph(View view){
        logger.logMessage(TAG, "loading graph");
        this.anyChartView.setVisibility(View.GONE);
        this.progressLinearView.setVisibility(View.VISIBLE);
        new LoadPieChartView().execute();
    }

    /**
     * Modify views to show the loaded chart
     * Because the loading is very fast it has a 1 second delay to avoid switching from
     * the loading animation to displaying the chart too fast. Even though it is fast, it is not
     * fast enough and the user need to know the chart is being loaded.
     */
    private void notifyChartHasLoaded(){
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        anyChartView.setVisibility(View.VISIBLE);
                        progressLinearView.setVisibility(View.GONE);
                    }
                },
                1000);
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadPieChartView extends AsyncTask<Void, Void, Pie> {
        protected Pie doInBackground(Void... voids) {
            return new PieChart().getPieChart();
        }

        @Override
        protected void onPostExecute(Pie pieChart) {
            anyChartView.setChart(pieChart);
            notifyChartHasLoaded();
        }
    }


}
