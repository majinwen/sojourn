/**
 * @FileName: ActivityGiftItemServiceImpl.java
 * @Package com.ziroom.minsu.services.cms.service
 * 
 * @author yd
 * @created 2016年10月9日 下午5:03:41
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.cms.ActivityGiftItemEntity;
import com.ziroom.minsu.services.cms.dao.ActivityGiftItemDao;
import com.ziroom.minsu.services.cms.dto.ActivityGiftItemRequest;
import com.ziroom.minsu.services.cms.entity.AcGiftItemVo;

/**
 * <p>活动礼物项 的实现</p>
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
@Service("cms.activityGiftItemServiceImpl")
public class ActivityGiftItemServiceImpl {

	/**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(ActivityGiftItemServiceImpl.class);
	
    @Resource(name = "cms.activityGiftItemDao")
    private ActivityGiftItemDao activityGiftItemDao;
    

	/**
	 * 
	 * 保存 礼物项实体
	 *
	 * @author yd
	 * @created 2016年10月9日 上午11:21:57
	 *
	 * @param activityGiftItem
	 * @return
	 */
	public int saveGiftItem(ActivityGiftItemEntity activityGiftItem){
		return activityGiftItemDao.saveGiftItem(activityGiftItem);
	}
	
	/**
	 * 
	 * 批量保存 活动项
	 *
	 * @author yd
	 * @created 2016年10月9日 下午9:26:20
	 *
	 * @param list
	 */
	public void bachSaveGiftItem(List<ActivityGiftItemEntity> list){
		
		if(!Check.NuNCollection(list)){
			for (ActivityGiftItemEntity activityGiftItemEntity : list) {
			    saveGiftItem(activityGiftItemEntity);
			}
		}
	}
	
	/**
	 * 
	 * 条件查询 当前活动礼物相（包括 当前活动礼物 和 系统中得礼物 只取前500个）
	 *
	 * @author yd
	 * @created 2016年10月10日 下午1:31:55
	 *
	 * @return
	 */
	public List<AcGiftItemVo> getAcGiftItemByCon(ActivityGiftItemRequest activityGiftItemRe){
		return activityGiftItemDao.getAcGiftItemByCon(activityGiftItemRe);
	}
	
	/**
	 * 
	 * 根据 actSn 修改活动相 为删除
	 *
	 * @author yd
	 * @created 2016年10月10日 下午9:41:06
	 *
	 * @param actSn
	 * @return
	 */
	public int updateAcItemByActSn(String actSn){
		
		return activityGiftItemDao.updateAcItemByActSn(actSn);
	}
	
	/**
	 * 
	 * 根据actSn查询礼物项
	 *
	 * @author yd
	 * @created 2016年10月11日 上午12:13:35
	 *
	 * @param actSn
	 * @return
	 */
	public List<ActivityGiftItemEntity> queryActivityGiftItemByActSn(String actSn){
		return activityGiftItemDao.queryActivityGiftItemByActSn(actSn);
	}
}
