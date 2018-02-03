package com.ziroom.minsu.entity.house;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>房源商机信息扩展表实体类</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class HouseBusinessMsgExtEntity extends BaseEntity{
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = 2064911408387799353L;

	/**
     * 主键自增id
     * This field corresponds to the database column t_house_business_msg_ext.id
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    private Integer id;

    /**
     * 逻辑fid
     * This field corresponds to the database column t_house_business_msg_ext.fid
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    private String fid;

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

    /**
     * 维护管家经理姓名
     * This field corresponds to the database column t_house_business_msg_ext.manager_name
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    private String managerName;

    /**
     * 维护管家员工号
     * This field corresponds to the database column t_house_business_msg_ext.wh_guard_code
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    private String whGuardCode;

    /**
     * 维护管家姓名
     * This field corresponds to the database column t_house_business_msg_ext.wh_guard_name
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    private String whGuardName;

    /**
     *是否与房东见面沟通 0:否，1：是
     * This field corresponds to the database column t_house_business_msg_ext.is_meet
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    private Integer isMeet;

    /**
     * 当前进度 0：已获取房源信息，1：已获取联系方式，2：已沟通意向，3：已发布，4：管家驳回，5：品质驳回，6：已上架，7：已线下核验，8：已拍照
     * This field corresponds to the database column t_house_business_msg_ext.business_plan
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    private Integer businessPlan;

    /**
     * 驳回原因 0：房东挂错，1：品质不符无法升级，2：品质不符需要升级，3：其他
     * This field corresponds to the database column t_house_business_msg_ext.reject_reason
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    private Integer rejectReason;

    /**
     * 跟进内容
     * This field corresponds to the database column t_house_business_msg_ext.follow_content
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    private String followContent;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg_ext.id
     *
     * @return the value of t_house_business_msg_ext.id
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg_ext.id
     *
     * @param id the value for t_house_business_msg_ext.id
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg_ext.fid
     *
     * @return the value of t_house_business_msg_ext.fid
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public String getFid() {
        return fid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg_ext.fid
     *
     * @param fid the value for t_house_business_msg_ext.fid
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg_ext.business_fid
     *
     * @return the value of t_house_business_msg_ext.business_fid
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public String getBusinessFid() {
        return businessFid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg_ext.business_fid
     *
     * @param businessFid the value for t_house_business_msg_ext.business_fid
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public void setBusinessFid(String businessFid) {
        this.businessFid = businessFid == null ? null : businessFid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg_ext.dt_guard_code
     *
     * @return the value of t_house_business_msg_ext.dt_guard_code
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public String getDtGuardCode() {
        return dtGuardCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg_ext.dt_guard_code
     *
     * @param dtGuardCode the value for t_house_business_msg_ext.dt_guard_code
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public void setDtGuardCode(String dtGuardCode) {
        this.dtGuardCode = dtGuardCode == null ? null : dtGuardCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg_ext.dt_guard_name
     *
     * @return the value of t_house_business_msg_ext.dt_guard_name
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public String getDtGuardName() {
        return dtGuardName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg_ext.dt_guard_name
     *
     * @param dtGuardName the value for t_house_business_msg_ext.dt_guard_name
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public void setDtGuardName(String dtGuardName) {
        this.dtGuardName = dtGuardName == null ? null : dtGuardName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg_ext.landlord_name
     *
     * @return the value of t_house_business_msg_ext.landlord_name
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public String getLandlordName() {
        return landlordName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg_ext.landlord_name
     *
     * @param landlordName the value for t_house_business_msg_ext.landlord_name
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public void setLandlordName(String landlordName) {
        this.landlordName = landlordName == null ? null : landlordName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg_ext.landlord_mobile
     *
     * @return the value of t_house_business_msg_ext.landlord_mobile
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public String getLandlordMobile() {
        return landlordMobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg_ext.landlord_mobile
     *
     * @param landlordMobile the value for t_house_business_msg_ext.landlord_mobile
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public void setLandlordMobile(String landlordMobile) {
        this.landlordMobile = landlordMobile == null ? null : landlordMobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg_ext.landlord_nick_name
     *
     * @return the value of t_house_business_msg_ext.landlord_nick_name
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public String getLandlordNickName() {
        return landlordNickName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg_ext.landlord_nick_name
     *
     * @param landlordNickName the value for t_house_business_msg_ext.landlord_nick_name
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public void setLandlordNickName(String landlordNickName) {
        this.landlordNickName = landlordNickName == null ? null : landlordNickName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg_ext.landlord_qq
     *
     * @return the value of t_house_business_msg_ext.landlord_qq
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public String getLandlordQq() {
        return landlordQq;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg_ext.landlord_qq
     *
     * @param landlordQq the value for t_house_business_msg_ext.landlord_qq
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public void setLandlordQq(String landlordQq) {
        this.landlordQq = landlordQq == null ? null : landlordQq.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg_ext.landlord_wechat
     *
     * @return the value of t_house_business_msg_ext.landlord_wechat
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public String getLandlordWechat() {
        return landlordWechat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg_ext.landlord_wechat
     *
     * @param landlordWechat the value for t_house_business_msg_ext.landlord_wechat
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public void setLandlordWechat(String landlordWechat) {
        this.landlordWechat = landlordWechat == null ? null : landlordWechat.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg_ext.landlord_email
     *
     * @return the value of t_house_business_msg_ext.landlord_email
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public String getLandlordEmail() {
        return landlordEmail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg_ext.landlord_email
     *
     * @param landlordEmail the value for t_house_business_msg_ext.landlord_email
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public void setLandlordEmail(String landlordEmail) {
        this.landlordEmail = landlordEmail == null ? null : landlordEmail.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg_ext.landlord_type
     *
     * @return the value of t_house_business_msg_ext.landlord_type
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public Integer getLandlordType() {
        return landlordType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg_ext.landlord_type
     *
     * @param landlordType the value for t_house_business_msg_ext.landlord_type
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public void setLandlordType(Integer landlordType) {
        this.landlordType = landlordType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg_ext.manager_code
     *
     * @return the value of t_house_business_msg_ext.manager_code
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public String getManagerCode() {
        return managerCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg_ext.manager_code
     *
     * @param managerCode the value for t_house_business_msg_ext.manager_code
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public void setManagerCode(String managerCode) {
        this.managerCode = managerCode == null ? null : managerCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg_ext.manager_name
     *
     * @return the value of t_house_business_msg_ext.manager_name
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public String getManagerName() {
        return managerName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg_ext.manager_name
     *
     * @param managerName the value for t_house_business_msg_ext.manager_name
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public void setManagerName(String managerName) {
        this.managerName = managerName == null ? null : managerName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg_ext.wh_guard_code
     *
     * @return the value of t_house_business_msg_ext.wh_guard_code
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public String getWhGuardCode() {
        return whGuardCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg_ext.wh_guard_code
     *
     * @param whGuardCode the value for t_house_business_msg_ext.wh_guard_code
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public void setWhGuardCode(String whGuardCode) {
        this.whGuardCode = whGuardCode == null ? null : whGuardCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg_ext.wh_guard_name
     *
     * @return the value of t_house_business_msg_ext.wh_guard_name
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public String getWhGuardName() {
        return whGuardName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg_ext.wh_guard_name
     *
     * @param whGuardName the value for t_house_business_msg_ext.wh_guard_name
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public void setWhGuardName(String whGuardName) {
        this.whGuardName = whGuardName == null ? null : whGuardName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg_ext.is_meet
     *
     * @return the value of t_house_business_msg_ext.is_meet
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public Integer getIsMeet() {
        return isMeet;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg_ext.is_meet
     *
     * @param isMeet the value for t_house_business_msg_ext.is_meet
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public void setIsMeet(Integer isMeet) {
        this.isMeet = isMeet;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg_ext.business_plan
     *
     * @return the value of t_house_business_msg_ext.business_plan
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public Integer getBusinessPlan() {
        return businessPlan;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg_ext.business_plan
     *
     * @param businessPlan the value for t_house_business_msg_ext.business_plan
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public void setBusinessPlan(Integer businessPlan) {
        this.businessPlan = businessPlan;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg_ext.reject_reason
     *
     * @return the value of t_house_business_msg_ext.reject_reason
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public Integer getRejectReason() {
        return rejectReason;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg_ext.reject_reason
     *
     * @param rejectReason the value for t_house_business_msg_ext.reject_reason
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public void setRejectReason(Integer rejectReason) {
        this.rejectReason = rejectReason;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_house_business_msg_ext.follow_content
     *
     * @return the value of t_house_business_msg_ext.follow_content
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public String getFollowContent() {
        return followContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_house_business_msg_ext.follow_content
     *
     * @param followContent the value for t_house_business_msg_ext.follow_content
     *
     * @mbggenerated Tue Jul 05 16:34:44 CST 2016
     */
    public void setFollowContent(String followContent) {
        this.followContent = followContent == null ? null : followContent.trim();
    }
}