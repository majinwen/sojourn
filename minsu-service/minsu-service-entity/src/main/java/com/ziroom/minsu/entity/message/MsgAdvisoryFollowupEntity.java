package com.ziroom.minsu.entity.message;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>首次咨询跟进实体类</p>
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
public class MsgAdvisoryFollowupEntity extends BaseEntity{
  

	/**
	 * 
	 */
	private static final long serialVersionUID = 3910606202782926138L;

	/**
     * 自增id
     */
    private Long id;

    /**
     * 逻辑id
     */
    private String fid;

    /**
     * 首次咨询执行表fid
     */
    private String msgFirstAdvisoryFid;

    /**
     * 跟进前状态 10：未跟进；20：跟进中；30：跟进结束；默认值为10
     */
    private Integer beforeStatus;

    /**
     * 跟进后状态 10：未跟进；20：跟进中；30：跟进结束；默认值为10
     */
    private Integer afterStatus;

    /**
     * 员工fid
     */
    private String empFid;

    /**
     * 员工code
     */
    private String empCode;

    /**
     * 操作人名称
     */
    private String empName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除 0：否，1：是
     */
    private Integer isDel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getMsgFirstAdvisoryFid() {
        return msgFirstAdvisoryFid;
    }

    public void setMsgFirstAdvisoryFid(String msgFirstAdvisoryFid) {
        this.msgFirstAdvisoryFid = msgFirstAdvisoryFid == null ? null : msgFirstAdvisoryFid.trim();
    }

    public Integer getBeforeStatus() {
        return beforeStatus;
    }

    public void setBeforeStatus(Integer beforeStatus) {
        this.beforeStatus = beforeStatus;
    }

    public Integer getAfterStatus() {
        return afterStatus;
    }

    public void setAfterStatus(Integer afterStatus) {
        this.afterStatus = afterStatus;
    }

    public String getEmpFid() {
        return empFid;
    }

    public void setEmpFid(String empFid) {
        this.empFid = empFid == null ? null : empFid.trim();
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode == null ? null : empCode.trim();
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}