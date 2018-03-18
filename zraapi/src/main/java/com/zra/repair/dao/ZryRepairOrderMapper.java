package com.zra.repair.dao;

import com.zra.repair.entity.ZryRepairOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ZryRepairOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZryRepairOrder record);

    int insertSelective(ZryRepairOrder record);

    ZryRepairOrder selectByFId(String fid);

    int updateByPrimaryKeySelective(ZryRepairOrder record);

    int updateByPrimaryKey(ZryRepairOrder record);

    List<ZryRepairOrder> selectAll();

    int deleteById(Integer id);

    int updateStatusByFId(@Param("fid") String fid, @Param("status") Integer status);

    List<ZryRepairOrder> selectByCondition(Map<String, String> searchMap);

    ZryRepairOrder selectByOrderSn(String orderSn);
    
    int updateStatusByOrderSn(@Param("orderSn") String orderSn, @Param("status") Integer status);
}