package com.zra.business.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zra.common.dto.business.AnswerRateQueryParamDto;
import com.zra.common.dto.business.BoReportCountDto;
import com.zra.common.dto.business.BoReportQueryParamDto;

/**
 * 商机报表dao
 * @author tianxf9
 *
 */
@Repository
public interface BusinessReportMapper {
	
	/**
	 * 约看量
	 * @author tianxf9
	 * @param paramDto
	 * @return
	 */
	List<BoReportCountDto> getYueKanCountList(BoReportQueryParamDto paramDto);
	
	/**
	 * 
	 * 查询选择时间段内约看商机中商机处理状态曾经或仍为“超时未处理”的商机数量＋查询开始时间之前商机阶段为待约看的商机数量）;用于计算约看及时跟进率
	 * @author tianxf9
	 * @param paramDto
	 * @return
	 */
	List<BoReportCountDto> getTimeOutAndStayYueKan(BoReportQueryParamDto paramDto);
	
	/**
	 * 带看量
	 * @author tianxf9
	 * @param paramDto
	 * @return
	 */
	List<BoReportCountDto> getDaiKanCountList(BoReportQueryParamDto paramDto);
	
	/**
	 * 查询回访量
	 * @author tianxf9
	 * @param paramDto
	 * @return
	 */
	List<BoReportCountDto> getVisitCount(BoReportQueryParamDto paramDto);
	
	/**
	 * 查询经历过待回访的商机个数（用于回访率：回访量／商机阶段经历过待回访的商机数目）
	 * @author tianxf9
	 * @param paramDto
	 * @return
	 */
	List<BoReportCountDto> getThroughVisitCount(BoReportQueryParamDto paramDto);
	
	/**
	 * 查询成交量
	 * @author tianxf9
	 * @return 
	 */
	List<BoReportCountDto> getDealCount(BoReportQueryParamDto paramDto);
	
	/**
	 * 商机阶段直接变更为未成交和由待约看直接变更为未成交的商机数量之和
	 * @author tianxf9
	 * @param paramDto
	 * @return
	 */
	List<BoReportCountDto> getFirstUnDealCount(BoReportQueryParamDto paramDto);
	
	/**
	 * 查询未成交商家
	 * @author tianxf9
	 * @param paramDto
	 * @return
	 */
	List<BoReportCountDto> getUnDealCount(BoReportQueryParamDto paramDto);
	
	/**
	 * 查询新增约看量
	 * @author tianxf9
	 * @param paramDto
	 * @return
	 */
	List<BoReportCountDto> getNewYueKanCount(BoReportQueryParamDto paramDto);
	
	/**
	 * 查询历史约看量
	 * @author tianxf9
	 * @param paramDto
	 * @return
	 */
	List<BoReportCountDto> getOldYueKanCount(BoReportQueryParamDto paramDto);

	/**
     * 查询客源量
     * @author wangws21 2017-1-18
     * @param paramDto
     * @return
     */
    List<BoReportCountDto> getKylCount(BoReportQueryParamDto paramDto);
	
	/**
	 * 
	 * 查询选择时间段内约看商机中商机处理状态曾经或仍为“超时未处理”的商机数量）;用于目标看板的商机及时跟进率；
	 * @author tianxf9
	 * @param paramDto
	 * @return
	 */
	List<BoReportCountDto> getTimeOutYueKan(BoReportQueryParamDto paramDto);
	
	/**
	 * 根据项目分机号查询项目的400来电数量或者接听数量
	 * @author tianxf9
	 * @param numbers
	 * @param startDate
	 * @param endDate
	 * @param isSuccess
	 * @return
	 */
	Integer getProjectCallCount(AnswerRateQueryParamDto paramDto);
	
	/**
	 * 根据管家手机号获取管家的接听数量或者来电数量
	 * @author tianxf9
	 * @param numbers
	 * @param startDate
	 * @param endDate
	 * @param isSuccess
	 * @return
	 */
	List<BoReportCountDto> getZOCallCount(AnswerRateQueryParamDto paramDto);
}