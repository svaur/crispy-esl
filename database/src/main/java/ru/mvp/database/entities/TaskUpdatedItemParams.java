package ru.mvp.database.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "task_updated_item_params", schema = "public", catalog = "eslbase")
public class TaskUpdatedItemParams {
    private int id;
    private Integer taskId;
    private int itemId;
    private int status;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "task_id")
    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    @Basic
    @Column(name = "item_id")
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
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
        TaskUpdatedItemParams that = (TaskUpdatedItemParams) o;
        return id == that.id &&
                itemId == that.itemId &&
                status == that.status &&
                Objects.equals(taskId, that.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskId, itemId, status);
    }
}
