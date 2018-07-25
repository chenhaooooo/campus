package com.campus.service;

import java.util.HashMap;

public interface CrawlCardService {
    /**
     * 登陆校园卡网站,返回登陆后的cookie
     * @param account
     * @param password
     * @return
     */
	public String  loginCampus(String account, String password);

    /**
     * 获取特定时间消费的记录
     * @param cookie
     * @param inputStartDate
     * @param inputEndDate
     */
	public void obtainCampus(String cookie, String inputStartDate, String inputEndDate);

    /**
     * 获取消费记录
     * @param account
     * @param cookie
     */
	public void obtainCampus(String account,String cookie);

    /**
     * 获取校园卡状态
     * @param account
     * @param cookie
     */
	public void obtainState(String account, String cookie);

    /**
     * 挂失校园卡
     * @param account
     * @param password
     * @param cookie
     * @return
     */
	public Boolean lostCampus(String account, String password, String cookie);

    /**
     * 获取当天消费的记录
     * @param account
     * @param password
     * @param beforeTime
     * @return
     */
	public Boolean obtainCampusNews(String account, String password, String beforeTime);

    /**
     * 更改校园卡密码
     * @param account
     * @param password
     * @param newPassword
     * @param cookie
     * @return
     */
	public Boolean alterPassword(String account, String password,String newPassword, String cookie);

}
