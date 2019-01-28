package ru.mvp.database.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "item_params", schema = "public", catalog = "eslbase")
public class ItemParams {
    private int id;
    private String paramValue;
    private ItemParamsGroup itemsByItemId;
    private DirectoryParams directoryParamsByParamId;

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
    @Column(name = "param_value")
    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemParams that = (ItemParams) o;
        return id == that.id &&
                Objects.equals(paramValue, that.paramValue) &&
                Objects.equals(itemsByItemId, that.itemsByItemId) &&
                Objects.equals(directoryParamsByParamId, that.directoryParamsByParamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paramValue, itemsByItemId, directoryParamsByParamId);
    }

    @ManyToOne
    @JoinColumn(name = "item_params_group_id", referencedColumnName = "id")
    public ItemParamsGroup getitemsByItemId() {
        return itemsByItemId;
    }

    public void setitemsByItemId(ItemParamsGroup itemsByItemId) {
        this.itemsByItemId = itemsByItemId;
    }

    @ManyToOne
    @JoinColumn(name = "param_id", referencedColumnName = "id", nullable = false)
    public DirectoryParams getDirectoryParamsByParamId() {
        return directoryParamsByParamId;
    }

    public void setDirectoryParamsByParamId(DirectoryParams directoryParamsByParamId) {
        this.directoryParamsByParamId = directoryParamsByParamId;
    }
}
