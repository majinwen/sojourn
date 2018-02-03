package com.ziroom.minsu.services.solr.utils;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * 获取扩展的敏感词
 * @author afi
 * @date 2016-04-12
 * @version 1.0
 *
 */
public class ExtWorldUtils {


    /**
     * 日志文件
     */
    private static final Logger logger = LoggerFactory.getLogger(SpellUtils.class);

    private final static String default_dic = "minsu.dic";

    private final static String changzu_dic = "changzu.dic";


    private static  Map<String,Date> freshDateMap = new ConcurrentHashMap<>();

//	/**
//	 * 存放当前配置文件中的键值对,放入内存
//	 */
//	private static Map<String, String> extMap = new ConcurrentHashMap<>();


    /**
     * 存放当前配置文件中的键值对,放入内存
     */
    private static Map<String, Map<String, String>> dicMap = new ConcurrentHashMap<>();


	/**
	 * 文件内容读入内存
	 * @author afi
	 * @version 1.0
	 * @throws IOException 
	 */
	private synchronized static void loadExtFile(String fileName) throws IOException {
        if (Check.NuNStr(fileName)){
            //默认走民宿的配置
            fileName = default_dic;
        }

        if (!dicMap.containsKey(fileName)){
            dicMap.put(fileName, new ConcurrentHashMap<String, String>());
        }
        Map<String, String> extMap = dicMap.get(fileName);
		if (!freshDateMap.containsKey(fileName) || DateUtil.getDatebetweenOfDayNum(freshDateMap.get(fileName),new Date())>1){
            try {
                String path = Thread.currentThread().getContextClassLoader().getResource(fileName).getPath();
                LogUtil.debug(logger, "当前加载文件路径为path{}", path);
                File file=new File(path);
                if(file.isFile() && file.exists()) { //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(
                            new FileInputStream(file), "UTF8");//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    while ((lineTxt = bufferedReader.readLine()) != null) {
                        extMap.put(lineTxt,"");
                    }
                    read.close();
                }
            } catch (Exception e) {
                LogUtil.error(logger,"加载文件失败{}",e);
            }
            freshDateMap.put(fileName,new Date());
        }
	}


    /**
     * 校验当前的扩展词中是否存在
     * @param world
     * @return
     */
    public static  boolean checkMinsuExt(String world) throws Exception{
        String dic = default_dic;
        return checkExt(world,dic);
    }

    /**
     * 校验当前的扩展词中是否存在
     * @param world
     * @return
     */
    public static  boolean checkChangzuExt(String world) throws Exception{
        String dic = changzu_dic;
       return checkExt(world,dic);
    }


    /**
     * 校验当前的扩展词中是否存在
     * @param world
     * @return
     */
    private static  boolean checkExt(String world,String fileName) throws Exception{
        Boolean rst = false;
        if(Check.NuNStr(world)){
            return rst;
        }
        loadExtFile(fileName);
        return dicMap.get(fileName).containsKey(world);
    }

}
