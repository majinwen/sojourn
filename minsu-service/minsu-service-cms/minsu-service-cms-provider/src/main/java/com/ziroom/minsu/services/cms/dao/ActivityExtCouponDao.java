package com.ziroom.minsu.services.cms.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActivityExtCouponEntity;

/**
 * <p>优惠券活动</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年6月8日
 * @since 1.0
 * @version 1.0
 */
@Repository("cms.activityExtCouponDao")
public class ActivityExtCouponDao {
	
	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(ActivityExtCouponDao.class);

	private String SQLID = "cms.activityExtCouponDao.";

	@Autowired
	@Qualifier("cms.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	
	/**
	 * 保存活动 优惠券 部分信息
	 * @author liyingjie
	 * @create 2016年7月13日下午7:03:30
	 * @param actCoupon
	 * @return
	 */
	public int insertActivityExtCoupon(ActivityExtCouponEntity actCoupon){
		if(Check.NuNObj(actCoupon)){
			LogUtil.error(LOGGER, "优惠券活动信息实体为空");
			throw new BusinessException("优惠券活动信息实体为空");
		}
		if(Check.NuNStr(actCoupon.getActSn())){
			LogUtil.error(LOGGER, "活动码为空");
			throw new BusinessException("活动码为空");
		}
		return mybatisDaoContext.save(SQLID + "insertActExtCoupon", actCoupon);
	}
	/**
	 * 通过优惠券码查询优惠券信息
	 * @author xiangbin
	 * @create 2017年8月22日12:09:23
	 * @param actCoupon
	 * @return
	 */
	public ActivityExtCouponEntity selectActExtCouponByActSn(String actSn){
		if(Check.NuNObj(actSn)){
			LogUtil.error(LOGGER, "优惠券活动码为空");
			throw new BusinessException("优惠券活动码为空");
		}
		
		return mybatisDaoContext.findOne(SQLID + "selectByActSn", ActivityExtCouponEntity.class,actSn);
	}
	
    /**
     * 修改 优惠券 部分信息
     * @author afi
     * @create 2016年7月13日下午7:03:30
     * @param actCoupon
     * @return
     */
    public int updateActivityExtCoupon(ActivityExtCouponEntity actCoupon){
        if(Check.NuNObj(actCoupon)){
            LogUtil.error(LOGGER, "优惠券活动信息实体为空");
            throw new BusinessException("优惠券活动信息实体为空");
        }
        if(Check.NuNStr(actCoupon.getActSn())){
            LogUtil.error(LOGGER, "活动码为空");
            throw new BusinessException("活动码为空");
        }
        return mybatisDaoContext.save(SQLID + "updateActivityExtCoupon", actCoupon);
    }




	/**
	 * 修改优惠券活动信息
	 * @author lishaochuan
	 * @create 2016年6月8日下午7:08:54
	 * @param actCoupon
	 * @return
	 *//*
	public int updateActCouponInfo(ActCouponInfoEntity actCoupon){
		if(Check.NuNObj(actCoupon)){
			LogUtil.error(LOGGER, "优惠券活动信息实体为空");
			throw new BusinessException("优惠券活动信息实体为空");
		}
		if(Check.NuNStr(actCoupon.getActSn())){
			LogUtil.error(LOGGER, "优惠码为空");
			throw new BusinessException("优惠码为空");
		}
		return mybatisDaoContext.update(SQLID + "updateActCouponInfo", actCoupon);
	}
	
	
	*//**
	 * 根据活动码获取优惠券活动Entity信息
	 * @author lishaochuan
	 * @create 2016年6月22日下午3:14:44
	 * @param actSn
	 * @return
	 *//*
	public ActCouponInfoEntity getActCouponInfoByActSn(String actSn){
		if(Check.NuNStr(actSn)){
			LogUtil.error(LOGGER, "活动码为空");
			throw new BusinessException("活动码为空");
		}
		return mybatisDaoContext.findOne(SQLID + "getActCouponInfoByActSn", ActCouponInfoEntity.class, actSn);
	}
	
	
	*//**
	 * 根据活动码获取优惠券活动Vo信息
	 * @author lishaochuan
	 * @create 2016年6月8日下午7:27:05
	 * @param actSn
	 * @return
	 *//*
	public ActCouponInfoVo getActCouponInfoVoByActSn(String actSn){
		if(Check.NuNStr(actSn)){
			LogUtil.error(LOGGER, "活动码为空");
			throw new BusinessException("活动码为空");
		}
		return mybatisDaoContext.findOne(SQLID + "getActCouponInfoVoByActSn", ActCouponInfoVo.class, actSn);
	}
	
	
	*//**
	 * 分页查询优惠券活动信息
	 * @author lishaochuan
	 * @create 2016年6月8日下午7:30:36
	 * @param request
	 * @return
	 *//*
	public PagingResult<ActCouponInfoVo> getActCouponInfoVoListByCondiction(ActCouponInfoRequest request){
    	PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(request.getLimit());
		pageBounds.setPage(request.getPage());
    	return mybatisDaoContext.findForPage(SQLID + "getActCouponInfoVoListByCondiction", ActCouponInfoVo.class, request, pageBounds);
    }
	
	*//**
	 * 获取该到进行中的活动count
	 * @author lishaochuan
	 * @create 2016年6月22日下午6:59:22
	 * @return
	 *//*
	public Long getActingCount(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("actStatus", ActStatusEnum.DISABLE.getCode());
		return mybatisDaoContext.findOne(SQLID + "getActingCount", Long.class, map);
    }
	
	*//**
	 * 修改到进行中的活动status
	 * @author lishaochuan
	 * @create 2016年6月22日下午7:00:22
	 * @param limit
	 * @return
	 *//*
	public int updateActingStatus(int limit){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("actStatus", ActStatusEnum.UNDERWAY.getCode());
		map.put("oldStatus", ActStatusEnum.DISABLE.getCode());
		map.put("limit", limit);
		return mybatisDaoContext.update(SQLID + "updateActingStatus", map);
	}
	
	*//**
	 * 获取到结束时间的活动count
	 * @author lishaochuan
	 * @create 2016年6月16日下午5:36:59
	 * @return
	 *//*
	public Long getActEndCount(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("actStatus", ActStatusEnum.END.getCode());
		return mybatisDaoContext.findOne(SQLID + "getActEndCount", Long.class, map);
    }
	
	*//**
	 * 修改到结束时间的活动status
	 * @author lishaochuan
	 * @create 2016年6月16日下午5:50:04
	 * @param limit
	 *//*
	public int updateActEndStatus(int limit){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("actStatus", ActStatusEnum.END.getCode());
		map.put("limit", limit);
		return mybatisDaoContext.update(SQLID + "updateActEndStatus", map);
	}*/
	
}
