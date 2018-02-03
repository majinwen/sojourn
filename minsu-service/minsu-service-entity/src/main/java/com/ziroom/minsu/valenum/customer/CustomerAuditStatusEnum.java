/**
 * @FileName: CustomerAuditStatusEnum.java
 * @Package com.ziroom.minsu.valenum.customer
 * 
 * @author loushuai
 * @created 2017年8月8日 上午10:14:05
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.customer;

/**
 * <p>TODO</p>
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
public enum CustomerAuditStatusEnum {

	UN_AUDIT(0,"未审核"),
	AUDIT_ADOPT(1,"审核通过"),
	AUDIT_UNADOPT(2,"审核未通过"),
	AUDITADOPT_REJECT(3,"审核通过驳回");
	
	/** 编码 */
	private int code;

	/** 名称 */
	private String name;

	private CustomerAuditStatusEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public static CustomerAuditStatusEnum getCustomerAuditStatusByCode(int code) {
        for (CustomerAuditStatusEnum statu : CustomerAuditStatusEnum.values()) {
            if (statu.getCode() == code) {
                return statu;
            }
        }
        return null;
    }
	
}
