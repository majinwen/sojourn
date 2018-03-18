/**
 * @FileName: MsgAdvisoryFollowVo.java
 * @Package com.ziroom.minsu.services.message.entity
 * 
 * @author lunan
 * @created 2017年5月25日 下午1:09:43
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.message.vo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * <p>IM跟进展示Vo</p>
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
public class MsgAdvisoryFollowVo extends BaseEntity{

	/**
	 * 
	 */
	@FieldMeta(skip = true)
	private static final long serialVersionUID = 3302727700330673674L;
	

	
	/**
	 * 预订人uid
	 */
	private String tenantUid;
	
	/**
	 * 预订人姓名
	 */
	 @FieldMeta(name = "预订人姓名", order = 1)
	private String tenantName;
	
	/**
	 * 预订人电话
	 */
	 @FieldMeta(name = "预订手机", order = 2)
	private String tenantTel;
	
	/**
	 * 房源名称
	 */
	 @FieldMeta(name = "房源名称", order = 3)
	private String houseName;
	
	/**
	 * 房东uid
	 */
	private String landlordUid;
	/**
	 * 城市name
	 */
	@FieldMeta(name = "城市", order = 4)
	private String cityName;
	
	/**
	 * 房东姓名
	 */
	@FieldMeta(name = "房东姓名", order =5 )
	private String landlordName;
	
	/**
	 * 房东电话 
	 */
	@FieldMeta(name = "房东手机", order =6 )
	private String landlordTel;
	
	/**
	 * 跟进状态
	 */
	private String followStatus;
	
	/**
	 * 跟进状态
	 */
	@FieldMeta(name = "跟进状态", order =8 )
	private String followStatusName;
	
	/**
	 * 城市code
	 */
	private String cityCode;
	
	

	/**
	 * 房源或房间fid(包括整租分租的)
	 */
	private String houseFid;
	
	/**
	 * 出租方式(包括整租分租的)
	 */
	private Integer rentWay;
	
	/**
	 *创建时间
	 */
	private Date createTime;
	
	/**
	 *创建时间
	 */
	@FieldMeta(name = "首咨创建时间", order =7 )
	private String createTimeStr;
	
	/**
	 * 消息baseFid
	 */
	private String msgBaseFid;
	
	/**
	 * 房源留言关联表Fid
	 */
	private String msgHouseFid;
	
	/**
	 * 首次咨询表fid
	 */
	private String msgFirstAdvisoryFid;
	
	/**
	 * 操作人姓名
	 */
	
	private String empName;
	
	/**
	 * 预订人昵称
	 */
	
	private String nickName;
	
	/**
	 * 预订人昵称邮箱
	 */
	
	private String tenantEmail;
	
	
	/**
	 * 操作人姓名集合
	 */
	@FieldMeta(name = "所有跟进人", order = 9)
	private String empNameList;
	
	/**
	 * 跟进人及跟进记录
	 */
    private List<Map<String, Object>> empList; 
	
	/**
	 * 房东首次回复时间
	 */
    private Date landLordFirstReplyTime;
	
	/**
	 * 房东首次回复时间
	 */
	@FieldMeta(name = "房东首次回复时间 ", order = 10)
    private String landLordFirstReplyTimeStr;
	
	/**
	 * 房东回复间隔时间
	 */
    private Integer replayTimeHouse;
	
	/**
	 * 首次跟进时间
	 */
	private Date firstFollowTime;
	
	/**
	 * 首次跟进时间
	 */
	@FieldMeta(name="首次跟进时间", order = 11)
	private String firstFollowTimeStr;
	
	/**
	 *跟进人，时间及内容
	 */
	@FieldMeta(name = "所有跟进内容", order = 12)
	private String allFollowRemark;
	
	/**
	 * 操作人姓名集合
	 */
/*	@FieldMeta(name = "所有跟进时间", order = 13)
	private String allFollowTime;*/
	

	public MsgAdvisoryFollowVo() {
		super();
	}


	
	public MsgAdvisoryFollowVo(String tenantUid, String tenantName,
			String tenantTel, String houseName, String landlordUid,
			String cityName, String landlordName, String landlordTel,
			String followStatus, String followStatusName, String cityCode,
			String houseFid, Integer rentWay, Date createTime,
			String createTimeStr, String msgBaseFid, String msgHouseFid,
			String msgFirstAdvisoryFid, String empName, String nickName,
			String tenantEmail, String empNameList,
			List<Map<String, Object>> empList, Date landLordFirstReplyTime,
			String landLordFirstReplyTimeStr, Integer replayTimeHouse,
			Date firstFollowTime, String firstFollowTimeStr,
			String allFollowRemark) {
		super();
		this.tenantUid = tenantUid;
		this.tenantName = tenantName;
		this.tenantTel = tenantTel;
		this.houseName = houseName;
		this.landlordUid = landlordUid;
		this.cityName = cityName;
		this.landlordName = landlordName;
		this.landlordTel = landlordTel;
		this.followStatus = followStatus;
		this.followStatusName = followStatusName;
		this.cityCode = cityCode;
		this.houseFid = houseFid;
		this.rentWay = rentWay;
		this.createTime = createTime;
		this.createTimeStr = createTimeStr;
		this.msgBaseFid = msgBaseFid;
		this.msgHouseFid = msgHouseFid;
		this.msgFirstAdvisoryFid = msgFirstAdvisoryFid;
		this.empName = empName;
		this.nickName = nickName;
		this.tenantEmail = tenantEmail;
		this.empNameList = empNameList;
		this.empList = empList;
		this.landLordFirstReplyTime = landLordFirstReplyTime;
		this.landLordFirstReplyTimeStr = landLordFirstReplyTimeStr;
		this.replayTimeHouse = replayTimeHouse;
		this.firstFollowTime = firstFollowTime;
		this.firstFollowTimeStr = firstFollowTimeStr;
		this.allFollowRemark = allFollowRemark;
	}



	public String getTenantUid() {
		return tenantUid;
	}

	
	public void setTenantUid(String tenantUid) {
		this.tenantUid = tenantUid;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getTenantTel() {
		return tenantTel;
	}

	public void setTenantTel(String tenantTel) {
		this.tenantTel = tenantTel;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getLandlordName() {
		return landlordName;
	}

	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}

	public String getLandlordTel() {
		return landlordTel;
	}

	public void setLandlordTel(String landlordTel) {
		this.landlordTel = landlordTel;
	}

	public String getFollowStatus() {
		return followStatus;
	}

	public void setFollowStatus(String followStatus) {
		this.followStatus = followStatus;
	}

	public String getFollowStatusName() {
		return followStatusName;
	}

	public void setFollowStatusName(String followStatusName) {
		this.followStatusName = followStatusName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getHouseFid() {
		return houseFid;
	}

	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getMsgBaseFid() {
		return msgBaseFid;
	}

	public void setMsgBaseFid(String msgBaseFid) {
		this.msgBaseFid = msgBaseFid;
	}

	public String getMsgHouseFid() {
		return msgHouseFid;
	}

	public void setMsgHouseFid(String msgHouseFid) {
		this.msgHouseFid = msgHouseFid;
	}

	public String getMsgFirstAdvisoryFid() {
		return msgFirstAdvisoryFid;
	}

	public void setMsgFirstAdvisoryFid(String msgFirstAdvisoryFid) {
		this.msgFirstAdvisoryFid = msgFirstAdvisoryFid;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getTenantEmail() {
		return tenantEmail;
	}

	public void setTenantEmail(String tenantEmail) {
		this.tenantEmail = tenantEmail;
	}

	public String getEmpNameList() {
		return empNameList;
	}

	public void setEmpNameList(String empNameList) {
		this.empNameList = empNameList;
	}

	public List<Map<String, Object>> getEmpList() {
		return empList;
	}

	public void setEmpList(List<Map<String, Object>> empList) {
		this.empList = empList;
	}

	public Date getLandLordFirstReplyTime() {
		return landLordFirstReplyTime;
	}

	public void setLandLordFirstReplyTime(Date landLordFirstReplyTime) {
		this.landLordFirstReplyTime = landLordFirstReplyTime;
	}

	public String getLandLordFirstReplyTimeStr() {
		return landLordFirstReplyTimeStr;
	}

	public void setLandLordFirstReplyTimeStr(String landLordFirstReplyTimeStr) {
		this.landLordFirstReplyTimeStr = landLordFirstReplyTimeStr;
	}

	public Integer getReplayTimeHouse() {
		return replayTimeHouse;
	}

	public void setReplayTimeHouse(Integer replayTimeHouse) {
		this.replayTimeHouse = replayTimeHouse;
	}

	public Date getFirstFollowTime() {
		return firstFollowTime;
	}

	public void setFirstFollowTime(Date firstFollowTime) {
		this.firstFollowTime = firstFollowTime;
	}

	public String getFirstFollowTimeStr() {
		return firstFollowTimeStr;
	}

	public void setFirstFollowTimeStr(String firstFollowTimeStr) {
		this.firstFollowTimeStr = firstFollowTimeStr;
	}
	
	


}
