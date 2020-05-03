package com.fmahieu.timetracker.database.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fmahieu.timetracker.application.App;

public class OperationDao {

    DatabaseHelper dbHelper;

    private static OperationDao instance;

    public static OperationDao getInstance(){
        if(instance == null){
            instance = new OperationDao();
        }
        return instance;
    }

    private OperationDao(){
        dbHelper = new DatabaseHelper();
    }

    public void insert(String tableName, ContentValues values){
        dbHelper.getWritableDatabase().insert(tableName, null, values);
    }

    public Cursor read(String tableName, String[] projection, String selection, String[] selectionArgs, String sortOrder ){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                tableName,   // The table to query
                projection,             // The first part of the query (columns to return)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause (the "?" in the clause)
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        return cursor;
    }
}
