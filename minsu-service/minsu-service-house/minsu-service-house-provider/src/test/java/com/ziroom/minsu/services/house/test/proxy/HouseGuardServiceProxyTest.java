package com.ziroom.minsu.services.house.test.proxy;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.house.HouseGuardRelEntity;
import com.ziroom.minsu.services.house.dto.HouseGuardDto;
import com.ziroom.minsu.services.house.dto.HouseGuardParam;
import com.ziroom.minsu.services.house.proxy.HouseGuardServiceProxy;
import com.ziroom.minsu.services.house.test.base.BaseTest;

/**
 * <p>房源管家关系代理测试</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class HouseGuardServiceProxyTest extends BaseTest {
	
	@Resource(name = "house.houseGuardServiceProxy")
	private HouseGuardServiceProxy houseGuardServiceProxy;
	
	@Test
	public void searchHouseGuardListTest() {
		HouseGuardDto houseGuardDto = new HouseGuardDto();
		//houseGuardDto.setHouseGuardFid("8a9e9aa855c070e60155c070e6c30001");
		String resultJson = houseGuardServiceProxy.searchHouseGuardList(JsonEntityTransform.Object2Json(houseGuardDto));
		System.err.println(resultJson);
	}
	
	@Test
	public void searchHouseGuardDetailTest() {
		String houseBaseFid = "8a9e9aae5419cc22015419cc250a0002";
		String resultJson = houseGuardServiceProxy.searchHouseGuardDetail(houseBaseFid);
		System.err.println(resultJson);
	}
	
	@Test
	public void searchHouseGuardLogListTest() {
		HouseGuardDto houseGuardDto = new HouseGuardDto();
		houseGuardDto.setHouseGuardFid("8a9e9aa855c070e60155c070e6c30001");
		String resultJson = houseGuardServiceProxy.searchHouseGuardLogList(JsonEntityTransform.Object2Json(houseGuardDto));
		System.err.println(resultJson);
	}
	
	@Test
	public void batchMergeHouseGuardRelTest() {
		HouseGuardParam houseGuardParam = new HouseGuardParam();
		houseGuardParam.setEmpGuardCode("20160706");
		houseGuardParam.setEmpGuardName("维护管家");
		
		List<HouseGuardRelEntity> list = new ArrayList<HouseGuardRelEntity>();
		HouseGuardRelEntity entity = new HouseGuardRelEntity();
		entity.setFid("8a9e9aa855c084680155c084688c0000");
		entity.setHouseFid("8a9e9aae5419cc22015419cc250a0002");
		list.add(entity);
		houseGuardParam.setListGuard(list);
		//String resultJson = houseGuardServiceProxy.batchMergeHouseGuardRel(JsonEntityTransform.Object2Json(houseGuardParam));
		String resultJson = houseGuardServiceProxy.batchMergeHouseGuardRel("{\"empPushCode\":\"20137434\",\"empPushName\":\"瞿琦\",\"empGuardCode\":\"20223709\",\"empGuardName\":\"杨东\",\"listGuard\":[{\"id\":null,\"fid\":null,\"houseFid\":\"8a9e9a9a54b646e60154b646e6d50001\",\"empPushCode\":null,\"empPushName\":null,\"empGuardCode\":null,\"empGuardName\":null,\"createFid\":null,\"createDate\":null,\"lastModifyDate\":null,\"isDel\":null},{\"id\":null,\"fid\":null,\"houseFid\":\"8a9e9a9a548a061301548a67e6300029\",\"empPushCode\":null,\"empPushName\":null,\"empGuardCode\":null,\"empGuardName\":null,\"createFid\":null,\"createDate\":null,\"lastModifyDate\":null,\"isDel\":null}]}");
		System.err.println(resultJson);
	}
	
	
	@Test
	public void findHouseGuardRelByHouseBaseFidTest(){
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.houseGuardServiceProxy.findHouseGuardRelByHouseBaseFid("8a9e9aae5419cc22015419cc250a0002"));
		
		if(dto.getCode() == DataTransferObject.SUCCESS){
			HouseGuardRelEntity hosueGuard = dto.parseData("houseGuardRel", new TypeReference<HouseGuardRelEntity>() {
			});
			
			System.out.println(hosueGuard);
		}
	}
	
	@Test
	public void updateByHouseBaseFidTest(){
		HouseGuardRelEntity houseGuard = new HouseGuardRelEntity();
		houseGuard.setHouseFid("8a9e9aae5419cc22015419cc250a0002");
		houseGuard.setEmpGuardCode("20152597");
		houseGuard.setEmpGuardName("testtest");
        houseGuardServiceProxy.updateHouseGuardByHouseFid(JsonEntityTransform.Object2Json(houseGuard));
		
	}


	@Test
	public void findHouseGuardRelByConditionTest(){
		HouseGuardRelEntity houseGuard = new HouseGuardRelEntity();
		houseGuard.setEmpPushName("");
		houseGuard.setEmpGuardName("刘军");
		String houseGuardRelByCondition = houseGuardServiceProxy.findHouseGuardRelByCondition(JsonEntityTransform.Object2Json(houseGuard));
		System.err.println(houseGuardRelByCondition);
	}
}
