package com.fmahieu.timetracker.dao;

import com.fmahieu.timetracker.models.TimePair;

/**
 * Holds constants needed to manipulate the time_pair table
 */
public class TimePairContract {
    private TimePairContract(){}

    public static final String TABLE_NAME = "time_pairs";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_TASK_ID = "task_id";
    public static final String COLUMN_NAME_START_TIME = "start_time";
    public static final String COLUMN_NAME_END_TIME = "end_time";
    public static final String COLUMN_NAME_DATE = "date";
    public static final String COLUMN_NAME_TOTAL_TIME = "total_time";

    static final String SQL_CREATE_TABLE=
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                   COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_TASK_ID + " TEXT," +
                    COLUMN_NAME_START_TIME + " TEXT," +
                    COLUMN_NAME_END_TIME + " TEXT," +
                    COLUMN_NAME_DATE + " TEXT," +
                    COLUMN_NAME_TOTAL_TIME + " INTEGER)";



}
