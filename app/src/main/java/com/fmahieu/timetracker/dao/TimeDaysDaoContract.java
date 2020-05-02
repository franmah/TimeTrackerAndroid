package com.fmahieu.timetracker.dao;

public class TimeDaysDaoContract {

    private TimeDaysDaoContract() {
    }

    public static final String TABLE_NAME = "time_days";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_TASK_ID = "task_id";
    public static final String COLUMN_NAME_DATE = "date";
    public static final String COLUMN_NAME_TOTAL_TIME = "total_time";

    static final String SQL_CREATE_TABLE=
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_TASK_ID + " TEXT," +
                    COLUMN_NAME_DATE + " TEXT," +
                    COLUMN_NAME_TOTAL_TIME + " INTEGER)";
}
