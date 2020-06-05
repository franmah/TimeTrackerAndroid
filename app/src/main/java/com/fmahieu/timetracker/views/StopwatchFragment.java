package com.fmahieu.timetracker.views;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fmahieu.timetracker.R;
import com.fmahieu.timetracker.models.singletons.Tasks;
import com.fmahieu.timetracker.presenters.StopwatchFragmentPresenter;

public class StopwatchFragment extends Fragment {

    private RecyclerView recycler;
    private TaskRecyclerAdapter adapter;
    private Context context;
    private final String TAG = "__StopwatchFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timer_view_fragment, container, false);

        this.recycler = view.findViewById(R.id.task_recycler_view);
        this.context = getContext();

        this.adapter = new TaskRecyclerAdapter(context);
        this.recycler.setLayoutManager(new LinearLayoutManager((context)));
        this.recycler.setAdapter(this.adapter);

        if(!Tasks.getInstance().hasLoaded()){
            Log.i(TAG, "loading tasks");
            new StopwatchFragmentPresenter(this).load();
        }
        return view;
    }

    public void loadViews(){
        adapter.notifyDataSetChanged();
    }

    private void stopTaskStopwatches(){RecyclerView.LayoutManager layoutManager = recycler.getLayoutManager();
        int numItems = layoutManager.getItemCount();
        for(int i = 0; i < numItems; i++) {
            TaskHolder taskHolder = (TaskHolder) recycler.findViewHolderForAdapterPosition(i);
            if(taskHolder != null){
                taskHolder.pauseStopwatch();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        stopTaskStopwatches();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
