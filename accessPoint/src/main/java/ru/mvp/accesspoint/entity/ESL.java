package ru.mvp.accesspoint.entity;

public class ESL {
    private String eslCode;
    private String connectivity;
    private String status;
    private String batteryLevel;
    private String bitmapImage;
    //придумать как хранить картинку (в оперативке слишком жирно)
// неплохо бы сделать билдер
    public ESL() {
    }

    public void setBitmapImage(String bitmapImage) {
        this.bitmapImage = bitmapImage;
    }

    public String getEslCode() {
        return eslCode;
    }

    public void setEslCode(String eslCode) {
        this.eslCode = eslCode;
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

}
