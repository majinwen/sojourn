package com.ziroom.minsu.entity.message;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;
/**
 * 
 * <p>聊天客户关系操作日志</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public class MsgUserRelOperaEntity extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6034430706803982088L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 业务编号
     */
    private String fid;

    /**
     * 环信用户关系表fid
     */
    private String msgUserRelFid;

    /**
     * 关系类型 （0=可聊 1=不可聊）
     */
    private Integer fromStatus;

    /**
     * 关系类型 （0=可聊 1=不可聊）
     */
    private Integer toStatus;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人fid （自如客是uid，业务人员是系统号)
     */
    private String createFid;

    /**
     * 创建人类型 0=自如客  1=业务人员
     */
    private Integer createrType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date lastModifyDate;

    /**
     * 是否删除 默认0(0：不删除 1：删除)
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

    public String getMsgUserRelFid() {
        return msgUserRelFid;
    }

    public void setMsgUserRelFid(String msgUserRelFid) {
        this.msgUserRelFid = msgUserRelFid == null ? null : msgUserRelFid.trim();
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

    public Integer getCreaterType() {
        return createrType;
    }

    public void setCreaterType(Integer createrType) {
        this.createrType = createrType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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