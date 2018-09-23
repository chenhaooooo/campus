package com.campus.pojo;

public class SouthPowerDetail {
    private float balance;
    private float powerUserMonth;
    private String buildingId;
    private String roomId;

    private String roomName;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public float getPowerUserMonth() {
        return powerUserMonth;
    }

    public void setPowerUserMonth(float powerUserMonth) {
        this.powerUserMonth = powerUserMonth;
    }

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

}
