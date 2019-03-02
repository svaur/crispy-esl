package ru.mvp.database.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "item_params_group", schema = "public", catalog = "eslbase")
public class ItemParamsGroup {
    private int id;
    private int itemId;
    private Integer templateId;
    private Timestamp dateAdded;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "template_id")
    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
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
                itemId == that.itemId &&
                Objects.equals(templateId, that.templateId) &&
                Objects.equals(dateAdded, that.dateAdded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemId, templateId, dateAdded);
    }
}
