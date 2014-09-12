package com.chalet.rhinocort.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.chalet.rhinocort.model.RhinocortWeeklyExportData;

/**
 * @author Chalet
 * @version 创建时间：2014年4月26日 下午10:27:35
 * 类说明
 */

public class RhinocortWeeklyExportDataRowMapper implements RowMapper<RhinocortWeeklyExportData>{

	@Override
	public RhinocortWeeklyExportData mapRow(ResultSet rs, int arg1) throws SQLException {
		RhinocortWeeklyExportData rhinocortData = new RhinocortWeeklyExportData();
		rhinocortData.setHospitalCode(rs.getString("hospitalCode"));
		rhinocortData.setHospitalName(rs.getString("hospitalName"));
		rhinocortData.setEnTNumPerDay(rs.getDouble("entNumPerDay"));
		rhinocortData.setArNumPerDay(rs.getDouble("arNumPerDay"));
		rhinocortData.setRhiARNumPerDay(rs.getDouble("rhiARNumPerDay"));
		rhinocortData.setPrhinitisNumPerDay(rs.getDouble("prhinitisNumPerDay"));
		rhinocortData.setRhiPRhinitisNumPerDay(rs.getDouble("rhiPRhinitisNumPerDay"));
		rhinocortData.setGmjsNumPerDay(rs.getDouble("gmjsNumPerDay"));
		rhinocortData.setCnjsNumPerDay(rs.getDouble("cnjsNumPerDay"));
		rhinocortData.setSaleName(rs.getString("saleName"));
		rhinocortData.setDsmName(rs.getString("dsmName"));
		rhinocortData.setRegion(rs.getString("region"));
		rhinocortData.setRegionCenter(rs.getString("regionCenter"));
        return rhinocortData;
	}

}
