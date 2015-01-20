package com.chalet.rhinocort.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.chalet.rhinocort.mapper.RhinocortDailyDataRowMapper;
import com.chalet.rhinocort.mapper.RhinocortRowMapper;
import com.chalet.rhinocort.mapper.RhinocortWeeklyDataRowMapper;
import com.chalet.rhinocort.mapper.RhinocortWeeklyExportDataRowMapper;
import com.chalet.rhinocort.model.Hospital;
import com.chalet.rhinocort.model.RhinocortData;
import com.chalet.rhinocort.model.RhinocortWeeklyData;
import com.chalet.rhinocort.model.RhinocortWeeklyExportData;
import com.chalet.rhinocort.model.UserInfo;
import com.chalet.rhinocort.utils.DataBean;
import com.chalet.rhinocort.utils.DateUtils;

/**
 * @author Chalet
 * @version 创建时间：2013年11月27日 下午11:29:42
 * 类说明
 */

@Repository("rhinocortDAO")
public class RhinocortDAOImpl implements RhinocortDAO {

	private Logger logger = Logger.getLogger(RhinocortDAOImpl.class);
	
	@Autowired
	@Qualifier("dataBean")
	private DataBean dataBean;
	
	@Override
	public RhinocortData getRhinocortDataByHospitalCode(String hospitalCode)
			throws Exception {
		StringBuffer sb = new StringBuffer("");
		Date startDateInCurrentWeek = DateUtils.getTheBeginDateOfRecordDate(new Date());
		sb.append("select rd.*,")
		.append(" ( select distinct u.name from tbl_userinfo u where u.userCode = rd.dsmCode ) as dsmName ")
		.append(" from tbl_rhinocort_data rd ")
		.append(" where rd.hospitalCode=? and rd.createdate between ? and now()");
		return dataBean.getJdbcTemplate().queryForObject(sb.toString(), new Object[]{hospitalCode,startDateInCurrentWeek}, new RhinocortRowMapper());
	}
	
	@Override
	public List<RhinocortData> getRhinocortDataByDate(Date createdatebegin, Date createdateend) throws Exception {
		StringBuffer sb = new StringBuffer("");
		sb.append("select rd.*,")
		.append(" ( select name from tbl_hospital h where h.code = rd.hospitalCode ) as hospitalName, ")
		.append(" ( select distinct u.name from tbl_userinfo u, tbl_hospital h where u.userCode = h.saleCode and h.code = rd.hospitalCode ) as salesName, ")
		.append(" ( select distinct u.name from tbl_userinfo u where u.userCode = rd.dsmCode ) as dsmName ")
		.append(" from tbl_rhinocort_data rd ")
		.append(" where ")
		.append(" DATE_FORMAT(rd.createdate,'%Y-%m-%d') between DATE_FORMAT(?,'%Y-%m-%d') and DATE_FORMAT(?,'%Y-%m-%d')")
		.append(" order by createdate desc");
		return dataBean.getJdbcTemplate().query(sb.toString(), new Object[]{new Timestamp(createdatebegin.getTime()),new Timestamp(createdateend.getTime())},new RhinocortDailyDataRowMapper());
	}

	@Override
	public RhinocortData getRhinocortDataById(int id) throws Exception {
		StringBuffer sb = new StringBuffer("");
		sb.append("select rd.*,")
		.append(" ( select distinct u.name from tbl_userinfo u where u.userCode = rd.dsmCode ) as dsmName ")
		.append(" from tbl_rhinocort_data rd ")
		.append(" where rd.id=? ");
		return dataBean.getJdbcTemplate().queryForObject(sb.toString(), new Object[]{id}, new RhinocortRowMapper());
	}
	
	@Override
	public void insert(final RhinocortData rhinocortData, final UserInfo operator, final Hospital hospital) throws Exception {
		logger.info(">>RhinocortDAOImpl insert");
		
		final String sql = "insert into tbl_rhinocort_data values(null,?,?,?,?,?,?,?,?,NOW(),NOW(),?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		dataBean.getJdbcTemplate().update(new PreparedStatementCreator(){
			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, rhinocortData.getHospitalCode());
				ps.setInt(2, rhinocortData.getNum1());
				ps.setInt(3, rhinocortData.getNum2());
				ps.setInt(4, rhinocortData.getNum3());
				ps.setInt(5, rhinocortData.getNum4());
				ps.setInt(6, rhinocortData.getNum5());
				ps.setString(7, operator.getName());
				ps.setString(8, (operator.getSuperior()==null||"".equalsIgnoreCase(operator.getSuperior()))?operator.getUserCode():operator.getSuperior());
				ps.setInt(9, rhinocortData.getNum6());
				ps.setInt(10, rhinocortData.getNum7());
				ps.setInt(11, rhinocortData.getNum8());
				ps.setInt(12, rhinocortData.getNum9());
				ps.setInt(13, rhinocortData.getNum10());
				ps.setInt(14, rhinocortData.getNum11());
				ps.setInt(15, rhinocortData.getNum12());
				return ps;
			}
		}, keyHolder);
		logger.info("returned id is "+keyHolder.getKey().intValue());
	}
	
	@Override
	public void update(RhinocortData rhinocortData, UserInfo operator) throws Exception {
		StringBuffer sql = new StringBuffer("update tbl_rhinocort_data set ");
	    sql.append("updatedate=NOW()")
	    	.append(", num1=? ")
	    	.append(", num2=? ")
	    	.append(", num3=? ")
	    	.append(", num4=? ")
	    	.append(", num5=? ")
	    	.append(", num6=? ")
	    	.append(", num7=? ")
	    	.append(", num8=? ")
	    	.append(", num9=? ")
	    	.append(", num10=? ")
	    	.append(", num11=? ")
	    	.append(", num12=? ")
	    	.append(", operatorName=? ")
	    	.append(" where id=? ");
	    
	    List<Object> paramList = new ArrayList<Object>();
	    paramList.add(rhinocortData.getNum1());
	    paramList.add(rhinocortData.getNum2());
	    paramList.add(rhinocortData.getNum3());
	    paramList.add(rhinocortData.getNum4());
	    paramList.add(rhinocortData.getNum5());
	    paramList.add(rhinocortData.getNum6());
	    paramList.add(rhinocortData.getNum7());
	    paramList.add(rhinocortData.getNum8());
	    paramList.add(rhinocortData.getNum9());
	    paramList.add(rhinocortData.getNum10());
	    paramList.add(rhinocortData.getNum11());
	    paramList.add(rhinocortData.getNum12());
    	paramList.add(operator.getName());
    	paramList.add(rhinocortData.getDataId());
		dataBean.getJdbcTemplate().update(sql.toString(), paramList.toArray());
	}
	
	public DataBean getDataBean() {
        return dataBean;
    }
    
    public void setDataBean(DataBean dataBean) {
        this.dataBean = dataBean;
    }

	@Override
	public List<RhinocortWeeklyData> getWeeklyReportData() throws Exception {
		Date lastMonday = DateUtils.getLastBeginDateOfRecordDate(new Date());
		Date thisMonday = new Date(lastMonday.getTime() + 7* 24 * 60 * 60 * 1000);
		StringBuffer sb = new StringBuffer("")
		.append("select h.regionCenter as name, ")
		.append(" 'RSD' as level, ")
		.append(" IFNULL(rhinocort_data.inRate,0) as inRate, ")
		.append(" IFNULL(rhinocort_data.num1,0) as num1, ")
		.append(" IFNULL(rhinocort_data.num2,0) as num2, ")
		.append(" IFNULL(rhinocort_data.num3,0) as num3, ")
		.append(" IFNULL(rhinocort_data.num4,0) as num4, ")
		.append(" IFNULL(rhinocort_data.num5,0) as num5 ")
		.append(" from ( ")
		.append("	select h.regionCenter, ")
		.append("		count(1) / (select count(1) from tbl_hospital h1 where h1.isKPI='1' and h1.regionCenter = h.regionCenter ) as inRate, ")
		.append("		(IFNULL(sum(num1),0)/count(1))*5 as num1, ")
		.append("		(IFNULL(sum(num2),0)/count(1))*5 as num2, ")
		.append("		(IFNULL(sum(num3),0)/count(1))*5 as num3, ")
		.append("		(IFNULL(sum(num4),0)/count(1))*5 as num4, ")
		.append("		(IFNULL(sum(num5),0)/count(1))*5 as num5 ")
		.append("	from tbl_rhinocort_data rd, tbl_hospital h ")
		.append("	where rd.createdate between ? and ? ")
		.append("	and rd.hospitalCode = h.code ")
		.append("	and h.isKPI='1' ")
		.append("	group by h.regionCenter ")
		.append(") rhinocort_data ")
		.append("right join tbl_hospital h on rhinocort_data.regionCenter = h.regionCenter ")
		.append("group by h.regionCenter ")
		.append("order by h.regionCenter ");
		return dataBean.getJdbcTemplate().query(sb.toString(), new Object[]{new Timestamp(lastMonday.getTime()),new Timestamp(thisMonday.getTime())},new RhinocortWeeklyDataRowMapper());
	}
	
	@Override
	public List<RhinocortWeeklyData> getRSMWeeklyReportData(String rsdName) throws Exception {
		Date lastMonday = DateUtils.getLastBeginDateOfRecordDate(new Date());
		Date thisMonday = new Date(lastMonday.getTime() + 7* 24 * 60 * 60 * 1000);
		StringBuffer sb = new StringBuffer("")
		.append("select h.region as name, ")
		.append(" 'RSM' as level, ")
		.append(" IFNULL(rhinocort_data.inRate,0) as inRate, ")
		.append(" IFNULL(rhinocort_data.num1,0) as num1, ")
		.append(" IFNULL(rhinocort_data.num2,0) as num2, ")
		.append(" IFNULL(rhinocort_data.num3,0) as num3, ")
		.append(" IFNULL(rhinocort_data.num4,0) as num4, ")
		.append(" IFNULL(rhinocort_data.num5,0) as num5 ")
		.append(" from ( ")
		.append("	select h.region, ")
		.append("		count(1) / (select count(1) from tbl_hospital h1 where h1.isKPI='1' and h1.region = h.region ) as inRate, ")
		.append("		(IFNULL(sum(num1),0)/count(1))*5 as num1, ")
		.append("		(IFNULL(sum(num2),0)/count(1))*5 as num2, ")
		.append("		(IFNULL(sum(num3),0)/count(1))*5 as num3, ")
		.append("		(IFNULL(sum(num4),0)/count(1))*5 as num4, ")
		.append("		(IFNULL(sum(num5),0)/count(1))*5 as num5 ")
		.append("	from tbl_rhinocort_data rd, tbl_hospital h ")
		.append("	where rd.createdate between ? and ? ")
		.append("	and rd.hospitalCode = h.code ")
		.append("	and h.isKPI='1' ")
		.append("	and h.regionCenter = ? ")
		.append("	group by h.region ")
		.append(") rhinocort_data ")
		.append("right join tbl_hospital h on rhinocort_data.region = h.region ")
		.append("where h.regionCenter = ? ")
		.append("and h.isKPI='1' ")
		.append("group by h.region ")
		.append("order by h.region ");
		return dataBean.getJdbcTemplate().query(sb.toString(), new Object[]{new Timestamp(lastMonday.getTime()),new Timestamp(thisMonday.getTime()),rsdName,rsdName},new RhinocortWeeklyDataRowMapper());
	}
	
	@Override
	public List<RhinocortWeeklyData> getREPWeeklyReportData(String rsmName) throws Exception {
		Date lastMonday = DateUtils.getLastBeginDateOfRecordDate(new Date());
		Date thisMonday = new Date(lastMonday.getTime() + 7* 24 * 60 * 60 * 1000);
		StringBuffer sb = new StringBuffer("")
		.append("select IFNULL(u.name,'vacant') as name,")
		.append(" 'REP' as level, ")
		.append(" IFNULL(rhinocort_data.inRate,0) as inRate, ")
		.append(" IFNULL(rhinocort_data.num1,0) as num1, ")
		.append(" IFNULL(rhinocort_data.num2,0) as num2, ")
		.append(" IFNULL(rhinocort_data.num3,0) as num3, ")
		.append(" IFNULL(rhinocort_data.num4,0) as num4, ")
		.append(" IFNULL(rhinocort_data.num5,0) as num5 ")
		.append(" from ( ")
		.append("	select h.region, h.saleCode, ")
		.append("		count(1) / (select count(1) from tbl_hospital h1 where h1.isKPI='1' and h1.region = h.region and h1.saleCode = h.saleCode ) as inRate, ")
		.append("		(IFNULL(sum(num1),0)/count(1))*5 as num1, ")
		.append("		(IFNULL(sum(num2),0)/count(1))*5 as num2, ")
		.append("		(IFNULL(sum(num3),0)/count(1))*5 as num3, ")
		.append("		(IFNULL(sum(num4),0)/count(1))*5 as num4, ")
		.append("		(IFNULL(sum(num5),0)/count(1))*5 as num5 ")
		.append("	from tbl_rhinocort_data rd, tbl_hospital h ")
		.append("	where rd.createdate between ? and ? ")
		.append("	and rd.hospitalCode = h.code ")
		.append("	and h.isKPI='1' ")
		.append("	and h.region = ? ")
		.append("	group by h.region, h.saleCode ")
		.append(") rhinocort_data ")
		.append("right join tbl_userinfo u on rhinocort_data.saleCode = u.userCode and rhinocort_data.region = u.region ")
		.append("where u.region = ? ")
		.append("and exists (select 1 from tbl_hospital h where u.userCode = h.saleCode and h.isKPI='1') ")
		.append("group by u.userCode ")
		.append("order by u.userCode ");
		return dataBean.getJdbcTemplate().query(sb.toString(), new Object[]{new Timestamp(lastMonday.getTime()),new Timestamp(thisMonday.getTime()),rsmName,rsmName},new RhinocortWeeklyDataRowMapper());
	}

	@Override
	public List<RhinocortWeeklyExportData> getWeeklyExportReportData(Date startDate, Date endDate) throws Exception {
		endDate = new Date(endDate.getTime() + 1* 24 * 60 * 60 * 1000);
		
		StringBuffer sb = new StringBuffer("")
		.append("select rd.hospitalCode,")
		.append(" h.name as hospitalName, ")
		.append(" ROUND(IFNULL(rd.num1,0),2) as entNumPerDay, ")
		.append(" ROUND(IFNULL(rd.num2,0),2) as arNumPerDay, ")
		.append(" ROUND(IFNULL(rd.num3,0),2) as rhiARNumPerDay, ")
		.append(" ROUND(IFNULL(rd.num4,0),2) as prhinitisNumPerDay, ")
		.append(" ROUND(IFNULL(rd.num5,0),2) as rhiPRhinitisNumPerDay, ")
		.append(" ROUND(IFNULL(rd.num6,0),2) as gmjsNumPerDay, ")
		.append(" ROUND(IFNULL(rd.num7,0),2) as cnjsNumPerDay, ")
		.append(" ROUND(IFNULL(rd.num8,0),2) as xcNum1PerDay, ")
		.append(" ROUND(IFNULL(rd.num9,0),2) as xcNum2PerDay, ")
		.append(" ROUND(IFNULL(rd.num10,0),2) as xcNum3PerDay, ")
		.append(" ROUND(IFNULL(rd.num11,0),2) as xcNum4PerDay, ")
		.append(" ROUND(IFNULL(rd.num12,0),2) as xcNum5PerDay, ")
		.append(" u.name as saleName, ")
		.append(" h.dsmName as dsmName, ")
		.append(" h.region as region, ")
		.append(" h.regionCenter as regionCenter ")
		.append(" from tbl_rhinocort_data rd, tbl_hospital h, tbl_userinfo u, tbl_hos_user hu")
		.append(" where rd.hospitalCode = h.code ")
		.append(" and h.code = hu.hosCode ")
		.append(" and hu.userCode = u.userCode ")
		.append(" and h.isKPI='1' ")
		.append(" and rd.createdate between ? and ? ");
		return dataBean.getJdbcTemplate().query(sb.toString(), new Object[]{new Timestamp(startDate.getTime()),new Timestamp(endDate.getTime())},new RhinocortWeeklyExportDataRowMapper());
	}
	
	@Override
	public List<RhinocortWeeklyExportData> getWeeklyExportNotReportData(Date startDate, Date endDate) throws Exception {
		endDate = new Date(endDate.getTime() + 1* 24 * 60 * 60 * 1000);
		
		StringBuffer sb = new StringBuffer("")
		.append("select h.regionCenter as regionCenter, ")
		.append(" h.region as region, ")
		.append(" h.code as hospitalCode, ")
		.append(" h.name as hospitalName,  ")
		.append(" h.dsmName as dsmName , ")
		.append(" u.name as saleName, ")
		.append(" 0 as entNumPerDay, 0 as arNumPerDay, 0 as rhiARNumPerDay, 0 as prhinitisNumPerDay, 0 as rhiPRhinitisNumPerDay, 0 as gmjsNumPerDay, 0 as cnjsNumPerDay, ")
		.append(" 0 as xcNum1PerDay, ")
		.append(" 0 as xcNum2PerDay, ")
		.append(" 0 as xcNum3PerDay, ")
		.append(" 0 as xcNum4PerDay, ")
		.append(" 0 as xcNum5PerDay ")
		.append(" from tbl_hospital h, tbl_userinfo u, tbl_hos_user hu ")
		.append(" where h.code not in ( ")
		.append(" 	select distinct rd.hospitalCode ")
		.append(" 	from tbl_rhinocort_data rd ")
		.append(" 	where rd.createdate between ? and ? ")
		.append(") ")
		.append(" and h.code = hu.hosCode ")
		.append(" and hu.userCode = u.userCode ")
		.append(" and h.isKPI='1'");
		return dataBean.getJdbcTemplate().query(sb.toString(), new Object[]{new Timestamp(startDate.getTime()),new Timestamp(endDate.getTime())},new RhinocortWeeklyExportDataRowMapper());
	}
}
