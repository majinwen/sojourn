package com.ziroom.minsu.entity.base;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;
/**
 * 
 * <p>消息业务权限验证实体</p>
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
public class AuthIdentifyEntity extends BaseEntity{
    /**
	 * 序列化 id
	 * 
	 */
	private static final long serialVersionUID = 5301881071925192625L;

	/**
     * 编号
     */
    private Long id;

    /**
     * 逻辑fid
     */
    private String fid;

    /**
     * 业务线授权码
     */
    private String code;

    /**
     * 业务线授权code说明
     */
    private String remark;

    /**
     * 创建者code
     */
    private String empCode;

    /**
     * 创建时间 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间 最后修改时间
     */
    private Date lastModifyDate;

    /**
     * 是否删除 是否删除 0：否，1：是
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode == null ? null : empCode.trim();
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