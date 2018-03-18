package com.ziroom.zrp.service.trading.service;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asura.framework.base.util.Check;
import com.ziroom.zrp.service.trading.dao.SurrendBackRecordDao;
import com.ziroom.zrp.trading.entity.SurrendBackRecordEntity;

@Service("trading.surrendBackRecordServiceImpl")
public class SurrendBackRecordServiceImpl {
	
	@Resource(name="trading.surrendBackRecordDao")
	private SurrendBackRecordDao surrendBackRecordDao;
	
	/**
     * <p>更新原因</p>
     * @author xiangb
     * @created 2017年11月21日
     * @param
     * @return
     */
    public int updateSurrendBackRecord(SurrendBackRecordEntity surrendBackRecordEntity){
    	return surrendBackRecordDao.updateSurrendBackRecord(surrendBackRecordEntity);
    }
    /**
     * <p>保存原因</p>
     * @author xiangb
     * @created 2017年11月21日
     * @param
     * @return
     */
    public int saveSurrendBackRecord(SurrendBackRecordEntity surrendBackRecordEntity){
    	return surrendBackRecordDao.saveSurrendBackRecord(surrendBackRecordEntity);
    }
    /**
     * <p>根据参数查询</p>
     * @author xiangb
     * @created 2017年11月21日
     * @param
     * @return
     */
    public List<SurrendBackRecordEntity> findSurrendBackRecordEntityByParam(String surrenderId,String type){
    	return surrendBackRecordDao.findSurrendBackRecordEntityByParam(surrenderId, type);
    }
	

}
