package com.campus.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class UserInfo {
	private String openid;// 用户唯一标识
	private String sessionKey;// 会话密钥
	private String nickName;// 用户昵称
	private int gender;// 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	private String city;// 用户所在城市
	private String province;// 用户所在省份

	private String country;// 用户所在国家
	private String language;// 用户的语言，简体中文为zh_CN
	private String unionid;// 用户在开放平台的唯一标识符
	private String recentTime;// 最近登录时间
	private String time;// 用户创建时间
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = sdf.format(time);
	}

	public String getRecentTime() {

		return recentTime;
	}

	public void setRecentTime(Date recentTime) {
		this.recentTime = sdf.format(recentTime);

	}
}
