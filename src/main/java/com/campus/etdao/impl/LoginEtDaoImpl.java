package com.campus.etdao.impl;

import com.campus.etdao.LoginEtDao;
import com.campus.pojo.UserInfo;
import com.campus.util.AesCbcUtil;
import com.campus.util.DataBase64;
import com.campus.util.HttpRequest;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


@Repository(value = "loginEtDao")
public class LoginEtDaoImpl implements LoginEtDao {

    // 小程序唯一标识 (在微信小程序管理后台获取)
    static final String APPID = "wx00588d1114809501";
    // 小程序的 app secret (在微信小程序管理后台获取)
    static final String SECRET = "8f9db73a982cd46a16dbbc666fef1d93";
    // 授权（必填）
    static final String TYPE = "authorization_code";
    //获取openid的地址
    static final String URL = "https://api.weixin.qq.com/sns/jscode2session";
    private Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * 获取用户的openid
     *
     * @param code 用户允许登录后，回调内容会带上 code（有效期五分钟），开发者需要将 code 发送到开发者服务器后台，使用code 换取
     *             session_key api，将 code 换成 openid 和 session_key
     * @return 返回openid
     */
    @Override
    public String getOpenid(String code) {
        // 请求参数
        String params = "appid=" + APPID + "&secret=" + SECRET + "&js_code=" + code + "&grant_type="
                + TYPE;
        // 发送请求
        String sr = HttpRequest.sendGet(URL, params,"UTF-8");
        // 解析相应内容（转换成json对象）
        JSONObject json = JSONObject.fromObject(sr);
        // 获取会话密钥（session_key）
        String session_key = (String) json.get("session_key");
        // 用户的唯一标识（openid）
        String openid = (String) json.get("openid");

        return openid;
    }
    /**
     * 获取用户的openid,session_key,并对用户隐私数据进行解密
     *
     * @param code 用户允许登录后，回调内容会带上 code（有效期五分钟），开发者需要将 code 发送到开发者服务器后台，使用code 换取
     *             session_key api，将 code 换成 openid 和 session_key
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param iv 加密算法的初始向量
     * @return 返回Map字典。status是解密是否成功标记，值为0、1；userInfo有用户的openid,session_key已经用户的敏感信息
     */
    @Override
    public Map decodeUser(String code, String encryptedData, String iv) {
        // 请求参数
        String params = "appid=" + APPID + "&secret=" + SECRET + "&js_code=" + code + "&grant_type="
                + TYPE;
        // 发送请求
        String sr = HttpRequest.sendGet(URL, params,"UTF-8");
        // 解析相应内容（转换成json对象）
        JSONObject json = JSONObject.fromObject(sr);
        // 获取会话密钥（session_key）
        String sessionKey = (String) json.get("session_key");
        // 用户的唯一标识（openid）
        String openid = (String) json.get("openid");
        HashMap map = new HashMap();
        //对encryptedData加密数据进行AES解密
        try {
            String result = AesCbcUtil.decrypt(encryptedData, sessionKey, iv, "UTF-8");
            JSONObject userInfoJSON = JSONObject.fromObject(result);
            UserInfo userInfo=new UserInfo();
            map.put("userInfo", userInfo);
            if (null != result && result.length() > 0 && openid != null && openid.length() > 0) {
                map.put("status", 0);
                map.put("msg", "解密成功");
                //UserInfo userInfo=new UserInfo();//创建一个JavaBean
                userInfo.setOpenid(openid);
                userInfo.setLanguage((String)userInfoJSON.get("language"));
                //对昵称进行base64编码,防止表情字符
                userInfo.setNickName(DataBase64.encodeBase64((String)userInfoJSON.get("nickName")));
                userInfo.setGender((int)userInfoJSON.get("gender"));
                userInfo.setCity((String)userInfoJSON.get("city"));
                userInfo.setProvince((String)userInfoJSON.get("province"));
                userInfo.setCountry((String)userInfoJSON.get("country"));
                userInfo.setSessionKey(sessionKey);

                return map;
            }
        } catch (Exception e) {
            //输出日志
            logger.info("错误信息："+e.getMessage());
        }
        map.put("status", 1);
        map.put("msg", "解密失败");

        return map;
    }
}
