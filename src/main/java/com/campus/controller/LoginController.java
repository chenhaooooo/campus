package com.campus.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import com.campus.pojo.UserInfo;
import com.campus.service.LoginService;
import com.campus.service.UserService;

/**
 * @author C_hao
 */
@Controller
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    LoginService loginService;
    @Autowired
    UserService userService;

    /**
     * 登录信息与绑定信息
     * @param code
     * 小程序用户的临时code
     * @param encryptedData
     * 加密信息
     * @param iv
     * 解密向量
     * @return
     * 返回openid和是否绑定校园卡
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object Login(@RequestParam(value = "code") String code,
                        @RequestParam(value = "encryptedData") String encryptedData,
                        @RequestParam(value = "iv") String iv) {
        code = code.trim();
        encryptedData = encryptedData.trim();
        iv = iv.trim();
        HashMap<String,Object> json = new HashMap<String,Object>();
        //解析微信用户发送过来登录信息
        Map map = loginService.decodeUser(code, encryptedData, iv);
        //得到用户信息
        UserInfo userInfo = (UserInfo) map.get("userInfo");
        //得到openid
        String openid = (String) userInfo.getOpenid();
        //得到解析情况
        int status = (int) map.get("status");
        //openid存在且成功解析
        if (openid != null && !openid.equals("") && status == 0) {
            UserInfo user = userService.selectUserInfo(openid);
            //设置更改时间
            userInfo.setRecentTime(new Date());
            // 没有该用户
            if (user == null) {
                userService.insertUserInfo(userInfo);
            }
            // 用户信息更改，session也会改变
            else if (!user.getSessionKey().equals(userInfo.getSessionKey())) {
                userService.updateUserInfo(userInfo);
            }
            //返回openid当作标识
            json.put("openid", openid);
            CardInfo card = userService.selectOpenidCardInfo(openid);
            if (card == null) {
                json.put("bind", VerifyStateEnum.NoExist);
            } else {
                json.put("bind", VerifyStateEnum.Exist);
            }
        }
        //发送过来的code是无效的
        else {
            json.put("errmsg", VerifyStateEnum.Invalid);
        }
        return json;
    }
}
