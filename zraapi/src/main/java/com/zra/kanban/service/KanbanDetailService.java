package com.zra.kanban.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zra.common.dto.kanban.KanbanQueryDto;
import com.zra.common.dto.kanban.SecondDataDetailDto;
import com.zra.common.dto.kanban.SecondDataShowDto;
import com.zra.kanban.dao.KanbanDetailMapper;
import com.zra.kanban.entity.KanbanDetail;
import com.zra.kanban.entity.dto.KanBanCountDto;
import com.zra.kanban.entity.dto.PaymentInfoDto;

/**
 * 目标看板二级数据数据详情
 * @author tianxf9
 *
 */
@Service
public class KanbanDetailService {
	
	@Autowired
	private KanbanDetailMapper detailMapper;
	
	
	/**
	 * 保存数据详情
	 * @author tianxf9
	 * @param details
	 * @return
	 */
	public int saveEntitys(List<KanbanDetail> details) {
		return this.detailMapper.insert(details);
	}
	
	/**
	 * 更新数据详情
	 * @author tianxf9
	 * @param detail
	 * @return
	 */
	public int updateEntity(KanbanDetail detail) {
		return this.detailMapper.updateByBid(detail);
	}
	
	/**
	 * 根据条件查询实体
	 * @author tianxf9
	 * @param queryDto
	 * @return
	 */
	public List<KanbanDetail> getEntitysByConditions(KanbanQueryDto queryDto) {
		return this.detailMapper.selectByConditions(queryDto);
	}
	
	/**
	 * 查询约看平均处理时长
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param isGroupByZo 是否按照管家分组;Y:是，N:否;
	 * @return
	 */
	public List<KanBanCountDto> getAvgHandTime(String startDate,String endDate,String isGroupByZo) {
		return this.detailMapper.getAvgHandTime(startDate, endDate, isGroupByZo);
	}
	
    /**
     * 获取退租量
     * @author tianxf9
     * @param startDate
     * @param endDate
     * @return
     */
	public List<KanBanCountDto> getQuitCountService(String startDate,String endDate,String isGroupByZo) {
		return this.detailMapper.getQuitCount(startDate, endDate,isGroupByZo);
	}
	
	/**
	 * 获取新签量
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<KanBanCountDto> getNewSignCountService(String startDate,String endDate,String isGroupByZo) {
		return this.detailMapper.getNewSignCount(startDate, endDate,isGroupByZo);
	}
	
	/**
	 * 获取续约量
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<KanBanCountDto> getRenewCountService(String startDate,String endDate,String isGroupByZo) {
		return this.detailMapper.getRenewCount(startDate, endDate,isGroupByZo);
	}
	
    /**
     * 获取空置房源数量
     * @author tianxf9
     * @param endDate
     * @param empNumMin
     * @param empNumMax
     * @return
     */
	public List<KanBanCountDto> getEmptyCountService(String endDate,int empNumMin,int empNumMax) {
		return this.detailMapper.getEmptyCount(endDate, empNumMin, empNumMax);
	}
	
	/**
	 * 获取应收账单相关信息
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<PaymentInfoDto> getPaymentAvgDaysService(String startDate,String endDate,String isGroupByZo) {
		return this.detailMapper.getPaymentAvgDays(startDate, endDate,isGroupByZo);
	}
	
	
	/**
	 * 删除实体根据条件
	 * @author tianxf9
	 * @param startDate
	 * @param type
	 * @return
	 */
	public int updateByConditions(String startDate,int type) {
		return this.detailMapper.updateByConditions(startDate, type);
	}
	
	/**
	 * 得到二级数据
	 * @author tianxf9
	 * @param queryDto
	 * @return
	 */
	public List<SecondDataShowDto> getSecondDataService(KanbanQueryDto queryDto) {
		return this.detailMapper.getSecondData(queryDto);
	}
	
	/**
	 * 得到二级数据详细信息
	 * @author tianxf9
	 * @param queryDto
	 * @return
	 */
	public List<SecondDataDetailDto> getSecondDataDetailService(KanbanQueryDto queryDto) {
		return this.detailMapper.getSecondDataDetail(queryDto);
	}
	

}
