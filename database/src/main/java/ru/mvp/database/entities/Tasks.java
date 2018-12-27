package ru.mvp.database.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Tasks {
    private int id;
    private String taskname;
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
    @Column(name = "taskname")
    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
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

        if (id != tasks.id) return false;
        if (status != tasks.status) return false;
        if (taskname != null ? !taskname.equals(tasks.taskname) : tasks.taskname != null) return false;
        if (cronExpression != null ? !cronExpression.equals(tasks.cronExpression) : tasks.cronExpression != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (taskname != null ? taskname.hashCode() : 0);
        result = 31 * result + (cronExpression != null ? cronExpression.hashCode() : 0);
        result = 31 * result + status;
        return result;
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
