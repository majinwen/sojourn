package com.ziroom.minsu.entity.order;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p>常用入住人</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/31.
 * @version 1.0
 * @since 1.0
 */
public class UsualContactEntity extends BaseEntity {

    /** 序列化id  */
    private static final long serialVersionUID = -585475812937998L;

    /** id */
    private Integer id;

    /** fid */
    private String fid;

    /** 用户uid */
    private String userUid;

    /** 联系人民称 */
    private String conName;

    /** 联系人电话 */
    private String conTel;

    /** 联系人性别 1：男 2：女 */
    private Integer conSex;
    
    /** 证件类型 1：身份证 2：护照 这个需要产品提供  */
    private Integer cardType;

    /** 证件编号 */
    private String cardValue;

    /**
     * 是否预订人 0：非 1：是
     */
    private Integer isBooker;
	
    /** 正面照片 */
    private String frontPic;
    
    /** 反面照片 */
    private String obversePic;

    /** 是否默认 0：非默认 1：默认 只能有一个默认值，下次下单会带出默认值 */
    private Integer isDefault;

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date lastModifyDate;

    /** 是否删除 0：未删除 1：已经删除 */
    private Integer isDel;
    
    /** 是否认证  0：未认证 1：已认证 */
    private Integer isAuth;

    
    public Integer getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(Integer isAuth) {
		this.isAuth = isAuth;
	}

	public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

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
        this.fid = fid;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getConTel() {
        return conTel;
    }

    public void setConTel(String conTel) {
        this.conTel = conTel;
    }

    public Integer getConSex() {
        return conSex;
    }

    public void setConSex(Integer conSex) {
        this.conSex = conSex;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public String getCardValue() {
        return cardValue;
    }

    public void setCardValue(String cardValue) {
        this.cardValue = cardValue;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

	public String getFrontPic() {
		return frontPic;
	}

	public void setFrontPic(String frontPic) {
		this.frontPic = frontPic;
	}

	public String getObversePic() {
		return obversePic;
	}

	public void setObversePic(String obversePic) {
		this.obversePic = obversePic;
	}


    public Integer getIsBooker() {
        return isBooker;
    }

    public void setIsBooker(Integer isBooker) {
        this.isBooker = isBooker;
    }
}
