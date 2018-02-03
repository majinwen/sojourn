package com.ziroom.minsu.entity.house;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;
/**
 * 
 * <p>房源智能锁表</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class HouseSmartlockEntity extends BaseEntity{
    /**
	 * 序列id
	 */
	private static final long serialVersionUID = 387501381709465681L;

	private Integer id;
	
    private String fid;
    /**
     * 房源fid
     */
    private String houseBaseFid;
    /**
     * 房间fid
     */
    private String roomFid;
    /**
     * 回调serviceid
     */
    private String serviceId;
    /**
     * 更新密码
     */
    private String updatePassword;
    /**
     * 当前显示密码
     */
    private String password;
    /**
     * 当前密码状态
     */
    private Integer passwordStatus;
    /**
     * 日期开始时间
     */
    private Date permissionBegin;
    /**
     * 日期结束时间
     */
    private Date permissionEnd;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 最后修改时间
     */
    private Date lastModifyDate;
    /**
     * 是否删除 0 未删除 1 删除
     */
    private Integer isDel;

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

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId == null ? null : serviceId.trim();
    }

    public String getUpdatePassword() {
        return updatePassword;
    }

    public void setUpdatePassword(String updatePassword) {
        this.updatePassword = updatePassword == null ? null : updatePassword.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getPasswordStatus() {
		return passwordStatus;
	}

	public void setPasswordStatus(Integer passwordStatus) {
		this.passwordStatus = passwordStatus;
	}

	public Date getPermissionBegin() {
        return permissionBegin;
    }

    public void setPermissionBegin(Date permissionBegin) {
        this.permissionBegin = permissionBegin;
    }

    public Date getPermissionEnd() {
        return permissionEnd;
    }

    public void setPermissionEnd(Date permissionEnd) {
        this.permissionEnd = permissionEnd;
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