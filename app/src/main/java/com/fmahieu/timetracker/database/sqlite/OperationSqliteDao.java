package com.fmahieu.timetracker.database.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class OperationSqliteDao {

    DatabaseHelper dbHelper;

    private static OperationSqliteDao instance;

    public static OperationSqliteDao getInstance(Context context){
        if(instance == null){
            instance = new OperationSqliteDao(context);
        }
        return instance;
    }

    private OperationSqliteDao(Context context){
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * Safe insert: if row is already in table it will be updated.
     * @param tableName
     * @param values
     */
    public void insert(String tableName, ContentValues values){
        dbHelper.getWritableDatabase().insertWithOnConflict(tableName, null,
                values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public Cursor read(String tableName, String[] projection, String selection,
                       String[] selectionArgs, String sortOrder, String groupBy ){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                tableName,   // The table to query
                projection,             // The first part of the query (columns to return)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause (the "?" in the clause)
                groupBy,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        return cursor;
    }

    public void update(String tableName, ContentValues values, String selection, String[] selectionArg) {
        dbHelper.getWritableDatabase().update(tableName, values, selection, selectionArg);
    }

    public void delete(String tableName, String selection, String[] selectionArgs) {
        dbHelper.getWritableDatabase().delete(tableName, selection, selectionArgs);
    }
}
