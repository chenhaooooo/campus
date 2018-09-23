package com.campus.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class SouthPowerInfo {
    //忽略此字段返回
    @JsonIgnore
    private String openid;
    //忽略此字段返回
    @JsonIgnore
    private String buildingId;
    //忽略此字段返回
    @JsonIgnore
    private String roomId;
    private String password;
    private String time;
    //忽略此字段返回
    @JsonIgnore
    private String roomName;
    private String room;;

    public String getRoom() {
        this.room = roomName;
        return room;
    }

    public void setRoom(String room) {
        this.room =roomName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
