package ru.mvp.database.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Tasks {
    private int id;
    private String taskName;
    private Timestamp startDate;
    private String cronExpression;
    private String barcodes;
    private int status;
    private Collection<TaskResults> taskResultsById;
    private Collection<TaskUpdatedItemParams> taskUpdatedItemParamsById;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "task_name")
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Basic
    @Column(name = "start_date")
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "cron_expression")
    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    @Basic
    @Column(name = "barcodes")
    public String getBarcodes() {
        return barcodes;
    }

    public void setBarcodes(String barcodes) {
        this.barcodes = barcodes;
    }

    @Basic
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tasks tasks = (Tasks) o;
        return id == tasks.id &&
                status == tasks.status &&
                Objects.equals(taskName, tasks.taskName) &&
                Objects.equals(startDate, tasks.startDate) &&
                Objects.equals(cronExpression, tasks.cronExpression) &&
                Objects.equals(barcodes, tasks.barcodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskName, startDate, cronExpression, barcodes, status);
    }

    @OneToMany(mappedBy = "tasksByTaskId")
    public Collection<TaskResults> getTaskResultsById() {
        return taskResultsById;
    }

    public void setTaskResultsById(Collection<TaskResults> taskResultsById) {
        this.taskResultsById = taskResultsById;
    }

    @OneToMany(mappedBy = "tasksByTaskId")
    public Collection<TaskUpdatedItemParams> getTaskUpdatedItemParamsById() {
        return taskUpdatedItemParamsById;
    }

    public void setTaskUpdatedItemParamsById(Collection<TaskUpdatedItemParams> taskUpdatedItemParamsById) {
        this.taskUpdatedItemParamsById = taskUpdatedItemParamsById;
    }
}
