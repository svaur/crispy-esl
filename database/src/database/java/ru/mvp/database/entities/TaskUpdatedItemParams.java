package ru.mvp.database.entities;

import javax.persistence.*;

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

        if (id != that.id) return false;
        if (status != that.status) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + status;
        return result;
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
