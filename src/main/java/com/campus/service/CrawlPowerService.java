package com.campus.service;


import com.campus.exception.PasswordErrorException;
import com.campus.pojo.SouthPowerInfo;

public interface CrawlPowerService {
	/**
	 * 获取宿舍用电记录
	 * @param building
	 * @param roomName
	 * @param buildingId
	 */
	public void obtainPowerUse(String building,String roomName, String buildingId);

	/**
	 * 获取宿舍购电记录
	 * @param building
	 * @param roomName
	 * @param buildingId
	 */
	public void obtainPowerBuy(String building,String roomName, String buildingId);


	public void obtainSouthPower(SouthPowerInfo southPowerInfo,Boolean insert) throws PasswordErrorException;
}
