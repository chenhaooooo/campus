package com.campus.etdao.impl;

import com.campus.etdao.CrawlSouthPowerEtDao;
import com.campus.exception.PasswordErrorException;
import com.campus.pojo.SouthPowerBuyData;
import com.campus.pojo.SouthPowerDetail;
import com.campus.pojo.SouthPowerInfo;
import com.campus.pojo.SouthPowerUseData;
import com.campus.util.Request;
import net.sf.json.JSONArray;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository(value = "crawlSouthPowerEtDao")
public class CrawlSouthPowerEtDaoImpl implements CrawlSouthPowerEtDao {
    static String url = "http://172.18.2.42:8000/";
    static float cost = 0.63f;
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public HashMap login(SouthPowerInfo southPowerInfo) {
        String buildingId = southPowerInfo.getBuildingId();
        String roomId = southPowerInfo.getRoomId();
        String password=southPowerInfo.getPassword();
        CloseableHttpResponse response = Request.get(url, null);
        String cookie = response.getHeaders(
                "Set-Cookie"
        )[0].getValue();
        Pattern pattern = Pattern.compile("ASP.NET_SessionId=.*?;");
        Matcher m = pattern.matcher(cookie);
        if (m.find()) {
            cookie = m.group(0);
        }
        String content = Request.getContent(response);
        Document doc = Jsoup.parse(content);
        String __EVENTTARGET = doc.select("#__EVENTTARGET").val().trim();
        String __EVENTARGUMENT = doc.select("#__EVENTARGUMENT").val().trim();
        String __LASTFOCUS = doc.select("#__LASTFOCUS").val().trim();
        String __VIEWSTATE = doc.select("#__VIEWSTATE").val().trim();
        String __EVENTVALIDATION = doc.select("#__EVENTVALIDATION").val().trim();
        String Radio1 = "1";
        String ScriptManager1 = "UpdatePanel1|Radio1$1";
        // 构建消息实体
        HashMap<String, String> params = new HashMap();
        params.put("__EVENTTARGET", __EVENTTARGET);
        params.put("__EVENTARGUMENT", __EVENTARGUMENT);
        params.put("__LASTFOCUS", __LASTFOCUS);
        params.put("__VIEWSTATE", __VIEWSTATE);
        params.put("__EVENTVALIDATION", __EVENTVALIDATION);
        params.put("Radio1", Radio1);
        params.put("ScriptManager1", ScriptManager1);
        HashMap headers = new HashMap();
        headers.put("Cookie", cookie);
        response = Request.post(url, headers, params);
        content = Request.getContent(response);
        //#################################################宿舍选择
        String txtjz2 = buildingId;//这里修改需要选择的宿舍
        String txtname2 = "001001001001001";//这里不能修改
        String txtpwd2 = "";
        //验证码
        String txtyzm2 = "";
        doc = Jsoup.parse(content);
        __EVENTTARGET = doc.select("#__EVENTTARGET").val().trim();
        __EVENTARGUMENT = doc.select("#__EVENTARGUMENT").val().trim();
        __LASTFOCUS = doc.select("#__LASTFOCUS").val().trim();
        __VIEWSTATE = doc.select("#__VIEWSTATE").val().trim();
        __EVENTVALIDATION = doc.select("#__EVENTVALIDATION").val().trim();
        Radio1 = "1";
        ScriptManager1 = "UpdatePanel1|Radio1$1";
        // 构建消息实体
        params = new HashMap();

        params.put("__EVENTTARGET", __EVENTTARGET);
        params.put("__EVENTARGUMENT", __EVENTARGUMENT);
        params.put("__LASTFOCUS", __LASTFOCUS);
        params.put("__VIEWSTATE", __VIEWSTATE);
        params.put("__EVENTVALIDATION", __EVENTVALIDATION);
        params.put("Radio1", Radio1);
        params.put("ScriptManager1", ScriptManager1);
        params.put("txtjz2", txtjz2);
        params.put("txtname2", txtname2);
        params.put("txtyzm2", txtyzm2);
        headers = new HashMap();
        headers.put("Cookie", cookie);
        response = Request.post(url, headers, params);
        content = Request.getContent(response);
        //##################################################################
        //登录
        doc = Jsoup.parse(content);
        __EVENTTARGET = doc.select("#__EVENTTARGET").val().trim();
        __EVENTARGUMENT = doc.select("#__EVENTARGUMENT").val().trim();
        __LASTFOCUS = doc.select("#__LASTFOCUS").val().trim();
        __VIEWSTATE = doc.select("#__VIEWSTATE").val().trim();
        __EVENTVALIDATION = doc.select("#__EVENTVALIDATION").val().trim();
        Radio1 = "1";
        // 构建消息实体
        params = new HashMap();
        txtjz2 = buildingId;//宿舍楼，这里要与前面的选择一致
        txtname2 = roomId;//选择宿舍楼宿舍层
        txtpwd2 = password;
        //验证码
        txtyzm2 = "2367";
        params.put("Radio1", Radio1);
        params.put("txtjz2", txtjz2);
        params.put("txtname2", txtname2);
        params.put("txtpwd2", txtpwd2);
        params.put("txtyzm2", txtyzm2);
        params.put("Button1", "");
        params.put("__EVENTTARGET", __EVENTTARGET);
        params.put("__EVENTARGUMENT", __EVENTARGUMENT);
        params.put("__LASTFOCUS", __LASTFOCUS);
        params.put("__VIEWSTATE", __VIEWSTATE);
        params.put("__EVENTVALIDATION", __EVENTVALIDATION);
        params.put("hidtime", "2018-08-27 17:46:01");

        headers = new HashMap();
        headers.put("Cookie", cookie);
        response = Request.post(url, headers, params);
        content = Request.getContent(response);
        pattern = Pattern.compile("密码错误，请重新输入!");
        m = pattern.matcher(content);
        if (m.find()) {
            throw new PasswordErrorException("密码错误，请重新输入");
        }
        String temp = "";
        for (int i = 0; i < response.getHeaders("Set-Cookie").length; i++) {
            temp += response.getHeaders("Set-Cookie")[i].getValue();
        }
        pattern = Pattern.compile("loginschool=.*?;");
        m = pattern.matcher(temp);
        if (m.find()) {
            cookie += m.group(0);
        }
        pattern = Pattern.compile(".ASPXAUTH=.*?;");
        m = pattern.matcher(temp);
        if (m.find()) {
            cookie += m.group(0);
        }
        headers.put("Cookie", cookie);
        response=Request.get(url+"Default2.aspx",headers);
        content=Request.getContent(response);
        return headers;

    }

    @Override
    public SouthPowerDetail obtainSouthPowerDetail(HashMap headers, SouthPowerInfo southPowerInfo) {
        CloseableHttpResponse response = Request.get(url + "PowerMonitoring/ssjkSSSJCX2.aspx?id=73", headers);

        String content = Request.getContent(response);
        String buildingId = southPowerInfo.getBuildingId();
        String roomId = southPowerInfo.getRoomId();
        //获取总余额
        float balance = 0;
        Pattern pattern = Pattern.compile("lblzye\",disabled:false,encodeValue:false,value:\"\\d*[.]\\d*\"");
        String temp = "";
        Matcher m = pattern.matcher(content);
        if (m.find()) {
            temp = m.group(0);
        }

        Pattern p = Pattern.compile("\\d*[.]\\d*");
        m = p.matcher(temp);
        if (m.find()) {
            balance = Float.parseFloat(m.group(0));
        }

        //获取本月总用电量
        float powerUserMonth = 0;
            pattern = Pattern.compile("lblbyzydl\",disabled:false,encodeValue:false,value:\".*?\"");
        temp = "";
        m = pattern.matcher(content);
        if (m.find()) {
            temp = m.group(0);
        }

        p = Pattern.compile("\\d*[.]\\d*");
        m = p.matcher(temp);
        if (m.find()) {
            powerUserMonth = Float.parseFloat(m.group(0));
        }
        //获取宿舍名
        String roomName="";

        pattern = Pattern.compile("lblyhmc\",disabled:false,encodeValue:true,value:\".*?\"");
        temp = "";
        m = pattern.matcher(content);
        if (m.find()) {
            temp = m.group(0);
        }
        roomName=temp.split("\"")[2];
        SouthPowerDetail southPowerDetail = new SouthPowerDetail();
        southPowerDetail.setBalance(balance);
        southPowerDetail.setBuildingId(buildingId);
        southPowerDetail.setRoomId(roomId);
        southPowerDetail.setPowerUserMonth(powerUserMonth);
        southPowerDetail.setRoomName(roomName);
        return southPowerDetail;
    }

    @Override
    public LinkedList obtainSouthPowerUseNowData(HashMap headers, SouthPowerDetail southPowerDetail) {
        CloseableHttpResponse response = Request.get(url + "UserAccountment/AccountDetails2.aspx?id=75", headers);
        String content = Request.getContent(response);
        Document doc = Jsoup.parse(content);
        HashMap params = new HashMap();
        String __EVENTTARGET = doc.select("#__EVENTTARGET").val();
        String __EVENTARGUMENT = doc.select("#__EVENTARGUMENT").val();
        String __VIEWSTATE = doc.select("#__VIEWSTATE").val();
        String __EVENTVALIDATION = doc.select("#__EVENTVALIDATION").val();
        String hidJZ = doc.select("#hidJZ").val();
        Calendar cal = Calendar.getInstance();
        //设置当前时间
        cal.setTime(new Date());
        String endTime = sdf.format(cal.getTime());
        cal.set(Calendar.DAY_OF_MONTH, 1);//设置为本月的第一天
        String startTime = sdf.format(cal.getTime());
        params.put("__EVENTTARGET", "RegionPanel2$Region1$Toolbar1$ContentPanel1$btnSelect");
        params.put("__EVENTARGUMENT", __EVENTARGUMENT);
        params.put("__VIEWSTATE", __VIEWSTATE);
        params.put("__EVENTVALIDATION", __EVENTVALIDATION);
        params.put("hidJZ", hidJZ);
        params.put("RegionPanel2$Region1$Toolbar1$ContentPanel1$TextBox1", startTime);
        params.put("RegionPanel2$Region1$Toolbar1$ContentPanel1$TextBox2", endTime);
        params.put("RegionPanel2$Region1$Toolbar1$ContentPanel1$txtDBBH", "");
        params.put("RegionPanel2$Region1$Toolbar1$ContentPanel1$ddlCZFS", "----全部----");
        params.put("RegionPanel2$Region1$toolbarButtom$pagesize", "1");
        params.put("__box_page_state_changed", "false");
        params.put("__2_collapsed", "false");
        params.put("__6_selectedRows", "");
        params.put("__box_disabled_control_before_postback", "__10");
        params.put("__box_ajax_mark", "true");
        response = Request.post(url + "UserAccountment/AccountDetails2.aspx?id=75", headers, params);
        content = Request.getContent(response);
        Pattern pattern = Pattern.compile("box.__6_store.loadData.*?]]");
        String temp = "";
        Matcher m = pattern.matcher(content);
        if (m.find()) {
            temp = m.group(0);
        }
        if (!temp.equals("")) {
            temp = temp.split("\\(")[1];
            JSONArray list = JSONArray.fromObject(temp);
            Iterator it = list.iterator();
            LinkedList southList = new LinkedList();
            float balance = southPowerDetail.getBalance();
            while (it.hasNext()) {
                JSONArray list2 = JSONArray.fromObject(it.next().toString());
                SouthPowerUseData southPowerUseData = new SouthPowerUseData();
                southPowerUseData.setBuildingId(southPowerDetail.getBuildingId());
                southPowerUseData.setRoomId(southPowerDetail.getRoomId());
                southPowerUseData.setRoomName(southPowerDetail.getRoomName());
                southPowerUseData.setMoney(Float.parseFloat(list2.get(5).toString().trim()));
                southPowerUseData.setResidue(balance / cost);
                southPowerUseData.setUseTime(list2.get(9).toString().trim());
                southPowerUseData.setUsePower(Float.parseFloat(list2.get(5).toString().trim()) / cost);
                balance = balance + Float.parseFloat(list2.get(5).toString().trim());
                southList.add(southPowerUseData);
            }
            return southList;
        } else {
            return null;
        }
    }

    @Override
    public LinkedList obtainSouthPowerUseHistoryData(HashMap headers, SouthPowerInfo southPowerInfo, String beforeTime) {
        CloseableHttpResponse response = Request.get(url + "UserAccountment/AccountDetails2.aspx?id=75", headers);
        String content = Request.getContent(response);
        String buildingId = southPowerInfo.getBuildingId();
        String roomId = southPowerInfo.getRoomId();
        Document doc = Jsoup.parse(content);
        HashMap params = new HashMap();
        String __EVENTTARGET = doc.select("#__EVENTTARGET").val();
        String __EVENTARGUMENT = doc.select("#__EVENTARGUMENT").val();
        String __VIEWSTATE = doc.select("#__VIEWSTATE").val();
        String __EVENTVALIDATION = doc.select("#__EVENTVALIDATION").val();
        String hidJZ = doc.select("#hidJZ").val();
        Calendar cal = Calendar.getInstance();
        //设置当前时间
        cal.setTime(new Date());

        String startTime = beforeTime;
        String endTime = sdf.format(cal.getTime());
        if (!endTime.equals(startTime)) {
            params.put("__EVENTTARGET", "RegionPanel2$Region1$Toolbar1$ContentPanel1$btnSelect");
            params.put("__EVENTARGUMENT", __EVENTARGUMENT);
            params.put("__VIEWSTATE", __VIEWSTATE);
            params.put("__EVENTVALIDATION", __EVENTVALIDATION);
            params.put("hidJZ", hidJZ);
            params.put("RegionPanel2$Region1$Toolbar1$ContentPanel1$TextBox1", startTime);
            params.put("RegionPanel2$Region1$Toolbar1$ContentPanel1$TextBox2", endTime);
            params.put("RegionPanel2$Region1$Toolbar1$ContentPanel1$txtDBBH", "");
            params.put("RegionPanel2$Region1$Toolbar1$ContentPanel1$ddlCZFS", "----全部----");
            params.put("RegionPanel2$Region1$toolbarButtom$pagesize", "1");
            params.put("__box_page_state_changed", "false");
            params.put("__2_collapsed", "false");
            params.put("__6_selectedRows", "");
            params.put("__box_disabled_control_before_postback", "__10");
            params.put("__box_ajax_mark", "true");
            response = Request.post(url + "UserAccountment/AccountDetails2.aspx?id=75", headers, params);
            content = Request.getContent(response);
            Pattern pattern = Pattern.compile("box.__6_store.loadData.*?]]");
            String temp = "";
            Matcher m = pattern.matcher(content);
            if (m.find()) {
                temp = m.group(0);
            }
            if (!temp.equals("")) {
                temp = temp.split("\\(")[1];
                JSONArray list = JSONArray.fromObject(temp);
                Iterator it = list.iterator();
                LinkedList southList = new LinkedList();
                while (it.hasNext()) {
                    JSONArray list2 = JSONArray.fromObject(it.hasNext());
                    SouthPowerUseData southPowerUseData = new SouthPowerUseData();
                    southPowerUseData.setBuildingId(buildingId);
                    southPowerUseData.setRoomId(roomId);
                    southPowerUseData.setMoney(Float.parseFloat(list2.get(5).toString().trim()));
                    southPowerUseData.setResidue(0);
                    southPowerUseData.setUseTime(list2.get(9).toString().trim());
                    southPowerUseData.setUsePower(Float.parseFloat(list2.get(5).toString().trim()) / cost);
                    southList.add(southPowerUseData);
                }
                return southList;
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    @Override
    public LinkedList obtainSouthPowerBuyData(HashMap headers, SouthPowerInfo southPowerInfo) {
        CloseableHttpResponse response = Request.get(url + "UserAccountment/manCZCX2.aspx?id=76", headers);
        String content = Request.getContent(response);
        Document doc = Jsoup.parse(content);
        String buildingId = southPowerInfo.getBuildingId();
        String roomId = southPowerInfo.getRoomId();
        String __EVENTTARGET = doc.select("#__EVENTTARGET").val();
        String __EVENTARGUMENT = doc.select("#__EVENTARGUMENT").val();
        String __VIEWSTATE = doc.select("#__VIEWSTATE").val();
        String __EVENTVALIDATION = doc.select("#__EVENTVALIDATION").val();
        String hidJZ = doc.select("#hidJZ").val();
        Calendar cal = Calendar.getInstance();
        //设置当前时间
        cal.setTime(new Date());
        String end = sdf.format(cal.getTime());
        cal.add(Calendar.MONTH,-5);
        String start =sdf.format(cal.getTime());
        HashMap params = new HashMap();
        params.put("__EVENTTARGET", "RegionPanel1$Region2$Panel1$Panel8$Toolbar1$ContentPanel1$btnSelect");
        params.put("__EVENTARGUMENT", __EVENTARGUMENT);
        params.put("__VIEWSTATE", __VIEWSTATE);
        params.put("__EVENTVALIDATION", __EVENTVALIDATION);
        params.put("hidJZ", hidJZ);
        params.put("RegionPanel1$Region2$Panel1$Panel8$Toolbar1$ContentPanel1$TextBox1", start);
        params.put("RegionPanel1$Region2$Panel1$Panel8$Toolbar1$ContentPanel1$TextBox2", end);
        params.put("RegionPanel1$Region2$Panel1$Panel8$Toolbar1$ContentPanel1$ddljllx", "3");
        params.put("RegionPanel1$Region2$Panel1$Panel8$Toolbar1$ContentPanel1$ddlZLLX", "全部");
        params.put("RegionPanel1$Region2$Panel1$Panel8$Toolbar2$pagesize", "1");
        params.put("__box_page_state_changed", "false");
        params.put("__14_collapsed", "false");
        params.put("__20_collapsed", "false");
        params.put("__44_hidden", "true");
        params.put("__44_collapsed", "false");
        params.put("__box_disabled_control_before_postback", "__25");
        params.put("__box_ajax_mark", "true");
        response = Request.post(url + "UserAccountment/manCZCX2.aspx?id=76", headers, params);
        content = Request.getContent(response);
        Pattern pattern = Pattern.compile("box.__20_store.loadData.*?]]");
        String temp = "";
        Matcher m = pattern.matcher(content);
        if (m.find()) {
            temp = m.group(0);
        }
        if (!temp.equals("")) {
            temp = temp.split("\\(")[1];
            JSONArray list = JSONArray.fromObject(temp);
            Iterator it = list.iterator();
            LinkedList southList = new LinkedList();
            while (it.hasNext()) {
                JSONArray list2 = JSONArray.fromObject(it.next().toString());

                SouthPowerBuyData southPowerBuyData = new SouthPowerBuyData();
                southPowerBuyData.setBuildingId(buildingId);
                southPowerBuyData.setRoomId(roomId);
                southPowerBuyData.setRoomName(list2.get(0).toString().trim());
                southPowerBuyData.setAmmeter(list2.get(1).toString().trim());
                southPowerBuyData.setBuyType(list2.get(2).toString().trim());
                southPowerBuyData.setNumber(Integer.parseInt(list2.get(3).toString().trim()));
                southPowerBuyData.setMoney(Float.parseFloat(list2.get(4).toString().trim()));
                southPowerBuyData.setBuyTime(list2.get(5).toString().trim());
                southPowerBuyData.setBuyer(list2.get(6).toString().trim());
                southPowerBuyData.setDown(list2.get(7).toString().trim());
                southPowerBuyData.setDownTime(list2.get(8).toString().trim());
                southPowerBuyData.setEnergy(Float.parseFloat(list2.get(4).toString().trim())/cost);
                southList.add(southPowerBuyData);
            }
            return southList;
        } else {
            return null;
        }
    }



}
