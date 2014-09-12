package com.chalet.rhinocort.service;

import java.util.List;

import com.chalet.rhinocort.model.HospitalUserRefer;
import com.chalet.rhinocort.model.UserInfo;
import com.chalet.rhinocort.model.WebUserInfo;

/**
 * @author Chalet
 * @version 创建时间：2013年11月24日 下午5:30:35
 * 类说明
 */

public interface UserService {

	public UserInfo getUserInfoByTel(String telephone) throws Exception;
	public UserInfo getUserInfoByUserCode(String userCode) throws Exception;
	public UserInfo getUserInfoByRegion(String region) throws Exception;
	public List<UserInfo> getUserInfoByLevel(String level) throws Exception;
	public void insert(List<UserInfo> userInfos) throws Exception;
	public void delete() throws Exception;
	public WebUserInfo getWebUser(String telephone, String password) throws Exception;
	
	public void deleteHosUsers() throws Exception;
	public void insertHosUsers(List<HospitalUserRefer> hosUsers) throws Exception;
}