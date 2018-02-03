package com.ziroom.minsu.services.house.dao;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.house.HouseSurveyMsgEntity;
import com.ziroom.minsu.services.house.survey.dto.SurveyRequestDto;
import com.ziroom.minsu.services.house.survey.entity.HouseSurveyVo;

/**
 * 
 * <p>房源实勘信息dao</p>
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
@Repository("house.houseSurveyMsgDao")
public class HouseSurveyMsgDao {


    private String SQLID="house.houseSurveyMsgDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 
     * 新增房源实勘信息
     *
     * @author liujun
     * @created 2016年11月16日
     *
     * @param houseBaseMsg
     * @return
     */
    public int insertHouseSurveyMsg(HouseSurveyMsgEntity houseSurveyMsgEntity) {
    	if (Check.NuNObj(houseSurveyMsgEntity.getFid())) {
    		houseSurveyMsgEntity.setFid(UUIDGenerator.hexUUID());
		}
		return mybatisDaoContext.save(SQLID+"insertHouseSurveyMsg", houseSurveyMsgEntity);
	}
    
    /**
     * 
     * 更新房源实勘信息
     *
     * @author liujun
     * @created 2016年11月16日
     *
     * @param houseSurveyMsgEntity
     * @return
     */
    public int updateHouseSurveyMsg(HouseSurveyMsgEntity houseSurveyMsgEntity) {
    	return mybatisDaoContext.update(SQLID+"updateHouseSurveyMsg", houseSurveyMsgEntity);
    }
	
	/**
	 * 
	 * 根据房源fid查询房源实勘信息
	 *
	 * @author liujun
	 * @created 2016年11月16日
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public HouseSurveyMsgEntity findHouseSurveyMsgByHouseFid(String houseBaseFid){
		return mybatisDaoContext.findOneSlave(SQLID+"findHouseSurveyMsgByHouseFid", HouseSurveyMsgEntity.class, houseBaseFid);
	}

	/**
	 * 根据房源fid查询需要实勘房源信息
	 *
	 * @author liujun
	 * @created 2016年11月16日
	 *
	 * @param requestDto
	 * @return
	 */
	public PagingResult<HouseSurveyVo> findSurveyHouseListByPage(
			SurveyRequestDto requestDto) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(requestDto.getPage());
		pageBounds.setLimit(requestDto.getLimit());
		Map<?, ?> paramMap = JsonEntityTransform.json2Map(JsonEntityTransform.Object2Json(requestDto));
		
		if(!Check.NuNObj(requestDto)
				&&requestDto.getRoleType()>0){
			return mybatisDaoContext.findForPage(SQLID+"findAuthSurveyHouseListByPage", HouseSurveyVo.class, paramMap, pageBounds);
		}
		return mybatisDaoContext.findForPage(SQLID+"findSurveyHouseListByPage", HouseSurveyVo.class, paramMap, pageBounds);
	}

	/**
	 * 根据房源实勘fid查询房源实勘信息
	 *
	 * @author liujun
	 * @created 2016年11月16日
	 *
	 * @param fid
	 * @return
	 */
	public HouseSurveyMsgEntity findHouseSurveyMsgByFid(String fid) {
		return mybatisDaoContext.findOneSlave(SQLID+"findHouseSurveyMsgByFid", HouseSurveyMsgEntity.class, fid);
	}
}
