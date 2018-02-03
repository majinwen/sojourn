package com.ziroom.minsu.services.cms.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActivityFreeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>免佣金表dao</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/10/11 14:36
 * @version 1.0
 * @since 1.0
 */
@Repository("cms.activityFreeDao")
public class ActivityFreeDao {


    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(ActivityFreeDao.class);

    private String SQLID = "cms.activityFreeDao.";

    @Autowired
    @Qualifier("cms.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /**
     * 根据uid获取当前的免佣金的信息
     * @author afi
     * @param uid
     * @return
     */
    public ActivityFreeEntity getByUidAndCode(String uid,String actCode){

        if (Check.NuNStr(uid)) {
            LogUtil.error(LOGGER, "参数为空,uid:{}", uid);
            throw new BusinessException("uid参数为空");
        }
        if (Check.NuNStr(actCode)) {
            LogUtil.error(LOGGER, "参数为空,actCode:{}", actCode);
            throw new BusinessException("actCode参数为空");
        }
        Map<String,Object> par = new HashMap<>();
        par.put("actCode",actCode);
        par.put("uid",uid);
        return mybatisDaoContext.findOne(SQLID + "getByUidAndCode", ActivityFreeEntity.class, par);

    }

    /**
     * 根据uid获取当前的免佣金的最后时限
     * @author 爱抚
     * @param uid
     * @return
     */
    public ActivityFreeEntity getLastByUidFuther(String uid){
        if (Check.NuNStr(uid)) {
            LogUtil.error(LOGGER, "参数为空,uid:{}", uid);
            throw new BusinessException("uid参数为空");
        }
        return mybatisDaoContext.findOne(SQLID + "getLastByUidFuther", ActivityFreeEntity.class, uid);
    }

    /**
     * 根据uid获取五周年的免佣金活动
     * @author 爱抚
     * @param uid
     * @return
     */
    public ActivityFreeEntity getFive(String uid){
        if (Check.NuNStr(uid)) {
            LogUtil.error(LOGGER, "参数为空,uid:{}", uid);
            throw new BusinessException("uid参数为空");
        }
        return mybatisDaoContext.findOne(SQLID + "getFive", ActivityFreeEntity.class, uid);
    }

    /**
     * 根据uid获取免佣金实体
     * @author afi
     * @param uid
     * @param freeType
     * @return
     */
    public ActivityFreeEntity getByUidAndType(String uid,int freeType){
        if (Check.NuNStr(uid)) {
            LogUtil.error(LOGGER, "参数为空,uid:{}", uid);
            throw new BusinessException("uid参数为空");
        }
        Map<String,Object> par = new HashMap<>();
        par.put("freeType",freeType);
        par.put("uid",uid);
        return mybatisDaoContext.findOne(SQLID + "getByUidAndType", ActivityFreeEntity.class, par);
    }


    /**
     * 根据uid获取免佣金实体
     * @author lisc
     * @param uid
     * @return
     */
    public ActivityFreeEntity getByUid(String uid) {
        if (Check.NuNStr(uid)) {
            LogUtil.error(LOGGER, "参数为空,uid:{}", uid);
            throw new BusinessException("uid参数为空");
        }
        return mybatisDaoContext.findOne(SQLID + "getByUid", ActivityFreeEntity.class, uid);
    }

    /**
     * 保存用户免佣金实体
     * @author lisc
     * @param activityFree
     * @return
     */
    public int save(ActivityFreeEntity activityFree) {
        if (Check.NuNObj(activityFree)) {
            LogUtil.error(LOGGER, "activityFree参数为空");
            throw new BusinessException("activityFree参数为空");
        }
        activityFree.setFid(UUIDGenerator.hexUUID());
        return mybatisDaoContext.save(SQLID + "save", activityFree);
    }


    /**
     * 修改用户免佣金实体
     * @author lisc
     * @param activityFree
     * @return
     */
    public int updateByUid(ActivityFreeEntity activityFree) {
        if (Check.NuNObjs(activityFree, activityFree.getUid())) {
            LogUtil.error(LOGGER, "activityFree参数为空");
            throw new BusinessException("activityFree参数为空");
        }
        return mybatisDaoContext.update(SQLID + "updateByUid", activityFree);
    }
}
