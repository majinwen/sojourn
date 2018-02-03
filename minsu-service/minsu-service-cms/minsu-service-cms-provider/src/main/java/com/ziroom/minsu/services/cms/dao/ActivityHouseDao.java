package com.ziroom.minsu.services.cms.dao;

import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.cms.ActivityHouseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>活动房源限制</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
@Repository("cms.activityHouseDao")
public class ActivityHouseDao {
    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(ActivityHouseDao.class);

    private String SQLID = "cms.activityHouseDao.";

    @Autowired
    @Qualifier("cms.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 保存活动房源限制关系
     * @author jixd
     * @created 2016年11月18日 11:20:49
     * @param activityHouseEntity
     * @return
     */
    public int saveActivityHouse(ActivityHouseEntity activityHouseEntity){
       return mybatisDaoContext.save(SQLID + "saveActivityHouse",activityHouseEntity);
    }

    /**
     * 根据活动编号查询限制的房源
     * @author jixd
     * @created 2016年11月18日 11:25:08
     * @param
     * @return
     */
    public List<ActivityHouseEntity> findHouseByActsn(String actSn){
        return mybatisDaoContext.findAll(SQLID + "findHouseByActsn",ActivityHouseEntity.class,actSn);
    }
}
