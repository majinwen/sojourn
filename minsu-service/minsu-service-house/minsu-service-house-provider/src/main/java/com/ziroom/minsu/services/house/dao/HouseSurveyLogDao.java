package com.ziroom.minsu.services.house.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.house.HouseSurveyLogEntity;

/**
 * 
 * <p>房源实勘日志信息dao</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改时间	修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
@Repository("house.houseSurveyLogDao")
public class HouseSurveyLogDao {


    private String SQLID="house.houseSurveyLogDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 
     * 新增房源实勘日志信息
     *
     * @author liujun
     * @created 2016年11月16日
     *
     * @param houseBaseMsg
     * @return
     */
    public int insertHouseSurveyLog(HouseSurveyLogEntity houseSurveyLogEntity) {
    	if (Check.NuNObj(houseSurveyLogEntity.getFid())) {
    		houseSurveyLogEntity.setFid(UUIDGenerator.hexUUID());
		}
		return mybatisDaoContext.save(SQLID+"insertHouseSurveyLog", houseSurveyLogEntity);
	}
}
