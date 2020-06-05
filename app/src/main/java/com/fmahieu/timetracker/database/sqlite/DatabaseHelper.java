package com.fmahieu.timetracker.database.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fmahieu.timetracker.application.App;
import com.fmahieu.timetracker.database.sqlite.contracts.TasksContract;
import com.fmahieu.timetracker.database.sqlite.contracts.TimeDaysContract;
import com.fmahieu.timetracker.logger.Logger;

/**
 * Create the database. Return the database object used to perform CRUD operations
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "timeTracker.db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(TasksContract.SQL_CREATE_TABLE);
            db.execSQL(TimeDaysContract.SQL_CREATE_TABLE);
        }
        catch (Exception e){
            new Logger().logException("__DatabaseHelper" , "error while creating table", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
