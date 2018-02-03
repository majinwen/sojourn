package com.ziroom.minsu.services.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.sms.MessageUtils;
import com.ziroom.minsu.services.common.sms.base.MailMessage;
import com.ziroom.minsu.services.common.sms.base.SmsMessage;


/**
 * 从内存中通过键获取值
 * @author yd
 * @date 2016-03-24
 * @version 1.0
 *
 */
public class SystemGlobalsUtils {

	/**
	 * 日志文件
	 */
	private static Logger log = LoggerFactory.getLogger(SystemGlobalsUtils.class);

	/**
	 * 存放当前配置文件中的键值对,放入内存
	 */
	private static Map<String, String> configFileNamesMap = new ConcurrentHashMap<String, String>();

	/**
	 * 文件内容读入内存
	 * @author yd
	 * @version 1.0
	 * @throws IOException 
	 */
	private static void loadConfig(String name) throws IOException {

		try {
			String path = Thread.currentThread().getContextClassLoader().getResource(name).getPath();
			LogUtil.info(log,"当前加载全局配置文件路径为path={}",path);
			InputStream  in = new BufferedInputStream(new FileInputStream(path));
			if(in!=null){
				Properties p = new Properties();
				p.load(in);
				Set<Object> keys =  p.keySet();
				if(keys!=null&&keys.size()>0){
					for (Object object : keys) {
						String key = (String)object;
						if(configFileNamesMap == null) configFileNamesMap = new LinkedHashMap<String, String>();
						configFileNamesMap.put(key, p.getProperty(key));
					}
				}
			}
			LogUtil.info(log,"当前内存的configFileNamesMap={}",configFileNamesMap.toString());
		} catch (Exception e) {
			LogUtil.error(log,"加载配置文件失败, error:{}",e);
		}

	}


	/**
	 * 取得当前类所在的文件
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static File getClassFile(Class clazz){
		URL path = clazz.getResource(clazz.getName().substring(clazz.getName().lastIndexOf(".")+1)+".classs");
		if(path == null){
			String name = clazz.getName().replaceAll("[.]", "/");
			path = clazz.getResource("/"+name+".class");
		}
		return new File(path.getFile());
	}
	/**
	 * 得到当前类的路径 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getClassFilePath(Class clazz){
		try{
			return java.net.URLDecoder.decode(getClassFile(clazz).getAbsolutePath(),"UTF-8");
		}catch (Exception e) {
			LogUtil.error(log,"error:{}",e);
			return "";
		}
	}

	/**
	 * 取得当前类所在的ClassPath目录，比如tomcat下的classes路径
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static File getClassPathFile(Class clazz){
		File file = getClassFile(clazz);
		for(int i=0,count = clazz.getName().split("[.]").length; i<count; i++)
			file = file.getParentFile();
		if(file.getName().toUpperCase().endsWith(".JAR!")){
			file = file.getParentFile();
		}
		return file;
	}
	/**
	 * 取得当前类所在的ClassPath路径
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getClassPath(Class clazz){
		try{
			return java.net.URLDecoder.decode(getClassPathFile(clazz).getAbsolutePath(),"UTF-8");
		}catch (Exception e) {
			LogUtil.error(log,"error:{}",e);
			return "";
		}
	}


	/**
	 * 加载配置文件
	 * @param configFileNames
	 */
	public static void loadConfigFile(String configFileNames){
		//读取配置文件
		if(configFileNames!=null){
			String[] fileNames = configFileNames.split(",");
			if(fileNames.length>0&&fileNames!=null){

				for (String name : fileNames) {
					if(checkFileName(name)){
						int n = name.lastIndexOf(".");
						String fileType = name.substring(n+1);//文件后缀名称
						if(fileType.equals("properties")){
							try {
								loadConfig(name);
							} catch (FileNotFoundException e) {
								log.debug("当前文件"+name+"无法找到");
								LogUtil.error(log,"error:{}",e);
							} catch (IOException e) {
								log.debug("加载文件"+name+"，出现问题");
								LogUtil.error(log,"error:{}",e);
							} 
						}

					}
				}
			}
		}else{
			LogUtil.info(log,"当前无名称为{}的配置文件",configFileNames);
		}
	}
	/**
	 * 校验文件类型，当前支持xml，和properties 文件 
	 * @param fileName
	 * @author yd
	 * 
	 * @date 2016-03-24
	 * @return
	 */
	private  static boolean checkFileName(String fileName){

		if(fileName==null){
			return false;
		}
		int n = fileName.lastIndexOf(".");
		if(n!=(-1)){
			String names = fileName.substring(n+1);

			if(!names.equals("xml")&&!names.equals("properties")){
				return false;
			}
		}else{
			LogUtil.info(log,"当前文件名称为{},不知道文件类型，无法解析",fileName);
			return false;

		}
		return true;
	}
	/**
	 * 读取配置文件值(以" "隔开)
	 * @author yd
	 * @verion 1.0
	 */
	public static String getValue(String paramName) {

		if(paramName == null){
			return null;
		}

		String str = "";
		if(paramName.contains(" ")) {
			for(String s : paramName.split(" ")) {
				str += configFileNamesMap.get(s);
			}
			return str;
		}
		return configFileNamesMap.get(paramName);
	}
	/**
	 * 获取值列表
	 * @param paramName
	 * @return
	 */
	public static List<String> getValues(String paramName) {

		if(paramName==null)  return null;
		List<String>  valueList =  new ArrayList<String>();
		if(paramName.contains(" ")) {
			for(String s : paramName.split(" ")) {
				if(configFileNamesMap.get(s)!=null){
					valueList.add(configFileNamesMap.get(s));
				}
			}
		}else{
			valueList.add(configFileNamesMap.get(paramName)) ;
		}
		
		return valueList;
	}
	/**
	 * 获取keys
	 * @return keys
	 */
	public static Set<?> getNames(){
		return configFileNamesMap.keySet();
	}
	static{
		String path = SystemGlobalsUtils.class.getResource("/").getPath();
		File file = new File(path);
		if(file.exists()){
			File fa[] = file.listFiles();
			for (int i = 0; i < fa.length; i++) {
				File fs = fa[i];
				if (!fs.isDirectory()) {
					if(checkFileName(fs.getName())){
						loadConfigFile(fs.getName());
					}
				}
			}
		}else{
			LogUtil.info(log,"该路径path={}的文件不存在",path);
		}
		LogUtil.info(log,"全局文件加载完成");
	}
	/**
	 * 
	 * 读取配置文件值，如不存在则返回默认值
	 */
	public static String getValue(String paramName, String defaultValue) {

		String value = configFileNamesMap.get(paramName);
		if(value==null||value.trim().length()==0) {
			return defaultValue;
		}
		return value;

	}

	public static void main(String[] args) {
		SmsMessage smsMessage = new SmsMessage("18701482471", "{1}你好，你于{2}预定的房间，欢迎您准时入住，谢谢!");

		MailMessage mailMessage = new MailMessage("610039854@qq.com", "{1}你好，你于{2}预定的房间，欢迎您准时入住，谢谢!","45645");
		mailMessage.setTitle("你好");

		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("{1}", "杨东");
		paramsMap.put("{2}", "2016-04-02");
		MessageUtils.sendSms(smsMessage, paramsMap);
		
		System.out.println(SystemGlobalsUtils.getNames());
	}
}
