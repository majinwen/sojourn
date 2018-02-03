package com.ziroom.minsu.services.cms.service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.cms.ActivityRemindLogEntity;
import com.ziroom.minsu.services.cms.dao.ActivityRemindLogDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>活动提醒信息记录逻辑</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp on on 2017/6/6.
 * @version 1.0
 * @since 1.0
 */
@Service("cms.activityRemindLogServiceImpl")
public class ActivityRemindLogServiceImpl {


    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(ActivityRemindLogServiceImpl.class);

    @Resource(name="cms.activityRemindLogDao")
    private ActivityRemindLogDao activityRemindLogDao;

    /**
     * @Description: 插入活动提醒信息记录表（不重复插入）
     * @Author: lusp
     * @Date: 2017/6/6 16:27
     * @Params:
     */
    public int insertActivityRemindLogIgnore(ActivityRemindLogEntity activityRemindLogEntity){
        if(Check.NuNStr(activityRemindLogEntity.getFid())){
            activityRemindLogEntity.setFid(UUIDGenerator.hexUUID());
        }
        return activityRemindLogDao.insertActivityRemindLogIgnore(activityRemindLogEntity);
    }

    /**
     * @Description: 根据用户uid 逻辑删除一条记录
     * @Author: lusp
     * @Date: 2017/6/6 16:34
     * @Params:
     */
    public int deleteActivityRemindLogByUid(ActivityRemindLogEntity activityRemindLogEntity){
        return activityRemindLogDao.deleteActivityRemindLogByUid(activityRemindLogEntity);
    }

    /**
     * @Description: 分页查询已经触发通知的新用户信息
     * @Author:lusp
     * @Date: 2017/6/7 11:34
     * @Params: paramMap
     */
    public PagingResult<ActivityRemindLogEntity> queryRemindUidInfoByPage(Map<String, Object> paramMap){
        return activityRemindLogDao.queryRemindUidInfoByPage(paramMap);
    }

    public int updateSendTimesRunTimeByUid(ActivityRemindLogEntity activityRemindLogEntity){
        return activityRemindLogDao.updateSendTimesRunTimeByUid(activityRemindLogEntity);
    }




}
