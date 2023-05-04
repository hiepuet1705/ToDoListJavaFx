package model;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Task {
    private int id;
    private String datecreated;
    private String deadline;
    private String description;
    private String task;


    public Task() {
    }

    public Task(int id, String datecreated, String deadline, String description, String task) {
        this.id = id;
        this.datecreated = datecreated;
        this.deadline = deadline;
        this.description = description;
        this.task = task;
    }

    public Task(String datecreated, String deadline, String description, String task) {
        this.datecreated = datecreated;
        this.deadline = deadline;
        this.description = description;
        this.task = task;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(String datecreated) {
        this.datecreated = datecreated;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }


}
