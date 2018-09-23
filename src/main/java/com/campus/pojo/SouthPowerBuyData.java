package com.campus.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class SouthPowerBuyData {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    DecimalFormat df = new DecimalFormat("######0.00");

    //忽略此字段返回
    @JsonIgnore
    private String buildingId;
    //忽略此字段返回
    @JsonIgnore
    private String roomId;
    //忽略此字段返回
    @JsonIgnore
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
    private float energy;
    private String room;

    public String getRoom() {
        room=this.roomName;
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public float getEnergy() {
        return energy;
    }

    public void setEnergy(float energy) {
        this.energy = Float.parseFloat(df.format(energy));
    }

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
        this.money = Float.parseFloat(df.format(money));
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        try {
            this.buyTime = sdf.format(sdf.parse(buyTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
        try {
            this.downTime = sdf.format(sdf.parse(downTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ;
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
