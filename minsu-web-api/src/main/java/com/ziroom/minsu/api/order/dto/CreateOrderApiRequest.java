package com.ziroom.minsu.api.order.dto;

import java.util.Date;
import java.util.List;

import com.asura.framework.base.exception.BusinessException;
import com.ziroom.minsu.api.common.dto.BaseParamDto;
import com.ziroom.minsu.valenum.house.RentWayEnum;

/**
 * <p>
 * 创建订单请求参数
 * </p>
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
public class CreateOrderApiRequest extends BaseParamDto {

	/** 序列化id */
	private static final long serialVersionUID = -6822554545526309673L;

	/** 用户电话 */
	private String userTel;

	/** 用户名称 */
	private String userName;

	/** 房源 houseFid、房间 roomFid、床位bedFid */
	private String fid;

	/** 租住方式 */
	private Integer rentWay;

	private Integer sourceType;

	/** 房间 联系人列表 */
	private List<String> tenantFids;

	/** 开始时间 */
	private Date startTime;
	/** 结束时间 */
	private Date endTime;

	/** 优惠券编号 */
	private String couponSn;

    /** 出行目的 */
    private String tripPurpose;

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public List<String> getTenantFids() {
		return tenantFids;
	}

	public void setTenantFids(List<String> tenantFids) {
		this.tenantFids = tenantFids;
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

	public String getCouponSn() {
		return couponSn;
	}

	public void setCouponSn(String couponSn) {
		this.couponSn = couponSn;
	}

    public String getTripPurpose() {
        return tripPurpose;
    }

    public void setTripPurpose(String tripPurpose) {
        this.tripPurpose = tripPurpose;
    }
}
