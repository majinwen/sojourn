package com.ziroom.minsu.services.house.test.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.HouseSurveyPicMsgEntity;
import com.ziroom.minsu.services.house.dao.HouseSurveyPicMsgDao;
import com.ziroom.minsu.services.house.survey.dto.SurveyPicDto;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import com.ziroom.minsu.valenum.house.SurveyPicTypeEnum;

/**
 * 
 * <p>房源维护管家日志dao测试类</p>
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
public class HouseSurveyPicMsgDaoTest extends BaseTest{
	
	@Resource(name = "house.houseSurveyPicMsgDao")
    private HouseSurveyPicMsgDao houseSurveyPicMsgDao;
	
	@Test
	public void insertHouseSurveyPicMsgTest(){
		HouseSurveyPicMsgEntity entity = new HouseSurveyPicMsgEntity();
		entity.setSurveyFid("8a9e99a6587529fb01587529fbd30000");
		entity.setPicName("IMG_0589x_1.JPG");
		entity.setPicType(0);
		entity.setPicBaseUrl("group1/M00/00/40/ChAiMFc-vyuAb_0UAAYDU0IXvg0891");
		entity.setPicSuffix(".JPG");
		entity.setPicServerUuid("df1396cc-d053-4cf9-99ef-74bd0cc49f4c");
		entity.setCreateFid(UUIDGenerator.hexUUID());
		int upNum = houseSurveyPicMsgDao.insertHouseSurveyPicMsg(entity);
		System.err.println(upNum);
	}
	
	@Test
	public void findHouseSurveyPicMsgByFidTest(){
		String surveyPicFid = "8a9e99a6587538540158753854480001";
		HouseSurveyPicMsgEntity entity = houseSurveyPicMsgDao.findHouseSurveyPicMsgByFid(surveyPicFid );
		System.err.println(entity);
	}
	
	@Test
	public void findSurveyPicListByTypeTest(){
		SurveyPicDto picDto = new SurveyPicDto();
		picDto.setSurveyFid("8a9e99a6587529fb01587529fbd30000");
		List<HouseSurveyPicMsgEntity> list = houseSurveyPicMsgDao.findSurveyPicListByType(picDto);
		System.err.println(list);
	}
	
	@Test
	public void findPicCountByTypeTest(){
		SurveyPicDto surveyPicDto = new SurveyPicDto();
		surveyPicDto.setSurveyFid("8a9e99a6587529fb01587529fbd30000");
		surveyPicDto.setPicType(SurveyPicTypeEnum.DEFAULT.getCode());
		long num = houseSurveyPicMsgDao.findPicCountByType(surveyPicDto);
		System.err.println(num);
	}
}
