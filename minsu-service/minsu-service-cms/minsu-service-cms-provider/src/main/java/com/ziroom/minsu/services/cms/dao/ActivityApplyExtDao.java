package com.ziroom.minsu.services.cms.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActivityApplyExtEntity;

/**
 * <p>活动申请扩展Dao</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年6月29日
 * @since 1.0
 * @version 1.0
 */
@Repository("cms.activityApplyExtDao")
public class ActivityApplyExtDao {
	
	/**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(ActivityApplyDao.class);

    private String SQLID = "cms.activityApplyExtDao.";

    @Autowired
    @Qualifier("cms.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    
    /**
     * 保存活动扩展信息
     * @author lishaochuan
     * @create 2016年6月29日下午3:55:18
     * @param applyExt
     * @return
     */
    public int save(ActivityApplyExtEntity applyExt) {
		if (Check.NuNObj(applyExt)) {
			LogUtil.error(LOGGER, "参数为空");
			throw new BusinessException("参数为空");
		}
		if (Check.NuNStr(applyExt.getFid())) {
			applyExt.setFid(UUIDGenerator.hexUUID());
		}
		// 直接保存优惠券信息
		return mybatisDaoContext.save(SQLID + "save", applyExt);
	}

}
