package com.zra.marketing.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zra.common.dto.marketing.MkNumberDto;
import com.zra.house.entity.dto.ProjectTelDto;
import com.zra.marketing.entity.MkNumberEntity;
@Repository
public interface MkNumberMapper {

    /**
     * 批量添加分机号
     * @author tianxf9
     * @param record
     * @return
     */
    int insert(List<MkNumberEntity> record);

    /**
     * 添加分机号
     * @author tianxf9
     * @param record
     * @return
     */
    int insertSelective(MkNumberEntity record);

    
    /**
     * 根据渠道bid查询渠道对应的分机号
     * @author tianxf9
     * @param channelBid
     * @return
     */
    List<MkNumberDto> selectByChannelBid(String channelBid);

    /**
     * 删除渠道分机号根据渠道bid
     * @author tianxf9
     * @param channelBid
     * @return
     */
    int delByChannelBid(@Param("channelBid")String channelBid,@Param("userId")String userId);
    
    /**
     * 根据渠道统计的bid获取分机号
     * @author tianxf9
     * @param channelBid
     * @return
     */
    List<String> getNumberByChannelBid(String channelBid);
    
    /**
     * 获取所有分机号
     * @author tianxf9
     * @return
     */
    List<ProjectTelDto> getAllNumber();
    
    /**
     * 根据项目id查询项目下所有分机号
     * @author tianxf9
     * @param projectId
     * @return
     */
    List<String> getNumbersByProId(String projectId);
    
    /**
     * 校验当前渠道下的分机号是否和其他渠道的重复
     * @author tianxf9
     * @param channelBid
     * @param channelBid
     * @return
     */
    List<String> isExistNumber(@Param("numbers")List<String> numbers,@Param("channelBid")String channelBid);
}