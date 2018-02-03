package com.ziroom.minsu.services.order.test.proxy;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.order.dto.CancelOrderServiceRequest;
import com.ziroom.minsu.services.order.proxy.CancelOrderServiceProxy;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.order.CancelTypeEnum;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2017/1/6 14:52
 * @version 1.0
 * @since 1.0
 */
public class CancelOrderServiceProxyTest extends BaseTest{

    @Resource(name = "order.cancelOrderServiceProxy")
    CancelOrderServiceProxy cancelOrderServiceProxy;

    @Test
    public void cancelOrderNegotiate() throws Exception {
        CancelOrderServiceRequest request = new CancelOrderServiceRequest();
        request.setOrderSn("160511K2U14JVG190954");
        request.setOperUid("8a9e9aaf5456d812015456ec711d1963");
        request.setCancelType(CancelTypeEnum.LANDLOR_APPLY.getCode());
        request.setCancelReason("测试协商取消订单");
        request.setPenaltyMoney(0);
        request.setIsTakeLandlordComm(0);
        request.setIsReturnCleanMoney(1);
        request.setIsReturnTonightRental(1);
        request.setIsEdit(1);
        request.setIsTakeFirstNightMoney(0);
        request.setIsTakeOneHundred(1);

        request.setIsCancelAngel(YesOrNoEnum.YES.getCode());
        request.setIsAddSystemEval(YesOrNoEnum.YES.getCode());
        request.setIsUpdateRankFactor(YesOrNoEnum.YES.getCode());
        request.setIsGiveCoupon(YesOrNoEnum.YES.getCode());
        request.setIsShieldCalendar(YesOrNoEnum.YES.getCode());
        
        request.setEmpCode("20223709");
        request.setOperName("杨东测试房东申请取消");
		
        String s = cancelOrderServiceProxy.cancelOrderNegotiate(JsonEntityTransform.Object2Json(request));
        System.err.println(s);
    }


   @Test
    public void testgetOrderConfigListByOrderSn(){
        String s = cancelOrderServiceProxy.getOrderConfigListByOrderSn("1701099Z47X2XV141055");
       System.out.println(s);
    }
   
   @Test
   public void testgetFinancePenaltyByOrderSn(){
       String s = cancelOrderServiceProxy.getFinancePenaltyByOrderSn("170515VS789GHG192250");
      System.out.println(s);
   }
   
   @Test
   public void testgetIsDoneAllPunish(){
       String s = cancelOrderServiceProxy.getIsDoneAllPunish("170515VS789GHG192250");
      System.out.println(s);
   }
   
   
}
