package com.ziroom.minsu.api.customer.entity;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.valenum.customer.BankNameEnum;


/**
 * <p>bank</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/9/7.
 * @version 1.0
 * @since 1.0
 */
public class BankParVo extends BaseEntity {

    private static final long serialVersionUID = 7743212311357371L;
    
    /**
     * 用户uid
     */
    private String uid;

    /**
     * 开户人姓名
     */
    private String accountName;

    /**
     * 开户行地区
     */
    private String bankArea;
    
    /**
     * 银行编码 {@link BankNameEnum}
     */
    private Integer bankCode;
    
    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行卡号
     */
    private String bankCardNo;

    /**
     * 系统来源
     */
    private String systemSource;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getBankArea() {
		return bankArea;
	}

	public void setBankArea(String bankArea) {
		this.bankArea = bankArea;
	}

	public Integer getBankCode() {
		return bankCode;
	}

	public void setBankCode(Integer bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getSystemSource() {
		return systemSource;
	}

	public void setSystemSource(String systemSource) {
		this.systemSource = systemSource;
	}
    
}
