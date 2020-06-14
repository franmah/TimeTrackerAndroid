package com.fmahieu.timetracker.database.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.fmahieu.timetracker.application.App;
import com.fmahieu.timetracker.database.DAO.TaskDao;
import com.fmahieu.timetracker.database.sqlite.contracts.TasksContract;
import com.fmahieu.timetracker.logger.Logger;
import com.fmahieu.timetracker.logic.TimeDateLogic.DateTimeOperationLogic;
import com.fmahieu.timetracker.models.Task;

import java.util.ArrayList;
import java.util.List;

public class TasksSqliteDao implements TaskDao {

    private final String TAG = "__TasksSqliteDao";

    private OperationSqliteDao operationDao;
    private Logger logger = new Logger();

    private String[] projection = {
            TasksContract.COLUMN_NAME_TASK_NAME,
            TasksContract.COLUMN_NAME_DATE_CREATED,
            TasksContract.COLUMN_NAME_TOTAL_TIME
    };

    public TasksSqliteDao(){
        operationDao = OperationSqliteDao.getInstance(App.getContext());
    }

    @Override
    public void addTask(Task task){
        ContentValues values = new ContentValues();
        values.put(TasksContract.COLUMN_NAME_TASK_NAME, task.getName());
        values.put(TasksContract.COLUMN_NAME_TOTAL_TIME, task.getTotalDuration());
        values.put(TasksContract.COLUMN_NAME_DATE_CREATED, task.getDateCreated());

        operationDao.insert(TasksContract.TABLE_NAME, values);
    }

    public void updateTaskName(String oldName, String newName) {

        ContentValues values = new ContentValues();
        values.put(TasksContract.COLUMN_NAME_TASK_NAME, newName);

        // which row to update
        String selection = TasksContract.COLUMN_NAME_TASK_NAME + " LIKE ?";
        String[] selectionArgs = {
                oldName
        };

        operationDao.update(TasksContract.TABLE_NAME, values, selection, selectionArgs);
    }

    @Override
    public void updateTask(Task task) {
        logger.logMessage(TAG, "updating db for task: " + task.getName());
        String duration = task.getTotalDuration();
        String name = task.getName();
        logger.logDebug(TAG, "duration of task: " + duration);

        ContentValues values = new ContentValues();
        values.put(TasksContract.COLUMN_NAME_TOTAL_TIME, duration);

        // which row to update
        String selection = TasksContract.COLUMN_NAME_TASK_NAME + " LIKE ?";
        String[] selectionArgs = {
                name
        };

        operationDao.update(TasksContract.TABLE_NAME, values, selection, selectionArgs);
    }

    @Override
    public List<Task> getAllTasks(){
        logger.logMessage(TAG, "getting all tasks");
        try {
            String sortBy = TasksContract.COLUMN_NAME_TASK_NAME;

            Cursor result = operationDao.read(TasksContract.TABLE_NAME, projection,
                    null, null, null, sortBy);

            List<Task> tasks = new ArrayList<>();
            while (result.moveToNext()) {
                String taskName = result.getString(result.getColumnIndexOrThrow(TasksContract.COLUMN_NAME_TASK_NAME));
                String dateCreated = result.getString(result.getColumnIndexOrThrow(TasksContract.COLUMN_NAME_DATE_CREATED));
                String totalDuration = result.getString(result.getColumnIndexOrThrow(TasksContract.COLUMN_NAME_TOTAL_TIME));
                logger.logDebug(TAG, "totalDuration from db: " + totalDuration);
                tasks.add(new Task(taskName, dateCreated, totalDuration));
            }
            result.close();

            return tasks;
        }
        catch (Exception e){
            logger.logException(TAG, "Error when getting all tasks", e);
            return null;
        }
    }

    @Override
    public Task getTask(String taskName) {
        try{
            String selection = TasksContract.COLUMN_NAME_TASK_NAME + " = ?";
            String[] selectionArgs = {
                    taskName
            };

            Cursor result = operationDao.read(TasksContract.TABLE_NAME, projection, selection,
                    selectionArgs, null, null);

            if(result.getCount() != 1){
                logger.logDebug(TAG, "getTask: result != 1 (result = " + result.getCount() + ")");
                return null;
            }

            result.moveToNext();
            taskName = result.getString(result.getColumnIndexOrThrow(TasksContract.COLUMN_NAME_TASK_NAME));
            String dateCreated = result.getString(result.getColumnIndexOrThrow(TasksContract.COLUMN_NAME_DATE_CREATED));
            String totalTime = result.getString(result.getColumnIndexOrThrow(TasksContract.COLUMN_NAME_TOTAL_TIME));

            return new Task(taskName, dateCreated, totalTime);

        }
        catch (Exception e){
            logger.logException(TAG, "error when getting task: " + taskName, e);
            return null;
        }
    }

    @Override
    public void deleteTask(String taskName) {
        try {
            if (taskName == null) {
                logger.logError(TAG, "delete task: taskName is null");
                return;
            }

            String selection = TasksContract.COLUMN_NAME_TASK_NAME + " = ?";
            String[] selectionArgs = {
                    taskName
            };

            operationDao.delete(TasksContract.TABLE_NAME, selection, selectionArgs);
        }
        catch (Exception e){
            logger.logException(TAG, "error while trying to delete task: " + taskName, e);
        }
    }
}
