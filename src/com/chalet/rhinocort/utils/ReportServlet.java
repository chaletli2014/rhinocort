package com.chalet.rhinocort.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.chalet.rhinocort.service.HospitalService;
import com.chalet.rhinocort.service.RhinocortService;
import com.chalet.rhinocort.service.UserService;

/**
 * @author Chalet
 * @version 创建时间：2013年12月5日 上午12:06:58
 * 类说明
 */

public class ReportServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;  
    
    private ReportThread myThread1;
    private ThreadChecker threadChecker;
    private Logger logger = Logger.getLogger(ReportServlet.class);
    
    public ReportServlet(){  
    }  
  
    public void init(){
    	
        String basePath = getServletContext().getRealPath("/");
        String contextPath = CustomizedProperty.getContextProperty("host", "http://localhost:8080");
        
        logger.info("init the report servlet, basePath is " + basePath + ",contextPath is " + contextPath);
    	String str = null;
        if (str == null && myThread1 == null) {
            try{
                UserService userService = (UserService)WebApplicationContextUtils.getWebApplicationContext(getServletContext()).getBean("userService");
                HospitalService hospitalService = (HospitalService)WebApplicationContextUtils.getWebApplicationContext(getServletContext()).getBean("hospitalService");
                RhinocortService rhinocortService = (RhinocortService)WebApplicationContextUtils.getWebApplicationContext(getServletContext()).getBean("rhinocortService");
                
                myThread1 = new ReportThread(basePath,userService,rhinocortService,hospitalService,contextPath);
                myThread1.start();
                
                threadChecker = new ThreadChecker(basePath,userService,rhinocortService,hospitalService,contextPath,myThread1);
                threadChecker.start();
            }catch(Exception e){
                logger.error("fail to init the thread,",e);
            }
        }
    }  
  
    public void doGet(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)  
        throws ServletException, IOException{  
    	
    }
  
    public void destory(){  
        if (myThread1 != null && myThread1.isInterrupted()) {  
            myThread1.interrupt();  
        }  
    }
}