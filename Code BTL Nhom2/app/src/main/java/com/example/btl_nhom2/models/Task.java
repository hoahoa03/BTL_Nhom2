package com.example.btl_nhom2.models;

public class Task {
    private int ID;
    private String taskName;

    ///0 là chưa hoàn thành, 1 là hoàn thành
    private int priority;
    private String startDay;
    private String startTime;
    private String endDay;
    private String endTime;

    private int categoryID;


    public Task(int ID, String taskName, int priority, String startDay, String startTime, String endDay, String endTime, int categoryID) {
        this.ID = ID;
        this.taskName = taskName;
        this.priority = priority;
        this.startDay = startDay;
        this.startTime = startTime;
        this.endDay = endDay;
        this.endTime = endTime;
        this.categoryID = categoryID;
    }

    public Task() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
