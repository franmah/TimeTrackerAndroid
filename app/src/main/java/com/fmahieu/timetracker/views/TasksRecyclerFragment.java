package com.fmahieu.timetracker.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fmahieu.timetracker.R;
import com.google.android.material.card.MaterialCardView;

public class TasksRecyclerFragment extends Fragment {

    private RecyclerView recycler;
    private TaskRecyclerAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timer_view_fragment, container, false);

        this.recycler = view.findViewById(R.id.task_recycler_view);
        this.adapter = new TaskRecyclerAdapter(getContext());

        this.recycler.setLayoutManager(new LinearLayoutManager((getContext())));
        this.recycler.setAdapter(this.adapter);

        return view;
    }
}
