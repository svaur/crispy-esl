package ru.mvp.database.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "item_params_group", schema = "public", catalog = "eslbase")
public class ItemParamsGroup {
    private int id;
    private Timestamp dateAdded;
    private Collection<ItemParams> itemParamsById;
    private Items itemsByItemId;
    private Templates templatesByTemplateId;
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
    @Column(name = "date_added")
    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemParamsGroup that = (ItemParamsGroup) o;
        return id == that.id &&
                Objects.equals(dateAdded, that.dateAdded) &&
                Objects.equals(itemParamsById, that.itemParamsById) &&
                Objects.equals(itemsByItemId, that.itemsByItemId) &&
                Objects.equals(templatesByTemplateId, that.templatesByTemplateId) &&
                Objects.equals(taskUpdatedItemParamsById, that.taskUpdatedItemParamsById);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateAdded, itemParamsById, itemsByItemId, templatesByTemplateId, taskUpdatedItemParamsById);
    }

    @OneToMany(mappedBy = "itemsByItemId")
    public Collection<ItemParams> getItemParamsById() {
        return itemParamsById;
    }

    public void setItemParamsById(Collection<ItemParams> itemParamsById) {
        this.itemParamsById = itemParamsById;
    }

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
    public Items getItemsByItemId() {
        return itemsByItemId;
    }

    public void setItemsByItemId(Items itemsByItemId) {
        this.itemsByItemId = itemsByItemId;
    }

    @ManyToOne
    @JoinColumn(name = "template_id", referencedColumnName = "id")
    public Templates getTemplatesByTemplateId() {
        return templatesByTemplateId;
    }

    public void setTemplatesByTemplateId(Templates templatesByTemplateId) {
        this.templatesByTemplateId = templatesByTemplateId;
    }

    @OneToMany(mappedBy = "itemsByItemId")
    public Collection<TaskUpdatedItemParams> getTaskUpdatedItemParamsById() {
        return taskUpdatedItemParamsById;
    }

    public void setTaskUpdatedItemParamsById(Collection<TaskUpdatedItemParams> taskUpdatedItemParamsById) {
        this.taskUpdatedItemParamsById = taskUpdatedItemParamsById;
    }
}
