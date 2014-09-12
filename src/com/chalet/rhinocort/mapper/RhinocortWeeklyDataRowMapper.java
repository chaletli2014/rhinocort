package com.chalet.rhinocort.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chalet.rhinocort.model.RhinocortData;
import com.chalet.rhinocort.model.RhinocortWeeklyData;

/**
 * @author Chalet
 * @version 创建时间：2014年3月15日 下午4:04:26
 * 类说明
 */

public class RhinocortWeeklyDataRowMapper implements RowMapper<RhinocortWeeklyData>{

	@Override
	public RhinocortWeeklyData mapRow(ResultSet rs, int arg1) throws SQLException {
		RhinocortWeeklyData rhinocortData = new RhinocortWeeklyData();
		rhinocortData.setInRate(rs.getDouble("inRate"));
		rhinocortData.setLevel(rs.getString("level"));
		rhinocortData.setName(rs.getString("name"));
		rhinocortData.setNum1(rs.getInt("num1"));
		rhinocortData.setNum2(rs.getInt("num2"));
		rhinocortData.setNum3(rs.getInt("num3"));
		rhinocortData.setNum4(rs.getInt("num4"));
		rhinocortData.setNum5(rs.getInt("num5"));
        return rhinocortData;
	}

}
