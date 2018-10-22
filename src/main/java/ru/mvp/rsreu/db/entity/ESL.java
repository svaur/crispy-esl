package ru.mvp.rsreu.db.entity;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Art on 30.09.2018.
 */
@Entity
@Table(name = "ESLS")
public class ESL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ELSCode")
    private int elsCode;

    @Column(name = "ELSType", nullable = false)
    private String elsType;

    @Column(name = "Connectivity", nullable = false)
    private boolean connectivity;

    @Column(name = "Status", nullable = false)
    private boolean status;

    @Column(name = "BatteryLevel", nullable = false)
    private String batteryLevel;

    @Column(name = "FirmWare", nullable = false)
    private String firmWare;

    @Column(name = "RegistrationDate", nullable = false)
    private Date registrationDate;

    @Column(name = "StartDate")
    private Date startDate;

    @Column(name = "ESLPattern", nullable = false)
    private String eslPattern;

    @Column(name = "LastUpdate", nullable = false)
    private Date lastUpdate;

    public ESL(){}

    public int getElsCode() {
        return elsCode;
    }

    public void setElsCode(int elsCode) {
        this.elsCode = elsCode;
    }

    public String getElsType() {
        return elsType;
    }

    public void setElsType(String elsType) {
        this.elsType = elsType;
    }

    public boolean isConnectivity() {
        return connectivity;
    }

    public void setConnectivity(boolean connectivity) {
        this.connectivity = connectivity;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
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

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getEslPattern() {
        return eslPattern;
    }

    public void setEslPattern(String eslPattern) {
        this.eslPattern = eslPattern;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ESL esl = (ESL) o;

        if (elsCode != esl.elsCode) return false;
        if (connectivity != esl.connectivity) return false;
        if (status != esl.status) return false;
        if (elsType != null ? !elsType.equals(esl.elsType) : esl.elsType != null) return false;
        if (batteryLevel != null ? !batteryLevel.equals(esl.batteryLevel) : esl.batteryLevel != null) return false;
        if (firmWare != null ? !firmWare.equals(esl.firmWare) : esl.firmWare != null) return false;
        if (registrationDate != null ? !registrationDate.equals(esl.registrationDate) : esl.registrationDate != null)
            return false;
        if (startDate != null ? !startDate.equals(esl.startDate) : esl.startDate != null) return false;
        if (eslPattern != null ? !eslPattern.equals(esl.eslPattern) : esl.eslPattern != null) return false;
        return lastUpdate != null ? lastUpdate.equals(esl.lastUpdate) : esl.lastUpdate == null;
    }

    @Override
    public int hashCode() {
        int result = elsCode;
        result = 31 * result + (elsType != null ? elsType.hashCode() : 0);
        result = 31 * result + (connectivity ? 1 : 0);
        result = 31 * result + (status ? 1 : 0);
        result = 31 * result + (batteryLevel != null ? batteryLevel.hashCode() : 0);
        result = 31 * result + (firmWare != null ? firmWare.hashCode() : 0);
        result = 31 * result + (registrationDate != null ? registrationDate.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (eslPattern != null ? eslPattern.hashCode() : 0);
        result = 31 * result + (lastUpdate != null ? lastUpdate.hashCode() : 0);
        return result;
    }
}
