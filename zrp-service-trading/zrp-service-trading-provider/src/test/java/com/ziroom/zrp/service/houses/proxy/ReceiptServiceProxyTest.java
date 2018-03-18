package com.ziroom.zrp.service.houses.proxy;

/*
 * <P></P>
 * <P>
 * <PRE>
 * <BR> 修改记录
 * <BR>------------------------------------------
 * <BR> 修改日期       修改人        修改内容
 * </PRE>
 * 
 * @Author lusp
 * @Date Create in 2017年09月 21日 17:40
 * @Version 1.0
 * @Since 1.0
 */

import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.proxy.ReceiptServiceProxy;
import org.junit.Test;

import javax.annotation.Resource;

public class ReceiptServiceProxyTest extends BaseTest{

    @Resource(name="trading.receiptServiceProxy")
    private ReceiptServiceProxy receiptServiceProxy;

    @Test
    public void testDeleteReceiptAndRel(){
        receiptServiceProxy.deleteReceiptAndRel("ZY20171255727");
    }

    @Test
    public void testsaveReceiptAndReturnFid(){
        String paramJson = "{\"id\":null,\"fid\":null,\"parentFid\":null,\"billNum\":\"ZY20171262000\",\"amount\":null,\"paySerialNum\":null,\"payType\":\"card\",\"payTime\":\"2017-12-29 00:00:00\",\"receiptMothed\":\"xxzf\",\"payer\":null,\"posId\":null,\"referenceNum\":null,\"checkNumber\":null,\"makerCode\":\"admin\",\"makerName\":\"系统管理员\",\"makerDept\":\"系统管理员\",\"confirmStatus\":0,\"receiptStatus\":1,\"accountFlag\":0,\"attach\":\"无\",\"createTime\":null,\"updateTime\":null,\"isDel\":null,\"isValid\":null,\"isSyncFinance\":null,\"contractId\":\"8a908e10609bfbab01609c359ce30039\",\"remittanceAccount\":null,\"amountAccept\":null}";
        receiptServiceProxy.saveReceiptAndReturnFid(paramJson);
    }


}
