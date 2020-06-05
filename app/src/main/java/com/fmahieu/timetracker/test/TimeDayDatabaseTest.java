package com.fmahieu.timetracker.test;

import com.fmahieu.timetracker.application.App;
import com.fmahieu.timetracker.database.DAO.TimeDayDao;
import com.fmahieu.timetracker.database.sqlite.TimeDaysSqliteDao;
import com.fmahieu.timetracker.logger.Logger;
import com.fmahieu.timetracker.logic.TimeDateLogic.DateOperationLogic;
import com.fmahieu.timetracker.models.TimeDay;

import java.util.List;

public class TimeDayDatabaseTest {
    private final String TAG = "__TimeDayDatabaseTest";

    private final String RESULT_SUCCESS = "SUCCESS";
    private final String RESULT_FAIL = "FAIL";

    private Logger logger = new Logger();
    private TimeDayDao dao = new TimeDaysSqliteDao(App.getContext());

    private int numTests = 3;

    public void executeTests(){
        try{
            int success = 0;
            success += testUpdate();
            success += testGetAllEntriesBetweenDates();
            success += testDeleteEntry();

            logger.logTest(TAG, success + "/" + numTests + " have succeeded");
        }
        catch (Exception e){
           logger.logException(TAG, "error while executing tests", e);
           logger.logTest(TAG, "error while executing tests");
        }
    }

    public int testUpdate(){
        logger.logTest(TAG, "testUpdate()");

        String testResult = RESULT_SUCCESS;

        String taskName = "testDBTimeDayUpdate";
        String date = new DateOperationLogic().getCurrentDate();
        long totalTime = 1000;

        TimeDay timeDay = new TimeDay(date, taskName, totalTime);

        dao.addTimeDayEntry(timeDay);

        TimeDay result = dao.getTimeDayForTaskForToday(taskName);
        long testTotalTime = result.getTotalTime();

        if(testTotalTime != totalTime){
            logger.logTest(TAG, "Error: testTotalTime (" + testTotalTime + "} != totalTime (" + totalTime + "}");
            testResult = "FAIL";
        }

        totalTime = 2000;

        TimeDay updatedTimeDay = new TimeDay(date, taskName, totalTime);
        dao.addTimeDayEntry(updatedTimeDay);

        result = dao.getTimeDayForTaskForToday(taskName);
        testTotalTime = result.getTotalTime();

        if(testTotalTime != 3000) {
            logger.logTest(TAG, "Error: TotalTime from result (" + testTotalTime +
                    ") != totalTime expected (" + totalTime + ")");
            testResult = RESULT_FAIL;
        }

        dao.deleteTimeDayAtDate(taskName, date);

        TimeDay deletedTimeDay = dao.getTimeDayForTaskForToday(taskName);
        if(deletedTimeDay != null){
            logger.logTest(TAG, "Error: deleted time day came back != null");
            testResult = RESULT_FAIL;
        }

        logger.logTest(TAG, "testUpdate done: " + testResult);
        return testResult.equals(RESULT_SUCCESS) ? 1 : 0;
    }

    public int testDeleteEntry(){
        logger.logTest(TAG, "testing delete...");
        String testResult = RESULT_SUCCESS;

        String taskName = "taskTestDelete_exist";
        String date = new DateOperationLogic().getCurrentDate();
        long totalTime = 1000;

        dao.addTimeDayEntry(new TimeDay(date, taskName, totalTime));
        dao.deleteTimeDayAtDate(taskName, date);
        TimeDay timeDay = dao.getTimeDayForTaskForToday(taskName);
        if(timeDay == null){
            return 1;
        }
        else{
            logger.logTest(TAG, "error, the task was not deleted");
            return 0;
        }
    }

    // TODO: replace calls to DAO methods by the interface that will always be used
    public int testGetAllEntriesBetweenDates(){
        String testResult = RESULT_SUCCESS;

        // test date with different months
        String date1 = "01/01/2020";
        String date2 = "02/01/2020";

        // test date withing same month
        String date3 = "03/01/2020";
        String date4 = "03/10/2020";
        String date5 = "03/20/2020";

        String taskName_1 = "testTaskAllEntries_1";
        String taskName_2 = "testTaskAllEntries_2";
        String taskName_3 = "testTaskAllEntries_3";
        String taskName_4 = "testTaskAllEntries_4";
        String taskName_5 = "testTaskAllEntries_5";

        long totalTime = 1000;

        dao.addTimeDayEntry(new TimeDay(date1, taskName_1, totalTime));
        dao.addTimeDayEntry(new TimeDay(date2, taskName_2, totalTime));
        dao.addTimeDayEntry(new TimeDay(date3, taskName_3, totalTime));
        dao.addTimeDayEntry(new TimeDay(date4, taskName_4, totalTime));
        dao.addTimeDayEntry(new TimeDay(date5, taskName_5, totalTime));

        List<TimeDay> list = dao.getAllTimeDayBetweenDates(date1, date5);
        if(list.size() != 5){
            logger.logTest(TAG, "Error: size of list != 5 (size = " + list.size() + ")" );
            for(int i = 0; i < list.size(); i++){
                logger.logTest(TAG, "task #" + i + ", name: " + list.get(i).getTaskName());
            }
            testResult = RESULT_FAIL;
        }

        list = dao.getAllTimeDayBetweenDates(date3, date4);
        if(list.size() != 2){
            logger.logTest(TAG, "Error: size of list != 2 (size = " + list.size() + ")" );
            testResult = RESULT_FAIL;
        }

        dao.deleteTimeDayAtDate(taskName_1, date1);
        dao.deleteTimeDayAtDate(taskName_2, date2);
        dao.deleteTimeDayAtDate(taskName_3, date3);
        dao.deleteTimeDayAtDate(taskName_4, date4);
        dao.deleteTimeDayAtDate(taskName_5, date5);

        logger.logTest(TAG, "testGetAllEntriesBetweenDates done: " + testResult);
        return testResult.equals(RESULT_SUCCESS) ? 1 : 0;

    }
}
