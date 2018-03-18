/**
 * @FileName: IntellectWatermeterReadDao.java
 * @Package com.ziroom.zrp.service.trading.dao
 * 
 * @author bushujie
 * @created 2018年1月12日 下午2:49:02
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.zrp.service.trading.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.zrp.service.trading.dto.waterwatt.IntellectWatermeterReadDto;
import com.ziroom.zrp.service.trading.entity.IntellectWatermeterReadVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.trading.entity.IntellectWatermeterReadEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>智能水表Dao</p>
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
@Repository("trading.IntellectWatermeterReadDao")
public class IntellectWatermeterReadDao {
	
    private String SQLID = "trading.IntellectWatermeterReadDao.";

    @Autowired
    @Qualifier("trading.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 
     * fid查询水表抄表记录
     *
     * @author bushujie
     * @created 2018年1月12日 下午2:56:00
     *
     * @param fid
     * @return
     */
    public IntellectWatermeterReadEntity findIntellectWatermeterReadByFid(String fid){
    	return mybatisDaoContext.findOneSlave(SQLID+"findIntellectWatermeterReadByFid", IntellectWatermeterReadEntity.class, fid);
    }
    
    /**
     * 
     * 插入水表抄表记录
     *
     * @author bushujie
     * @created 2018年1月12日 下午4:26:51
     *
     * @param intellectWatermeterReadEntity
     */
    public void insertIntellectWatermeterRead(IntellectWatermeterReadEntity intellectWatermeterReadEntity){
        if(Check.NuNStr(intellectWatermeterReadEntity.getFid())){
            intellectWatermeterReadEntity.setFid(UUIDGenerator.hexUUID());
        }
    	mybatisDaoContext.save(SQLID+"insertIntellectWatermeterRead", intellectWatermeterReadEntity);
    }
    
    /**
     * 
     * 更新水表抄表记录
     *
     * @author bushujie
     * @created 2018年1月12日 下午4:32:04
     *
     * @param intellectWatermeterReadEntity
     * @return
     */
    public int updateIntellectWatermeterRead(IntellectWatermeterReadEntity intellectWatermeterReadEntity){
    	return mybatisDaoContext.update(SQLID+"updateIntellectWatermeterRead", intellectWatermeterReadEntity);
    }
    
    /**
     * 
     * 查询最后一次定时抄表记录，根据房间
     *
     * @author bushujie
     * @created 2018年1月19日 下午4:41:11
     *
     * @param intellectWatermeterReadEntity
     * @return
     */
    public IntellectWatermeterReadEntity getLastIntellectWatermeterReadByRoomId(IntellectWatermeterReadEntity intellectWatermeterReadEntity){
    	return mybatisDaoContext.findOneSlave(SQLID+"getLastIntellectWatermeterReadByRoomId", IntellectWatermeterReadEntity.class, intellectWatermeterReadEntity);
    }

    /**
     *
     * 查询最新抄表记录
     *
     * @author zhangyl2
     * @created 2018年02月01日 18:33
     * @param
     * @return
     */
    public IntellectWatermeterReadEntity getNewestWatermeterRead(String projectId, String roomId){
        Map<String, Object> params = new HashMap<>();
        params.put("projectId", projectId);
        params.put("roomId", roomId);
        return mybatisDaoContext.findOneSlave(SQLID+"getNewestWatermeterRead", IntellectWatermeterReadEntity.class, params);
    }

    /**
     *
     * 分页查询定时任务的水表抄表记录
     *
     * @author zhangyl2
     * @created 2018年02月24日 15:19
     * @param
     * @return
     */
    public PagingResult<IntellectWatermeterReadVo> getIntellectWatermeterReadByPage(IntellectWatermeterReadDto intellectWatermeterReadDto){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setPage(intellectWatermeterReadDto.getPage());
        pageBounds.setLimit(intellectWatermeterReadDto.getRows());
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", intellectWatermeterReadDto.getProjectId());
        map.put("projectIds", intellectWatermeterReadDto.getProjectIds());
        map.put("readStatus", intellectWatermeterReadDto.getReadStatus());
        return mybatisDaoContext.findForPage(SQLID + "getIntellectWatermeterReadByPage", IntellectWatermeterReadVo.class, map, pageBounds);
    }
}
