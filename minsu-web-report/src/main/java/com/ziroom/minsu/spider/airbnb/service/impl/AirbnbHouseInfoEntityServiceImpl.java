/**
 * @FileName: AirbnbHouseInfoEntityServiceImpl.java
 * @Package com.ziroom.minsu.spider.airbnb.service.impl
 * 
 * @author zhangshaobin
 * @created 2016年9月30日 下午4:51:42
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.airbnb.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ziroom.minsu.spider.airbnb.dao.AirbnbAdditionalHostsEntityMapper;
import com.ziroom.minsu.spider.airbnb.dao.AirbnbHouseInfoEntityMapper;
import com.ziroom.minsu.spider.airbnb.dao.AirbnbHousePriceEntityMapper;
import com.ziroom.minsu.spider.airbnb.entity.AirbnbAdditionalHostsEntity;
import com.ziroom.minsu.spider.airbnb.entity.AirbnbHouseInfoEntityWithBLOBs;
import com.ziroom.minsu.spider.airbnb.entity.AirbnbHousePriceEntity;
import com.ziroom.minsu.spider.airbnb.service.AirbnbHouseInfoEntityService;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
@Service("houseInfoService")
public class AirbnbHouseInfoEntityServiceImpl implements
		AirbnbHouseInfoEntityService {

	@Autowired
	private AirbnbHouseInfoEntityMapper houseInfoMapper;	
	@Autowired
	private AirbnbAdditionalHostsEntityMapper additionalHostsMapper;	
	@Autowired
	private AirbnbHousePriceEntityMapper housePriceMapper;
	 
	@Override
	public int saveAirbnbHouseInfo(AirbnbHouseInfoEntityWithBLOBs houseInfoEntity) {
		int n=0;
		if (houseInfoEntity!=null && houseInfoEntity.getHouseSn()!=null ) {
			AirbnbHouseInfoEntityWithBLOBs oldhouseInfoEntity=houseInfoMapper.selectByHouseSn(houseInfoEntity.getHouseSn());
			if (oldhouseInfoEntity==null) {
				houseInfoEntity.setCreateDate(new Date());
				houseInfoEntity.setLastModifyDate(new Date());
				n=houseInfoMapper.insert(houseInfoEntity);
			}else {
				houseInfoEntity.setId(oldhouseInfoEntity.getId());
				houseInfoEntity.setLastModifyDate(new Date());
				n=houseInfoMapper.updateByPrimaryKeySelective(houseInfoEntity);
			}
			
			if (n>0 && houseInfoEntity.getAdditionalHostsEntities()!=null && houseInfoEntity.getAdditionalHostsEntities().size()>0) {
				for (AirbnbAdditionalHostsEntity additionalHosts : houseInfoEntity.getAdditionalHostsEntities()) {
					AirbnbAdditionalHostsEntity oldAdditionalHosts = additionalHostsMapper.findByAdditionalHostSn(additionalHosts.getAdditionalHostSn());
					if (oldAdditionalHosts==null) {
						additionalHosts.setCreateDate(new Date());
						additionalHosts.setLastModifyDate(new Date());
						additionalHostsMapper.insert(additionalHosts);
					}else {
						additionalHosts.setId(oldAdditionalHosts.getId());
						additionalHosts.setLastModifyDate(new Date());
						additionalHostsMapper.updateByPrimaryKeySelective(additionalHosts);
					}
				
				}
			}
			
			if (n>0 && houseInfoEntity.getPriceEntities()!=null && houseInfoEntity.getPriceEntities().size()>0) {
				housePriceMapper.delByHouseSn(houseInfoEntity.getHouseSn());//历史数据太多，直接删除
				Set<String> priceSet = new HashSet<String>();
				
				for (AirbnbHousePriceEntity price : houseInfoEntity.getPriceEntities()) {
					try {
						String keyString = price.getHouseSn()+"-"+new SimpleDateFormat("yyyy-MM-dd").format(price.getDate());
						if (!priceSet.contains(keyString)) {
							price.setCreateDate(new Date());
							price.setLastModifyDate(new Date());
							housePriceMapper.insert(price);//日历中每个月的月末和下月初的日期可能相同
							priceSet.add(keyString);
						}
						
					} catch (Exception e) { 
						e.printStackTrace();
					}
				}
				
			}
			
		}
		return n;
	}


	@Override
	public AirbnbHouseInfoEntityWithBLOBs selectAirbnbHouseByHouseSn(
			String houseSn) {
		return houseInfoMapper.selectByHouseSn(houseSn);
	}

 
	@Override
	public List<AirbnbHousePriceEntity> selectAirbnbPriceByHouseSn(
			String houseSn) {
		return housePriceMapper.findByHouseSn(houseSn);
	}
}
