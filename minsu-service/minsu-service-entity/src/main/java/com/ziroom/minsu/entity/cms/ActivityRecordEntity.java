package com.ziroom.minsu.entity.cms;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>活动礼物领取记录 实体</p>
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
public class ActivityRecordEntity extends BaseEntity{
    /**
	 * 序列id
	 */
	private static final long serialVersionUID = 815173677641786651L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 关联编号
     */
    private String fid;

    /**
     * 活动组sn
     */
    private String groupSn;

    /**
     * 活动编码
     */
    private String actSn;

    /**
     * 随机码
     */
    private String randSn;

    /**
     * 用户uid
     */
    private String userUid;

    /**
     * 用户手机号
     */
    private String userMobile;

    /**
     * 礼品fid
     */
    private String giftFid;

    /**
     * 当前礼物是否已被领取  0=未领取 默认  1=已领取
     */
    private Integer isPickUp;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后更新时间
     */
    private Date lastModifyDate;

    /**
     * 免佣金开始时间
     */
    private Date startTime;

    /**
     * 免佣金结束时间
     */
    private Date endTime;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户地址
     */
    private String userAdress;

    /**
     * 备注
     */
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getGroupSn() {
        return groupSn;
    }

    public void setGroupSn(String groupSn) {
        this.groupSn = groupSn == null ? null : groupSn.trim();
    }

    public String getActSn() {
        return actSn;
    }

    public void setActSn(String actSn) {
        this.actSn = actSn == null ? null : actSn.trim();
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid == null ? null : userUid.trim();
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile == null ? null : userMobile.trim();
    }

    public String getGiftFid() {
        return giftFid;
    }

    public void setGiftFid(String giftFid) {
        this.giftFid = giftFid == null ? null : giftFid.trim();
    }

    public Integer getIsPickUp() {
        return isPickUp;
    }

    public void setIsPickUp(Integer isPickUp) {
        this.isPickUp = isPickUp;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserAdress() {
        return userAdress;
    }

    public void setUserAdress(String userAdress) {
        this.userAdress = userAdress == null ? null : userAdress.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getRandSn() {
        return randSn;
    }

    public void setRandSn(String randSn) {
        this.randSn = randSn;
    }
}