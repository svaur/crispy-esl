package ru.mvp.database.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;

@Entity
public class Esls {
    private int id;
    private String code;
    private String batteryLevel;
    private byte[] currentImage;
    private String connectivity;
    private String esltype;
    private String firmware;
    private Timestamp lastUpdate;
    private Timestamp registrationDate;
    private Timestamp startDate;
    private String status;
    private Items itemsById;

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
    @Column(name = "connectivity")
    public String getConnectivity() {
        return connectivity;
    }

    public void setConnectivity(String connectivity) {
        this.connectivity = connectivity;
    }

    @Basic
    @Column(name = "esltype")
    public String getEsltype() {
        return esltype;
    }

    public void setEsltype(String esltype) {
        this.esltype = esltype;
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

        if (id != esls.id) return false;
        if (code != null ? !code.equals(esls.code) : esls.code != null) return false;
        if (batteryLevel != null ? !batteryLevel.equals(esls.batteryLevel) : esls.batteryLevel != null) return false;
        if (!Arrays.equals(currentImage, esls.currentImage)) return false;
        if (connectivity != null ? !connectivity.equals(esls.connectivity) : esls.connectivity != null) return false;
        if (esltype != null ? !esltype.equals(esls.esltype) : esls.esltype != null) return false;
        if (firmware != null ? !firmware.equals(esls.firmware) : esls.firmware != null) return false;
        if (lastUpdate != null ? !lastUpdate.equals(esls.lastUpdate) : esls.lastUpdate != null) return false;
        if (registrationDate != null ? !registrationDate.equals(esls.registrationDate) : esls.registrationDate != null)
            return false;
        if (startDate != null ? !startDate.equals(esls.startDate) : esls.startDate != null) return false;
        if (status != null ? !status.equals(esls.status) : esls.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (batteryLevel != null ? batteryLevel.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(currentImage);
        result = 31 * result + (connectivity != null ? connectivity.hashCode() : 0);
        result = 31 * result + (esltype != null ? esltype.hashCode() : 0);
        result = 31 * result + (firmware != null ? firmware.hashCode() : 0);
        result = 31 * result + (lastUpdate != null ? lastUpdate.hashCode() : 0);
        result = 31 * result + (registrationDate != null ? registrationDate.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @OneToOne(mappedBy = "eslsByEslId")
    public Items getItemsById() {
        return itemsById;
    }

    public void setItemsById(Items itemsById) {
        this.itemsById = itemsById;
    }
}
