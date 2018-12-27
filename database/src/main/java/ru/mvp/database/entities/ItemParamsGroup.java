package ru.mvp.database.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

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

        if (id != that.id) return false;
        if (dateAdded != null ? !dateAdded.equals(that.dateAdded) : that.dateAdded != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (dateAdded != null ? dateAdded.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "itemParamsGroupByItemParamsGroupId")
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

    @OneToMany(mappedBy = "itemParamsGroupByItemParamsGroupId")
    public Collection<TaskUpdatedItemParams> getTaskUpdatedItemParamsById() {
        return taskUpdatedItemParamsById;
    }

    public void setTaskUpdatedItemParamsById(Collection<TaskUpdatedItemParams> taskUpdatedItemParamsById) {
        this.taskUpdatedItemParamsById = taskUpdatedItemParamsById;
    }
}
