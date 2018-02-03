package com.ziroom.minsu.services.cms.service;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.*;
import com.ziroom.minsu.services.cms.dao.ActivityCityDao;
import com.ziroom.minsu.services.cms.dao.ActivityDao;
import com.ziroom.minsu.services.cms.dao.ActivityFullDao;
import com.ziroom.minsu.services.cms.dto.ActCouponRequest;
import com.ziroom.minsu.services.cms.dto.ActivityInfoRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>优惠券活动的service</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author afi
 * @since 1.0
 * @version 1.0
 */
@Service("cms.activityFullServiceImpl")
public class ActivityFullServiceImpl {

	/**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(ActivityFullServiceImpl.class);
	
    @Resource(name = "cms.activityFullDao")
    private ActivityFullDao activityFullDao;



    /**
     * 根据活动编号获取优惠券活动信息
     * @author afi
     * @param actSn
     * @return
     */
    public ActivityFullEntity getActivityFullBySn(String actSn) {
        if (Check.NuNStr(actSn)) {
            LogUtil.error(LOGGER, "参数为空,actSn:{}", actSn);
            throw new BusinessException("actSn参数为空");
        }
        return activityFullDao.getActivityFullBySn(actSn);
    }

    /**
     * 根据活动组获取活动信息
     * @author jixd
     * @created 2017年06月15日 16:52:02
     * @param
     * @return
     */
    public List<ActivityFullEntity> getActivityFullByGroupSn(String groupSn) {
        return activityFullDao.getActivityFullByGroupSn(groupSn);
    }

    
}
