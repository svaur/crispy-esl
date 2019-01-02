package ru.mvp.database.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Tasks {
    private int id;
    private String taskName;
    private String cronExpression;
    private int status;
    private Collection<TaskResults> taskResultsById;
    private Collection<TaskUpdatedItemParams> taskUpdatedItemParamsById;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "cron_expression")
    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
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
                Objects.equals(cronExpression, tasks.cronExpression) &&
                Objects.equals(taskResultsById, tasks.taskResultsById) &&
                Objects.equals(taskUpdatedItemParamsById, tasks.taskUpdatedItemParamsById);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskName, cronExpression, status, taskResultsById, taskUpdatedItemParamsById);
    }

    @OneToMany(mappedBy = "tasks_by_task_id")
    public Collection<TaskResults> getTaskResultsById() {
        return taskResultsById;
    }

    public void setTaskResultsById(Collection<TaskResults> taskResultsById) {
        this.taskResultsById = taskResultsById;
    }

    @OneToMany(mappedBy = "tasks_by_task_id")
    public Collection<TaskUpdatedItemParams> getTaskUpdatedItemParamsById() {
        return taskUpdatedItemParamsById;
    }

    public void setTaskUpdatedItemParamsById(Collection<TaskUpdatedItemParams> taskUpdatedItemParamsById) {
        this.taskUpdatedItemParamsById = taskUpdatedItemParamsById;
    }
}
