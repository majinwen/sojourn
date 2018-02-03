/**
 * @FileName: GuardAreaLogRequest.java
 * @Package com.ziroom.minsu.services.basedata.dto
 * 
 * @author yd
 * @created 2016年7月5日 下午4:48:41
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;


/**
 * <p>区域管家 日志查询条件</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class GuardAreaLogRequest extends PageRequest{


	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -8620487279586354465L;

	/**
     * 主表fid
     */
    private String guradAreaFid;

    /**
     * 原国家code
     */
    private String oldNationCode;

    /**
     * 原省code
     */
    private String oldProvinceCode;

    /**
     * 原市code
     */
    private String oldCityCode;

    /**
     * 原区code
     */
    private String oldAreaCode;

    /**
     * 管家编号
     */
    private String guardCode;

    /**
     * 管家姓名
     */
    private String guardName;

    /**
     * 创建人fid
     */
    private String createFid;

	public String getGuradAreaFid() {
		return guradAreaFid;
	}

	public void setGuradAreaFid(String guradAreaFid) {
		this.guradAreaFid = guradAreaFid;
	}

	public String getOldNationCode() {
		return oldNationCode;
	}

	public void setOldNationCode(String oldNationCode) {
		this.oldNationCode = oldNationCode;
	}

	public String getOldProvinceCode() {
		return oldProvinceCode;
	}

	public void setOldProvinceCode(String oldProvinceCode) {
		this.oldProvinceCode = oldProvinceCode;
	}

	public String getOldCityCode() {
		return oldCityCode;
	}

	public void setOldCityCode(String oldCityCode) {
		this.oldCityCode = oldCityCode;
	}

	public String getOldAreaCode() {
		return oldAreaCode;
	}

	public void setOldAreaCode(String oldAreaCode) {
		this.oldAreaCode = oldAreaCode;
	}

	public String getGuardCode() {
		return guardCode;
	}

	public void setGuardCode(String guardCode) {
		this.guardCode = guardCode;
	}

	public String getGuardName() {
		return guardName;
	}

	public void setGuardName(String guardName) {
		this.guardName = guardName;
	}

	public String getCreateFid() {
		return createFid;
	}

	public void setCreateFid(String createFid) {
		this.createFid = createFid;
	}
    
    
    
 
}
