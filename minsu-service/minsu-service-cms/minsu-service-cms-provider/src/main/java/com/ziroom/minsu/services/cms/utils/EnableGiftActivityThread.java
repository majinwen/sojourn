/**
 * @FileName: EnableGiftActivityThread.java
 * @Package com.ziroom.minsu.services.cms.utils
 * 
 * @author yd
 * @created 2016年10月10日 下午11:43:53
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActivityEntity;
import com.ziroom.minsu.entity.cms.ActivityGiftItemEntity;
import com.ziroom.minsu.entity.cms.ActivityRecordEntity;
import com.ziroom.minsu.services.cms.dto.ActivityGiftItemRequest;
import com.ziroom.minsu.services.cms.entity.AcGiftItemVo;
import com.ziroom.minsu.services.cms.service.ActivityGiftItemServiceImpl;
import com.ziroom.minsu.services.cms.service.ActivityRecordServiceImpl;
import com.ziroom.minsu.services.cms.service.ActivityServiceImpl;
import com.ziroom.minsu.valenum.cms.IsPickUpEnum;

/**
 * <p>启用礼物活动项目 插入礼物项  
 * 
 *  </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public  class EnableGiftActivityThread implements Runnable{

	private static Logger LOGGER  = LoggerFactory.getLogger(EnableGiftActivityThread.class);

	//活动礼物项
	private ActivityGiftItemServiceImpl activityGiftItemServiceImpl;

	//活动
	private ActivityServiceImpl activityServiceImpl;

	//活动礼品 领取记录
	private ActivityRecordServiceImpl activityRecordServiceImpl;

	//活动 编号
	private String actSn;

	public EnableGiftActivityThread(){}; 

	public EnableGiftActivityThread(ActivityGiftItemServiceImpl activityGiftItemServiceImpl,ActivityServiceImpl activityServiceImpl
			,ActivityRecordServiceImpl activityRecordServiceImpl,String actSn){
		this.activityGiftItemServiceImpl = activityGiftItemServiceImpl;
		this.activityRecordServiceImpl = activityRecordServiceImpl;
		this.activityServiceImpl = activityServiceImpl;
		this.actSn  = actSn;

	}; 

	/**
	 * @return the actSn
	 */
	public String getActSn() {
		return actSn;
	}
	/**
	 * @param actSn the actSn to set
	 */
	public void setActSn(String actSn) {
		this.actSn = actSn;
	}
	/**
	 * @return the activityGiftItemServiceImpl
	 */
	public ActivityGiftItemServiceImpl getActivityGiftItemServiceImpl() {
		return activityGiftItemServiceImpl;
	}
	/**
	 * @param activityGiftItemServiceImpl the activityGiftItemServiceImpl to set
	 */
	public void setActivityGiftItemServiceImpl(
			ActivityGiftItemServiceImpl activityGiftItemServiceImpl) {
		this.activityGiftItemServiceImpl = activityGiftItemServiceImpl;
	}
	/**
	 * @return the activityServiceImpl
	 */
	public ActivityServiceImpl getActivityServiceImpl() {
		return activityServiceImpl;
	}
	/**
	 * @param activityServiceImpl the activityServiceImpl to set
	 */
	public void setActivityServiceImpl(ActivityServiceImpl activityServiceImpl) {
		this.activityServiceImpl = activityServiceImpl;
	}
	/**
	 * @return the activityRecordServiceImpl
	 */
	public ActivityRecordServiceImpl getActivityRecordServiceImpl() {
		return activityRecordServiceImpl;
	}
	/**
	 * @param activityRecordServiceImpl the activityRecordServiceImpl to set
	 */
	public void setActivityRecordServiceImpl(
			ActivityRecordServiceImpl activityRecordServiceImpl) {
		this.activityRecordServiceImpl = activityRecordServiceImpl;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		String actSn = this.getActSn();
		if(!Check.NuNStr(actSn)){
			ActivityEntity ac = this.activityServiceImpl.selectByActSn(actSn);
			if(!Check.NuNObj(ac)&&ac.getActType().intValue() == ac.getActType()){
				LogUtil.info(LOGGER, "根据actSn={}，查询活动ac={}", ac.toJsonStr());
				ActivityGiftItemRequest activityGiftItemRe = new ActivityGiftItemRequest();
				activityGiftItemRe.setActSn(actSn);
				List<ActivityGiftItemEntity> listAcGiftItems = this.activityGiftItemServiceImpl.queryActivityGiftItemByActSn(actSn);

				if(!Check.NuNCollection(listAcGiftItems)){
                    this.activityRecordServiceImpl.deleteAcRecord(actSn);//删除当前活动的 重复数据
                    
                    List<ActivityRecordEntity> listRecords = new ArrayList<ActivityRecordEntity>();
                    
                    for (ActivityGiftItemEntity acGiftItemVo : listAcGiftItems) {
						  for (int i = 0; i < acGiftItemVo.getGiftCount(); i++) {
							  ActivityRecordEntity acRecord = new ActivityRecordEntity();
							  acRecord.setActSn(actSn);
							  acRecord.setFid(UUIDGenerator.hexUUID());
							  acRecord.setGiftFid(acGiftItemVo.getGiftFid());
							  acRecord.setGroupSn(ac.getGroupSn());
							  acRecord.setIsPickUp(IsPickUpEnum.NO_PICK_UP.getCode());
							  listRecords.add(acRecord);
						}
					}
                    
                    if(!Check.NuNCollection(listRecords)){
                    	this.activityRecordServiceImpl.bachSaveActivityRecord(listRecords);
                    }
				}

			}
		}

	}

}
