/**
 * @FileName: IMpushUtils.java
 * @Package com.ziroom.minsu.services.common.im
 * 
 * @author yd
 * @created 2016年4月14日 上午11:37:00
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.common.jpush;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.audience.Audience;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.constant.JpushConst;
import com.ziroom.minsu.services.common.jpush.base.JpushConfig;
import com.ziroom.minsu.services.common.jpush.base.JpushEntity;
import com.ziroom.minsu.services.common.jpush.base.JpushTask;
import com.ziroom.minsu.services.common.jpush.base.MessageTypeEnum;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;

/**
 * <p>极光推送工具类</p>
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
public class JpushUtils {
	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(JpushUtils.class);
	
	/**
	 * 线程池框架
	 */
	private final static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(50, 100, 3000L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>());
	
	
	/**
	 * 在所有平台上 给多个对象推送（也包括一个用户）
	 * @author yd
	 * @created 2016年4月14日 下午3:55:56
	 *
	 * @param listUserUid  推送的用户UID
	 * @param jpushConfig  推送消息配置
	 */
	public static void sendPushMany(List<String> listUserUid,JpushConfig jpushConfig,Map<String, String> paramsMap){
		
		if(checkJpushConfig(jpushConfig)&&!Check.NuNCollection(listUserUid)){
			chanageContent(jpushConfig, paramsMap);
			String[] aliasArr = new String[listUserUid.size()];
			List<String> listMad5Uid = new ArrayList<String>();
			int i=0;
			for (String uid : listUserUid) {
				listMad5Uid.add(MD5Util.MD5Encode(uid, "UTF-8"));
				aliasArr[i] = MD5Util.MD5Encode(uid, "UTF-8");
				i++;
			}
			jpushConfig.setAudience(Audience.alias(listMad5Uid));
			jpushConfig.setAlias(aliasArr);
			if(!Check.NuNObj(jpushConfig)&&!Check.NuNStr(jpushConfig.getContent())){
				LogUtil.info(logger, "给极光推送对象jpushConfig={}", JsonEntityTransform.Object2Json(jpushConfig));
				threadPoolExecutor.execute(new JpushTask(jpushConfig));
			}
		}
		
	}
	
	/**
	 * 在所有平台上 给单个用户推送
	 * @author yd
	 * @created 2016年4月14日 下午3:55:56
	 *
	 * @param uid  推送的用户UID
	 * @param jpushConfig  推送消息配置
	 */
	public static void sendPushOne(String uid ,JpushConfig jpushConfig,Map<String, String> paramsMap){
		if(!Check.NuNStr(uid)){
			List<String> listUserUid  = new ArrayList<String>();
			listUserUid.add(uid);
			sendPushMany(listUserUid, jpushConfig,paramsMap);
		}
	}
	
	/**
	 * 
	 * 参数替换
	 *
	 * @author yd
	 * @created 2016年5月11日 下午1:25:40
	 *
	 * @param jpushConfig
	 * @param paramsMap
	 */
	private static void chanageContent(JpushConfig jpushConfig,Map<String, String> paramsMap){
		
		if(!Check.NuNObj(jpushConfig)&&!Check.NuNStr(jpushConfig.getContent())&&!Check.NuNMap(paramsMap)){
			String content = jpushConfig.getContent();
			logger.info("当前模板的短信内容comment=["+content+"]");
			if(content!=null){
				for (Map.Entry<String, String> entry:paramsMap.entrySet()) {
					content = content.replace( entry.getKey(), entry.getValue());
				}
			}
			jpushConfig.setContent(content);
			
			logger.info("当前发送的短信对象smsMessage=[" + jpushConfig.toString() + "]");
		}
	}
	/**
	 * 
	 * 校验参数
	 *
	 * @author yd
	 * @created 2016年4月19日 下午3:49:46
	 *
	 * @param jpushConfig
	 * @return
	 */
	private static boolean checkJpushConfig(JpushConfig jpushConfig){
		
		if(Check.NuNObj(jpushConfig)||Check.NuNStr(jpushConfig.getContent())){
			LogUtil.info(logger, "jpushConfig is null ,content is {},参数错误",jpushConfig.getContent());
			return false;
		}
		//默认所有平台
		if(Check.NuNObj(jpushConfig.getPlatform()))jpushConfig.setPlatform(Platform.all());
		//默认走通知
		if(Check.NuNObj(jpushConfig.getMessageType()))jpushConfig.setMessageType(MessageTypeEnum.NOTIFICATION.getCode());
		
		return true;
	}
	/**
	 * 
	 * 所有平台上给所有用户推送消息
	 * @author yd
	 * @created 2016年4月14日 下午3:57:09
	 * @param jpushConfig 需要推送的内容
	 *
	 */
	public static void  sendPushAll(JpushConfig jpushConfig,Map<String, String> paramsMap){
		
		chanageContent(jpushConfig, paramsMap);
		
		if(checkJpushConfig(jpushConfig)){
			LogUtil.info(logger,"给所有用户推送的消息内容jpushConfig={},注意不能推送null或者空", jpushConfig.toString());
			threadPoolExecutor.execute(new JpushTask(jpushConfig));
		}
	}

	/**
	 *
	 * 测试类
	 *
	 * @author yd
	 * @created 2016年4月19日 下午4:11:59
	 *
	 * @param args
	 */
    public static void main(String[] args) {
    	
    	Map<String, String> paramsMap = new HashMap<String, String>();
    	paramsMap.put("{1}", "我的房源名称9944");

    	//自定义消息额外参数
    	Map<String, String> extrasMap = new HashMap<String, String>();
		extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.MSG_BODY_TYPE_VALUE);
		extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_4);
		extrasMap.put(JpushConst.MSG_HAS_RESPONSE,"1");
		extrasMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_LAN);
		extrasMap.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));
    	extrasMap.put("url","http://www.ziroom.com");
    	JpushConfig jpushConfig = new JpushConfig();
    	String content = "恭喜，您提交的房源{1}已通过审核，可以随时管理房源、接受订单";
    	jpushConfig.setContent(content);
    	//JpushUtils.sendPushAll(jpushConfig,paramsMap); //推送给所有用户
    	List<String> listUserUid  = new ArrayList<String>();
    	listUserUid.add("a06f82a2-423a-e4e3-4ea8-e98317540190");
    	
    	//设置为自定义消息
    	//jpushConfig.setMessageType(MessageTypeEnum.MESSAGE.getCode());
    	//JpushUtils.sendPushMany(listUserUid, jpushConfig,paramsMap);//推送给特定用户
    	jpushConfig.setMessageType(MessageTypeEnum.MESSAGE.getCode());
    	jpushConfig.setTitle("房源上架通知");
    	jpushConfig.setExtrasMap(extrasMap);
    	JpushUtils.sendPushOne("78aa924b-5c7c-42aa-ad36-378156ab43af", jpushConfig, paramsMap);
    	
    	//JpushEntity jpushEntity = new JpushEntity();
    	
    	//1d853e39bea247b4b9416220ec9633a5 
    	/*jpushEntity.setToken("f37556e463d14f00acc6a27afeefdcb3");
    	
    	String[] alias = new String[]{MD5Util.MD5Encode("a06f82a2-423a-e4e3-4ea8-e98317540190", "UTF-8")};
    	jpushEntity.setAlias(alias);
    	jpushEntity.setContent("恭喜，您提交的房源已通过审核，可以随时管理房源、接受订单");
    	jpushEntity.setExtras(extrasMap);
    	jpushEntity.setTitle("房源上架通知");*/
    	
    	try {
    		/*String result = CloseableHttpUtil.sendPost("http://api.push.t.ziroom.com/push", JsonEntityTransform.Object2Json(jpushEntity));
    		System.out.println(result);*/
    		Thread.sleep(100000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
