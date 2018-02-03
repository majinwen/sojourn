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
import com.ziroom.minsu.entity.cms.ActivityApplyDescEntity;
import com.ziroom.minsu.entity.cms.ActivityApplyExtEntity;

/**
 * <p>活动申请描述表Dao</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author zl
 * @since 1.0
 * @version 1.0
 */
@Repository("cms.ActivityApplyDescDao")
public class ActivityApplyDescDao {
	
	/**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(ActivityApplyDescDao.class);

    private String SQLID = "cms.ActivityApplyDescDao.";

    @Autowired
    @Qualifier("cms.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    
    /**
     * 保存活动描述信息
     * @author zl 
     * @param applyExt
     * @return
     */
    public int save(ActivityApplyDescEntity entity) {
		if (Check.NuNObj(entity)) {
			LogUtil.error(LOGGER, "参数为空");
			throw new BusinessException("参数为空");
		}
		if (Check.NuNStr(entity.getFid())) {
			entity.setFid(UUIDGenerator.hexUUID());
		}
		return mybatisDaoContext.save(SQLID + "insertSelective", entity);
	}

}
