/**
 * @FileName: ImExtForChangzuVo.java
 * @Package com.ziroom.minsu.services.message.entity
 * 
 * @author loushuai
 * @created 2017年8月31日 下午5:46:22
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.entity;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>长租imExt封装对象</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public class ImExtForChangzuVo extends BaseEntity{

	 /**
	 * 
	 */
	private static final long serialVersionUID = -8684396786294590756L;

    /**
	 * 图片是否合规
	*/
	private Integer isPicNeedVagueDeal;
	
	/**
	 * showMsgTxt 列表页展示文案
	*/
	private String showMsgTxt;
  
	/**
     * 自如网标识  ZIROOM_MINSU_IM= 代表民宿 ZIROOM_ZRY_IM= 自如驿  ZIROOM_CHANGZU_IM= 自如长租
     */
    private String ziroomFlag;
    
    /**
     * 同步环信聊天记录环境标识 （minsu_d  minsu_t  minsu_q minsu_online）
     */
    private String domainFlag;
    
    /**
     * 文本消息类型（100：一般聊天文本消息， 101：打招呼消息， 102：卡片消息， 103：活动消息，200：屏蔽消息 201：取消屏蔽消息 202：投诉消息）
     */
    private Integer ziroomType; 
	
    /**
     * 卡片消息或活动消息跳转地址
     */
    private String linkUrl; 
    
    /**
     * 卡片消息或活动消息“文字说明”
     */
    private String content; 
    
    /**
     * 活动名称，只有活动消息才有
     */
    private String activityName; 
    
    /**
     * 活动图片地址，只有活动消息才有
     */
    private String activityPicUrl;
    
    /**
     * 活动地址，只有活动消息才有
     */
    private String activityAddr; 
    
    /**
     * 活动日期，只有活动消息才有
     */
    private String activityDate; 
   
    /**
     * 活动星期，只有活动消息才有
     */
    private String activityWeek;
    
    /**
     * 环信 消息id
     */
    private String huanxinMsgId;

    /**
     * from的头像
     */
    private String headUrl;
    
    /**
     * from昵称
     */
    private String nicName;
    
    /**
     * to的头像
     */
    private String toHeadUrl;
    
    /**
     * to的昵称
     */
    private String toNicName;
    
    /**
     * 动画表情的文件名
     */
    private String gifFileName;
    
    
	public Integer getIsPicNeedVagueDeal() {
		return isPicNeedVagueDeal;
	}

	public void setIsPicNeedVagueDeal(Integer isPicNeedVagueDeal) {
		this.isPicNeedVagueDeal = isPicNeedVagueDeal;
	}

	public String getShowMsgTxt() {
		return showMsgTxt;
	}

	public void setShowMsgTxt(String showMsgTxt) {
		this.showMsgTxt = showMsgTxt;
	}

	public String getZiroomFlag() {
		return ziroomFlag;
	}

	public void setZiroomFlag(String ziroomFlag) {
		this.ziroomFlag = ziroomFlag;
	}

	public String getDomainFlag() {
		return domainFlag;
	}

	public void setDomainFlag(String domainFlag) {
		this.domainFlag = domainFlag;
	}

	public Integer getZiroomType() {
		return ziroomType;
	}

	public void setZiroomType(Integer ziroomType) {
		this.ziroomType = ziroomType;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityPicUrl() {
		return activityPicUrl;
	}

	public void setActivityPicUrl(String activityPicUrl) {
		this.activityPicUrl = activityPicUrl;
	}

	public String getActivityAddr() {
		return activityAddr;
	}

	public void setActivityAddr(String activityAddr) {
		this.activityAddr = activityAddr;
	}

	public String getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(String activityDate) {
		this.activityDate = activityDate;
	}

	public String getActivityWeek() {
		return activityWeek;
	}

	public void setActivityWeek(String activityWeek) {
		this.activityWeek = activityWeek;
	}

	public String getHuanxinMsgId() {
		return huanxinMsgId;
	}

	public void setHuanxinMsgId(String huanxinMsgId) {
		this.huanxinMsgId = huanxinMsgId;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getNicName() {
		return nicName;
	}

	public void setNicName(String nicName) {
		this.nicName = nicName;
	}

	public String getToHeadUrl() {
		return toHeadUrl;
	}

	public void setToHeadUrl(String toHeadUrl) {
		this.toHeadUrl = toHeadUrl;
	}

	public String getToNicName() {
		return toNicName;
	}

	public void setToNicName(String toNicName) {
		this.toNicName = toNicName;
	}

	public String getGifFileName() {
		return gifFileName;
	}

	public void setGifFileName(String gifFileName) {
		this.gifFileName = gifFileName;
	}
    
}
