package com.zra.business.dao;

import java.util.Date;
import java.util.List;

import com.zra.business.entity.BusinessResultEntity;
import com.zra.business.entity.dto.BusinessEvaluateSMSDto;
import com.zra.business.entity.vo.BusinessResultVo;
import com.zra.common.dto.business.BusinessResultDto;

/**
 * 商机处理结果dao
 * @author wangws21 2016-8-3
 */
public interface BusinessResultMapper {
	
	
    /**
     * 保存商机处理结果  wangws21 2016-8-3
     * @param business 商机处理结果
     * @return effectNum
     */
    int insert(BusinessResultEntity businessResult);

    /**
	 * wangws21 2016-8-8
	 * 获取商机处理历史结果
	 * @param businessBid 商机bid
	 * @return List<BusinessResultDto>
	 */
	List<BusinessResultDto> getBusinessResultList(String businessBid);

	
	/**
	 * 获取最新的商机处理结果
	 * wangws21 2016-8-11 
	 * @param businessBid
	 * @return
	 */
	BusinessResultEntity getLastBusinessResult(String businessBid);

	/**
	 * 查询发送约看提醒信息列表
	 *
	 * @author liujun
	 * @created 2016年8月15日
	 *
	 * @param deadline
	 * @return
	 */
	List<BusinessResultVo> getYkRemindSmsList(Date deadline);

	/**
	 * 根据业务id更新商机处理结果信息
	 *
	 * @author liujun
	 * @created 2016年8月15日
	 *
	 * @param businessResultEntity
	 * @return
	 */
	int updateBusinessResultEntity(BusinessResultEntity businessResultEntity);

	/**
	 * 查询发送约看提醒信息列表
	 * @author wangws21
	 * @created 2016年8月23日
	 * @param endDate 截止时间
	 * @return List<BusinessEvaluateSMSDto>
	 */
	List<BusinessEvaluateSMSDto> getBusinessEvaluateSmsList(Date endDate);
}