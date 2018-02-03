package com.ziroom.minsu.services.cms.service;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActivityFreeEntity;
import com.ziroom.minsu.services.cms.dao.ActivityFreeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>免佣金的逻辑</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/10/20.
 * @version 1.0
 * @since 1.0
 */
@Service("cms.activityFreeServiceImpl")
public class ActivityFreeServiceImpl {


    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(ActivityServiceImpl.class);

    @Resource(name = "cms.activityFreeDao")
    private ActivityFreeDao activityFreeDao;




    /**
     * 根据uid获取将来的最远的免佣金的逻辑
     * @author afi
     * @param uid
     * @return
     */
    public ActivityFreeEntity getLastByUidFuther(String uid) {
        return activityFreeDao.getLastByUidFuther(uid);
    }

    /**
     * 根据uid获取当前的免佣金的信息
     * @author afi
     * @param uid
     * @return
     */
    public ActivityFreeEntity getByUidAndCode(String uid,String actCode) {
        return activityFreeDao.getByUidAndCode(uid,actCode);
    }

    /**
     * 保存用户免佣金实体
     * @author afi
     * @param activityFree
     * @return
     */
    public int saveFreeComm(ActivityFreeEntity activityFree) {
        if (Check.NuNObj(activityFree)) {
            LogUtil.error(LOGGER, "activityFree参数为空");
            throw new BusinessException("activityFree参数为空");
        }
        return activityFreeDao.save(activityFree);
    }
}
