package com.zra.syncc.service;

import java.util.List;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zra.common.utils.MultipleDataSource;
import com.zra.syncc.dao.ZraZrCallDetailMapper;
import com.zra.syncc.entity.ZraZrCallDetailEntity;

/**
 * 自如寓400来电详情服务
 * @author tianxf9
 *
 */
@Service
public class ZraZrCallDetailService {
	
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(ZraZrCallDetailService.class);
    
	@Autowired
	private ZraZrCallDetailMapper dao;
	
   /**
     * 从400系统获取自如寓通话详情
     * @author tianxf9
     * @param startDate
     * @param endDate
     * @param extNum
     * @return
     */
    public List<ZraZrCallDetailEntity> getCallDetailFromNewCC(String startDate,String endDate,String extNum) {
        MultipleDataSource.setDataSourceKey("newccdataSource");
        List<ZraZrCallDetailEntity> callDetailEntity = this.dao.getCallDetailFromNewCC(startDate,endDate,extNum);
        LOGGER.info("新平台从400系统获取自如寓通话详情数量："+callDetailEntity.size());
        MultipleDataSource.setDataSourceKey("dataSource");
        return callDetailEntity;
    }
	
	/**
	 * 保存400通话详情
	 * @author tianxf9
	 * @param entitys
	 * @return
	 */
	public int saveCallDetailEntitys(ZraZrCallDetailEntity entitys) {
		return this.dao.saveCallDetail(entitys);
	}
	
	public List<ZraZrCallDetailEntity> getCallDetailById(String callId) {
		return this.dao.getCallDetailById(callId);
	}
	
	public int updateCallDetailEntity(ZraZrCallDetailEntity entity) {
	    LOGGER.info("更新通话详情："+JSON.toJSONString(entity));
		return this.dao.updateCallDetailEntity(entity);
	}
	
}
