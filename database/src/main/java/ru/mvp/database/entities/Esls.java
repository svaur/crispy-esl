package ru.mvp.database.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

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
    private Collection<Items> itemsById;

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
    @Column(name = "esl_type")
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
        return id == esls.id &&
                Objects.equals(code, esls.code) &&
                Objects.equals(batteryLevel, esls.batteryLevel) &&
                Arrays.equals(currentImage, esls.currentImage) &&
                Objects.equals(connectivity, esls.connectivity) &&
                Objects.equals(esltype, esls.esltype) &&
                Objects.equals(firmware, esls.firmware) &&
                Objects.equals(lastUpdate, esls.lastUpdate) &&
                Objects.equals(registrationDate, esls.registrationDate) &&
                Objects.equals(startDate, esls.startDate) &&
                Objects.equals(status, esls.status) &&
                Objects.equals(itemsById, esls.itemsById);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, code, batteryLevel, connectivity, esltype, firmware, lastUpdate, registrationDate, startDate, status, itemsById);
        result = 31 * result + Arrays.hashCode(currentImage);
        return result;
    }

    @OneToMany(mappedBy = "eslsByEslId")
    public Collection<Items> getItemsById() {
        return itemsById;
    }

    public void setItemsById(Collection<Items> itemsById) {
        this.itemsById = itemsById;
    }
}
