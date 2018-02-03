package com.ziroom.minsu.services.house.dto;

import java.util.List;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.entity.house.HouseGuardRelEntity;

/**
 * 
 * <p>
 * 房源维护管家参数dto
 * </p>
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
public class HouseGuardParam extends BaseEntity {
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 4478762835153593998L;

	/**
	 * 地推管家系统号
	 */
	private String empPushCode;

	/**
	 * 地推管家姓名
	 */
	private String empPushName;

	/**
	 * 维护管家系统号
	 */
	private String empGuardCode;

	/**
	 * 维护管家姓名
	 */
	private String empGuardName;
	
	/**
	 * 创建人
	 */
	private String createFid;
	
	/**
	 * 房源渠道 房源渠道 1:直营, 2:房东, 3:地推
	 */
	private Integer houseChannel;

	/**
	 * 逻辑id集合
	 */
	private List<HouseGuardRelEntity> listGuard;
	
	/**
	 * @return the houseChannel
	 */
	public Integer getHouseChannel() {
		return houseChannel;
	}

	/**
	 * @param houseChannel the houseChannel to set
	 */
	public void setHouseChannel(Integer houseChannel) {
		this.houseChannel = houseChannel;
	}

	public String getEmpPushCode() {
		return empPushCode;
	}

	public void setEmpPushCode(String empPushCode) {
		this.empPushCode = empPushCode;
	}

	public String getEmpPushName() {
		return empPushName;
	}

	public void setEmpPushName(String empPushName) {
		this.empPushName = empPushName;
	}

	public String getEmpGuardCode() {
		return empGuardCode;
	}

	public void setEmpGuardCode(String empGuardCode) {
		this.empGuardCode = empGuardCode;
	}

	public String getEmpGuardName() {
		return empGuardName;
	}

	public void setEmpGuardName(String empGuardName) {
		this.empGuardName = empGuardName;
	}

	public List<HouseGuardRelEntity> getListGuard() {
		return listGuard;
	}

	public void setListGuard(List<HouseGuardRelEntity> listGuard) {
		this.listGuard = listGuard;
	}

	public String getCreateFid() {
		return createFid;
	}

	public void setCreateFid(String createFid) {
		this.createFid = createFid;
	}

}
