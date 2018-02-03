/**
 * @FileName: ActivityGiftItemDao.java
 * @Package com.ziroom.minsu.services.cms.dao
 * 
 * @author yd
 * @created 2016年10月9日 上午11:17:56
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.cms.ActivityGiftItemEntity;
import com.ziroom.minsu.services.cms.dto.ActivityGiftItemRequest;
import com.ziroom.minsu.services.cms.entity.AcGiftItemVo;

/**
 * <p>TODO</p>
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
@Repository("cms.activityGiftItemDao")
public class ActivityGiftItemDao {


	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(ActivityGiftItemDao.class);

	private String SQLID = "cms.activityGiftItemDao.";

	@Autowired
	@Qualifier("cms.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

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

		if(!Check.NuNObj(activityGiftItem)){
			if(Check.NuNStr(activityGiftItem.getFid())) activityGiftItem.setFid(UUIDGenerator.hexUUID());
			return mybatisDaoContext.save(SQLID+"insertSelective", activityGiftItem);
		}

		return 0;
	}


	/**
	 * 
	 * 批量保存 礼物项是实体
	 *
	 * @author yd
	 * @created 2016年10月9日 上午11:22:57
	 *
	 * @param listGiftItems
	 */
	public void bathSaveGiftItem(List<ActivityGiftItemEntity> listGiftItems){

		if(!Check.NuNCollection(listGiftItems)){
			for (ActivityGiftItemEntity activityGiftItemEntity : listGiftItems) {
				saveGiftItem(activityGiftItemEntity);
			}
		}
	}
	
	/**
	 * 
	 * 根据fid 修改礼物项
	 *
	 * @author yd
	 * @created 2016年10月9日 上午11:25:27
	 *
	 * @param giftItem
	 * @return
	 */
	public int updateGiftItem(ActivityGiftItemEntity giftItem){
		
		if(!Check.NuNObj(giftItem)&&!Check.NuNStr(giftItem.getFid())){
			return mybatisDaoContext.update(SQLID+"updateByFid", giftItem);
		}
		return 0;
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
		return mybatisDaoContext.findAll(SQLID+"getAcGiftItemByCon", AcGiftItemVo.class, activityGiftItemRe);
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
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("actSn", actSn);
		return mybatisDaoContext.update(SQLID+"updateAcItemByActSn", param);
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
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("actSn", actSn);
		return mybatisDaoContext.findAll(SQLID+"queryActivityGiftItemByActSn", ActivityGiftItemEntity.class, params);
	}
}
