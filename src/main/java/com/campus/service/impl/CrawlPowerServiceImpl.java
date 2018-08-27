package com.campus.service.impl;

import com.campus.dao.PowerDataDao;
import com.campus.dao.UserDao;
import com.campus.etdao.CrawlSouthPowerEtDao;
import com.campus.exception.PasswordErrorException;
import com.campus.pojo.SouthPowerDetail;
import com.campus.pojo.SouthPowerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.campus.etdao.CrawlPowerEtDao;
import com.campus.service.CrawlPowerService;
import java.util.HashMap;
import java.util.LinkedList;

@Service(value = "crawlPowerService")
public class CrawlPowerServiceImpl implements CrawlPowerService {

	@Autowired
    CrawlPowerEtDao crawlPowerEtDao;

	@Autowired
	CrawlSouthPowerEtDao crawlSouthPowerEtDao;

	@Autowired
	PowerDataDao powerDataDao;
	@Autowired
	UserDao userDao;
	/**
	 * 获取宿舍用电记录
	 * @param building
	 * @param roomName
	 * @param buildingId
	 */
	@Override
	public void obtainPowerUse(String building, String roomName, String buildingId) {
		crawlPowerEtDao.obtainPowerUse(building, roomName, buildingId);
	}

	/**
	 * 获取宿舍购电记录
	 * @param building
	 * @param roomName
	 * @param buildingId
	 */
	@Override
	public void obtainPowerBuy(String building, String roomName, String buildingId) {
		crawlPowerEtDao.obtainPowerBuy(building, roomName, buildingId);

	}

	@Override
	public void obtainSouthPower(SouthPowerInfo southPowerInfo,Boolean insert) throws PasswordErrorException {

		HashMap headers=crawlSouthPowerEtDao.login(southPowerInfo);
		String roomName="";


		SouthPowerDetail southPowerDetail=crawlSouthPowerEtDao.obtainSouthPowerDetail(headers,southPowerInfo);
		roomName=southPowerDetail.getRoomName();
		southPowerInfo.setRoomName(roomName);
		if(insert)
		{
			userDao.insertSouthPowerInfo(southPowerInfo);

		}
		LinkedList list=crawlSouthPowerEtDao.obtainSouthPowerUseNowData(headers,southPowerDetail);
		if(list!=null)
		{
			powerDataDao.insertSouthPowerUseData(list);
		}

		LinkedList list2=crawlSouthPowerEtDao.obtainSouthPowerBuyData(headers,southPowerInfo);
		if(list2!=null)
		{
			powerDataDao.insertSouthPowerBuyData(list2);;

		}

	}

}
