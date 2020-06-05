package com.fmahieu.timetracker.database.DAO;

import com.fmahieu.timetracker.models.Task;
import java.util.List;

/**
 * Call the right functions from the database dao/handlers/repository
 * Handles basic functions, shouldn't have to change if sql technology/library is changed
 */
public interface TaskDao {

    void addTask(Task task);

    void updateTask(Task task);

    List<Task> getAllTasks();

    Task getTask(String taskName);

    void deleteTask(String taskName);
}
