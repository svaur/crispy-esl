package ru.mvp.database.entities;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Items {
    private int id;
    private String code;
    private String name;
    private Timestamp lastUpdated;
    private BigInteger price;
    private String storageUnit;
    private Collection<Esls> eslsById;
    private Collection<ItemParamsGroup> itemParamsGroupsById;
    private Collection<TaskUpdatedItemParams> taskUpdatedItemParamsById;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "last_updated")
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Basic
    @Column(name = "price")
    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    @Basic
    @Column(name = "storage_unit")
    public String getStorageUnit() {
        return storageUnit;
    }

    public void setStorageUnit(String storageUnit) {
        this.storageUnit = storageUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Items items = (Items) o;
        return id == items.id &&
                Objects.equals(code, items.code) &&
                Objects.equals(name, items.name) &&
                Objects.equals(lastUpdated, items.lastUpdated) &&
                Objects.equals(price, items.price) &&
                Objects.equals(storageUnit, items.storageUnit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, lastUpdated, price, storageUnit);
    }

    @OneToMany(mappedBy = "itemsByItemsId")
    public Collection<Esls> getEslsById() {
        return eslsById;
    }

    public void setEslsById(Collection<Esls> eslsById) {
        this.eslsById = eslsById;
    }

    @OneToMany(mappedBy = "itemsByItemId")
    public Collection<ItemParamsGroup> getItemParamsGroupsById() {
        return itemParamsGroupsById;
    }

    public void setItemParamsGroupsById(Collection<ItemParamsGroup> itemParamsGroupsById) {
        this.itemParamsGroupsById = itemParamsGroupsById;
    }

    @OneToMany(mappedBy = "itemsByItemId")
    public Collection<TaskUpdatedItemParams> getTaskUpdatedItemParamsById() {
        return taskUpdatedItemParamsById;
    }

    public void setTaskUpdatedItemParamsById(Collection<TaskUpdatedItemParams> taskUpdatedItemParamsById) {
        this.taskUpdatedItemParamsById = taskUpdatedItemParamsById;
    }
}
