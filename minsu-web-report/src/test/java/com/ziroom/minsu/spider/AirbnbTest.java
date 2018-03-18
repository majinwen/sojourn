/**
 * @FileName: AirbnbTest.java
 * @Package com.ziroom.minsu.spider
 * 
 * @author zl 
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseTest;

import com.alibaba.fastjson.JSON;
import com.ziroom.minsu.spider.airbnb.dao.AirbnbAdditionalHostsEntityMapper;
import com.ziroom.minsu.spider.airbnb.dao.AirbnbHostInfoEntityMapper;
import com.ziroom.minsu.spider.airbnb.dao.AirbnbHouseInfoEntityMapper;
import com.ziroom.minsu.spider.airbnb.dao.AirbnbHousePriceEntityMapper;
import com.ziroom.minsu.spider.airbnb.dto.AirbnbListRequest;
import com.ziroom.minsu.spider.airbnb.entity.AirbnbAdditionalHostsEntity;
import com.ziroom.minsu.spider.airbnb.entity.AirbnbHostInfoEntity;
import com.ziroom.minsu.spider.airbnb.entity.AirbnbHouseInfoEntity;
import com.ziroom.minsu.spider.airbnb.entity.AirbnbHouseInfoEntityWithBLOBs;
import com.ziroom.minsu.spider.airbnb.entity.AirbnbHousePriceEntity;
import com.ziroom.minsu.spider.airbnb.processor.AirbnbProcessor;
import com.ziroom.minsu.spider.airbnb.processor.tasks.AirbnbHostTasker;
import com.ziroom.minsu.spider.airbnb.processor.tasks.AirbnbHouseInfoTasker;
import com.ziroom.minsu.spider.airbnb.service.AirbnbHostInfoEntityService;
import com.ziroom.minsu.spider.airbnb.service.AirbnbHouseInfoEntityService;
import com.ziroom.minsu.spider.commons.SpiderEntityParser;
import com.ziroom.minsu.spider.commons.dto.HouseMsgVo;
import com.ziroom.minsu.spider.failurls.entity.FailUrlRecordsEntity;
import com.ziroom.minsu.spider.failurls.processor.tasks.FailedUrlTasker;
import com.ziroom.minsu.spider.failurls.service.FailUrlRecordsService;

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
public class AirbnbTest extends BaseTest {
	
	private final static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(30, 100, 300000L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
	
	@Autowired
	private AirbnbHostInfoEntityService airbnbHostInfoService;
	
	@Autowired
	private AirbnbHouseInfoEntityService houseInfoService;
	
	@Autowired
	private FailUrlRecordsService failUrlService;
	
	@Autowired
	private AirbnbAdditionalHostsEntityMapper additionalHostsMapper;
	
	@Autowired
	private AirbnbHostInfoEntityMapper hostInfoEntityMapper;
	
	@Autowired
	private AirbnbHouseInfoEntityMapper houseInfoMapper;
	
	@Autowired
	private AirbnbHousePriceEntityMapper housePriceMapper;
	
	@Ignore
	@Test
	public void modifyAdditionalHosts(){
		List<AirbnbAdditionalHostsEntity> list =additionalHostsMapper.findAll();
		for (AirbnbAdditionalHostsEntity airbnbAdditionalHostsEntity : list) {
			if (airbnbAdditionalHostsEntity.getHouseSn().indexOf("?")>-1) {
				airbnbAdditionalHostsEntity.setHouseSn(airbnbAdditionalHostsEntity.getHouseSn().substring(0, airbnbAdditionalHostsEntity.getHouseSn().indexOf("?")));
				additionalHostsMapper.updateByPrimaryKeySelective(airbnbAdditionalHostsEntity);
			}
		}
	}
	@Ignore
	@Test
	public void modifyHosts(){
		List<AirbnbHostInfoEntity> list=hostInfoEntityMapper.selectAll();
		for (AirbnbHostInfoEntity airbnbHostInfoEntity : list) {
			if (airbnbHostInfoEntity.getDetailUrl()==null || airbnbHostInfoEntity.getHostImg()==null) {
				String url="https://zh.airbnb.com/users/show/"+airbnbHostInfoEntity.getHostSn();
				AirbnbHostTasker hostTasker= new AirbnbHostTasker(url);
				hostTasker.run();
			}
		}
	}
	
	@Test
	public void modifyHouseAvailable() throws InterruptedException{
		List<AirbnbHouseInfoEntity> list =houseInfoMapper.selectByAvailableNull();
		if (list!=null && list.size()>0) {
			for (AirbnbHouseInfoEntity airbnbHouseInfoEntity : list) {
				AirbnbHouseInfoTasker houseInfoTasker = new AirbnbHouseInfoTasker(airbnbHouseInfoEntity.getDetailUrl(), null);
//				houseInfoTasker.run();
				Thread.sleep(5000);
				threadPoolExecutor.execute(houseInfoTasker);
			}
		}
	}
	@Ignore
	@Test
	public void insertPrice(){
		try {
			
			AirbnbHousePriceEntity price = new AirbnbHousePriceEntity();
			price.setAvailable(1);
			price.setCreateDate(new Date());
			price.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2016-08-30"));
			price.setHouseSn("14940828");
			price.setLastModifyDate(new Date());
			price.setLocalCurrency("CNY");
			price.setLocalPrice(149);
			price.setNativeCurrency("CNY");
			price.setNativePrice(149);
			
			housePriceMapper.insert(price);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void airbnbHouseToMinsuHouse(){
		AirbnbHouseInfoEntityWithBLOBs airbnb = houseInfoMapper.selectByHouseSn("11245829");
		
		HouseMsgVo minsuHouse = SpiderEntityParser.airbnbHouseToMinsuHouse(airbnb);
		
		System.err.println(JSON.toJSONString(minsuHouse));
		
	}
	
	
	
	
	
	@Ignore
	@Test
	public void modifyHouseInfo(){
		List<AirbnbHouseInfoEntity> list =houseInfoMapper.selectByHouseNull();
		if (list!=null && list.size()>0) {
			for (AirbnbHouseInfoEntity airbnbHouseInfoEntity : list) {
				airbnbHouseInfoEntity.setHouseSn(airbnbHouseInfoEntity.getHouseSn().replaceAll("\\?guests=(\\d+)", ""));
				airbnbHouseInfoEntity.setDetailUrl(airbnbHouseInfoEntity.getDetailUrl().replaceAll("\\?guests=(\\d+)", ""));
				AirbnbHouseInfoEntityWithBLOBs houseInfoEntity = new AirbnbHouseInfoEntityWithBLOBs();
				houseInfoEntity.setId(airbnbHouseInfoEntity.getId());
				houseInfoEntity.setHouseSn(airbnbHouseInfoEntity.getHouseSn());
				houseInfoEntity.setDetailUrl(airbnbHouseInfoEntity.getDetailUrl());
				houseInfoEntity.setLastModifyDate(new Date());
				try {
					houseInfoMapper.updateByPrimaryKeySelective(houseInfoEntity);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
	}
	
	@Ignore
	@Test
	public void saveAirbnbHouseInfo(){
		AirbnbHouseInfoEntityWithBLOBs house = new AirbnbHouseInfoEntityWithBLOBs();

		house.setHouseSn("5757575");
		house.setMinNights(6);
		house.setAllowsChildren(1);
		house.setAllowsEvents(0);
		house.setCreateDate(new Date());
		List<AirbnbAdditionalHostsEntity> additionalHostsEntities=new ArrayList<AirbnbAdditionalHostsEntity>();
		AirbnbAdditionalHostsEntity additionalHostsEntity = new AirbnbAdditionalHostsEntity();
		additionalHostsEntity.setAdditionalHostSn("111111111");
		additionalHostsEntity.setCountry("66666");
		additionalHostsEntity.setCreateDate(new Date());
		additionalHostsEntity.setHouseSn("5757575");
		additionalHostsEntity.setHostSn("111111111");		
		additionalHostsEntities.add(additionalHostsEntity);
		
		List<AirbnbHousePriceEntity> priceEntities=new ArrayList<AirbnbHousePriceEntity>();
		AirbnbHousePriceEntity priceEntity = new AirbnbHousePriceEntity();
		priceEntity.setCreateDate(new Date());
		priceEntity.setDate(new Date());
		priceEntity.setHouseSn("5757575");
		priceEntity.setLocalCurrency("CNY");
		priceEntity.setLocalPrice(77777);
		priceEntities.add(priceEntity);
		priceEntity.setNativePrice(888888);
		priceEntity.setNativeCurrency("CNY");
		
		house.setAdditionalHostsEntities(additionalHostsEntities);
		house.setPriceEntities(priceEntities);
		try {
			houseInfoService.saveAirbnbHouseInfo(house);
			
		} catch (Exception e) {
		 e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void saveAirbnbHostInfo(){
		AirbnbHostInfoEntity host = new AirbnbHostInfoEntity();
		host.setHostSn("68688");
		host.setLastName("e4e888888");
		host.setCreateDate(new Date());
		try {
			airbnbHostInfoService.saveAirbnbHostInfo(host);
			
		} catch (Exception e) {
		 e.printStackTrace();
		}
	}
	
	@Test
	public void spiderHouseDetail(){
		String url="https://zh.airbnb.com/rooms/16329752";
		
		AirbnbHouseInfoTasker houseInfoTasker = new AirbnbHouseInfoTasker(url, null);
		houseInfoTasker.run(); 
	}
	 
	@Ignore
	@Test
	public void spiderHostDetail(){		
		String url="https://www.airbnb.com/users/show/15809097";
		AirbnbHostTasker hostTasker= new AirbnbHostTasker(url);
		hostTasker.run(); 		
	}
	
	@Test
	public void spiderALL(){
		System.out.println("begin");
		Date beginDate=new Date();
		
		Set<FailUrlRecordsEntity> failUrlRecords= new HashSet<FailUrlRecordsEntity>();
		List<String> cityList = new ArrayList<String>();
//		cityList.add("Hangzhou--China");
//		cityList.add("Dali--China");
//		cityList.add("Lijiang--China");
//		cityList.add("Xi'an--China");
		cityList.add("Shanghai--China");
		cityList.add("Beijing--China");
//		cityList.add("Suzhou--China");
//		cityList.add("Xiamen--China");
//		cityList.add("Qingdao--China");
		cityList.add("Guangzhou--China");
//		cityList.add("Shenzhen--China");
//		cityList.add("Sanya--China");
		cityList.add("Chengdu--China");


		for (String city:cityList) {
			AirbnbListRequest request = new AirbnbListRequest(); 
			try {
				
				AirbnbProcessor.parseHouseList(city,request,failUrlRecords);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		for (FailUrlRecordsEntity failUrl: failUrlRecords) {
			failUrlService.saveFailUrlRecords(failUrl);
		}
		
		System.out.println("end,cost="+(new Date().getTime()-beginDate.getTime())+",failUrlRecords="+failUrlRecords.size());
		
	}
	
	@Test
	public void spiderFailed(){
		
		List<Integer> urlIds = new ArrayList<>();
		int i=5500;
		while (i<8662) {
			urlIds.add(i);
			i++;
		}
		
		FailedUrlTasker failedUrlTasker = new FailedUrlTasker(urlIds);
		
		Integer[] urlTypes =new Integer[]{11};
//		FailedUrlTasker failedUrlTasker = new FailedUrlTasker(urlTypes);
		failedUrlTasker.run();
		
		
	}
	

 

}
