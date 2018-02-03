package com.ziroom.minsu.services.house.test.proxy;

import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseDescEntity;
import com.ziroom.minsu.entity.house.HouseUpdateFieldAuditManagerEntity;
import com.ziroom.minsu.services.house.dao.HouseUpdateFieldAuditManagerDao;
import com.ziroom.minsu.services.house.issue.dto.HouseTypeLocationDto;
import com.ziroom.minsu.services.house.issue.vo.HouseBaseVo;
import com.ziroom.minsu.services.house.proxy.HouseIssueAppServiceProxy;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import org.junit.Test;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * <p>房东端-房源发布</p>
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
public class HouseIssueServiceAppProxyTest extends BaseTest {

	@Resource(name = "house.houseIssueAppServiceProxy")
	private HouseIssueAppServiceProxy houseIssueAppServiceProxy;

	@Test
	public void saveHouseConfListTest(){
//		String paramJson = "{\n" +
//				"  \"uid\" : \"1233587a-7096-4737-b4f7-e2c142b6e64d\",\n" +
//				"  \"rentWay\" : \"1\","
//				"  \"houseBaseFid\" : \"8a90a2d45d1c2c95015d1c36f418005e\"" +
//				"}";
//		String result = houseIssueAppServiceProxy.saveHouseDescAndBaseInfoSublet(paramJson);
//		System.out.print(result);
	}

	@Test
	public void saveHouseDescAndBaseInfoEntire(){
		String paramJson = "{\n" +
				"  \"leasePrice\" : 0,\n" +
				"  \"houseDesc\" : \"或大或小继续简单家你想念想念想念你那大男大女你的内心想念家你的你的惊喜交加心心念念继续继续继续就减肥减肥家的家继续奶茶坚持坚持家坚持继续简单继续继续继续简单家继续继续继续继续继续继续家你真心经济学家下降自己继续继续\",\n" +
				"  \"cleaningFees\" : 0,\n" +
				"  \"houseFacility\" : \"ProductRulesEnum002001_1,ProductRulesEnum002001_2,ProductRulesEnum002001_3\",\n" +
				"  \"houseBaseFid\" : \"8a9084df5dc0d391015dc0d57d060016\",\n" +
				"  \"roomFid\" : \"\",\n" +
				"  \"weekendPriceVal\" : 0,\n" +
				"  \"houseRoomList\" : [\n" +
				"    {\n" +
				"      \"fid\" : \"8a9084df5dc0d391015dc0d768d40026\",\n" +
				"      \"bedMsg\" : \"1_1,2_1\"\n" +
				"    },\n" +
				"    {\n" +
				"      \"fid\" : \"8a9084df5dc0d391015dc0d768e00029\",\n" +
				"      \"bedMsg\" : \"3_1,4_1\"\n" +
				"    },\n" +
				"    {\n" +
				"      \"fid\" : \"8a9084df5dc0d391015dc0d768e9002c\",\n" +
				"      \"bedMsg\" : \"1_1,2_1\"\n" +
				"    },\n" +
				"    {\n" +
				"      \"fid\" : \"8a9084df5dc0d391015dc0d768ef002f\",\n" +
				"      \"bedMsg\" : \"1_1,2_1\"\n" +
				"    },\n" +
				"    {\n" +
				"      \"fid\" : \"8a9084df5dc0d391015dc0d768f80032\",\n" +
				"      \"bedMsg\" : \"1_1,2_1\"\n" +
				"    }\n" +
				"  ],\n" +
				"  \"houseModel\" : \"5,1,2,1,1\",\n" +
				"  \"fullDayRateSwitch\" : 0,\n" +
				"  \"weekendPriceSwitch\" : 0,\n" +
				"  \"sevenDiscountRate\" : 0,\n" +
				"  \"houseAroundDesc\" : \"自己继续下降超级减肥减肥家的解决方法可参考参考参考参考参考课程开发可参考参考参考吃饭看看可参考参考参考参考参考参考参考参考可参考可参考参考参考参考参考参考参考参考可参考参考参考参考参考开心开心开心可参考参考\",\n" +
				"  \"uid\" : \"1233587a-7096-4737-b4f7-e2c142b6e64d\",\n" +
				"  \"thirtyDiscountRate\" : 0,\n" +
				"  \"houseName\" : \"fdsafsafaff5656fdfdsfdg\",\n" +
				"  \"houseArea\" : 258,\n" +
				"  \"weekendPriceType\" : \"\",\n" +
				"  \"rentWay\" : 0,\n" +
				"  \"checkInLimit\" : \"3\"\n" +
				"}\n";
		String result = houseIssueAppServiceProxy.saveHouseDescAndBaseInfoEntire(paramJson);
	}


	/**
	 * @description: 过滤房源需要审核的字段，将审核字段设置为null
	 * @author: lusp
	 * @date: 2017/8/8 14:26
	 * @params: obj,houseUpdateFieldAuditManagerEntities
	 * @return: obj
	 */
	public static <T extends Object> T FilterAuditField(T obj,List<HouseUpdateFieldAuditManagerEntity> houseUpdateFieldAuditManagerEntities)throws NoSuchMethodException,IllegalAccessException,InvocationTargetException {
		if(Check.NuNObj(houseUpdateFieldAuditManagerEntities)||Check.NuNObj(obj)){
			return obj;
		}
		Class<?> clazz = obj.getClass();
		String className = clazz.getName();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field:fields){
			String fieldName = field.getName();
			if("serialVersionUID".equals(fieldName)){
				continue;
			}
			String methodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
			Class<?> type = field.getType();
			Method method = clazz.getMethod(methodName,type);
			String fieldFullName = className+"."+fieldName;
			for(HouseUpdateFieldAuditManagerEntity houseUpdateFieldAuditManagerEntity:houseUpdateFieldAuditManagerEntities){
				if(houseUpdateFieldAuditManagerEntity.getFieldPath().trim().equals(fieldFullName.trim())){
					method.invoke(obj,new Object[]{null});
					break;
				}
			}
		}
		return obj;
	}

	@Resource(name = "house.houseUpdateFieldAuditManagerDao")
	private HouseUpdateFieldAuditManagerDao houseUpdateFieldAuditManagerDao;

	@Test
	public void FilterAuditFieldTest()throws Exception{
		List<HouseUpdateFieldAuditManagerEntity> houseUpdateFieldAuditManagerEntities = houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByType(0);
		HouseBaseMsgEntity houseBaseMsgEntity=new HouseBaseMsgEntity();
		houseBaseMsgEntity.setHouseName("fjlkdsjfljsdlkfjsdjfejeufioewu");
		houseBaseMsgEntity.setToiletNum(9);
		HouseDescEntity desc = new HouseDescEntity();
		desc.setHouseAroundDesc("我们之间金风科技开房记录发九分裤的房间");
		houseBaseMsgEntity = this.FilterAuditField(houseBaseMsgEntity,houseUpdateFieldAuditManagerEntities);
		desc = this.FilterAuditField(desc,houseUpdateFieldAuditManagerEntities);
		System.out.println(houseBaseMsgEntity);
		System.out.println(desc);

	}

	@Test
	public void SaveHousePhyAndExtTest() throws SOAParseException {
		HouseTypeLocationDto dto=new HouseTypeLocationDto();
		dto.setRentWay(RentWayEnum.ROOM.getCode());
		dto.setRoomType(1);
		dto.setHouseType(2);
		dto.setRegionCode("100000,110000,110100,110101");
		dto.setRegionName("中国,北京,北京市,朝阳区");
		dto.setHouseStreet("合租null");
		dto.setLandlordUid("合租null");
		dto.setHouseSource(3);
		//dto.setHouseGuardRel();
		String param = JsonEntityTransform.Object2Json(dto);
		String resultJson=houseIssueAppServiceProxy.saveHousePhyAndExt(param);
		HouseBaseVo houseBaseVo= SOAResParseUtil.getValueFromDataByKey(resultJson, "houseBaseVo", HouseBaseVo.class);
		System.err.print(houseBaseVo.toString());

	}

	@Test
	public void saveHouseMsgAndConf() {
		//待写
	}
}
