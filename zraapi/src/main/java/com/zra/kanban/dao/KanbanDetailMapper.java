package com.zra.kanban.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zra.common.dto.kanban.KanbanQueryDto;
import com.zra.common.dto.kanban.SecondDataDetailDto;
import com.zra.common.dto.kanban.SecondDataShowDto;
import com.zra.kanban.entity.KanbanDetail;
import com.zra.kanban.entity.dto.KanBanCountDto;
import com.zra.kanban.entity.dto.PaymentInfoDto;
/**
 * 目标看板二级数据详情DAO
 * @author tianxf9
 *
 */
@Repository
public interface KanbanDetailMapper {

    /**
     * 保存二级数据核心数据
     * @author tianxf9
     * @param record
     * @return
     */
    int insert(List<KanbanDetail> records);

    /**
     * 根据条件查询二级数据详细数据
     * @author tianxf9
     * @param queryDto
     * @return
     */
    List<KanbanDetail> selectByConditions(KanbanQueryDto queryDto);

    /**
     * 更新数据
     * @author tianxf9
     * @param record
     * @return
     */
    int updateByBid(KanbanDetail record);
   
    /**
     * 查询约看平均处理时长
     * @author tianxf9
     * @param startDate
     * @param endDate
     * @param isGroupByZo
     * @return
     */
    List<KanBanCountDto> getAvgHandTime(@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("isGroupByZo")String isGroupByZo);
    
    /**
     * 获取退租量
     * @author tianxf9
     * @param startDate
     * @param endDate
     * @param isGroupByZo
     * @return
     */
    List<KanBanCountDto> getQuitCount(@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("isGroupByZo")String isGroupByZo);
    
    /**
     * 获取新签量
     * @author tianxf9
     * @param startDate
     * @param endDate
     * @return
     */
    List<KanBanCountDto> getNewSignCount(@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("isGroupByZo")String isGroupByZo);
    
    /**
     * 获取续约量
     * @author tianxf9
     * @param startDate
     * @param endDate
     * @return
     */
    List<KanBanCountDto> getRenewCount(@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("isGroupByZo")String isGroupByZo);
    
   
    /**
     * 获取空置房源
     * @author tianxf9
     * @param recordDate
     * @param empNumMin
     * @param empNumMax
     * @return
     */
    List<KanBanCountDto> getEmptyCount(@Param("recordDate")String recordDate,@Param("empNumMin")int empNumMin,@Param("empNumMax")int empNumMax);
    
    /**
     * 获取应收账单相关信息
     * @author tianxf9
     * @param startDate
     * @param endDate
     * @return
     */
    List<PaymentInfoDto> getPaymentAvgDays(@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("isGroupByZo")String isGroupByZo);
    
    /**
     * 更新目标看吧二级数据
     * @author tianxf9
     * @param startDate
     * @param type
     * @return
     */
    int updateByConditions(@Param("startDate")String startDate,@Param("type")int type);
    
    /**
     * 查询二级数据
     * @author tianxf9
     * @param queryDto
     * @return
     */
    List<SecondDataShowDto> getSecondData(KanbanQueryDto queryDto);
    
    /**
     * 查询二级数据详细信息
     * @author tianxf9
     * @param queryDto
     * @return
     */
    List<SecondDataDetailDto> getSecondDataDetail(KanbanQueryDto queryDto);
}