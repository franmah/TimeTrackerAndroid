package com.fmahieu.timetracker.database.roomDb;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {TaskEntity.class}, version = 1, exportSchema = false)
public abstract class RoomTimeTrackerDatabase extends RoomDatabase {

    public static final String DB_NAME = "com.fmahieu.timeTracker.db";
    public abstract RoomTaskDao taskDao();

}
