package com.ziroom.minsu.services.cms.service;

import com.ziroom.minsu.entity.cms.ActivityHouseEntity;
import com.ziroom.minsu.services.cms.dao.ActivityHouseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>活动限制房源</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @since 1.0
 */
@Service("cms.activityHouseServiceImpl")
public class ActivityHouseServiceImpl {
    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(ActivityHouseServiceImpl.class);

    @Resource(name = "cms.activityHouseDao")
    private ActivityHouseDao activityHouseDao;

    /**
     * 保存活动房源关联
     * @author jixd
     * @created 2016年11月18日 11:31:06
     * @param
     * @return
     */
    public int saveActivityHouse(ActivityHouseEntity activityHouseEntity){
        return activityHouseDao.saveActivityHouse(activityHouseEntity);
    }

    /**
     * 查找活动限制的房源
     * @author jixd
     * @created 2016年11月18日 11:32:03
     * @param
     * @return
     */
    public List<ActivityHouseEntity> findHouseByActsn(String actSn){
        return activityHouseDao.findHouseByActsn(actSn);
    }

}
