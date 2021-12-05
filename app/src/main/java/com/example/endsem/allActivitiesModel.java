package com.example.endsem;

public class allActivitiesModel {

    //Create variables that hold all the data

    String taskName;
    String taskDesc;
    String taskDate;
    String actId;


    public allActivitiesModel(String taskName, String taskDate, String taskDesc, String actId) {
        this.taskName = taskName;
        this.taskDate = taskDate;
        this.taskDesc = taskDesc;
        this.actId = actId;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public String getActId() {
        return actId;
    }
}
