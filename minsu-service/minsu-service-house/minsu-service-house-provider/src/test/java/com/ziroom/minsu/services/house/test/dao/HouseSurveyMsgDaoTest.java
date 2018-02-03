package com.ziroom.minsu.services.house.test.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.house.HouseSurveyMsgEntity;
import com.ziroom.minsu.services.house.dao.HouseSurveyMsgDao;
import com.ziroom.minsu.services.house.survey.dto.SurveyRequestDto;
import com.ziroom.minsu.services.house.survey.entity.HouseSurveyVo;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import com.ziroom.minsu.valenum.base.RoleTypeEnum;
import com.ziroom.minsu.valenum.house.SurveyResultEnum;

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
public class HouseSurveyMsgDaoTest extends BaseTest{
	
	@Resource(name = "house.houseSurveyMsgDao")
    private HouseSurveyMsgDao houseSurveyMsgDao;
	
	@Test
	public void insertHouseSurveyMsgTest(){
		HouseSurveyMsgEntity entity = new HouseSurveyMsgEntity();
		entity.setHouseBaseFid("8a9e9aae5419cc22015419cc250a0002");
		entity.setSurveySn("110100000003SK");
		int upNum = houseSurveyMsgDao.insertHouseSurveyMsg(entity);
		System.err.println(upNum);
	}
	
	@Test
	public void findHouseSurveyMsgByFidTest(){
		String fid = "8a9e99a6587529fb01587529fbd30000";
		HouseSurveyMsgEntity entity = houseSurveyMsgDao.findHouseSurveyMsgByFid(fid);
		System.err.println(entity);
	}
	
	@Test
	public void findHouseSurveyMsgByHouseFidTest(){
		String houseBaseFid = "8a9e9aae5419cc22015419cc250a0002";
		HouseSurveyMsgEntity entity = houseSurveyMsgDao.findHouseSurveyMsgByHouseFid(houseBaseFid );
		System.err.println(entity);
	}
	
	@Test
	public void findSurveyHouseListByPageTest(){
		SurveyRequestDto requestDto = new SurveyRequestDto();
		requestDto.setSurveyResult(SurveyResultEnum.UNSURVEYED.getCode());
		requestDto.setRoleType(RoleTypeEnum.ADMIN.getCode());
		PagingResult<HouseSurveyVo> pagingResult = houseSurveyMsgDao.findSurveyHouseListByPage(requestDto );
		System.err.println(JsonEntityTransform.Object2Json(pagingResult));
	}
}
