package com.zra.kanban.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zra.common.dto.kanban.KanbanQueryDto;
import com.zra.kanban.dao.KanbanSummaryMapper;
import com.zra.kanban.entity.KanbanSummary;
import com.zra.kanban.entity.dto.KanBanCountDto;
import com.zra.kanban.entity.dto.KanbanRateDto;

/**
 * 目标看板核心数据service
 * @author tianxf9
 *
 */
@Service
public class KanbanSummaryService {
	
	@Autowired
	private KanbanSummaryMapper summaryMapper;
	
	/**
	 * 保存实体
	 * @author tianxf9
	 * @param summaryEntitys
	 * @return
	 */
	public int saveEntitys(List<KanbanSummary> summaryEntitys) {
		return this.summaryMapper.insert(summaryEntitys);
	}
	
	/**
	 * 更新实体
	 * @author tianxf9
	 * @param summaryEntity
	 * @return
	 */
	public int updateEntity(KanbanSummary summaryEntity) {
		return this.summaryMapper.updateById(summaryEntity);
	}
	
	/**
	 * 查询
	 * @author tianxf9
	 * @param queryDto
	 * @return
	 */
	public List<KanbanSummary> getEntitysByConditions(KanbanQueryDto queryDto) {
		return this.summaryMapper.selectByConditions(queryDto);
	}
	
	/**
	 * 查询出租周期
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<KanbanRateDto> getLeaseCycleService(String startDate,String endDate) {
		
		List<KanBanCountDto> rentDetailCount = this.summaryMapper.getSumRentDetailNum(startDate, endDate);
		List<KanBanCountDto> emptyNumCount = this.summaryMapper.getSumEmptyNum(startDate, endDate);
		
		Map<String,Integer> emptyNumCountMap = new HashMap<String,Integer>();
		for(KanBanCountDto countDto:emptyNumCount) {
			emptyNumCountMap.put(countDto.getProjectId(),countDto.getCount());
		}
		
		
		List<KanbanRateDto> results = new ArrayList<KanbanRateDto>();
		for(KanBanCountDto detailCount:rentDetailCount) {
			KanbanRateDto result = new KanbanRateDto();
			result.setProjectId(detailCount.getProjectId());
			if(emptyNumCountMap.get(detailCount.getProjectId())!=null&&detailCount.getCount()!=0) {
				BigDecimal rate = new BigDecimal(emptyNumCountMap.get(detailCount.getProjectId())).divide(new BigDecimal(detailCount.getCount()), 2, BigDecimal.ROUND_HALF_UP);
				result.setRate(rate);
			}else {
				result.setRate(new BigDecimal(0));
			}
			
			results.add(result);
		}
		
		return results;
	}
	
	
	/**
	 * 根据条件删除实体
	 * @author tianxf9
	 * @param startDateStr
	 * @param type
	 * @return
	 */
	public int updateEntitysByConditions(String startDateStr,int type) {
		return this.summaryMapper.updateByConditions(startDateStr,type);
	}

}
