package com.fmahieu.timetracker.MockData;

import com.fmahieu.timetracker.logic.TimeDateLogic.DateTimeOperationLogic;
import com.fmahieu.timetracker.models.singletons.Tasks;

import java.time.LocalTime;

public class DummyTasks {

    private Tasks tasks;
    private DateTimeOperationLogic timeOperationLogic;

    public DummyTasks(){
        this.tasks = Tasks.getInstance();
        timeOperationLogic = new DateTimeOperationLogic();
    }

    public void AddTasks(int numTasks){
        for(int i = 0; i < numTasks; i++){
            //this.tasks.addTask("dummy activity #" + i, );
        }
    }

    public void AddTasksWithDuration(){
        LocalTime t1;
        LocalTime t2;
        String duration;

        t1 = LocalTime.parse("06:30");
        t2 = LocalTime.parse("07:30");
        duration = timeOperationLogic.getDurationBetweenTwoDateTimes(t1.toString(), t2.toString());
        //this.tasks.addTask("OneHourActivity");
        this.tasks.setTotalTime("OneHourActivity", duration);

        t1 = LocalTime.parse("06:30");
        t2 = LocalTime.parse("08:30");
        duration = timeOperationLogic.getDurationBetweenTwoDateTimes(t1.toString(), t2.toString());
       // this.tasks.addTask("TwoHourActivity");
        this.tasks.setTotalTime("TwoHourActivity", duration);

        t1 = LocalTime.parse("06:30");
        t2 = LocalTime.parse("09:30");
        duration = timeOperationLogic.getDurationBetweenTwoDateTimes(t1.toString(), t2.toString());
        //this.tasks.addTask("ThreeHour");
        this.tasks.setTotalTime("ThreeHourActivity", duration);

    }
}
