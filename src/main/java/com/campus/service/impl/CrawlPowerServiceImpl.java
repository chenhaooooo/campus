package com.campus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.campus.etdao.CrawlPowerEtDao;
import com.campus.service.CrawlPowerService;

@Service(value = "crawlPowerService")
public class CrawlPowerServiceImpl implements CrawlPowerService {

	@Autowired
    CrawlPowerEtDao crawlPowerDao;

	/**
	 * 获取宿舍用电记录
	 * @param building
	 * @param roomName
	 * @param buildingId
	 */
	@Override
	public void obtainPowerUse(String building, String roomName, String buildingId) {
		crawlPowerDao.obtainPowerUse(building, roomName, buildingId);
	}

	/**
	 * 获取宿舍购电记录
	 * @param building
	 * @param roomName
	 * @param buildingId
	 */
	@Override
	public void obtainPowerBuy(String building, String roomName, String buildingId) {
		crawlPowerDao.obtainPowerBuy(building, roomName, buildingId);

	}

}
