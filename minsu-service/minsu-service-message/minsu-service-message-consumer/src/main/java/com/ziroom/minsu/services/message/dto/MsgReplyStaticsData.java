package com.ziroom.minsu.services.message.dto;

import java.io.Serializable;

public class MsgReplyStaticsData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2956778544511642176L;

	/**
	 * 已经回复的消息条数
	 */
	private Integer repliedMsgNum;
	
	/**
	 * 30min内回复的消息条数
	 */
	private Integer replied30MsgNum;
	
	/**
	 * 总咨询条数
	 */
	private Integer totalMsgNum;
	
	/**
	 * 最短回复时长
	 */
	private Integer minRepliedTime;
	
	/**
	 * 最长回复时长
	 */
	private Integer maxRepliedTime;
	
	
	/**
	 * 平均回复时长
	 */
	private Integer avgRepliedTime;

	
	public Integer getReplied30MsgNum() {
		return replied30MsgNum==null?0:replied30MsgNum;
	}
	
	
	public void setReplied30MsgNum(Integer replied30MsgNum) {
		this.replied30MsgNum = replied30MsgNum;
	}


	public Integer getRepliedMsgNum() {
		return repliedMsgNum==null?0:repliedMsgNum;
	}


	public Integer getTotalMsgNum() {
		return totalMsgNum==null?0:totalMsgNum;
	}


	public Integer getMinRepliedTime() {
		return minRepliedTime==null?0:minRepliedTime;
	}


	public Integer getMaxRepliedTime() {
		return maxRepliedTime==null?0:maxRepliedTime;
	}


	public Integer getAvgRepliedTime() {
		return avgRepliedTime==null?0:avgRepliedTime;
	}


	public void setRepliedMsgNum(Integer repliedMsgNum) {
		this.repliedMsgNum = repliedMsgNum;
	}


	public void setTotalMsgNum(Integer totalMsgNum) {
		this.totalMsgNum = totalMsgNum;
	}


	public void setMinRepliedTime(Integer minRepliedTime) {
		this.minRepliedTime = minRepliedTime;
	}


	public void setMaxRepliedTime(Integer maxRepliedTime) {
		this.maxRepliedTime = maxRepliedTime;
	}


	public void setAvgRepliedTime(Integer avgRepliedTime) {
		this.avgRepliedTime = avgRepliedTime;
	}
	
}
