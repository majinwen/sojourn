package com.zra.kanban.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zra.common.dto.kanban.KanbanQueryDto;
import com.zra.kanban.entity.KanbanSummary;
import com.zra.kanban.entity.dto.KanBanCountDto;
/**
 * 目标看板核心数据DAO
 * @author tianxf9
 *
 */
@Repository
public interface KanbanSummaryMapper {
	
    /**
     * 保存核心数据
     * @author tianxf9
     * @param record
     * @return
     */
    int insert(List<KanbanSummary> record);

    /**
     * 根据条件查询核心数据
     * @author tianxf9
     * @param queryDto
     * @return
     */
    List<KanbanSummary> selectByConditions(KanbanQueryDto queryDto);

    /**
     * 更新数据
     * @author tianxf9
     * @param record
     * @return
     */
    int updateById(KanbanSummary record);
    
    /**
     * 获取出租周期分母部分
     * @author tianxf9
     * @param startDate
     * @param endDate
     * @return
     */
    List<KanBanCountDto> getSumEmptyNum(@Param("startDate")String startDate,@Param("endDate")String endDate);
    
    /**
     * 获取出租周期分子部分
     * @author tianxf9
     * @param startDate
     * @param endDate
     * @return
     */
    List<KanBanCountDto> getSumRentDetailNum(@Param("startDate")String startDate,@Param("endDate")String endDate);
    
    /**
     * 根据条件更新数据
     * @author tianxf9
     * @param startDate
     * @param type
     * @return
     */
    int updateByConditions(@Param("startDate")String startDate,@Param("type")int type);
}