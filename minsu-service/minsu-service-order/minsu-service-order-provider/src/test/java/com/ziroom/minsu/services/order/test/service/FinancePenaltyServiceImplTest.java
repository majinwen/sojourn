package com.ziroom.minsu.services.order.test.service;

import com.ziroom.minsu.entity.order.FinancePayVouchersEntity;
import com.ziroom.minsu.services.order.service.FinancePayServiceImpl;
import com.ziroom.minsu.services.order.service.FinancePenaltyServiceImpl;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by homelink on 2017/5/12.
 */
public class FinancePenaltyServiceImplTest extends BaseTest {

    @Resource(name = "order.financePenaltyServiceImpl")
    private FinancePenaltyServiceImpl financePenaltyServiceImpl;

    @Resource(name = "order.financePayServiceImpl")
    private FinancePayServiceImpl financePayServiceImpl;

    @Test
    public void testpenaltyOffsetPayAndTran2Income(){
        FinancePayVouchersEntity fk1703098134VDLZ112400 = financePayServiceImpl.findPayVouchersByPvSn("FK170220DO6IEXOA132000");
        financePenaltyServiceImpl.penaltyOffsetPayAndTran2Income(fk1703098134VDLZ112400);
    }
}
