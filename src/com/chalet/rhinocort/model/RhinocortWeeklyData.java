package com.chalet.rhinocort.model;
/**
 * @author Chalet
 * @version 创建时间：2014年4月16日 下午9:24:38
 * 类说明
 */

public class RhinocortWeeklyData {

	private String name;
	private String level;
	private double inRate;
	/**
	 * 五官科门诊病人数.
	 */
	private double num1;
	/**
	 * 过敏性鼻炎病人数.
	 */
	private double num2;
	/**
	 * 处方雷诺考特的过敏性鼻炎病人数.
	 */
	private double num3;
	/**
	 * 常年性鼻炎病人数.
	 */
	private double num4;
	/**
	 * 处方雷诺考特的常年性鼻炎病人数.
	 */
	private double num5;
	/**
	 * 过敏鼻炎患者中使用鼻用激素的人数.
	 */
	private double num6;
	/**
	 * 常年鼻炎患者中使用鼻用激素的人数.
	 */
	private double num7;
	
	private double num2Rate;
	private double num3Rate;
	
	public double getInRate() {
		return inRate;
	}
	public void setInRate(double inRate) {
		this.inRate = inRate;
	}
	public double getNum1() {
		return num1;
	}
	public void setNum1(double num1) {
		this.num1 = num1;
	}
	public double getNum2() {
		return num2;
	}
	public void setNum2(double num2) {
		this.num2 = num2;
	}
	public double getNum3() {
		return num3;
	}
	public void setNum3(double num3) {
		this.num3 = num3;
	}
	public double getNum4() {
		return num4;
	}
	public void setNum4(double num4) {
		this.num4 = num4;
	}
	public double getNum5() {
		return num5;
	}
	public void setNum5(double num5) {
		this.num5 = num5;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
    public double getNum2Rate() {
    	if( 0 == (num1+num2+num3+num4+num5) ){
    		return 0;
    	}else{
    		return num2/(num1+num2+num3+num4+num5);
    	}
    }
    public double getNum3Rate() {
    	if( num2 == 0 ){
    		return 0;
    	}else{
    		return num3/num2;
    	}
    }
	public double getNum6() {
		return num6;
	}
	public void setNum6(double num6) {
		this.num6 = num6;
	}
	public double getNum7() {
		return num7;
	}
	public void setNum7(double num7) {
		this.num7 = num7;
	}
}
