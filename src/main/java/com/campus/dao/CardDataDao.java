package com.campus.dao;

import java.util.LinkedList;

import org.apache.ibatis.annotations.Param;

import com.campus.pojo.CardData;
import com.campus.pojo.CardDetail;


public interface CardDataDao {
    /**
     * 添加校园卡消费记录
     *
     * @param list 消费记录列表
     * @return 添加结果
     */
    public int insertCardData(@Param("list") LinkedList<CardData> list);

    /**
     * 查询一个月的消费总额
     *
     * @param account 校园卡账号
     * @return 消费总额
     */
    public float selectConsume(String account);

    /**
     * 添加校园卡消费详情
     *
     * @param cardDetail 校园卡详情
     * @return 添加结果
     */
    public int insertCardDetail(CardDetail cardDetail);

}
