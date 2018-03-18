package com.ziroom.zrp.service.trading.pojo;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>需要同步财务的应收账单</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年11月20日 18:38
 * @since 1.0
 */
public class SynFinBillDetailPojo extends BaseEntity  {
    //合同id
    private String contractId;

    //应收账单明细id
    private String billDetailFid;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getBillDetailFid() {
        return billDetailFid;
    }

    public void setBillDetailFid(String billDetailFid) {
        this.billDetailFid = billDetailFid;
    }
}
