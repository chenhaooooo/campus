package com.campus.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class SouthPowerUseData {
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
        this.usePower = usePower;
    }

    public float getResidue() {
        return residue;
    }

    public void setResidue(float residue) {
        this.residue = residue;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
