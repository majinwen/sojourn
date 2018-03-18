package com.ziroom.zrp.service.trading.dto;

import com.asura.framework.base.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年10月18日 11:52
 * @since 1.0
 */
public class EnterpriseCustomerDto extends BaseEntity implements Serializable {

    /**
     *父合同id
     */
    private String surParentRentId;


    /**
     * 客户id
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
     * 创建人
     */
    private String createrId;

    /**
     * 更新人
     */
    private String updaterId;

    /**
     * 友家客户库uid
     */

    private String customerUid;

    public EnterpriseCustomerDto() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSurParentRentId() {
        return surParentRentId;
    }

    public void setSurParentRentId(String surParentRentId) {
        this.surParentRentId = surParentRentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getContacterNum() {
        return contacterNum;
    }

    public void setContacterNum(String contacterNum) {
        this.contacterNum = contacterNum;
    }

    public String getContacter() {
        return contacter;
    }

    public void setContacter(String contacter) {
        this.contacter = contacter;
    }

    public String getContacterTel() {
        return contacterTel;
    }

    public void setContacterTel(String contacterTel) {
        this.contacterTel = contacterTel;
    }

    public String getProxyPicurl() {
        return proxyPicurl;
    }

    public void setProxyPicurl(String proxyPicurl) {
        this.proxyPicurl = proxyPicurl;
    }

    public String getLicensePicurl03() {
        return licensePicurl03;
    }

    public void setLicensePicurl03(String licensePicurl03) {
        this.licensePicurl03 = licensePicurl03;
    }

    public String getLicensePicurl02() {
        return licensePicurl02;
    }

    public void setLicensePicurl02(String licensePicurl02) {
        this.licensePicurl02 = licensePicurl02;
    }

    public String getLicensePicurl01() {
        return licensePicurl01;
    }

    public void setLicensePicurl01(String licensePicurl01) {
        this.licensePicurl01 = licensePicurl01;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId;
    }

    public String getCustomerUid() {
        return customerUid;
    }

    public void setCustomerUid(String customerUid) {
        this.customerUid = customerUid;
    }
}
