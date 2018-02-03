/**
 * @FileName: GuardAreaRequest.java
 * @Package com.ziroom.minsu.services.basedata.dto
 * 
 * @author yd
 * @created 2016年7月5日 下午3:17:37
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>区域管家查询条件封装</p>
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
public class GuardAreaRequest extends PageRequest{



	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 4172714430049262679L;

	/**
	 * 国家code
	 */
	private String nationCode;

	/**
	 * 省code
	 */
	private String provinceCode;

	/**
	 * 市code
	 */
	private String cityCode;

	/**
	 * 区code
	 */
	private String areaCode;

	/**
	 * 员工编号
	 */
	private String empCode;

	/**
	 * 创建人fid
	 */
	private String createFid;


	/**
	 * 是否删除
	 */
	private Integer isDel;

	/**
	 * 管家手机
	 */
	private String empPhone;

	/**
	 * 管家姓名
	 */
	private String empName;

	
	public String getEmpPhone() {
		return empPhone;
	}


	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}


	public String getEmpName() {
		return empName;
	}


	public void setEmpName(String empName) {
		this.empName = empName;
	}


	public String getNationCode() {
		return nationCode;
	}


	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}


	public String getProvinceCode() {
		return provinceCode;
	}


	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}


	public String getCityCode() {
		return cityCode;
	}


	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}


	public String getAreaCode() {
		return areaCode;
	}


	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}


	public String getEmpCode() {
		return empCode;
	}


	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}


	public String getCreateFid() {
		return createFid;
	}


	public void setCreateFid(String createFid) {
		this.createFid = createFid;
	}


	public Integer getIsDel() {
		return isDel;
	}


	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}




}
