
package com.zra.common.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>redids</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class RedisKeyZyuConst {


    private final  static Logger LOGGER = LoggerFactory.getLogger(RedisKeyZyuConst.class);
	
	/**
	 * 配置相关缓存前缀
	 */
	public static final String CONF_KEY_PREFIX="zra:redis:conf";


    /**
     * 获取房间锁
     */
    public static final String ROOM_STMART_KEY_PREFIX="zra:redis:room:stmart";
    /**
     * redis中间前缀
     */
    public static final String REDIS_SPLIT_PRE = ":";
    /**
     * redis中间后缀
     */
    public static final String REDIS_SPLIT_SUFFIX = "_";


    /**
     * 获取redis锁的超时时间 1s
     */
    public static final long GET_REDIS_LOCK_TIMEOUT=1;
    /**
     * 20s
     */
    public static final int ROOM_STMART_TIME=40;
    /**
     * 1小时
     */
    public static final int LOCATION_TIME=60*60;

	/**
	 * 2小时
	 */
	public static final int CONF_CACHE_TIME=7200;

    /**
     * 24小时
     */
    public static final int SEARCH_NEW_LIST_TIME=24*60*60;




	/**
	 * 私有构造方法
	 */
	private RedisKeyZyuConst(){
		
	}


    public static String getRoomSmartLockKey(Object... objs){
        return getKeyArray(ROOM_STMART_KEY_PREFIX,objs);
    }

    /**
     * 获取配置相关的redis的key
     * @param objs
     * @return
     */
    public static String getConfigKey(Object... objs){
        return getKeyArray(CONF_KEY_PREFIX,objs);
    }





    /**
     * 获取搜索相关的redis的key
     * @param argArray
     * @return
     */
    public static String getKeyArray(String pre,Object[] argArray){
        if(argArray != null && argArray.length != 0) {
            StringBuffer sb = new StringBuffer();
            sb.append(pre);
            int start = 0;
           for(int i=0;i< argArray.length;i++){
               if(argArray[i] == null){
                   continue;
               }
               if(start==0){
                   sb.append(REDIS_SPLIT_PRE);
               }else {
                   sb.append(REDIS_SPLIT_SUFFIX);
               }
               sb.append(argArray[i]);
               start ++;
           }
            return sb.toString();
        } else {
            LOGGER.error("key empty or null argument array");
            throw new RuntimeException("key empty or null argument array");

        }
    }
    
    public static void main(String[] args) {
		System.out.println(getConfigKey("23159596-ba9f-484a-b7d7-f084208aaa7c"));
	}

}
