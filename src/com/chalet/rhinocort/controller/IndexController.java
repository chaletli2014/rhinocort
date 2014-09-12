package com.chalet.rhinocort.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chalet.rhinocort.model.Hospital;
import com.chalet.rhinocort.model.RhinocortData;
import com.chalet.rhinocort.model.RhinocortWeeklyData;
import com.chalet.rhinocort.model.UserInfo;
import com.chalet.rhinocort.service.HospitalService;
import com.chalet.rhinocort.service.RhinocortService;
import com.chalet.rhinocort.service.UserService;
import com.chalet.rhinocort.utils.DateUtils;
import com.chalet.rhinocort.utils.RhinocortAttributes;
import com.chalet.rhinocort.utils.StringUtils;

@Controller
public class IndexController extends BaseController{
    
    private Logger logger = Logger.getLogger(IndexController.class);
    
    @Autowired
    @Qualifier("userService")
    private UserService userService;
    
    @Autowired
    @Qualifier("hospitalService")
    private HospitalService hospitalService;
    
    @Autowired
    @Qualifier("rhinocortService")
    private RhinocortService rhinocortService;
    
    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request){
        ModelAndView view = new ModelAndView();
        verifyCurrentUser(request,view);
    	view.setViewName("index");
        return view;
    }
    
    @RequestMapping("/rhinocort")
    public ModelAndView rhinocort(HttpServletRequest request){
        ModelAndView view = new ModelAndView();
        String operator_telephone = verifyCurrentUser(request,view);
        
        UserInfo currentUser = (UserInfo)request.getSession().getAttribute(RhinocortAttributes.CURRENT_OPERATOR_OBJECT);
        if( null == operator_telephone || "".equalsIgnoreCase(operator_telephone) || null == currentUser || 
        		! ( RhinocortAttributes.USER_LEVEL_REP.equalsIgnoreCase(currentUser.getLevel()) 
        				|| RhinocortAttributes.USER_LEVEL_DSM.equalsIgnoreCase(currentUser.getLevel()))){
        	view.addObject(RhinocortAttributes.JSP_VERIFY_MESSAGE, RhinocortAttributes.RETURNED_MESSAGE_3);
        	view.setViewName("index");
        	return view;
        }
        
        List<Hospital> hospitals = new ArrayList<Hospital>();
        try{
            hospitals = hospitalService.getHospitalsByUserTel(operator_telephone);
            view.addObject("hospitals", hospitals);
            
            String selectedHospitalCode = request.getParameter("selectedHospital");
            RhinocortData existedData = new RhinocortData();
            if( (null != selectedHospitalCode && !"".equalsIgnoreCase(selectedHospitalCode)) ){
            	
            	logger.info(String.format("get the data of the selected hospital - %s", selectedHospitalCode) );
                existedData = rhinocortService.getRhinocortDataByHospitalCode(selectedHospitalCode);
                if( existedData != null ){
			    	logger.info(String.format("init ped existedData is %s", existedData.getHospitalCode()));
			    }
                view.addObject("selectedHospital", selectedHospitalCode);
            }
            view.addObject("existedData", existedData);
            
            String message = "";
            if( null != request.getSession().getAttribute(RhinocortAttributes.COLLECT_PEDIATRICS_MESSAGE) ){
                message = (String)request.getSession().getAttribute(RhinocortAttributes.COLLECT_PEDIATRICS_MESSAGE);
            }
            view.addObject(RhinocortAttributes.JSP_VERIFY_MESSAGE, message);
            request.getSession().removeAttribute(RhinocortAttributes.COLLECT_PEDIATRICS_MESSAGE);
        }catch(Exception e){
            logger.info("fail to init the rhinocort,",e);
        }
        view.setViewName("rhinocortform");
        return view;
    }
    
    @RequestMapping("/doCollectData")
    public String doCollectData(HttpServletRequest request){
    	String operator_telephone = (String)request.getSession(true).getAttribute(RhinocortAttributes.CURRENT_OPERATOR);
    	logger.info("collectData, user = "+operator_telephone);
        try{
        	UserInfo currentUser = (UserInfo)request.getSession().getAttribute(RhinocortAttributes.CURRENT_OPERATOR_OBJECT);
            if( null == operator_telephone || "".equalsIgnoreCase(operator_telephone) || null == currentUser || 
            		! ( RhinocortAttributes.USER_LEVEL_REP.equalsIgnoreCase(currentUser.getLevel()) 
            				|| RhinocortAttributes.USER_LEVEL_DSM.equalsIgnoreCase(currentUser.getLevel()))){
            	return "redirect:index";
            }
            
            String dataId = request.getParameter("dataId");
            String hospitalCode = request.getParameter("hospital");
            logger.info(String.format("doing the data persistence, dataId is %s, hospital is %s", dataId,hospitalCode));
            
            RhinocortData existedData = rhinocortService.getRhinocortDataByHospitalCode(hospitalCode);
            if( null != existedData ){
            	logger.info(String.format("check the ped data again when user %s collecting, the data id is %s", operator_telephone, existedData.getDataId()));
            }
            if( ( null == dataId || "".equalsIgnoreCase(dataId) ) 
                    && null != existedData){
                dataId = String.valueOf(existedData.getDataId());
                logger.info(String.format("the res data is found which id is %s", dataId));
            }
            
            //new data
            if( null == dataId || "".equalsIgnoreCase(dataId) ){
                RhinocortData rhinocortData = new RhinocortData();
                populateRhinocortData(request,rhinocortData);
                
                Hospital hospital = hospitalService.getHospitalByCode(hospitalCode);
                
                logger.info("insert the data of rhinocort");
                rhinocortService.insert(rhinocortData, currentUser, hospital);
            }else{
                //update the current data
                RhinocortData dbRhinocortData = rhinocortService.getRhinocortDataById(Integer.parseInt(dataId));
                if( null == dbRhinocortData ){
                    request.getSession().setAttribute(RhinocortAttributes.COLLECT_RESPIROLOGY_MESSAGE, RhinocortAttributes.RETURNED_MESSAGE_1);
                }else{
                    populateRhinocortData(request,dbRhinocortData);
                    
                    logger.info("update the data of rhinocort");
                    rhinocortService.update(dbRhinocortData, currentUser);
                }
            }
            
            request.getSession().setAttribute(RhinocortAttributes.COLLECT_PEDIATRICS_MESSAGE, RhinocortAttributes.RETURNED_MESSAGE_0);
        }catch(Exception e){
            logger.error("fail to collect rhinocort "+e.getMessage(),e);
            request.getSession().setAttribute(RhinocortAttributes.COLLECT_PEDIATRICS_MESSAGE, RhinocortAttributes.RETURNED_MESSAGE_1);
        }
        
        return "redirect:rhinocort";
    }
    
    @RequestMapping("/weeklyreport")
    public ModelAndView weeklyReport(HttpServletRequest request){
        logger.info("weeklyReport");
        ModelAndView view = new ModelAndView();
        verifyCurrentUser(request,view);
        List<RhinocortWeeklyData> weeklyData = new ArrayList<RhinocortWeeklyData>();
        
        try{
        	weeklyData = rhinocortService.getWeeklyReportData();
        }catch(Exception e){
        	logger.error("fail to getWeeklyReportData,",e);
        }
        
        StringBuffer title = new StringBuffer();
        Date today = new Date();
        title.append("全国【").append(DateUtils.getLastBeginDateOfFormatter1(today)).append("到").append(DateUtils.getLastEndDateOfFormatter1(today)).append("】").append("周报数据表");
        
        view.addObject("title", title);
        view.addObject("weeklyData", weeklyData);
    	view.setViewName("weeklyReport");
        return view;
    }
    
    @RequestMapping(value = "/lowerWeeklyReport", method = RequestMethod.GET)  
    @ResponseBody
    public Map<String, Object> lowerWeeklyReport(HttpServletRequest request){
    	logger.info("get lower weeklyReport - " + request.getParameter("lowername"));
    	
    	String lowername = request.getParameter("lowername");
    	
    	String level = lowername.split("-")[0];
    	String name = lowername.split("-")[1];
    	
    	List<RhinocortWeeklyData> weeklyData = new ArrayList<RhinocortWeeklyData>();

    	try{
    		weeklyData = rhinocortService.getLowerWeeklyReportData(level, name);
    	}catch(Exception e){
    		logger.error("fail to getWeeklyReportData,",e);
    	}
    	StringBuffer subTitle = new StringBuffer("所属【");
    	if("RSD".equalsIgnoreCase(level)){
    	    subTitle.append("全国】").append(name);
    	}else if( "RSM".equalsIgnoreCase(level) ){
    	    subTitle.append(name).append("】").append("销售");
    	}
//    	Date today = new Date();
//    	.append("【").append(DateUtils.getLastBeginDateOfFormatter1(today)).append("到").append(DateUtils.getLastEndDateOfFormatter1(today)).append("】")
    	subTitle.append("周报数据表");
    	
        Map<String, Object> modelMap = new HashMap<String, Object>(3);  
        modelMap.put("total", weeklyData.size());  
        modelMap.put("subTitle", subTitle);
        modelMap.put("data", weeklyData);
        modelMap.put("success", "true");
        return modelMap;
    }
    
    private void populateRhinocortData(HttpServletRequest request,RhinocortData rhinocortData){
        String hospitalCode = request.getParameter("hospital");        
        int num1 = StringUtils.getIntegerFromString(request.getParameter("num1"));
        int num2 = StringUtils.getIntegerFromString(request.getParameter("num2"));
        int num3 = StringUtils.getIntegerFromString(request.getParameter("num3"));
        int num4 = StringUtils.getIntegerFromString(request.getParameter("num4"));
        int num5 = StringUtils.getIntegerFromString(request.getParameter("num5"));
        int num6 = StringUtils.getIntegerFromString(request.getParameter("num6"));
        int num7 = StringUtils.getIntegerFromString(request.getParameter("num7"));
        
        rhinocortData.setHospitalCode(hospitalCode);
        rhinocortData.setNum1(num1);
        rhinocortData.setNum2(num2);
        rhinocortData.setNum3(num3);
        rhinocortData.setNum4(num4);
        rhinocortData.setNum5(num5);
        rhinocortData.setNum6(num6);
        rhinocortData.setNum7(num7);
    }
}