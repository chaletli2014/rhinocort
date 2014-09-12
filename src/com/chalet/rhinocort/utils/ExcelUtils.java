package com.chalet.rhinocort.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.chalet.rhinocort.model.Hospital;
import com.chalet.rhinocort.model.HospitalUserRefer;
import com.chalet.rhinocort.model.UserInfo;

public class ExcelUtils {
    
    private static Logger logger = Logger.getLogger(ExcelUtils.class);
    
    public static Map<String,List> getAllInfosFromFile(String filePath,List<String> regionHeaders, 
            List<String> hospitalHeaders, List<String> repHeaders, List<String> dsmHeaders, List<String> rsmHeaders) throws Exception{
        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        Map<String,List> allInfos = new HashMap<String,List>();
        InputStream is = null;
        try{
        	is = new FileInputStream(filePath);
            Workbook book = createCommonWorkbook(is);
            Sheet sheet = book.getSheetAt(0);
            
            int region_header_count = 0;
            int hos_header_count = 0;
            int rep_header_count = 0;
            int dsm_header_count = 0;
            int rsm_header_count = 0;
            Map<String, Integer> regionHeaderColumn = new HashMap<String, Integer>();
            Map<String, Integer> hospitalHeaderColumn = new HashMap<String, Integer>();
            Map<String, Integer> repHeaderColumn = new HashMap<String, Integer>();
            Map<String, Integer> dsmHeaderColumn = new HashMap<String, Integer>();
            Map<String, Integer> rsmHeaderColumn = new HashMap<String, Integer>();
            
            Row row = sheet.getRow(sheet.getFirstRowNum());
            if( row == null ){
                throw new Exception("文件格式不正确，无法导入数据");
            }
            
            for( int i = row.getFirstCellNum(); i < row.getPhysicalNumberOfCells(); i++ ){
                if( null == row.getCell(i).toString() || "".equalsIgnoreCase(row.getCell(i).toString()) ){
                    logger.info("current column is " + i + ", the header is null, so break the loop");
                    break;
                }
                logger.info("header row : " + row.getCell(i).toString());
                if( regionHeaders.contains(row.getCell(i).toString()) ){
                    regionHeaderColumn.put(row.getCell(i).toString(), i);
                    region_header_count++;
                    continue;
                }
                if( hospitalHeaders.contains(row.getCell(i).toString()) ){
                    hospitalHeaderColumn.put(row.getCell(i).toString(), i);
                    hos_header_count++;
                    continue;
                }
                if( repHeaders.contains(row.getCell(i).toString()) ){
                    repHeaderColumn.put(row.getCell(i).toString(), i);
                    rep_header_count++;
                    continue;
                }
                if( dsmHeaders.contains(row.getCell(i).toString()) ){
                    dsmHeaderColumn.put(row.getCell(i).toString(), i);
                    dsm_header_count++;
                    continue;
                }
                if( rsmHeaders.contains(row.getCell(i).toString()) ){
                    rsmHeaderColumn.put(row.getCell(i).toString(), i);
                    rsm_header_count++;
                    continue;
                }
            }
            logger.info("hospitalHeaders num is " + hos_header_count + ",regionHeader num is " + region_header_count
                    + ", repHeaders num is " + rep_header_count + ", dsmHeaders num is " + dsm_header_count 
                    + ", rsmHeaders num is " + rsm_header_count);
            if( hos_header_count != hospitalHeaders.size() 
                    || region_header_count != regionHeaders.size() 
                    || rep_header_count != repHeaders.size() 
                    || dsm_header_count != dsmHeaders.size() 
                    || rsm_header_count != rsmHeaders.size()){
                throw new Exception("文件格式不正确，无法导入数据");
            }
            
            //get the data
            List<String> userCodes = new ArrayList<String>();
            List<HospitalUserRefer> hosUsers = new ArrayList<HospitalUserRefer>();
            List<String> hospitalCodes = new ArrayList<String>();
            Map<String,Hospital> hospitalMap = new HashMap<String, Hospital>();
            
            for( int i = sheet.getFirstRowNum() + 1; i < sheet.getPhysicalNumberOfRows(); i++ ){
                row = sheet.getRow(i);
                logger.info(" read row " + i);
                if( null == row.getCell(0) || "".equalsIgnoreCase(row.getCell(0).toString()) ){
                    break;
                }
                //collect region info
                String distName = row.getCell(regionHeaderColumn.get(regionHeaders.get(0))).toString();
                String bRName = row.getCell(regionHeaderColumn.get(regionHeaders.get(1))).toString();
                
                // collect hospital info
                String hospitalCode = row.getCell(hospitalHeaderColumn.get(hospitalHeaders.get(0))).toString();
                String hospitalName = row.getCell(hospitalHeaderColumn.get(hospitalHeaders.get(1))).toString();
                String province = row.getCell(hospitalHeaderColumn.get(hospitalHeaders.get(2))).toString();
                String city = row.getCell(hospitalHeaderColumn.get(hospitalHeaders.get(3))).toString();
                Cell hospitalLevelCell = row.getCell(hospitalHeaderColumn.get(hospitalHeaders.get(4)));
                hospitalLevelCell.setCellType(Cell.CELL_TYPE_STRING);
                String hospitalLevel = hospitalLevelCell.toString();
                Cell isKPICell = row.getCell(hospitalHeaderColumn.get(hospitalHeaders.get(5)));
                isKPICell.setCellType(Cell.CELL_TYPE_STRING);
                String isKPI = isKPICell.toString();
                
                //collect rep info
                Cell repCodeCell = row.getCell(repHeaderColumn.get(repHeaders.get(0)));
                repCodeCell.setCellType(Cell.CELL_TYPE_STRING);
                String repCode = repCodeCell.toString();
                String repName = row.getCell(repHeaderColumn.get(repHeaders.get(1))).toString();
                Cell repTelCell = row.getCell(repHeaderColumn.get(repHeaders.get(2)));
                repTelCell.setCellType(Cell.CELL_TYPE_STRING);
                String repTel = repTelCell.toString();
                
                Cell isPrimaryCell = row.getCell(repHeaderColumn.get(repHeaders.get(3)));
                isPrimaryCell.setCellType(Cell.CELL_TYPE_STRING);
                String isPrimary = isPrimaryCell.toString();
                
                Cell repEmailCell = row.getCell(repHeaderColumn.get(repHeaders.get(4)));
                repEmailCell.setCellType(Cell.CELL_TYPE_STRING);
                String repEmail = repEmailCell.toString();
                
                //collect dsm info
                Cell dsmCodeCell = row.getCell(dsmHeaderColumn.get(dsmHeaders.get(0)));
                dsmCodeCell.setCellType(Cell.CELL_TYPE_STRING);
                String dsmCode = dsmCodeCell.toString();
                
                String dsmName = row.getCell(dsmHeaderColumn.get(dsmHeaders.get(1))).toString();
                
                Cell dsmTelCell = row.getCell(dsmHeaderColumn.get(dsmHeaders.get(2)));
                dsmTelCell.setCellType(Cell.CELL_TYPE_STRING);
                String dsmTel = dsmTelCell.toString();
                
                Cell dsmEmailCell = row.getCell(dsmHeaderColumn.get(dsmHeaders.get(3)));
                dsmEmailCell.setCellType(Cell.CELL_TYPE_STRING);
                String dsmEmail = dsmEmailCell.toString();
                
                //collect rsm info
                Cell rsmCodeCell = row.getCell(rsmHeaderColumn.get(rsmHeaders.get(0)));
                rsmCodeCell.setCellType(Cell.CELL_TYPE_STRING);
                String rsmCode = rsmCodeCell.toString();
                
                String rsmName = row.getCell(rsmHeaderColumn.get(rsmHeaders.get(1))).toString();
                
                Cell rsmTelCell = row.getCell(rsmHeaderColumn.get(rsmHeaders.get(2)));
                rsmTelCell.setCellType(Cell.CELL_TYPE_STRING);
                String rsmTel = rsmTelCell.toString();
                
                Cell rsmEmailCell = row.getCell(rsmHeaderColumn.get(rsmHeaders.get(3)));
                rsmEmailCell.setCellType(Cell.CELL_TYPE_STRING);
                String rsmEmail = rsmEmailCell.toString();
                
                if( null != hospitalCodes && !hospitalCodes.contains(hospitalCode) 
                        && null != hospitalName && !"".equalsIgnoreCase(hospitalName) ){
                    Hospital hospital = new Hospital();
                    hospital.setCode(hospitalCode);
                    hospital.setName(hospitalName);
                    hospital.setProvince(province);
                    hospital.setCity(city);
                    hospital.setDsmCode(dsmCode);
                    hospital.setDsmName(dsmName);
                    hospital.setRegion(distName);
                    hospital.setRegionCenter(bRName);
//                    hospital.setSaleName(repName);
//                    hospital.setSaleCode(repCode);
                    hospital.setLevel(hospitalLevel);
                    hospital.setIsKPI(isKPI);
                    
                    hospitalMap.put(hospitalCode, hospital);
                    hospitalCodes.add(hospitalCode);
                }
                
                if( null!=isPrimary && "1".equalsIgnoreCase(isPrimary) ){
                    Hospital existHospital = hospitalMap.get(hospitalCode);
                    existHospital.setSaleCode(repCode);
                    existHospital.setSaleName(repName);
                    hospitalMap.put(hospitalCode, existHospital);
                }
                
                if( null != userCodes && !userCodes.contains(distName+dsmCode+repCode+repName) 
                        && null != repName && !"".equalsIgnoreCase(repName) ){
                    UserInfo repInfo = new UserInfo();
                    repInfo.setUserCode(repCode);
                    repInfo.setName(repName);
                    repInfo.setRegion(distName);
                    repInfo.setRegionCenter(bRName);
                    repInfo.setLevel("REP");
                    repInfo.setTelephone(repTel);
                    repInfo.setSuperior(dsmCode);
                    repInfo.setEmail(repEmail);
                    userInfos.add(repInfo);
                    
                    userCodes.add(distName+dsmCode+repCode+repName);
                }
                
                if( null != userCodes && !userCodes.contains(distName+dsmCode+dsmName) 
                        && null != dsmName && !"".equalsIgnoreCase(dsmName) ){
                    UserInfo dsmInfo = new UserInfo();
                    dsmInfo.setUserCode(dsmCode);
                    dsmInfo.setName(dsmName);
                    dsmInfo.setTelephone(dsmTel);
                    dsmInfo.setRegion(distName);
                    dsmInfo.setRegionCenter(bRName);
                    dsmInfo.setLevel("DSM");
                    dsmInfo.setEmail(dsmEmail);
                    userInfos.add(dsmInfo);
                    
                    userCodes.add(distName+dsmCode+dsmName);
                }
                
                if( null != userCodes && !userCodes.contains(distName+rsmCode+rsmName) 
                        && null != rsmName && !"".equalsIgnoreCase(rsmName) ){
                    UserInfo rsmInfo = new UserInfo();
                    rsmInfo.setUserCode(rsmCode);
                    rsmInfo.setName(rsmName);
                    rsmInfo.setTelephone(rsmTel);
                    rsmInfo.setRegion(distName);
                    rsmInfo.setRegionCenter(bRName);
                    rsmInfo.setLevel("RSM");
                    rsmInfo.setEmail(rsmEmail);
                    userInfos.add(rsmInfo);
                    
                    userCodes.add(distName+rsmCode+rsmName);
                }
                
              //hos users reference
                if( null != repCode && !"".equalsIgnoreCase(repCode) && !"#N/A".equalsIgnoreCase(repCode)
                        && null != hospitalCode && !"".equalsIgnoreCase(hospitalCode)){
                    HospitalUserRefer hosUserRefer = new HospitalUserRefer();
                    hosUserRefer.setHospitalCode(hospitalCode);
                    hosUserRefer.setUserCode(repCode);
                    hosUserRefer.setIsPrimary(isPrimary);
                    
                    hosUsers.add(hosUserRefer);
                }
            }
            
            List<Hospital> hospitalInfos = new ArrayList<Hospital>();
            for( Hospital hos : hospitalMap.values()){
                hospitalInfos.add(hos);
            }
            logger.info(String.format("hospital list size is %s", hospitalInfos==null?0:hospitalInfos.size()) );
            
            allInfos.put("hospitals", hospitalInfos);
            allInfos.put("users", userInfos);
            allInfos.put("hosUsers", hosUsers);
        }catch(Exception e){
            logger.error("fail to get users from the excel file.",e);
            throw new Exception(e.getMessage());
        }finally{
            if( null!= is ){
                is.close();
            }
        }
        
        return allInfos;
    }
    
    public static List<UserInfo> getBMUserInfosFromFile(String filePath,List<String> headers) throws Exception{
        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        FileInputStream is = null;
        try{
            is = new FileInputStream(filePath);
            Workbook book = createCommonWorkbook(is);
            Sheet sheet = book.getSheetAt(0);
            
            int header_count = 0;
            Map<String, Integer> headerColumn = new HashMap<String, Integer>();
            
            Row row = sheet.getRow(sheet.getFirstRowNum());
            for( int i = row.getFirstCellNum(); i < row.getPhysicalNumberOfCells(); i++ ){
                if( headers.contains(row.getCell(i).toString()) ){
                    logger.info(String.format("row.getCell(i).toString() is %s, i is %s", row.getCell(i).toString(),i));
                    headerColumn.put(row.getCell(i).toString(), i);
                    header_count++;
                }
            }
            logger.info("header_count is " + header_count);
            if( header_count != headers.size() ){
                throw new Exception("文件格式不正确，无法导入数据");
            }
            //get the data
            logger.info(String.format("sheet.getPhysicalNumberOfRows() is %s", sheet.getPhysicalNumberOfRows()));
            for( int i = sheet.getFirstRowNum() + 1; i < sheet.getPhysicalNumberOfRows(); i++ ){
                row = sheet.getRow(i);
                logger.info(String.format("read the row from BM excel %s",i));
                
                String userCode = "";
                String name = "";
                String type = "";
                String tel = "";
                String email = "";
                try{
                    Cell userCodeCell = row.getCell(headerColumn.get(headers.get(0)));
                    Cell nameCell = row.getCell(headerColumn.get(headers.get(1)));
                    Cell typeCell = row.getCell(headerColumn.get(headers.get(2)));
                    Cell telCell = row.getCell(headerColumn.get(headers.get(3)));
                    Cell emailCell = row.getCell(headerColumn.get(headers.get(4)));
                    if( userCodeCell == null && nameCell == null
                            && typeCell == null
                            && telCell == null
                            && emailCell == null ){
                        logger.info("there is no new info, break now");
                        break;
                    }
                    
                    userCodeCell.setCellType(Cell.CELL_TYPE_STRING);
                    if( null != userCodeCell ){
                        userCode = userCodeCell.toString();
                    }
                    logger.info(String.format("insert BM user whose code is %s",userCode));
                    
                    name = nameCell.toString();
                    
                    type = typeCell.toString();
                    
                    telCell.setCellType(Cell.CELL_TYPE_STRING);
                    tel = telCell.toString();
                    
                    if( null != emailCell ){
                        email = emailCell.toString();
                    }
                }catch(Exception e){
                    logger.error("fail to get the user from the excel,",e);
                }
                
                if( null != tel && !"".equalsIgnoreCase(tel) ){
                    UserInfo userInfo = new UserInfo();
                    userInfo.setUserCode(userCode);
                    userInfo.setName(name);
                    userInfo.setLevel(type);
                    userInfo.setTelephone(tel);
                    userInfo.setEmail(email);
                    userInfos.add(userInfo);
                }
            }
            
        }catch(Exception e){
            logger.error("fail to get users from the excel file.",e);
            throw new Exception(e.getMessage());
        }finally{
            if(null!= is){
                is.close();
            }
        }
        
        return userInfos;
    }

    public static Workbook createCommonWorkbook(InputStream inp) throws IOException, InvalidFormatException { 
        if (!inp.markSupported()) {
            inp = new PushbackInputStream(inp,8);
        } 
        if (POIFSFileSystem.hasPOIFSHeader(inp)) { 
            return new HSSFWorkbook(inp); 
        } 
        if (POIXMLDocument.hasOOXMLHeader(inp)) { 
            return new XSSFWorkbook(OPCPackage.open(inp)); 
        } 
        throw new IOException("不能解析的excel版本"); 
    }
    
    public static void main(String[] args){
    }
}
