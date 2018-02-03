/**
 * @FileName: MsgHouseServiceProxyTest.java
 * @Package com.ziroom.minsu.services.message.test.proxy
 * 
 * @author yd
 * @created 2016年4月18日 下午9:22:24
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.test.proxy;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.message.MsgHouseEntity;
import com.ziroom.minsu.services.message.dto.AppChatRecordsDto;
import com.ziroom.minsu.services.message.dto.AppChatRecordsExt;
import com.ziroom.minsu.services.message.dto.MsgHouseRequest;
import com.ziroom.minsu.services.message.entity.MsgHouseListVo;
import com.ziroom.minsu.services.message.proxy.MsgHouseServiceProxy;
import com.ziroom.minsu.services.message.test.base.BaseTest;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;

/**
 * <p>信息房源关系代理层测试</p>
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
public class MsgHouseServiceProxyTest extends BaseTest{

	@Resource(name = "message.msgHouseServiceProxy")
	private MsgHouseServiceProxy msgHouseServiceProxy;


	@Test
	public void queryAllByPageTest() {
		MsgHouseRequest msgHouseRequest = new MsgHouseRequest();
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.msgHouseServiceProxy.queryAllByPage(JsonEntityTransform.Object2Json(msgHouseRequest)));
		List<MsgHouseEntity> listEntities = dto.parseData("listMsgHouse", new TypeReference<List<MsgHouseEntity>>() {
		});
		System.out.println(listEntities);
	}

	@Test
	public void queryLandlordByPageTest(){
		MsgHouseRequest msgHouseRequest = new MsgHouseRequest();
		msgHouseRequest.setLandlordUid("4f5ds45f6d");
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.msgHouseServiceProxy.queryLandlordByPage(JsonEntityTransform.Object2Json(msgHouseRequest)));
		List<MsgHouseEntity> listEntities = dto.parseData("listMsgHouse", new TypeReference<List<MsgHouseEntity>>() {
		});
		System.out.println(listEntities);
	}

	@Test
	public void queryTenantByPageTest(){
		MsgHouseRequest msgHouseRequest = new MsgHouseRequest();
		msgHouseRequest.setTenantUid("4f5ds45f6d");
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.msgHouseServiceProxy.queryTenantByPage(JsonEntityTransform.Object2Json(msgHouseRequest)));
		List<MsgHouseEntity> listEntities = dto.parseData("listMsgHouse", new TypeReference<List<MsgHouseEntity>>() {
		});
		System.out.println(listEntities);
	}

	@Test
	public void saveTest(){
		MsgHouseEntity msgHouseEntity = new MsgHouseEntity();
		msgHouseEntity.setBedFid("F45fdsfdsf6DS4F");
		msgHouseEntity.setCreateTime(new Date());
		msgHouseEntity.setHouseFid("fdfdsfdfs45f6");
		msgHouseEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
		msgHouseEntity.setLandlordUid("f45fdsfdsfds64f");
		msgHouseEntity.setRentWay(RentWayEnum.BED.getCode());
		msgHouseEntity.setTenantUid("4f5ds6f45d6sf");
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.msgHouseServiceProxy.save(JsonEntityTransform.Object2Json(msgHouseEntity)));

		System.out.println(dto.getData().get("result"));
	}

	@Test
	public void deleteByFidTest(){
		MsgHouseEntity msgHouseEntity = new MsgHouseEntity();
		msgHouseEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
		msgHouseEntity.setFid("8a9e9c8b541e32c001541e32c0150000");

		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.msgHouseServiceProxy.deleteByFid(JsonEntityTransform.Object2Json(msgHouseEntity)));

		System.out.println(dto.getData().get("result"));
	}

	@Test
	public void testQueryTenantList(){
		MsgHouseRequest msgHouseRequest = new MsgHouseRequest();
		msgHouseRequest.setTenantUid("4f5ds6f45d6sf");
		msgHouseRequest.setLimit(10);
		msgHouseRequest.setPage(1);
		String resultJson = msgHouseServiceProxy.queryTenantList(JsonEntityTransform.Object2Json(msgHouseRequest));
		List<MsgHouseListVo> json2List = JsonEntityTransform.json2List(resultJson, MsgHouseListVo.class);

		System.out.println(json2List.toString());
	}

	@Test
	public void queryLandlordListTest(){
		MsgHouseRequest msgHouseRequest = new MsgHouseRequest();
		msgHouseRequest.setLandlordUid("9afeae98-56ff-0c35-77cf-8624b2e1efae");;

		String resultJson = msgHouseServiceProxy.queryLandlordList(JsonEntityTransform.Object2Json(msgHouseRequest));

		System.out.println(resultJson);
	}
	@Test
	public void queryFriendsUidTest(){

		MsgHouseRequest msRequest = new MsgHouseRequest();

		//msRequest.setLandlordUid("7a8c4184-8e2e-37b4-08e8-f4c20225e350");
		msRequest.setTenantUid("7a8c4184-8e2e-37b4-08e8-f4c20225e350");
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.msgHouseServiceProxy.queryFriendsUid(JsonEntityTransform.Object2Json(msRequest)));
		
		System.out.println(dto);

	}
	
	@Test
	public void saveImMsgTest(){
		AppChatRecordsDto appChatRecordsDto  = new AppChatRecordsDto();
		appChatRecordsDto.setLandlordUid("acc89c6f-9fd8-488b-beac-4d818601a140");
		appChatRecordsDto.setTenantUid("1322e66a-bc44-4776-a104-3296171e9aba");
		String msg = "民国1927B地址：延安中路545弄明德里21号后门（是21号后门不是小区后门，百度地图app下一个导航很精准）巨鹿路进小区正门后靠近正门右手边第一排看见垃圾箱右转，看见健身器材在第二排再右转。如果找到的是黑色大门就返回建筑物另一边找红色后门。如果从延安中路后门（晚11点关闭）进入则走到底左手边最后一个弄堂左转，看见健身器材再第二排左转。\\n\\n路线：2/12/13号线南京西路站12号口出，；1/10/12号线陕西南路站2号口出。\\n\\n钥匙：红色木头门旁边信箱里，密码：321红色钥匙牌上写着“二楼B”，勿拿其他钥匙，出入红色木门勿反锁，关上就好。\\n房间：二楼入口楼梯处，房间门上字母B\\n\\nWi-Fi：ChinaNet-QeYu\\n密码：mwcyj9yy\\n \\n请遵守以下规则：\\n1.保持整洁，如烧饭需立即清理厨房，离开时垃圾带走，所有电源关闭，钥匙放回信箱里。\\n2.勿大声喧哗引起邻居投诉。\\n3.勿在床上饮食，床品油污或血渍需赔付80元。\\n4.卫生纸勿投入马桶，造成堵塞需赔付200元。\\n \\n有任何问题及时沟通，我会为您解决。请勿问邻居以及保安，电话：15618596212 **：mimicococo希";
		if(msg.length()>500){
			msg = msg.substring(0, 500);
		}
		appChatRecordsDto.setMsgContent(msg);
		appChatRecordsDto.setMsgSentType(UserTypeEnum.LANDLORD_HUAXIN.getUserType());
		appChatRecordsDto.setSendNum("2");
		
		AppChatRecordsExt appChatRecordsExt =  new AppChatRecordsExt();
		appChatRecordsExt.setEndDate("2016-09-29");
		appChatRecordsExt.setFid("8a9e9a8b53d6089f0153d608a1f80002");
		appChatRecordsExt.setHouseCard("1");
		appChatRecordsExt.setHouseName("测试房源名称");
		appChatRecordsExt.setHousePicUrl("http://pic.ziroom.com.cn/static/images/default.png");
		appChatRecordsExt.setPersonNum("2");
		appChatRecordsExt.setRentWay(RentWayEnum.HOUSE.getCode());
		appChatRecordsExt.setStartDate("2016-09-21");
		appChatRecordsExt.setZiroomFlag("ZIROOM_MINSU_IM");
		appChatRecordsExt.setEm_expr_big_name("FDFDASFDFAS");
		appChatRecordsDto.setAppChatRecordsExt(appChatRecordsExt);
		
		DataTransferObject dto = 	JsonEntityTransform.json2DataTransferObject(this.msgHouseServiceProxy.saveImMsg(JsonEntityTransform.Object2Json(appChatRecordsDto)));
		
		System.out.println(dto);
	}
	
	@Test
	public void queryConsultNumByHouseFidTest(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startTime", "2016-11-01");
		paramMap.put("endTime", "2016-11-30");
		String resultJson = this.msgHouseServiceProxy.queryConsultNumByHouseFid(JsonEntityTransform.Object2Json(paramMap));
		System.err.println(resultJson);
	}
}
