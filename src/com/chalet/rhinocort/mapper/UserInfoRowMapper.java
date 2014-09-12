package com.chalet.rhinocort.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chalet.rhinocort.model.UserInfo;

public class UserInfoRowMapper implements RowMapper<UserInfo>{
    @Override
    public UserInfo mapRow(ResultSet rs, int i) throws SQLException {
        UserInfo dbUser = new UserInfo();
        dbUser.setId(rs.getInt("id"));
        dbUser.setName(rs.getString("name"));
        dbUser.setUserCode(rs.getString("userCode"));
        dbUser.setTelephone(rs.getString("telephone"));
        dbUser.setRegion(rs.getString("region"));
        dbUser.setRegionCenter(rs.getString("regionCenter"));
        dbUser.setLevel(rs.getString("level"));
        dbUser.setSuperior(rs.getString("superior"));
        return dbUser;
    }
    
}
