package com.ziroom.minsu.entity.photographer;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>摄影师基本信息实体</p>
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
public class PhotographerBaseMsgEntity extends BaseEntity{
    /**
	 * 序列id
	 */
	private static final long serialVersionUID = 6860253996005636814L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 摄影师uid
     */
    private String photographerUid;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 摄影师联系手机号
     */
    private String mobile;

    /**
     * 所在城市code
     */
    private String cityCode;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 开始参加摄影工作时间（到月）
     */
    private Date photographerStartTime;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别（1=女 2=男）
     */
    private Integer sex;

    /**
     * 摄影师等级(A B C D E A等级最高)
     */
    private String photographerGrade;

    /**
     * 摄影师来源(0=其他 1=民宿摄影部)
     */
    private Integer photographerSource;

    /**
     * 摄影师状态( 1=正常  2=异常  3=删除)
     */
    private Integer photographerStatu;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后修改时间
     */
    private Date lastModifyDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhotographerUid() {
        return photographerUid;
    }

    public void setPhotographerUid(String photographerUid) {
        this.photographerUid = photographerUid == null ? null : photographerUid.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public Date getPhotographerStartTime() {
        return photographerStartTime;
    }

    public void setPhotographerStartTime(Date photographerStartTime) {
        this.photographerStartTime = photographerStartTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhotographerGrade() {
        return photographerGrade;
    }

    public void setPhotographerGrade(String photographerGrade) {
        this.photographerGrade = photographerGrade == null ? null : photographerGrade.trim();
    }

    public Integer getPhotographerSource() {
        return photographerSource;
    }

    public void setPhotographerSource(Integer photographerSource) {
        this.photographerSource = photographerSource;
    }

    public Integer getPhotographerStatu() {
        return photographerStatu;
    }

    public void setPhotographerStatu(Integer photographerStatu) {
        this.photographerStatu = photographerStatu;
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