package com.campus.controller;

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

import com.campus.pojo.PowerInfo;
import com.campus.service.CrawlPowerService;
import com.campus.service.UserService;

/**
 * 电费查询
 *
 * @author C_hao
 */
@Controller
public class PowerController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserService userService;

    @Autowired
    CrawlPowerService crawlPowerService;

    /**
     * 绑定宿舍
     *
     * @param openid     小程序用户的唯一标识
     * @param building   宿舍楼
     * @param roomName   宿舍号
     * @param buildingId 宿舍楼id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "pwbind", method = RequestMethod.POST)
    public Object powerBind(@RequestParam(value = "openid") String openid,
                            @RequestParam(value = "building") String building, @RequestParam(value = "roomname") String roomName,
                            @RequestParam(value = "buildingid") String buildingId) {
        building = building.trim();
        roomName = roomName.trim();
        openid = openid.trim();
        HashMap<String, Object> json = new HashMap<String, Object>();
        //参数为空
        if (building.equals("") || roomName.equals("") || openid.equals("") || buildingId.equals("")) {
            json.put("errmsg", VerifyStateEnum.Invalid);
        }
        //根据openid查询，不存在此用户
        else if (userService.selectUserInfo(openid) == null) {
            json.put("errmsg", VerifyStateEnum.Invalid);
        } else {
            SouthPowerInfo southPowerInfo = userService.selectSouthPowerInfo(openid);
            if (southPowerInfo != null) {
                int number = userService.deleteSouthPowerInfo(openid);
                if (number == 0) {
                    json.put("errmsg", VerifyStateEnum.Fail);//删除失败
                }
            }
            PowerInfo powerInfo = new PowerInfo();
            powerInfo.setOpenid(openid);
            powerInfo.setBuilding(building);
            powerInfo.setBuildingId(buildingId);
            powerInfo.setRoomName(roomName);
            //绑定宿舍信息
            int num = userService.insertPowerInfo(powerInfo);
            //绑定成功
            if (num > 0) {
                json.put("success", VerifyStateEnum.Success);
                //获取宿舍购电情况并存储
                crawlPowerService.obtainPowerBuy(building, roomName, buildingId);
                //获取宿舍用电情况并存储
                crawlPowerService.obtainPowerUse(building, roomName, buildingId);
            }
            //绑定失败
            else {
                json.put("success", VerifyStateEnum.Fail);
            }
        }
        return json;
    }

    /**
     * 获取电费数据
     *
     * @param openid    小程序用户的唯一标识
     * @param attribute 丢失属性
     * @param page      页码
     * @return 返回购电/用电数据
     */
    @ResponseBody
    @RequestMapping(value = "pwdata", method = RequestMethod.POST)
    public Object powerData(@RequestParam(value = "openid") String openid,
                            @RequestParam(value = "attribute") String attribute, @RequestParam(value = "page") String page) {

        attribute = attribute.trim();
        openid = openid.trim();
        page = page.trim();
        HashMap<String, Object> json = new HashMap<String, Object>();
        //参数为空
        if (openid.equals("") || attribute.equals("")) {
            json.put("errmsg", VerifyStateEnum.Invalid);
        }
        //不存在该用户
        else if (userService.selectUserInfo(openid) == null) {
            json.put("errmsg", VerifyStateEnum.Invalid);
        }
        //判断参数attribute类型是否正确
        else if (attribute.equals("use") || attribute.equals("buy")) {
            PowerInfo powerInfo = userService.selectPowerInfo(openid);
            //绑定北苑宿舍
            if (powerInfo != null) {
                json.put("pwbind", VerifyStateEnum.Success);
                List list = null;
                //对页码进行判断，非法页码默认为1
                boolean result = page.matches("[1-9]+");
                int startPage = 1;
                if (result) {
                    startPage = Integer.parseInt(page);
                    if (startPage < 1) {
                        startPage = 1;
                    }
                }
                //查询用电情况
                if (attribute.equals("use")) {
                    list = userService.selectPowerUseData(powerInfo, startPage, 5);
                }
                //查询购电情况
                else {
                    list = userService.selectPowerBuyData(powerInfo, startPage, 5);
                }
                json.put("pwdata", list);
                json.put("pwtype", 1);
            }
            //没有绑定北苑宿舍的情况
            else {
                SouthPowerInfo southpowerInfo = userService.selectSouthPowerInfo(openid);
                //没有北苑南苑绑定宿舍
                if (southpowerInfo == null) {
                    json.put("pwbind", VerifyStateEnum.Fail);
                }
                //已经绑定过宿舍
                else {
                    json.put("pwbind", VerifyStateEnum.Success);
                    List list = null;
                    //对页码进行判断，非法页码默认为1
                    boolean result = page.matches("[1-9]+");
                    int startPage = 1;
                    if (result) {
                        startPage = Integer.parseInt(page);
                        if (startPage < 1) {
                            startPage = 1;
                        }
                    }
                    //查询用电情况
                    if (attribute.equals("use")) {
                        list = userService.selectSouthPowerUseData(southpowerInfo, startPage, 5);
                    }
                    //查询购电情况
                    else {
                        list = userService.selectSouthPowerBuyData(southpowerInfo, startPage, 5);
                    }
                    json.put("pwdata", list);
                    json.put("pwtype", 0);
                }
            }

        }
        return json;
    }

    @RequestMapping(value = "southpwbind", method = RequestMethod.POST)
    @ResponseBody
    public Object southPowerBind(@RequestParam(value = "openid") String openid,
                                 @RequestParam(value = "buildingid") String buildingId,
                                 @RequestParam(value = "roomid") String roomId,
                                 @RequestParam(value = "password") String password) {
        buildingId = buildingId.trim();
        roomId = roomId.trim();
        openid = openid.trim();
        password = password.trim();
        HashMap<String, Object> json = new HashMap<String, Object>();
        //参数为空
        if (buildingId.equals("") || roomId.equals("") || openid.equals("")) {
            json.put("errmsg", VerifyStateEnum.Invalid);
        }
        //根据openid查询，不存在此用户
        else if (userService.selectUserInfo(openid) == null) {
            json.put("errmsg", VerifyStateEnum.Invalid);
        } else {
            PowerInfo powerInfo = userService.selectPowerInfo(openid);
            if (powerInfo != null) {
                int number = userService.deletePowerInfo(openid);
                if (number == 0) {
                    json.put("errmsg", VerifyStateEnum.Fail);//删除失败，即绑定失败
                    return json;
                }
            }
            SouthPowerInfo southPowerInfo = new SouthPowerInfo();
            southPowerInfo.setOpenid(openid);
            southPowerInfo.setBuildingId(buildingId);
            southPowerInfo.setRoomId(roomId);
            southPowerInfo.setPassword(password);
            try {
                crawlPowerService.obtainSouthPower(southPowerInfo, true);
                json.put("pwbing", VerifyStateEnum.Success);
            } catch (PasswordErrorException e) {
                json.put("errmsg", VerifyStateEnum.ErrorPassword);
            } catch (VerifyError e) {
                json.put("errmsg", e.toString());
            }
        }
        return json;
    }
}
