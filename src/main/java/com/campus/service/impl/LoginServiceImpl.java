package com.campus.service.impl;

import com.campus.etdao.LoginEtDao;
import com.campus.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service(value = "loginService")
public class LoginServiceImpl implements LoginService {

    @Autowired
    LoginEtDao loginDao;

    /**
     * 获取用户的openid
     * @param code
     * @return
     */
    @Override
    public String getOpenid(String code) {
        return loginDao.getOpenid(code);
    }

    /**
     * 解密微信用户发来的登陆信息
     * @param code
     * @param encryptedData
     * @param iv
     * @return
     */
    @Override
    public Map decodeUser(String code, String encryptedData, String iv) {
        return loginDao.decodeUser(code, encryptedData, iv);
    }

}
