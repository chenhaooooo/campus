package com.campus.etdao;

import com.campus.pojo.UserInfo;
import com.campus.util.AesCbcUtil;
import com.campus.util.DataBase64;
import com.campus.util.HttpRequest;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public interface LoginEtDao {

    /**
     * 获取用户的openid小程序的唯一标识
     *
     * @param code 用户允许登录后，回调内容会带上 code（有效期五分钟），开发者需要将 code 发送到开发者服务器后台，使用code 换取
     *             session_key api，将 code 换成 openid 和 session_key
     * @return 返回openid
     */
    public String getOpenid(String code);

    /**
     * 获取用户的openid,session_key,并对用户隐私数据进行解密
     *
     * @param code          用户允许登录后，回调内容会带上 code（有效期五分钟），开发者需要将 code 发送到开发者服务器后台，使用code 换取
     *                      session_key api，将 code 换成 openid 和 session_key
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param iv            加密算法的初始向量
     * @return 返回Map字典。status是解密是否成功标记，值为0、1；userInfo有用户的openid,session_key已经用户的敏感信息
     */
    public Map decodeUser(String code, String encryptedData, String iv);
}
