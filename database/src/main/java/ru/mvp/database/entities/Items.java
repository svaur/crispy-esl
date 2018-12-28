package ru.mvp.database.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

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

        if (id != items.id) return false;
        if (code != null ? !code.equals(items.code) : items.code != null) return false;
        if (name != null ? !name.equals(items.name) : items.name != null) return false;
        if (lastUpdated != null ? !lastUpdated.equals(items.lastUpdated) : items.lastUpdated != null) return false;
        if (storageunit != null ? !storageunit.equals(items.storageunit) : items.storageunit != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lastUpdated != null ? lastUpdated.hashCode() : 0);
        result = 31 * result + (storageunit != null ? storageunit.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "itemsByItemId")
    public Collection<ItemParamsGroup> getItemParamsGroupsById() {
        return itemParamsGroupsById;
    }

    public void setItemParamsGroupsById(Collection<ItemParamsGroup> itemParamsGroupsById) {
        this.itemParamsGroupsById = itemParamsGroupsById;
    }

    @OneToOne
    @JoinColumn(name = "esl_id", referencedColumnName = "id")
    public Esls getEslsByEslId() {
        return eslsByEslId;
    }

    public void setEslsByEslId(Esls eslsByEslId) {
        this.eslsByEslId = eslsByEslId;
    }
}
