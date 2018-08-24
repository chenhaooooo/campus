package com.campus.controller;

import java.util.HashMap;

import com.campus.enums.VerifyStateEnum;
import com.campus.exception.PasswordErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campus.pojo.CardInfo;
import com.campus.service.CrawlCardService;
import com.campus.service.UserService;
import com.campus.util.DataBase64;

/**
 * @author C_hao
 */
@Controller
public class LostController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    CrawlCardService crawlCardService;
    @Autowired
    UserService userService;


    /**
     * 挂失校园卡
     *
     * @param openid
     * 小程序用户的唯一标识
     * @param password
     * 校园卡密码
     * @return
     * 挂失结果
     */
    @ResponseBody
    @RequestMapping(value = "lost", method = RequestMethod.POST)
    public Object lost(@RequestParam(value = "openid") String openid,
                       @RequestParam(value = "password") String password) {

        openid = openid.trim();
        HashMap<String,Object> json = new HashMap<String,Object>();
        //参数为空
        if (openid.equals("")) {
            json.put("errmsg", VerifyStateEnum.Invalid);
        } else {
            //查询openid绑定的校园卡
            CardInfo cardInfo = userService.selectOpenidCardInfo(openid);
            //校园卡存在
            if (cardInfo != null) {
                String account = cardInfo.getAccount();
                //登录时，密码错误会抛出异常
                try {
                    String cookie = crawlCardService.loginCampus(account, password);
                    //校园卡挂失
                    Boolean result = crawlCardService.lostCampus(account, password, cookie);
                    //成功挂失
                    if (result) {
                        json.put("success", VerifyStateEnum.Success);
                    }
                    //失败挂失
                    else {
                        json.put("success", VerifyStateEnum.Fail);
                    }
                    //判断是否密码被更改
                    if (!password.equals(DataBase64.decodeBase64(cardInfo.getPassword()))) {
                        userService.updateCardInfo(cardInfo);
                    }
                }
                //密码错误
                catch (PasswordErrorException e) {
                    json.put("errmsg", VerifyStateEnum.ErrorPassword);
                }


            }
            //没有绑定校园卡
            else {
                json.put("errmsg", VerifyStateEnum.NoBindCard);
            }

        }

        return json;

    }
}
