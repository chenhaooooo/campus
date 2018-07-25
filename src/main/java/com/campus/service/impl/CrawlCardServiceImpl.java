package com.campus.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.campus.util.CrawlThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.campus.etdao.CrawlCardEtDao;
import com.campus.service.CrawlCardService;

@Service(value = "crawCardService")
public class CrawlCardServiceImpl implements CrawlCardService {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    CrawlCardEtDao crawlCardEtDao;

    /**
     * 模拟登陆校园卡网站
     * @param account
     * @param password
     * @return
     */
    @Override
    public String loginCampus(String account, String password) {

        return crawlCardEtDao.loginCampus(account, password);
    }

    /**
     * 获取历史消费记录
     * @param cookie
     * @param inputStartDate
     * @param inputEndDate
     */
    @Override
    public void obtainCampus(String cookie, String inputStartDate, String inputEndDate) {
        crawlCardEtDao.obtainCampus(cookie, inputStartDate, inputEndDate);

    }

    /**
     * 获取校园卡的数据，应用在用户绑定时获取数据上
     * @param account
     * @param cookie
     */
    @Override
    public void obtainCampus(String account,String cookie) {

        final String inputStartDate;
        final String inputEndDate;
        // 可以对每个时间域单独修改
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -5);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int dats = c.get(Calendar.DATE);

        if (month < 10) {
            inputStartDate = year + "0" + month + "" + dats;
        } else {
            inputStartDate = year + "" + month + "" + dats;
        }
        c.add(Calendar.MONTH, 5);
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        dats = c.get(Calendar.DATE);
        if (month < 10) {
            inputEndDate = year + "0" + month + "" + dats;
        } else {
            inputEndDate = year + "" + month + "" + dats;
        }
        //开启线程，获取历史记录
        new CrawlThread(cookie,inputStartDate,inputEndDate,this).start();
        //获取当天记录
        crawlCardEtDao.obtainCampus(cookie);
        //获取校园卡状态
        this.obtainState(account, cookie);// 保存校园卡状态

    }
    /**
     * 获取校园卡离上次更新后的数据
     * @param account
     * @param password
     * @param beforeTime
     * @return
     */
    @Override
    public Boolean obtainCampusNews(String account, String password, String beforeTime) {
        String inputStartDate;
        String inputEndDate;
        //之前有更新过数据
        if (!beforeTime.equals("")) {
            //上次更新时间
            Calendar cal1 = Calendar.getInstance();
            //当前时间
            Calendar cal2 = Calendar.getInstance();
            try {
                //设置上次更新时间
                cal1.setTime(sdf.parse(beforeTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //设置当前时间
            cal2.setTime(new Date());
            //上次更新离现在的月份数
            int difference = (cal2.YEAR - cal1.YEAR) * 12 + cal2.MONTH - cal1.MONTH;
            //大于五个月
            if (difference > 5) {
                // 可以对每个时间域单独修改
                Calendar c = Calendar.getInstance();
                //设置时间为五个月前
                c.add(Calendar.MONTH, -5);
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH) + 1;
                int dats = c.get(Calendar.DATE);
                //调整日期格式
                if (month < 10) {
                    inputStartDate = year + "0" + month + "" + dats;
                } else {
                    inputStartDate = year + "" + month + "" + dats;
                }
                //当前时间
                c.add(Calendar.MONTH, 5);
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH) + 1;
                dats = c.get(Calendar.DATE);
                //调整日期格式
                if (month < 10) {
                    inputEndDate = year + "0" + month + "" + dats;
                } else {
                    inputEndDate = year + "" + month + "" + dats;
                }
            }
            //上次更新离现在少于五个月
            else {
                //调整上次更新时间
                int year = cal1.get(Calendar.YEAR);
                int month = cal1.get(Calendar.MONTH) + 1;
                int dats = cal1.get(Calendar.DATE);
                if (month < 10) {
                    inputStartDate = year + "0" + month + "" + dats;
                } else {
                    inputStartDate = year + "" + month + "" + dats;
                }
                //调整当前时间
                year = cal2.get(Calendar.YEAR);
                month = cal2.get(Calendar.MONTH) + 1;
                dats = cal2.get(Calendar.DATE);
                if (month < 10) {
                    inputEndDate = year + "0" + month + "" + dats;
                } else {
                    inputEndDate = year + "" + month + "" + dats;
                }
            }
        }

        //之前没更新过数据，新用户
        else {
            //设置五个月前日期
            Calendar c = Calendar.getInstance();// 可以对每个时间域单独修改
            c.add(Calendar.MONTH, -5);
            //调整日期格式
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH) + 1;
            int dats = c.get(Calendar.DATE);

            if (month < 10) {
                inputStartDate = year + "0" + month + "" + dats;
            } else {
                inputStartDate = year + "" + month + "" + dats;
            }
            //设置当前日期
            c.add(Calendar.MONTH, 5);
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH) + 1;
            dats = c.get(Calendar.DATE);
            //调整日期格式
            if (month < 10) {
                inputEndDate = year + "0" + month + "" + dats;
            } else {
                inputEndDate = year + "" + month + "" + dats;
            }
        }
        //登陆，获取cookie
        String cookie = this.loginCampus(account, password);
        //更新数据
        this.obtainCampus(cookie, inputStartDate, inputEndDate);
        this.obtainCampus(account,cookie);// 获取当天记录
        this.obtainState(account, cookie);// 保存校园卡状态
        return true;

    }

    /**
     * 获取校园卡状态
     * @param account
     * @param cookie
     */
    @Override
    public void obtainState(String account, String cookie) {

        crawlCardEtDao.obtainState(account, cookie);
    }

    /**
     * 挂失校园卡
     * @param account
     * @param password
     * @param cookie
     * @return
     */
    @Override
    public Boolean lostCampus(String account, String password, String cookie) {

        return crawlCardEtDao.lostCampus(account, password, cookie);
    }

    /**
     * 更改校园卡密码
     * @param account
     * @param password
     * @param newPassword
     * @param cookie
     * @return
     */
    @Override
    public Boolean alterPassword(String account, String password, String newPassword, String cookie) {

        return crawlCardEtDao.alterPassword(account, password, newPassword, cookie);
    }


}
