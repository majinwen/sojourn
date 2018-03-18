package com.zra.zmconfig.dao;

import java.util.List;

import com.zra.zmconfig.entity.CfZmConfig;

public interface CfZmConfigMapper {
	
    /**
     * 查询数据
     * @param record
     * @return
     */
    int deleteByPrimaryKey(CfZmConfig record);

    /**
     * 保存
     * 
     */
    int insert(CfZmConfig record);
    
    /**
     * 
     * */
    int update(CfZmConfig record);


    
    /**
     * 查询全部
     * @return
     */
    List<CfZmConfig> queryAll(CfZmConfig record);
    
}