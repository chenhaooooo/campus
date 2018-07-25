package com.campus.controller;

import java.util.Date;

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

import com.campus.pojo.PickLost;
import com.campus.pojo.UserInfo;
import com.campus.service.UserService;

/**
 * @author C_hao
 */
@Controller
public class PickLostController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserService userService;

    /**
     * 发布拾/失信息
     *
     * @param openid 小程序用户的唯一标识
     * @param owner 校园卡主人
     * @param account 校园卡账号
     * @param department 院系
     * @param ctway 联系方式
     * @param message 备注信息
     * @param attribute 拾/失属性
     * @return
     */
    @RequestMapping(value = "picklost", method = RequestMethod.POST)
    @ResponseBody
    public Object LostReporter(@RequestParam(value = "openid") String openid,
                               @RequestParam(value = "owner") String owner, @RequestParam(value = "account") String account,
                               @RequestParam(value = "department") String department, @RequestParam(value = "ctway") String ctway,
                               @RequestParam(value = "message") String message, @RequestParam(value = "attribute") String attribute) {
        openid = openid.trim();
        owner = owner.trim();
        account = account.trim();
        department = department.trim();
        ctway = ctway.trim();
        message = message.trim();
        attribute = attribute.trim();
        HashMap json = new HashMap();
        //参数为空
        if (openid.equals("") || owner.equals("") || account.equals("")
                || department.equals("") || ctway.equals("")
                || attribute.equals("")) {
            json.put("errmsg", VerifyStateEnum.Invalid);
        }
        //类型attribute不是pick或lost
        else if (!(attribute.equals("pick") || attribute.equals("lost"))) {
            json.put("errmsg", VerifyStateEnum.IllegalType);
        } else {
            PickLost pickLost = new PickLost();
            pickLost.setAccount(account);
            pickLost.setAttribute(attribute);
            pickLost.setCtway(ctway);
            pickLost.setDepartment(department);
            pickLost.setMessage(message);
            pickLost.setOpenid(openid);
            pickLost.setOwner(owner);
            pickLost.setRecentTime(new Date());
            int num = userService.insertPickLost(pickLost);
            //添加丢失信息成功
            if (num > 0) {
                json.put("success", VerifyStateEnum.Success);
            }
            //添加丢失信息失败
            else {
                json.put("success", VerifyStateEnum.Fail);
            }
        }
        return json;

    }

    /**
     * 查看拾/失栏
     *
     * @param openid 小程序用户的唯一标识
     * @param attribute 拾/失属性
     * @param page 页码
     * @return 返回拾/失信息
     */
    @RequestMapping(value = "plbar", method = RequestMethod.POST)
    @ResponseBody
    public Object LostBar(@RequestParam(value = "openid") String openid,
                          @RequestParam(value = "attribute") String attribute, @RequestParam(value = "page") String page) {
        openid = openid.trim();
        page = page.trim();
        HashMap json = new HashMap();
        //参数为空
        if (openid.equals("")) {
            json.put("errmsg", VerifyStateEnum.Invalid);
        }
        //参数attribute类型非法
        else if (!(attribute.equals("pick") || attribute.equals("lost"))) {
            json.put("errmsg", VerifyStateEnum.IllegalType);
        } else {
            //判断是否存在openid对应的用户
            UserInfo userInfo = userService.selectUserInfo(openid);
            //用户存在
            if (userInfo != null) {
                //对页码进行正则匹配，防止出现非法页码
                //若出现非法页码，则页码默认为1
                boolean result = page.matches("[1-9]+");
                int startPage = 1;
                if (result) {
                    startPage = Integer.parseInt(page);
                    if (startPage < 1) {
                        startPage = 1;
                    }
                }
                //得到丢失信息
                List<PickLost> list = userService.selectPickLost(attribute, startPage, 5);
                json.put("data", list);
            }
        }
        return json;

    }

    /**
     * 隐藏用户发布信息
     *
     * @param openid
     * @param time
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "pldelete", method = RequestMethod.POST)
    public Object hiddeOperation(@RequestParam(value = "openid") String openid,
                                 @RequestParam(value = "time") String time) {
        openid = openid.trim();
        time = time.trim();
        HashMap<String,Object> json = new HashMap<String,Object>();
        //参数为空
        if (openid.equals("") || time.equals("")) {
            json.put("errmsg", VerifyStateEnum.Invalid);
        } else {
            PickLost pickLost = new PickLost();
            pickLost.setOpenid(openid);
            pickLost.setTime(time);
            //将挂失信息隐藏，不显示给用户
            pickLost.setHidden("1");
            //更新
            int num = userService.updatePickLost(pickLost);
            //隐藏成功
            if (num > 0) {
                json.put("success", VerifyStateEnum.Success);
            }
            //隐藏失败
            else {
                json.put("success", VerifyStateEnum.Fail);
            }
        }
        return json;
    }

    /**
     * 获取发布信息
     *
     * @param openid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "reportAll", method = RequestMethod.POST)
    public Object selectReportAll(@RequestParam(value = "openid") String openid) {
        openid = openid.trim();
        HashMap json = new HashMap();
        //参数为空
        if (openid.equals("")) {
            json.put("errmsg", VerifyStateEnum.Invalid);
        } else {
            PickLost pickLost = new PickLost();
            pickLost.setOpenid(openid);
            //查询所有发布过的信息
            List pl = userService.selectReportAll(openid);
            json.put("pldata", pl);
        }

        return json;
    }
}
