package com.chalet.rhinocort.model;
/**
 * @author Chalet
 * @version 创建时间：2014年4月26日 下午9:45:55
 * 类说明
 */

public class RhinocortWeeklyExportData {

	private String duration;
	private String hospitalCode;
	private String hospitalName;
	
	/**
	 * 日均五官科门诊总病人数
	 */
	private double enTNumPerDay;
	
	/**
	 * 日均过敏性鼻炎总病人数
	 */
	private double arNumPerDay;
	
	/**
	 * 过敏性鼻炎病人比例
	 * 日均过敏性鼻炎病人数/日均五官科门诊总病人数
	 */
	private double arRate;
	
	/**
	 * 日均常年性鼻炎病人数
	 */
	private double prhinitisNumPerDay;
	
	/**
	 * 常年性鼻炎病人比例
	 * 日均常年性鼻炎病人数/日均五官科门诊总病人数
	 */
	private double prhinitisRate;
	
	/**
	 * 日均处方雷诺考特的过敏性鼻炎病人数
	 */
	private double rhiARNumPerDay;
	
	/**
	 * 雷诺考特过敏鼻炎患者处方比例
	 * 日均处方雷诺考特的过敏性鼻炎病人数/日均过敏性鼻炎病人数
	 */
	private double rhiARRate;
	
	/**
	 * 日均处方雷诺考特的常年性鼻炎病人数
	 */
	private double rhiPRhinitisNumPerDay;
	
	/**
	 * 雷诺考特常年鼻炎患者处方比例
	 * 日均处方雷诺考特的常年性鼻炎病人数/日均常年性鼻炎病人数
	 */
	private double rhiPRRate;
	
	/**
	 * 雷诺考特处方比例
	 * (日均处方雷诺考特的常年性鼻炎病人数+日均处方雷诺考特的过敏性鼻炎病人数)/(日均常年性鼻炎病人数+日均过敏性鼻炎病人数)
	 */
	private double rhinocortRate;
	
	/**
	 * 日均过敏鼻炎患者中使用鼻用激素的人数.
	 */
	private double gmjsNumPerDay;
	
	/**
	 * 过敏鼻炎患者中使用鼻用激素的比例.
	 * 日均过敏鼻炎患者中使用鼻用激素的人数/日均过敏性鼻炎总病人数
	 */
	private double gmjsRate;
	
	/**
	 * 日均常年鼻炎患者中使用鼻用激素的人数.
	 */
	private double cnjsNumPerDay;
	
	/**
	 * 常年鼻炎患者中使用鼻用激素的比例.
	 * 日均常年鼻炎患者中使用鼻用激素的人数/日均常年性鼻炎病人数
	 */
	private double cnjsRate;
	
	private double xcNum1PerDay;
	private double xcNum2PerDay;
	private double xcNum3PerDay;
	private double xcNum4PerDay;
	private double xcNum5PerDay;
	
	/**
	 * 哮喘合并过敏鼻炎比例
	 */
	private double xcRate1;
	
	/**
	 * 哮喘合并过敏鼻炎中鼻喷激素处方比例
	 */
	private double xcRate2;
	
	/**
	 * 鼻喷激素中雷诺考特处方比例
	 */
	private double xcRate3;
	
	/**
	 * 哮喘合并过敏鼻炎中孟鲁司特类药物处方比例
	 */
	private double xcRate4;
	
	private String saleName;
	private String dsmName;
	private String region;
	private String regionCenter;
	
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getHospitalCode() {
		return hospitalCode;
	}
	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	
	/**
	 * 日均五官科门诊总病人数.
	 */
	public double getEnTNumPerDay() {
		return enTNumPerDay;
	}
	
	/**
	 * 日均五官科门诊总病人数.
	 */
	public void setEnTNumPerDay(double enTNumPerDay) {
		this.enTNumPerDay = enTNumPerDay;
	}
	
	/**
	 * 日均过敏性鼻炎总病人数.
	 * @return
	 */
	public double getArNumPerDay() {
		return arNumPerDay;
	}
	
	/**
	 * 日均过敏性鼻炎总病人数.
	 * @return
	 */
	public void setArNumPerDay(double arNumPerDay) {
		this.arNumPerDay = arNumPerDay;
	}
	
	/**
	 * 过敏性鼻炎病人比例.
	 * 日均过敏性鼻炎病人数/日均五官科门诊总病人数
	 * @return
	 */
	public double getArRate() {
		if( this.enTNumPerDay == 0 ){
			return 0;
		}
		return this.arNumPerDay/this.enTNumPerDay;
	}
	
	/**
	 * 过敏性鼻炎病人比例.
	 * 日均过敏性鼻炎病人数/日均五官科门诊总病人数
	 * @return
	 */
	public void setArRate(double arRate) {
		this.arRate = arRate;
	}
	
	/**
	 * 日均处方雷诺考特的过敏性鼻炎病人数.
	 * @return
	 */
	public double getRhiARNumPerDay() {
		return rhiARNumPerDay;
	}
	
	/**
	 * 日均处方雷诺考特的过敏性鼻炎病人数.
	 * @return
	 */
	public void setRhiARNumPerDay(double rhiARNumPerDay) {
		this.rhiARNumPerDay = rhiARNumPerDay;
	}
	
	/**
	 * 雷诺考特过敏鼻炎患者处方比例.
	 * 日均处方雷诺考特的过敏性鼻炎病人数/日均过敏性鼻炎病人数
	 * @return
	 */
	public double getRhiARRate() {
		if( this.arNumPerDay == 0 ){
			return 0;
		}
		return this.rhiARNumPerDay/this.arNumPerDay;
	}
	
	/**
	 * 雷诺考特过敏鼻炎患者处方比例.
	 * 日均处方雷诺考特的过敏性鼻炎病人数/日均过敏性鼻炎病人数
	 * @return
	 */
	public void setRhiARRate(double rhiARRate) {
		this.rhiARRate = rhiARRate;
	}
	
	/**
	 * 日均常年性鼻炎病人数.
	 * @return
	 */
	public double getPrhinitisNumPerDay() {
		return prhinitisNumPerDay;
	}
	
	/**
	 * 日均常年性鼻炎病人数.
	 * @return
	 */
	public void setPrhinitisNumPerDay(double prhinitisNumPerDay) {
		this.prhinitisNumPerDay = prhinitisNumPerDay;
	}
	
	/**
	 * 日均处方雷诺考特的常年性鼻炎病人数.
	 * @return
	 */
	public double getRhiPRhinitisNumPerDay() {
		return rhiPRhinitisNumPerDay;
	}
	
	/**
	 * 日均处方雷诺考特的常年性鼻炎病人数.
	 * @return
	 */
	public void setRhiPRhinitisNumPerDay(double rhiPRhinitisNumPerDay) {
		this.rhiPRhinitisNumPerDay = rhiPRhinitisNumPerDay;
	}
	public String getSaleName() {
		return saleName;
	}
	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}
	public String getDsmName() {
		return dsmName;
	}
	public void setDsmName(String dsmName) {
		this.dsmName = dsmName;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getRegionCenter() {
		return regionCenter;
	}
	public void setRegionCenter(String regionCenter) {
		this.regionCenter = regionCenter;
	}
	/**
	 * 常年性鼻炎病人比例
	 * 日均常年性鼻炎病人数/日均五官科门诊总病人数
	 * @return
	 */
    public double getPrhinitisRate() {
        if( this.enTNumPerDay == 0 ){
            return 0;
        }
        return this.prhinitisNumPerDay/this.enTNumPerDay;
    }
    /**
     * 常年性鼻炎病人比例
     * @param prhinitisRate
     */
    public void setPrhinitisRate(double prhinitisRate) {
        this.prhinitisRate = prhinitisRate;
    }
    
    /**
     * 雷诺考特常年鼻炎患者处方比例
     * 日均处方雷诺考特的常年性鼻炎病人数/日均常年性鼻炎病人数
     * @return
     */
    public double getRhiPRRate() {
        if( this.prhinitisNumPerDay == 0 ){
            return 0;
        }
        return this.rhiPRhinitisNumPerDay/this.prhinitisNumPerDay;
    }
    
    /**
     * 雷诺考特常年鼻炎患者处方比例
     * @param rhiPRRate
     */
    public void setRhiPRRate(double rhiPRRate) {
        this.rhiPRRate = rhiPRRate;
    }
    
    /**
     * 雷诺考特处方比例
     * (日均处方雷诺考特的常年性鼻炎病人数+日均处方雷诺考特的过敏性鼻炎病人数)/(日均常年性鼻炎病人数+日均过敏性鼻炎病人数)
     */
    public double getRhinocortRate() {
        if( this.prhinitisNumPerDay+this.arNumPerDay == 0){
            return 0;
        }
        return (this.rhiPRhinitisNumPerDay+this.rhiARNumPerDay)/(this.prhinitisNumPerDay+this.arNumPerDay);
    }
    
    /**
     * 雷诺考特处方比例
     * (日均处方雷诺考特的常年性鼻炎病人数+日均处方雷诺考特的过敏性鼻炎病人数)/(日均常年性鼻炎病人数+日均过敏性鼻炎病人数)
     */
    public void setRhinocortRate(double rhinocortRate) {
        this.rhinocortRate = rhinocortRate;
    }
	public double getGmjsNumPerDay() {
		return gmjsNumPerDay;
	}
	public void setGmjsNumPerDay(double gmjsNumPerDay) {
		this.gmjsNumPerDay = gmjsNumPerDay;
	}
	public double getCnjsNumPerDay() {
		return cnjsNumPerDay;
	}
	public void setCnjsNumPerDay(double cnjsNumPerDay) {
		this.cnjsNumPerDay = cnjsNumPerDay;
	}
	public double getGmjsRate() {
		if( arNumPerDay == 0 ){
			return 0;
		}
		return this.gmjsNumPerDay/this.arNumPerDay;
	}
	public void setGmjsRate(double gmjsRate) {
		this.gmjsRate = gmjsRate;
	}
	public double getCnjsRate() {
		if( prhinitisNumPerDay == 0 ){
			return 0;
		}
		return cnjsNumPerDay/prhinitisNumPerDay;
	}
	public void setCnjsRate(double cnjsRate) {
		this.cnjsRate = cnjsRate;
	}
	
	public double getXcRate1() {
		if( xcNum1PerDay == 0 ){
			return 0;
		}
		return xcNum2PerDay/xcNum1PerDay;
	}
	public void setXcRate1(double xcRate1) {
		this.xcRate1 = xcRate1;
	}
	
	public double getXcRate2() {
		if( xcNum2PerDay == 0 ){
			return 0;
		}
		return xcNum3PerDay/xcNum2PerDay;
	}
	public void setXcRate2(double xcRate2) {
		this.xcRate2 = xcRate2;
	}
	
	public double getXcRate3() {
		if( xcNum3PerDay == 0 ){
			return 0;
		}
		return xcNum4PerDay/xcNum3PerDay;
	}
	public void setXcRate3(double xcRate3) {
		this.xcRate3 = xcRate3;
	}
	
	public double getXcRate4() {
		if( xcNum2PerDay == 0 ){
			return 0;
		}
		return xcNum5PerDay/xcNum2PerDay;
	}
	public void setXcRate4(double xcRate4) {
		this.xcRate4 = xcRate4;
	}
	
	public double getXcNum1PerDay() {
		return xcNum1PerDay;
	}
	public void setXcNum1PerDay(double xcNum1PerDay) {
		this.xcNum1PerDay = xcNum1PerDay;
	}
	public double getXcNum2PerDay() {
		return xcNum2PerDay;
	}
	public void setXcNum2PerDay(double xcNum2PerDay) {
		this.xcNum2PerDay = xcNum2PerDay;
	}
	public double getXcNum3PerDay() {
		return xcNum3PerDay;
	}
	public void setXcNum3PerDay(double xcNum3PerDay) {
		this.xcNum3PerDay = xcNum3PerDay;
	}
	public double getXcNum4PerDay() {
		return xcNum4PerDay;
	}
	public void setXcNum4PerDay(double xcNum4PerDay) {
		this.xcNum4PerDay = xcNum4PerDay;
	}
	public double getXcNum5PerDay() {
		return xcNum5PerDay;
	}
	public void setXcNum5PerDay(double xcNum5PerDay) {
		this.xcNum5PerDay = xcNum5PerDay;
	}
}
