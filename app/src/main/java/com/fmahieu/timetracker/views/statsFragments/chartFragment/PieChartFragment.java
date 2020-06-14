package com.fmahieu.timetracker.views.statsFragments.chartFragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.fmahieu.timetracker.R;
import com.fmahieu.timetracker.logger.Logger;
import com.fmahieu.timetracker.logic.chartlogic.ChartLogic;

import java.util.List;

public class PieChartFragment extends Fragment {

    private final String TAG = "__PieChartFragment";

    private AnyChartView anyChartView;
    private LinearLayout progressLinearView; // show loading circle while chart is loading
    private Logger logger = new Logger();

    private String fromDate;
    private String toDate;

    public PieChartFragment(String fromDate, String toDate){
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

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
        anyChartView.setVisibility(View.GONE);
        progressLinearView.setVisibility(View.VISIBLE);

        testLoadChart(fromDate, toDate);
        return view;
    }

    private void testLoadChart(final String from, final String to){
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        ChartLogic chartLogic = new ChartLogic();
                        List<DataEntry> dataEntries = chartLogic.getTimeDayData(from, to);

                        Pie pie = AnyChart.pie();
                        pie.data(dataEntries);

                        pie.labels().position("inside");

                        pie.legend().title().enabled(true);
                        pie.legend().title()
                                .text("Activities")
                                .padding(20d, 0d, 8d, 0d);

                        pie.legend()
                                .position("center")
                                .itemsLayout(LegendLayout.HORIZONTAL)
                                .align(Align.CENTER);

                        pie.radius("40%");
                        anyChartView.setChart(pie);
                        progressLinearView.setVisibility(View.GONE);
                        anyChartView.setVisibility(View.VISIBLE);
                    }
                }, 500);
    }
}
