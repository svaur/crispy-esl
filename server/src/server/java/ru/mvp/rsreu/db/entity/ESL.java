package ru.mvp.rsreu.db.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Art on 30.09.2018.
 */
@Entity
@Table(name = "ESLS")
public class ESL {

    @Id
    @Column(name = "ESLCode")
    private String eslCode;

    @Column(name = "ESLType", nullable = false)
    private String eslType;

    @Column(name = "Connectivity", nullable = false)
    private String connectivity;

    @Column(name = "Status", nullable = false)
    private String status;

    @Column(name = "BatteryLevel", nullable = false)
    private String batteryLevel;

    @Column(name = "FirmWare", nullable = false)
    private String firmWare;

    @Column(name = "RegistrationDate", nullable = false)
    private LocalDateTime registrationDate;

    @Column(name = "StartDate")
    private LocalDateTime startDate;

    @Column(name = "ESLPattern", nullable = false)
    private String eslPattern;

    @Column(name = "LastUpdate", nullable = false)
    private LocalDateTime lastUpdate;

    @OneToOne(cascade = CascadeType.ALL)
    private Item item;

    public ESL() {
    }

    @PrePersist
    protected void onCreate() {
        lastUpdate = LocalDateTime.now();    //todo в зависимости от реализации в образце потребует изменений

    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdate = LocalDateTime.now();    //todo в зависимости от реализации в образце потребует изменений
    }

    public String getEslCode() {
        return eslCode;
    }

    public void setEslCode(String eslCode) {
        this.eslCode = eslCode;
    }

    public String getEslType() {
        return eslType;
    }

    public void setEslType(String eslType) {
        this.eslType = eslType;
    }

    public String getConnectivity() {
        return connectivity;
    }

    public void setConnectivity(String connectivity) {
        this.connectivity = connectivity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(String batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public String getFirmWare() {
        return firmWare;
    }

    public void setFirmWare(String firmWare) {
        this.firmWare = firmWare;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public String getEslPattern() {
        return eslPattern;
    }

    public void setEslPattern(String eslPattern) {
        this.eslPattern = eslPattern;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ESL esl = (ESL) o;

        if (eslCode != null ? !eslCode.equals(esl.eslCode) : esl.eslCode != null) return false;
        if (eslType != null ? !eslType.equals(esl.eslType) : esl.eslType != null) return false;
        if (connectivity != null ? !connectivity.equals(esl.connectivity) : esl.connectivity != null) return false;
        if (status != null ? !status.equals(esl.status) : esl.status != null) return false;
        if (batteryLevel != null ? !batteryLevel.equals(esl.batteryLevel) : esl.batteryLevel != null) return false;
        if (firmWare != null ? !firmWare.equals(esl.firmWare) : esl.firmWare != null) return false;
        if (registrationDate != null ? !registrationDate.equals(esl.registrationDate) : esl.registrationDate != null)
            return false;
        if (startDate != null ? !startDate.equals(esl.startDate) : esl.startDate != null) return false;
        if (eslPattern != null ? !eslPattern.equals(esl.eslPattern) : esl.eslPattern != null) return false;
        if (lastUpdate != null ? !lastUpdate.equals(esl.lastUpdate) : esl.lastUpdate != null) return false;
        return item != null ? item.equals(esl.item) : esl.item == null;
    }

    @Override
    public int hashCode() {
        int result = eslCode != null ? eslCode.hashCode() : 0;
        result = 31 * result + (eslType != null ? eslType.hashCode() : 0);
        result = 31 * result + (connectivity != null ? connectivity.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (batteryLevel != null ? batteryLevel.hashCode() : 0);
        result = 31 * result + (firmWare != null ? firmWare.hashCode() : 0);
        result = 31 * result + (registrationDate != null ? registrationDate.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (eslPattern != null ? eslPattern.hashCode() : 0);
        result = 31 * result + (lastUpdate != null ? lastUpdate.hashCode() : 0);
        result = 31 * result + (item != null ? item.hashCode() : 0);
        return result;
    }
}
