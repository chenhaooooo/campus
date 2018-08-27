package com.campus.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import com.campus.enums.VerifyStateEnum;
import com.campus.exception.PasswordErrorException;
import com.campus.pojo.SouthPowerInfo;
import org.bouncycastle.openssl.PasswordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campus.pojo.CardData;
import com.campus.pojo.CardInfo;
import com.campus.pojo.PowerInfo;
import com.campus.service.CrawlCardService;
import com.campus.service.CrawlPowerService;
import com.campus.service.UserService;
import com.campus.util.DataBase64;

/**
 * @author C_hao
 */
@Controller
public class DataController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserService userService;

    @Autowired
    CrawlCardService crawlCardService;
    @Autowired
    CrawlPowerService crawlPowerService;

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取校园卡消费记录详细
     *
     * @param openid
     * 小程序用户的唯一标识
     * @param page
     * 页码
     * @return
     * 返回校园卡消费记录
     */
    @ResponseBody
    @RequestMapping(value = "data", method = RequestMethod.POST)
    public Object data(@RequestParam(value = "openid") String openid, @RequestParam(value = "page") String page) {
        openid = openid.trim();
        page = page.trim();
        HashMap<String,Object> json = new HashMap<String,Object>();
        //参数为空
        if (openid.equals("")) {
            json.put("errmsg", VerifyStateEnum.Invalid);
        } else {
            //对页码进行判断，出现非法页码默认为1
            boolean result = page.matches("[1-9]+");
            int startPage = 1;
            if (result) {
                startPage = Integer.parseInt(page);
                if (startPage < 1) {
                    startPage = 1;
                }
            }
            //查询出openid对应的校园卡信息
            CardInfo cardInfo = userService.selectOpenidCardInfo(openid);
            //校园卡信息不为空
            if (cardInfo != null) {
                //根据页码，获取校园卡消费记录
                List<CardData> list = userService.selectCardData(cardInfo.getAccount(), startPage, 10);
                json.put("data", list);
            }
            //校园卡信息为空，则未绑定校园卡信息
            else {
                json.put("errmsg", VerifyStateEnum.NoBindCard);
            }

        }

        return json;

    }

    /**
     * 更新校园卡数据
     *
     * @param openid
     * 小程序用户的唯一标识
     * @return
     * 返回更新结果
     */
    @ResponseBody
    @RequestMapping(value = "obtain", method = RequestMethod.POST)
    public Object obtain(@RequestParam(value = "openid") String openid) {
        openid = openid.trim();
        HashMap json = new HashMap();
        if (openid.equals("")) {
            json.put("errmsg", VerifyStateEnum.Invalid);
        } else {
            //查询openid对应绑定的校园卡信息
            CardInfo cardInfo = userService.selectOpenidCardInfo(openid);
            if (cardInfo != null) {
                String account = cardInfo.getAccount();
                String password = DataBase64.decodeBase64(cardInfo.getPassword());
                CardData cardData = userService.selectCardDataFirst(cardInfo.getAccount());
                String beforeTime = "";
                //如果存在此校园卡消费记录，则读取出最新一笔的时间
                if (cardData != null) {
                    beforeTime = cardData.getConsumeTime();
                }
                //获取最新数据并存储起来
                Boolean result = crawlCardService.obtainCampusNews(account, password, beforeTime);

                if (result) {
                    json.put("success",VerifyStateEnum.Success);
                }
                //更新失败，方便前端处理，不然是VerifyStateEnum.Fail
                else {
                    json.put("errmsg", VerifyStateEnum.UpdateFail);
                }
            }

            PowerInfo powerInfo = userService.selectPowerInfo(openid);
            if (powerInfo != null) {
                final String roomName = powerInfo.getRoomName();
                final String building = powerInfo.getBuilding();
                final String buildingId = powerInfo.getBuildingId();
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        crawlPowerService.obtainPowerBuy(building, roomName, buildingId);
                        crawlPowerService.obtainPowerUse(building, roomName, buildingId);

                    }

                }).start();

            }
            final SouthPowerInfo southPowerInfo = userService.selectSouthPowerInfo(openid);
            if(southPowerInfo != null)
            {


                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            crawlPowerService.obtainSouthPower(southPowerInfo,false);
                        } catch (PasswordErrorException e) {
                            e.printStackTrace();
                        }

                    }

                }).start();
            }

        }
        return json;

    }

}
