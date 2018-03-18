package com.zra.marketing.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zra.common.dto.marketing.MkChannelDto;
import com.zra.common.dto.marketing.MkLineChannelShowDto;
import com.zra.common.dto.marketing.SchedulePersonDto;
import com.zra.marketing.entity.MkChannelEntity;
import com.zra.marketing.entity.dto.MkChannelCountDto;
@Repository
public interface MkChannelMapper {
 
	
	/**
	 * 删除渠道
	 * @author tianxf9
	 * @param channelBid
	 * @param userId
	 * @return
	 */
    int deleteMkChannelByBid(@Param("channelBid")String channelBid,@Param("userId")String userId);

    /**
     * 批量保存渠道
     * @author tianxf9
     * @param record
     * @return
     */
    int insertEntitys(List<MkChannelEntity> records);
    
    /**
     * 保存渠道
     * @author tianxf9
     * @param record
     * @return
     */
    int insertEntity(MkChannelEntity record);


    /**
     * 根据城市编号查询线下渠道
     * @author tianxf9
     * @param cityId
     * @return
     */
    List<MkLineChannelShowDto> selectLineChannelByCityId(String cityId);
    
    
    /**
     * 根据城市编号查询线上渠道
     * @author tianxf9
     * @param cityId
     * @return
     */
    List<MkChannelDto> getOnLineChannelByCityId(String cityId);

    /**
     * 更新渠道内容
     * @author tianxf9
     * @param record
     * @return
     */
    int updateByBid(MkChannelEntity record);
    
    
    /**
     * 获取约看量
     * @author tianxf9
     * @param startDate
     * @param endDate
     * @return
     */
    List<MkChannelCountDto> getYueKanCount(@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("channelType")int channelType,@Param("cityId")String cityId);
    
    /**
     * 获取客源量
     * @author tianxf9
     * @param startDate
     * @param endDate
     * @return
     */
    List<MkChannelCountDto> getTouristsCount(@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("channelType")int channelType,@Param("cityId")String cityId);
    
    /**
     * 获取客源成交量
     * @author tianxf9
     * @param startDate
     * @param endDate
     * @return
     */
    List<MkChannelCountDto> getDealCount(@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("channelType")int channelType,@Param("cityId")String cityId);
    
    
    /**
     * 根据日期获得所有排班
     * @author tianxf9
     * @param fweek
     * @return
     */
    List<SchedulePersonDto> getSchedulePresonInfo(@Param("fweek")String fweek,@Param("projectIds")Set<String> projectIds);
    
    /**
     * 根据bid获取name
     * @author tianxf9
     * @param channelBid
     * @return
     */
    String getChannelNameByBid(String channelBid);
    
    /**
     * 判断渠道名称是否已经存在
     * @author tianxf9
     * @param channelName
     * @param channelType
     * @return
     */
    int isExistChannelName(@Param("channelName")String channelName,@Param("channelType")int channelType,@Param("channelBid")String channelBid);
 
}