/**
 * @FileName: SendImMsgRequest.java
 * @Package com.ziroom.minsu.services.message.utils.base
 * 
 * @author yd
 * @created 2017年4月8日 下午3:08:19
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.job.im;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Map;

/**
 * <p>给im发送消息请求参数</p>
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
public class SendImMsgRequest extends BaseEntity{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 4374939867935367213L;

	/**
	 * users 给用户发消息。chatgroups: 给群发消息，chatrooms: 给聊天室发消息
	 */
	private String target_type;
	
	/**
	 * 注意这里需要用数组，数组长度建议不大于20，即使只有一个用户，
     * 也要用数组 ['u1']，给用户发送时数组元素是用户名，给群组发送时  
     *  数组元素是groupid
	 */
	private String[] target;
	
	/**
	 * 消息内容
	 */
	private String msg;
	
	/**
	 * 表示消息发送者。无此字段Server会默认设置为"from":"admin"，有from字段但值为空串("")时请求失败
	 */
	private String from;
	
	/**
	 * 扩展消息map
	 */
	private Map<String, Object> extMap;

	/**
	 * @return the extMap
	 */
	public Map<String, Object> getExtMap() {
		return extMap;
	}

	/**
	 * @param extMap the extMap to set
	 */
	public void setExtMap(Map<String, Object> extMap) {
		this.extMap = extMap;
	}

	/**
	 * @return the target_type
	 */
	public String getTarget_type() {
		return target_type;
	}

	/**
	 * @param target_type the target_type to set
	 */
	public void setTarget_type(String target_type) {
		this.target_type = target_type;
	}

	/**
	 * @return the target
	 */
	public String[] getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(String[] target) {
		this.target = target;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}
	
	
}
