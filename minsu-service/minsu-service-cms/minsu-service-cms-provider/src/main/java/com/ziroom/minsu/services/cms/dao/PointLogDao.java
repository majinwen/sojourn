package com.ziroom.minsu.services.cms.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.PointLogEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
/**
 *
 * @author yanb
 * @created 2017年12月05日 18:14:31
 */
@Repository("cms.pointLogDao")
public class PointLogDao {

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(ActCouponDao.class);

    private String SQLID="cms.pointLogDao.";

    @Autowired
    @Qualifier("cms.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    public int insertPointLog(PointLogEntity pointLog) {
        if(Check.NuNObj(pointLog)){
            LogUtil.error(logger,"参数为空");
            throw new BusinessException("参数为空");
        }
        return mybatisDaoContext.save(SQLID + "insertPointLog", pointLog);
    }

    public int updatePointLogByFid(PointLogEntity pointLog){
        if(Check.NuNObj(pointLog)){
            LogUtil.error(logger,"参数为空");
            throw new BusinessException("参数为空");
        }
        if(Check.NuNStr(pointLog.getFid())){
            LogUtil.error(logger,"积分记录fid为空");
            throw new BusinessException("fid为空");
        }
        return mybatisDaoContext.update(SQLID + "updatePointLogByFid", pointLog);
    }
}