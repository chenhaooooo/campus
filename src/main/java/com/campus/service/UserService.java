package com.campus.service;

import java.util.ArrayList;
import java.util.List;

import com.campus.pojo.CardData;
import com.campus.pojo.CardDetail;
import com.campus.pojo.CardInfo;
import com.campus.pojo.FeedBack;
import com.campus.pojo.PickLost;
import com.campus.pojo.PowerBuyData;
import com.campus.pojo.PowerInfo;
import com.campus.pojo.PowerUseData;
import com.campus.pojo.UserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public interface UserService {
    /**
     * 获取用户信息
     *
     * @param openid
     * @return
     */

    public UserInfo selectUserInfo(String openid);

    /**
     * 插入用户信息
     *
     * @param userInfo
     * @return
     */

    public int insertUserInfo(UserInfo userInfo);

    /**
     * 更新用户信息
     *
     * @param userInfo
     * @return
     */

    public int updateUserInfo(UserInfo userInfo);

    /**
     * 根据openid获取用户校园卡信息
     *
     * @param openid
     * @return
     */

    public CardInfo selectOpenidCardInfo(String openid);

    /**
     * 插入校园卡信息
     *
     * @param cardInfo
     * @return
     */

    public int insertCardInfo(CardInfo cardInfo);

    /**
     * 获取校园卡详细信息
     *
     * @param account
     * @return
     */

    public CardDetail selectCardDetail(String account);

    /**
     * 根据账号account获取用户校园卡信息
     *
     * @param account
     * @return
     */

    public CardInfo selectAccountCardInfo(String account);

    /**
     * 获取校园卡前十笔消费记录
     *
     * @param account
     * @return
     */

    public List<CardData> selectDetailData(String account);

    /**
     * 获取校园卡消费记录，按页查询
     *
     * @param account
     * @param startPage
     * @param PageSize
     * @return
     */

    public List<CardData> selectCardData(String account, int startPage, int PageSize);

    /**
     * 获取校园卡最近四个月的月消费情况
     *
     * @param account
     * @return
     */

    public List selectCardDataMonth(String account);

    /**
     * 获取当月校园卡消费地点
     *
     * @param account
     * @return
     */

    public List selectCardDataPlace(String account);

    /**
     * 更新校园卡账号密码信息
     *
     * @param cardInfo
     * @return
     */

    public int updateCardInfo(CardInfo cardInfo);

    /**
     * 获取最新的一笔消费记录
     *
     * @param account
     * @return
     */

    public CardData selectCardDataFirst(String account);

    /**
     * 插入校园卡丢失信息
     *
     * @param pickLost
     * @return
     */

    public int insertPickLost(PickLost pickLost);

    /**
     * 获取丢失信息
     *
     * @param attribute
     * @param startPage
     * @param PageSize
     * @return
     */

    public List<PickLost> selectPickLost(String attribute, int startPage, int PageSize);

    /**
     * 更新校园卡丢失信息
     *
     * @param pickLost
     * @return
     */

    public int updatePickLost(PickLost pickLost);

    /**
     * 获取发布校园卡丢失信息
     *
     * @param openid
     * @return
     */

    public List<PickLost> selectReportAll(String openid);

    /**
     * 获取用户的反馈信息
     *
     * @param account
     * @return
     */

    public List<FeedBack> selectFeedBack(String account);

    /**
     * 插入反馈信息
     *
     * @param feedBack
     * @return
     */

    public int insertFeedBack(FeedBack feedBack);


    /**
     * 插入用户宿舍信息
     *
     * @param powerInfo
     * @return
     */

    public int insertPowerInfo(PowerInfo powerInfo);

    /**
     * 获取宿舍用电记录
     *
     * @param powerInfo
     * @param startPage
     * @param PageSize
     * @return
     */

    public List<PowerUseData> selectPowerUseData(PowerInfo powerInfo, int startPage, int PageSize);

    /**
     * 获取宿舍购电记录
     *
     * @param powerInfo
     * @param startPage
     * @param PageSize
     * @return
     */

    public List<PowerBuyData> selectPowerBuyData(PowerInfo powerInfo, int startPage, int PageSize);

    /**
     * 获取用户宿舍信息
     *
     * @param openid
     * @return
     */

    public PowerInfo selectPowerInfo(String openid);

    /**
     * 获取最新一笔购电信息
     *
     * @param powerInfo
     * @return
     */

    public PowerBuyData selectFirstPowerBuy(PowerInfo powerInfo);

    /**
     * 获取最新一笔用电信息
     *
     * @param powerInfo
     * @return
     */

    public PowerUseData selectFirstPowerUse(PowerInfo powerInfo);

    /**
     * 删除校园卡绑定
     *
     * @param cardInfo
     * @return
     */

    public int deleteBinding(CardInfo cardInfo);
}
