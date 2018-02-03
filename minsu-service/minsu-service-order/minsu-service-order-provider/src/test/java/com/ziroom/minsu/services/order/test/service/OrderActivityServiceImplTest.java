package com.ziroom.minsu.services.order.test.service;

import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.finance.entity.OrderActivityInfoVo;
import com.ziroom.minsu.services.order.dto.CanclOrderRequest;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.services.order.service.OrderActivityServiceImpl;
import com.ziroom.minsu.services.order.service.OrderCommonServiceImpl;
import com.ziroom.minsu.services.order.service.OrderUserServiceImpl;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.valenum.order.OrderAcTypeEnum;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>用户的测试</p>
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
public class OrderActivityServiceImplTest extends BaseTest{

    @Resource(name = "order.orderActivityServiceImpl")
    private OrderActivityServiceImpl orderActivityService;


    @Test
    public void testlistOrderActByOrderSnAndType() throws Exception{
        List<Integer> types = Arrays.asList(OrderAcTypeEnum.COUPON.getCode(), OrderAcTypeEnum.FIRST_ORDER_REDUC.getCode());
        List<OrderActivityInfoVo> orderActList = orderActivityService.listOrderActByOrderSnAndType("1706074KQQCOJ4114207",types);
        System.err.println(JsonEntityTransform.Object2Json(orderActList));
    }


}
