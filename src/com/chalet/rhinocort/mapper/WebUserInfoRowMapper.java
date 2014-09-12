package com.chalet.rhinocort.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chalet.rhinocort.model.WebUserInfo;

/**
 * @author Chalet
 * @version 创建时间：2014年3月16日 下午2:09:44
 * 类说明
 */

public class WebUserInfoRowMapper implements RowMapper<WebUserInfo>{

	public WebUserInfo mapRow(ResultSet rs, int i) throws SQLException {
        WebUserInfo dbUser = new WebUserInfo();
        dbUser.setId(rs.getInt("id"));
        dbUser.setName(rs.getString("name"));
        dbUser.setTelephone(rs.getString("telephone"));
        dbUser.setLevel(rs.getString("level"));
        dbUser.setCreatedate(rs.getDate("createdate"));
        return dbUser;
    }
}
