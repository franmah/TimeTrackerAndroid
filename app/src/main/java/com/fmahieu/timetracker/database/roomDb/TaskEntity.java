package com.fmahieu.timetracker.database.roomDb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TaskEntity {

    @PrimaryKey(autoGenerate = true)
    private int task_id;

    @ColumnInfo(name = "task_name")
    private String task_name;

    @ColumnInfo(name = "date_created")
    private String date_created;

    @ColumnInfo(name = "total_time")
    private String total_time;

    public TaskEntity(String task_name, String date_created, String total_time) {
        this.task_name = task_name;
        this.date_created = date_created;
        this.total_time = total_time;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getTotal_time() {
        return total_time;
    }

    public void setTotal_time(String total_time) {
        this.total_time = total_time;
    }
}
