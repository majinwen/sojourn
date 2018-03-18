/**
 * @FileName: XiaozhuHouseInfoEntityServiceImpl.java
 * @Package com.ziroom.minsu.spider.xiaozhunew.service.impl
 * 
 * @author zl
 * @created 2016年10月21日 下午9:16:29
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.xiaozhunew.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ziroom.minsu.spider.xiaozhunew.dao.XiaozhuNewHouseInfoEntityMapper;
import com.ziroom.minsu.spider.xiaozhunew.dao.XiaozhuNewHousePriceEntityMapper;
import com.ziroom.minsu.spider.xiaozhunew.entity.XiaozhuNewHouseInfoEntityWithBLOBs;
import com.ziroom.minsu.spider.xiaozhunew.entity.XiaozhuNewHousePriceEntity;
import com.ziroom.minsu.spider.xiaozhunew.service.XiaozhuHouseInfoEntityService;

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
public class XiaozhuHouseInfoEntityServiceImpl implements
		XiaozhuHouseInfoEntityService {

	@Autowired
	private XiaozhuNewHouseInfoEntityMapper houseMapper;
	
	@Autowired
	private XiaozhuNewHousePriceEntityMapper priceMapper;
	
	@Override
	public int saveAirbnbHouseInfo(	XiaozhuNewHouseInfoEntityWithBLOBs houseInfoEntity) {

		int n=0;
		if (houseInfoEntity!=null && houseInfoEntity.getHouseSn()!=null ) {
			XiaozhuNewHouseInfoEntityWithBLOBs oldhouseInfoEntity=houseMapper.selectByHouseSn(houseInfoEntity.getHouseSn());
			if (oldhouseInfoEntity==null) {
				houseInfoEntity.setCreateDate(new Date());
				houseInfoEntity.setLastModifyDate(new Date());
				n=houseMapper.insert(houseInfoEntity);
			}else {
				houseInfoEntity.setId(oldhouseInfoEntity.getId());
				houseInfoEntity.setLastModifyDate(new Date());
				n=houseMapper.updateByPrimaryKeySelective(houseInfoEntity);
			}
			
			if (n>0 && houseInfoEntity.getPriceEntities()!=null && houseInfoEntity.getPriceEntities().size()>0) {
				priceMapper.delByHouseSn(houseInfoEntity.getHouseSn());//历史数据太多，直接删除
				Set<String> priceSet = new HashSet<String>();
				
				for (XiaozhuNewHousePriceEntity price : houseInfoEntity.getPriceEntities()) {
					try {
						String keyString = price.getHouseSn()+"-"+new SimpleDateFormat("yyyy-MM-dd").format(price.getDate());
						if (!priceSet.contains(keyString)) {
							price.setCreateDate(new Date());
							price.setLastModifyDate(new Date());
							priceMapper.insert(price);//日历中每个月的月末和下月初的日期可能相同
							priceSet.add(keyString);
						}
						
					} catch (Exception e) { 
						
					}
				}
				
			}
			
		}
		return n;
	}

	@Override
	public XiaozhuNewHouseInfoEntityWithBLOBs selectByHouseSn(String houseSn) {
		
		return houseMapper.selectByHouseSn(houseSn);
	}

	@Override
	public List<XiaozhuNewHousePriceEntity> findByHouseSn(String houseSn) {
		return priceMapper.findByHouseSn(houseSn);
	}

}
