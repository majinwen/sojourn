/**
 * @FileName: HouseBusinessMsgExtDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author yd
 * @created 2016年7月9日 下午2:24:04
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>商机扩展查询条件</p>
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
public class HouseBusinessMsgExtDto  extends PageRequest{

	 /**
	 * 序列id
	 */
	private static final long serialVersionUID = -2159439699553434982L;

	/**
     * 商机主表fid
     * This field corresponds to the database column t_house_business_msg_ext.business_fid
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    private String businessFid;

    /**
     * 地推管家员工号
     * This field corresponds to the database column t_house_business_msg_ext.dt_guard_code
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    private String dtGuardCode;

    /**
     * 地推管家姓名
     * This field corresponds to the database column t_house_business_msg_ext.dt_guard_name
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    private String dtGuardName;

    /**
     * 房东姓名
     * This field corresponds to the database column t_house_business_msg_ext.landlord_name
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    private String landlordName;

    /**
     * 房东手机号
     * This field corresponds to the database column t_house_business_msg_ext.landlord_mobile
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    private String landlordMobile;

    /**
     * 房东昵称
     * This field corresponds to the database column t_house_business_msg_ext.landlord_nick_name
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    private String landlordNickName;

    /**
     * 房东qq号
     * This field corresponds to the database column t_house_business_msg_ext.landlord_qq
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    private String landlordQq;

    /**
     * 房东微信
     * This field corresponds to the database column t_house_business_msg_ext.landlord_wechat
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    private String landlordWechat;

    /**
     * 房东邮箱
     * This field corresponds to the database column t_house_business_msg_ext.landlord_email
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    private String landlordEmail;

    /**
     * 房东类型 0:专业型 1:半专业型 2:体验型
     * This field corresponds to the database column t_house_business_msg_ext.landlord_type
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    private Integer landlordType;

    /**
     * 维护管家经理员工号
     * This field corresponds to the database column t_house_business_msg_ext.manager_code
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    private String managerCode;

	public String getBusinessFid() {
		return businessFid;
	}

	public void setBusinessFid(String businessFid) {
		this.businessFid = businessFid;
	}

	public String getDtGuardCode() {
		return dtGuardCode;
	}

	public void setDtGuardCode(String dtGuardCode) {
		this.dtGuardCode = dtGuardCode;
	}

	public String getDtGuardName() {
		return dtGuardName;
	}

	public void setDtGuardName(String dtGuardName) {
		this.dtGuardName = dtGuardName;
	}

	public String getLandlordName() {
		return landlordName;
	}

	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}

	public String getLandlordMobile() {
		return landlordMobile;
	}

	public void setLandlordMobile(String landlordMobile) {
		this.landlordMobile = landlordMobile;
	}

	public String getLandlordNickName() {
		return landlordNickName;
	}

	public void setLandlordNickName(String landlordNickName) {
		this.landlordNickName = landlordNickName;
	}

	public String getLandlordQq() {
		return landlordQq;
	}

	public void setLandlordQq(String landlordQq) {
		this.landlordQq = landlordQq;
	}

	public String getLandlordWechat() {
		return landlordWechat;
	}

	public void setLandlordWechat(String landlordWechat) {
		this.landlordWechat = landlordWechat;
	}

	public String getLandlordEmail() {
		return landlordEmail;
	}

	public void setLandlordEmail(String landlordEmail) {
		this.landlordEmail = landlordEmail;
	}

	public Integer getLandlordType() {
		return landlordType;
	}

	public void setLandlordType(Integer landlordType) {
		this.landlordType = landlordType;
	}

	public String getManagerCode() {
		return managerCode;
	}

	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}
    
    
    

}
