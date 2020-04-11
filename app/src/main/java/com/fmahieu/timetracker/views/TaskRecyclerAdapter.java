package com.fmahieu.timetracker.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fmahieu.timetracker.logic.TaskLogic;

import java.util.ArrayList;
import java.util.List;


public class TaskRecyclerAdapter extends RecyclerView.Adapter<TaskHolder> {
    private final String TAG = "__TaskAdapter";

    private List<String> tasks;
    private TaskLogic taskLogic;
    private Context context;

    public TaskRecyclerAdapter(Context context){
        this.taskLogic = new TaskLogic();
        this.context = context;
        this.tasks = taskLogic.getTasksNames();

        if(this.tasks == null){
            this.tasks = new ArrayList<>();
        }
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        return new TaskHolder(layoutInflater, parent, this.context);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        holder.bind(this.tasks.get(position));
    }

    @Override
    public int getItemCount() {
        int newNumTasks = taskLogic.getNumberOfTasks();

        // Update the list of tasks if new tasks have been added
        if(this.tasks.size() != newNumTasks) {
            this.tasks = taskLogic.getTasksNames();
        }
        return newNumTasks;
    }
}
