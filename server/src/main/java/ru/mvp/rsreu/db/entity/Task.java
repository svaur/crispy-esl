package ru.mvp.rsreu.db.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "TASKS")
 public class Task {

    @Id
    @Column(name = "TaskName", nullable = false)
    private String taskName;

    @Column(name = "TaskType", nullable = false)
    private String taskType;

    @Column(name = "Frequency")
    private String  frequency;

    @Column(name = "LastUpdated")
    private Date lastUpdated;

    @Column(name = "NextSheduled")
    private Date nextSheduled;

    public Task() {
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Date getNextSheduled() {
        return nextSheduled;
    }

    public void setNextSheduled(Date nextSheduled) {
        this.nextSheduled = nextSheduled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return Objects.equals(getTaskName(), task.getTaskName()) &&
                Objects.equals(getTaskType(), task.getTaskType()) &&
                Objects.equals(getFrequency(), task.getFrequency()) &&
                Objects.equals(getLastUpdated(), task.getLastUpdated()) &&
                Objects.equals(getNextSheduled(), task.getNextSheduled());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTaskName(), getTaskType(), getFrequency(), getLastUpdated(), getNextSheduled());
    }
}
