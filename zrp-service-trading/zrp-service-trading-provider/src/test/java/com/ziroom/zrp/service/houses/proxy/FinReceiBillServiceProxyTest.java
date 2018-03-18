package com.ziroom.zrp.service.houses.proxy;

import java.util.*;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.google.common.collect.Lists;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.houses.valenum.MeterTypeEnum;
import com.ziroom.zrp.service.trading.dto.finance.FinReceiBillDetailDto;
import com.ziroom.zrp.service.trading.dto.finance.FinReceiBillDto;
import com.ziroom.zrp.service.trading.proxy.FinReceiBillServiceProxy;
import com.ziroom.zrp.service.trading.valenum.FeeItemEnum;

import com.ziroom.zrp.service.trading.valenum.finance.DocumentTypeEnum;
import com.ziroom.zrp.service.trading.valenum.surrender.ExpenseItemEnum;
import com.zra.common.utils.KeyGenUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年11月23日 17:47
 * @since 1.0
 */
@Slf4j
public class FinReceiBillServiceProxyTest extends BaseTest {

    @Resource(name="trading.finReceiBillServiceProxy")
    private FinReceiBillServiceProxy finReceiBillServiceProxy;

    @Test
    public void testSyncReceiptBillToFinByContractId() {
        String contractId = "8a9091bd5fe78f82015fe7b982500058";
        String result = this.finReceiBillServiceProxy.syncReceiptBillToFinByContractId(contractId);
        System.out.println("result:" + result);

    }

    @Test
    public void testAsyncRetrySyncReceiptBillToFin() {
        this.finReceiBillServiceProxy.asyncRetrySyncReceiptBillToFin();
    }
    
    @Test
    public void testGetFinReceiBillByContractId(){
    	Map<String, Object> paramMap=new HashMap<String, Object>();
    	paramMap.put("contractId", "8a9091bd61031474016108361500001d");
    	paramMap.put("itemId",FeeItemEnum.WATER_FEE.getItemFid());
    	String resultJson=finReceiBillServiceProxy.getFinReceiBillByContractId(JsonEntityTransform.Object2Json(paramMap));
    	System.err.println(resultJson);
    }

    /**
     * 水电表 应收账单失败
     */
    @Test
    public void SaveFinanceReceivableOfWaterWattTest() {

        FinReceiBillDto finReceiBillDto;
        List<FinReceiBillDetailDto > finReceiBillDetailDtos = Lists.newArrayList();

        // contract fid
        final String contractId = "8a908e10614f17f301615057acaf0012";
        // the main bill fid
        String mainFid = UUID.randomUUID().toString().replaceAll("-","");

        final String billNumber = "100720180201000009266069";// CodeUtil.genReceiBillCode(DocumentTypeEnum.LIFE_FEE.getCode());
        final Double oughtTotalAmount = 310.5D;
        finReceiBillDto = FinReceiBillDto.builder()
                .fid(mainFid)
                .contractId("213")
                .billNumber(billNumber)
                .deviceTypeName(MeterTypeEnum.ELECTRICITY.name())
                .reading(230.0D)
                .oughtTotalAmount(oughtTotalAmount)
                .billState(1)
                .billType(Integer.valueOf(DocumentTypeEnum.LIFE_FEE.getCode()))
                .genWay(1)
                .actualTotalAmount(oughtTotalAmount)
                .planGatherDate(new Date())
                .commonts("1")
                .createId("53213bf7-4d23-edb4-8e5a-52ce7ae1bcf7")
                .updateId("53213bf7-4d23-edb4-8e5a-52ce7ae1bcf7")
                .createTime(new Date())
                .updateTime(new Date())
                .startCycle(new Date())
                .endCycle(new Date())
                .cityId("10001")
                .build();

        // 应收账单明细
        finReceiBillDetailDtos.add(
                FinReceiBillDetailDto.builder()
                        .fid(KeyGenUtils.genKey())
                        .expenseItemId(Integer.valueOf(ExpenseItemEnum.DF.getId()))
                        .oughtAmount(oughtTotalAmount)
                        .actualAmount(oughtTotalAmount)
                        .cityId("10001")
                        .roomId("123213")
                        .createId("53213bf7-4d23-edb4-8e5a-52ce7ae1bcf7")
                        .createTime(new Date())
                        .updateTime(new Date())
                        .isDel(0)
                        .isValid(1)
                        .status(0)
                        .remark("备注测试")
                        .billType(DocumentTypeEnum.LIFE_FEE.getCode())
                        .billFid(mainFid)
                        .billNum(billNumber)
                        .actualAmount(66.9)
                        .build()
        );

        finReceiBillDto.setFinReceiBillDetailDtos(finReceiBillDetailDtos);

        String dtoStr = "";

        // test catch exception
        try {
            dtoStr = finReceiBillServiceProxy.saveFinanceReceivableOfWaterWatt(finReceiBillDto);
            log.info(dtoStr);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(dtoStr);

        Assert.notNull(dto, "保存应收账单异常，结果为null！");
        Assert.isTrue(DataTransferObject.SUCCESS == dto.getCode(), String.format("保存水电表应收账单失败:%s", dto.getMsg()));
    }
}
