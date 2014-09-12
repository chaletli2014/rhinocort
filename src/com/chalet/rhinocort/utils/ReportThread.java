package com.chalet.rhinocort.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.chalet.rhinocort.model.Hospital;
import com.chalet.rhinocort.model.UserInfo;
import com.chalet.rhinocort.service.HospitalService;
import com.chalet.rhinocort.service.RhinocortService;
import com.chalet.rhinocort.service.UserService;

public class ReportThread extends Thread {
    private String basePath = "";
    private String contextPath = "";
    private UserService userService;
    private RhinocortService rhinocortService;
    private HospitalService hospitalService;
    private boolean isRestart = false;
    private long taskTime = 0;
    
    private Logger logger = Logger.getLogger(ReportThread.class);
    
    
    public ReportThread(){
        
    }
    public ReportThread(String basePath, UserService userService, RhinocortService rhinocortService, HospitalService hospitalService, String contextPath){
        this.basePath = basePath;
        this.userService = userService;
        this.rhinocortService = rhinocortService;
        this.hospitalService = hospitalService;
        this.contextPath = contextPath;
    }
    public void run() {  
        
        boolean emailIsSend = false;
        
        while (!this.isInterrupted()) {
            //check report time
            Date now = new Date();
            //daily report start
            try {
                Date beginDate = DateUtils.getTheBeginDateOfRecordDate(now);
                //0-Sunday
                int dayInWeek = now.getDay();
                int hour = now.getHours();
                int dayInMonth = now.getDate();
                logger.info("current hour is " + hour);
                if( hour == Integer.parseInt(CustomizedProperty.getContextProperty("report_generate_time", "11"))
                        || isRestart ){
                    logger.info("console : now is " + hour + ", begin to generate report");
                    
                    checkAndCreateFileFolder(basePath + "report/"+beginDate);
                    
                    BirtReportUtils html = new BirtReportUtils();
                    int email_send_flag = Integer.parseInt(CustomizedProperty.getContextProperty("email_send_flag", "0"));
                    
                    
                    if( dayInWeek == Integer.parseInt(CustomizedProperty.getContextProperty("weekly_report_day", "1")) ){
                        logger.info("today is Monday, start to generate the html weekly report");
                        
                        this.taskTime = System.currentTimeMillis();
                        html.startHtmlPlatform();
                        ReportUtils.refreshWeeklyPDFReport(basePath, contextPath, beginDate);
                        this.taskTime = System.currentTimeMillis();
                        html.stopPlatform();
                        logger.info("end to generate the weekly report");
                    }
                    
                    this.taskTime = 0;
                    logger.info("Finished");
                }
                
                Thread.sleep(60000*30);
            } catch (Exception e) {  
                logger.error("fail to send the report,",e);
                this.interrupt();
            }  finally{
                isRestart = false;
            }
        }
    }
    
    private void checkAndCreateFileFolder(String filePath){
        File file = new File(filePath);
        if( !file.exists() ){
            logger.info("filePath " + filePath + " is not found, create it automaticlly");
            file.mkdirs();
        }
    }
    public boolean isRestart() {
        return isRestart;
    }
    public void setRestart(boolean isRestart) {
        this.isRestart = isRestart;
    }
    public long getTaskTime() {
        return taskTime;
    }
    public void setTaskTime(long taskTime) {
        this.taskTime = taskTime;
    }
}
