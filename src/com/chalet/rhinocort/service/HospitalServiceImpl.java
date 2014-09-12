package com.chalet.rhinocort.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.chalet.rhinocort.dao.HospitalDAO;
import com.chalet.rhinocort.dao.UserDAO;
import com.chalet.rhinocort.model.Hospital;
import com.chalet.rhinocort.model.UserInfo;
import com.chalet.rhinocort.utils.RhinocortAttributes;

/**
 * @author Chalet
 * @version 创建时间：2013年11月24日 下午5:14:49
 * 类说明
 */

@Service("hospitalService")
public class HospitalServiceImpl implements HospitalService {

	@Autowired
	@Qualifier("hospitalDAO")
	private HospitalDAO hospitalDAO;
	
	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;
	
	private Logger logger = Logger.getLogger(HospitalServiceImpl.class);

	public List<Hospital> getAllHospitals() throws Exception{
	    return hospitalDAO.getAllHospitals();
	}
	
	@Override
	public List<Hospital> getHospitalsByUserTel(String telephone) throws Exception {
	    UserInfo user = userDAO.getUserInfoByTel(telephone);
	    if( RhinocortAttributes.USER_LEVEL_REP.equalsIgnoreCase(user.getLevel()) ){
	        return hospitalDAO.getHospitalsByUserTel(telephone);
	    }else if ( RhinocortAttributes.USER_LEVEL_DSM.equalsIgnoreCase(user.getLevel()) ){
	        return hospitalDAO.getHospitalsByDSMTel(telephone);
	    }
	    return new ArrayList<Hospital>();
	}
	
	@Override
	public void insert(List<Hospital> hospitals) throws Exception {
		hospitalDAO.insert(hospitals);
	}

	@Override
	public void delete() throws Exception {
		hospitalDAO.delete();
	}
	
    public UserInfo getPrimarySalesOfHospital(String hospitalCode) throws Exception {
        try{
            return hospitalDAO.getPrimarySalesOfHospital(hospitalCode);
        }catch(IncorrectResultSizeDataAccessException e){
            logger.error(String.format("fail to get the primary sales of hospital %s, incorrect result size", hospitalCode));
            return null;
        }
    }
    
    @Override
	public Hospital getHospitalByCode(String hospitalCode) throws Exception {
	    return hospitalDAO.getHospitalByCode(hospitalCode);
	}
}
