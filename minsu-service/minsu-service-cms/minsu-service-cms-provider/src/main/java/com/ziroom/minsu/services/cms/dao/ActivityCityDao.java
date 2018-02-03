package com.ziroom.minsu.services.cms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActivityCityEntity;

/**
 * <p>活动城市</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/7/13.
 * @version 1.0
 * @since 1.0
 */
@Repository("cms.activityCityDao")
public class ActivityCityDao {


    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(ActivityDao.class);

    private String SQLID = "cms.activityCityDao.";

    @Autowired
    @Qualifier("cms.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /**
     * 当前活动开通的城市
     * @author lishaochuan
     * @create 2016年6月23日下午8:08:26
     * @return
     */
    public List<ActivityCityEntity> getActivityCitiesByActSn(String actSn){
        if (Check.NuNStr(actSn)){
            return null;
        }
        return mybatisDaoContext.findAll(SQLID + "getActivityCitiesByActSn", ActivityCityEntity.class, actSn);
    }

    /**
     * 保存活动开通的城市
     * @author afi
     * @create 2016年6月23日下午2:57:42
     * @param activityCityEntity
     * @return
     */
    public int saveActivityCity(ActivityCityEntity activityCityEntity){
        if(Check.NuNObj(activityCityEntity)){
            LogUtil.error(LOGGER,"activityCityEntity参数为空");
            throw new BusinessException("activityCityEntity参数为空");
        }
        if(Check.NuNStr(activityCityEntity.getActSn())){
            LogUtil.error(LOGGER,"actSn参数为空");
            throw new BusinessException("actSn参数为空");
        }
        if(Check.NuNStr(activityCityEntity.getCityCode())){
            LogUtil.error(LOGGER,"城市不能为空");
            throw new BusinessException("城市不能为空");
        }

        return mybatisDaoContext.save(SQLID + "saveActivityCity", activityCityEntity);
    }


    /**
     * 删除活动
     * @author afi
     * @param actSn
     * @return
     */
    public int deleteByActSn(String actSn){
        if(Check.NuNObj(actSn)){
            LogUtil.error(LOGGER,"actSn参数为空");
            throw new BusinessException("actSn参数为空");
        }
        Map<String,Object> par = new HashMap<>();
        par.put("actSn",actSn);
        return mybatisDaoContext.delete(SQLID + "deleteByActSn", par);
    }

}
