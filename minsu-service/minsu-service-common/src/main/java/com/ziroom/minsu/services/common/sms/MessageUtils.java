package com.ziroom.minsu.services.common.sms;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
//code.ziroom.com/minsu/minsu-service.git
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.sms.base.BaseMessage;
import com.ziroom.minsu.services.common.sms.base.MailMessage;
import com.ziroom.minsu.services.common.sms.base.SmsMessage;
import com.ziroom.minsu.services.common.sms.base.ThreadSmsSend;
import com.ziroom.minsu.services.common.utils.SystemGlobalsUtils;


/**
 * 短信工具统一接口工具类
 * <p>
 * 使用说明： 此处涉及发送短信消息和邮件消息  ，使用时，传入短信或者邮箱实体 要初始化，接收人和接受内容
 * 
 * 例如：
 * SmsMessage smsMessage = new SmsMessage("18701482471", "{1}你好，你于{2}预定的房间，欢迎您准时入住，谢谢!");
 * MailMessage mailMessage = new MailMessage("610039854@qq.com", "{1}你好，你于{2}预定的房间，欢迎您准时入住，谢谢!");

 * 然后发送：
 * SmsMessageUtils.sendSms(smsMessage,paramsMap);
 * SmsMessageUtils.sendMail(mailMessage,paramsMap);
 * 
 * paramsMap 这个map是要动态替换掉的参数
 * 第三个参数为当前要请求的URL地址
 *  需要给发送人和发送内容，其他信息配置在sms.properties文件中
 *  
 *  注意看哪些字段是必填项
 * </p>
 * @author yd
 * @date 2016-03-31 21:09
 * @version 1.0
 *
 */
public class MessageUtils {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(MessageUtils.class);

	//private final static String SMS_FILE_NAME = "sms.properties";

	private final static String SMS_KEY = "sms.send.url"; //短息key

	private final static String MAIL_KEY = "mail.send.url"; //邮箱key
	/**
	 * 线程池框架
	 */
	private final static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(50, 100, 3000L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>());

	/**
	 * 
	 * 短信接口，并解析短信中需要替换的内容：比如：短信comment 为‘{1}你好，你于{2}预定的房间，欢迎您准时入住，谢谢’，其中{1},{2}是要替换的内容，被放在paramsMap中
	 * key = {x} x=1,2； value = “杨东”  当前的内容
	 *
	 *
	 * @author yd
	 * @created 2016年4月2日 下午1:57:04
	 *
	 * @param baseMessage
	 * @param paramsMap
	 */
	public static void sendSms(SmsMessage baseMessage,Map<String, String> paramsMap){

		baseMessage.setToken(SystemGlobalsUtils.getValue("sms.token"));
	    sendMessage(baseMessage, paramsMap, MessageUtils.SMS_KEY);

	}

	/**
	 * 
	 * 邮件发送
	 * 原理同上
	 * @author yd
	 * @created 2016年4月2日 下午9:16:06
	 *
	 * @param baseMessage
	 * @param paramsMap
	 */
	public static void sendMail(MailMessage baseMessage,Map<String, String> paramsMap){
		baseMessage.setToken(SystemGlobalsUtils.getValue("mail.token"));
		//LogUtil.info(logger, "邮件token{}", baseMessage.getToken());
		//LogUtil.info(logger, "邮件paramsMap{}", JsonEntityTransform.Object2Json(paramsMap));
		sendMessage(baseMessage, paramsMap, MessageUtils.MAIL_KEY);
	}
	/**
	 * 
	 * 消息发送
	 *
	 * @author yd
	 * @created 2016年4月2日 下午9:14:55
	 *
	 * @param baseMessage
	 * @param paramsMap
	 * @param key
	 */
	private static void sendMessage(BaseMessage baseMessage,Map<String, String> paramsMap,String key){
		
		if(Check.NuNObj(baseMessage)){
			return ;
		}
		
		//替换短息内容comment中的{x}
		String content = baseMessage.getContent();
		if(paramsMap!=null&&paramsMap.size()>0){
			//LogUtil.info(logger,"当前模板的短信内容key={},comment={}",key,content);
			if(content!=null){
				for (Map.Entry<String, String> entry:paramsMap.entrySet()) {
					logger.info(entry.getKey()+"的值："+entry.getValue());
					content = content.replace( entry.getKey(), entry.getValue());
				}
			}
			baseMessage.setContent(content);
		}
		
		if(Check.NuNStr(content)){
			return ;
		}
		
		//短信发送
		if(key.equals(MessageUtils.SMS_KEY)){
			String  mobileNationCode=baseMessage.getMobileNationCode();
			// 区分国内外发送短信
			LogUtil.info(logger, "当前短信手机号国际编码mobileNationCode={},短信发送手机号mobile={},发送内容smsContent={}",mobileNationCode,baseMessage.getTo(),baseMessage.getContent());
			if(SysConst.MOBILE_NATION_CODE.equals(mobileNationCode)){
				threadPoolExecutor.execute(new ThreadSmsSend(baseMessage,SystemGlobalsUtils.getValue(key)));
			}else{
				LogUtil.info(logger, "国外编码mobileNationCode={},手机号mobile={}",mobileNationCode,baseMessage.getTo());
			}
		}else{
			//邮件发送
			threadPoolExecutor.execute(new ThreadSmsSend(baseMessage,SystemGlobalsUtils.getValue(key)));
		}
		
	}

	public static void main(String[] args) {


		SmsMessage smsMessage = new SmsMessage("18911989053", " Hi，您在北京市的旅行已结束两天了，有没有安全到家呢？现在打开自如APP，把你的评价留给房东，民宿菌就送上5元返现哦！（http://h.ziroom.com/ayi3ev）");
		
		MailMessage mailMessage = new MailMessage("busj@ziroom.com", "{1}你好，你于{2}预定的房间，欢迎您准时入住，谢谢!","45645");
		mailMessage.setTitle("你好");

		Map<String, String> paramsMap = new HashMap<String, String>();

		paramsMap.put("{1}", "杨东");
		paramsMap.put("{2}", "2016-04-02");
		sendSms(smsMessage, paramsMap);
		
		//sendMail(mailMessage,paramsMap);

		try {
			Thread.sleep(999999999);
		} catch (InterruptedException e) {
			LogUtil.error(logger,"error:{}",e);
		}
	}
}
