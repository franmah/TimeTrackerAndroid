package com.fmahieu.timetracker.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class OperationDao {

    DatabaseHelper dbHelper;

    public OperationDao(Context context){
        dbHelper = new DatabaseHelper(context);
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
