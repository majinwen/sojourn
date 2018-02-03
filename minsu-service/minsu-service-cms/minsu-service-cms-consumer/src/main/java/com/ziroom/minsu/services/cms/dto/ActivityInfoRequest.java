package com.ziroom.minsu.services.cms.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>
 * 活动信息表
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年6月23日
 * @since 1.0
 * @version 1.0
 */
public class ActivityInfoRequest extends PageRequest {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -4601503358849413081L;

	private String cityCode;

	/**
	 * 活动编号
	 */
	private String actSn;

	private String actName;

	private String createTimeStart;

	private String createTimeEnd;

	private String actType;

    private String actKind;

	private String actStatus;

	private String roleCode;
	
	private String groupSn;

    private String serviceLine;

    public String getActSn() {
		return actSn;
	}

	public void setActSn(String actSn) {
		this.actSn = actSn;
	}

	/**
	 * @return the groupSn
	 */
	public String getGroupSn() {
		return groupSn;
	}

	/**
	 * @param groupSn the groupSn to set
	 */
	public void setGroupSn(String groupSn) {
		this.groupSn = groupSn;
	}

	public String getActKind() {
        return actKind;
    }

    public void setActKind(String actKind) {
        this.actKind = actKind;
    }

    public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getActType() {
		return actType;
	}

	public void setActType(String actType) {
		this.actType = actType;
	}

	public String getActStatus() {
		return actStatus;
	}

	public void setActStatus(String actStatus) {
		this.actStatus = actStatus;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

    public String getServiceLine() {
        return serviceLine;
    }

    public void setServiceLine(String serviceLine) {
        this.serviceLine = serviceLine;
    }
}
