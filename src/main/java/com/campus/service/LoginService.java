package com.campus.service;

import java.util.Map;

public interface LoginService {
    /**
     * 根据微信用户登录的code解析初openid
     * @param code
     * @return
     */
    public String getOpenid(String code);

    /**
     * 解析出微信用户登录的加密信息
     * @param code
     * @param encryptedData
     * @param iv
     * @return
     */
    public Map decodeUser(String code, String encryptedData, String iv);
}
