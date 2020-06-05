package com.fmahieu.timetracker.database.sqlite.contracts;

/**
 * Holds constants needed to access the "time_days" table in the database.
 * This table keep tracks of how much time was spent for each activity on each days
 */
public class TimeDaysContract {

    private TimeDaysContract(){}

    public static final String TABLE_NAME = "time_days";
    public static final String COLUMN_NAME_ID = "time_day_id";
    public static final String COLUMN_NAME_TASK_NAME = "task_name";
    /** date should be in the format YYYY-MM-DD*/
    public static final String COLUMN_NAME_DATE = "date";
    /** Store seconds as a long */
    public static final String COLUMN_NAME_TOTAL_TIME = "total_time";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COLUMN_NAME_ID + " STRING PRIMARY KEY," +
                    COLUMN_NAME_TASK_NAME + " TEXT," +
                    COLUMN_NAME_DATE + " TEXT," +
                    COLUMN_NAME_TOTAL_TIME + " INTEGER)";
}
