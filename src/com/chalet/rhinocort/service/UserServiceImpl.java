package com.chalet.rhinocort.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.chalet.rhinocort.dao.UserDAO;
import com.chalet.rhinocort.model.HospitalUserRefer;
import com.chalet.rhinocort.model.UserInfo;
import com.chalet.rhinocort.model.WebUserInfo;
import com.chalet.rhinocort.utils.ChaletMD5Utils;

/**
 * @author Chalet
 * @version 创建时间：2013年11月24日 下午5:30:57
 * 类说明
 */

@Service("userService")
public class UserServiceImpl implements UserService {

    Logger logger = Logger.getLogger(UserServiceImpl.class);
    
	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;
	
	@Override
	public UserInfo getUserInfoByTel(String telephone) throws Exception {
		return userDAO.getUserInfoByTel(telephone);
	}
	
	@Override
	public UserInfo getUserInfoByUserCode(String userCode) throws Exception {
	    return userDAO.getUserInfoByUserCode(userCode);
	}
	
	@Override
	public UserInfo getUserInfoByRegion(String region) throws Exception {
		return userDAO.getUserInfoByRegion(region);
	}
	public List<UserInfo> getUserInfoByLevel(String level) throws Exception {
	    return userDAO.getUserInfoByLevel(level);
	}

	@Override
	public void insert(List<UserInfo> userInfos) throws Exception {
	    try{
	        userDAO.insert(userInfos);
	    }catch(Exception e){
	        logger.error("fail to insert the user info,",e);
	        throw new Exception("更新用户信息失败，请确保数据格式及内容正确");
	    }
	}
	
	public WebUserInfo getWebUser(String telephone, String password) throws Exception {
        try{
            return userDAO.getWebUser(telephone, ChaletMD5Utils.MD5(password));
        } catch(EmptyResultDataAccessException erd){
            logger.info("there is no web user found.");
            return null;
        } catch(Exception e){
            logger.error("fail to get the web user info by telephone - " + telephone,e);
            return null;
        }
    }
	
	@Override
	public void delete() throws Exception {
		userDAO.delete();
	}
	

    public void deleteHosUsers() throws Exception {
        userDAO.deleteHosUsers();
    }

    public void insertHosUsers(List<HospitalUserRefer> hosUsers) throws Exception {
        if( null != hosUsers && hosUsers.size() > 0){
            userDAO.insertHosUsers(hosUsers);
        }else{
            logger.info("the size of the list hosUsers is 0, no need to insert the data");
        }
    }
}