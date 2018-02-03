package com.ziroom.minsu.services.cms.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActivityApplyEntity;
import com.ziroom.minsu.services.cms.dto.LanApplayRequest;
import com.ziroom.minsu.services.cms.entity.ActivityApplyVo;

/**
 * <p>活动申请Dao</p>
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
@Repository("cms.activityApplyDao")
public class ActivityApplyDao {
	
	
	/**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(ActivityApplyDao.class);

    private String SQLID = "cms.activityApplyDao.";

    @Autowired
    @Qualifier("cms.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    
    
    /**
     * 根据手机号查询申请信息
     * @author lishaochuan
     * @create 2016年6月30日上午10:12:25
     * @param mobile
     * @return
     */
    public ActivityApplyEntity getApplyByMobile(String mobile){
    	if (Check.NuNStr(mobile)) {
    		LogUtil.error(LOGGER, "参数为空");
			throw new BusinessException("参数为空");
		}
    	return mybatisDaoContext.findOne(SQLID + "selectByMoblie", ActivityApplyEntity.class, mobile);
    }
    
    
    /**
     * 保存活动申请信息
     * @author lishaochuan
     * @create 2016年6月29日下午3:24:48
     * @param apply
     * @return
     */
	public int save(ActivityApplyEntity apply) {
		if (Check.NuNObj(apply)) {
			LogUtil.error(LOGGER, "参数为空");
			throw new BusinessException("参数为空");
		}
		if (Check.NuNStr(apply.getFid())) {
			apply.setFid(UUIDGenerator.hexUUID());
		}
		return mybatisDaoContext.save(SQLID + "save", apply);
	}

	
	/**
     * 
     * 获取申请列表
     * @author liyingjie
     * @created 2016年6月25日 下午6:47:58
     * @param request
     * @return
     */
    public PagingResult<ActivityApplyVo> getApplayList(LanApplayRequest request){
    	if(Check.NuNObj(request)){
    		LogUtil.info(LOGGER, "getApplayList 参数对象为空");
			throw new BusinessException("getApplayList 参数对象为空");
    	}
    	PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(request.getLimit());
		pageBounds.setPage(request.getPage());
		return mybatisDaoContext.findForPage(SQLID + "findApplayExtVo", ActivityApplyVo.class, request, pageBounds);
    }
    
    /**
	 * 查询申请详情
	 * @author zl
	 * @param applyFid
	 * @return
	 */
    public ActivityApplyVo getApplyDetailWithBLOBs(String applyFid){
    	if (Check.NuNStr(applyFid)) {
    		LogUtil.info(LOGGER, "参数为空");
			throw new BusinessException("参数为空");
		}
    	return mybatisDaoContext.findOne(SQLID + "getApplyDetailWithBLOBs", ActivityApplyVo.class, applyFid);
    }
	
	
}
