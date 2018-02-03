package com.ziroom.minsu.services.order.test.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.ziroom.minsu.services.order.service.OrderCommonServiceImpl;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;
import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.services.order.dto.OrderRequest;
import com.ziroom.minsu.services.order.entity.OrderDetailVo;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.valenum.order.OrderParamEnum;

/**
 * <p>公共的订单类的测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/3.
 * @version 1.0
 * @since 1.0
 */
public class OrderCommonServiceImplTest extends BaseTest {

    @Resource(name = "order.orderCommonServiceImpl")
    private OrderCommonServiceImpl orderCommonService;




    @Test
    public void Test() {

        OrderInfoVo infoVo = orderCommonService.getOrderInfoByOrderSn("16041974RRJ8SX172841");
        OrderStatusEnum statusEnum = OrderStatusEnum.getOrderStatusByCode(infoVo.getOrderStatus());
        String aa = statusEnum.getShowName(infoVo);
        System.out.println(aa);
    }


    @Test
    public void testupdateOrderInfo() throws Exception{

        OrderEntity orderUpdateVo = new OrderEntity();
        orderUpdateVo.setOrderSn("8a9e9cd253d0b29d0153d0b29d460001");
        orderUpdateVo.setOrderStatus(10);
        orderCommonService.updateOrderInfoAndStatus("",10, orderUpdateVo,null, "test", OrderParamEnum.OTHER_COST_DES.getCode());
    }
    
    @Test
    public void testOrderHouseToOrderDetail() throws Exception{

        OrderDetailVo orderDetailVo = this.orderCommonService.orderHouseToOrderDetail("170719030A9M47144353");

        if(orderDetailVo!=null){
    		System.out.println(orderDetailVo.toString());
    	}
    }
    
    @Test
    public void testGetOrderListByCondiction() throws Exception{
    	/*OrderRequest orderRequest = new OrderRequest();
		orderRequest.setLandlordUid("testfafngdong");
		orderRequest.setRequestType(2);
		
		//查询待处理订单
		List<Integer> listOrderStatus = new ArrayList<Integer>();
		listOrderStatus.add(10);
		listOrderStatus.add(50);
		orderRequest.setListOrderStatus(listOrderStatus);*/
    	OrderRequest orderRequest = new OrderRequest();
		orderRequest.setUserUid("1122222");
		orderRequest.setRequestType(1);
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(100);
		list.add(110);
		list.add(200);
		orderRequest.setListEvaStatus(list);
		orderRequest.setPage(1);
		orderRequest.setPage(10);
		PagingResult<OrderInfoVo> pagingResult = orderCommonService.getOrderInfoListByCondiction(orderRequest);
		
		if(pagingResult!=null){
			System.out.println(pagingResult);
		}
    }
    
    @Test
    public void testGetOrderBaseByOrderSn() throws Exception{
    	String orderSn = "8a9e9cd253d0b29d0153d0b29d460001";
    	OrderEntity orderEntity = orderCommonService.getOrderBaseByOrderSn(orderSn);
    	if(orderEntity!=null){
    		System.out.println(orderEntity.toString());
    	}
    }

    
    @Test
    public void testQueryOrderByCondition(){
    	
    	List<String> listOrderSn = new ArrayList<String>();
    	listOrderSn.add("8a9e9cd253d0b29d0153d0b29d460001");
    	listOrderSn.add("8a9e9cc253e9938a0153e9938c370009");
    	listOrderSn.add("8a9e9cc253e9938a0153e9938d380015");
    	listOrderSn.add("8a9e9cc253e9938a0153e9938ed90021");
    	
    	OrderRequest orderRequest = new OrderRequest();
    	orderRequest.setListOrderSn(listOrderSn);
    	List<OrderEntity> lsitEntities = this.orderCommonService.queryOrderByCondition(orderRequest);
    	
    	System.out.println(lsitEntities);
    }
    
    @Test
    public void testUpdateEvaStatuByOrderSn(){
    	
    	List<String> listOrderSn = new ArrayList<String>();
    	listOrderSn.add("8a9e9cd253d0b29d0153d0b29d460001");
    	/*listOrderSn.add("8a9e9cc253e9938a0153e9938c370009");
    	listOrderSn.add("8a9e9cc253e9938a0153e9938d380015");
    	listOrderSn.add("8a9e9cc253e9938a0153e9938ed90021")*/;
    	OrderRequest orderRequest = new OrderRequest();
    	orderRequest.setListOrderSn(listOrderSn);
    	orderRequest.setEvaStatus(111);
    	int index = this.orderCommonService.updateEvaStatuByOrderSn(orderRequest);
    	
    	System.out.println(index);
    }
}
