package com.campus.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 枚举类
 */
public enum VerifyStateEnum {

    Success("1"), Fail("0"), Invalid("invalid"), ErrorPassword("密码错误"), ErrorLength("密码长度不符合要求"),
    NoBindCard("未绑定校园卡"), IllegalType("非法类型"), UpdateFail("更新失败"),Exist("1"),NoExist("0");


    private String stateInfo;

    private VerifyStateEnum(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    //返回此对象的json数据
    @JsonValue
    public String getStateInfo()
    {
        return this.stateInfo;
    }
    public void setStateInfo(String stateInfo)
    {
        this.stateInfo=stateInfo;
    }

}
