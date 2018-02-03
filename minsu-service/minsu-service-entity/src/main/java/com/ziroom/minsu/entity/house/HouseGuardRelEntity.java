package com.ziroom.minsu.entity.house;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>房源专员关系实体</p>
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
public class HouseGuardRelEntity extends BaseEntity{
	
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = -4391061079297117508L;

	/**
	 * 主键
	 */
    private Integer id;

    /**
     * 逻辑id
     */
    private String fid;

    /**
     * 房源逻辑id
     */
    private String houseFid;
    
    /**
     * 地推管家系统号-该岗位已取消
     */
    @Deprecated // modified by liujun 2017-02-24
    private String empPushCode;
    
    /**
     * 地推管家姓名-该岗位已取消
     */
    @Deprecated // modified by liujun 2017-02-24
    private String empPushName;

    /**
     * 运营专员系统号 
     */
    private String empGuardCode;// 维护管家岗位变更为运营专员 2017-02-24

    /**
     * 运营专员姓名
     */
    private String empGuardName;// 维护管家岗位变更为运营专员 2017-02-24

    /**
     * 创建人id
     */
    private String createFid;

    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 最后修改日期
     */
    private Date lastModifyDate;

    /**
     * 是否删除(0:否,1:是)
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

    public String getHouseFid() {
        return houseFid;
    }

    public void setHouseFid(String houseFid) {
        this.houseFid = houseFid == null ? null : houseFid.trim();
    }
    
    @Deprecated // modified by liujun 2017-02-24
    public String getEmpPushCode() {
    	return empPushCode;
    }
    
    @Deprecated // modified by liujun 2017-02-24
    public void setEmpPushCode(String empPushCode) {
    	this.empPushCode = empPushCode == null ? null : empPushCode.trim();
    }
    
    @Deprecated // modified by liujun 2017-02-24
    public String getEmpPushName() {
    	return empPushName;
    }
    
    @Deprecated // modified by liujun 2017-02-24
    public void setEmpPushName(String empPushName) {
    	this.empPushName = empPushName == null ? null : empPushName.trim();
    }

    public String getEmpGuardCode() {
        return empGuardCode;
    }

    public void setEmpGuardCode(String empGuardCode) {
        this.empGuardCode = empGuardCode == null ? null : empGuardCode.trim();
    }

    public String getEmpGuardName() {
        return empGuardName;
    }

    public void setEmpGuardName(String empGuardName) {
        this.empGuardName = empGuardName == null ? null : empGuardName.trim();
    }
    
    public String getCreateFid() {
    	return createFid;
    }
    
    public void setCreateFid(String createFid) {
    	this.createFid = createFid;
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