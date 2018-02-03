package com.ziroom.minsu.services.cms.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.cms.ActivityFreeEntity;
import com.ziroom.minsu.entity.cms.ActivityGiftEntity;
import com.ziroom.minsu.services.cms.dao.ActivityFreeDao;
import com.ziroom.minsu.services.cms.dao.ActivityGiftDao;
import com.ziroom.minsu.services.cms.dto.ActivityGiftRequest;

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

@Service("cms.activityGiftServiceImpl")
public class ActivityGiftServiceImpl {

	/**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(ActivityGiftServiceImpl.class);
	
    @Resource(name = "cms.activityGiftDao")
    private  ActivityGiftDao  activityGiftDao;

    @Resource(name = "cms.activityFreeDao")
    private  ActivityFreeDao  activityFreeDao;
    /**
     * 获取当前房东参加的免佣金活动
     * @author afi
     * @param landUid
     * @return
     */
    public ActivityGiftEntity getLanFreeCommAc(String landUid){
        if (Check.NuNStr(landUid)){
            return null;
        }
        return activityGiftDao.getLanFreeCommAc(landUid);
    }

    /**
     * 获取当前的礼物对象
     * @author afi
     * @param fid
     * @return
     */
    public ActivityGiftEntity getGiftByFid(String fid){
        if (Check.NuNStr(fid)){
            return null;
        }
        return activityGiftDao.getGiftByFid(fid);
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
        return activityGiftDao.queryActivityGifyByPage(activityGift);
    }

	/**
	 * 插入活动礼品信息
	 *
	 * @author liujun
	 * @created 2016年10月17日
	 *
	 * @param activityGiftEntity
	 * @return
	 */
	public int insertActivityGiftEntity(ActivityGiftEntity activityGiftEntity) {
		activityGiftEntity.setFid(UUIDGenerator.hexUUID());
		return activityGiftDao.saveGift(activityGiftEntity);
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
		return activityGiftDao.updateActivityGiftEntityByFid(activityGiftEntity);
	}

	/**
	 *  修改用户免佣金实体
	 *
	 * @author loushuai
	 * @created 2017年5月17日 下午8:13:43
	 *
	 * @param uid
	 * @return
	 */
	public int cancelFreeCommission(ActivityFreeEntity activityFreeEntity) {
		return activityFreeDao.updateByUid(activityFreeEntity);
	}

	/**
	 * 
	 * 获取免佣金实体
	 * @author loushuai
	 * @created 2017年5月20日 下午5:31:53
	 *
	 * @param activityFreeEntity
	 * @return
	 */
	public ActivityFreeEntity getFreeCommissionByUid(ActivityFreeEntity activityFreeEntity) {
		return activityFreeDao.getByUid(activityFreeEntity.getUid());
	}
    
}
