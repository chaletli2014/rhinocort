package com.chalet.rhinocort.utils;

import org.apache.log4j.Logger;

/**
 * @author Chalet
 * @version 创建时间：2013年11月24日 下午3:38:55
 * 类说明
 */

public class StringUtils {
	
	private static Logger logger = Logger.getLogger(StringUtils.class);

	public static int getIntegerFromString(String value){
		int result = 0;
		if( null != value && !"".equalsIgnoreCase(value) ){
			try{
				result = Integer.parseInt(value);
			}catch(Exception e){
				logger.error("fail to convert string to int,",e);
			}
		}
		return result;
	}
	
	public static double getDoubleFromString(String value){
	    double result = 0;
	    if( null != value && !"".equalsIgnoreCase(value) ){
	        try{
	            result = Double.parseDouble(value);
	        }catch(Exception e){
	            logger.error("fail to convert string to int,",e);
	        }
	    }
	    return result;
	}
	
	public static String getTheZHValueOfRegionCenter(String regionCenter){
	    
	    if( RhinocortAttributes.BR_NAME_CENTRAL.equalsIgnoreCase(regionCenter) ){
            return RhinocortAttributes.BR_NAME_CENTRAL_ZH;
        }else if( RhinocortAttributes.BR_NAME_EAST1.equalsIgnoreCase(regionCenter) ){
            return RhinocortAttributes.BR_NAME_EAST1_ZH;
        }else if( RhinocortAttributes.BR_NAME_EAST2.equalsIgnoreCase(regionCenter) ){
            return RhinocortAttributes.BR_NAME_EAST2_ZH;
        }else if( RhinocortAttributes.BR_NAME_NORTH.equalsIgnoreCase(regionCenter) ){
            return RhinocortAttributes.BR_NAME_NORTH_ZH;
        }else if( RhinocortAttributes.BR_NAME_SOUTH.equalsIgnoreCase(regionCenter) ){
            return RhinocortAttributes.BR_NAME_SOUTH_ZH;
        }else if( RhinocortAttributes.BR_NAME_WEST.equalsIgnoreCase(regionCenter) ){
            return RhinocortAttributes.BR_NAME_WEST_ZH;
        }else{
            return regionCenter;
        }
	}
}
