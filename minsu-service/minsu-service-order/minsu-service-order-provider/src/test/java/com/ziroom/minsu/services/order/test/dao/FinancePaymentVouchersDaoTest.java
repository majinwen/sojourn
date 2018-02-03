package com.ziroom.minsu.services.order.test.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.order.FinancePaymentVouchersEntity;
import com.ziroom.minsu.services.finance.dto.PaymentVouchersRequest;
import com.ziroom.minsu.services.finance.entity.FinancePaymentVo;
import com.ziroom.minsu.services.order.dao.FinancePaymentVouchersDao;
import com.ziroom.minsu.services.order.test.base.BaseTest;

/**
 * <p>测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/22.
 * @version 1.0
 * @since 1.0
 */
public class FinancePaymentVouchersDaoTest extends BaseTest{

    @Resource(name = "order.financePaymentVouchersDao")
    private FinancePaymentVouchersDao financePaymentVouchersDao;

    @Test
    public void changeCouponMoney(){

        financePaymentVouchersDao.changeCouponMoney("160419V6621S1J172304",1);
    }


    @Test
    public void insertPaymentVouchersTest(){
    	FinancePaymentVouchersEntity financePaymentVouchers = new FinancePaymentVouchersEntity();
    	financePaymentVouchers.setFid(UUIDGenerator.hexUUID());
    	financePaymentVouchers.setPaymentSn(UUIDGenerator.hexUUID());
    	financePaymentVouchers.setOrderSn(UUIDGenerator.hexUUID());
    	financePaymentVouchers.setPayTime(new Date());
    	financePaymentVouchersDao.insertPaymentVouchers(financePaymentVouchers);
    }
    
    @Test
    public void updatePaymentVouchersTest(){
    	financePaymentVouchersDao.taskUpdatePaymentVouchers("8a9e9c9b545d273501545d2735140000");
    }
    
    @Test
    public void findForPagePaymentVouchersTest(){
    	PaymentVouchersRequest paramRequest = new PaymentVouchersRequest();
    	paramRequest.setLimit(5);
    	paramRequest.setPage(1);
    	PagingResult<FinancePaymentVouchersEntity> res = financePaymentVouchersDao.getPaymentVouchersList(paramRequest);
       System.out.println("-------"+JsonEntityTransform.Object2Json(res)); 
    }
    
    @Test
    public void queryPaymentVoByPageTest(){
    	PaymentVouchersRequest paramRequest = new PaymentVouchersRequest();
    	paramRequest.setLimit(5);
    	paramRequest.setPage(1);
    	PagingResult<FinancePaymentVo> res = financePaymentVouchersDao.queryPaymentVoByPage(paramRequest);
       System.out.println("-------"+JsonEntityTransform.Object2Json(res)); 
    }
}
