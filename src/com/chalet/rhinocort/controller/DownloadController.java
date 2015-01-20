package com.chalet.rhinocort.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chalet.rhinocort.model.RhinocortData;
import com.chalet.rhinocort.model.RhinocortWeeklyExportData;
import com.chalet.rhinocort.service.RhinocortService;
import com.chalet.rhinocort.utils.DateUtils;

/**
 * @author Chalet
 * @version 创建时间：2014年4月12日 下午11:22:10
 * 类说明
 */

@Controller
public class DownloadController {
	
	private Logger logger = Logger.getLogger(DownloadController.class);
	
    @Autowired
    @Qualifier("rhinocortService")
    private RhinocortService rhinocortService;

	@RequestMapping("/doDownloadDailyData")
    public String doDownloadDailyData(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	logger.info("download the daily data..");
    	FileOutputStream fOut = null;
    	String fileName = null;
        try{
            String chooseDate = request.getParameter("chooseDate");
            String chooseDate_end = request.getParameter("chooseDate_end");
            
            SimpleDateFormat exportdateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            if( null == chooseDate || "".equalsIgnoreCase(chooseDate) || null == chooseDate_end || "".equalsIgnoreCase(chooseDate_end) ){
            	logger.error(String.format("the choose date is %s, the choose end date is %s", chooseDate,chooseDate_end));
            }else{
            	SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
            	Date chooseDate_d = simpledateformat.parse(chooseDate);
            	Date chooseDate_end_d = simpledateformat.parse(chooseDate_end);
            	
            	logger.info(String.format("begin to get the daily data from %s to %s", chooseDate,chooseDate_end));
            		List<RhinocortData> rhinocortData = rhinocortService.getRhinocortDataByDate(chooseDate_d, chooseDate_end_d);
            		
            		File resDir = new File(request.getRealPath("/") + "dailyReport/");
            		if( !resDir.exists() ){
            			resDir.mkdir();
            		}
            		fileName = "dailyReport/原始数据-" + chooseDate +" - " + chooseDate_end + ".xls";
            		File tmpFile = new File(request.getRealPath("/") + fileName);
            		if( !tmpFile.exists() ){
            			tmpFile.createNewFile();
            		}
            		
            		fOut = new FileOutputStream(tmpFile);
            		
            		HSSFWorkbook workbook = new HSSFWorkbook();
            		workbook.createSheet("日报");
                    HSSFSheet sheet = workbook.getSheetAt(0);
                    int currentRowNum = 0;
                    
                    //build the header
                    HSSFRow row = sheet.createRow(currentRowNum++);
                    row.createCell(0, XSSFCell.CELL_TYPE_STRING).setCellValue("编号");
                    row.createCell(1, XSSFCell.CELL_TYPE_STRING).setCellValue("录入日期");
                    row.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("医院编号");
                    row.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue("医院名称");
                    row.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue("五官科门诊病人数");
                    row.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue("过敏性鼻炎病人数");
                    row.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue("处方雷诺考特的过敏性鼻炎病人数");
                    row.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue("过敏鼻炎患者中使用鼻用激素的人数");
                    row.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue("常年性鼻炎病人数");
                    row.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue("处方雷诺考特的常年性鼻炎病人数");
                    row.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue("常年鼻炎患者中使用鼻用激素的人数");
                    row.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue("哮喘患者数");
                    row.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue("哮喘合并过敏鼻炎患者数");
                    row.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue("哮喘合并过敏鼻炎使用鼻喷激素患者数");
                    row.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue("哮喘合并过敏鼻炎使用雷诺考特患者数");
                    row.createCell(15, XSSFCell.CELL_TYPE_STRING).setCellValue("哮喘合并过敏鼻炎使用孟鲁司特类药物患者数");
                    row.createCell(16, XSSFCell.CELL_TYPE_STRING).setCellValue("销售代表姓名");
                    row.createCell(17, XSSFCell.CELL_TYPE_STRING).setCellValue("所属DSM");
                    
                    HSSFCellStyle numberCellStyle = workbook.createCellStyle();
                    numberCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
                    
                    for( RhinocortData data : rhinocortData ){
                    	row = sheet.createRow(currentRowNum++);
                    	row.createCell(0, XSSFCell.CELL_TYPE_NUMERIC).setCellValue(currentRowNum-1);
                        row.createCell(1, XSSFCell.CELL_TYPE_STRING).setCellValue(exportdateformat.format(data.getCreatedate()));
                        row.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue(data.getHospitalCode());
                        row.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(data.getHospitalName());
                        
                        HSSFCell num1Cell = row.createCell(4, XSSFCell.CELL_TYPE_NUMERIC);
                        num1Cell.setCellValue(data.getNum1());
                        num1Cell.setCellStyle(numberCellStyle);
                        
                        HSSFCell num2Cell = row.createCell(5, XSSFCell.CELL_TYPE_NUMERIC);
                        num2Cell.setCellValue(data.getNum2());
                        num2Cell.setCellStyle(numberCellStyle);
                        
                        HSSFCell num3Cell = row.createCell(6, XSSFCell.CELL_TYPE_NUMERIC);
                        num3Cell.setCellValue(data.getNum3());
                        num3Cell.setCellStyle(numberCellStyle);
                        
                		HSSFCell num6Cell = row.createCell(7, XSSFCell.CELL_TYPE_NUMERIC);
                		num6Cell.setCellValue(data.getNum6());
                		num6Cell.setCellStyle(numberCellStyle);
                		
                		HSSFCell num4Cell = row.createCell(8, XSSFCell.CELL_TYPE_NUMERIC);
                		num4Cell.setCellValue(data.getNum4());
                		num4Cell.setCellStyle(numberCellStyle);
                		
                		HSSFCell num5Cell = row.createCell(9, XSSFCell.CELL_TYPE_NUMERIC);
                		num5Cell.setCellValue(data.getNum5());
                		num5Cell.setCellStyle(numberCellStyle);
                		
                		HSSFCell num7Cell = row.createCell(10, XSSFCell.CELL_TYPE_NUMERIC);
                		num7Cell.setCellValue(data.getNum7());
                		num7Cell.setCellStyle(numberCellStyle);
                		
                		HSSFCell num8Cell = row.createCell(11, XSSFCell.CELL_TYPE_NUMERIC);
                		num8Cell.setCellValue(data.getNum8());
                		num8Cell.setCellStyle(numberCellStyle);
                		
                		HSSFCell num9Cell = row.createCell(12, XSSFCell.CELL_TYPE_NUMERIC);
                		num9Cell.setCellValue(data.getNum9());
                		num9Cell.setCellStyle(numberCellStyle);
                		
                		HSSFCell num10Cell = row.createCell(13, XSSFCell.CELL_TYPE_NUMERIC);
                		num10Cell.setCellValue(data.getNum10());
                		num10Cell.setCellStyle(numberCellStyle);
                		
                		HSSFCell num11Cell = row.createCell(14, XSSFCell.CELL_TYPE_NUMERIC);
                		num11Cell.setCellValue(data.getNum11());
                		num11Cell.setCellStyle(numberCellStyle);
                		
                		HSSFCell num12Cell = row.createCell(15, XSSFCell.CELL_TYPE_NUMERIC);
                		num12Cell.setCellValue(data.getNum12());
                		num12Cell.setCellStyle(numberCellStyle);
                		
                        row.createCell(16, XSSFCell.CELL_TYPE_STRING).setCellValue(data.getSalesName());
                        row.createCell(17, XSSFCell.CELL_TYPE_STRING).setCellValue(data.getDsmName());
                    }
                    workbook.write(fOut);
            }
        }catch(Exception e){
            logger.error("fail to download the file,",e);
        }finally{
            if( fOut != null ){
                fOut.close();
            }
        }
        request.getSession().setAttribute("dataFile", fileName);
        return "redirect:showUploadData";
    }
	
	@RequestMapping("/doDownloadWeeklyData")
	public String doDownloadWeeklyData(HttpServletRequest request, HttpServletResponse response) throws IOException{
		logger.info("download the weekly data..");
		FileOutputStream fOut = null;
		String fileName = null;
		try{
			String chooseDate = request.getParameter("chooseDate");
			
			SimpleDateFormat exportdateformat = new SimpleDateFormat("MM.dd");
			SimpleDateFormat dateTitleformat = new SimpleDateFormat("yyyy年MM月dd日");
			if( null == chooseDate || "".equalsIgnoreCase(chooseDate) ){
				logger.error(String.format("the choose date is %s", chooseDate));
			}else{
				SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
				Date chooseDate_d = simpledateformat.parse(chooseDate);
				
				Date startDate = DateUtils.getTheBeginDateOfRecordDate(chooseDate_d);
				Date endDate = DateUtils.getTheEndDateOfRecordDate(chooseDate_d);
				
				String duration = exportdateformat.format(startDate) +" - " + exportdateformat.format(endDate);
				
				String dateTitle = "报告日期：" + dateTitleformat.format(startDate) +" - " + dateTitleformat.format(endDate);
				
				logger.info(String.format("begin to get the weekly data %s", duration));
				List<RhinocortWeeklyExportData> exportWeeklyData = rhinocortService.getWeeklyExportReportData(startDate,endDate);
				List<RhinocortWeeklyExportData> exportNotReportWeeklyData = rhinocortService.getWeeklyExportNotReportData(startDate,endDate);
				
				File resDir = new File(request.getRealPath("/") + "weeklyExportReport/");
				if( !resDir.exists() ){
					resDir.mkdir();
				}
				fileName = "weeklyExportReport/周报数据-" + duration + ".xls";
				File tmpFile = new File(request.getRealPath("/") + fileName);
				if( !tmpFile.exists() ){
					tmpFile.createNewFile();
				}
				
				fOut = new FileOutputStream(tmpFile);
				
				HSSFWorkbook workbook = new HSSFWorkbook();
                
                HSSFFont top1FontStyle = workbook.createFont();
                top1FontStyle.setColor(HSSFColor.WHITE.index);
                top1FontStyle.setFontName("楷体");
                top1FontStyle.setFontHeightInPoints((short)10);
                top1FontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                
                HSSFFont top2FontStyle = workbook.createFont();
                top2FontStyle.setColor(HSSFColor.BLACK.index);
                top2FontStyle.setFontName("楷体");
                top2FontStyle.setFontHeightInPoints((short)10);
                
                HSSFFont englishFont = workbook.createFont();
                englishFont.setColor(HSSFColor.BLACK.index);
                englishFont.setFontName("Times New Roman");
                englishFont.setFontHeightInPoints((short)10);
                
                HSSFPalette palette = workbook.getCustomPalette();
                palette.setColorAtIndex((short)63, (byte) (83), (byte) (142), (byte) (213));
                palette.setColorAtIndex((short)62, (byte) (197), (byte) (217), (byte) (241));
                
                HSSFCellStyle topStyle=workbook.createCellStyle();
                topStyle.setFillForegroundColor((short)63);
                topStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                topStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                topStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                topStyle.setFont(top1FontStyle);
                topStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                topStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
                topStyle.setLeftBorderColor(HSSFColor.BLACK.index);
                topStyle.setRightBorderColor(HSSFColor.BLACK.index);
                
                HSSFCellStyle top2Style=workbook.createCellStyle();
                top2Style.setFillForegroundColor((short)62);
                top2Style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                top2Style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                top2Style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                top2Style.setFont(top2FontStyle);
                top2Style.setWrapText(true);
                top2Style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                top2Style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                top2Style.setLeftBorderColor(HSSFColor.BLACK.index);
                top2Style.setRightBorderColor(HSSFColor.BLACK.index);
                
                HSSFCellStyle englishStyle=workbook.createCellStyle();
                englishStyle.setFillForegroundColor((short)62);
                englishStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                englishStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                englishStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                englishStyle.setFont(englishFont);
                englishStyle.setWrapText(true);
                englishStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                englishStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
                englishStyle.setLeftBorderColor(HSSFColor.BLACK.index);
                englishStyle.setRightBorderColor(HSSFColor.BLACK.index);
                
                HSSFCellStyle valueEnglishStyle = workbook.createCellStyle();
                valueEnglishStyle.setFont(englishFont);
                
                HSSFCellStyle valueEnglishBorderStyle = workbook.createCellStyle();
                valueEnglishBorderStyle.setFont(englishFont);
                valueEnglishBorderStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
                valueEnglishBorderStyle.setRightBorderColor(HSSFColor.BLACK.index);
                
                HSSFCellStyle valueNameStyle = workbook.createCellStyle();
                valueNameStyle.setFont(top2FontStyle);
                
                HSSFCellStyle valueNameBorderStyle = workbook.createCellStyle();
                valueNameBorderStyle.setFont(top2FontStyle);
                valueNameBorderStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
                valueNameBorderStyle.setRightBorderColor(HSSFColor.BLACK.index);
                
                populateFirstWeeklyReportSheet(workbook, topStyle, top2Style, englishStyle, valueNameStyle, valueEnglishStyle, valueNameBorderStyle, englishFont, exportWeeklyData, dateTitle);
                populateSecondWeeklyReportSheet(workbook, topStyle, top2Style, englishStyle, valueNameStyle, valueEnglishStyle, valueNameBorderStyle, englishFont, exportNotReportWeeklyData, dateTitle);
				workbook.write(fOut);
			}
		}catch(Exception e){
			logger.error("fail to download the file,",e);
		}finally{
			if( fOut != null ){
				fOut.close();
			}
		}
		request.getSession().setAttribute("weeklyDataFile", fileName);
		return "redirect:showUploadData";
	}
	
	private void populateFirstWeeklyReportSheet(HSSFWorkbook workbook, 
			HSSFCellStyle topStyle, 
			HSSFCellStyle top2Style,
			HSSFCellStyle englishStyle,
			HSSFCellStyle valueNameStyle,
			HSSFCellStyle valueEnglishStyle,
			HSSFCellStyle valueNameBorderStyle,
			HSSFFont englishFont,
			List<RhinocortWeeklyExportData> exportWeeklyData,
			String dateTitle){
		HSSFSheet sheet = workbook.createSheet("周报");
        int currentRowNum = 0;
        
        //build the header
        HSSFRow row = sheet.createRow(currentRowNum++);
        
        HSSFCell topDateCell = row.createCell(0,XSSFCell.CELL_TYPE_STRING);
        topDateCell.setCellValue(dateTitle);
        topDateCell.setCellStyle(valueEnglishStyle);
        sheet.addMergedRegion(new Region(0, (short)0, 0, (short)2));
        
        row = sheet.createRow(currentRowNum++);
        
        row.createCell(0, XSSFCell.CELL_TYPE_STRING).setCellValue("医院以及区域基本情况");
        row.createCell(1, XSSFCell.CELL_TYPE_STRING).setCellValue("");
        row.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("");
        row.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue("");
        sheet.addMergedRegion(new Region(1, (short)0, 1, (short)3));
        row.getCell(0).setCellStyle(topStyle);
        
        row.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue("五官科门诊情况");
        row.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue("");
        row.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue("");
        row.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue("");
        row.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue("");
        row.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue("");
        row.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue("");
        row.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue("");
        row.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue("");
        sheet.addMergedRegion(new Region(1, (short)4, 1, (short)12));
        row.getCell(4).setCellStyle(topStyle);
        
        row.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue("雷诺考特处方情况");
        row.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue("");
        row.createCell(15, XSSFCell.CELL_TYPE_STRING).setCellValue("");
        row.createCell(16, XSSFCell.CELL_TYPE_STRING).setCellValue("");
        row.createCell(17, XSSFCell.CELL_TYPE_STRING).setCellValue("");
        sheet.addMergedRegion(new Region(1, (short)13, 1, (short)17));
        row.getCell(13).setCellStyle(topStyle);
        
        row.createCell(18, XSSFCell.CELL_TYPE_STRING).setCellValue("哮喘合并过敏鼻炎情况");
        row.createCell(19, XSSFCell.CELL_TYPE_STRING).setCellValue("");
        row.createCell(20, XSSFCell.CELL_TYPE_STRING).setCellValue("");
        row.createCell(21, XSSFCell.CELL_TYPE_STRING).setCellValue("");
        sheet.addMergedRegion(new Region(1, (short)18, 1, (short)21));
        row.getCell(18).setCellStyle(topStyle);
        
        row.createCell(22, XSSFCell.CELL_TYPE_STRING).setCellValue("销售同事情况");
        row.createCell(23, XSSFCell.CELL_TYPE_STRING).setCellValue("");
        sheet.addMergedRegion(new Region(1, (short)22, 1, (short)23));
        row.getCell(22).setCellStyle(topStyle);
        
        row = sheet.createRow(currentRowNum++);
        HSSFCell headerBRNameCell = row.createCell(0, XSSFCell.CELL_TYPE_STRING);
        headerBRNameCell.setCellValue("BR Name");
        headerBRNameCell.setCellStyle(englishStyle);
        
        HSSFCell headerRSMCell = row.createCell(1, XSSFCell.CELL_TYPE_STRING);
        headerRSMCell.setCellValue("RSM");
        headerRSMCell.setCellStyle(englishStyle);
        
        HSSFCell headerHosCodeCell = row.createCell(2, XSSFCell.CELL_TYPE_STRING);
        headerHosCodeCell.setCellValue("医院编号");
        headerHosCodeCell.setCellStyle(top2Style);
        
        HSSFCell headerHosNameCell = row.createCell(3, XSSFCell.CELL_TYPE_STRING);
        headerHosNameCell.setCellValue("KPI医院名称");
        headerHosNameCell.setCellStyle(top2Style);
        
        HSSFCell headerNum1Cell = row.createCell(4, XSSFCell.CELL_TYPE_STRING);
        headerNum1Cell.setCellValue("日均五官科门诊总病人数");
        headerNum1Cell.setCellStyle(top2Style);
        
        HSSFCell headerNum2Cell = row.createCell(5, XSSFCell.CELL_TYPE_STRING);
        headerNum2Cell.setCellValue("日均过敏性鼻炎病人数");
        headerNum2Cell.setCellStyle(top2Style);
        
        HSSFCell headerRate1Cell = row.createCell(6, XSSFCell.CELL_TYPE_STRING);
        headerRate1Cell.setCellValue("过敏性鼻炎病人比例");
        headerRate1Cell.setCellStyle(top2Style);
        
        HSSFCell headerGMJSNumCell = row.createCell(7, XSSFCell.CELL_TYPE_STRING);
        headerGMJSNumCell.setCellValue("日均过敏鼻炎患者中使用鼻用激素的人数");
        headerGMJSNumCell.setCellStyle(top2Style);
        
        HSSFCell headerGMJSRateCell = row.createCell(8, XSSFCell.CELL_TYPE_STRING);
        headerGMJSRateCell.setCellValue("过敏鼻炎患者中使用鼻用激素的比例");
        headerGMJSRateCell.setCellStyle(top2Style);
        
        HSSFCell headerNum3Cell = row.createCell(9, XSSFCell.CELL_TYPE_STRING);
        headerNum3Cell.setCellValue("日均常年性鼻炎病人数");
        headerNum3Cell.setCellStyle(top2Style);
        
        HSSFCell headerRate2Cell = row.createCell(10, XSSFCell.CELL_TYPE_STRING);
        headerRate2Cell.setCellValue("常年性鼻炎病人比例");
        headerRate2Cell.setCellStyle(top2Style);
        
        HSSFCell headerCNJSNumCell = row.createCell(11, XSSFCell.CELL_TYPE_STRING);
        headerCNJSNumCell.setCellValue("日均常年鼻炎患者中使用鼻用激素的人数");
        headerCNJSNumCell.setCellStyle(top2Style);
        
        HSSFCell headerCNJSRateCell = row.createCell(12, XSSFCell.CELL_TYPE_STRING);
        headerCNJSRateCell.setCellValue("常年鼻炎患者中使用鼻用激素的比例");
        headerCNJSRateCell.setCellStyle(top2Style);
        
        HSSFCell headerNum4Cell = row.createCell(13, XSSFCell.CELL_TYPE_STRING);
        headerNum4Cell.setCellValue("日均处方雷诺考特的过敏性鼻炎病人数");
        headerNum4Cell.setCellStyle(top2Style);
        
        HSSFCell headerRate3Cell = row.createCell(14, XSSFCell.CELL_TYPE_STRING);
        headerRate3Cell.setCellValue("雷诺考特过敏鼻炎患者处方比例");
        headerRate3Cell.setCellStyle(top2Style);
        
        HSSFCell headerNum5Cell = row.createCell(15, XSSFCell.CELL_TYPE_STRING);
        headerNum5Cell.setCellValue("日均处方雷诺考特的常年性鼻炎病人数");
        headerNum5Cell.setCellStyle(top2Style);
        
        HSSFCell headerRate4Cell = row.createCell(16, XSSFCell.CELL_TYPE_STRING);
        headerRate4Cell.setCellValue("雷诺考特常年鼻炎患者处方比例");
        headerRate4Cell.setCellStyle(top2Style);
        
        HSSFCell headerRate5Cell = row.createCell(17, XSSFCell.CELL_TYPE_STRING);
        headerRate5Cell.setCellValue("雷诺考特处方比例");
        headerRate5Cell.setCellStyle(top2Style);
        
        HSSFCell headerRate6Cell = row.createCell(18, XSSFCell.CELL_TYPE_STRING);
        headerRate6Cell.setCellValue("哮喘合并过敏鼻炎比例");
        headerRate6Cell.setCellStyle(top2Style);
        
        HSSFCell headerRate7Cell = row.createCell(19, XSSFCell.CELL_TYPE_STRING);
        headerRate7Cell.setCellValue("哮喘合并过敏鼻炎中鼻喷激素处方比例");
        headerRate7Cell.setCellStyle(top2Style);
        
        HSSFCell headerRate8Cell = row.createCell(20, XSSFCell.CELL_TYPE_STRING);
        headerRate8Cell.setCellValue("鼻喷激素中雷诺考特处方比例");
        headerRate8Cell.setCellStyle(top2Style);
        
        HSSFCell headerRate9Cell = row.createCell(21, XSSFCell.CELL_TYPE_STRING);
        headerRate9Cell.setCellValue("哮喘合并过敏鼻炎中孟鲁司特类药物处方比例");
        headerRate9Cell.setCellStyle(top2Style);
        
        HSSFCell headerSalesCell = row.createCell(22, XSSFCell.CELL_TYPE_STRING);
        headerSalesCell.setCellValue("销售代表姓名");
        headerSalesCell.setCellStyle(top2Style);
        
        HSSFCell headerDSMNameCell = row.createCell(23, XSSFCell.CELL_TYPE_STRING);
        headerDSMNameCell.setCellValue("所属DSM");
        headerDSMNameCell.setCellStyle(top2Style);
        
        
        int hosColumnWidth = 12;
        int dataColumnWidth = 14;
        int salesColumnWidth = 13;
        
        sheet.setColumnWidth(0, hosColumnWidth*256);
        sheet.setColumnWidth(1, hosColumnWidth*256);
        sheet.setColumnWidth(2, hosColumnWidth*256);
        sheet.setColumnWidth(3, 44*256);
        sheet.setColumnWidth(4, dataColumnWidth*256);
        sheet.setColumnWidth(5, dataColumnWidth*256);
        sheet.setColumnWidth(6, dataColumnWidth*256);
        sheet.setColumnWidth(7, dataColumnWidth*256);
        sheet.setColumnWidth(8, dataColumnWidth*256);
        sheet.setColumnWidth(9, dataColumnWidth*256);
        sheet.setColumnWidth(10, dataColumnWidth*256);
        sheet.setColumnWidth(11, dataColumnWidth*256);
        sheet.setColumnWidth(12, dataColumnWidth*256);
        sheet.setColumnWidth(13, dataColumnWidth*256);
        sheet.setColumnWidth(14, dataColumnWidth*256);
        sheet.setColumnWidth(15, dataColumnWidth*256);
        sheet.setColumnWidth(16, dataColumnWidth*256);
        sheet.setColumnWidth(17, dataColumnWidth*256);
        sheet.setColumnWidth(18, dataColumnWidth*256);
        sheet.setColumnWidth(19, dataColumnWidth*256);
        sheet.setColumnWidth(20, dataColumnWidth*256);
        sheet.setColumnWidth(21, dataColumnWidth*256);
        sheet.setColumnWidth(22, salesColumnWidth*256);
        sheet.setColumnWidth(23, salesColumnWidth*256);
        
        HSSFCellStyle numberCellStyle = workbook.createCellStyle();
        numberCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
        numberCellStyle.setFont(englishFont);
		
		HSSFCellStyle percentCellStyle = workbook.createCellStyle();
        percentCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0%"));
        percentCellStyle.setFont(englishFont);
        
        HSSFCellStyle percentBorderStyle = workbook.createCellStyle();
        percentBorderStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0%"));
        percentBorderStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        percentBorderStyle.setRightBorderColor(HSSFColor.BLACK.index);
        percentBorderStyle.setFont(englishFont);
		
		for( RhinocortWeeklyExportData data : exportWeeklyData ){
			row = sheet.createRow(currentRowNum++);
			
			HSSFCell rsdCell = row.createCell(0, XSSFCell.CELL_TYPE_STRING);
			rsdCell.setCellValue(data.getRegionCenter());
			rsdCell.setCellStyle(valueEnglishStyle);
			
			HSSFCell rsmCell = row.createCell(1, XSSFCell.CELL_TYPE_STRING);
			rsmCell.setCellValue(data.getRegion());
			rsmCell.setCellStyle(valueEnglishStyle);
					
			HSSFCell hosCodeCell = row.createCell(2, XSSFCell.CELL_TYPE_STRING);
			hosCodeCell.setCellValue(data.getHospitalCode());
			hosCodeCell.setCellStyle(valueEnglishStyle);
			
			HSSFCell hosNameCell = row.createCell(3, XSSFCell.CELL_TYPE_STRING);
			hosNameCell.setCellValue(data.getHospitalName());
			hosNameCell.setCellStyle(valueNameBorderStyle);
			
			HSSFCell num1Cell = row.createCell(4, XSSFCell.CELL_TYPE_NUMERIC);
			num1Cell.setCellValue(data.getEnTNumPerDay());
			num1Cell.setCellStyle(numberCellStyle);
			
			HSSFCell num2Cell = row.createCell(5, XSSFCell.CELL_TYPE_NUMERIC);
			num2Cell.setCellValue(data.getArNumPerDay());
			num2Cell.setCellStyle(numberCellStyle);
			
			HSSFCell num3Cell = row.createCell(6, XSSFCell.CELL_TYPE_NUMERIC);
			num3Cell.setCellValue(data.getArRate());
			num3Cell.setCellStyle(percentCellStyle);
			
			/** 日均过敏鼻炎患者中使用鼻用激素的人数*/
			HSSFCell gmJSNumCell = row.createCell(7, XSSFCell.CELL_TYPE_NUMERIC);
			gmJSNumCell.setCellValue(data.getGmjsNumPerDay());
			gmJSNumCell.setCellStyle(numberCellStyle);
			
			/** 过敏鼻炎患者中使用鼻用激素的比例*/
			HSSFCell gmJSRateCell = row.createCell(8, XSSFCell.CELL_TYPE_NUMERIC);
			gmJSRateCell.setCellValue(data.getGmjsRate());
			gmJSRateCell.setCellStyle(percentCellStyle);
			
			HSSFCell num4Cell = row.createCell(9, XSSFCell.CELL_TYPE_NUMERIC);
			num4Cell.setCellValue(data.getPrhinitisNumPerDay());
			num4Cell.setCellStyle(numberCellStyle);
			
			HSSFCell num5Cell = row.createCell(10, XSSFCell.CELL_TYPE_NUMERIC);
			num5Cell.setCellValue(data.getPrhinitisRate());
			num5Cell.setCellStyle(percentCellStyle);
			
			/** 日均常年鼻炎患者中使用鼻用激素的人数*/
			HSSFCell cnJSNumCell = row.createCell(11, XSSFCell.CELL_TYPE_NUMERIC);
			cnJSNumCell.setCellValue(data.getCnjsNumPerDay());
			cnJSNumCell.setCellStyle(numberCellStyle);
			
			/** 常年鼻炎患者中使用鼻用激素的比例*/
			HSSFCell cnJSRateCell = row.createCell(12, XSSFCell.CELL_TYPE_NUMERIC);
			cnJSRateCell.setCellValue(data.getCnjsRate());
			cnJSRateCell.setCellStyle(percentBorderStyle);
			
			HSSFCell prhinitisCell = row.createCell(13, XSSFCell.CELL_TYPE_NUMERIC);
			prhinitisCell.setCellValue(data.getRhiARNumPerDay());
			prhinitisCell.setCellStyle(numberCellStyle);

            HSSFCell prhinitisRateCell = row.createCell(14, XSSFCell.CELL_TYPE_NUMERIC);
            prhinitisRateCell.setCellValue(data.getRhiARRate());
            prhinitisRateCell.setCellStyle(percentCellStyle);

            HSSFCell rhiPRhiNumCell = row.createCell(15, XSSFCell.CELL_TYPE_NUMERIC);
            rhiPRhiNumCell.setCellValue(data.getRhiPRhinitisNumPerDay());
            rhiPRhiNumCell.setCellStyle(numberCellStyle);
            
			HSSFCell rhiPRRateCell = row.createCell(16, XSSFCell.CELL_TYPE_NUMERIC);
			rhiPRRateCell.setCellValue(data.getRhiPRRate());
			rhiPRRateCell.setCellStyle(percentCellStyle);
			
			HSSFCell rhinocortRateCell = row.createCell(17, XSSFCell.CELL_TYPE_NUMERIC);
			rhinocortRateCell.setCellValue(data.getRhinocortRate());
			rhinocortRateCell.setCellStyle(percentBorderStyle);
			
			/** 哮喘合并过敏鼻炎比例  */
			HSSFCell xcRate1Cell = row.createCell(18, XSSFCell.CELL_TYPE_NUMERIC);
			xcRate1Cell.setCellValue(data.getXcRate1());
			xcRate1Cell.setCellStyle(percentCellStyle);
			
			/** 哮喘合并过敏鼻炎中鼻喷激素处方比例 */
			HSSFCell xcRate2Cell = row.createCell(19, XSSFCell.CELL_TYPE_NUMERIC);
			xcRate2Cell.setCellValue(data.getXcRate2());
			xcRate2Cell.setCellStyle(percentCellStyle);
			
			/** 鼻喷激素中雷诺考特处方比例  */
			HSSFCell xcRate3Cell = row.createCell(20, XSSFCell.CELL_TYPE_NUMERIC);
			xcRate3Cell.setCellValue(data.getXcRate3());
			xcRate3Cell.setCellStyle(percentCellStyle);

			/** 哮喘合并过敏鼻炎中孟鲁司特类药物处方比例  */
			HSSFCell xcRate4Cell = row.createCell(21, XSSFCell.CELL_TYPE_NUMERIC);
			xcRate4Cell.setCellValue(data.getXcRate4());
			xcRate4Cell.setCellStyle(percentBorderStyle);
			
			HSSFCell salesNameCell = row.createCell(22, XSSFCell.CELL_TYPE_STRING);
			salesNameCell.setCellValue(data.getSaleName());
			salesNameCell.setCellStyle(valueNameStyle);
			
			HSSFCell dsmNameCell = row.createCell(23, XSSFCell.CELL_TYPE_STRING);
			dsmNameCell.setCellValue(data.getDsmName());
			dsmNameCell.setCellStyle(valueNameBorderStyle);
		}
	}
	
	private void populateSecondWeeklyReportSheet(HSSFWorkbook workbook, 
			HSSFCellStyle topStyle, 
			HSSFCellStyle top2Style,
			HSSFCellStyle englishStyle,
			HSSFCellStyle valueNameStyle,
			HSSFCellStyle valueEnglishStyle,
			HSSFCellStyle valueNameBorderStyle,
			HSSFFont englishFont,
			List<RhinocortWeeklyExportData> exportWeeklyData,
			String dateTitle){
		HSSFSheet sheet = workbook.createSheet("未提交数据医院列表");
        int currentRowNum = 0;
        
        //build the header
        HSSFRow row = sheet.createRow(currentRowNum++);
        
        HSSFCell topDateCell = row.createCell(0,XSSFCell.CELL_TYPE_STRING);
        topDateCell.setCellValue(dateTitle);
        topDateCell.setCellStyle(valueEnglishStyle);
        sheet.addMergedRegion(new Region(0, (short)0, 0, (short)2));
        
        row = sheet.createRow(currentRowNum++);
        
        row.createCell(0, XSSFCell.CELL_TYPE_STRING).setCellValue("医院以及区域基本情况");
        row.createCell(1, XSSFCell.CELL_TYPE_STRING).setCellValue("");
        row.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("");
        row.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue("");
        sheet.addMergedRegion(new Region(1, (short)0, 1, (short)3));
        row.getCell(0).setCellStyle(topStyle);
        
        row.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue("销售同事情况");
        row.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue("");
        sheet.addMergedRegion(new Region(1, (short)4, 1, (short)5));
        row.getCell(4).setCellStyle(topStyle);
        
        row = sheet.createRow(currentRowNum++);
        HSSFCell headerBRNameCell = row.createCell(0, XSSFCell.CELL_TYPE_STRING);
        headerBRNameCell.setCellValue("BR Name");
        headerBRNameCell.setCellStyle(englishStyle);
        
        HSSFCell headerRSMCell = row.createCell(1, XSSFCell.CELL_TYPE_STRING);
        headerRSMCell.setCellValue("RSM");
        headerRSMCell.setCellStyle(englishStyle);
        
        HSSFCell headerHosCodeCell = row.createCell(2, XSSFCell.CELL_TYPE_STRING);
        headerHosCodeCell.setCellValue("医院编号");
        headerHosCodeCell.setCellStyle(top2Style);
        
        HSSFCell headerHosNameCell = row.createCell(3, XSSFCell.CELL_TYPE_STRING);
        headerHosNameCell.setCellValue("KPI医院名称");
        headerHosNameCell.setCellStyle(top2Style);
        
        HSSFCell headerSalesCell = row.createCell(4, XSSFCell.CELL_TYPE_STRING);
        headerSalesCell.setCellValue("销售代表姓名");
        headerSalesCell.setCellStyle(top2Style);
        
        HSSFCell headerDSMNameCell = row.createCell(5, XSSFCell.CELL_TYPE_STRING);
        headerDSMNameCell.setCellValue("所属DSM");
        headerDSMNameCell.setCellStyle(top2Style);
        
        
        int hosColumnWidth = 12;
        int salesColumnWidth = 13;
        
        sheet.setColumnWidth(0, hosColumnWidth*256);
        sheet.setColumnWidth(1, hosColumnWidth*256);
        sheet.setColumnWidth(2, hosColumnWidth*256);
        sheet.setColumnWidth(3, 44*256);
        sheet.setColumnWidth(4, salesColumnWidth*256);
        sheet.setColumnWidth(5, salesColumnWidth*256);
        
		for( RhinocortWeeklyExportData data : exportWeeklyData ){
			row = sheet.createRow(currentRowNum++);
			
			HSSFCell rsdCell = row.createCell(0, XSSFCell.CELL_TYPE_STRING);
			rsdCell.setCellValue(data.getRegionCenter());
			rsdCell.setCellStyle(valueEnglishStyle);
			
			HSSFCell rsmCell = row.createCell(1, XSSFCell.CELL_TYPE_STRING);
			rsmCell.setCellValue(data.getRegion());
			rsmCell.setCellStyle(valueEnglishStyle);
					
			HSSFCell hosCodeCell = row.createCell(2, XSSFCell.CELL_TYPE_STRING);
			hosCodeCell.setCellValue(data.getHospitalCode());
			hosCodeCell.setCellStyle(valueEnglishStyle);
			
			HSSFCell hosNameCell = row.createCell(3, XSSFCell.CELL_TYPE_STRING);
			hosNameCell.setCellValue(data.getHospitalName());
			hosNameCell.setCellStyle(valueNameBorderStyle);
			
			HSSFCell salesNameCell = row.createCell(4, XSSFCell.CELL_TYPE_STRING);
			salesNameCell.setCellValue(data.getSaleName());
			salesNameCell.setCellStyle(valueNameStyle);
			
			HSSFCell dsmNameCell = row.createCell(5, XSSFCell.CELL_TYPE_STRING);
			dsmNameCell.setCellValue(data.getDsmName());
			dsmNameCell.setCellStyle(valueNameBorderStyle);
		}
	}
}
