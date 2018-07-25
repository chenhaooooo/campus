package com.campus.pojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PowerUseData {
	private String building;
	private String roomName;
	private String usePower;
	private String residue;
	private String totalUse;
	private String totalBuy;
	private String useTime;
	private String time;
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void setData(int step, String data) {
		switch (step) {
		case 0:
			
			break;
		case 1:
			this.roomName = data;
			break;
		case 2:
			this.usePower = data;
			break;
		case 3:
			this.residue = data;
			break;
		case 4:
			this.totalUse = data;
			break;
		case 5:
			this.totalBuy = data;
			break;
		case 6:
			try {
				this.useTime = sdf.format(sdf.parse(data));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			break;
		}
	}



	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getUsePower() {
		return usePower;
	}

	public void setUsePower(String usePower) {
		this.usePower = usePower;
	}

	public String getResidue() {
		return residue;
	}

	public void setResidue(String residue) {
		this.residue = residue;
	}

	public String getTotalUse() {
		return totalUse;
	}

	public void setTotalUse(String totalUse) {
		this.totalUse = totalUse;
	}

	public String getTotalBuy() {
		return totalBuy;
	}

	public void setTotalBuy(String totalBuy) {
		this.totalBuy = totalBuy;
	}

	public String getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = sdf.format(useTime);
	}

	public String getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = sdf.format(time);
	}

}
