package com.example.aueangkul.firebasedemo;

/**
 * Created by Aueangkul on 12/20/2016.
 */

public class SendInformation {
    private String position;
    private String ssid;
    private int rssi;
    private String MacAddress;
    private int count;

    public SendInformation() {
    }

    public SendInformation(String position, String MacAddress, String ssid, int rssi, int count) {
        this.position = position;
        this.MacAddress = MacAddress;
        this.ssid = ssid;
        this.rssi = rssi;
        this.count = count;
    }



    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMacAddress(){ return MacAddress;}

    public void setMacAddress(String macAddress) {
        this.MacAddress = macAddress;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
