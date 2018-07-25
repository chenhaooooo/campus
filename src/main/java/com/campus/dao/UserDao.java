package com.campus.dao;

import java.util.List;

import com.campus.pojo.CardData;
import com.campus.pojo.CardDetail;
import com.campus.pojo.CardInfo;
import com.campus.pojo.FeedBack;
import com.campus.pojo.PickLost;
import com.campus.pojo.PowerInfo;
import com.campus.pojo.UserInfo;


public interface UserDao {
    /**
     * 查询微信用户信息
     *
     * @param openid 小程序唯一标识
     * @return UserInfo 用户信息
     */
    public UserInfo selectUserInfo(String openid);

    /**
     * 添加用户信息
     *
     * @param userInfo 用户信息
     * @return 添加结果
     */
    public int insertUserInfo(UserInfo userInfo);

    /**
     * 更新用户信息
     *
     * @param userInfo 用户信息
     * @return 更新结果
     */
    public int updateUserInfo(UserInfo userInfo);

    /**
     * 根据openid查询用户的校园卡信息
     *
     * @param openid 小程序唯一标识
     * @return 返回校园卡信息
     */
    public CardInfo selectOpenidCardInfo(String openid);

    /**
     * 根据账号查询校园卡信息
     *
     * @param account 校园卡账号
     * @return 返回校园卡信息
     */
    public CardInfo selectAccountCardInfo(String account);

    /**
     * 添加校园卡信息
     *
     * @param cardInfo 校园卡信息
     * @return 添加结果
     */
    public int insertCardInfo(CardInfo cardInfo);

    /**
     * 查询校园卡消费详情
     *
     * @param account 校园卡账号
     * @return 返回校园卡详情
     */
    public CardDetail selectCardDetail(String account);

    /**
     * 查询最近十笔校园卡消费详情记录
     *
     * @param account 校园卡账号
     * @return 返回校园卡前十笔消费记录
     */
    public List<CardData> selectCardDetailData(String account);

    /**
     * 查询校园卡消费详情记录
     *
     * @param account 校园卡账号
     * @return 校园卡详情记录
     */
    public List<CardData> selectCardData(String account);

    /**
     * 查询校园卡最近四个月的各个月消费总额
     *
     * @param account 校园卡账号
     * @return 校园卡最近四个月的各个月消费总额
     */
    public List selectCardDataMonth(String account);

    /**
     * 校园卡当月消费各个地点的总额
     *
     * @param account 校园卡账号
     * @return 返回校园卡当月消费各个地点的总额
     */
    public List selectCardDataPlace(String account);

    /**
     * 更新校园卡信息
     *
     * @param cardInfo 校园卡信息
     * @return 返回更新结果
     */
    public int updateCardInfo(CardInfo cardInfo);

    /**
     * 查询最近一笔消费记录
     *
     * @param account 校园卡记录
     * @return 校园卡消费记录
     */
    public CardData selectCardDataFirst(String account);

    /**
     * 添加挂失信息
     *
     * @param pickLost 挂失信息
     * @return 添加结果
     */
    public int insertPickLost(PickLost pickLost);

    /**
     * 查询拾/失信息
     *
     * @param attribute 拾/失类型
     * @return 返回拾/失信息
     */
    public List<PickLost> selectPickLost(String attribute);

    /**
     * 更新拾/失信息
     *
     * @param pickLost 拾/失信息
     * @return 更新结果
     */
    public int updatePickLost(PickLost pickLost);

    /**
     * 查询拾/失发布信息
     *
     * @param openid 小程序唯一标识
     * @return 拾/失发布信息
     */
    public List<PickLost> selectReportAll(String openid);

    /**
     * 删除校园卡详情信息
     *
     * @param account 校园卡账号
     * @return 删除结果
     */
    public int deleteCardDetail(String account);

    /**
     * 删除校园卡信息
     *
     * @param account 校园卡账号
     * @return 删除结果
     */
    public int deleteCardInfo(String account);

    /**
     * 删除校园卡消费记录
     *
     * @param account 校园卡账号
     * @return 删除结果
     */
    public int deleteCardData(String account);

    /**
     * 添加反馈信息
     *
     * @param feedBack 反馈信息
     * @return 添加结果
     */
    public int insertFeedBack(FeedBack feedBack);

    /**
     * 查询反馈信息
     *
     * @param openid 小程序唯一标识
     * @return 返回反馈信息
     */
    public List<FeedBack> selectFeedBack(String openid);

    /**
     * 添加用户宿舍信息
     *
     * @param powerInfo 宿舍信息
     * @return 返回添加结果
     */
    public int insertPowerInfo(PowerInfo powerInfo);

    /**
     * 查询用电信息
     *
     * @param openid 小程序唯一标识
     * @return 返回用电信息
     */
    public PowerInfo selectPowerInfo(String openid);


}
