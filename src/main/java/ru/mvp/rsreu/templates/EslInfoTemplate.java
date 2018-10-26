package ru.mvp.rsreu.templates;

/**
 * объект, содержащий информацию для отображения на ценнике
 */
public class EslInfoTemplate {
    private String goodName;
    private String goodSecondName;
    private String oldCost;
    private String newCost;
    private String currency;
    private String vendorCode;

    public String getGoodName() {
        return goodName;
    }

    public String getGoodSecondName() {
        return goodSecondName;
    }

    public String getOldCost() {
        return oldCost;
    }

    public String getNewCost() {
        return newCost;
    }

    public String getCurrency() {
        return currency;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public EslInfoTemplate(String goodName, String goodSecondName, String oldCost, String newCost, String currency, String vendorCode) {
        this.goodName = goodName;
        this.goodSecondName = goodSecondName;
        this.oldCost = oldCost;
        this.newCost = newCost;
        this.currency = currency;
        this.vendorCode = vendorCode;
    }
}
