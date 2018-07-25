package com.campus.pojo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class DataPlace {
	private String place;
	private float price;
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	
	
}
