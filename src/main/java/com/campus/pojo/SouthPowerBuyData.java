package com.campus.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class SouthPowerBuyData {
    //忽略此字段返回
    @JsonIgnore
    private String buildingId;
    //忽略此字段返回
    @JsonIgnore
    private String roomId;
    private String roomName;
    //忽略此字段返回
    @JsonIgnore
    private String ammeter;
    //忽略此字段返回
    @JsonIgnore
    private int number;
    private float money;
    private String buyTime;
    //忽略此字段返回
    @JsonIgnore
    private String buyer;
    //忽略此字段返回
    @JsonIgnore
    private String down;
    private String downTime;
    //忽略此字段返回
    @JsonIgnore
    private String time;
    private String buyType;

    public String getBuyType() {
        return buyType;
    }

    public void setBuyType(String buyType) {
        this.buyType = buyType;
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

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getAmmeter() {
        return ammeter;
    }

    public void setAmmeter(String ammeter) {
        this.ammeter = ammeter;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getDown() {
        return down;
    }

    public void setDown(String down) {
        this.down = down;
    }

    public String getDownTime() {
        return downTime;
    }

    public void setDownTime(String downTime) {
        this.downTime = downTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
