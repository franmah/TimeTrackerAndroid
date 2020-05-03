package com.fmahieu.timetracker.database.roomDb;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.room.Room;

import com.fmahieu.timetracker.models.Task;
import java.util.ArrayList;
import java.util.List;

/**
 * Access the database using DAOs
 */
public class TaskRepository {

    private RoomTimeTrackerDatabase database;

    public TaskRepository(Context context){
        database = Room.databaseBuilder(context, RoomTimeTrackerDatabase.class, RoomTimeTrackerDatabase.DB_NAME).build();
    }

    public void insertTask(Task task){
        Log.i("__Testing", "adding task to db");
        final TaskEntity taskEntity = new TaskEntity(task.getName(), task.getDateCreated(), task.getTotalDuration());
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                database.taskDao().insertTask(taskEntity);
                return null;
            }
        }.execute();
    }

    public Task getTask(String task_name) {
        TaskEntity taskEntity = database.taskDao().getTask(task_name).getValue();
        Log.i("__Testing", "entity: " + taskEntity.getTask_name());
        return new Task(taskEntity.getTask_name(), taskEntity.getDate_created(), taskEntity.getTotal_time());
    }

    public Task getTask(int task_id){
        TaskEntity taskEntity = database.taskDao().getTask(task_id).getValue();
        return new Task(taskEntity.getTask_name(), taskEntity.getDate_created(), taskEntity.getTotal_time());
    }

    public List<Task> getAllTasks() {
       List<TaskEntity> entities = database.taskDao().fetchAllTasks().getValue();
       Log.i("__Testing", "loading tasks");
       if(entities == null){
           Log.i("__Testing", "null entitites");
           return new ArrayList<>();
       }

       List<Task> tasks = new ArrayList<>();
       for(int i = 0; i < entities.size(); i++){
           Log.i("__TEsting", "task: " + entities.get(i).getTask_name());
           TaskEntity entity = entities.get(i);
           tasks.add(new Task(entity.getTask_name(), entity.getDate_created(), entity.getTotal_time()));
       }
       return tasks;
    }
}
