package ru.mvp.database.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Items {
    private int id;
    private String code;
    private String name;
    private Timestamp lastUpdated;
    private String storageunit;
    private Collection<ItemParamsGroup> itemParamsGroupsById;
    private Esls eslsByEslId;

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
    @Column(name = "storageunit")
    public String getStorageunit() {
        return storageunit;
    }

    public void setStorageunit(String storageunit) {
        this.storageunit = storageunit;
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
                Objects.equals(storageunit, items.storageunit) &&
                Objects.equals(itemParamsGroupsById, items.itemParamsGroupsById) &&
                Objects.equals(eslsByEslId, items.eslsByEslId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, lastUpdated, storageunit, itemParamsGroupsById, eslsByEslId);
    }

    @OneToMany(mappedBy = "itemsByItemId")
    public Collection<ItemParamsGroup> getItemParamsGroupsById() {
        return itemParamsGroupsById;
    }

    public void setItemParamsGroupsById(Collection<ItemParamsGroup> itemParamsGroupsById) {
        this.itemParamsGroupsById = itemParamsGroupsById;
    }

    @ManyToOne
    @JoinColumn(name = "esl_id", referencedColumnName = "id")
    public Esls getEslsByEslId() {
        return eslsByEslId;
    }

    public void setEslsByEslId(Esls eslsByEslId) {
        this.eslsByEslId = eslsByEslId;
    }
}
