
package com.ziroom.minsu.services.cms.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActivityExtCouponEntity;
import com.ziroom.minsu.services.cms.dao.ActivityExtCouponDao;

/**
 * <p>活动信息service</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年6月23日
 * @since 1.0
 * @version 1.0
 */
@Service("cms.actCouponExtServiceImpl")
public class ActCouponExtServiceImpl {

	/**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(ActCouponExtServiceImpl.class);
	
    @Resource(name = "cms.activityExtCouponDao")
    private ActivityExtCouponDao activityExtCouponDao;


    /**
     * 保存优惠券活动信息
     * @author liyingjie
     * @create 2016年6月23日下午3:15:08
     * @param activityEntity
     * @return
     */
    public int saveActivityInfo(ActivityExtCouponEntity activityEntity){
    	if(Check.NuNStr(activityEntity.getActSn())){
    		activityEntity.setActSn(UUIDGenerator.hexUUID());
    	}
        return activityExtCouponDao.insertActivityExtCoupon(activityEntity);
    }
    /**
     * 修改 优惠券 部分信息(这次主要修改了优惠券数量coupon_num)
     * @author xiangb
     * @create 2017年8月16日19:42:34
     * @param ActivityExtCouponEntity
     * @return
     */
    public void updateActivityInfo(ActivityExtCouponEntity activityEntity){
    	
        activityExtCouponDao.updateActivityExtCoupon(activityEntity);
    }
}



