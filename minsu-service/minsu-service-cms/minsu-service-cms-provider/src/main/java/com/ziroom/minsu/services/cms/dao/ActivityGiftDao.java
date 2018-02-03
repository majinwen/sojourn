package com.ziroom.minsu.services.cms.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.cms.ActivityGiftEntity;
import com.ziroom.minsu.services.cms.dto.ActivityGiftRequest;

/**
 * 
 * <p>活动礼物 持久化层</p>
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

@Repository("cms.activityGiftDao")
public class ActivityGiftDao {

	
	 /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(ActivityGiftDao.class);

    private String SQLID = "cms.activityGiftDao.";

    @Autowired
    @Qualifier("cms.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /**
     * 获取当前的礼物对象
     * @author afi
     * @param fid
     * @return
     */
    public ActivityGiftEntity getGiftByFid(String fid){
        if (Check.NuNObj(fid)){
            return null;
        }
        return mybatisDaoContext.findOneSlave(SQLID + "getGiftByFid", ActivityGiftEntity.class,fid);

    }

    /**
     * 获取当前房东参加的免佣金活动
     * @author afi
     * @param landUid
     * @return
     */
    public ActivityGiftEntity getLanFreeCommAc(String landUid){
        if (Check.NuNObj(landUid)){
            return null;
        }
        return mybatisDaoContext.findOneSlave(SQLID + "getLanFreeCommAc", ActivityGiftEntity.class,landUid);
    }

    
    /**
     * 
     * 保存礼物
     *
     * @author yd
     * @created 2016年10月9日 上午10:14:52
     *
     * @param activityGift
     * @return
     */
    public int saveGift(ActivityGiftEntity activityGift){
    	
    	if(!Check.NuNObj(activityGift)){
    		if(Check.NuNStr(activityGift.getFid())) activityGift.setFid(UUIDGenerator.hexUUID());
    		
    		return this.mybatisDaoContext.save(SQLID+"insertSelective", activityGift);
    	}
    	return 0;
    }
    
    /**
     * 
     * 分页查询 活动礼物
     *
     * @author yd
     * @created 2016年10月9日 上午10:45:32
     *
     * @param activityGift
     * @return
     */
    public PagingResult<ActivityGiftEntity> queryActivityGifyByPage(ActivityGiftRequest activityGift){
    	
    	   PageBounds pageBounds = new PageBounds();
           pageBounds.setLimit(activityGift.getLimit());
           pageBounds.setPage(activityGift.getPage());
           return mybatisDaoContext.findForPage(SQLID + "queryActivityGifyByCon", ActivityGiftEntity.class, activityGift, pageBounds);
    }

	/**
	 * 修改活动礼品信息
	 *
	 * @author liujun
	 * @created 2016年10月17日
	 *
	 * @param activityGiftEntity
	 * @return
	 */
	public int updateActivityGiftEntityByFid(ActivityGiftEntity activityGiftEntity) {
		return mybatisDaoContext.update(SQLID + "updateActivityGiftEntityByFid", activityGiftEntity);
	}
    
    
    
}
