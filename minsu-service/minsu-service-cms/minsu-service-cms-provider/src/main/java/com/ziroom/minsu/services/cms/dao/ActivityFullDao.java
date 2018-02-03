package com.ziroom.minsu.services.cms.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActivityFullEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>比较全的活动信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/7/14.
 * @version 1.0
 * @since 1.0
 */
@Repository("cms.activityFullDao")
public class ActivityFullDao {


    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(ActivityDao.class);

    private String SQLID = "cms.activityFullDao.";

    @Autowired
    @Qualifier("cms.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


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
        return mybatisDaoContext.findOne(SQLID + "getActivityFullBySn", ActivityFullEntity.class, actSn);
    }

    /**
     * 根据活动号获取活动完整信息
     * @author jixd
     * @created 2017年06月15日 16:51:08
     * @param
     * @return
     */
    public List<ActivityFullEntity> getActivityFullByGroupSn(String groupSn){
        return mybatisDaoContext.findAll(SQLID + "getActivityFullByGroupSn",groupSn);
    }



}
