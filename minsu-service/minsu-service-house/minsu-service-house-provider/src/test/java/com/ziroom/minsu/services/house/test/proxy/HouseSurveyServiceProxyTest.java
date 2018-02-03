package com.ziroom.minsu.services.house.test.proxy;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.HouseSurveyMsgEntity;
import com.ziroom.minsu.entity.house.HouseSurveyPicMsgEntity;
import com.ziroom.minsu.services.house.proxy.HouseSurveyServiceProxy;
import com.ziroom.minsu.services.house.survey.dto.SurveyPicDto;
import com.ziroom.minsu.services.house.survey.dto.SurveyRequestDto;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import com.ziroom.minsu.valenum.base.RoleTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.house.SurveyOperateTypeEnum;
import com.ziroom.minsu.valenum.house.SurveyPicTypeEnum;
import com.ziroom.minsu.valenum.house.SurveyResultEnum;

/**
 * <p>房源实勘代理测试</p>
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
public class HouseSurveyServiceProxyTest extends BaseTest {
	
	@Resource(name = "house.houseSurveyServiceProxy")
	private HouseSurveyServiceProxy houseSurveyServiceProxy;
	
	@Test
	public void insertHouseSurveyMsgTest() {
		HouseSurveyMsgEntity houseSurveyMsgEntity = new HouseSurveyMsgEntity();
		houseSurveyMsgEntity.setHouseBaseFid("8a9e9aae5419cc22015419cc250a0002");
		String resultJson = houseSurveyServiceProxy.insertHouseSurveyMsg(JsonEntityTransform.Object2Json(houseSurveyMsgEntity));
		System.err.println(resultJson);
	}
	
	@Test
	public void findHouseSurveyMsgByHouseFidTest() {
		String houseBaseFid = "8a9e9aae5419cc22015419cc250a0002";
		String resultJson = houseSurveyServiceProxy.findHouseSurveyMsgByHouseFid(houseBaseFid);
		System.err.println(resultJson);
	}
	
	@Test
	public void findHouseSurveyPicMsgByFidTest() {
		String surveyPicFid = "8a9e99a6587538540158753854480001";
		String resultJson = houseSurveyServiceProxy.findHouseSurveyPicMsgByFid(surveyPicFid);
		System.err.println(resultJson);
	}
	
	@Test
	public void findSurveyPicListByTypeTest() {
		SurveyPicDto picDto = new SurveyPicDto();
		picDto.setSurveyFid("8a9e99a6587529fb01587529fbd30000");
		String resultJson = houseSurveyServiceProxy.findSurveyPicListByType(JsonEntityTransform.Object2Json(picDto));
		System.err.println(resultJson);
	}
	
	@Test
	public void findPicCountByTypeTest() {
		SurveyPicDto surveyPicDto = new SurveyPicDto();
		surveyPicDto.setSurveyFid("8a9e99a6587529fb01587529fbd30000");
		surveyPicDto.setPicType(SurveyPicTypeEnum.DEFAULT.getCode());
		String resultJson = houseSurveyServiceProxy.findPicCountByType(JsonEntityTransform.Object2Json(surveyPicDto));
		System.err.println(resultJson);
	}
	
	@Test
	public void findSurveyHouseListByPageTest() {
		SurveyRequestDto requestDto = new SurveyRequestDto();
		requestDto.setSurveyResult(SurveyResultEnum.UNSURVEYED.getCode());
		requestDto.setRoleType(RoleTypeEnum.ADMIN.getCode());
		String resultJson = houseSurveyServiceProxy.findSurveyHouseListByPage(JsonEntityTransform.Object2Json(requestDto));
		System.err.println(resultJson);
	}
	
	@Test
	public void saveSurveyPicMsgListTest() {
		HouseSurveyPicMsgEntity entity = new HouseSurveyPicMsgEntity();
		entity.setSurveyFid("8a9e99a6587529fb01587529fbd30000");
		entity.setPicName("IMG_0589x_1.JPG");
		entity.setPicType(0);
		entity.setPicBaseUrl("group1/M00/00/40/ChAiMFc-vyuAb_0UAAYDU0IXvg0891");
		entity.setPicSuffix(".JPG");
		entity.setPicServerUuid("df1396cc-d053-4cf9-99ef-74bd0cc49f4c");
		entity.setCreateFid(UUIDGenerator.hexUUID());
		
		List<HouseSurveyPicMsgEntity> list = new ArrayList<>();
		list.add(entity);
		String resultJson = houseSurveyServiceProxy.saveSurveyPicMsgList(JsonEntityTransform.Object2Json(list));
		System.err.println(resultJson);
	}
	
	@Test
	public void updateHouseSurveyPicMsgTest() {
		HouseSurveyPicMsgEntity entity = new HouseSurveyPicMsgEntity();
		entity.setFid("8a9e99a658762ea40158762ea7150001");
		entity.setPicName("IMG_0589x_2.JPG");
		String resultJson = houseSurveyServiceProxy.updateHouseSurveyPicMsg(JsonEntityTransform.Object2Json(entity));
		System.err.println(resultJson);
	}
	
	@Test
	public void updateHouseSurveyMsgTest() {
		HouseSurveyMsgEntity houseSurveyMsgEntity = new HouseSurveyMsgEntity();
		houseSurveyMsgEntity.setFid("8a9e99a6587529fb01587529fbd30000");
		houseSurveyMsgEntity.setIsAudit(YesOrNoEnum.YES.getCode());
		houseSurveyMsgEntity.setOperateFid(UUIDGenerator.hexUUID());
		houseSurveyMsgEntity.setOperateType(SurveyOperateTypeEnum.AUDIT.getCode());
		houseSurveyMsgEntity.setRemark("测试审阅");
		String resultJson = houseSurveyServiceProxy.updateHouseSurveyMsg(JsonEntityTransform.Object2Json(houseSurveyMsgEntity));
		System.err.println(resultJson);
	}
}
