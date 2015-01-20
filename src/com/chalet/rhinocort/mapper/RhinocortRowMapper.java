package com.chalet.rhinocort.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chalet.rhinocort.model.RhinocortData;

/**
 * @author Chalet
 * @version 创建时间：2014年3月15日 下午4:04:26
 * 类说明
 */

public class RhinocortRowMapper implements RowMapper<RhinocortData>{

	@Override
	public RhinocortData mapRow(ResultSet rs, int arg1) throws SQLException {
		RhinocortData rhinocortData = new RhinocortData();
		rhinocortData.setDataId(rs.getInt("id"));
		rhinocortData.setCreatedate(rs.getDate("createdate"));
		rhinocortData.setHospitalCode(rs.getString("hospitalCode"));
		rhinocortData.setNum1(rs.getInt("num1"));
		rhinocortData.setNum2(rs.getInt("num2"));
		rhinocortData.setNum3(rs.getInt("num3"));
		rhinocortData.setNum4(rs.getInt("num4"));
    	rhinocortData.setNum5(rs.getInt("num5"));
    	rhinocortData.setNum6(rs.getInt("num6"));
    	rhinocortData.setNum7(rs.getInt("num7"));
    	rhinocortData.setNum8(rs.getInt("num8"));
    	rhinocortData.setNum9(rs.getInt("num9"));
    	rhinocortData.setNum10(rs.getInt("num10"));
    	rhinocortData.setNum11(rs.getInt("num11"));
    	rhinocortData.setNum12(rs.getInt("num12"));
    	rhinocortData.setDsmName(rs.getString("dsmName"));
        return rhinocortData;
	}

}
