package com.campus.etdao;

public interface CrawlPowerEtDao {

    /**
     * 获取宿舍用电信息
     *
     * @param building   宿舍楼
     * @param roomName   宿舍号
     * @param buildingId 宿舍楼id
     */
    public void obtainPowerUse(String building, String roomName, String buildingId);

    /**
     * 获取宿舍购电信息
     *
     * @param building   宿舍楼
     * @param roomName   宿舍号
     * @param buildingId 宿舍楼id
     */
    public void obtainPowerBuy(String building, String roomName, String buildingId);
}
