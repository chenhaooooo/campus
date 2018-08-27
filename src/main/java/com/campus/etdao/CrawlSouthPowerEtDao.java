package com.campus.etdao;

import com.campus.pojo.SouthPowerDetail;
import com.campus.pojo.SouthPowerInfo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public interface CrawlSouthPowerEtDao {
    public HashMap login(SouthPowerInfo southPowerInfo);
    public SouthPowerDetail obtainSouthPowerDetail(HashMap headers,SouthPowerInfo southPowerInfo);
    public LinkedList obtainSouthPowerUseNowData(HashMap headers, SouthPowerDetail southPowerDetail);
    public LinkedList obtainSouthPowerUseHistoryData(HashMap headers,SouthPowerInfo southPowerInfo, String beforeTime);
    public LinkedList obtainSouthPowerBuyData(HashMap headers, SouthPowerInfo southPowerInfo);
}
