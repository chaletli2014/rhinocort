package com.chalet.rhinocort.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.chalet.rhinocort.mapper.HospitalRowMapper;
import com.chalet.rhinocort.mapper.UserInfoRowMapper;
import com.chalet.rhinocort.model.Hospital;
import com.chalet.rhinocort.model.UserInfo;
import com.chalet.rhinocort.utils.DataBean;

/**
 * @author Chalet
 * @version 创建时间：2013年11月24日 下午5:07:36
 * 类说明
 */

@Repository("hospitalDAO")
public class HospitalDAOImpl implements HospitalDAO {

	@Autowired
	@Qualifier("dataBean")
	private DataBean dataBean;
	
	private Logger logger = Logger.getLogger(HospitalDAOImpl.class);

	public List<Hospital> getAllHospitals() throws Exception{
	    String searchSQL = "select * from tbl_hospital where isKPI='1'";
        return dataBean.getJdbcTemplate().query(searchSQL, new HospitalRowMapper());
	}

	@Override
	public void insert(final List<Hospital> hospitals) throws Exception {
		String insertSQL = "insert into tbl_hospital values(null,?,?,?,?,?,?,?,?,?,?,?,?)";
		dataBean.getJdbcTemplate().batchUpdate(insertSQL, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, hospitals.get(i).getName());
				ps.setString(2, hospitals.get(i).getCode());
				ps.setString(3, hospitals.get(i).getCity());
				ps.setString(4, hospitals.get(i).getProvince());
				ps.setString(5, hospitals.get(i).getRegion());
				ps.setString(6, hospitals.get(i).getRegionCenter());
				ps.setString(7, hospitals.get(i).getDsmCode());
				ps.setString(8, hospitals.get(i).getDsmName());
				ps.setString(9, hospitals.get(i).getSaleCode());
				ps.setString(10, hospitals.get(i).getSaleName());
				ps.setString(11, hospitals.get(i).getLevel());
				ps.setString(12, hospitals.get(i).getIsKPI());
			}
			
			@Override
			public int getBatchSize() {
				return hospitals.size();
			}
		});
	}
	
	@Override
	public List<Hospital> getHospitalsByDSMTel(String telephone) throws Exception {
		StringBuffer searchSQL = new StringBuffer("select h.id ")
				.append(",case when h.isKPI='1' then concat('* ',h.name) else h.name end name")
				.append(", h.code")
				.append(", h.city")
				.append(", h.province")
				.append(", h.region")
				.append(", h.saleCode")
				.append(", h.dsmCode")
				.append(", h.isKPI ")
				.append(" from tbl_userinfo u, tbl_hospital h ")
				.append(" where u.userCode = h.dsmCode and u.telephone = ? ")
				.append(" order by h.isKPI desc, h.name asc ");
        return dataBean.getJdbcTemplate().query(searchSQL.toString(), new Object[]{telephone}, new HospitalRowMapper());
	}
	
	@Override
	public List<Hospital> getHospitalsByUserTel(String telephone) throws Exception {
		StringBuffer searchSQL = new StringBuffer("select h.id ")
		.append(",case when h.isKPI='1' then concat('* ',h.name) else h.name end name")
		.append(", h.code")
		.append(", h.city")
		.append(", h.province")
		.append(", h.region")
		.append(", h.saleCode")
		.append(", h.dsmCode")
		.append(", h.isKPI ")
		.append(" from tbl_userinfo u, tbl_hos_user hu, tbl_hospital h ")
		.append(" where u.userCode = hu.userCode and hu.hosCode = h.code and u.telephone = ? ")
		.append(" order by h.isKPI desc, h.name asc");
		return dataBean.getJdbcTemplate().query(searchSQL.toString(), new Object[]{telephone}, new HospitalRowMapper());
	}
	
	public UserInfo getPrimarySalesOfHospital(String hospitalCode) throws Exception {
	    UserInfo primarySales = new UserInfo();
        String sql = "select ui.* from tbl_hospital h, tbl_userinfo ui where h.code = ? and h.saleCode = ui.userCode";
        primarySales = dataBean.getJdbcTemplate().queryForObject(sql, new Object[]{hospitalCode}, new UserInfoRowMapper());
        return primarySales;
	}

    @Override
    public void delete() throws Exception {
        dataBean.getJdbcTemplate().update("truncate table tbl_hospital");
    }
    
	@Override
	public Hospital getHospitalByCode(String hospitalCode) throws Exception {
	    Hospital hospital = new Hospital();
	    String sql = "select * from tbl_hospital where code = ?";
	    hospital = dataBean.getJdbcTemplate().queryForObject(sql, new Object[]{hospitalCode}, new HospitalRowMapper());
	    return hospital;
	}
	
	public DataBean getDataBean() {
		return dataBean;
	}
	public void setDataBean(DataBean dataBean) {
		this.dataBean = dataBean;
	}
}
