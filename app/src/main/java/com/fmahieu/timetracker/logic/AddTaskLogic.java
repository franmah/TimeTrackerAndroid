package com.fmahieu.timetracker.logic;

import com.fmahieu.timetracker.models.singletons.Tasks;

public class AddTaskLogic {

    private Tasks tasks;

    public AddTaskLogic(){
        this.tasks = Tasks.getInstance();
    }

    public void addTask(String newTaskName) {
        this.tasks.addTask(newTaskName);
    }

    public boolean doesTaskExist(String taskName){
        return tasks.doesTaskExist(taskName);
    }
}
