package com.ziroom.minsu.services.cms.dao;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.cms.ActivityApplyEntity;
import com.ziroom.minsu.entity.cms.ActivityChristmasApplyEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/12/9 10:30
 * @version 1.0
 * @since 1.0
 */
@Repository("cms.activityChristmasApplyDao")
public class ActivityChristmasApplyDao {

    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(ActivityChristmasApplyDao.class);

    private String SQLID = "cms.activityChristmasApplyDao.";

    @Autowired
    @Qualifier("cms.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /**
     * 根据条件查询圣诞活动报名信息
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2016/12/9 11:21
     */
    public ActivityChristmasApplyEntity selectByCondition(ActivityChristmasApplyEntity christmasApply) {
        return mybatisDaoContext.findOne(SQLID + "selectByCondition", ActivityChristmasApplyEntity.class, christmasApply.toMap());
    }


    /**
     * 保存圣诞活动报名信息
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2016/12/9 10:56
     */
    public int save(ActivityChristmasApplyEntity christmasApply) {
        if (Check.NuNStr(christmasApply.getFid())) {
            christmasApply.setFid(UUIDGenerator.hexUUID());
        }
        return mybatisDaoContext.save(SQLID + "save", christmasApply);
    }
}
