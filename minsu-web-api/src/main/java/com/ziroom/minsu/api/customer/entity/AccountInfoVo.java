package com.ziroom.minsu.api.customer.entity;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.entity.base.MinsuShowEntity;

/**
 * <p>账户信息</p>
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
public class AccountInfoVo extends BaseEntity{

    /** 序列化ID */
    private static final long serialVersionUID = 77432589857371L;

    /** 余额 */
    private String balance;

    /** 银行卡信息 */
    private BankInfoVo bankInfo;

    /** 是否有银行卡信息 */
    private Integer haveBank = 0;

    /** 收款方式 */
    private MinsuShowEntity  receiveType;

    /** 结算方式 */
    private MinsuShowEntity checkType;

    /** 提示信息 */
    private String msg;


    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public BankInfoVo getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(BankInfoVo bankInfo) {
        this.bankInfo = bankInfo;
    }

    public Integer getHaveBank() {
        return haveBank;
    }

    public void setHaveBank(Integer haveBank) {
        this.haveBank = haveBank;
    }

    public MinsuShowEntity getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(MinsuShowEntity receiveType) {
        this.receiveType = receiveType;
    }

    public MinsuShowEntity getCheckType() {
        return checkType;
    }

    public void setCheckType(MinsuShowEntity checkType) {
        this.checkType = checkType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
