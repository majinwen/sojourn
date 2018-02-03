
package com.ziroom.minsu.services.common.jpush.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.push.PushClient;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Message.Builder;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.PushPayload;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.common.utils.SystemGlobalsUtils;

/**
 * <p>极光推送发送任务
 * 说明：推送分：A. 1对1推送  B. 1对多推送  C. 给所有推送    A和B可以认为是一样的
 * </p>
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
public class JpushTask implements Runnable{

	private static Logger logger = LoggerFactory.getLogger(JpushTask.class);

	/**
	 * 极光配置
	 */
	private JpushConfig jpushConfig;

	/**
	 * 请求客户
	 */
	protected static PushClient _client = new PushClient(JpushConfig.MASTER_SECRET, JpushConfig.APP_KEY);

	public JpushTask(){};
	public JpushTask(JpushConfig jpushConfig){
		this.jpushConfig = jpushConfig;
		if(_client == null) _client = new PushClient(JpushConfig.MASTER_SECRET, JpushConfig.APP_KEY);
	}
	/* 
	 * @docment 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		PushPayload payload = null;
		if(!Check.NuNObj(jpushConfig.getMessageType())&&jpushConfig.getMessageType().intValue() == MessageTypeEnum.MESSAGE.getCode()){

			Builder builder  = Message.newBuilder().setMsgContent(jpushConfig.getContent()).setContentType("text").setTitle(jpushConfig.getTitle());
			if(!Check.NuNMap(jpushConfig.getExtrasMap())){
				builder.addExtras(jpushConfig.getExtrasMap());
			}
			payload = PushPayload.newBuilder()
					.setPlatform(jpushConfig.getPlatform())
					.setAudience(jpushConfig.getAudience())
					.setMessage(builder.build())
					.setOptions(Options.newBuilder()
							.setApnsProduction(true).build())
							.build();

		}
		/*else{
			payload = PushPayload.newBuilder() 
					.setPlatform(jpushConfig.getPlatform())
					.setAudience(jpushConfig.getAudience())
					.setNotification(Notification.newBuilder().addPlatformNotification(AndroidNotification.newBuilder().setAlert(jpushConfig.getContent()).setTitle(jpushConfig.getTitle()).build()).addPlatformNotification(WinphoneNotification.newBuilder().setAlert(jpushConfig.getContent()).setTitle(jpushConfig.getTitle()).build()).addPlatformNotification(IosNotification.newBuilder().setAlert(jpushConfig.getContent()).autoBadge().build()).build())
					.setMessage(Message.newBuilder().setMsgContent(jpushConfig.getContent()).setContentType("text").setTitle(jpushConfig.getTitle()).build())
					.setOptions(Options.newBuilder()
							.setApnsProduction(true).build())
							.build();
		}*/
		try {
			//PushResult result= _client.sendPush(payload);
			
			LogUtil.info(logger, "极光参数jpushConfig={}", JsonEntityTransform.Object2Json(jpushConfig));
			JpushEntity jpushEntity = new JpushEntity();
	    	jpushEntity.setToken(JpushConfig.JPUSH_TOKEN);
	    	
	    	jpushEntity.setAlias(jpushConfig.getAlias());
	    	jpushEntity.setContent(jpushConfig.getContent());
	    	jpushEntity.setExtras(jpushConfig.getExtrasMap());
	    	String title = jpushConfig.getTitle();
	    	if(Check.NuNStrStrict(title)){
	    		title = "民宿消息";
	    	}
	    	jpushEntity.setTitle(title);
	    	String[] platform = new String[]{"android","ios"};
	    	jpushEntity.setPlatform(platform);
	    	
	    	String result = CloseableHttpUtil.sendPost(SystemGlobalsUtils.getValue("JPUSH_SEND_URL"), JsonEntityTransform.Object2Json(jpushEntity));
	    	LogUtil.info(logger, "极光返回result={}", result);
		} catch (Exception e) {
			LogUtil.error(logger,"极光推送错误,error:{}",e);
		}
	}
	public JpushConfig getJpushConfig() {
		return jpushConfig;
	}
	public void setJpushConfig(JpushConfig jpushConfig) {
		this.jpushConfig = jpushConfig;
	}


}



