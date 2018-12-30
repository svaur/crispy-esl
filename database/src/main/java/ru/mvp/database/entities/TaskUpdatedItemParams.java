package ru.mvp.database.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "task_updated_item_params", schema = "public", catalog = "eslbase")
public class TaskUpdatedItemParams {
    private int id;
    private int status;
    private Tasks tasksByTaskId;
    private ItemParamsGroup itemParamsGroupByItemParamsGroupId;

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
                status == that.status &&
                Objects.equals(tasksByTaskId, that.tasksByTaskId) &&
                Objects.equals(itemParamsGroupByItemParamsGroupId, that.itemParamsGroupByItemParamsGroupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, tasksByTaskId, itemParamsGroupByItemParamsGroupId);
    }

    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    public Tasks getTasksByTaskId() {
        return tasksByTaskId;
    }

    public void setTasksByTaskId(Tasks tasksByTaskId) {
        this.tasksByTaskId = tasksByTaskId;
    }

    @ManyToOne
    @JoinColumn(name = "item_params_group_id", referencedColumnName = "id")
    public ItemParamsGroup getItemParamsGroupByItemParamsGroupId() {
        return itemParamsGroupByItemParamsGroupId;
    }

    public void setItemParamsGroupByItemParamsGroupId(ItemParamsGroup itemParamsGroupByItemParamsGroupId) {
        this.itemParamsGroupByItemParamsGroupId = itemParamsGroupByItemParamsGroupId;
    }
}
