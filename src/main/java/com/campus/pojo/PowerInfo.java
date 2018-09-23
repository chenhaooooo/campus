package com.campus.pojo;

import org.codehaus.jackson.annotate.JsonIgnore;

public class PowerInfo {
	private String openid;
	private String building;
	private String time;
	private String buildingId;
	private String roomName;
	private String room;

	public String getRoom() {
		this.room=building+roomName;
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}
	//忽略此字段返回
	@JsonIgnore
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
