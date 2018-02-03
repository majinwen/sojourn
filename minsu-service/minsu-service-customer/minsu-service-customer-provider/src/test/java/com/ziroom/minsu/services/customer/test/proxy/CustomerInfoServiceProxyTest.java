package com.ziroom.minsu.services.customer.test.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.services.customer.dto.CustomerBaseMsgDto;
import com.ziroom.minsu.services.customer.dto.StaticsCusBaseReqDto;
import com.ziroom.minsu.services.customer.proxy.CustomerInfoServiceProxy;
import com.ziroom.minsu.services.customer.test.BaseTest;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.customer.CustomerAuditStatusEnum;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomerInfoServiceProxyTest extends BaseTest {
	
	@Resource(name = "customer.customerInfoServiceProxy")
	private CustomerInfoServiceProxy customerInfoServiceProxy;
	
	@Test
	public void getCustomerBankcardTest(){
		String result = customerInfoServiceProxy.getCustomerBankcard("eafa5957-7436-2850-ea89-b4829391c0c1");
		System.err.println("result:"+result);
	}


	@Test
	public void selectByConditionTest(){

		CustomerBaseMsgDto customerBaseDto = new CustomerBaseMsgDto();
		customerBaseDto.setRealName("afi");
		 DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.customerInfoServiceProxy.selectByCondition(JsonEntityTransform.Object2Json(customerBaseDto)));
		System.out.println(dto);
	}
	
	@Test
	public void getCustomerInfoByUidTest(){
		System.out.println(this.customerInfoServiceProxy.getCustomerInfoByUid("e0a0f779-9117-6283-84e1-43e0be20ecf4"));
	}
	
	@Test
	public void updateCustomerInfoTest(){
		CustomerBaseMsgEntity customerBaseMsgEntity=new CustomerBaseMsgEntity();
		customerBaseMsgEntity.setUid("05b76912-ad83-4e96-9f9f-b5f7ab9bb4bb");
//		customerBaseMsgEntity.setCustomerBirthday(new Date());
		customerBaseMsgEntity.setCustomerEmail("ghftg88888888@ikik.hfh");
		customerBaseMsgEntity.setNickName("Zk000");
		customerBaseMsgEntity.setCustomerEdu(0);
		
		System.err.println(this.customerInfoServiceProxy.updateCustomerInfo(JsonEntityTransform.Object2Json(customerBaseMsgEntity)));
	}
	
	@Test
	public void staticsGetLandlordListTest(){
		 StaticsCusBaseReqDto customerDto = new StaticsCusBaseReqDto();
		 customerDto.setLimit(30);
		 customerDto.setPage(1);
		System.err.println(this.customerInfoServiceProxy.staticsGetLandlordList(JsonEntityTransform.Object2Json(customerDto)));
	}
	@Test
	public void countLanlordNum(){
		String resultJson = this.customerInfoServiceProxy.countLanlordNum(null);
		System.err.println(resultJson);
		System.err.println(resultJson);
		
	}
	
	
	@Test
	public void getCustomerListByUidListTest(){
		/*List<String> uidList = new ArrayList<>();
		uidList.add("8a9e9a9e543d23f901543d23f9e90000");
		uidList.add("7bbbf57f-6228-5e92-91dc-c9688d4398ce");
		uidList.add("4bf3aaf2-5f1f-4ff6-871c-1000de055665");*/
		Set<String> set = new HashSet<>();
		set.add("8a9e9a9e543d23f901543d23f9e90000");
		set.add("7bbbf57f-6228-5e92-91dc-c9688d4398ce");
		set.add("4bf3aaf2-5f1f-4ff6-871c-1000de055665");
		String customerListByUidList = customerInfoServiceProxy.getCustomerListByUidList(JsonEntityTransform.Object2Json(set));
		System.err.println(customerListByUidList);
	}
	
	@Test
	public void testgetByCustomNameAndTel(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("realName", "徐晓璐");
		paramMap.put("customerMobile", "15810006104");
		paramMap.put("isLandlord",0);
		String customerListByUidList = customerInfoServiceProxy.getByCustomNameAndTel(JsonEntityTransform.Object2Json(paramMap));
		System.err.println(customerListByUidList);
	}
	
	@Test
	public void testgetFieldAuditNewLogByParam(){
		Map<String, Object> map = new HashMap<>();
    	map.put("fieldAuditStatu", 0);
    	map.put("uid", "52a4aea1-5527-7421-1b25-83fbca1c1856");
		String customerListByUidList = customerInfoServiceProxy.getFieldAuditNewLogByParam(JsonEntityTransform.Object2Json(map));
		System.err.println(customerListByUidList);
	}
	
	@Test
	public void testgetLatestUnAuditHeadPic(){
		Map<String, Object> headPicMap = new HashMap<>();
		headPicMap.put("auditStatus", YesOrNoEnum.NO.getCode());
		headPicMap.put("picType", 3);
		headPicMap.put("uid", "52a4aea1-5527-7421-1b25-83fbca1c1856");
		String customerListByUidList = customerInfoServiceProxy.getLatestUnAuditHeadPic(JsonEntityTransform.Object2Json(headPicMap));
		System.err.println(customerListByUidList);
	}
	
	@Test
	public void testgetAllNeedAuditLand(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("fieldAuditStatu", CustomerAuditStatusEnum.UN_AUDIT.getCode());
		String customerListByUidList = customerInfoServiceProxy.getAllNeedAuditLand(JsonEntityTransform.Object2Json(paramMap));
		System.err.println(customerListByUidList);
	}
	
	@Test
	public void testgetCustomerSearchVoByUid(){
		String customerListByUidList = customerInfoServiceProxy.getCustomerSearchVoByUid("52a706a4-570c-4d49-8cf5-450a1339cd85");
		System.err.println(customerListByUidList);
	}

	@Test
	public void demoTest() {
		try {
			String yaoQingRenJson = customerInfoServiceProxy.getCustomerInfoByUid("a071b6d6-8658-44b6-bf43-8c5d54680b6f");
			CustomerBaseMsgEntity yaoQingRenCustomer = SOAResParseUtil.getValueFromDataByKey(yaoQingRenJson, "yaoQingRenCustomer", CustomerBaseMsgEntity.class);
			System.err.println(yaoQingRenCustomer.toJsonStr());
			if (Check.NuNObj(yaoQingRenCustomer)||Check.NuNStr(yaoQingRenCustomer.getCustomerMobile())) {
				System.err.println("失败~~~~~~"+yaoQingRenCustomer.getCustomerMobile());
			}
		} catch (SOAParseException e) {
			e.printStackTrace();
		}
	}
	
}
