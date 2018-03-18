/**
 * @FileName: XiaozhuHostEntityServiceImpl.java
 * @Package com.ziroom.minsu.spider.xiaozhunew.service.impl
 * 
 * @author zl
 * @created 2016年10月21日 下午9:12:46
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.xiaozhunew.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ziroom.minsu.spider.xiaozhunew.dao.XiaozhuNewHostInfoEntityMapper;
import com.ziroom.minsu.spider.xiaozhunew.entity.XiaozhuNewHostInfoEntity;
import com.ziroom.minsu.spider.xiaozhunew.service.XiaozhuHostEntityService;

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
@Service
public class XiaozhuHostEntityServiceImpl implements XiaozhuHostEntityService {

	@Autowired
	private XiaozhuNewHostInfoEntityMapper hostmapper;
	
	@Override
	public int saveAirbnbHostInfo(XiaozhuNewHostInfoEntity hostInfoEntity) {
		if (hostInfoEntity!=null && hostInfoEntity.getHostSn()!=null) {
			
			if (hostInfoEntity.getOrderAcceptRate()!=null) {
				BigDecimal bd= new BigDecimal(hostInfoEntity.getOrderAcceptRate()); 
				hostInfoEntity.setOrderAcceptRate(bd.setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue());
			}
			
			XiaozhuNewHostInfoEntity oldEntity = hostmapper.selectByHostSn(hostInfoEntity.getHostSn()); 
			if (oldEntity==null) {
				hostInfoEntity.setCreateDate(new Date());
				hostInfoEntity.setLastModifyDate(new Date());
				return hostmapper.insert(hostInfoEntity);
			}else {
				hostInfoEntity.setId(oldEntity.getId());
				hostInfoEntity.setLastModifyDate(new Date());
				return hostmapper.updateByPrimaryKeySelective(hostInfoEntity);
			}
		}
		return 0;
	}

}
