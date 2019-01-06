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
    //todo будут ли операции расчитаны тут. Если да, то надо будет тип BigInteger
    private Double price;
    private String storageUnit;
    private Collection<ItemParamsGroup> itemParamsGroupsByCode;
    private Esls eslsByEslCode;

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
    @Column(name = "code", unique=true)
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
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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
                Objects.equals(storageUnit, items.storageUnit) &&
                Objects.equals(itemParamsGroupsByCode, items.itemParamsGroupsByCode) &&
                Objects.equals(eslsByEslCode, items.eslsByEslCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, lastUpdated, price, storageUnit, itemParamsGroupsByCode, eslsByEslCode);
    }

    @OneToMany(mappedBy = "itemsByItemCode")
    public Collection<ItemParamsGroup> getItemParamsGroupsByCode() {
        return itemParamsGroupsByCode;
    }

    public void setItemParamsGroupsByCode(Collection<ItemParamsGroup> itemParamsGroupsByCode) {
        this.itemParamsGroupsByCode = itemParamsGroupsByCode;
    }

    @OneToOne
    @JoinColumn(name = "esl_code", referencedColumnName = "code")
    public Esls getEslsByEslCode() {
        return eslsByEslCode;
    }

    public void setEslsByEslCode(Esls eslsByEslCode) {
        this.eslsByEslCode = eslsByEslCode;
    }
}
