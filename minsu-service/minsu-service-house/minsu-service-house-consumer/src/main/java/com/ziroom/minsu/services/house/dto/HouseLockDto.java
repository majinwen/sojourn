package com.ziroom.minsu.services.house.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.ziroom.minsu.valenum.order.LockTypeEnum;

/**
 * <p>锁定房源参数对象</p>
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
public class HouseLockDto {

	/**
	 * 房源逻辑id
	 */
	@NotNull(message="{house.base.fid.null}")
	private String houseBaseFid;
	
	/**
	 * 房间逻辑id
	 */
	private String houseRoomFid;
	
	/**
	 * 床位逻辑id
	 */
	private String bedFid;
	
	/**
	 * 出租方式 @RentWayEnum
	 */
	@NotNull(message="{house.rentway.null}")
	private Integer rentWay;
	
	/**
	 * 锁定日期
	 */
	@NotEmpty(message="{house.lockdate.null}")
	private List<String> lockDateList;
	
	/**
	 * 锁定类型
	 */
	private Integer lockType = LockTypeEnum.LANDLADY.getCode();

	public String getHouseBaseFid() {
		return houseBaseFid;
	}

	public void setHouseBaseFid(String houseBaseFid) {
		this.houseBaseFid = houseBaseFid;
	}

	public String getHouseRoomFid() {
		return houseRoomFid;
	}

	public void setHouseRoomFid(String houseRoomFid) {
		this.houseRoomFid = houseRoomFid;
	}

	public String getBedFid() {
		return bedFid;
	}

	public void setBedFid(String bedFid) {
		this.bedFid = bedFid;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public List<String> getLockDateList() {
		return lockDateList;
	}

	public void setLockDateList(List<String> lockDateList) {
		this.lockDateList = lockDateList;
	}

	public Integer getLockType() {
		return lockType;
	}

	public void setLockType(Integer lockType) {
		this.lockType = lockType;
	}
	
}
