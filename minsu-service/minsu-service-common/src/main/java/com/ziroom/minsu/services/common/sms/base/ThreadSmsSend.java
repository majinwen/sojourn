package com.ziroom.minsu.services.common.sms.base;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.core.db.dialect.MsSQLDialect;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.common.utils.SystemGlobalsUtils;

/**
 * 
 * <p>邮件发送线程</p>
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
public class ThreadSmsSend implements Runnable {
	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(ThreadSmsSend.class);
	
	/**
	 * 消息体
	 */
	private BaseMessage baseMessage;
	
	/**
	 * 发送地址
	 */
	private String url;
	
	/**
	 * 字符编码，默认UTF-8
	 */
	private String characterSet;
	
	public ThreadSmsSend(){	this.characterSet = "UTF-8";}
	
	public ThreadSmsSend(String url){	
		this.url = url;
		this.characterSet = "UTF-8";
	}
	
	public ThreadSmsSend(BaseMessage baseMessage) {
		this.baseMessage = baseMessage;
		this.characterSet = "UTF-8";
	}
	public ThreadSmsSend(BaseMessage baseMessage,String url) {
		this.baseMessage = baseMessage;
		this.url = url;
		this.characterSet = "UTF-8";
	}

	public BaseMessage getBaseMessage() {
		return baseMessage;
	}

	public void setBaseMessage(BaseMessage baseMessage) {
		this.baseMessage = baseMessage;
	}

	public String getCharacterSet() {
		return characterSet;
	}

	public void setCharacterSet(String characterSet) {
		this.characterSet = characterSet;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public void run() {
		try {
			Map<String, String> param = (Map<String, String>) JsonEntityTransform.json2Map(JsonEntityTransform.Object2Json(baseMessage));
			param.remove("mobileNationCode");
			LogUtil.info(logger,"信息发送地址url=["+this.getUrl()+"]");
			String resData = CloseableHttpUtil.sendPost(this.getUrl(),  JsonEntityTransform.Object2Json(param));
			LogUtil.info(logger,"短信接口返回数据resData=["+resData+"]",resData);
		} catch (Exception e1) {
			LogUtil.error(logger,"短信请求失败");
			e1.printStackTrace();
		}
	}

}
