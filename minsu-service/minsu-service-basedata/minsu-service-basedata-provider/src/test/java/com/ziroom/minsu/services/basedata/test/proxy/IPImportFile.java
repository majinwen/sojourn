package com.ziroom.minsu.services.basedata.test.proxy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;

public class IPImportFile {
	
	  /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(IPImportFile.class);
	
    public static void readCsvFile() throws ParseException {
        File file = new File("F:\\ip.csv");
        String fileName="F:\\IpInsert.sql";
        BufferedReader reader = null;
        FileWriter writer = null;
        try {
        	writer = new FileWriter(fileName);
            reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"gb2312"));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
            	System.err.println(tempString);            		
            	tempString = tempString.replaceAll("\"", "").trim();
            	String[] tmps = tempString.split(",");
            	String ip = tmps[0];
            	String port = tmps[1];
            	String fid =UUIDGenerator.hexUUID();
            	
            	String sql ="insert into `minsu_base_db`.`t_net_proxy_ip_port` (`fid`, `proxy_ip`, `proxy_port`, `is_valid`, `valid_used_count`, `create_date`, `last_modify_date`, `is_del`) values('"+fid+"','"+ip+"','"+port+"','1','0','2017-06-29 15:03:48','2017-06-29 15:03:48','0');";
            	writer.write("\n\t"+sql);
            	
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
        
    }
	
	
	public static void main(String[] args) throws ParseException {
		readCsvFile();
	}
}
