package com.ziroom.minsu.services.order.test.proxy;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileExePinjie {


    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(FileExePinjie.class);

    public static String readFileByLines() {
		File file = new File("D:\\111.txt");
        Set<String> words = new HashSet<>();
		BufferedReader reader = null;
		try {
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			String tempString = null;

			while ((tempString = reader.readLine()) != null) {
				if(!Check.NuNStr(tempString)){
                    words.add("'"+tempString.trim()+"',");
				}
			}
			reader.close();
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
		}

        String fileName="D:\\222.txt";
        
       try
        {
            FileWriter writer = new FileWriter(fileName);
            for (String word : words) {
                writer.write(word);
            }
            writer.close();
        } catch (IOException e){
           e.printStackTrace();
       }
		return null;
	}

	/**
	 * INSERT INTO `uas_db`.`uas_authority` (`parent_id`, `auth_code`, `auth_name`, `auth_desc`, `auth_url`, `auth_type`, 
	`auth_order`, `add_time`, `modified_time`, `is_delete`, `sys_id`, `is_leaf`)
	VALUES(0,'020','领队管理','描述:领队管理模块','',1,1,1,1,0,2,0);
	 * @param args
	 */
	public static void main(String[] args) {
		readFileByLines();
	}
}
