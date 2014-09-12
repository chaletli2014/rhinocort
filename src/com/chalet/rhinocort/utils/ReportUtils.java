package com.chalet.rhinocort.utils;

import java.io.File;
import java.util.Date;
import org.apache.log4j.Logger;

public class ReportUtils {
    
    private static Logger logger = Logger.getLogger(ReportUtils.class);
    
    private static String LOG_MESSAGE = "the weekly report is already exists for %s,no need to generate";
    
    public static void refreshWeeklyPDFReport(String basePath, String contextPath, Date refreshDate){
    	refreshWeeklyPDFReport(basePath, contextPath, refreshDate, false);
    }
    
    public static void refreshWeeklyPDFReport(String basePath, String contextPath, Date refreshDate, boolean checkFileExists){
        try{
            String lastday = DateUtils.getLastBeginDateOfFormatter1(refreshDate);
            logger.info(String.format("start to refresh the pdf weekly report, lastday is %s", lastday));
            
            BirtReportUtils html = new BirtReportUtils();
            boolean isFirstRefresh = true;
            html.startPlatform();
            
            createWeeklyPDFReport(html, basePath, contextPath, lastday, isFirstRefresh,checkFileExists);
            
            isFirstRefresh = false;
            
            html.stopPlatform();
            logger.info("end to refresh the pdf weekly report");
        }catch(Exception e){
            logger.error("fail to refresh the pdf weekly report,",e);
        }
    }

    public static void createWeeklyPDFReport(BirtReportUtils html, String basePath, String contextPath, String lastday, boolean isFirstRefresh, boolean checkFileExists) throws Exception{
        String fileName = basePath + "weeklyReport/"+lastday+"/周报-RSM-"+lastday+".pdf";
        
        switch(LsAttributes.USER_LEVEL_RSM){
            case LsAttributes.USER_LEVEL_BM:
            case LsAttributes.USER_LEVEL_RSD:
            case LsAttributes.USER_LEVEL_RSM:
              //RSM
            	if( !new File(fileName).exists() || ( !checkFileExists && new File(fileName).exists()) ){
            		html.runRefreshReport( basePath + "reportDesigns/rhinocortRate.rptdesign","","",fileName,"pdf","","","");
            		logger.info("the weekly report for RSM is done.");
            	}else{
            		logger.info(String.format(LOG_MESSAGE, fileName));
            	}
                break;
            case LsAttributes.USER_LEVEL_DSM:
            case LsAttributes.USER_LEVEL_REP:
              
                break;
            default:
                logger.info(String.format("no need to generate the report"));
                break;
        }
    }
}
