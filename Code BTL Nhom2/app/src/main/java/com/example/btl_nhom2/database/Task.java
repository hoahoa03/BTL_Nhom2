package com.example.btl_nhom2.database;

import java.util.Date;

public class Task {
    private int ID;
    private String taskName;
    private String content;
    private String priority;
    private Date startDay;
    private Date endDay;

    public Task(int ID, String taskName, String content, String priority, Date startDay, Date endDay) {
        this.ID = ID;
        this.taskName = taskName;
        this.content = content;
        this.priority = priority;
        this.startDay = startDay;
        this.endDay = endDay;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Date getStartDay() {
        return startDay;
    }

    public void setStartDay(Date startDay) {
        this.startDay = startDay;
    }

    public Date getEndDay() {
        return endDay;
    }

    public void setEndDay(Date endDay) {
        this.endDay = endDay;
    }
}
