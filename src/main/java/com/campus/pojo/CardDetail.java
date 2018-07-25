package com.campus.pojo;

import java.math.BigDecimal;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


public class CardDetail {

	String name = "";// 名字
	double consume = 0;// 一个月总消费
	double money = 0;// 余额
	String state = "";// 校园卡状态
	String shortAccount = "";// 爬取信息的校园卡账号
	String account = "";// 绑定的学号
	String recentTime = "";
	String time="";
	static SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public double getConsume() {
		return consume;
	}

	public String getRecentTime() {
		return recentTime;
	}


	public String getTime() {
		return time;
	}

	public void setTime(Date time)  {
		this.time =spf.format(time);
	}

	public void setRecentTime(Date recentTime) {
		this.recentTime = spf.format(recentTime);
	}


	public void setConsume(double consume) {
		BigDecimal b = new BigDecimal(consume);
		consume = b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		this.consume = consume;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getShortAccount() {
		return shortAccount;
	}

	public void setShortAccount(String showAccount) {
		this.shortAccount = showAccount;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

}