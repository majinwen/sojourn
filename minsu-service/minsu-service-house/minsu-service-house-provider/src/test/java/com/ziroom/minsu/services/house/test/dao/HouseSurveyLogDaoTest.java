package com.ziroom.minsu.services.house.test.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.HouseSurveyLogEntity;
import com.ziroom.minsu.services.house.dao.HouseSurveyLogDao;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import com.ziroom.minsu.valenum.house.SurveyOperateTypeEnum;

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
public class HouseSurveyLogDaoTest extends BaseTest{
	
	@Resource(name = "house.houseSurveyLogDao")
    private HouseSurveyLogDao houseSurveyLogDao;
	
	@Test
	public void insertHouseSurveyLogTest(){
		HouseSurveyLogEntity entity = new HouseSurveyLogEntity();
		entity.setSurveyFid("8a9e99a6587529fb01587529fbd30000");
		entity.setOperateFid(UUIDGenerator.hexUUID());
		entity.setOperateType(SurveyOperateTypeEnum.AUDIT.getCode());
		entity.setCreateDate(new Date());
		int upNum = houseSurveyLogDao.insertHouseSurveyLog(entity);
		System.err.println(upNum);
	}
}
