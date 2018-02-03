package com.ziroom.minsu.services.house.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.house.HouseSurveyPicMsgEntity;
import com.ziroom.minsu.services.house.survey.dto.SurveyPicDto;

/**
 * 
 * <p>房源实勘图片信息dao</p>
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
@Repository("house.houseSurveyPicMsgDao")
public class HouseSurveyPicMsgDao {


    private String SQLID="house.houseSurveyPicMsgDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 
     * 新增房源实勘图片信息
     *
     * @author liujun
     * @created 2016年11月16日
     *
     * @param houseBaseMsg
     * @return
     */
    public int insertHouseSurveyPicMsg(HouseSurveyPicMsgEntity houseSurveyPicMsgEntity) {
    	if (Check.NuNObj(houseSurveyPicMsgEntity.getFid())) {
    		houseSurveyPicMsgEntity.setFid(UUIDGenerator.hexUUID());
		}
		return mybatisDaoContext.save(SQLID+"insertHouseSurveyPicMsg", houseSurveyPicMsgEntity);
	}
    
    /**
     * 
     * 更新房源实勘图片信息
     *
     * @author liujun
     * @created 2016年11月16日
     *
     * @param houseSurveyPicMsgEntity
     * @return
     */
    public int updateHouseSurveyPicMsg(HouseSurveyPicMsgEntity houseSurveyPicMsgEntity) {
    	return mybatisDaoContext.update(SQLID+"updateHouseSurveyPicMsg", houseSurveyPicMsgEntity);
    }
	
	/**
	 * 
	 * 根据fid查询房源实勘图片信息
	 *
	 * @author liujun
	 * @created 2016年11月16日
	 *
	 * @param surveyPicFid
	 * @return
	 */
	public HouseSurveyPicMsgEntity findHouseSurveyPicMsgByFid(String surveyPicFid){
		return mybatisDaoContext.findOneSlave(SQLID+"findHouseSurveyPicMsgByFid", HouseSurveyPicMsgEntity.class, surveyPicFid);
	}

	/**
	 * 根据图片类型查询图片数量
	 *
	 * @author liujun
	 * @created 2016年11月17日
	 *
	 * @param surveyPicDto
	 * @return
	 */
	public long findPicCountByType(SurveyPicDto surveyPicDto) {
		return mybatisDaoContext.countBySlave(SQLID+"findPicCountByType", surveyPicDto.toMap());
	}

	/**
	 * 根据房源实勘fid与图片类型查询房源实勘图片列表
	 *
	 * @author liujun
	 * @created 2016年11月21日
	 *
	 * @param picDto
	 * @return
	 */
	public List<HouseSurveyPicMsgEntity> findSurveyPicListByType(SurveyPicDto picDto) {
		return mybatisDaoContext.findAll(SQLID+"findSurveyPicListByType", HouseSurveyPicMsgEntity.class, picDto);
	}
}
