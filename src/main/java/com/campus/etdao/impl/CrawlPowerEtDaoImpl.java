package com.campus.etdao.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.campus.etdao.CrawlPowerEtDao;
import com.campus.dao.PowerDataDao;
import com.campus.pojo.PowerBuyData;
import com.campus.pojo.PowerInfo;
import com.campus.pojo.PowerUseData;
import com.campus.util.HttpRequest;

@Repository(value = "crawlPowerEtDao")
public class CrawlPowerEtDaoImpl implements CrawlPowerEtDao {
	static String urlPower = "http://dkcx.vipgz1.idcfengye.com/";
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	PowerDataDao powerDataDao;

	/**
	 * 获取宿舍用电信息
	 * @param building 宿舍楼
	 * @param roomName 宿舍号
	 * @param buildingId 宿舍楼id
	 */
	@Override
	public void obtainPowerUse(String building, String roomName, String buildingId) {
		String urlPath = urlPower + "selectList.do";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cl = Calendar.getInstance();
		String endTime = sdf.format(new Date());
		PowerInfo powerInfo = new PowerInfo();
		powerInfo.setBuilding(building);
		powerInfo.setRoomName(roomName);
		PowerUseData powerFirstUseData = powerDataDao.selectFirstPowerUse(powerInfo);// 这样处理加快速度
		String beginTime;
		if (powerFirstUseData != null) {
			beginTime = powerFirstUseData.getUseTime();
		} else {
			cl.setTime(new Date());
			cl.set(Calendar.YEAR, cl.get(Calendar.YEAR) - 1);
			beginTime = sdf.format(cl.getTime());
		}
		
		String content = HttpRequest.sendGet(urlPower + "login.do",
				"buildingName=" + building + "&buildingId=" + buildingId + "&roomName=" + roomName, "GB2312");
		Document doc = Jsoup.parse(content);
		String roomId = doc.select(
				"body > center > form > table > tbody > tr:nth-child(3) > td > input[type=\"hidden\"]:nth-child(8)")
				.val().trim();
		roomName = doc.select(
				"body > center > form > table > tbody > tr:nth-child(3) > td > input[type=\"hidden\"]:nth-child(9)")
				.val().trim();
		building = doc.select(
				"body > center > form > table > tbody > tr:nth-child(3) > td > input[type=\"hidden\"]:nth-child(10)")
				.val().trim();
		String param = "hiddenType=0&isHost=&client=&type=2&" + "roomName=" + roomName + "&roomId=" + roomId
				+ "&beginTime=" + beginTime + "&endTime=" + endTime;
		content = HttpRequest.sendPost(urlPath, param, "GB2312");
		doc = Jsoup.parse(content);
		Pattern pattern = Pattern.compile("当前页:.*?/.*?\\d+");
		Matcher m = pattern.matcher(content);
		String str = "";
		if (m.find()) {
			str = m.group(0);
		}
		int num = Integer.parseInt(str.split("/")[1].trim());
		LinkedList<PowerUseData> list = null;
		Iterator<Element> it = doc.select("#oTable > tbody > tr").iterator();
		list = new LinkedList<PowerUseData>();
		it.next();// 清除标题
		while (it.hasNext()) {
			Elements child = it.next().children();
			if (child.size() == 7) {
				int step = 0;
				PowerUseData powerUseData = new PowerUseData();
				list.add(powerUseData);
				for (Element link : child) {
					powerUseData.setData(step, link.text().trim());
					step++;
				}
				powerUseData.setBuilding(building);

			}

		}
		if (!list.isEmpty())
		{
			powerDataDao.insertPowerUseData(list);
		}
			
		for (int i = 2; i <= num; i++) {
			content = HttpRequest.sendPost(urlPath, param + "&pageNo=" + i, "GB2312");
			doc = Jsoup.parse(content);
			it = doc.select("#oTable > tbody > tr").iterator();
			it.next();// 清除标题
			list = new LinkedList<PowerUseData>();
			while (it.hasNext()) {
				Elements child = it.next().children();

				if (child.size() == 7) {
					int step = 0;
					PowerUseData powerUseData = new PowerUseData();
					list.add(powerUseData);
					for (Element link : child) {
						powerUseData.setData(step, link.text().trim());
						step++;
					}
					powerUseData.setBuilding(building);

				}

			}
			if (!list.isEmpty())
				powerDataDao.insertPowerUseData(list);
		}

	}

	/**
	 * 获取宿舍购电信息
	 * @param building 宿舍楼
	 * @param roomName 宿舍号
	 * @param buildingId 宿舍楼id
	 */
	@Override
	public void obtainPowerBuy(String building, String roomName, String buildingId) {
		String urlPath = urlPower + "selectList.do";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cl = Calendar.getInstance();
		String endTime = sdf.format(new Date());
		PowerInfo powerInfo = new PowerInfo();
		powerInfo.setBuilding(building);
		powerInfo.setRoomName(roomName);
		PowerBuyData powerFirstBuyData = powerDataDao.selectFirstPowerBuy(powerInfo);// 这样处理加快速度
		String beginTime;
		if (powerFirstBuyData != null) {
			beginTime = powerFirstBuyData.getBuyTime();
		} else {
			cl.setTime(new Date());
			cl.set(Calendar.YEAR, cl.get(Calendar.YEAR) - 1);
			beginTime = sdf.format(cl.getTime());
		}
		String content = HttpRequest.sendGet(urlPower + "login.do",
				"buildingName=" + building + "&buildingId=" + buildingId + "&roomName=" + roomName, "GB2312");
		Document doc = Jsoup.parse(content);
		String roomId = doc.select(
				"body > center > form > table > tbody > tr:nth-child(3) > td > input[type=\"hidden\"]:nth-child(8)")
				.val().trim();
		roomName = doc.select(
				"body > center > form > table > tbody > tr:nth-child(3) > td > input[type=\"hidden\"]:nth-child(9)")
				.val().trim();
		building = doc.select(
				"body > center > form > table > tbody > tr:nth-child(3) > td > input[type=\"hidden\"]:nth-child(10)")
				.val().trim();
		String param = "hiddenType=0&isHost=&client=&type=1&" + "roomName=" + roomName + "&roomId=" + roomId
				+ "&beginTime=" + beginTime + "&endTime=" + endTime;
		content = HttpRequest.sendPost(urlPath, param, "GB2312");
		doc = Jsoup.parse(content);
		Pattern pattern = Pattern.compile("当前页:.*?/.*?\\d+");
		Matcher m = pattern.matcher(content);
		String str = "";
		if (m.find()) {
			str = m.group(0);
		}
		int num = Integer.parseInt(str.split("/")[1].trim());
		LinkedList<PowerBuyData> list = null;
		Iterator<Element> it = doc.select("#oTable > tbody > tr").iterator();
		list = new LinkedList<PowerBuyData>();
		it.next();// 清除标题
		while (it.hasNext()) {
			Elements child = it.next().children();

			if (child.size() == 7) {
				int step = 0;
				PowerBuyData powerBuyData = new PowerBuyData();
				list.add(powerBuyData);
				for (Element link : child) {
					powerBuyData.setData(step, link.text().trim());
					step++;
				}
				powerBuyData.setBuilding(building);

			}

		}
		if (!list.isEmpty())
			powerDataDao.insertPowerBuyData(list);
		for (int i = 2; i <= num; i++) {
			content = HttpRequest.sendPost(urlPath, param + "&pageNo=" + i, "GB2312");
			doc = Jsoup.parse(content);
			it = doc.select("#oTable > tbody > tr").iterator();
			it.next();// 清除标题
			list = new LinkedList<PowerBuyData>();
			while (it.hasNext()) {
				Elements child = it.next().children();

				if (child.size() == 7) {
					int step = 0;
					PowerBuyData powerBuyData = new PowerBuyData();
					list.add(powerBuyData);
					for (Element link : child) {
						powerBuyData.setData(step, link.text().trim());
						step++;
					}
					powerBuyData.setBuilding(building);
					

				}

			}
			if (!list.isEmpty())
				powerDataDao.insertPowerBuyData(list);
		}

	}
}
