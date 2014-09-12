package com.chalet.rhinocort.dao;

import java.util.Date;
import java.util.List;

import com.chalet.rhinocort.model.Hospital;
import com.chalet.rhinocort.model.RhinocortData;
import com.chalet.rhinocort.model.RhinocortWeeklyData;
import com.chalet.rhinocort.model.RhinocortWeeklyExportData;
import com.chalet.rhinocort.model.UserInfo;

/**
 * @author Chalet
 * @version 创建时间：2013年11月24日 下午3:53:37
 * 类说明
 */

public interface RhinocortDAO {

    public RhinocortData getRhinocortDataByHospitalCode(String hospitalName) throws Exception;
    public List<RhinocortData> getRhinocortDataByDate(Date createdatebegin, Date createdateend) throws Exception;
    public RhinocortData getRhinocortDataById(int id) throws Exception;
	public void insert(RhinocortData RhinocortData, UserInfo operator, Hospital hospital) throws Exception;
	public void update(RhinocortData RhinocortData, UserInfo operator) throws Exception;
	
	public List<RhinocortWeeklyData> getWeeklyReportData() throws Exception;
	public List<RhinocortWeeklyData> getRSMWeeklyReportData(String rsdName) throws Exception;
	public List<RhinocortWeeklyData> getREPWeeklyReportData(String rsmName) throws Exception;
	public List<RhinocortWeeklyExportData> getWeeklyExportReportData(Date startDate,Date endDate) throws Exception;
	public List<RhinocortWeeklyExportData> getWeeklyExportNotReportData(Date startDate,Date endDate) throws Exception;
}
