package com.ziroom.minsu.services.common.vo;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>TODO</p>
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
public class HouseStatsVo extends BaseEntity{

	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = -6894477798740177956L;

	/**
     * 房源fid
     */
    private String houseFid;

    /**
     * 出租类型 0：整租，1：合租，2：床位
     */
    private Integer rentWay;
	
	/**
	 * 统计数量
	 */
	private Integer statsNum;

	public String getHouseFid() {
		return houseFid;
	}

	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public Integer getStatsNum() {
		return statsNum;
	}

	public void setStatsNum(Integer statsNum) {
		this.statsNum = statsNum;
	}
	
}
