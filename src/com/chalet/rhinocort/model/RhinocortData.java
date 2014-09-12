package com.chalet.rhinocort.model;

import java.util.Date;

/**
 * @author Chalet
 * @version 创建时间：2013年11月27日 下午11:24:22
 * 类说明
 */

public class RhinocortData {

	private int dataId;
	private String hospitalName;
	private String hospitalCode;
	/**
	 * 五官科门诊病人数.
	 */
	private int num1;
	/**
	 * 过敏性鼻炎病人数.
	 */
	private int num2;
	/**
	 * 处方雷诺考特的过敏性鼻炎病人数.
	 */
	private int num3;
	/**
	 * 常年性鼻炎病人数.
	 */
	private int num4;
	/**
	 * 处方雷诺考特的常年性鼻炎病人数.
	 */
	private int num5;
	/**
	 * 过敏鼻炎患者中使用鼻用激素的人数.
	 */
	private int num6;
	/**
	 * 常年鼻炎患者中使用鼻用激素的人数.
	 */
	private int num7;
    private Date createdate;
    private Date updatedate;
    private String dsmName;
    private String salesName;
	
	public int getDataId() {
		return dataId;
	}
	public void setDataId(int dataId) {
		this.dataId = dataId;
	}
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
    public String getHospitalCode() {
        return hospitalCode;
    }
    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }
    public String getDsmName() {
        return dsmName;
    }
    public void setDsmName(String dsmName) {
        this.dsmName = dsmName;
    }
	public int getNum1() {
		return num1;
	}
	public void setNum1(int num1) {
		this.num1 = num1;
	}
	public int getNum2() {
		return num2;
	}
	public void setNum2(int num2) {
		this.num2 = num2;
	}
	public int getNum3() {
		return num3;
	}
	public void setNum3(int num3) {
		this.num3 = num3;
	}
	public int getNum4() {
		return num4;
	}
	public void setNum4(int num4) {
		this.num4 = num4;
	}
	public int getNum5() {
		return num5;
	}
	public void setNum5(int num5) {
		this.num5 = num5;
	}
	public Date getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
	public String getSalesName() {
		return salesName;
	}
	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}
	public int getNum6() {
		return num6;
	}
	public void setNum6(int num6) {
		this.num6 = num6;
	}
	public int getNum7() {
		return num7;
	}
	public void setNum7(int num7) {
		this.num7 = num7;
	}
}
