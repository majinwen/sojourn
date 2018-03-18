/**
 * @FileName: IntellectWattMeterSnapshotDao.java
 * @Package com.ziroom.zrp.service.trading.dao
 * 
 * @author bushujie
 * @created 2018年1月12日 下午4:35:56
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.zrp.service.trading.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.zra.common.dto.base.BasePageParamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.trading.entity.IntellectWattMeterSnapshotEntity;

/**
 * <p>TODO</p>
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
@Repository("trading.intellectWattMeterSnapshotDao")
public class IntellectWattMeterSnapshotDao {
	
    private String SQLID = "trading.IntellectWattMeterSnapshotDao.";

    @Autowired
    @Qualifier("trading.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 
     * fid查询充电记录
     *
     * @author bushujie
     * @created 2018年1月12日 下午4:53:33
     *
     * @param fid
     * @return
     */
    public IntellectWattMeterSnapshotEntity findIntellectWattMeterSnapshot(String fid){
    	return mybatisDaoContext.findOneSlave(SQLID+"findIntellectWattMeterSnapshot", IntellectWattMeterSnapshotEntity.class, fid);
    }
    
    /**
     * 
     * 插入智能电表充电记录
     *
     * @author bushujie
     * @created 2018年1月12日 下午5:10:04
     *
     * @param intellectWattMeterSnapshotEntity
     */
    public int insertIntellectWattMeterSnapshot(IntellectWattMeterSnapshotEntity intellectWattMeterSnapshotEntity){
       if (Check.NuNStr(intellectWattMeterSnapshotEntity.getFid())){
           intellectWattMeterSnapshotEntity.setFid(UUIDGenerator.hexUUID());
       }
       return mybatisDaoContext.save(SQLID+"insertIntellectWattMeterSnapshot", intellectWattMeterSnapshotEntity);
    }
    
    /**
     * 
     * 更新智能电表充电记录
     *
     * @author bushujie
     * @created 2018年1月12日 下午5:11:35
     *
     * @param intellectWattMeterSnapshotEntity
     * @return
     */
    public int updateIntellectWattMeterSnapshot(IntellectWattMeterSnapshotEntity intellectWattMeterSnapshotEntity){
    	return mybatisDaoContext.update(SQLID+"updateIntellectWattMeterSnapshot", intellectWattMeterSnapshotEntity);
    }
    /**
     * 查找
     * @author jixd
     * @created 2018年02月08日 09:25:19
     * @param
     * @return
     */
    public IntellectWattMeterSnapshotEntity findIntellectWattMeterByServiceId(String serviceId){
        return mybatisDaoContext.findOne(SQLID + "findIntellectWattMeterByServiceId",IntellectWattMeterSnapshotEntity.class,serviceId);
    }

    /**
     * 查询失败重试电表
     * @param basePageParamDto
     * @return
     */
    public PagingResult<IntellectWattMeterSnapshotEntity> listRetryWattMeterPage(BasePageParamDto basePageParamDto){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setPage(basePageParamDto.getPage());
        pageBounds.setLimit(basePageParamDto.getRows());
        return mybatisDaoContext.findForPage(SQLID +"listRetryWattMeterPage",IntellectWattMeterSnapshotEntity.class,null,pageBounds);
    }
}
