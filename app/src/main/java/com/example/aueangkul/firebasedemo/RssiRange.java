package com.example.aueangkul.firebasedemo;

/**
 * Created by Aueangkul on 3/30/2017.
 */

public class RssiRange {
    private String position;
    private int rssiRange100;
    private int rssiRange80;
    private int rssiRange60;
    private int rssiRange40;
    private int rssiRange20;
    private int count;

    public RssiRange(String position,int rssiRange100, int rssiRange80, int rssiRange60, int rssiRange40, int rssiRange20, int count){
        this.position = position;
        this.rssiRange100 = rssiRange100;
        this.rssiRange80 = rssiRange80;
        this.rssiRange60 = rssiRange60;
        this.rssiRange40 = rssiRange40;
        this.rssiRange20 = rssiRange20;
        this.count = count;

    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getRssiRange100() {
        return rssiRange100;
    }

    public void setRssiRange100(int rssiRange100) {
        this.rssiRange100 = rssiRange100;
    }

    public int getRssiRange80() {
        return rssiRange80;
    }

    public void setRssiRange80(int rssiRange80) {
        this.rssiRange80 = rssiRange80;
    }

    public int getRssiRange60() {
        return rssiRange60;
    }

    public void setRssiRange60(int rssiRange60) {
        this.rssiRange60 = rssiRange60;
    }

    public int getRssiRange40() {
        return rssiRange40;
    }

    public void setRssiRange40(int rssiRange40) {
        this.rssiRange40 = rssiRange40;
    }

    public int getRssiRange20() {
        return rssiRange20;
    }

    public void setRssiRange20(int rssiRange20) {
        this.rssiRange20 = rssiRange20;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
