package com.campus.util;

import java.text.ParseException;
import java.util.LinkedList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.campus.pojo.CardData;


public class CardDataUtil {
	/**
	 * 
	 * @param links
	 * @throws ParseException
	 */
	public static LinkedList<CardData> setData(Elements links) throws ParseException {
		CardData node = null;
		LinkedList<CardData> list=new LinkedList<CardData>();
		int step = 0;
		for (Element link : links) {
			if (step % 9 == 0) {
				node = new CardData();
				list.add(node);
			}
			node.set(step % 9, link.text().trim());
			step++;
		}
		return list;
	}
/**
 * 
 * @param links
 * @throws ParseException
 */
	public static LinkedList<CardData> setToday(Elements links) throws ParseException {
		CardData node = null;
		LinkedList<CardData> list=new LinkedList<CardData>();
		int step = 0;
		for (Element link : links) {
			if (step % 10 == 0) {
				node = new CardData();
				list.add(node);
			}
			node.setToday(step % 10, link.text().trim());
			step++;
		}
		return list;
	}
}
