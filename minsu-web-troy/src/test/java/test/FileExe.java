package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileExe {


    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(FileExe.class);

	public static String readFileByLines() {
		String insert = "INSERT INTO t_conf_city(fid,show_name,CODE,LEVEL,pcode,city_status)";

		File file = new File("F:\\city.txt");
		BufferedReader reader = null;
		try {
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"gbk")); 
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				StringBuffer sb = new StringBuffer();
				String[] s = tempString.split(",");
				sb.append("'" + s[1] + "',");
				sb.append("'" + s[0] + "',");
				sb.append("" +(Integer.valueOf(s[4])+1) + ",");
				sb.append("'" +s[2]+"'," );
				sb.append(1);
				String value = "VALUES('"+UUIDGenerator.hexUUID()+"'," + sb.toString() + ");";
				System.out.println(insert + " " + value);

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
