package com.ziroom.minsu.services.cms.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.PointTiersEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *  积分阶梯dao
 * @author yanb
 * @created 2017年12月05日 18:14:31
 */
@Repository("cms.pointTiersDao")
public class PointTiersDao {

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(ActCouponDao.class);

    private String SQLID="cms.pointTiersDao.";

    @Autowired
    @Qualifier("cms.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 保存积分阶梯
     * @author yanb
     * @created 2017年12月05日 21:52:01
     * @param pointTiers
     * @return
     */
    public void insertPointTiers(PointTiersEntity pointTiers) {
        if (Check.NuNObj(pointTiers)) {
            LogUtil.error(logger, "参数为空");
            throw new BusinessException("参数为空");
        }
        mybatisDaoContext.save(SQLID + "insertPointTiers", pointTiers);
    }

    /**
     * 根据fid更新积分阶梯
     * @author yanb
     * @created 2017年12月05日 21:52:54
     * @param  * @param pointTiers
     * @return int
     */
    public int updatePointTiersByFid(PointTiersEntity pointTiers) {
        if (Check.NuNObj(pointTiers)) {
            LogUtil.error(logger, "参数为空");
            throw new BusinessException("参数为空");
        }
        return mybatisDaoContext.update(SQLID + "updatePointTiersByFid", pointTiers);
    }

    /**
	 * 
	 * 根据总人数和活动类别查询积分档位
	 * @author loushuai
	 * @created 2017年12月7日 下午3:16:24
	 *
	 * @param map
	 * @return
	 */
    public PointTiersEntity getPointTiersByParam(Map<String, Object> map){
        if (Check.NuNObj(map)) {
            LogUtil.error(logger, "参数为空");
            throw new BusinessException("参数为空");
        }
        return mybatisDaoContext.findOne(SQLID+"getPointTiersByParam", PointTiersEntity.class, map);
    }



}