package com.campus.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SouthPowerUseData {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    DecimalFormat df   = new DecimalFormat("######0.00");
    //忽略此字段返回
    @JsonIgnore
    private String buildingId;
    //忽略此字段返回
    @JsonIgnore
    private String roomId;
    private String roomName;
    private float usePower;
    private float residue;
    private float money;
    private String useTime;
    //忽略此字段返回
    @JsonIgnore
    private String time;

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public float getUsePower() {
        return usePower;
    }

    public void setUsePower(float usePower) {
        this.usePower = Float.parseFloat(df.format(usePower));
    }

    public float getResidue() {
        return residue;
    }

    public void setResidue(float residue) {
        this.residue = Float.parseFloat(df.format(residue));
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = Float.parseFloat(df.format(money));
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        try {
            this.useTime = sdf.format(sdf.parse(useTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        try {
            this.time = sdf.format(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
