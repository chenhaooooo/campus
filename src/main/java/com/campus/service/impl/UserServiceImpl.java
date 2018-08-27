package com.campus.service.impl;

import com.campus.dao.PowerDataDao;
import com.campus.dao.UserDao;
import com.campus.pojo.*;
import com.campus.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "userService")
public class UserServiceImpl implements UserService {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    UserDao userDao;
    @Autowired
    PowerDataDao powerDataDao;

    /**
     * 获取用户信息
     *
     * @param openid
     * @return
     */
    @Override
    public UserInfo selectUserInfo(String openid) {
        return userDao.selectUserInfo(openid);
    }

    /**
     * 插入用户信息
     *
     * @param userInfo
     * @return
     */
    @Override
    public int insertUserInfo(UserInfo userInfo) {

        return userDao.insertUserInfo(userInfo);
    }

    /**
     * 更新用户信息
     *
     * @param userInfo
     * @return
     */
    @Override
    public int updateUserInfo(UserInfo userInfo) {

        return userDao.updateUserInfo(userInfo);
    }

    /**
     * 根据openid获取用户校园卡信息
     *
     * @param openid
     * @return
     */
    @Override
    public CardInfo selectOpenidCardInfo(String openid) {

        return userDao.selectOpenidCardInfo(openid);
    }

    /**
     * 插入校园卡信息
     *
     * @param cardInfo
     * @return
     */
    @Override
    public int insertCardInfo(CardInfo cardInfo) {

        return userDao.insertCardInfo(cardInfo);
    }

    /**
     * 获取校园卡详细信息
     *
     * @param account
     * @return
     */
    @Override
    public CardDetail selectCardDetail(String account) {

        return userDao.selectCardDetail(account);
    }

    /**
     * 根据账号account获取用户校园卡信息
     *
     * @param account
     * @return
     */
    @Override
    public CardInfo selectAccountCardInfo(String account) {

        return userDao.selectAccountCardInfo(account);
    }

    /**
     * 获取校园卡前十笔消费记录
     *
     * @param account
     * @return
     */
    @Override
    public List<CardData> selectDetailData(String account) {

        return userDao.selectCardDetailData(account);
    }

    /**
     * 获取校园卡消费记录，按页查询
     *
     * @param account
     * @param startPage
     * @param PageSize
     * @return
     */
    @Override
    public List<CardData> selectCardData(String account, int startPage, int PageSize) {
        PageHelper.startPage(startPage, PageSize);
        List<CardData> cardDatas = new ArrayList<>();
        cardDatas = userDao.selectCardData(account);
        PageInfo pi = new PageInfo(cardDatas);
        return pi.getList();
    }

    /**
     * 获取校园卡最近四个月的月消费情况
     *
     * @param account
     * @return
     */
    @Override
    public List selectCardDataMonth(String account) {

        return userDao.selectCardDataMonth(account);
    }

    /**
     * 获取当月校园卡消费地点
     *
     * @param account
     * @return
     */
    @Override
    public List selectCardDataPlace(String account) {

        return userDao.selectCardDataPlace(account);
    }

    /**
     * 更新校园卡账号密码信息
     *
     * @param cardInfo
     * @return
     */
    @Override
    public int updateCardInfo(CardInfo cardInfo) {

        return userDao.updateCardInfo(cardInfo);
    }

    /**
     * 获取最新的一笔消费记录
     *
     * @param account
     * @return
     */
    @Override
    public CardData selectCardDataFirst(String account) {
        return userDao.selectCardDataFirst(account);
    }

    /**
     * 插入校园卡丢失信息
     *
     * @param pickLost
     * @return
     */
    @Override
    public int insertPickLost(PickLost pickLost) {

        return userDao.insertPickLost(pickLost);
    }

    /**
     * 获取丢失信息
     *
     * @param attribute
     * @param startPage
     * @param PageSize
     * @return
     */
    @Override
    public List<PickLost> selectPickLost(String attribute, int startPage, int PageSize) {
        PageHelper.startPage(startPage, PageSize);
        List<PickLost> pickLosts = new ArrayList<>();
        pickLosts = userDao.selectPickLost(attribute);
        PageInfo pi = new PageInfo(pickLosts);

        return pi.getList();
    }

    /**
     * 更新校园卡丢失信息
     *
     * @param pickLost
     * @return
     */
    @Override
    public int updatePickLost(PickLost pickLost) {

        return userDao.updatePickLost(pickLost);
    }

    /**
     * 获取发布校园卡丢失信息
     *
     * @param openid
     * @return
     */
    @Override
    public List<PickLost> selectReportAll(String openid) {

        return userDao.selectReportAll(openid);
    }

    /**
     * 获取用户的反馈信息
     *
     * @param account
     * @return
     */
    @Override
    public List<FeedBack> selectFeedBack(String account) {
        return userDao.selectFeedBack(account);
    }

    /**
     * 插入反馈信息
     *
     * @param feedBack
     * @return
     */
    @Override
    public int insertFeedBack(FeedBack feedBack) {

        return userDao.insertFeedBack(feedBack);
    }


    /**
     * 插入用户宿舍信息
     *
     * @param powerInfo
     * @return
     */
    @Override
    public int insertPowerInfo(PowerInfo powerInfo) {

        return userDao.insertPowerInfo(powerInfo);
    }

    /**
     * 获取宿舍用电记录
     *
     * @param powerInfo
     * @param startPage
     * @param PageSize
     * @return
     */
    @Override
    public List<PowerUseData> selectPowerUseData(PowerInfo powerInfo, int startPage, int PageSize) {
        PageHelper.startPage(startPage, PageSize);
        List<PowerUseData> powerUseDatas = new ArrayList<>();
        powerUseDatas = powerDataDao.selectPowerUseData(powerInfo);
        PageInfo pi = new PageInfo(powerUseDatas);

        return pi.getList();
    }

    /**
     * 获取宿舍购电记录
     *
     * @param powerInfo
     * @param startPage
     * @param PageSize
     * @return
     */
    @Override
    public List<PowerBuyData> selectPowerBuyData(PowerInfo powerInfo, int startPage, int PageSize) {
        PageHelper.startPage(startPage, PageSize);
        List<PowerBuyData> powerBuyDatas = new ArrayList<>();
        powerBuyDatas = powerDataDao.selectPowerBuyData(powerInfo);
        PageInfo pi = new PageInfo(powerBuyDatas);
        return pi.getList();
    }

    /**
     * 获取用户宿舍信息
     *
     * @param openid
     * @return
     */
    @Override
    public PowerInfo selectPowerInfo(String openid) {

        return userDao.selectPowerInfo(openid);
    }

    /**
     * 获取最新一笔购电信息
     *
     * @param powerInfo
     * @return
     */
    @Override
    public PowerBuyData selectFirstPowerBuy(PowerInfo powerInfo) {

        return powerDataDao.selectFirstPowerBuy(powerInfo);
    }

    /**
     * 获取最新一笔用电信息
     *
     * @param powerInfo
     * @return
     */
    @Override
    public PowerUseData selectFirstPowerUse(PowerInfo powerInfo) {
        return powerDataDao.selectFirstPowerUse(powerInfo);
    }

    /**
     * 删除校园卡绑定
     *
     * @param cardInfo
     * @return
     */
    @Override
    public int deleteBinding(CardInfo cardInfo) {

        return userDao.deleteCardData(cardInfo.getAccount()) + userDao.deleteCardDetail(cardInfo.getAccount())
                + userDao.deleteCardInfo(cardInfo.getAccount());
    }

    @Override
    public List<SouthPowerUseData> selectSouthPowerUseData(SouthPowerInfo southPowerInfo, int startPage, int PageSize) {
        PageHelper.startPage(startPage, PageSize);
        List<SouthPowerUseData> southPowerUseDatas = new ArrayList<>();
        southPowerUseDatas = powerDataDao.selectSouthPowerUseData(southPowerInfo);
        PageInfo pi = new PageInfo(southPowerUseDatas);
        return pi.getList();
    }

    @Override
    public List<SouthPowerBuyData> selectSouthPowerBuyData(SouthPowerInfo southPowerInfo, int startPage, int PageSize) {
        PageHelper.startPage(startPage, PageSize);
        List<SouthPowerBuyData> southPowerBuyDatas = new ArrayList<>();
        southPowerBuyDatas = powerDataDao.selectSouthPowerBuyData(southPowerInfo);
        PageInfo pi = new PageInfo(southPowerBuyDatas);
        return pi.getList();
    }

    @Override
    public SouthPowerInfo selectSouthPowerInfo(String openid) {
        return userDao.selectSouthPowerInfo(openid);
    }

    @Override
    public int deletePowerInfo(String openid) {
        return userDao.deletePowerInfo(openid);
    }

    @Override
    public int deleteSouthPowerInfo(String openid) {
        return userDao.deleteSouthPowerInfo(openid);
    }

    @Override
    public SouthPowerBuyData selectFirstSouthPowerBuy(SouthPowerInfo southPowerInfo) {
        return powerDataDao.selectFirstSouthPowerBuy(southPowerInfo);
    }

    @Override
    public SouthPowerUseData selectFirstSouthPowerUse(SouthPowerInfo SouthPowerInfo) {
        return powerDataDao.selectFirstSouthPowerUse(SouthPowerInfo);
    }

}
