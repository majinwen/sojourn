/**
 * @FileName: RedisKeyConst.java
 * @Package com.ziroom.minsu.services.basedata.constant
 * 
 * @author bushujie
 * @created 2016年3月26日 下午4:23:49
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.common.utils;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;

/**
 * <p>redids</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class RedisKeyConst {
	
	/**
	 * 配置相关缓存前缀
	 */
	public static final String CONF_KEY_PREFIX="minsu:redis:conf";
	
	/**
	 * 静态资源相关缓存前缀
	 */
	public static final String CONF_KEY_STATIC="minsu:redis:static";

    /**
     * 搜索相关缓存前缀
     */
    public static final String SEARCH_KEY_PREFIX="minsu:redis:search";

    /**
     * 搜索cms相关缓存前缀
     */
    public static final String SEARCH_KEY_CMS_PREFIX="minsu:redis:search:cms";
    
    /**
     * 房源锁相关缓存前缀
     */
    public static final String HOUSE_LOCK_KEY_PREFIX="minsu:redis:houselock";
    
    /**
     * 城市相关缓存信息
     */
    public static final String CITY_KEY_PREFIX="minsu:redis:city";

    /**
     * 用户缓存前缀
     */
    public static final String CUSTOMER_VO_KEY_PREFIX="minsu:redis:customervo";
    
    /**
     * 房源相关缓存前缀
     */
    public static final String HOUSE_KEY_PREFIX="minsu:redis:house";
    /**
     * 手机验证码code缓存 3分钟
     */
    public static final String MOBILE_CODE_KEY_PREFIX="minsu:redis:mobilecode";
    
    /**
     * 房源 收益
     */
    public static final String MONTH_PROFIT_KEY_PREFIX="minsu:redis:profit";
    
    /**
     * 员工缓存前缀
     */
    public static final String EMPLOYEE_KEY_PREFIX="minsu:redis:emp";
    
    
    /**
     * IM缓存前缀
     */
    public static final String IM_KEY_PREFIX="minsu:redis:im";

    /**
     * 收藏相关缓存前缀
     */
    public static final String COLLECT_KEY_PREFIX="minsu:redis:collect";

    /**
     * 环信token缓存前缀
     */
    public static final String HUANXIN_TOKEN_KEY_PREFIX="minsu:redis:huanxin_token";


    /**
     * 配置相关缓存前缀
     */
    public static final String LOCATION_KEY_PREFIX="minsu:redis:location";
    
    /**
     * 权限相关缓存前缀
     */
    public static final String RES_KEY_PREFIX="minsu:redis:res";
    
    /**
     * 权限相关缓存前缀
     */
    public static final String RES_KEY_PREFIX_GROUPUID="minsu:redis:groupactuid";
    
    /**
     * 权限相关缓存前缀
     */
    public static final String RES_KEY_PREFIX_GROUPACTBYUID="minsu:redis:groupactbyuid";
    
	/**
	 * 自如驿订单相关缓存前缀
	 */
	public static final String ZIRUYI_KEY_ORDER="ziruyi:redis:order";
    /**
     * 共工配置前缀
     */
    public static final String COMMON_KEY_CONF = "common:redis:conf";
    

    /**
     * 民宿和自如驿未入住订单列表缓存前缀
     */
    public static final String UNCHECKIN_MSYZ_ORDER_LIST_KEY_PREFIX="minsu:redis:uncheckinmsyzorderlist";

    /**
     * redis中间前缀
     */
    public static final String REDIS_SPLIT_PRE = ":";
    /**
     * redis中间后缀
     */
    public static final String REDIS_SPLIT_SUFFIX = "_";

	/**
	 * 配置相关缓存时长
	 */
	public static final int CONF_CACHE_TIME=7200;

    /**
     * 搜索最新房源的设置失效时间
     */
    public static final int SEARCH_NEW_LIST_TIME=24*60*60;

    /**
     * 位置设置时长
     */
    public static final int LOCATION_TIME=60*60;


	/**
	 * 房源锁失效时间
	 */
	public static final int HOUSE_LOCK_CACHE_TIME=1800;
	/**
	 * 用户信息保存时长 12小时
	 */
	public static final int CUSTOMERVO_LOCK_CACHE_TIME=12*60*60;
	/**
	 * 用户信息保存时长 2分钟
	 */
	public static final int CUSTOMERVO_LOCK_CACHE_TIME_SHORT=2*60;
	
	/**
	 * 城市信息缓存时间
	 */
	public static final int CITY_CONF_CACHE_TIME=86400;
	
	/**
	 * 房源浏览量缓存时间
	 */
	public static final int HOUSE_STATISTICAL_CACHE_TIME=30*24*60*60;
	/**
	 * 手机验证码时间
	 */
	public static final int MOBILE_CODE_CACHE_TIME=3*60;
	
	/**
	 *收益缓存时间 30分钟
	 */
	//public static final int PRIFIT_CACHE_TIME=30*60;

    public static final int PRIFIT_CACHE_TIME=5*60;
    
    /**
     * IM缓存时间
     */
    public static final int IM_CACHE_TIME=12*60*60;
    /**
     * 环信token缓存时间 6天
     */
    public static final int HUANXIN_TOKEN_CACHE_TIME=6*24*60*60;

    /**
     * 房间没有fid的时候设置价格的缓存时间
     */
    public static final int ROOM_NEW_CACHE_TIME=3*60*60;
    
    /**
     * 活动的缓存时间 为3s
     */
    public static final int GROUP_ACTSN_UID_TIME=3;
    
    /**
     * 活动的缓存时间 为1s
     */
    public static final int ACHIEVE_COUPON_GROUP_ACTSN_UID_TIME=1; 
    
	/**
	 * 自如驿订单缓存时长 2分钟
	 */
	public static final int ZIRUYI_ORDER_CACHE_TIME=2*60;

	/**
	 * 私有构造方法
	 */
	private RedisKeyConst(){
		
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
     * 获取地理位置相关的redis的key
     * @param objs
     * @return
     */
    public static String getLocalKey(Object... objs){
        return getKeyArray(LOCATION_KEY_PREFIX,objs);
    }


    /**
     * 获取搜索相关的redis的key
     * @param objs
     * @return
     */
    public static String getSearchKey(Object... objs){
        return getKeyArray(SEARCH_KEY_PREFIX,objs);
    }
    
    /**
     * 搜索cms相关的redis的key
     * @author zl
     * @created 2017/8/1 15:40
     * @param
     * @return 
     */
    public static String getSearchCmsKey(Object... objs){
        return getKeyArray(SEARCH_KEY_CMS_PREFIX,objs);
    }
    
    /**
     * 获取锁房源相关redis的key
     * @param objs
     * @return
     */
    public static String getHouseLockKey(Object... objs){
        return getKeyArray(HOUSE_LOCK_KEY_PREFIX,objs);
    }
    /**
     * 获取用户相关redis的key
     * @param objs
     * @return
     */
    public static String getCutomerKey(Object... objs){
        return getKeyArray(CUSTOMER_VO_KEY_PREFIX,objs);
    }
    
    /**
     * 
     * 获取城市相关redis的key
     *
     * @author bushujie
     * @created 2016年5月6日 下午5:13:36
     *
     * @param objs
     * @return
     */
    public static String getCityConfKey(Object... objs){
    	return getKeyArray(CITY_KEY_PREFIX,objs);
    }
    
    /**
     * 
     * 获取房源相关key
     *
     * @author bushujie
     * @created 2016年5月15日 上午2:25:03
     *
     * @param objs
     * @return
     */
    public static String getHouseKey(Object... objs){
    	return getKeyArray(HOUSE_KEY_PREFIX,objs);
    }

    /**
     * 获取手机验证码相关redis的key
     * @param objs
     * @return
     */
    public static String getMobileCodeKey(Object... objs){
        return getKeyArray(MOBILE_CODE_KEY_PREFIX,objs);
    }
    
    /**
     * 获取配置相关的redis的key
     * @param objs
     * @return
     */
    public static String getProfitKey(Object... objs){
        return getKeyArray(MONTH_PROFIT_KEY_PREFIX,objs);
    }
    
    /**
     * 获取员工相关的redis的key
     * @param objs
     * @return
     */
    public static String getEmpKey(Object... objs){
    	return getKeyArray(EMPLOYEE_KEY_PREFIX,objs);
    }
    
    /**
     * 获取IM聊天前缀
     * @param objs
     * @return
     */
    public static String getImKey(Object... objs){
    	return getKeyArray(IM_KEY_PREFIX,objs);
    }



    /**
     * 获取收藏前缀
     * @param objs
     * @return
     */
    public static String getCollectKey(Object... objs){
        return getKeyArray(COLLECT_KEY_PREFIX,objs);
    }
    /**
     * 获取环信相关redis的key
     * @param objs
     * @return
     */
    public static String getHuanxinTokenKey(Object... objs){
        return getKeyArray(HUANXIN_TOKEN_KEY_PREFIX,objs);
    }
    
    /**
     * 
     * 获取权限相关缓存key
     *
     * @author bushujie
     * @created 2016年12月13日 下午5:45:18
     *
     * @param objs
     * @return
     */
    public static String getResCacheKey(Object... objs){
    	 return getKeyArray(RES_KEY_PREFIX,objs);
    }
    
    /**
     * 
     * 根据uid领取礼包
     *
     * @author yd
     * @created 2017年5月12日 下午10:18:36
     *
     * @param objs
     * @return
     */
    public static String getGroupAcnUid(Object... objs){
   	 return getKeyArray(RES_KEY_PREFIX_GROUPUID,objs);
   }
    public static void main(String[] args) {
    	System.out.println(getGroupAcnUid(null,"FYLQ01","e4614125-1d7d-417b-849e-c1f7839281d3"));
	}
    
    /**
     * 
     * 根据uid领取优惠券
     *
     * @author loushuai
     * @created 2017年11月20日 下午10:18:36
     *
     * @param objs
     * @return
     */
    public static String getGroupAcnByUid(Object... objs){
   	 return getKeyArray(RES_KEY_PREFIX_GROUPACTBYUID,objs);
   }
    
    /**
     * 民宿和自如驿未入住订单列表redis的key
     * @param objs
     * @return
     */
    public static String getUnCheckinMsYzOrderListKey(Object... objs){
        return getKeyArray(UNCHECKIN_MSYZ_ORDER_LIST_KEY_PREFIX,objs);
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
               if(Check.NuNObj(argArray[i])){
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
            throw new BusinessException("key empty or null argument array");
        }
    }

}
