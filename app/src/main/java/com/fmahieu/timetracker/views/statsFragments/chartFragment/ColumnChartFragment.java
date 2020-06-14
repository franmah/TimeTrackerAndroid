package com.fmahieu.timetracker.views.statsFragments.chartFragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.fmahieu.timetracker.R;
import com.fmahieu.timetracker.logger.Logger;
import com.fmahieu.timetracker.logic.chartlogic.ChartLogic;
import com.fmahieu.timetracker.views.statsFragments.StatsFragment;

import java.util.List;

public class ColumnChartFragment extends Fragment implements ChartFragment  {

    private final String TAG = "__ColumnChartFragment";

    private String fromDate;
    private String toDate;

    private Logger logger = new Logger();
    private AnyChartView anyChartView;
    private LinearLayout progressLoadingView;

    public ColumnChartFragment(String fromDate, String toDate){
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
        View view = inflater.inflate(R.layout.column_chart_fragment, container, false);

        this.anyChartView = view.findViewById(R.id.anyChart_view_columnChartFrag);
        this.anyChartView.setVisibility(View.GONE);
        this.progressLoadingView = view.findViewById(R.id.progress_layout_columnChartFragment);
        loadGraph();
        return view;
    }

    private void showGraph(){
        logger.logMessage(TAG, "showing column graph");
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        anyChartView.setVisibility(View.VISIBLE);
                        progressLoadingView.setVisibility(View.GONE);
                    }
                },
                500);
    }

    @Override
    public void loadGraph() {
        logger.logMessage(TAG, "loading column graph");
        new LoadColumnChartView().execute(fromDate, toDate);
    }

    private class LoadColumnChartView extends AsyncTask<String, Void, Cartesian> {

        protected Cartesian doInBackground(String... strings) {
            if(strings.length == 2) {
                ChartLogic chartLogic = new ChartLogic();
                List<DataEntry> dataEntries = chartLogic.getTimeDayData(strings[0], strings[1]);

                Cartesian cartesian = AnyChart.column();
                Column column = cartesian.column(dataEntries);

                /*
                column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("${%Value}{groupsSeparator: }");

                cartesian.animation(true);
                cartesian.title("Top 10 Cosmetic Products by Revenue");

                cartesian.yScale().minimum(0d);

                cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");

                cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
                cartesian.interactivity().hoverMode(HoverMode.BY_X);

                cartesian.xAxis(0).title("Product");
                cartesian.yAxis(0).title("Revenue");
                 */
                return cartesian;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Cartesian columnChart) {
            anyChartView.setChart(columnChart);
            showGraph();
        }
    }
}
