package com.campus.util;

import com.campus.service.CrawlCardService;

public class CrawlThread extends Thread {
    String cookie;
    String inputStartDate;
    String inputEndDate;
    CrawlCardService crawlCardService;
    public CrawlThread(String cookie,String inputStartDate,String inputEndDate,CrawlCardService crawlCardService)
    {
        this.cookie=cookie;
        this.crawlCardService=crawlCardService;
        this.inputStartDate=inputStartDate;
        this.inputEndDate=inputEndDate;

    }
    public void run()
    {
        crawlCardService.obtainCampus(cookie, inputStartDate, inputEndDate);
    }

}
