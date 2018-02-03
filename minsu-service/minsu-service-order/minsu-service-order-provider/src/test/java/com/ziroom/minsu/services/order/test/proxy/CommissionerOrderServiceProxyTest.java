package com.ziroom.minsu.services.order.test.proxy;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.order.OrderRelationEntity;
import com.ziroom.minsu.services.order.dto.OrderRelationRequest;
import com.ziroom.minsu.services.order.dto.OrderRelationSaveRequest;
import com.ziroom.minsu.services.order.entity.OrderRelationVo;
import com.ziroom.minsu.services.order.proxy.CommissionerOrderServiceProxy;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.valenum.order.CheckedStatusEnum;

/**
 * <p>客服操作强制取消订单测试</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class CommissionerOrderServiceProxyTest extends BaseTest{

	@Resource(name = "order.commissionerOrderServiceProxy")
	private CommissionerOrderServiceProxy commissionerOrderServiceProxy;
	@Test
	public void queryOrderRelanionByPageTest() {
		OrderRelationRequest relationRequest  = new OrderRelationRequest();
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.commissionerOrderServiceProxy.queryOrderRelanionByPage(JsonEntityTransform.Object2Json(relationRequest)));

		List<OrderRelationVo> listOrderRelationVos = dto.parseData("listOrderRelationVo", new TypeReference<List<OrderRelationVo>>() {
		});

		System.out.println(listOrderRelationVos);
	}

	@Test
	public void saveOrUpdateOrderRelationTest(){

		OrderRelationSaveRequest orderRelationSave  = new OrderRelationSaveRequest();

		OrderRelationEntity orderRelationEntity = new OrderRelationEntity();
		orderRelationEntity.setAuditPersonFid("fdsffdsfdsdsfdsfdfd");
		orderRelationEntity.setCheckedStatus(CheckedStatusEnum.INIT_CHECKED.getCode());
		orderRelationEntity.setCreatedFid("456456456456");
		orderRelationEntity.setCreatedTime(new Date());
		orderRelationEntity.setLastModifyDate(new Date());
		orderRelationEntity.setNewOrderSn("160419DUAT580C173428");
		orderRelationEntity.setOldOrderSn("160421NSX89EX3165044");
		orderRelationEntity.setOrderBalance(851000);

		orderRelationSave.setOrderRelationEntity(orderRelationEntity);

		orderRelationSave.setPayUid("11111");

		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.commissionerOrderServiceProxy.saveOrderRelation(JsonEntityTransform.Object2Json(orderRelationSave)));
		System.out.println(dto.getData().get("result"));
		
	}

	@Test
	public void updateOrderRelationByConditionTest(){

		OrderRelationSaveRequest orderRelationSave  = new OrderRelationSaveRequest();

		OrderRelationEntity orderRelationEntity = new OrderRelationEntity();
		orderRelationEntity.setAuditPersonFid("fdsffdsfdsdsfdsfdfd");
		orderRelationEntity.setCheckedStatus(CheckedStatusEnum.SYS_CHECKED_OK.getCode());
		orderRelationEntity.setCreatedFid("456456456456");
		orderRelationEntity.setLastModifyDate(new Date());
		orderRelationEntity.setOldOrderSn("160421NSX89EX3165044");
		orderRelationEntity.setNewOrderSn("160419DUAT580C173428");
		orderRelationEntity.setOrderBalance(851000);

		orderRelationSave.setOrderRelationEntity(orderRelationEntity);

		orderRelationSave.setPayUid("11111");
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.commissionerOrderServiceProxy.updateOrderRelationByCondition(JsonEntityTransform.Object2Json(orderRelationSave)));

		System.out.println(dto.getData().get("result"));
	}

	@Test
	public void compulsoryCancellOrderByLandlordTest(){

		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.commissionerOrderServiceProxy.compulsoryCancellOrderByLandlord(JsonEntityTransform.Object2Json("16042157HI7218182613")));

		System.out.println(dto.getData().get("result"));
	}

	@Test
	public void recoveryCancellOrdeTest(){
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.commissionerOrderServiceProxy.recoveryCancelOrde(JsonEntityTransform.Object2Json("16041974RRJ8SX172841"),""));

		System.out.println(dto.getData().get("result"));
	}

	
	@Test
	public void agreeCancelOrdeTest(){
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.commissionerOrderServiceProxy.agreeCancelOrde(JsonEntityTransform.Object2Json("16042157HI7218182613"),"fdsafdsf",2));

		System.out.println(dto.getData().get("result"));
	}
	
	
	@Test
	public void findCancleOrderVoTest(){

		
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.commissionerOrderServiceProxy.findCancleOrderVo("1701123TK4323Q205042"));
		
		System.out.println(dto);
	}
}
