package com.ziroom.zrp.trading.entity;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;
/**
 * <p>企业签约人信息</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年9月14日
 * @since 1.0
 */
public class RentEpsCustomerEntity extends BaseEntity{
	
	private static final long serialVersionUID = -8334313444845320237L;

	/**
     * 主键id
     */
    private String id;

    /**
     * 企业组织机构码
     */
    private String code;

    /**
     * 企业名称
     */
    private String name;

    /**
     * 公司、单位地址
     */
    private String address;

    /**
     * 联系人邮箱
     */
    private String email;

    /**
     * 营业执照号
     */
    private String businessLicense;

    /**
     * 租房事务联系人证件号
     */
    private String contacterNum;

    /**
     * 租房事务联系人
     */
    private String contacter;

    /**
     * 联系电话
     */
    private String contacterTel;

    /**
     * 委托书照片地址
     */
    private String proxyPicurl;

    /**
     * 营业执照复印件地址
     */
    private String licensePicurl03;

    /**
     * 营业执照复印件地址
     */
    private String licensePicurl02;

    /**
     * 营业执照复印件地址
     */
    private String licensePicurl01;

    /**
     * 是否删除 0：否  1：是
     */
    private Integer isDeleted;

    /**
     * 删除人
     */
    private String deleterId;

    /**
     * 删除时间
     */
    private Date deletedTime;

    /**
     * 创建人
     */
    private String createrId;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新人
     */
    private String updaterId;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 友家客户库
     */
    private String customerUid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense == null ? null : businessLicense.trim();
    }

    public String getContacterNum() {
        return contacterNum;
    }

    public void setContacterNum(String contacterNum) {
        this.contacterNum = contacterNum == null ? null : contacterNum.trim();
    }

    public String getContacter() {
        return contacter;
    }

    public void setContacter(String contacter) {
        this.contacter = contacter == null ? null : contacter.trim();
    }

    public String getContacterTel() {
        return contacterTel;
    }

    public void setContacterTel(String contacterTel) {
        this.contacterTel = contacterTel == null ? null : contacterTel.trim();
    }

    public String getProxyPicurl() {
        return proxyPicurl;
    }

    public void setProxyPicurl(String proxyPicurl) {
        this.proxyPicurl = proxyPicurl == null ? null : proxyPicurl.trim();
    }

    public String getLicensePicurl03() {
        return licensePicurl03;
    }

    public void setLicensePicurl03(String licensePicurl03) {
        this.licensePicurl03 = licensePicurl03 == null ? null : licensePicurl03.trim();
    }

    public String getLicensePicurl02() {
        return licensePicurl02;
    }

    public void setLicensePicurl02(String licensePicurl02) {
        this.licensePicurl02 = licensePicurl02 == null ? null : licensePicurl02.trim();
    }

    public String getLicensePicurl01() {
        return licensePicurl01;
    }

    public void setLicensePicurl01(String licensePicurl01) {
        this.licensePicurl01 = licensePicurl01 == null ? null : licensePicurl01.trim();
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getDeleterId() {
        return deleterId;
    }

    public void setDeleterId(String deleterId) {
        this.deleterId = deleterId == null ? null : deleterId.trim();
    }

    public Date getDeletedTime() {
        return deletedTime;
    }

    public void setDeletedTime(Date deletedTime) {
        this.deletedTime = deletedTime;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId == null ? null : createrId.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId == null ? null : updaterId.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCustomerUid() {
        return customerUid;
    }

    public void setCustomerUid(String customerUid) {
        this.customerUid = customerUid;
    }
}