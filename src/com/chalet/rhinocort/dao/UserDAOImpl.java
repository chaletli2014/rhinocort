package com.chalet.rhinocort.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.chalet.rhinocort.mapper.UserInfoRowMapper;
import com.chalet.rhinocort.mapper.WebUserInfoRowMapper;
import com.chalet.rhinocort.model.HospitalUserRefer;
import com.chalet.rhinocort.model.UserInfo;
import com.chalet.rhinocort.model.WebUserInfo;
import com.chalet.rhinocort.utils.DataBean;

/**
 * @author Chalet
 * @version 创建时间：2013年11月24日 下午5:19:10
 * 类说明
 */

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {

	@Autowired
	@Qualifier("dataBean")
	private DataBean dataBean;
	
	private Logger logger = Logger.getLogger(UserDAOImpl.class);
	
	@Override
	public UserInfo getUserInfoByTel(String telephone) throws Exception {
		UserInfo userInfo = new UserInfo();
		String sql = "select * from tbl_userinfo where telephone = ?";
		userInfo = dataBean.getJdbcTemplate().queryForObject(sql, new Object[]{telephone}, new UserInfoRowMapper());
		return userInfo;
	}
	
	@Override
	public UserInfo getUserInfoByUserCode(String userCode) throws Exception {
	    UserInfo userInfo = new UserInfo();
	    String sql = "select * from tbl_userinfo where userCode = ?";
	    logger.info("get user info by user code - " + userCode);
	    userInfo = dataBean.getJdbcTemplate().queryForObject(sql, new Object[]{userCode}, new UserInfoRowMapper());
	    return userInfo;
	}
	
	@Override
	public UserInfo getUserInfoByRegion(String region) throws Exception {
		UserInfo userInfo = new UserInfo();
		String sql = "select * from tbl_userinfo where region = ? and level='RSM' ";
		logger.info("get user info by region - " + region);
		userInfo = dataBean.getJdbcTemplate().queryForObject(sql, new Object[]{region}, new UserInfoRowMapper());
		return userInfo;
	}
	
	public List<UserInfo> getUserInfoByLevel(String level) throws Exception {
		String sql = "";
		sql = "select * from tbl_userinfo where level = '"+level+"' order by region";
		return dataBean.getJdbcTemplate().query(sql, new UserInfoRowMapper());
	}
	
	@Override
	public void insert(final List<UserInfo> userInfos) throws Exception {
		String insertSQL = "insert into tbl_userinfo values(null,?,?,?,?,?,?,?,?,now(),now())";
		dataBean.getJdbcTemplate().batchUpdate(insertSQL, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, userInfos.get(i).getName());
				ps.setString(2, userInfos.get(i).getUserCode());
				ps.setString(3, userInfos.get(i).getTelephone());
				ps.setString(4, userInfos.get(i).getEmail());
				ps.setString(5, userInfos.get(i).getRegion());
				ps.setString(6, userInfos.get(i).getRegionCenter());
				ps.setString(7, userInfos.get(i).getLevel());
				ps.setString(8, userInfos.get(i).getSuperior());
			}
			
			@Override
			public int getBatchSize() {
				return userInfos.size();
			}
		});
	}
	
	@Override
	public void insert(final UserInfo userInfo) throws Exception {
		String insertSQL = "insert into tbl_userinfo values(null,?,?,?,?,?,?,?,?,now(),now())";
		dataBean.getJdbcTemplate().update(insertSQL, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, userInfo.getName());
				ps.setString(2, userInfo.getUserCode());
				ps.setString(3, userInfo.getTelephone());
				ps.setString(4, userInfo.getEmail());
				ps.setString(5, userInfo.getRegion());
				ps.setString(6, userInfo.getRegionCenter());
				ps.setString(7, userInfo.getLevel());
				ps.setString(8, userInfo.getSuperior());
			}
		});
	}

	@Override
	public void delete() throws Exception {
		dataBean.getJdbcTemplate().update("truncate table tbl_userinfo");
	}
	
	public WebUserInfo getWebUser(String telephone, String password) throws Exception {
        WebUserInfo userInfo = new WebUserInfo();
        String sql = "select * from tbl_web_userinfo where telephone = ? and password = ?";
        userInfo = dataBean.getJdbcTemplate().queryForObject(sql, new Object[]{telephone, password}, new WebUserInfoRowMapper());
        return userInfo;
    }
	

    public void deleteHosUsers() throws Exception {
        dataBean.getJdbcTemplate().update("truncate table tbl_hos_user");
    }

    public void insertHosUsers(final List<HospitalUserRefer> hosUsers) throws Exception {
        String insertSQL = "insert into tbl_hos_user values(?,?,?)";
        dataBean.getJdbcTemplate().batchUpdate(insertSQL, new BatchPreparedStatementSetter() {
            
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, hosUsers.get(i).getHospitalCode());
                ps.setString(2, hosUsers.get(i).getUserCode());
                ps.setString(3, hosUsers.get(i).getIsPrimary());
            }
            
            @Override
            public int getBatchSize() {
                return hosUsers.size();
            }
        });
    }
}
