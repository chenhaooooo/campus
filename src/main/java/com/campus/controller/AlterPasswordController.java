package com.campus.controller;

import java.util.HashMap;

import com.campus.enums.VerifyStateEnum;
import com.campus.exception.PasswordErrorException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class AlterPasswordController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    CrawlCardService crawlCardService;
    @Autowired
    UserService userService;

    /**
     * 修改密码
     *
     * @param openid
     * 小程序用户的唯一标识
     * @param password
     * 旧密码
     * @param newPassword
     * 新密码
     * @return
     * 返回修改校园卡密码的结果
     */
    @ResponseBody
    @RequestMapping(value = "alterpwd", method = RequestMethod.POST)
    public Object alterPassword(@RequestParam(value = "openid") String openid,
                                @RequestParam(value = "password") String password,
                                @RequestParam(value = "newpassword") String newPassword) {
        openid = openid.trim();
        password = password.trim();
        newPassword = newPassword.trim();
        HashMap<String,Object> json = new HashMap<String,Object>();

        //参数为空
        if (openid.equals("") || password.equals("") || newPassword.equals("")) {
            json.put("errmsg", VerifyStateEnum.Invalid);
        }
        //密码长度不符合要求
        else if (password.length() != 6 || newPassword.length() != 6) {
            json.put("errmsg", VerifyStateEnum.ErrorLength);
        } else {
            CardInfo cardInfo = userService.selectOpenidCardInfo(openid);

            if (cardInfo != null) {
                try {
                    //账号
                    String account=cardInfo.getAccount();
                    //登陆,用输入的密码登陆网站
                    String cookie = crawlCardService.loginCampus(cardInfo.getAccount(), password);
                    //更改密码
                    Boolean result = crawlCardService.alterPassword(cardInfo.getAccount(), password, newPassword,
                            cookie);
                    //更改成功
                    if (result) {
                        json.put("success", VerifyStateEnum.Success);
                        //更新信息，放回数据库密码 需要加密
                        cardInfo.setPassword(DataBase64.encodeBase64(newPassword));
                        userService.updateCardInfo(cardInfo);
                    }
                    //更改失败
                    else {
                        json.put("success", VerifyStateEnum.Fail);
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