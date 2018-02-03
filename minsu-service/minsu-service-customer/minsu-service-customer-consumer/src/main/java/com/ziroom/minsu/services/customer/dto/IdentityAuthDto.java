/**
 * @FileName: IdentityAuthDto.java
 * @Package com.ziroom.minsu.services.customer.dto
 * 
 * @author bushujie
 * @created 2016年4月22日 下午10:05:03
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * <p>身份认证参数</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class IdentityAuthDto {
	
	//真实姓名
	@NotEmpty(message = "{realName.null}")
	private String realName;
	//证件类型
	@NotNull(message = "{idType.null}")
	private Integer idType;
	//证件号码
	@NotEmpty(message = "{idNo.null}")
	private String idNo;	
	//uid
	@NotEmpty(message = "{uid.null}")
	private String uid;

	/**
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return the idType
	 */
	public Integer getIdType() {
		return idType;
	}

	/**
	 * @param idType the idType to set
	 */
	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	/**
	 * @return the idNo
	 */
	public String getIdNo() {
		return idNo;
	}

	/**
	 * @param idNo the idNo to set
	 */
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
}
