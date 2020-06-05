package com.fmahieu.timetracker.database.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.fmahieu.timetracker.database.DAO.TimeDayDao;
import com.fmahieu.timetracker.database.sqlite.contracts.TimeDaysContract;
import com.fmahieu.timetracker.logger.Logger;
import com.fmahieu.timetracker.logic.TimeDateLogic.DateOperationLogic;
import com.fmahieu.timetracker.models.TimeDay;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Handles operation on the days table
 */
public class TimeDaysSqliteDao implements TimeDayDao {

    private final String TAG = "__TimeDaysSqliteDao";
    private Logger logger = new Logger();

    private OperationSqliteDao operationDao;
    private DateAdapter dateAdapter;

    private String[] projection = {
            TimeDaysContract.COLUMN_NAME_ID,
            TimeDaysContract.COLUMN_NAME_TASK_NAME,
            TimeDaysContract.COLUMN_NAME_DATE,
            TimeDaysContract.COLUMN_NAME_TOTAL_TIME
    };

    public TimeDaysSqliteDao(Context context) {
        this.operationDao = OperationSqliteDao.getInstance(context);
        this.dateAdapter = new DateAdapter();
    }

    /**
     * Add or update a TimeDay entry to the database.
     * Date passed within TimeDay object can be of any format.
     * @param timeDay the TimeDay object to add
     */
    @Override
    public void addTimeDayEntry(TimeDay timeDay){
        if(timeDay == null){
            logger.logError(TAG, "timeDay passed is null");
        }

        String taskName = timeDay.getTaskName();
        long totalTime = timeDay.getTotalTime();
        String date = timeDay.getDate();
        String id = "";

        date = dateAdapter.formatDateForDatabase(date);
        if(date == null){
            logger.logError(TAG, "converted date is null");
            return;
        }

        // fetch entry for the task (in case there is already an entry for the day)
        TimeDay previousTimeDayEntry = getTimeDayForTaskForToday(taskName);

        // if entry exists, update entry to add
        if(previousTimeDayEntry != null){
            totalTime += previousTimeDayEntry.getTotalTime();
            id = previousTimeDayEntry.getId();
        }
        else{
            id = UUID.randomUUID().toString();
        }

        // create parameters to safely insert new entry
        ContentValues values = new ContentValues();
        values.put(TimeDaysContract.COLUMN_NAME_ID, id);
        values.put(TimeDaysContract.COLUMN_NAME_TASK_NAME, taskName);
        values.put(TimeDaysContract.COLUMN_NAME_DATE, date);
        values.put(TimeDaysContract.COLUMN_NAME_TOTAL_TIME, totalTime);

        operationDao.insert(TimeDaysContract.TABLE_NAME, values);
        logger.logMessage(TAG, "entry added/updated to " + TimeDaysContract.TABLE_NAME + " table");
    }

    @Override
    public void deleteTimeDayAtDate(String taskName, String date){
        if(taskName == null || date == null){
            logger.logError(TAG, "taskName (" + taskName + ") or date (" + date + ") is null");
            return;
        }
        date = dateAdapter.formatDateForDatabase(date);

        String selection = TimeDaysContract.COLUMN_NAME_TASK_NAME + " = ? AND " +
                            TimeDaysContract.COLUMN_NAME_DATE + " = ?";

        String[] selectionArgs = {
                taskName,
                date
        };

        operationDao.delete(TimeDaysContract.TABLE_NAME, selection, selectionArgs);
        logger.logMessage(TAG, "entry deleted from " + TimeDaysContract.TABLE_NAME + " table");
    }

    public void deleteTimeDay(String taskName) {
        if(taskName == null){
            logger.logError(TAG, "trying to delete a task with a null name");
            return;
        }
        String selection = TimeDaysContract.COLUMN_NAME_TASK_NAME + " = ?";
        String[] selectionArgs = {
                taskName,
        };

        operationDao.delete(TimeDaysContract.TABLE_NAME, selection, selectionArgs);
        logger.logMessage(TAG, "task entries deleted from " + TimeDaysContract.TABLE_NAME + " table");
    }

    @Override
    public TimeDay getTimeDayForTaskForToday(String taskName){
        String currentDate = new DateOperationLogic().getCurrentDate();
        return getTimeDayForTaskBetweenDates(taskName, currentDate, currentDate);
    }

    @Override
    public TimeDay getTimeDayForTaskBetweenDates(String taskName, String from, String to){
        if(taskName == null || from == null || to == null){
            logger.logError(TAG, "taskName or from or to is null");
        }

        List<TimeDay> timeDays = getAllTimeDayBetweenDates(from, to);

        int timeDays_size = timeDays.size();
        for(int i = 0; i < timeDays_size; i++){
            if(timeDays.get(i).getTaskName().equals(taskName)){
                return timeDays.get(i);
            }
        }
        return null;
    }

    @Override
    public List<TimeDay> getAllTimeDayBetweenDates(String from, String to){
        from = dateAdapter.formatDateForDatabase(from);
        to = dateAdapter.formatDateForDatabase(to);

        String selection = TimeDaysContract.COLUMN_NAME_DATE + " BETWEEN ? AND ?";

        String[] selectionArgs = {
                from,
                to
        };

        String sortBy = TimeDaysContract.COLUMN_NAME_TASK_NAME;
        String groupBy = TimeDaysContract.COLUMN_NAME_TASK_NAME;

        Cursor result = operationDao.read(TimeDaysContract.TABLE_NAME, projection, selection,
                selectionArgs, sortBy, groupBy);

        List<TimeDay> timeDays = new ArrayList<>();
        while(result.moveToNext()){
            String taskId = result.getString(result.getColumnIndexOrThrow(TimeDaysContract.COLUMN_NAME_ID));
            String taskName = result.getString(result.getColumnIndexOrThrow(TimeDaysContract.COLUMN_NAME_TASK_NAME));
            String date = result.getString(result.getColumnIndexOrThrow(TimeDaysContract.COLUMN_NAME_DATE));
            long totalTime = result.getLong(result.getColumnIndexOrThrow(TimeDaysContract.COLUMN_NAME_TOTAL_TIME));

            date = dateAdapter.formatDateForApp(date);

            logger.logDebug(TAG, "retrieved: " + taskId + ", " + taskName + ", " + totalTime + ", " + date);
            timeDays.add(new TimeDay(taskName, date, totalTime, taskId));
        }

        logger.logDebug(TAG, "returning " + timeDays.size() + " items");
        return timeDays;
    }

}
