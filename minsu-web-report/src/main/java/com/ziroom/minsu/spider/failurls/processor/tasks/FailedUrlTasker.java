package com.ziroom.minsu.spider.failurls.processor.tasks;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ziroom.minsu.spider.airbnb.dto.AirbnbListRequest;
import com.ziroom.minsu.spider.airbnb.processor.AirbnbProcessor;
import com.ziroom.minsu.spider.airbnb.processor.tasks.AirbnbHostTasker;
import com.ziroom.minsu.spider.airbnb.processor.tasks.AirbnbHouseInfoTasker;
import com.ziroom.minsu.spider.commons.BaseTasker;
import com.ziroom.minsu.spider.failurls.entity.FailUrlRecordsEntity;
import com.ziroom.minsu.spider.failurls.entity.enums.FailUrlRecordTypeEnum;
import com.ziroom.minsu.spider.failurls.service.FailUrlRecordsService;
import com.ziroom.minsu.spider.xiaozhunew.processor.XiaozhuProcessor;
import com.ziroom.minsu.spider.xiaozhunew.processor.tasks.XiaozhuHostTasker;
import com.ziroom.minsu.spider.xiaozhunew.processor.tasks.XiaozhuHouseTasker;
/**
 * 
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
public class FailedUrlTasker extends BaseTasker implements Runnable {

	private List<Integer> urlIds;
	
    private Integer[] urlType; 
	
	private FailUrlRecordsService failUrlService;
	
	public FailedUrlTasker(List<Integer> urlIds) {
		super();
		this.urlIds = urlIds;
		init();
	} 
	public FailedUrlTasker(Integer[] urlType) {
		super();
		this.urlType = urlType;
		init();
	}
	private void init(){
		failUrlService = super.getBeanByClass(FailUrlRecordsService.class);
	}


	@Override
	public void run() {
		try { 
			List<FailUrlRecordsEntity> list = null;
			if (urlIds!=null && urlIds.size()>0) {
				list =failUrlService.selectByIDs(urlIds);
			}
			
			if (urlType!=null && urlType.length>0) {
				list =failUrlService.selectByUrlTypes(urlType);
			}
			
			if (list!=null && list.size()>0) {
				for (FailUrlRecordsEntity failUrlRecordsEntity : list) {
					if (FailUrlRecordTypeEnum.AirbnbList.getCode()== failUrlRecordsEntity.getUrlType()) {
						 try {
							 Set<FailUrlRecordsEntity> failUrlRecords= new HashSet<FailUrlRecordsEntity>();
							 AirbnbProcessor.parseHouseListByListUrl(failUrlRecordsEntity.getUrl(), failUrlRecords);
							 for (FailUrlRecordsEntity failUrl: failUrlRecords) {
									failUrlService.saveFailUrlRecords(failUrl);
								}
						 } catch (Exception e) {
							e.printStackTrace();
						 }
						
					}else if (FailUrlRecordTypeEnum.AirbnbHouse.getCode()== failUrlRecordsEntity.getUrlType()) {
						
						AirbnbHouseInfoTasker houseInfoTasker = new AirbnbHouseInfoTasker(failUrlRecordsEntity.getUrl(), null);
						houseInfoTasker.run();
						
					}else if (FailUrlRecordTypeEnum.AirbnbHost.getCode()== failUrlRecordsEntity.getUrlType()) {
						
						AirbnbHostTasker hostTasker= new AirbnbHostTasker(failUrlRecordsEntity.getUrl());
						hostTasker.run();
						
					}else if (FailUrlRecordTypeEnum.XiaozhuList.getCode()== failUrlRecordsEntity.getUrlType()) {
						try {
							 Set<FailUrlRecordsEntity> failUrlRecords= new HashSet<FailUrlRecordsEntity>();
							XiaozhuProcessor.pearseListByListUrl(failUrlRecordsEntity.getUrl(),failUrlRecords);
							 for (FailUrlRecordsEntity failUrl: failUrlRecords) {
									failUrlService.saveFailUrlRecords(failUrl);
								}
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}else if (FailUrlRecordTypeEnum.XiaozhuHouse.getCode()== failUrlRecordsEntity.getUrlType()) {
						
						XiaozhuHouseTasker houseTasker = new XiaozhuHouseTasker(failUrlRecordsEntity.getUrl(),null,null);
						houseTasker.run();
						
					}else if (FailUrlRecordTypeEnum.XiaozhuHost.getCode()== failUrlRecordsEntity.getUrlType()) {
						
						XiaozhuHostTasker tasker = new XiaozhuHostTasker(failUrlRecordsEntity.getUrl(),null);
						tasker.run();
						
					}
					
					
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
	}

}
