package com.ziroom.minsu.entity.cms;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>总积分表</p>
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
public class PointSumEntity extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 9039359088024803546L;

	/**
     * 主键id
     */
    private Integer id;

    /**
     * 所得积分人uid
     */
    private String uid;
    
    /**
     * 积分来源 1，邀请好友下单活动'
     */
    private Integer pointsSource;

    /**
     * 总积分值
     */
    private Integer sumPoints;

    /**
     * 贡献出有效积分的人数
     */
    private Integer sumPerson;

    /**
     * 已兑换积分
     */
    private Integer hasExchangePoints;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后修改时间
     */
    private Date lastModifyDate;

    /**
     * 是否删除 0：否，1：是
     */
    private Integer isDel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }
    
    public Integer getPointsSource() {
		return pointsSource;
	}

	public void setPointsSource(Integer pointsSource) {
		this.pointsSource = pointsSource;
	}

	public Integer getSumPoints() {
        return sumPoints;
    }

    public void setSumPoints(Integer sumPoints) {
        this.sumPoints = sumPoints;
    }

    public Integer getSumPerson() {
        return sumPerson;
    }

    public void setSumPerson(Integer sumPerson) {
        this.sumPerson = sumPerson;
    }

    public Integer getHasExchangePoints() {
		return hasExchangePoints;
	}

	public void setHasExchangePoints(Integer hasExchangePoints) {
		this.hasExchangePoints = hasExchangePoints;
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

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}