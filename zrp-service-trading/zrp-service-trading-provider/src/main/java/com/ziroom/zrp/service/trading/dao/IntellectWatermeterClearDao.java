/**
 * @FileName: IntellectWatermeterClearDao.java
 * @Package com.ziroom.zrp.service.trading.dao
 * 
 * @author bushujie
 * @created 2018年1月12日 下午2:30:20
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.zrp.service.trading.dao;


import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.trading.entity.IntellectWatermeterClearEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
@Repository("trading.IntellectWatermeterClearDao")
public class IntellectWatermeterClearDao {
	
    private String SQLID = "trading.IntellectWatermeterClearDao.";

    @Autowired
    @Qualifier("trading.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 
     * fid查询智能水表清算记录
     *
     * @author bushujie
     * @created 2018年1月12日 下午2:36:04
     *
     * @param fid
     * @return
     */
    public IntellectWatermeterClearEntity findIntellectWatermeterClearByFid(String fid){
    	return mybatisDaoContext.findOneSlave(SQLID+"findIntellectWatermeterClearByFid", IntellectWatermeterClearEntity.class, fid);
    }
    
    /**
     * 
     * 插入智能水表清算记录
     *
     * @author bushujie
     * @created 2018年1月12日 下午2:39:22
     *
     * @param intellectWatermeterClearEntity
     */
    public int insertIntellectWatermeterClear(IntellectWatermeterClearEntity intellectWatermeterClearEntity){
        if(Check.NuNStr(intellectWatermeterClearEntity.getFid())){
            intellectWatermeterClearEntity.setFid(UUIDGenerator.hexUUID());
        }
    	return mybatisDaoContext.save(SQLID+"insertIntellectWatermeterClear", intellectWatermeterClearEntity);
    }
    
    /**
     * 
     * 更新智能水表清算记录
     *
     * @author bushujie
     * @created 2018年1月12日 下午2:42:26
     *
     * @param intellectWatermeterClearEntity
     * @return
     */
    public int updateIntellectWatermeterClear(IntellectWatermeterClearEntity intellectWatermeterClearEntity){
    	return mybatisDaoContext.update(SQLID+"updateIntellectWatermeterClear", intellectWatermeterClearEntity);
    }

    /**
     * 更新智能水表清算记录状态为已转移
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public int updateIntellectWatermeterClearStatus(String contractId) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("contractId", contractId);
        return mybatisDaoContext.update(SQLID+"updateIntellectWatermeterClearStatus", paramMap);
    }

    /**
     * 更新智能水表清算记录状态为已结算
     * @author xiangb
     * @created 2018年3月16日11:36:16
     * @param
     * @return
     */
    public int updateClearStatusToYJS(String contractId) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("contractId", contractId);
        return mybatisDaoContext.update(SQLID+"updateClearStatusToYJS", paramMap);
    }


    /**
      * @description: 根据合同id查询智能水表清算记录
      * @author: lusp
      * @date: 2018/2/7 下午 14:17
      * @params: contractId
      * @return: List<IntellectWatermeterClearEntity>
      */
    public List<IntellectWatermeterClearEntity> findIntellectWatermeterClearByContractId(String contractId){
        return mybatisDaoContext.findAll(SQLID+"findIntellectWatermeterClearByContractId",IntellectWatermeterClearEntity.class,contractId);
    }

    /**
     *
     * 查询最新（不管未结算、已结算）的清算记录
     *
     * @author zhangyl2
     * @created 2018年02月28日 11:13
     * @param
     * @return
     */
    public IntellectWatermeterClearEntity getLastClearingReading(String contractId){
        return mybatisDaoContext.findOne(SQLID+"getLastClearingReading",IntellectWatermeterClearEntity.class,contractId);
    }
}
