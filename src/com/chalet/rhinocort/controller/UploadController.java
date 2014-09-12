package com.chalet.rhinocort.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.chalet.rhinocort.model.WebUserInfo;
import com.chalet.rhinocort.service.HospitalService;
import com.chalet.rhinocort.service.RhinocortService;
import com.chalet.rhinocort.service.UserService;
import com.chalet.rhinocort.utils.ExcelUtils;
import com.chalet.rhinocort.utils.RhinocortAttributes;

/**
 * @author Chalet
 * @version 创建时间：2013年12月18日 下午10:24:54
 * 类说明
 */

@Controller
public class UploadController {

	private Logger logger = Logger.getLogger(UploadController.class);
	
    @Autowired
    @Qualifier("userService")
    private UserService userService;
    
    @Autowired
    @Qualifier("hospitalService")
    private HospitalService hospitalService;
    
    @Autowired
    @Qualifier("rhinocortService")
    private RhinocortService rhinocortService;

    @RequestMapping("/showUploadData")
    public ModelAndView showUploadData(HttpServletRequest request){
        ModelAndView view = new ModelAndView();
        
        if( null != request.getSession(true).getAttribute(RhinocortAttributes.WEB_LOGIN_USER)){
        	view.addObject(RhinocortAttributes.WEB_LOGIN_USER, (WebUserInfo)request.getSession(true).getAttribute(RhinocortAttributes.WEB_LOGIN_USER));
        }
        view.setViewName("uploadData");
        
        String message = "";
        if( null != request.getSession().getAttribute(RhinocortAttributes.UPLOAD_FILE_MESSAGE) ){
            message = (String)request.getSession().getAttribute(RhinocortAttributes.UPLOAD_FILE_MESSAGE);
            view.addObject(RhinocortAttributes.JSP_VERIFY_MESSAGE, message);
            request.getSession().removeAttribute(RhinocortAttributes.UPLOAD_FILE_MESSAGE);
        }
        
        String messageareaid = "";
        if( null != request.getSession().getAttribute(RhinocortAttributes.MESSAGE_AREA_ID) ){
        	messageareaid = (String)request.getSession().getAttribute(RhinocortAttributes.MESSAGE_AREA_ID);
        	view.addObject(RhinocortAttributes.MESSAGE_AREA_ID, messageareaid);
        	request.getSession().removeAttribute(RhinocortAttributes.MESSAGE_AREA_ID);
        }
        
        //upload daily res data message
        int validDataMessage = 0;
        if( null != request.getSession().getAttribute(RhinocortAttributes.VALID_RES_DATA_NUM) ){
        	validDataMessage = (Integer)request.getSession().getAttribute(RhinocortAttributes.VALID_RES_DATA_NUM);
        	view.addObject(RhinocortAttributes.VALID_RES_DATA_NUM, validDataMessage);
        	request.getSession().removeAttribute(RhinocortAttributes.VALID_RES_DATA_NUM);
        }
        
        if( null != request.getSession().getAttribute("dataFile") ){
        	view.addObject("dataFile",(String)request.getSession().getAttribute("dataFile"));
        	request.getSession().removeAttribute("dataFile");
        }
        
        if( null != request.getSession().getAttribute("weeklyDataFile") ){
        	view.addObject("weeklyDataFile",(String)request.getSession().getAttribute("weeklyDataFile"));
        	request.getSession().removeAttribute("weeklyDataFile");
        }
        return view;
    }
    
    @RequestMapping("/doUploadAllData")
    public String doUploadAllData(HttpServletRequest request){
        try{
            List<String> regionHeaders = new ArrayList<String>();
            regionHeaders.add("DIST NAME");//RSM
            regionHeaders.add("BR Name");//RSD
            
            List<String> hospitalHeaders = new ArrayList<String>();
            hospitalHeaders.add("Hospital Code");//Sub Institution
            hospitalHeaders.add("Name");//
            hospitalHeaders.add("Province");
            hospitalHeaders.add("City");
            hospitalHeaders.add("Hospital Level");
            hospitalHeaders.add("是否为KPI医院（是=1，否=0）");
            
            List<String> repHeaders = new ArrayList<String>();
            repHeaders.add("Rep Code");
            repHeaders.add("Rep Name");
            repHeaders.add("Rep Tel");
            repHeaders.add("是否为专职销售（是=1,否=0）");
            repHeaders.add("Rep Email");
            
            List<String> dsmHeaders = new ArrayList<String>();
            dsmHeaders.add("DSM Code");
            dsmHeaders.add("DSM Name");
            dsmHeaders.add("DSM Tel");
            dsmHeaders.add("DSM Email");
            
            List<String> rsmHeaders = new ArrayList<String>();
            rsmHeaders.add("RSM Code");
            rsmHeaders.add("RSM Name");
            rsmHeaders.add("RSM Tel");
            rsmHeaders.add("RSM Email");
            
            long begin = System.currentTimeMillis();
            Map<String, List> allInfos = ExcelUtils.getAllInfosFromFile(loadFile(request), regionHeaders, hospitalHeaders, repHeaders, dsmHeaders, rsmHeaders);
            long end = System.currentTimeMillis();
            logger.info("all item size is " + allInfos.size() + ", spend time " + (end - begin) + " ms");
            
            logger.info("remove the old user infos firstly");
            userService.delete();
            userService.insert(allInfos.get("users"));
            long finish = System.currentTimeMillis();
            logger.info("time spent to insert the user infos into DB is " + (finish-end) + " ms");
            
            logger.info("begin to handle the hospital");
            hospitalService.delete();
            hospitalService.insert(allInfos.get("hospitals"));
            long hosFinish = System.currentTimeMillis();
            logger.info("time spent to insert the hospital infos into DB is " + (hosFinish-finish) + " ms");
            
            logger.info("begin to handle the hospital user reference");
            userService.deleteHosUsers();
            userService.insertHosUsers(allInfos.get("hosUsers"));
            long hosUserFinish = System.currentTimeMillis();
            logger.info("time spent to insert the hospital user reference infos into DB is " + (hosUserFinish - hosFinish) + " ms");
            
            request.getSession().setAttribute(RhinocortAttributes.UPLOAD_FILE_MESSAGE, RhinocortAttributes.RETURNED_MESSAGE_0);
        }catch(Exception e){
            logger.error("fail to upload the file,",e);
            request.getSession().setAttribute(RhinocortAttributes.UPLOAD_FILE_MESSAGE, (null==e.getMessage()||"".equalsIgnoreCase(e.getMessage()))?RhinocortAttributes.RETURNED_MESSAGE_1:e.getMessage());
        }
        request.getSession().setAttribute(RhinocortAttributes.MESSAGE_AREA_ID, "uploadAllResult_div");
        return "redirect:showUploadData";
    }
    
    private String loadFile(HttpServletRequest request){
    	String savePath =request.getSession().getServletContext().getRealPath("/")+"uploadfiles\\";  
    	String fileName = "";
    	try{
    		request.setCharacterEncoding("UTF-8");
    		File f1 = new File(savePath);
    		if (!f1.exists()) {
    			f1.mkdirs();  
    		}  
    		DiskFileItemFactory fac = new DiskFileItemFactory();
    		ServletFileUpload upload = new ServletFileUpload(fac);
    		
    		upload.setHeaderEncoding("utf-8");  
    		List fileList = null;
    		try {  
    			fileList = upload.parseRequest(request);  
    		} catch (FileUploadException ex) {
    			ex.printStackTrace();
    		}
    		
    		FileItem item=(FileItem)fileList.get(0);
    		
    		if( item.isFormField() ){
    			logger.info(item.getFieldName());
    			logger.info(item.getString());
    		}else{
    			fileName = item.getName();
    			if( fileName.indexOf("\\") > 0 ){
    			    fileName = fileName.substring(fileName.lastIndexOf("\\")+1, fileName.length());
    			}
    			FileOutputStream fos = new FileOutputStream(savePath+fileName);
    			InputStream in = item.getInputStream();
    			byte buffer[] = new byte[1024];
    			int len = 0;
    			while((len=in.read(buffer))>0){
    				fos.write(buffer,0,len);
    			}
    			in.close();
    			fos.close();
    		}
    	}catch(Exception e){
    		logger.error("fail to load the file");
    	}
    	return savePath+fileName;
    }
}
