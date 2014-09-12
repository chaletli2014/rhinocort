package com.chalet.rhinocort.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.chalet.rhinocort.model.WebUserInfo;
import com.chalet.rhinocort.service.UserService;
import com.chalet.rhinocort.utils.RhinocortAttributes;

@Controller
public class LoginController {

    private Logger logger = Logger.getLogger(LoginController.class);
    
    @Autowired
    @Qualifier("userService")
    private UserService userService;
    
    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request){
        ModelAndView view = new ModelAndView();
        view.setViewName("login");
        
        if( null != request.getSession(true).getAttribute(RhinocortAttributes.WEB_LOGIN_MESSAGE)){
            view.addObject(RhinocortAttributes.WEB_LOGIN_MESSAGE,(String)request.getSession().getAttribute(RhinocortAttributes.WEB_LOGIN_MESSAGE));
        }
        request.getSession().removeAttribute(RhinocortAttributes.WEB_LOGIN_MESSAGE);
        return view;
    }
    @RequestMapping("/doLogin")
    public String doLogin(HttpServletRequest request){
        try{
            String telephone = request.getParameter("web_login_userName");
            String password = request.getParameter("web_login_password");
            WebUserInfo webUser = userService.getWebUser(telephone, password);
            
            if( webUser == null ){
                request.getSession(true).setAttribute(RhinocortAttributes.WEB_LOGIN_MESSAGE,RhinocortAttributes.WEB_LOGIN_MESSAGE_NO_USER);
                return "redirect:login";
            }else{
                request.getSession(true).setAttribute(RhinocortAttributes.WEB_LOGIN_USER, webUser);
                return "redirect:showUploadData";
            }
            
        }catch(Exception e){
            logger.error("fail to login",e);
            request.getSession(true).setAttribute(RhinocortAttributes.WEB_LOGIN_MESSAGE,e.getMessage());
            return "redirect:login";
        }
    }
}
