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

public class DefaultPicFileExe {


    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(DefaultPicFileExe.class);
    
    /**
     * 
     * 更新最新审核未通过时间
     *
     * @author bushujie
     * @created 2017年8月15日 下午2:36:57
     *
     * @return
     */
	public static String readFileByLines() {
		String update = "UPDATE `minsu_house_db`.`t_house_biz_msg` SET refuse_date=";

		File file = new File("F:\\refuse_date.txt");
		BufferedReader reader = null;
		try {
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8")); 
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				StringBuffer sb = new StringBuffer(update);
				String[] s = tempString.split(",");
				sb.append("'" + s[1] + "',");
				if(!s[2].equals("\\N")){
					sb.append("refuse_reason=").append("'"+s[2]+"',");
				}
				sb.append("refuse_mark=").append("'"+s[3]+"'");
				sb.append(" WHERE house_base_fid=");
				sb.append("'" + s[0] + "';");
				System.out.println(sb.toString());
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
     * 
     * 更新最新审核未通过时间(分租)
     *
     * @author bushujie
     * @created 2017年8月15日 下午2:36:57
     *
     * @return
     */
	public static String readFileByLinesF() {
		String update = "UPDATE `minsu_house_db`.`t_house_biz_msg` SET refuse_date=";

		File file = new File("F:\\roomreful.txt");
		BufferedReader reader = null;
		try {
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8")); 
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				StringBuffer sb = new StringBuffer(update);
				String[] s = tempString.split(",");
				sb.append("'" + s[1] + "',");
				if(!s[2].equals("\\N")){
					sb.append("refuse_reason=").append("'"+s[2]+"',");
				}
				sb.append("refuse_mark=").append("'"+s[3]+"'");
				sb.append(" WHERE room_fid=");
				sb.append("'" + s[0] + "';");
				System.out.println(sb.toString());
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
     * 
     * 更新首次上架时间
     *
     * @author bushujie
     * @created 2017年8月15日 下午2:36:57
     *
     * @return
     */
	public static String readFileByLinesUpDate() {
		String update = "UPDATE `minsu_house_db`.`t_house_biz_msg` SET first_up_date=";

		File file = new File("F:\\up_date.txt");
		BufferedReader reader = null;
		try {
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8")); 
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				StringBuffer sb = new StringBuffer(update);
				String[] s = tempString.split(",");
				sb.append("'" + s[1] + "'");
				sb.append(" WHERE house_base_fid=");
				sb.append("'" + s[0] + "';");
				System.out.println(sb.toString());
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
     * 
     * 更新首次发布时间（分租）
     *
     * @author bushujie
     * @created 2017年8月15日 下午2:36:57
     *
     * @return
     */
	public static String readFileByLinesFirstIsDateF() {
		String update = "UPDATE `minsu_house_db`.`t_house_biz_msg` SET first_deploy_date=";

		File file = new File("F:\\roomError.txt");
		BufferedReader reader = null;
		try {
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8")); 
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				StringBuffer sb = new StringBuffer(update);
				String[] s = tempString.split(",");
				sb.append("'" + s[1] + "'");
				sb.append(" WHERE room_fid=");
				sb.append("'" + s[0] + "';");
				System.out.println(sb.toString());
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
  * 
  * 更新首次发布时间
  *
  * @author bushujie
  * @created 2017年8月15日 下午2:36:57
  *
  * @return
  */
	public static String readFileByLinesFirstIsDate() {
		String update = "UPDATE `minsu_house_db`.`t_house_biz_msg` SET first_deploy_date=";

		File file = new File("F:\\houseError.txt");
		BufferedReader reader = null;
		try {
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8")); 
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				StringBuffer sb = new StringBuffer(update);
				String[] s = tempString.split(",");
				sb.append("'" + s[1] + "'");
				sb.append(" WHERE house_base_fid=");
				sb.append("'" + s[0] + "';");
				System.out.println(sb.toString());
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
     * 
     * 更新首次上架时间(分租)
     *
     * @author bushujie
     * @created 2017年8月15日 下午2:36:57
     *
     * @return
     */
	public static String readFileByLinesUpDateF() {
		String update = "UPDATE `minsu_house_db`.`t_house_biz_msg` SET first_up_date=";

		File file = new File("F:\\room_firstup_date.txt");
		BufferedReader reader = null;
		try {
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8")); 
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				StringBuffer sb = new StringBuffer(update);
				String[] s = tempString.split(",");
				sb.append("'" + s[1] + "'");
				sb.append(" WHERE room_fid=");
				sb.append("'" + s[0] + "';");
				System.out.println(sb.toString());
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
     * 
     * 更新首次上架时间
     *
     * @author bushujie
     * @created 2017年8月15日 下午2:36:57
     *
     * @return
     */
	public static String readFileByLinesLastUpDate() {
		String update = "UPDATE `minsu_house_db`.`t_house_biz_msg` SET last_up_date=";

		File file = new File("F:\\lastup_date.txt");
		BufferedReader reader = null;
		try {
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8")); 
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				StringBuffer sb = new StringBuffer(update);
				String[] s = tempString.split(",");
				sb.append("'" + s[1] + "'");
				sb.append(" WHERE house_base_fid=");
				sb.append("'" + s[0] + "';");
				System.out.println(sb.toString());
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
     * 
     * 更新最新上架时间（分）
     *
     * @author bushujie
     * @created 2017年8月15日 下午2:36:57
     *
     * @return
     */
	public static String readFileByLinesLastUpDateF() {
		String update = "UPDATE `minsu_house_db`.`t_house_biz_msg` SET last_up_date=";

		File file = new File("F:\\room_lastup_date.txt");
		BufferedReader reader = null;
		try {
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8")); 
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				StringBuffer sb = new StringBuffer(update);
				String[] s = tempString.split(",");
				sb.append("'" + s[1] + "'");
				sb.append(" WHERE room_fid=");
				sb.append("'" + s[0] + "';");
				System.out.println(sb.toString());
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
     * 
     * 更新最后一次发布时间
     *
     * @author bushujie
     * @created 2017年8月15日 下午2:36:57
     *
     * @return
     */
	public static String readFileByLinesLastIsDate() {
		String update = "UPDATE `minsu_house_db`.`t_house_biz_msg` SET last_deploy_date=";

		File file = new File("F:\\lastis_date.txt");
		BufferedReader reader = null;
		try {
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8")); 
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				StringBuffer sb = new StringBuffer(update);
				String[] s = tempString.split(",");
				sb.append("'" + s[1] + "'");
				sb.append(" WHERE house_base_fid=");
				sb.append("'" + s[0] + "';");
				System.out.println(sb.toString());
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
     * 
     * 更新最后一次发布时间( 分租)
     *
     * @author bushujie
     * @created 2017年8月15日 下午2:36:57
     *
     * @return
     */
	public static String readFileByLinesLastIsDateF() {
		String update = "UPDATE `minsu_house_db`.`t_house_biz_msg` SET last_deploy_date=";

		File file = new File("F:\\room_lastis_date.txt");
		BufferedReader reader = null;
		try {
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8")); 
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				StringBuffer sb = new StringBuffer(update);
				String[] s = tempString.split(",");
				sb.append("'" + s[1] + "'");
				sb.append(" WHERE room_fid=");
				sb.append("'" + s[0] + "';");
				System.out.println(sb.toString());
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
	 * 
	 * 插入语句
	 *
	 * @author bushujie
	 * @created 2017年8月14日 下午8:11:52
	 *
	 * @return
	 */
	public static String readFileByLinesInsert() {
		String update = "INSERT INTO `minsu_house_db`.`t_house_biz_msg`(fid,rent_way,house_base_fid,first_deploy_date,create_fid) VALUES";

		File file = new File("F:\\firstdate.txt");
		BufferedReader reader = null;
		try {
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"gbk")); 
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				StringBuffer sb = new StringBuffer();
				sb.append(update);
				sb.append(" ('").append(UUIDGenerator.hexUUID()).append("',0,");
				String[] s = tempString.split(",");
				sb.append("'" + s[0] + "',");
				sb.append("'" + s[1] + "','001');");
				System.out.println(sb.toString());
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
	 * 
	 * 插入语句分租首次发布时间
	 *
	 * @author bushujie
	 * @created 2017年8月14日 下午8:11:52
	 *
	 * @return
	 */
	public static String readFileByLinesInsertRoomFirstDate() {
		String update = "INSERT INTO `minsu_house_db`.`t_house_biz_msg`(fid,rent_way,house_base_fid,room_fid,first_deploy_date,create_fid) VALUES";

		File file = new File("F:\\notbizlog.txt");
		BufferedReader reader = null;
		try {
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"gbk")); 
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				StringBuffer sb = new StringBuffer();
				sb.append(update);
				sb.append(" ('").append(UUIDGenerator.hexUUID()).append("',1,");
				String[] s = tempString.split(",");
				sb.append("'" + s[0] + "',");
				sb.append("'" + s[1] + "',");
				sb.append("'" + s[2] + "','001');");
				System.out.println(sb.toString());
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
	 * 
	 * 调整菜单排序号
	 *
	 * @author bushujie
	 * @created 2017年11月30日 下午8:26:41
	 *
	 * @return
	 */
	public static String resSort() {
		String update = "UPDATE `minsu_ups_db`.`t_sys_resource` SET order_code=";

		File file = new File("D:\\test\\zrysc.txt");
		BufferedReader reader = null;
		try {
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"gbk")); 
			String tempString = null;
			int i=1;
			while ((tempString = reader.readLine()) != null) {
				StringBuffer sb = new StringBuffer();
				sb.append(update);
				sb.append(i);
				sb.append(" WHERE fid='");
				sb.append(tempString).append("';");
				System.out.println(sb.toString());
				i++;
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
	 * 
	 * 调整菜单排序号
	 *
	 * @author bushujie
	 * @created 2017年11月30日 下午8:26:41
	 *
	 * @return
	 */
	public static String empCodeEdit() {
		String update = "UPDATE `zra`.`tprojectzo` SET emp_code=";

		File file = new File("D:\\test\\zoempcode.txt");
		BufferedReader reader = null;
		try {
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"gbk")); 
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				StringBuffer sb = new StringBuffer();
				sb.append(update);
				sb.append(tempString.split(",")[1]);
				sb.append(" WHERE fid='");
				sb.append(tempString.split(",")[0]).append("';");
				if(!tempString.split(",")[1].equals("\\N")){
					System.out.println(sb.toString());
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
		return null;
	}


	/**
	 * INSERT INTO `uas_db`.`uas_authority` (`parent_id`, `auth_code`, `auth_name`, `auth_desc`, `auth_url`, `auth_type`, 
	`auth_order`, `add_time`, `modified_time`, `is_delete`, `sys_id`, `is_leaf`)
	VALUES(0,'020','领队管理','描述:领队管理模块','',1,1,1,1,0,2,0);
	 * @param args
	 */
	public static void main(String[] args) {
		empCodeEdit();
	}
}
