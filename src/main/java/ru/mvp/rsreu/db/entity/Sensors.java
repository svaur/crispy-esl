package ru.mvp.rsreu.db.entity;

import javax.persistence.*;

/**
 * Created by Art on 30.09.2018.
 */
@Entity
@Table(name = "SENSORS")
public class Sensors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NUMBER")
    private int number;

    @OneToOne(cascade = CascadeType.ALL)
    private Merchandise merchandise;

    @Column(name = "STATUS")
    private boolean status;

    @Column(name = "BATTERY")
    private int battery;

    @Column(name = "LOCATION")
    private String location;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Merchandise getMerchandise() {
        return merchandise;
    }

    public void setMerchandise(Merchandise merchandise) {
        this.merchandise = merchandise;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sensors sensors = (Sensors) o;

        if (number != sensors.number) return false;
        if (status != sensors.status) return false;
        if (battery != sensors.battery) return false;
        if (merchandise != null ? !merchandise.equals(sensors.merchandise) : sensors.merchandise != null) return false;
        return location != null ? location.equals(sensors.location) : sensors.location == null;
    }

    @Override
    public int hashCode() {
        int result = number;
        result = 31 * result + (merchandise != null ? merchandise.hashCode() : 0);
        result = 31 * result + (status ? 1 : 0);
        result = 31 * result + battery;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }
}
