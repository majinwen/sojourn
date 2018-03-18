/**
 * @FileName: AirbnbHostInfoEntityServiceImpl.java
 * @Package com.ziroom.minsu.spider.airbnb.service.impl
 * 
 * @author zl
 * @created 2016年9月30日 下午4:48:11
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.airbnb.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ziroom.minsu.spider.airbnb.dao.AirbnbHostInfoEntityMapper;
import com.ziroom.minsu.spider.airbnb.entity.AirbnbHostInfoEntity;
import com.ziroom.minsu.spider.airbnb.service.AirbnbHostInfoEntityService;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
@Service("airbnbHostInfoService")
public class AirbnbHostInfoEntityServiceImpl implements
		AirbnbHostInfoEntityService {
 
	@Autowired
	private AirbnbHostInfoEntityMapper hostInfoEntityMapper;
	
	@Override
	public int saveAirbnbHostInfo(AirbnbHostInfoEntity hostInfoEntity) {
		if (hostInfoEntity!=null && hostInfoEntity.getHostSn()!=null) {
			AirbnbHostInfoEntity oldAirbnbHostInfoEntity = hostInfoEntityMapper.selectByHostSn(hostInfoEntity.getHostSn()); 
			if (oldAirbnbHostInfoEntity==null) {
				hostInfoEntity.setCreateDate(new Date());
				hostInfoEntity.setLastModifyDate(new Date());
				return hostInfoEntityMapper.insert(hostInfoEntity);
			}else {
				hostInfoEntity.setId(oldAirbnbHostInfoEntity.getId());
				hostInfoEntity.setLastModifyDate(new Date());
				return hostInfoEntityMapper.updateByPrimaryKeySelective(hostInfoEntity);
			}
		}
		return 0;
	}

}
