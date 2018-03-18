package com.zra.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zra.business.dao.BusinessReportMapper;
import com.zra.common.dto.business.AnswerRateQueryParamDto;
import com.zra.common.dto.business.BoReportCountDto;
import com.zra.common.dto.business.BoReportQueryParamDto;

/**
 * @author tianxf9
 * 商机报表服务
 */
@Service
public class BusinessReportService {

    @Autowired
    private BusinessReportMapper businessReportMapper;
	/**
	 * 查询约看量
	 * @author tianxf9
	 * @param paramDto
	 * @return
	 */
	public List<BoReportCountDto> getYueKanCountList(BoReportQueryParamDto paramDto) {
		return this.businessReportMapper.getYueKanCountList(paramDto);
	}
	
	/**
	 * 查询约看没有及时跟进的用于计算约看跟进率
	 * 查询选择时间段内约看商机中商机处理状态曾经或仍为“超时未处理”的商机数量＋查询开始时间之前商机阶段为待约看的商机数量）;
	 * @author tianxf9
	 * @param paramDto
	 * @return
	 */
	public List<BoReportCountDto> getTimeOutAndStayYueKan(BoReportQueryParamDto paramDto) {
		return this.businessReportMapper.getTimeOutAndStayYueKan(paramDto);
	}
	
	/**
	 * 查询带看量
	 * @author tianxf9
	 * @param paramDto
	 * @return
	 */
	public List<BoReportCountDto> getDaiKanCountList(BoReportQueryParamDto paramDto) {
		return this.businessReportMapper.getDaiKanCountList(paramDto);
	}
	
	/**
	 * 查询回访量
	 * @author tianxf9
	 * @param paramDto
	 * @return
	 */
	public List<BoReportCountDto> getVisitCount(BoReportQueryParamDto paramDto) {
		return this.businessReportMapper.getVisitCount(paramDto);
	}
	
	/**
	 * 查询经历过待回访的商机个数（用于回访率：回访量／商机阶段经历过待回访的商机数目）
	 * @author tianxf9
	 * @param paramDto
	 * @return
	 */
	public List<BoReportCountDto> getThroughVisitCount(BoReportQueryParamDto paramDto) {
		return this.businessReportMapper.getThroughVisitCount(paramDto);
	}
	
	/**
	 * 查询成交量
	 * @param paramDto
	 * @return
	 */
	public List<BoReportCountDto> getDealCount(BoReportQueryParamDto paramDto) {
		return this.businessReportMapper.getDealCount(paramDto);
	}
	
	/**
	 * 商机阶段直接变更为未成交和由待约看直接变更为未成交的商机数量之和
	 * @author tianxf9
	 * @param paramDto
	 * @return
	 */
	public List<BoReportCountDto> getFirstUnDealCount(BoReportQueryParamDto paramDto) {
		return this.businessReportMapper.getFirstUnDealCount(paramDto);
	}
	
	/**
	 * 查询未成交商机
	 * @author tianxf9
	 * @param paramDto
	 * @return
	 */
	public List<BoReportCountDto> getUnDealCount(BoReportQueryParamDto paramDto) {
		return this.businessReportMapper.getUnDealCount(paramDto);
	}
	
	/**
	 * 查询新增约看量
	 * @author tianxf9
	 * @param paramDto
	 * @return
	 */
	public List<BoReportCountDto> getNewYueKanCount(BoReportQueryParamDto paramDto) {
		return this.businessReportMapper.getNewYueKanCount(paramDto);
	}
	
	/**
	 * 查询遗留约看量
	 * @author tianxf9
	 * @param paramDto
	 * @return
	 */
	public List<BoReportCountDto> getOldYueKanCount(BoReportQueryParamDto paramDto) {
		return this.businessReportMapper.getOldYueKanCount(paramDto);
	}

	/**
     * 查询客源量
     * @author wangws21 2017-1-18
     * @param paramDto
     * @return
     */
    public List<BoReportCountDto> getKylCount(BoReportQueryParamDto paramDto) {
        return this.businessReportMapper.getKylCount(paramDto);
    }
	
	/**
	 * 用于目标看板
	 * 查询选择时间段内约看商机中商机处理状态曾经或仍为“超时未处理”的商机数量;
	 * @author tianxf9
	 * @param paramDto
	 * @return
	 */
	public List<BoReportCountDto> getTimeOutYueKan(BoReportQueryParamDto paramDto) {
		return this.businessReportMapper.getTimeOutYueKan(paramDto);
	}
	
	/**
	 * 
	 * 获取项目的来电量
	 * @author tianxf9
	 * @param paramDto
	 * @return
	 */
	public Integer getProjectCallCountService(AnswerRateQueryParamDto paramDto) {
		return this.businessReportMapper.getProjectCallCount(paramDto);
	}
	
	/**
	 * 获取管家来电量
	 * @author tianxf9
	 * @param paramDto
	 * @return
	 */
	public List<BoReportCountDto> getZOCallCountService(AnswerRateQueryParamDto paramDto) {
		return this.businessReportMapper.getZOCallCount(paramDto);
	}
	
}
