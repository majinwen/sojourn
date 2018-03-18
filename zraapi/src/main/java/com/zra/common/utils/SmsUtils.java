package com.zra.common.utils;

import javax.annotation.concurrent.ThreadSafe;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;

import com.ziroom.platform.tesla.client.TeslaClientFactory;

/**
 * @author cuiyuhui
 * 发送短信
 * https://code.ziroom.com/tech-docs/manual/blob/master/message/API.md
 */
@ThreadSafe
public enum SmsUtils {
	
	INSTANCE;
	
	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(SmsUtils.class);
	
	
	/**
	 * 发送短信，支持多个手机号码
	 * 返回true短信发送成功 ，如果短信发送开关关闭，则直接返回true.
	 * 返回false短信发送失败
	 * @param phones
	 * @return 
	 */
	public boolean sendSMS(String content,String... phones){
		
		if("false".equals(PropUtils.getString(ZraApiConst.SMS_IS_SEND))){
			LOGGER.error("[短信发送]短信发送功能已关闭");
			return false;
		}
		
		if(phones == null || phones.length == 0){
			LOGGER.error("[短信发送]手机号为空，不能发送");
			return false;
		}
		
		//短信服务
		SMSRequestEntity smsEntity = SmsUtils.INSTANCE.buildSMSEntity(content,phones);
		WebTarget target = TeslaClientFactory.newDynamicClient("sms").target("");
		LOGGER.info("[短信发送]，短信地址："+target.getUri()+"，短信"+smsEntity);
		Response response = target.path("/api/sms/send").request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(smsEntity, MediaType.APPLICATION_JSON_TYPE));
		
		if(response.getStatus()<300){
			SMSResponseEntity smsResponseEntity= response.readEntity(SMSResponseEntity.class);
			//添加日志
			LOGGER.info("[短信发送],发送返回数据："+smsResponseEntity.toString());
			if(smsResponseEntity != null && "0".equals(smsResponseEntity.getResponse_code())){
				return true;
			}
		}else{
			String res = response.readEntity(String.class);
			LOGGER.error("[短信发送]调用短信接口失败："+res);
		}
		return false;
	}
	
	
	/**
	 * 构建SMSEntity实体
	 * @return
	 */
	public SMSRequestEntity buildSMSEntity(String content,String... phones){
		StringBuilder sb = new StringBuilder(22);
		for(String phone:phones){
			sb.append(phone);
			sb.append(",");
		}
		String to = sb.toString();
		String token = PropUtils.getString(ZraApiConst.SMS_TOKEN);
		String signature = PropUtils.getString(ZraApiConst.SMS_SIGNATURE_KEY);
		return SmsUtils.INSTANCE.new SMSRequestEntity(token, to, content, signature);
	}
	
	/**
	 * @author cuiyuhui
	 * 请求短信内部类实体
	 */
	private class SMSRequestEntity{
		
		private String token;
		
		private String to;
		
		private String content;
		
		private String signature;
		public SMSRequestEntity(String token, String to, String content, String signature) {
			this.token = token;
			this.to = to;
			this.content = content;
			this.signature = signature;
		}

		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public String getTo() {
			return to;
		}
		public void setTo(String to) {
			this.to = to;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getSignature() {
			return signature;
		}
		public void setSignature(String signature) {
			this.signature = signature;
		}

		@Override
		public String toString() {
			return "SMSRequestEntity [token=" + token + ", to=" + to + ", content=" + content + ", signature="
					+ signature + "]";
		}
	}
	
	/**
	 * @author cuiyuhui
	 * 短信响应实体
	 * 属性名不允这么写，我之所以这么写，是因为我不知道怎么修改。哈哈哈。。
	 */
	private static class SMSResponseEntity{
		
		private String serial_no;
		
		private String server_machine_name;
		
		private String server_ip;
		
		private String server_current_time;
		
		private String response_code;
		
		private String error_info;
		
		private String message_info;
		
		public  SMSResponseEntity(){
			
		}
		public String getSerial_no() {
			return serial_no;
		}

		public void setSerial_no(String serial_no) {
			this.serial_no = serial_no;
		}
		public String getServer_machine_name() {
			return server_machine_name;
		}
		public void setServer_machine_name(String server_machine_name) {
			this.server_machine_name = server_machine_name;
		}
		public String getServer_ip() {
			return server_ip;
		}
		public void setServer_ip(String server_ip) {
			this.server_ip = server_ip;
		}
		public String getServer_current_time() {
			return server_current_time;
		}
		public void setServer_current_time(String server_current_time) {
			this.server_current_time = server_current_time;
		}
		public String getResponse_code() {
			return response_code;
		}
		public void setResponse_code(String response_code) {
			this.response_code = response_code;
		}
		public String getError_info() {
			return error_info;
		}
		public void setError_info(String error_info) {
			this.error_info = error_info;
		}
		public String getMessage_info() {
			return message_info;
		}
		public void setMessage_info(String message_info) {
			this.message_info = message_info;
		}
		@Override
		public String toString() {
			return "SMSResponseEntity [serial_no=" + serial_no + ", server_machine_name=" + server_machine_name
					+ ", server_ip=" + server_ip + ", server_current_time=" + server_current_time + ", response_code="
					+ response_code + ", error_info=" + error_info + ",message_info="+message_info+"]";
		}
		
	}
	
	public static void main(String[] args) {
		SmsUtils.INSTANCE.sendSMS("你好", "18500469934");
	}
	
	
}
