package com.chalet.rhinocort.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.chalet.rhinocort.model.UserInfo;
import com.chalet.rhinocort.service.UserService;
import com.chalet.rhinocort.utils.RhinocortAttributes;

/**
 * @author Chalet
 * @version 创建时间：2014年1月2日 下午9:19:21
 * 类说明
 */

@Controller
public class BaseController {
	
	Logger logger = Logger.getLogger(BaseController.class);
    
    @Autowired
    @Qualifier("userService")
    private UserService userService;

    public String verifyCurrentUser(HttpServletRequest request, ModelAndView view){
    	String currentUserTelephone = request.getParameter(RhinocortAttributes.CURRENT_OPERATOR);
    	
        try{
        	if( null == currentUserTelephone || "".equalsIgnoreCase(currentUserTelephone)  ){
        		currentUserTelephone = (String)request.getSession(true).getAttribute(RhinocortAttributes.CURRENT_OPERATOR);
        	}else{
        		request.getSession(true).setAttribute(RhinocortAttributes.CURRENT_OPERATOR, currentUserTelephone);
        	}
        	
        	if( null == currentUserTelephone || "".equalsIgnoreCase(currentUserTelephone)  ){
        		logger.warn("fail to get the user tel from APP side.");
        		view.addObject(RhinocortAttributes.JSP_VERIFY_MESSAGE, RhinocortAttributes.NO_USER_FOUND);
        	}else{
        		UserInfo user = (UserInfo)request.getSession(true).getAttribute(RhinocortAttributes.CURRENT_OPERATOR_OBJECT);
        		if( user == null || !user.getTelephone().equalsIgnoreCase(currentUserTelephone) ){
        			user = userService.getUserInfoByTel(currentUserTelephone);
        			request.getSession(true).setAttribute(RhinocortAttributes.CURRENT_OPERATOR_OBJECT, user);
        		}

        		if( user == null ){
        			logger.warn("fail to get the user info.");
        			view.addObject(RhinocortAttributes.JSP_VERIFY_MESSAGE, RhinocortAttributes.NO_USER_FOUND);
        		}
        	}
        }catch(EmptyResultDataAccessException er){
        	logger.info("there is no user found whose telephone is " + currentUserTelephone);
        	view.addObject(RhinocortAttributes.JSP_VERIFY_MESSAGE, RhinocortAttributes.NO_USER_FOUND);
        }catch(Exception e){
        	logger.error("fail to verify the current user,",e);
        	view.addObject(RhinocortAttributes.JSP_VERIFY_MESSAGE, RhinocortAttributes.NO_USER_FOUND);
        }
        
        return currentUserTelephone;
    }
    
    public void checkAndCreateFileFolder(String filePath){
        File file = new File(filePath);
        if( !file.exists() ){
            logger.info("filePath " + filePath + " is not found, create it automaticlly");
            file.mkdirs();
        }
    }
}
