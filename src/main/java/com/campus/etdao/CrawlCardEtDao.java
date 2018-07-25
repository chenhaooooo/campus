package com.campus.etdao;

public interface CrawlCardEtDao {

    /**
     * 登录校园卡网站
     *
     * @param account  校园卡账号
     * @param password 校园卡密码（六位）
     * @return 登录结果，返回cookie
     */
    public String loginCampus(String account, String password);

    /**
     * 获取某个时间段内的校园卡消费信息
     *
     * @param cookie         校园卡网站登录后的cookie
     * @param inputStartDate 开始日期，例如：20180723
     * @param inputEndDate   结束日期，例如：20180723
     */
    public void obtainCampus(String cookie, String inputStartDate, String inputEndDate);

    /**
     * 获取当天的校园卡消费记录（因为历史记录与当天的记录链接不同）
     *
     * @param cookie 校园卡网站登录后的cookie
     */
    public void obtainCampus(String cookie);

    /**
     * 获取校园卡状态
     *
     * @param account 校园卡账号
     * @param cookie  校园卡网站登录后的cookie
     */
    public void obtainState(String account, String cookie);

    /**
     * 挂失校园卡
     *
     * @param account  校园卡账号
     * @param password 校园卡密码
     * @param cookie   校园卡网站登录后的cookie
     * @return 挂失结果
     */
    public Boolean lostCampus(String account, String password, String cookie);

    /**
     * 修改校园卡密码
     *
     * @param account     校园卡账号
     * @param password    当前校园卡密码
     * @param newPassword 校园卡更改密码
     * @param cookie      校园卡网站登录后的cookie
     * @return 更改结果
     */
    public Boolean alterPassword(String account, String password, String newPassword, String cookie);

}
