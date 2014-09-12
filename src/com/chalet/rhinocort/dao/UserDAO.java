package com.chalet.rhinocort.dao;

import java.util.List;

import com.chalet.rhinocort.model.HospitalUserRefer;
import com.chalet.rhinocort.model.UserInfo;
import com.chalet.rhinocort.model.WebUserInfo;

/**
 * @author Chalet
 * @version 创建时间：2013年11月24日 下午5:16:40
 * 类说明
 */

public interface UserDAO {

	public UserInfo getUserInfoByTel(String telephone) throws Exception;
	public UserInfo getUserInfoByRegion(String region) throws Exception;
	public UserInfo getUserInfoByUserCode(String userCode) throws Exception;
	public List<UserInfo> getUserInfoByLevel(String level) throws Exception;
	public void insert(List<UserInfo> userInfos) throws Exception;
	public void insert(UserInfo userInfo) throws Exception;
	public void delete() throws Exception;
	public WebUserInfo getWebUser(String telephone, String password) throws Exception;
	
	public void deleteHosUsers() throws Exception;
	public void insertHosUsers(List<HospitalUserRefer> hosUsers) throws Exception;
	
    public void deleteBMUsers() throws Exception;
}
