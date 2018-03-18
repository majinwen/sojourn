package com.ziroom.minsu.report.board.entity;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>城市日可出租天数记录表</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class CityDailyMsgEntity extends BaseEntity{
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = -4732993014417309620L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 国家编码
     */
    private String nationCode;

    /**
     * 省编码
     */
    private String provinceCode;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 累计上架房源数量
     */
    private Integer totalUpNum;

    /**
     * 累计房东锁定房源天数
     */
    private Integer lockNum;
    
    /**
     * 日地推发布房源量
     */
    private Integer pushIssueNum;
    
    /**
     * 日自主发布房源量
     */
    private Integer selfIssueNum;
    
    /**
     * 日首次自主上架房源量
     */
    private Integer initialSelfUpNum;

	/**
     * 房源发布量
     */
    private Integer issueNum;

    /**
     * 日首次地推上架房源数量
     */
    private Integer initialPushUpNum;

    /**
     * 日上架房源数量(同一房源同一天多次上架算一次)
     */
    private Integer upNum;

    /**
     * 日下架房源数量(同一房源同一天多次下架算一次)
     */
    private Integer downNum;

    /**
     * 日最终上架数量(同一房源一天内最后操作为上架算一次)
     */
    private Integer finalUpNum;

    /**
     * 日最终下架数量(同一房源一天内最后操作为下架算一次)
     */
    private Integer finalDownNum;
    
    /**
     * 立即预定类型的房源数
     */
    private Integer immediateBookTypeNum;

    /**
     * 预订订单数量
     */
    private Integer bookOrderNum;

    /**
     * 支付订单数量
     */
    private Integer payOrderNum;

    /**
     * 间夜数量
     */
    private Integer roomNightNum;

    /**
     * 咨询量
     */
    private Integer consultNum;

    /**
     * 体验型房东量
     */
    private Integer expLandNum;

    /**
     * 非专业型房东量
     */
    private Integer nonProLandNum;

    /**
     * 专业型房东量
     */
    private Integer proLandNum;

    /**
     * 统计日期
     */
    private Date statDate;

    /**
     * 是否删除 0:否 1:是
     */
    private Integer isDel;

    /**
     * 创建日期
     */
    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNationCode() {
        return nationCode;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode == null ? null : nationCode.trim();
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode == null ? null : provinceCode.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public Integer getTotalUpNum() {
        return totalUpNum;
    }

    public void setTotalUpNum(Integer totalUpNum) {
        this.totalUpNum = totalUpNum;
    }

    public Integer getLockNum() {
        return lockNum;
    }

    public void setLockNum(Integer lockNum) {
        this.lockNum = lockNum;
    }

    public Integer getIssueNum() {
        return issueNum;
    }

    public void setIssueNum(Integer issueNum) {
        this.issueNum = issueNum;
    }

    public Integer getInitialPushUpNum() {
        return initialPushUpNum;
    }

    public void setInitialPushUpNum(Integer initialPushUpNum) {
        this.initialPushUpNum = initialPushUpNum;
    }

    public Integer getUpNum() {
        return upNum;
    }

    public void setUpNum(Integer upNum) {
        this.upNum = upNum;
    }

    public Integer getDownNum() {
        return downNum;
    }

    public void setDownNum(Integer downNum) {
        this.downNum = downNum;
    }

    public Integer getFinalUpNum() {
        return finalUpNum;
    }

    public void setFinalUpNum(Integer finalUpNum) {
        this.finalUpNum = finalUpNum;
    }

    public Integer getFinalDownNum() {
        return finalDownNum;
    }

    public void setFinalDownNum(Integer finalDownNum) {
        this.finalDownNum = finalDownNum;
    }

    public Integer getBookOrderNum() {
        return bookOrderNum;
    }

    public void setBookOrderNum(Integer bookOrderNum) {
        this.bookOrderNum = bookOrderNum;
    }

    public Integer getPayOrderNum() {
        return payOrderNum;
    }

    public void setPayOrderNum(Integer payOrderNum) {
        this.payOrderNum = payOrderNum;
    }

    public Integer getRoomNightNum() {
        return roomNightNum;
    }

    public void setRoomNightNum(Integer roomNightNum) {
        this.roomNightNum = roomNightNum;
    }

    public Integer getConsultNum() {
        return consultNum;
    }

    public void setConsultNum(Integer consultNum) {
        this.consultNum = consultNum;
    }

    public Integer getExpLandNum() {
        return expLandNum;
    }

    public void setExpLandNum(Integer expLandNum) {
        this.expLandNum = expLandNum;
    }

    public Integer getNonProLandNum() {
        return nonProLandNum;
    }

    public void setNonProLandNum(Integer nonProLandNum) {
        this.nonProLandNum = nonProLandNum;
    }

    public Integer getProLandNum() {
        return proLandNum;
    }

    public void setProLandNum(Integer proLandNum) {
        this.proLandNum = proLandNum;
    }

    public Date getStatDate() {
        return statDate;
    }

    public void setStatDate(Date statDate) {
        this.statDate = statDate;
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
    
    /**
	 * @return the pushIssueNum
	 */
	public Integer getPushIssueNum() {
		return pushIssueNum;
	}

	/**
	 * @param pushIssueNum the pushIssueNum to set
	 */
	public void setPushIssueNum(Integer pushIssueNum) {
		this.pushIssueNum = pushIssueNum;
	}

	/**
	 * @return the selfIssueNum
	 */
	public Integer getSelfIssueNum() {
		return selfIssueNum;
	}

	/**
	 * @param selfIssueNum the selfIssueNum to set
	 */
	public void setSelfIssueNum(Integer selfIssueNum) {
		this.selfIssueNum = selfIssueNum;
	}

	/**
	 * @return the initialSelfUpNum
	 */
	public Integer getInitialSelfUpNum() {
		return initialSelfUpNum;
	}

	/**
	 * @param initialSelfUpNum the initialSelfUpNum to set
	 */
	public void setInitialSelfUpNum(Integer initialSelfUpNum) {
		this.initialSelfUpNum = initialSelfUpNum;
	}

	public Integer getImmediateBookTypeNum() {
		return immediateBookTypeNum;
	}

	public void setImmediateBookTypeNum(Integer immediateBookTypeNum) {
		this.immediateBookTypeNum = immediateBookTypeNum;
	}
	
}