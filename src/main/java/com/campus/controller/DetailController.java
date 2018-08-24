package com.campus.controller;

import java.util.HashMap;
import java.util.List;

import com.campus.enums.VerifyStateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campus.pojo.CardDetail;
import com.campus.pojo.CardInfo;
import com.campus.pojo.PowerInfo;
import com.campus.pojo.PowerUseData;
import com.campus.service.UserService;

/**
 * @author C_hao
 */
@Controller
public class DetailController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserService userService;

    /**
     * 获取校园卡详细信息
     *
     * @param openid
     * 小程序用户的唯一标识
     * @return
     * 校园卡详细信息
     */
    @ResponseBody
    @RequestMapping(value = "detail", method = RequestMethod.POST)
    public Object detail(@RequestParam(value = "openid") String openid) {
        openid = openid.trim();
        HashMap<String,Object> json = new HashMap<String,Object>();
        //参数为空
        if (openid.equals("")) {
            json.put("errmsg", VerifyStateEnum.Invalid);
        } else {
            //查询openid对应校园卡的信息
            CardInfo cardInfo = userService.selectOpenidCardInfo(openid);
            //校园卡信息为空，则未绑定校园卡
            if (cardInfo == null) {
                json.put("errmsg", VerifyStateEnum.NoBindCard);
            }
            //校园卡信息不为空，存在
            else {
                String account = cardInfo.getAccount();
                CardDetail cardDetail = userService.selectCardDetail(account);
                List list = userService.selectDetailData(account);
                PowerInfo powerInfo = userService.selectPowerInfo(openid);
                HashMap pwInfo = new HashMap();
                if (powerInfo != null) {
                    pwInfo.put("building", powerInfo.getBuilding());
                    pwInfo.put("roomname", powerInfo.getRoomName());
                    PowerUseData powerUseData = userService.selectFirstPowerUse(powerInfo);
                    json.put("pwdata", powerUseData);
                }
                json.put("pwinfo", pwInfo);
                json.put("info", cardDetail);
                json.put("data", list);
            }
        }

        return json;
    }
}
