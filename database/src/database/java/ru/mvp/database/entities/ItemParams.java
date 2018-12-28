package ru.mvp.database.entities;

import javax.persistence.*;

@Entity
@Table(name = "item_params", schema = "public", catalog = "eslbase")
public class ItemParams {
    private int id;
    private String paramValue;
    private ItemParamsGroup itemParamsGroupByItemParamsGroupId;
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

        if (id != that.id) return false;
        if (paramValue != null ? !paramValue.equals(that.paramValue) : that.paramValue != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (paramValue != null ? paramValue.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "item_params_group_id", referencedColumnName = "id")
    public ItemParamsGroup getItemParamsGroupByItemParamsGroupId() {
        return itemParamsGroupByItemParamsGroupId;
    }

    public void setItemParamsGroupByItemParamsGroupId(ItemParamsGroup itemParamsGroupByItemParamsGroupId) {
        this.itemParamsGroupByItemParamsGroupId = itemParamsGroupByItemParamsGroupId;
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
