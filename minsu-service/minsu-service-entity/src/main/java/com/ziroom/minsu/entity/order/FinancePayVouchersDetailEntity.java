package com.ziroom.minsu.entity.order;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>付款单明细实体</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie
 * @since 1.0
 * @version 1.0
 */
public class FinancePayVouchersDetailEntity extends BaseEntity{
	
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = -357154803955831530L;

	/** 自增id */
    private Integer id;

    /** 付款单号 */
    private String pvSn;

    /** 费用项目 费用项目:  1：房租  2：押金  3：赔付款  4：违约金 */
    private Integer feeItemCode;

    /** 费用项目金额 */
    private Integer itemMoney;

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPvSn() {
        return pvSn;
    }

    public void setPvSn(String pvSn) {
        this.pvSn = pvSn == null ? null : pvSn.trim();
    }

    public Integer getFeeItemCode() {
        return feeItemCode;
    }

    public void setFeeItemCode(Integer feeItemCode) {
        this.feeItemCode = feeItemCode;
    }

    public Integer getItemMoney() {
        return itemMoney;
    }

    public void setItemMoney(Integer itemMoney) {
        this.itemMoney = itemMoney;
    }
}