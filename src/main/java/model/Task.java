package model;

public class Task {
    private long datecreated;
    private long deadline;
    private String description;
    private String task;


    public Task() {
    }

    public Task(long datecreated, long deadline, String description, String task) {
        this.datecreated = datecreated;
        this.deadline = deadline;
        this.description = description;
        this.task = task;
    }

    public long getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(long datecreated) {
        this.datecreated = datecreated;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
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
