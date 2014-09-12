package com.chalet.rhinocort.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chalet.rhinocort.model.Hospital;

/**
 * @author Chalet
 * @version 创建时间：2014年3月16日 下午2:39:39
 * 类说明
 */

public class HospitalRowMapper implements RowMapper<Hospital>{

	public Hospital mapRow(ResultSet rs, int i) throws SQLException {
		Hospital dbHospital = new Hospital();
		dbHospital.setId(rs.getInt("id"));
		dbHospital.setCode(rs.getString("code"));
		dbHospital.setName(rs.getString("name"));
		dbHospital.setCity(rs.getString("city"));
		dbHospital.setProvince(rs.getString("province"));
		dbHospital.setRegion(rs.getString("region"));
		dbHospital.setSaleCode(rs.getString("saleCode"));
		dbHospital.setDsmCode(rs.getString("dsmCode"));
		dbHospital.setIsKPI(rs.getString("isKPI"));
		return dbHospital;
	}
}
