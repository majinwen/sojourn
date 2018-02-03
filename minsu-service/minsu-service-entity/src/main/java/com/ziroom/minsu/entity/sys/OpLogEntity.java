package com.ziroom.minsu.entity.sys;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 *
 * <p>操作记录</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp 2017/5/8
 * @since 1.0
 * @version 1.0
 */
public class OpLogEntity extends BaseEntity{

    /**
     *
     */
    private static final long serialVersionUID = -7144472384025171L;

    /** id */
    private Integer id;
    
    /** fid */
    private String fid;

    /** 员工id */
    private String opEmployeeId;

    /** 姓名 */
    private String opEmployeeName;

    /** 员工编号*/
    private String opEmployeeCode;
    
    /** 系统编号 */
    private Integer opAppNo;

    /** 操作链接 */
    private String opUrl;

    /** is del */
    private Short isDel;

    /** 创建时间 */
    private Date createDate;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the fid
	 */
	public String getFid() {
		return fid;
	}

	/**
	 * @param fid the fid to set
	 */
	public void setFid(String fid) {
		this.fid = fid;
	}

	/**
	 * @return the opEmployeeId
	 */
	public String getOpEmployeeId() {
		return opEmployeeId;
	}

	/**
	 * @param opEmployeeId the opEmployeeId to set
	 */
	public void setOpEmployeeId(String opEmployeeId) {
		this.opEmployeeId = opEmployeeId;
	}

	/**
	 * @return the opEmployeeName
	 */
	public String getOpEmployeeName() {
		return opEmployeeName;
	}

	/**
	 * @param opEmployeeName the opEmployeeName to set
	 */
	public void setOpEmployeeName(String opEmployeeName) {
		this.opEmployeeName = opEmployeeName;
	}

	/**
	 * @return the opEmployeeCode
	 */
	public String getOpEmployeeCode() {
		return opEmployeeCode;
	}

	/**
	 * @param opEmployeeCode the opEmployeeCode to set
	 */
	public void setOpEmployeeCode(String opEmployeeCode) {
		this.opEmployeeCode = opEmployeeCode;
	}

	

	/**
	 * @return the opAppNo
	 */
	public Integer getOpAppNo() {
		return opAppNo;
	}

	/**
	 * @param opAppNo the opAppNo to set
	 */
	public void setOpAppNo(Integer opAppNo) {
		this.opAppNo = opAppNo;
	}

	/**
	 * @return the opUrl
	 */
	public String getOpUrl() {
		return opUrl;
	}

	/**
	 * @param opUrl the opUrl to set
	 */
	public void setOpUrl(String opUrl) {
		this.opUrl = opUrl;
	}

	/**
	 * @return the isDel
	 */
	public Short getIsDel() {
		return isDel;
	}

	/**
	 * @param isDel the isDel to set
	 */
	public void setIsDel(Short isDel) {
		this.isDel = isDel;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
    
    

}
