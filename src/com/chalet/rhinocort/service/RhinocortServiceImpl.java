package com.chalet.rhinocort.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.chalet.rhinocort.dao.RhinocortDAO;
import com.chalet.rhinocort.model.Hospital;
import com.chalet.rhinocort.model.RhinocortData;
import com.chalet.rhinocort.model.RhinocortWeeklyData;
import com.chalet.rhinocort.model.RhinocortWeeklyExportData;
import com.chalet.rhinocort.model.UserInfo;
import com.chalet.rhinocort.utils.RhinocortAttributes;

/**
 * @author Chalet
 * @version 创建时间：2013年11月27日 下午11:42:40
 * 类说明
 */

@Service("rhinocortService")
public class RhinocortServiceImpl implements RhinocortService {

	@Autowired
	@Qualifier("rhinocortDAO")
	private RhinocortDAO rhinocortDAO;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	@Qualifier("hospitalService")
	private HospitalService hospitalService;
	
	private Logger logger = Logger.getLogger(RhinocortServiceImpl.class);
	
	@Override
	public RhinocortData getRhinocortDataByHospitalCode(String hospitalCode) throws Exception {
		try{
	        return rhinocortDAO.getRhinocortDataByHospitalCode(hospitalCode);
	    } catch(EmptyResultDataAccessException erd){
	        logger.info("there is no record found.");
	        return null;
	    } catch(Exception e){
	        logger.error(String.format("fail to get the rhinocort data by hospital - %s", hospitalCode),e);
	        return null;
	    }
	}

	@Override
	public RhinocortData getRhinocortDataById(int id) throws Exception {
		try{
            return rhinocortDAO.getRhinocortDataById(id);
        }catch(EmptyResultDataAccessException erd){
	        logger.info(String.format("there is no record found which id is %s",id));
	        return null;
	    } catch(Exception e){
            logger.error(String.format("fail to get the rhinocort data by ID - %s", id),e);
            return null;
        }
	}

	@Override
	public void insert(RhinocortData rhinocortData, UserInfo operator, Hospital hospital) throws Exception {
		rhinocortDAO.insert(rhinocortData, operator, hospital);
	}

	@Override
	public void update(RhinocortData rhinocortData, UserInfo operator)
			throws Exception {
		rhinocortDAO.update(rhinocortData, operator);
	}

	@Override
	public List<RhinocortData> getRhinocortDataByDate(Date createdatebegin, Date createdateend) throws Exception {
		try{
			return rhinocortDAO.getRhinocortDataByDate(createdatebegin,createdateend);
		}catch(EmptyResultDataAccessException erd){
            logger.warn("there is no record found.");
            return new ArrayList<RhinocortData>();
        } catch(Exception e){
	        logger.error(String.format("fail to get the Rhinocort data by date - %s to %s", createdatebegin,createdateend),e);
	        return new ArrayList<RhinocortData>();
	    }
	}

	@Override
	public List<RhinocortWeeklyData> getWeeklyReportData() throws Exception {
		try{
			return rhinocortDAO.getWeeklyReportData();
		}catch(EmptyResultDataAccessException erd){
			logger.warn("there is no record found.");
            return new ArrayList<RhinocortWeeklyData>();
		}catch(Exception e){
	        logger.error("fail to get lastweek Rhinocort data",e);
	        return new ArrayList<RhinocortWeeklyData>();
	    }
	}

	@Override
	public List<RhinocortWeeklyData> getLowerWeeklyReportData(String level,
			String lowerName) throws Exception {
		List<RhinocortWeeklyData> weeklyData = new ArrayList<RhinocortWeeklyData>();
		switch(level){
			case RhinocortAttributes.USER_LEVEL_RSD:
				weeklyData = rhinocortDAO.getRSMWeeklyReportData(lowerName);
				break;
			case RhinocortAttributes.USER_LEVEL_RSM:
				weeklyData = rhinocortDAO.getREPWeeklyReportData(lowerName);
				break;
		}
		return weeklyData;
	}

	@Override
	public List<RhinocortWeeklyExportData> getWeeklyExportReportData(Date startDate, Date endDate) throws Exception {
		try{
			return rhinocortDAO.getWeeklyExportReportData(startDate, endDate);
		}catch(EmptyResultDataAccessException erd){
			logger.warn("there is no record found in method getWeeklyExportReportData.");
            return new ArrayList<RhinocortWeeklyExportData>();
		}catch(Exception e){
	        logger.error("fail to get weekly export Rhinocort data",e);
	        return new ArrayList<RhinocortWeeklyExportData>();
	    }
	}
	
	@Override
	public List<RhinocortWeeklyExportData> getWeeklyExportNotReportData(Date startDate, Date endDate) throws Exception {
		try{
			return rhinocortDAO.getWeeklyExportNotReportData(startDate, endDate);
		}catch(EmptyResultDataAccessException erd){
			logger.warn("there is no record found in method getWeeklyExportReportData.");
			return new ArrayList<RhinocortWeeklyExportData>();
		}catch(Exception e){
			logger.error("fail to get weekly export Rhinocort data",e);
			return new ArrayList<RhinocortWeeklyExportData>();
		}
	}
}
