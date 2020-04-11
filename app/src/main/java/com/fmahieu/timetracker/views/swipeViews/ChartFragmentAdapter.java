package com.fmahieu.timetracker.views.swipeViews;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.fmahieu.timetracker.views.ColumnChartFragment;
import com.fmahieu.timetracker.views.PieChartFragment;

public class ChartFragmentAdapter extends FragmentStateAdapter {

    public ChartFragmentAdapter(FragmentActivity fm) {
        super(fm);
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PieChartFragment();
            case 1:
                return new ColumnChartFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}