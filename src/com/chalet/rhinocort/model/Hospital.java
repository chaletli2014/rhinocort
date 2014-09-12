package com.chalet.rhinocort.model;

public class Hospital {

    private int id;
    private String name;
    private String code;
    private String city;
    private String province;
    private String region;
    private String regionCenter;
    private String dsmCode;
    private String dsmName;
    private String saleCode;
    private String saleName;
    private String level;
    private String isKPI;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getDsmCode() {
        return dsmCode;
    }
    public void setDsmCode(String dsmCode) {
        this.dsmCode = dsmCode;
    }
    public String getDsmName() {
        return dsmName;
    }
    public void setDsmName(String dsmName) {
        this.dsmName = dsmName;
    }
    public String getSaleName() {
        return saleName;
    }
    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }
    public String getSaleCode() {
        return saleCode;
    }
    public void setSaleCode(String saleCode) {
        this.saleCode = saleCode;
    }
	public String getRegionCenter() {
		return regionCenter;
	}
	public void setRegionCenter(String regionCenter) {
		this.regionCenter = regionCenter;
	}
	public String getIsKPI() {
		return isKPI;
	}
	public void setIsKPI(String isKPI) {
		this.isKPI = isKPI;
	}
}
