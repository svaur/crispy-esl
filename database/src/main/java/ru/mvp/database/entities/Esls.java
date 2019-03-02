package ru.mvp.database.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Esls {
    private int id;
    private String code;
    private String batteryLevel;
    private byte[] currentImage;
    private byte[] nextImage;
    private String connectivity;
    private String eslType;
    private String firmware;
    private Timestamp lastUpdate;
    private Timestamp registrationDate;
    private Timestamp startDate;
    private String status;
    private Items itemsByItemsId;

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
    @Column(name = "battery_level")
    public String getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(String batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    @Basic
    @Column(name = "current_image")
    public byte[] getCurrentImage() {
        return currentImage;
    }

    public void setCurrentImage(byte[] currentImage) {
        this.currentImage = currentImage;
    }

    @Basic
    @Column(name = "next_image")
    public byte[] getNextImage() {
        return nextImage;
    }

    public void setNextImage(byte[] nextImage) {
        this.nextImage = nextImage;
    }

    @Basic
    @Column(name = "connectivity")
    public String getConnectivity() {
        return connectivity;
    }

    public void setConnectivity(String connectivity) {
        this.connectivity = connectivity;
    }

    @Basic
    @Column(name = "esl_type")
    public String getEslType() {
        return eslType;
    }

    public void setEslType(String eslType) {
        this.eslType = eslType;
    }

    @Basic
    @Column(name = "firmware")
    public String getFirmware() {
        return firmware;
    }

    public void setFirmware(String firmware) {
        this.firmware = firmware;
    }

    @Basic
    @Column(name = "last_update")
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Basic
    @Column(name = "registration_date")
    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Basic
    @Column(name = "start_date")
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Esls esls = (Esls) o;
        return id == esls.id &&
                Objects.equals(code, esls.code) &&
                Objects.equals(batteryLevel, esls.batteryLevel) &&
                Arrays.equals(currentImage, esls.currentImage) &&
                Arrays.equals(nextImage, esls.nextImage) &&
                Objects.equals(connectivity, esls.connectivity) &&
                Objects.equals(eslType, esls.eslType) &&
                Objects.equals(firmware, esls.firmware) &&
                Objects.equals(lastUpdate, esls.lastUpdate) &&
                Objects.equals(registrationDate, esls.registrationDate) &&
                Objects.equals(startDate, esls.startDate) &&
                Objects.equals(status, esls.status);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, code, batteryLevel, connectivity, eslType, firmware, lastUpdate, registrationDate, startDate, status);
        result = 31 * result + Arrays.hashCode(currentImage);
        result = 31 * result + Arrays.hashCode(nextImage);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "items_id", referencedColumnName = "id")
    public Items getItemsByItemsId() {
        return itemsByItemsId;
    }

    public void setItemsByItemsId(Items itemsByItemsId) {
        this.itemsByItemsId = itemsByItemsId;
    }
}
