package com.ziroom.minsu.services.customer.entity;

import java.util.List;

import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.services.common.entity.NameValuePair;

/**
 * 
 * <p>个人中心-个人资料值对象</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class CustomerBaseMsgVo extends CustomerBaseMsgEntity{

	/**
	 * 序列化对象
	 */
	private static final long serialVersionUID = -1345821386818370117L;
	
	/**
	 * 教育背景
	 */
	private String customerEduName;
	
	/**
	 * 教育背景列表
	 */
	private List<NameValuePair<String,Integer>> eduList;
	
	/**
	 * 性别
	 */
	private String sexName;
	
	/**
	 * 性别列表
	 */
	private List<NameValuePair<String,Integer>> sexList;
	
	/**
	 * 个人介绍是否完成
	 */
	private String hasIntroduce;
	
	/**
	 * 生日
	 */
    private String customerBirthdayStr;
    
	/**
	 * 个人介绍审核信息
	 */
    private String introduceAuditMsg;
    
    /**
	 * 昵称审核信息
	 */
    private String nickNameAuditMsg;

	/**
	 * @return the introduceAuditMsg
	 */
	public String getIntroduceAuditMsg() {
		return introduceAuditMsg;
	}

	/**
	 * @param introduceAuditMsg the introduceAuditMsg to set
	 */
	public void setIntroduceAuditMsg(String introduceAuditMsg) {
		this.introduceAuditMsg = introduceAuditMsg;
	}

	public String getCustomerEduName() {
		return customerEduName;
	}

	public void setCustomerEduName(String customerEduName) {
		this.customerEduName = customerEduName;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public String getHasIntroduce() {
		return hasIntroduce;
	}

	public void setHasIntroduce(String hasIntroduce) {
		this.hasIntroduce = hasIntroduce;
	}

	public List<NameValuePair<String,Integer>> getEduList() {
		return eduList;
	}

	public void setEduList(List<NameValuePair<String,Integer>> eduList) {
		this.eduList = eduList;
	}

	public List<NameValuePair<String,Integer>> getSexList() {
		return sexList;
	}

	public void setSexList(List<NameValuePair<String,Integer>> sexList) {
		this.sexList = sexList;
	}

	public String getCustomerBirthdayStr() {
		return customerBirthdayStr;
	}

	public void setCustomerBirthdayStr(String customerBirthdayStr) {
		this.customerBirthdayStr = customerBirthdayStr;
	}

	public String getNickNameAuditMsg() {
		return nickNameAuditMsg;
	}

	public void setNickNameAuditMsg(String nickNameAuditMsg) {
		this.nickNameAuditMsg = nickNameAuditMsg;
	}
	
}
