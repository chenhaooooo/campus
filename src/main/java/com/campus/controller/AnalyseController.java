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

import com.campus.pojo.CardInfo;
import com.campus.service.UserService;

/**
 * @author C_hao
 */
@Controller
public class AnalyseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserService userService;

    /**
     * 对校园卡消费数据进行分析返回
     * @param openid
     * 小程序用户的唯一标识
     * @return
     * 返回最近4个月的消费情况（每个月的总额），当前月份消费地点
     */
    @RequestMapping(value = "analyse", method = RequestMethod.POST)
    @ResponseBody
    public Object analyse(@RequestParam(value = "openid") String openid) {
        HashMap<String,Object> json = new HashMap<String,Object>();
        openid = openid.trim();
        //参数为空
        if (openid.equals("")) {
            json.put("errmsg", VerifyStateEnum.Invalid);
        } else {
            //查询出openid对应的账号信息
            CardInfo cardInfo = userService.selectOpenidCardInfo(openid);
            //该账号存在
            if (cardInfo != null) {
                String account=cardInfo.getAccount();
                //最近4个月月消费信息数据
                List monthInfo = userService.selectCardDataMonth(account);
                //这个月内的地方消费信息数据
                List placeInfo = userService.selectCardDataPlace(account);
                //构造一个字典存放这些数据
                HashMap<String,List> data = new HashMap<String,List>();
                data.put("monthInfo", monthInfo);
                data.put("placeInfo", placeInfo);
                //放入要返回的json中
                json.put("data", data);
            }
            //没有绑定校园卡
            else {
                json.put("errmsg", VerifyStateEnum.NoBindCard);
            }

        }
        return json;

    }
}
