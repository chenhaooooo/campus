package com.campus.pojo;

import java.math.BigDecimal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


public class CardData {
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String consumeTime;
	private String account = "";
	private String name = "";
	private String type = "";
	private String place = "";
	private double price =0.0;
	private double money = 0.0;
	private int number = 0;
	private String status = "";
	public CardData() throws ParseException {
		consumeTime = sdf.format(new Date());
	}
	
	public String getConsumeTime()  {
		try {
			this.consumeTime = sdf.format(sdf.parse(consumeTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return consumeTime;
	}

	public void setConsumeTime(String consumeTime) throws ParseException {
		this.consumeTime = sdf.format(sdf.parse((consumeTime.trim().replace('/', '-'))));
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place.replace('.', '\0');
		if(this.place.equals("南苑226网关"))
		{
			this.place="广州校区南苑饭堂";
		}
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public CardData(String consumeTime, String account, String name, String type, String place, String price, String money,
			String number, String status) throws ParseException {
		this.consumeTime = sdf.format(sdf.parse((consumeTime.trim().replace('/', '-'))));
		this.setAccount(account);
		this.setName(name);
		this.type = type;
		this.place = place.replace('.', '\0');
		if(this.place.equals("南苑226网关"))
		{
			this.place="广州校区南苑饭堂";
		}
		this.price = Double.parseDouble(price);
		if(this.place.equals("银行转账")&&this.price<0)
		{
			this.place="水电费充值";
		}
		this.money =Double.parseDouble(money);
		this.number = Integer.parseInt(number);
		this.status = status;
	}

	

	public void set(int i, String dt) throws ParseException {
		switch (i) {
		case 0:
			this.consumeTime = sdf.format(sdf.parse((dt.trim().replace('/', '-'))));
			break;
		case 1:
			this.setAccount(dt);
			break;
		case 2:
			this.setName(dt);
			break;
		case 3:
			this.type = dt;
			break;
		case 4:
			this.place = dt.replaceAll("\\.", "");
			if(this.place.equals("南苑226网关"))
			{
				this.place="广州校区南苑饭堂";
			}
			break;
		case 5:
			this.price = Double.parseDouble(dt.replaceAll(",", ""));
			if(this.place.equals("银行转帐")&&this.price<0)
			{
				this.place="水电费充值";
			}
			break;
		case 6:
			this.money =  Double.parseDouble(dt.replaceAll(",", ""));
			break;
		case 7:
			this.number = Integer.parseInt(dt);
			break;
		case 8:
			this.status = dt;
			break;
		}
	}

	public void setToday(int i, String dt) throws ParseException {
		switch (i) {
		case 0:
			this.consumeTime = sdf.format(sdf.parse((dt.trim().replace('/', '-'))));
			break;
		case 1:
			
			this.setAccount(dt);
			break;
		case 2:
			this.setName(dt);
			break;
		case 3:
			this.type = dt;
			break;
		case 4:
			this.place =dt.replaceAll("\\.", "");
			if(this.place.equals("南苑226网关"))
			{
				this.place="广州校区南苑饭堂";
			}
			break;
		case 5:
			break;
		case 6:
			this.price =  Double.parseDouble(dt.replaceAll(",", "").trim());
			if(this.place.equals("银行转账")&&this.price<0)
			{
				this.place="水电费充值";
			}
			break;
		case 7:
			this.money =  Double.parseDouble(dt.replaceAll(",", "").trim());
			break;
		case 8:
			this.number = Integer.parseInt(dt);
			break;
		case 9:
			this.status = dt;
			break;
		}
	}


	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		 BigDecimal b = new BigDecimal(price);
		 price = b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		this.price = price;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		 BigDecimal b = new BigDecimal(money);
		 money = b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		this.money = money;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
