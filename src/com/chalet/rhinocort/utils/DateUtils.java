package com.chalet.rhinocort.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    
    private static SimpleDateFormat formatter_1 = new SimpleDateFormat("yyyy-MM-dd");
    
	public static Date getTheBeginDateOfRecordDate(Date recordDate){
        // 0 sunday
        Date beginDate = recordDate;
        if( recordDate.getDay() == 0 ){
        	beginDate = new Date(recordDate.getTime() - 6 * 24 * 60 * 60 * 1000);
        }else{
        	beginDate = new Date(recordDate.getTime() - (recordDate.getDay()-1) * 24 * 60 * 60 * 1000);
        }
        beginDate = new Date(beginDate.getYear(),beginDate.getMonth(),beginDate.getDate());
        return beginDate;
    }
	
	public static String getTheBeginDateOfRecordDateOfFormatter1(Date recordDate){
	    return formatter_1.format(getTheBeginDateOfRecordDate(recordDate));
	}
	
	public static Date getTheEndDateOfRecordDate(Date recordDate){
		return new Date(getTheBeginDateOfRecordDate(recordDate).getTime() + 6 * 24 * 60 * 60 * 1000);
	}

    public static Date getLastBeginDateOfRecordDate(Date recordDate){
        // 0 sunday
        Date beginDate = recordDate;
        if( recordDate.getDay() == 0 ){
            beginDate = new Date(recordDate.getTime() - (6+7) * 24 * 60 * 60 * 1000);
        }else{
            beginDate = new Date(recordDate.getTime() - ((recordDate.getDay()-1)+7) * 24 * 60 * 60 * 1000);
        }
        beginDate = new Date(beginDate.getYear(),beginDate.getMonth(),beginDate.getDate());
        return beginDate;
    }
    
	public static String getLastBeginDateOfFormatter1(Date recordDate){
	    // 0 sunday
	    Date lastBeginDate = getLastBeginDateOfRecordDate(recordDate);
	    return formatter_1.format(lastBeginDate);
	}
	
	public static String getLastEndDateOfFormatter1(Date recordDate){
	    Date lastBeginDate = getLastBeginDateOfRecordDate(recordDate);
	    Date lastEndDate = new Date(lastBeginDate.getTime() + 6 * 24 * 60 * 60 * 1000);
	    return formatter_1.format(lastEndDate);
	}
	
	public static String getLastEndDate4Report(Date recordDate){
	    Date lastBeginDate = getLastBeginDateOfRecordDate(recordDate);
	    Date lastEndDate = new Date(lastBeginDate.getTime() + 7 * 24 * 60 * 60 * 1000);
	    return formatter_1.format(lastEndDate);
	}
}
