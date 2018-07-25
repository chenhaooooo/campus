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
public class BindingController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserService userService;
    @Autowired
    CrawlCardService crawlCardService;

    /**
     * 绑定校园卡信息
     * @param openid 小程序用户的唯一标识
     * @param account 校园卡账号（学号）
     * @param password 校园卡密码（六位数）
     * @return 返回绑定结果
     */
    @ResponseBody
    @RequestMapping(value = "binding", method = RequestMethod.POST)
    public Object Binding(@RequestParam(value = "openid") String openid,
                          @RequestParam(value = "account") String account, @RequestParam(value = "password") String password) {
        openid = openid.trim();
        account = account.trim();
        password = password.trim();
        HashMap<String,Object> json = new HashMap<String,Object>();
        //参数为空
        if (openid.equals("") || account.equals("") || password.equals("")) {
            json.put("errmsg", VerifyStateEnum.Invalid);
        } else {
            CardInfo cardInfo = new CardInfo();
            cardInfo.setOpenid(openid);
            cardInfo.setAccount(account);
            cardInfo.setPassword(DataBase64.encodeBase64(password));
            CardInfo card = userService.selectAccountCardInfo(account);
            if (card != null) {
                json.put("exist", VerifyStateEnum.Exist);
            } else {

                try {
                    String cookie = crawlCardService.loginCampus(account, password);

                    int num = userService.insertCardInfo(cardInfo);
                    if (num > 0) {
                        //绑定成功
                        json.put("success", VerifyStateEnum.Success);
                        // 开启多线程，获取历史记录

                    } else {
                        json.put("success", VerifyStateEnum.Fail);
                    }
                }
                //绑定密码错误
                catch (PasswordErrorException e)
                {
                    json.put("msg",VerifyStateEnum.ErrorPassword);
                }


            }
        }

        return json;

    }

    /**
     * 删除绑定校园卡
     * @param openid 小程序用户的唯一标识
     * @return 返回删除的结果
     */
    @ResponseBody
    @RequestMapping(value = "dtbind", method = RequestMethod.POST)
    public Object DeleteBind(@RequestParam(value = "openid") String openid) {
        openid = openid.trim();
        HashMap<String,Object> json = new HashMap<String,Object>();
        //参数为空
        if (openid.equals("")) {
            json.put("errmsg", VerifyStateEnum.Invalid);
        } else {
            //根据openid查询相对应的校园卡信息
            CardInfo cardInfo = userService.selectOpenidCardInfo(openid);
            //校园卡信息存在
            if (cardInfo != null) {
                int num = userService.deleteBinding(cardInfo);
                if (num > 0) {
                    json.put("success", VerifyStateEnum.Success);
                } else {
                    json.put("success", VerifyStateEnum.Fail);
                }
            }
            //校园卡信息不存在
            else {
                json.put("errmsg", VerifyStateEnum.NoBindCard);
            }
        }
        return json;

    }
}
