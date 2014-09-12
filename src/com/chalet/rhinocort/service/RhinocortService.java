package com.chalet.rhinocort.service;

import java.util.Date;
import java.util.List;

import com.chalet.rhinocort.model.Hospital;
import com.chalet.rhinocort.model.RhinocortData;
import com.chalet.rhinocort.model.RhinocortWeeklyData;
import com.chalet.rhinocort.model.RhinocortWeeklyExportData;
import com.chalet.rhinocort.model.UserInfo;

/**
 * @author Chalet
 * @version 创建时间：2013年11月24日 下午3:50:47
 * 类说明
 */

public interface RhinocortService {

	public RhinocortData getRhinocortDataByHospitalCode(String hospitalCode) throws Exception;
	public List<RhinocortData> getRhinocortDataByDate(Date createdatebegin, Date createdateend) throws Exception;
	public RhinocortData getRhinocortDataById(int id) throws Exception;
	public List<RhinocortWeeklyData> getWeeklyReportData() throws Exception;
	public List<RhinocortWeeklyData> getLowerWeeklyReportData(String level, String lowerName) throws Exception;
	public List<RhinocortWeeklyExportData> getWeeklyExportReportData(Date startDate, Date endDate) throws Exception;
	public List<RhinocortWeeklyExportData> getWeeklyExportNotReportData(Date startDate, Date endDate) throws Exception;
	public void insert(RhinocortData rhinocortData, UserInfo operator, Hospital hospital) throws Exception;
	public void update(RhinocortData rhinocortData, UserInfo operator) throws Exception;
	
}
