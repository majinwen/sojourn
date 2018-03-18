package com.ziroom.zrp.service.trading.dto.finance;

import java.util.List;

/**
 * <p>根据合同号批量查询应收账单信息接口</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年10月25日 17:03
 * @since 1.0
 */
public class ReceiptBillContractsRequest  extends ReceiptBillRequest{

    private List<String> outContractList;

    public List<String> getOutContractList() {
        return outContractList;
    }

    public void setOutContractList(List<String> outContractList) {
        this.outContractList = outContractList;
    }
}
