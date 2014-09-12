package com.chalet.rhinocort.utils;

import org.apache.log4j.Logger;

import com.chalet.rhinocort.service.HospitalService;
import com.chalet.rhinocort.service.RhinocortService;
import com.chalet.rhinocort.service.UserService;

public class ThreadChecker extends Thread {
    private ReportThread thread1 = null;
    
    private String basePath = "";
    private String contextPath = "";
    private UserService userService;
    private RhinocortService rhinocortService;
    private HospitalService hospitalService;
    
    private long timeDuration = Long.parseLong(CustomizedProperty.getContextProperty("restart_report_thread_timeout", "600000"));
    
    private Logger logger = Logger.getLogger(ReportThread.class);
    
    
    public ThreadChecker(){
        
    }
    public ThreadChecker(String basePath, UserService userService, RhinocortService rhinocortService, HospitalService hospitalService, String contextPath, ReportThread thread1){
        this.thread1 = thread1;
        this.basePath = basePath;
        this.userService = userService;
        this.rhinocortService = rhinocortService;
        this.hospitalService = hospitalService;
        this.contextPath = contextPath;
    }
    public void run() {
        while (!this.isInterrupted()) {
            try{
                logger.info(String.format("-------------------------------the status report thread is %s--------------------------------", thread1.getState().name()));
                Thread.sleep(timeDuration);
                long lastTaskTime = thread1.getTaskTime();
                long nowTime = System.currentTimeMillis();
                logger.info(String.format("------the time past %s after the latest task, lastTaskTime is %s", nowTime-lastTaskTime,lastTaskTime));
                if( !thread1.isAlive() 
                        || ((nowTime-lastTaskTime) > timeDuration && lastTaskTime != 0 ) ){
                    logger.warn(String.format("report thread is not alive, it is %s, restart it", thread1.getState().name()));
                    thread1.interrupt();
                    
                    thread1 = new ReportThread(basePath,userService,rhinocortService,hospitalService,contextPath);
                    thread1.setRestart(true);
                    thread1.start();
                }
            }catch(Exception e){
                logger.error("fail to run the threadchecker",e);
                this.interrupt();
            }
        }
        
    }
}
