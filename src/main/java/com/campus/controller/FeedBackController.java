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

import com.campus.pojo.FeedBack;
import com.campus.pojo.UserInfo;
import com.campus.service.UserService;

/**
 * @author C_hao
 */
@Controller
public class FeedBackController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserService userService;

    /**
     * 反馈信息提交
     *
     * @param openid 小程序用户的唯一标识
     * @param title 反馈信息的标题
     * @param message 反馈信息的内容
     * @return 提交结果
     */
    @ResponseBody
    @RequestMapping(value = "fbcommit")
    public Object feedBackCommit(@RequestParam(value = "openid") String openid,
                                 @RequestParam(value = "title") String title, @RequestParam(value = "message") String message) {
        openid = openid.trim();
        title = title.trim();
        message = message.trim();
        HashMap<String, Object> json = new HashMap<String, Object>();
        //参数为空
        if (openid.equals("") || message.equals("") || title.equals("")) {
            json.put("errmsg", VerifyStateEnum.Invalid);
        } else {
            UserInfo userInfo = userService.selectUserInfo(openid);
            if (userInfo != null) {
                FeedBack feedBack = new FeedBack();
                feedBack.setMessage(message);
                feedBack.setOpenid(openid);
                feedBack.setTitle(title);
                int num = userService.insertFeedBack(feedBack);
                //添加反馈信息成功
                if (num > 0) {
                    json.put("success", VerifyStateEnum.Success);
                }
                //添加反馈信息失败
                else {
                    json.put("success", VerifyStateEnum.Fail);
                }
            }
            //openid非法，即没有存在用户
            else {
                json.put("errmsg", VerifyStateEnum.Invalid);
            }
        }
        return json;

    }

    /**
     * 获取全部反馈信息
     *
     * @param openid 小程序用户的唯一标识
     * @return 返回用户全部的反馈信息
     */
    @ResponseBody
    @RequestMapping(value = "fball",method=RequestMethod.POST)
    public Object feedBackAll(@RequestParam(value = "openid") String openid) {
        openid = openid.trim();
        HashMap<String, Object> json = new HashMap<String, Object>();
        //参数为空
        if (openid.equals("")) {
            json.put("errmsg", VerifyStateEnum.Invalid);
        } else {
            UserInfo userInfo = userService.selectUserInfo(openid);
            //用户存在
            if (userInfo != null) {
                //获取反馈信息
                List<FeedBack> list = userService.selectFeedBack(openid);
                json.put("data", list);
            }
            //用户不存在，即openid非法
            else {
                json.put("errmsg", VerifyStateEnum.Invalid);
            }

        }
        return json;

    }
}
