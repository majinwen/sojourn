package com.zra.repair.dao;

import com.github.pagehelper.Page;
import com.zra.repair.entity.ZryRepairOrder;
import com.zra.repair.entity.ZryRepairOrderLog;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ZryRepairOrderLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZryRepairOrderLog record);

    int insertSelective(ZryRepairOrderLog record);

    ZryRepairOrderLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZryRepairOrderLog record);

    int updateByPrimaryKey(ZryRepairOrderLog record);

    Page selectOrderBy();

    List<ZryRepairOrderLog> selectByCondition(Map searchMap);
}