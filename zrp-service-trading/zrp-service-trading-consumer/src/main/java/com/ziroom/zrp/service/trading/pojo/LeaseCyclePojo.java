package com.ziroom.zrp.service.trading.pojo;

/**
 * <p>租赁周期类</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @Date Created in 2017年09月12日 19:01
 * @version 1.0
 * @since 1.0
 */
public class LeaseCyclePojo {

	private String conType;//租赁周期，日租，月租，年租
	private String projectId; // 项目标识
	private Integer rentType; // 出租类型
	private String conCycleCode; //付款方式

	public String getConCycleCode() {
		return conCycleCode;
	}

	public void setConCycleCode(String conCycleCode) {
		this.conCycleCode = conCycleCode;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Integer getRentType() {
		return rentType;
	}

	public void setRentType(Integer rentType) {
		this.rentType = rentType;
	}

	public LeaseCyclePojo(String conType, String projectId, Integer rentType) {
		this.conType = conType;
		this.projectId = projectId;
		this.rentType = rentType;
	}

	public String getConType() {

		return conType;
	}

	public void setConType(String conType) {
		this.conType = conType;
	}
}
