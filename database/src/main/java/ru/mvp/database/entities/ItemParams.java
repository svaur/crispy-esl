package ru.mvp.database.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "item_params", schema = "public", catalog = "eslbase")
public class ItemParams {
    private int id;
    private Integer itemParamsGroupId;
    private int paramId;
    private String paramValue;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "item_params_group_id")
    public Integer getItemParamsGroupId() {
        return itemParamsGroupId;
    }

    public void setItemParamsGroupId(Integer itemParamsGroupId) {
        this.itemParamsGroupId = itemParamsGroupId;
    }

    @Basic
    @Column(name = "param_id")
    public int getParamId() {
        return paramId;
    }

    public void setParamId(int paramId) {
        this.paramId = paramId;
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
                paramId == that.paramId &&
                Objects.equals(itemParamsGroupId, that.itemParamsGroupId) &&
                Objects.equals(paramValue, that.paramValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemParamsGroupId, paramId, paramValue);
    }
}
