package com.chalet.rhinocort.service;

import java.util.List;

import com.chalet.rhinocort.model.Hospital;
import com.chalet.rhinocort.model.UserInfo;

/**
 * @author Chalet
 * @version 创建时间：2013年11月24日 下午5:14:28
 * 类说明
 */

public interface HospitalService {
	public List<Hospital> getHospitalsByUserTel(String telephone) throws Exception;
	
	public void insert(List<Hospital> hospitals) throws Exception;
	
	public void delete() throws Exception;
	
	public UserInfo getPrimarySalesOfHospital(String hospitalCode) throws Exception;
	
	public List<Hospital> getAllHospitals() throws Exception;
	
	public Hospital getHospitalByCode(String hospitalCode) throws Exception;
}
