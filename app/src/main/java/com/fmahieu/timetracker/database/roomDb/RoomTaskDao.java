package com.fmahieu.timetracker.database.roomDb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fmahieu.timetracker.models.Task;

import java.util.List;

import com.fmahieu.timetracker.database.roomDb.TaskEntity;

@Dao
public interface RoomTaskDao {

    @Insert
    Long insertTask(TaskEntity task);

    @Query("SELECT * FROM TaskEntity ")
    LiveData<List<TaskEntity>> fetchAllTasks();

    @Query("SELECT * FROM TaskEntity WHERE task_id =:taskId")
    LiveData<TaskEntity> getTask(int taskId);

    @Query("SELECT * FROM TaskEntity WHERE task_id =:taskName")
    LiveData<TaskEntity> getTask(String taskName);

    @Update
    void updateTask(TaskEntity taskEntity);

    @Delete
    void deleteTask(TaskEntity taskEntity);

}
