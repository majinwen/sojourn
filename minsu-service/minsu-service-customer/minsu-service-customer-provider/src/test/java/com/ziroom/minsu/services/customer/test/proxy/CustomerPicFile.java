package com.ziroom.minsu.services.customer.test.proxy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.util.DateUtil;
import com.asura.framework.utils.LogUtil;

public class CustomerPicFile {
	
	  /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(CustomerPicFile.class);
	
    public static void readCsvFile() throws ParseException {
        File file = new File("F:\\33.csv");
        Set<String> delFids = new HashSet<>();
        BufferedReader reader = null;
        String fileName="F:\\UpCustomerPic.sql";
        FileWriter writer = null;
        try {
            reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"gb2312"));
            String tempString = null;
            Map<String, Date> dateMap = new HashMap<>();
            Map<String, String> keyMap = new HashMap<>();
            writer = new FileWriter(fileName+".failed");
            while ((tempString = reader.readLine()) != null) {
            	
            	try {
            		
            		System.err.println(tempString);            		
            		tempString = tempString.replaceAll("\"", "").trim();
                	String[] tmps = tempString.split(",");
                	String fid = tmps[1];
                	String uid = tmps[2];
                	String picType =tmps[3];
                	String dateStr=tmps[9];
                	Date date =null;
                	try {
                		date = DateUtil.parseDate(dateStr,"yyyy-MM-dd HH:mm:ss");
					} catch (Exception e) {
						date = DateUtil.parseDate(dateStr,"yyyy/MM/dd HH:mm");
					} 
                	String isdel=tmps[10];
                	
                	if(isdel.equals("1")){
                		continue;
                	}
                	
                	String key = uid+picType;            	
                	
                	if(dateMap.get(key)==null){
                		dateMap.put(key, date);
                		keyMap.put(key, fid);
                	}else{
                		if(date.before(dateMap.get(key))){
                			delFids.add(fid);
                		}else{
                			delFids.add(keyMap.get(key));
                			dateMap.put(key, date);
                    		keyMap.put(key, fid);
                		}
                	}
				} catch (Exception e) {
					 LogUtil.error(logger, "e={}", e);
					 writer.write("\n\t"+tempString);
				}
            	
            }

            reader.close();
            writer.close();
        } catch (IOException e) {
            LogUtil.error(logger, "e={}", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    LogUtil.error(logger, "e={}", e1);
                }
            }
            if (writer != null) {
                try {
                	writer.close();
                } catch (IOException e1) {
                    LogUtil.error(logger, "e={}", e1);
                }
            }
        }
        
        
       
        try
         {
             writer = new FileWriter(fileName);
             for (String fid : delFids) {
            	 String sql="UPDATE `minsu_customer_db`.`t_customer_pic_msg` SET is_del='1' WHERE fid ='"+fid+"' ;";
                 writer.write("\n\t"+sql);
             }
             writer.close();
         } catch (IOException e){
            e.printStackTrace();
        }finally {
            if (writer != null) {
                try {
                	writer.close();
                } catch (IOException e1) {
                    LogUtil.error(logger, "e={}", e1);
                }
            }
        }
        
    }
	
	
	public static void main(String[] args) throws ParseException {
		readCsvFile();
	}
}
