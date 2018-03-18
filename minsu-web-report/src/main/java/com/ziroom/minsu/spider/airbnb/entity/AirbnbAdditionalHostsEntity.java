package com.ziroom.minsu.spider.airbnb.entity;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

public class AirbnbAdditionalHostsEntity extends BaseEntity{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -1417160164510955903L;

	/**
     * 自增id
     */
    private Integer id;

    /**
     * 附加房东编号
     */
    private String additionalHostSn;

    /**
     * 名字
     */
    private String firstName;

    /**
     * 姓
     */
    private String lastName;

    /**
     * 性别
     */
    private String sex;

    /**
     * 生日
     */
    private Date birthdate;

    /**
     * 国家编号
     */
    private String country;

    /**
     * email
     */
    private String email;

    /**
     * facebook_id
     */
    private String facebookId;

    /**
     * 加入时间
     */
    private Date affiliateAt;

    /**
     * 注册ip
     */
    private String initialIp;

    /**
     * 语言编码
     */
    private Integer languages;

    /**
     * 国家货币
     */
    private String nativeCurrency;

    /**
     * 评价数量
     */
    private Integer revieweeCount;

    /**
     * 评价分数
     */
    private Float revieweeRating;

    /**
     * 房源编号
     */
    private String houseSn;

    /**
     * 房东编号
     */
    private String hostSn;

    /**
     * 创建日期
     */
    private Date createDate;
    
	/**
	 * 最后修改日期
	 */
	private Date lastModifyDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdditionalHostSn() {
        return additionalHostSn;
    }

    public void setAdditionalHostSn(String additionalHostSn) {
        this.additionalHostSn = additionalHostSn == null ? null : additionalHostSn.trim();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName == null ? null : firstName.trim();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName == null ? null : lastName.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId == null ? null : facebookId.trim();
    }

    public Date getAffiliateAt() {
        return affiliateAt;
    }

    public void setAffiliateAt(Date affiliateAt) {
        this.affiliateAt = affiliateAt;
    }

    public String getInitialIp() {
        return initialIp;
    }

    public void setInitialIp(String initialIp) {
        this.initialIp = initialIp == null ? null : initialIp.trim();
    }

    public Integer getLanguages() {
        return languages;
    }

    public void setLanguages(Integer languages) {
        this.languages = languages;
    }

    public String getNativeCurrency() {
        return nativeCurrency;
    }

    public void setNativeCurrency(String nativeCurrency) {
        this.nativeCurrency = nativeCurrency == null ? null : nativeCurrency.trim();
    }

    public Integer getRevieweeCount() {
        return revieweeCount;
    }

    public void setRevieweeCount(Integer revieweeCount) {
        this.revieweeCount = revieweeCount;
    }

    public Float getRevieweeRating() {
        return revieweeRating;
    }

    public void setRevieweeRating(Float revieweeRating) {
        this.revieweeRating = revieweeRating;
    }

    public String getHouseSn() {
        return houseSn;
    }

    public void setHouseSn(String houseSn) {
        this.houseSn = houseSn == null ? null : houseSn.trim();
    }

    public String getHostSn() {
        return hostSn;
    }

    public void setHostSn(String hostSn) {
        this.hostSn = hostSn == null ? null : hostSn.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
    
}