package com.fmahieu.timetracker.database.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.fmahieu.timetracker.database.sqlite.contracts.TasksContract;
import com.fmahieu.timetracker.models.Task;

import java.util.ArrayList;
import java.util.List;

public class TasksDao {

    private OperationDao operationDao;

    public TasksDao(){
        operationDao = OperationDao.getInstance();
    }

    public void addTask(Task task){
        ContentValues values = new ContentValues();
        values.put(TasksContract.COLUMN_NAME_TASK_NAME, task.getName());
        values.put(TasksContract.COLUMN_NAME_TOTAL_TIME, task.getTotalDuration());
        values.put(TasksContract.COLUMN_NAME_DATE_CREATED, task.getDateCreated());

        operationDao.insert(TasksContract.TABLE_NAME, values);
    }

    public List<Task> getAllTasks() throws Exception{

        String[] projection = {
                TasksContract.COLUMN_NAME_TASK_NAME,
                TasksContract.COLUMN_NAME_DATE_CREATED,
                TasksContract.COLUMN_NAME_TOTAL_TIME
        };

        Cursor result = operationDao.read(TasksContract.TABLE_NAME, projection,
                null, null, null);

        List<Task> tasks = new ArrayList<>();
        while(result.moveToNext()){
            String taskName = result.getString(result.getColumnIndexOrThrow(TasksContract.COLUMN_NAME_TASK_NAME));
            String dateCreated = result.getString(result.getColumnIndexOrThrow(TasksContract.COLUMN_NAME_DATE_CREATED));
            String totalDuration = result.getString(result.getColumnIndexOrThrow(TasksContract.COLUMN_NAME_TOTAL_TIME));

            tasks.add(new Task(taskName, dateCreated, totalDuration));
        }
        result.close();

        return tasks;
    }
}
