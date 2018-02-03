package com.ziroom.minsu.services.cms.service;

import com.ziroom.minsu.entity.cms.ActivityChristmasApplyEntity;
import com.ziroom.minsu.services.cms.dao.ActivityChristmasApplyDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/12/9 11:33
 * @version 1.0
 * @since 1.0
 */
@Service("cms.activityChristmasApplyImpl")
public class ActivityChristmasApplyImpl {

    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(ActivityApplyServiceImpl.class);

    @Resource(name = "cms.activityChristmasApplyDao")
    private ActivityChristmasApplyDao activityChristmasApplyDao;


    /**
     * 根据条件查询圣诞活动报名信息
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2016/12/9 11:34
     */
    public ActivityChristmasApplyEntity selectByCondition(ActivityChristmasApplyEntity christmasApply) {
        return activityChristmasApplyDao.selectByCondition(christmasApply);
    }


    /**
     * 保存圣诞活动报名信息
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2016/12/9 11:35
     */
    public int save(ActivityChristmasApplyEntity christmasApply) {
        return activityChristmasApplyDao.save(christmasApply);
    }
}
