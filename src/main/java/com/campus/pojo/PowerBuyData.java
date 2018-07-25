package com.campus.pojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PowerBuyData {
	private String building;
	private String roomName;
	private String buyer;
	private String form;
	private String energy;
	private String money;
	private String buyTime;
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
			this.buyer = data;
			break;
		case 3:
			this.form = data;
			break;
		case 4:
			this.energy = data;
			break;
		case 5:
			this.money = data;
			break;
		case 6:
			try {
				this.buyTime = sdf.format(sdf.parse(data));
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

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public String getEnergy() {
		return energy;
	}

	public void setEnergy(String energy) {
		this.energy = energy;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(Date buyTime) {
		this.buyTime = sdf.format(buyTime);
	}

	public String getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = sdf.format(time);
	}

}
