
package com.ziroom.minsu.services.customer.entity;

import java.io.Serializable;

/**
 * <p>redis中保存的用户信息</p>
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
public class CustomerVo implements Serializable{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 3492363212589268883L;

	/**
	 * 用户uid
	 */
	private String uid;
	
	/**
	 * 真实姓名
	 */
	private String realName;
	
	/**
	 * 客户手机号
	 */
	private String customerMobile;
	
	/**
	 * 客户性别 1：男，2：女
	 */
	private String customerSex;
	
	/**
	 * 出生日期
	 */
	private String customerBirthday;
	
	/**
	 * 用户邮箱
	 */
	private String customerEmail;
	
	/**
	 * 证件类型(0=其他 1=身份证 2=护照 3=军官证 4=通行证 5=驾驶证 6=台胞证 7=社保卡 8=省份证 9=社保卡 10=学生证 11=回乡证 12=营业执照 13=港澳通行证 14户口本 15=居住证)
	 */
	private Integer idType;
	
	/**
	 * 证件号码
	 */
	private String idNo;
	
	/**
	 * 用户昵称
	 */
	private String nickName;
	
	/**
	 * 是否房东 0：否，1：是 根据是否发布房源更新 (一旦 是房东 最好能来更新 redis 不更新 在下次入库时 才会更新)
	 */
	private Integer isLandlord;
	
	/**
	 * 居住详细地址
	 */
	private String resideAddr;
	
	/**
	 * 客户学历(0=其他 1=小学 2=初中 3=高中 4=中技 5=中专 6=大专 7=本科 8=MBA 9=硕士 10=博士)
	 */
	private Integer customerEdu;
	/**
	 * 客户职业
	 */
	private String customerJob;
	/**
	 * 城市
	 */
	private String cityCode;
	
	/**
	 * 客户头像URL地址
	 */
	private String userPicUrl;
	
	
	/**
	 * 此token供mapp使用，目的就是去使自如token失效
	 */
	private String token;
	
	/**
	 * 分机号 供方文用
	 */
	private String ziroomPhone;

	/**
	 * 主机号
	 */
	private String hostNumber;
	
	/**
	 * 可展示的手机号
	 */
	private String showMobile;
	
	/**
	 * 用户自我介绍
	 */
	private String customerIntroduce;
	
	/**
	 * 国家码
	 */
	private String countryCode;
	
	

	/**
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * @return the customerIntroduce
	 */
	public String getCustomerIntroduce() {
		return customerIntroduce;
	}

	/**
	 * @param customerIntroduce the customerIntroduce to set
	 */
	public void setCustomerIntroduce(String customerIntroduce) {
		this.customerIntroduce = customerIntroduce;
	}

	public String getShowMobile() {
		return showMobile;
	}

	public void setShowMobile(String showMobile) {
		this.showMobile = showMobile;
	}

	public String getHostNumber() {
		return hostNumber;
	}

	public void setHostNumber(String hostNumber) {
		this.hostNumber = hostNumber;
	}

	public String getZiroomPhone() {
		return ziroomPhone;
	}

	public void setZiroomPhone(String ziroomPhone) {
		this.ziroomPhone = ziroomPhone;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getCustomerSex() {
		return customerSex;
	}

	public void setCustomerSex(String customerSex) {
		this.customerSex = customerSex;
	}

	public String getCustomerBirthday() {
		return customerBirthday;
	}

	public void setCustomerBirthday(String customerBirthday) {
		this.customerBirthday = customerBirthday;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getIsLandlord() {
		return isLandlord;
	}

	public void setIsLandlord(Integer isLandlord) {
		this.isLandlord = isLandlord;
	}

	public String getResideAddr() {
		return resideAddr;
	}

	public void setResideAddr(String resideAddr) {
		this.resideAddr = resideAddr;
	}

	public Integer getCustomerEdu() {
		return customerEdu;
	}

	public void setCustomerEdu(Integer customerEdu) {
		this.customerEdu = customerEdu;
	}

	public String getUserPicUrl() {
		return userPicUrl;
	}

	public void setUserPicUrl(String userPicUrl) {
		this.userPicUrl = userPicUrl;
	}

	public String getCustomerJob() {
		return customerJob;
	}

	public void setCustomerJob(String customerJob) {
		this.customerJob = customerJob;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	 
}
