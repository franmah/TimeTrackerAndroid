package com.fmahieu.timetracker.database.sqlite.contracts;

public class TasksContract {

    private TasksContract(){}

    public static final String TABLE_NAME = "tasks";
    public static final String COLUMN_NAME_TASK_ID = "task_id";
    public static final String COLUMN_NAME_TASK_NAME = "task_name";
    public static final String COLUMN_NAME_DATE_CREATED = "date_created";
    public static final String COLUMN_NAME_TOTAL_TIME = "total_time";

    public static final String SQL_CREATE_TABLE=
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COLUMN_NAME_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME_TASK_NAME + " TEXT," +
                    COLUMN_NAME_DATE_CREATED + " TEXT," +
                    COLUMN_NAME_TOTAL_TIME + " INTEGER)";
}
