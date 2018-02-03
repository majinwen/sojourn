package com.ziroom.minsu.entity.cms;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>活动礼品项 实体</p>
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
public class ActivityGiftItemEntity extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7330910657863628234L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 关联编号
     */
    private String fid;

    /**
     * 活动码
     */
    private String actSn;

    /**
     * 礼品fid
     */
    private String giftFid;

    /**
     * 礼品个数
     */
    private Integer giftCount;

    /**
     * 是否删除0不删除1删除
     */
    private Integer isDel;

    /**
     * 创建时间
     */
    private Date createDate;

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

    public String getActSn() {
        return actSn;
    }

    public void setActSn(String actSn) {
        this.actSn = actSn == null ? null : actSn.trim();
    }

    public String getGiftFid() {
        return giftFid;
    }

    public void setGiftFid(String giftFid) {
        this.giftFid = giftFid == null ? null : giftFid.trim();
    }

    /**
	 * @return the giftCount
	 */
	public Integer getGiftCount() {
		return giftCount;
	}

	/**
	 * @param giftCount the giftCount to set
	 */
	public void setGiftCount(Integer giftCount) {
		this.giftCount = giftCount;
	}

	public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}