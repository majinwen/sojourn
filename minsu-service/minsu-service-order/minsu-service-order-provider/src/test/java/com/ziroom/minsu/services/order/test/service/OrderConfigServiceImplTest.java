package com.ziroom.minsu.services.order.test.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.base.MinsuEleEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.order.service.OrderConfigServiceImpl;
import com.ziroom.minsu.valenum.traderules.*;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import com.asura.framework.base.util.DateUtil;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.services.order.entity.CommissionVo;
import com.ziroom.minsu.services.order.entity.OrderConfigVo;

/**
 * <p>订单配置的测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/2.
 * @version 1.0
 * @since 1.0
 */
public class OrderConfigServiceImplTest extends BaseTest{



    @Resource(name = "basedata.cityTemplateService")
    private CityTemplateService cityTemplateService;




    @Resource(name = "order.orderConfigServiceImpl")
    private OrderConfigServiceImpl orderConfigService;





    @Test
    public void TestGetCheckOutStrategyByOrderSn() throws Exception{
        String orderSn = "161212N5JKX8XC162509";
        CheckOutStrategy checkOutStrategy = orderConfigService.getCheckOutStrategyByOrderSn(orderSn);
        System.out.println(JsonEntityTransform.Object2Json(checkOutStrategy));
    }




    @Test
    public void TestgetTextListByCodes() throws Exception{
        List<String> codeList = new ArrayList<>();
        codeList.add(TradeRulesEnum005Enum.TradeRulesEnum005002.getValue());
        String confJson =  cityTemplateService.getTextListByLikeCodes(null, ValueUtil.transList2Str(codeList));
        System.out.println(confJson);
        DataTransferObject cinfDto = JsonEntityTransform.json2DataTransferObject(confJson);
        List<MinsuEleEntity> confList = null;
        if(cinfDto.getCode() == DataTransferObject.SUCCESS){
            confList = cinfDto.parseData("confList", new TypeReference<List<MinsuEleEntity>>() {
            });
        }else {

        }
        System.out.println(JsonEntityTransform.Object2Json(codeList));
    }
    
//    @Test
//    public void TestGetCheckInTime() throws Exception{
//    	String orderSn = "8a9e9cd253d0b29d0153d0b29d460001";
//    	orderConfigService.getCheckInTime(orderSn);
//    }

    
//    @Test
//    public void TestGetCheckOutTime() throws Exception{
//    	String orderSn = "8a9e9cd253d0b29d0153d0b29d460001";
//    	orderConfigService.getCheckOutTime(orderSn);
//    }
//
    
    /**
     * 获取订单配置信息
     * @author lishaochuan
     * @create 2016年5月16日上午11:39:23
     */
    @Test
    public void TestGetOrderConfigVo(){
    	String orderSn = "160516AI6SAR7V110538";
    	OrderConfigVo orderConfigVo = orderConfigService.getOrderConfigVo(orderSn);
		System.err.println("orderConfigVo:" + orderConfigVo);
    }
}
