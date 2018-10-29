package com.campus.etdao.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.campus.exception.PasswordErrorException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

import com.campus.etdao.CrawlCardEtDao;
import com.campus.dao.CardDataDao;
import com.campus.pojo.CardData;
import com.campus.pojo.CardDetail;
import com.campus.util.CardDataUtil;
import com.campus.util.HttpRequest;

@Repository(value = "crawlCardEtDao")
public class CrawlCardEtDaoImpl implements CrawlCardEtDao {
    static String urlCampus = "http://gdufcard.vipgz1.idcfengye.com/";

    private Logger logger = Logger.getLogger(this.getClass().getName());
    @Autowired
    CardDataDao cardDataDao;

    /**
     * 登录校园卡网站
     * @param account 校园卡账号
     * @param password 校园卡密码（六位）
     * @return 登录结果，返回cookie
     */
    @Override
    public String loginCampus(String account, String password) {
        String urlPath = urlCampus + "homeLogin.action";
        String cookie = HttpRequest.getCookie(urlPath, "");// 访问首页,得到cookie
        String checkPic = urlCampus + "getCheckpic.action?rand=7676.2";// 验证码链接
        HttpRequest.sendGet(checkPic, "", cookie, "UTF-8");// 改变验证码

        String loginURL = urlCampus + "loginstudent.action";
        String params = "name=" + account + "&userType=1&passwd=" + password
                + "&loginType=2&rand=7676&imageField.x=24&imageField.y=19";
        String content = HttpRequest.sendPost(loginURL, params, cookie, "GB2312");// 登陆
        Pattern pattern = Pattern.compile("<p class=\"biaotou\" >.*?</p>");
        Matcher m = pattern.matcher(content);
        String tip = "";
        if (m.find()) {
            tip = m.group(0);
        }
        if (!tip.equals("")) {
            tip = (tip.split(">")[1]).split("<")[0];
        }

        if (!tip.equals("")) {
            throw new PasswordErrorException("密码错误");
        }

        return cookie;
    }

    /**
     * 获取某个时间段内的校园卡消费信息
     * @param cookie 校园卡网站登录后的cookie
     * @param inputStartDate 开始日期，例如：20180723
     * @param inputEndDate  结束日期，例如：20180723
     */
    @Override
    public void obtainCampus(String cookie, String inputStartDate, String inputEndDate) {
        String consumeURL1 = urlCampus + "accounthisTrjn.action";// 消费界面首页链接
        int pagenum = 0;
        String regEx;
        Pattern pattern;
        Matcher matcher;
        String content;
        Document doc;
        Elements links = null;
        LinkedList<CardData> list = null;
        content = HttpRequest.sendGet(consumeURL1, "", cookie, "GBK");
        // 获取_continue参数
        pattern = Pattern.compile("__continue=.*?\"");
        Matcher m = pattern.matcher(content);
        String _continue = "";
        if (m.find()) {
            _continue = m.group(0).split("\"")[0];
        }

        // 获取校园卡账号
        pattern = Pattern.compile("account\".*?</option>");
        m = pattern.matcher(content);
        String CardAccount = "";
        if (m.find()) {
            CardAccount = m.group(0);
        }
        /*
         * 获取第二次历史记录页面
         */
        CardAccount = CardAccount.split("</option>")[0].split("value=")[1].split("\"")[1];

        String params = "account=" + CardAccount + "&inputObject=all&Submit=+%C8%B7+%B6%A8+";
        String consumeURL2 = urlCampus + "accounthisTrjn.action?" + _continue;
        content = HttpRequest.sendPost(consumeURL2, params, cookie, "GB2312");

        // 获取_continue参数
        pattern = Pattern.compile("__continue=.*?\"");
        m = pattern.matcher(content);
        _continue = "";
        while (m.find()) {
            _continue = m.group(0).split("\"")[0];
        }

        /*
         * 获取第三次历史记录页面
         */
        String date = "inputStartDate=" + inputStartDate + "&inputEndDate=" + inputEndDate;
        String consumeURL3 = urlCampus + "accounthisTrjn.action?" + _continue;
        content = HttpRequest.sendPost(consumeURL3, date, cookie, "GB2312");

        /*
         * 获取第四次历史记录页面
         */
        pattern = Pattern.compile("__continue=.*?\"");
        m = pattern.matcher(content);
        _continue = "";
        if (m.find()) {
            _continue = m.group(0).split("\"")[0];
        }

        String consumeURL4 = urlCampus + "accounthisTrjn.action?" + _continue;
        content = HttpRequest.sendPost(consumeURL4, date, cookie, "GB2312");
        doc = Jsoup.parse(content);

        String num = doc.select("#tables > tbody > tr:nth-child(19) > td > div").text();
        list = new LinkedList<CardData>();
        // 保存首页数据
        links = doc.getElementsByClass("listbg").select("td");
        System.out.println(content);
        if (links != null) {
            try {
                list = CardDataUtil.setData(links);
                if (list.size() != 0) {
                    cardDataDao.insertCardData(list);
                }
            } catch (ParseException e) {
                logger.info(CardAccount + "保存历史记录失败：" + e.getMessage());
            }

        }

        links = doc.getElementsByClass("listbg2").select("td");
        if (links != null) {
            try {
                list = CardDataUtil.setData(links);
                if (list.size() != 0) {
                    cardDataDao.insertCardData(list);
                }
            } catch (ParseException e) {
                logger.info(CardAccount + "保存历史记录失败：" + e.getMessage());
            }

        }
        // 获取下一页信息
        regEx = "\\共[1-9]\\d*";
        pattern = Pattern.compile(regEx);// 编译匹配
        matcher = pattern.matcher(num);// 找出页数
        if (matcher.find()) {
            String str = matcher.group().substring(1);
            pagenum = Integer.parseInt(str);
        }
        params = "inputStartDate=" + inputStartDate + "&inputEndDate=" + inputEndDate + "&pageNum=";
        String consumeURL5 = urlCampus + "accountconsubBrows.action";
        for (int i = 1; i < pagenum; i++) {
            content = HttpRequest.sendGet(consumeURL5, params + i, cookie, "GB2312");
            // 保存当前页数据
            doc = Jsoup.parse(content);
            links = doc.getElementsByClass("listbg").select("td");
            if (links != null) {
                try {
                    list = CardDataUtil.setData(links);
                    if (list.size() != 0) {
                        cardDataDao.insertCardData(list);
                    }
                } catch (ParseException e) {
                    logger.info(CardAccount + "保存历史记录失败：" + e.getMessage());
                }

            }
            links = doc.getElementsByClass("listbg2").select("td");
            if (links != null) {
                try {
                    list = CardDataUtil.setData(links);
                    if (list.size() != 0) {
                        cardDataDao.insertCardData(list);
                    }
                } catch (ParseException e) {
                    logger.info(CardAccount + "保存历史记录失败：" + e.getMessage());
                }

            }
        }
    }

    /**
     * 获取当天的校园卡消费记录（因为历史记录与当天的记录链接不同）
     * @param cookie 校园卡网站登录后的cookie
     */
    @Override
    public void obtainCampus(String cookie) {
        Elements links = null;
        LinkedList<CardData> list = null;
        String params;
        String content = "";
        Document doc;
        list = new LinkedList<CardData>();
        // 当日流水
        content = HttpRequest.sendGet(urlCampus + "accounttodayTrjn.action", "", cookie, "GBK");
        doc = Jsoup.parse(content);
        String shortAccount = doc.select("#account > option").text().trim();
        params = "account=" + shortAccount + "&inputObject=all&Submit=+%C8%B7+%B6%A8+";
        content = HttpRequest.sendGet(urlCampus + "accounttodatTrjnObject.action", params, cookie, "GB2312");
        doc = Jsoup.parse(content);
        links = doc.getElementsByClass("listbg").select("td");
        if (links != null) {
            try {
                list = CardDataUtil.setToday(links);

                if (list.size() != 0) {
                    cardDataDao.insertCardData(list);
                }
            } catch (ParseException e) {
                logger.info("保存当天记录失败：" + e.getMessage());
            }

        }
        links = doc.getElementsByClass("listbg2").select("td");
        if (links != null) {
            try {
                list = CardDataUtil.setToday(links);
                if (list.size() != 0) {
                    cardDataDao.insertCardData(list);
                }

            } catch (ParseException e) {
                logger.info("保存当天记录失败：" + e.getMessage());
            }

        }
    }

    /**
     * 获取校园卡状态
     * @param account 校园卡账号
     * @param cookie 校园卡网站登录后的cookie
     */
    public void obtainState(String account, String cookie) {
        String regEx;
        Pattern pattern;
        Matcher matcher;
        CardDetail cardDetail = new CardDetail();
        // 基本信息
        String content = HttpRequest.sendGet(urlCampus + "accountcardUser.action", "", cookie, "UTF-8");

        Document doc = Jsoup.parse(content);
        String moneyXml = doc
                .select("body > table > tbody > tr > td > table > tbody > tr:nth-child(2)"
                        + " > th > table > tbody > tr > th > table > tbody > tr:nth-child(12) > td:nth-child(2)")
                .text().replaceAll(",", "");
        regEx = "[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*元";
        pattern = Pattern.compile(regEx);// 编译匹配
        matcher = pattern.matcher(moneyXml);
        String money = "0";
        if (matcher.find()) {
            money = matcher.group().substring(0, moneyXml.indexOf("元")).trim();
        }
        String state = doc.select("body > table > tbody > tr > td > " +
                "table > tbody > tr:nth-child(2) > th > table > tbody > tr > " +
                "th > table > tbody > tr:nth-child(12) > td:nth-child(6)").text().trim();
        String name = doc.select("body > table > tbody > tr > td > table > tbody > tr:nth-child(2) > th > table > tbody"
                + " > tr > th > table > tbody > tr:nth-child(2) > td:nth-child(2) > div").text().trim();
        String shortAccount = doc
                .select("body > table > tbody > tr > td > table > tbody > tr:nth-child(2) > th >"
                        + " table > tbody > tr > th > table > tbody > tr:nth-child(2) > td:nth-child(4) > div")
                .text().trim();
        cardDetail.setMoney(Float.parseFloat(money));
        cardDetail.setState(state);
        cardDetail.setShortAccount(shortAccount);
        cardDetail.setAccount(account);
        cardDetail.setName(name);
        cardDetail.setConsume(cardDataDao.selectConsume(account));
        cardDetail.setRecentTime(new Date());
        cardDataDao.insertCardDetail(cardDetail);

    }

    /**
     * 挂失校园卡
     * @param account 校园卡账号
     * @param password 校园卡密码
     * @param cookie 校园卡网站登录后的cookie
     * @return 挂失结果
     */
    @Override
    public Boolean lostCampus(String account, String password, String cookie) {

        Pattern pattern;
        Boolean result;
        String params;
        String content;
        String loseURL = urlCampus + "accountloss.action";
        content = HttpRequest.sendGet(loseURL, "", cookie, "GB2312");

        pattern = Pattern.compile(" <option value=.*?>");
        Matcher m = pattern.matcher(content);
        String shortAccount = "";
        while (m.find()) {
            shortAccount = m.group(0).split("\"")[1];
        }
        params = "account=" + shortAccount + "&passwd=" + password;
        String loseURL2 = urlCampus + "accountDoLoss.action";
        content = HttpRequest.sendGet(loseURL2, params, cookie, "GB2312");
        pattern = Pattern.compile("校园卡挂失成功");
        m = pattern.matcher(content);
        String tip = "";
        while (m.find()) {
            tip = m.group(0);
        }
        if (tip.equals("校园卡挂失成功")) {
            result = true;

        } else {
            result = false;
        }
        // 为防止用户在其它地方挂失，更新状态
        try {
            this.obtainState(account, cookie);
        } catch (Exception e) {
            logger.info(account+"挂失更新状态出错");
            logger.info("报错情况："+e.toString());
        } // 遍历数据库数据

        return result;
    }

    /**
     * 修改校园卡密码
     * @param account 校园卡账号
     * @param password 当前校园卡密码
     * @param newPassword 校园卡更改密码
     * @param cookie 校园卡网站登录后的cookie
     * @return 更改结果
     */
    @Override
    public Boolean alterPassword(String account, String password, String newPassword, String cookie) {
        Pattern pattern;
        Boolean result;
        String params;
        String content;
        String alterURL = urlCampus + "accountcpwd.action";
        content = HttpRequest.sendGet(alterURL, "", cookie, "GB2312");
        Document doc = Jsoup.parse(content);
        String shortAccount = doc.select("#account > option").text().trim();
        params = "account=" + shortAccount + "&passwd=" + password + "&newpasswd=" + newPassword + "&newpasswd2="
                + newPassword;
        String alterURL2 = urlCampus + "accountDocpwd.action";
        content = HttpRequest.sendGet(alterURL2, params, cookie, "GB2312");
        pattern = Pattern.compile("操作成功");
        Matcher m = pattern.matcher(content);
        String tip = "";
        while (m.find()) {
            tip = m.group(0);
        }
        if (tip.equals("操作成功")) {
            result = true;

        } else {
            result = false;
        }
        return result;
    }

}
