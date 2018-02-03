package com.ziroom.minsu.entity.house;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>
 * 房源操作日志实体
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
public class HouseOperateLogEntity extends BaseEntity {
	/**
	 * 序列化id
	 * 
	 */
	private static final long serialVersionUID = -7569084488086906434L;

	// 自增id
	private Integer id;

	// 逻辑id
	private String fid;

	// 房源基本信息表逻辑id
	private String houseBaseFid;

	// 房源房间信息表逻辑id
	private String roomFid;

	// 房源床位信息表逻辑id
	private String bedFid;
	
	@Deprecated // 审核说明  @HouseOperateLogEntity#auditCause
	private Integer cause;
	
	// 审核说明(审核原因可以多选 eg:10,11) @HouseAuditCauseEnum
	private String auditCause;

	// 初始状态
	private Integer fromStatus;

	// 改变后状态
	private Integer toStatus;

	// 操作类型(0：房东,1：业务人员)
	private Integer operateType;

	// 描述
	private String remark;

	// 创建人uid
	private String createFid;

	// 创建日期
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

	public String getHouseBaseFid() {
		return houseBaseFid;
	}

	public void setHouseBaseFid(String houseBaseFid) {
		this.houseBaseFid = houseBaseFid == null ? null : houseBaseFid.trim();
	}

	public String getRoomFid() {
		return roomFid;
	}

	public void setRoomFid(String roomFid) {
		this.roomFid = roomFid == null ? null : roomFid.trim();
	}

	public String getBedFid() {
		return bedFid;
	}

	public void setBedFid(String bedFid) {
		this.bedFid = bedFid == null ? null : bedFid.trim();
	}

	public Integer getFromStatus() {
		return fromStatus;
	}

	public void setFromStatus(Integer fromStatus) {
		this.fromStatus = fromStatus;
	}

	public Integer getToStatus() {
		return toStatus;
	}

	public void setToStatus(Integer toStatus) {
		this.toStatus = toStatus;
	}

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getCreateFid() {
		return createFid;
	}

	public void setCreateFid(String createFid) {
		this.createFid = createFid == null ? null : createFid.trim();
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Deprecated
	public Integer getCause() {
		return cause;
	}

	@Deprecated
	public void setCause(Integer cause) {
		this.cause = cause;
	}
	
	public String getAuditCause() {
		return auditCause;
	}
	
	public void setAuditCause(String auditCause) {
		this.auditCause = auditCause;
	}
}