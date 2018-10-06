package ru.mvp.rsreu.db.entity;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Art on 30.09.2018.
 */
@Entity
@Table(name = "ITEMS")
public class Item {

    @Id
    @Column(name = "ItemCode")
    private int itemCode;

    @Column(name = "ItemName", length = 256, nullable = false)
    private String itemName;

    @Column(name = "Quantity", nullable = false)
    private int quantity;

    @Column(name = "Price", nullable = false)
    private double price;

    @Column(name = "PromotionPrice", nullable = false)
    private double promotionPrice;

    @Column(name = "LastUpdated", nullable = false)
    private Date lastUpdated;

    @Column(name = "StorageUnit", nullable = false)
    private String storageUnit;

    @OneToOne(cascade = CascadeType.ALL)
    private ESL esl;

    public Item() {
    }

    public int getItemCode() {
        return itemCode;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(double promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getStorageUnit() {
        return storageUnit;
    }

    public void setStorageUnit(String storageUnit) {
        this.storageUnit = storageUnit;
    }

    public ESL getEsl() {
        return esl;
    }

    public void setEsl(ESL esl) {
        this.esl = esl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (itemCode != item.itemCode) return false;
        if (quantity != item.quantity) return false;
        if (Double.compare(item.price, price) != 0) return false;
        if (Double.compare(item.promotionPrice, promotionPrice) != 0) return false;
        if (itemName != null ? !itemName.equals(item.itemName) : item.itemName != null) return false;
        if (lastUpdated != null ? !lastUpdated.equals(item.lastUpdated) : item.lastUpdated != null) return false;
        if (storageUnit != null ? !storageUnit.equals(item.storageUnit) : item.storageUnit != null) return false;
        return esl != null ? esl.equals(item.esl) : item.esl == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = itemCode;
        result = 31 * result + (itemName != null ? itemName.hashCode() : 0);
        result = 31 * result + quantity;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(promotionPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (lastUpdated != null ? lastUpdated.hashCode() : 0);
        result = 31 * result + (storageUnit != null ? storageUnit.hashCode() : 0);
        result = 31 * result + (esl != null ? esl.hashCode() : 0);
        return result;
    }
}
