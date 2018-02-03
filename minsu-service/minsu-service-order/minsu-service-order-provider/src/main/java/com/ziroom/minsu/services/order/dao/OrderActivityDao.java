package com.ziroom.minsu.services.order.dao;


import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderActivityEntity;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.finance.entity.OrderActivityInfoVo;
import com.ziroom.minsu.valenum.order.CouponStatusEnum;
import com.ziroom.minsu.valenum.order.OrderAcTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单 活动 信息
 * </p>
 * <p/>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liyingjie on 2016/4/1.
 * @version 1.0
 * @since 1.0
 */
@Repository("order.activityDao")
public class OrderActivityDao {
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(OrderActivityDao.class);
	private String SQLID = "order.activityDao.";

	@Autowired
	@Qualifier("order.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;



    /**
     * 验证当前优惠券在订单是否可用
     * @author afi
     * @param couponSn
     * @return
     */
    public boolean checkCouponOk(String couponSn){
        if (Check.NuNStr(couponSn)){
            return false;
        }
        Map<String,Object> par = new HashMap<>();
        par.put("couponSn", couponSn);
        Long count = mybatisDaoContext.count(SQLID + "checkCoupon", par);
        return count == 0;
    }

	/**
	 * 查询未同步优惠券状态的count
	 * @author lishaochuan
	 * @create 2016年6月15日下午8:43:13
	 * @return
	 */
	public Long getNotSyncCount(Map<String, Object> map){
		return mybatisDaoContext.findOne(SQLID + "getNotSyncCount", Long.class, map);
    }
	
	/**
	 * 查询未同步优惠券状态的list
	 * @author lishaochuan
	 * @create 2016年6月15日下午8:43:27
	 * @param map
	 * @return
	 */
	public List<OrderActivityEntity> getNotSyncList(Map<String, Object> map){
        return mybatisDaoContext.findAll(SQLID + "getNotSyncList", OrderActivityEntity.class, map);
    }
	
	/**
	 *
	 * 插入资源记录
	 *
	 * @author liyingjie
	 * @created 2016年4月1日 
	 *
	 * @param activityEntity
	 */
	public void insertActivityRes(OrderActivityEntity activityEntity) {
		if(Check.NuNObj(activityEntity) || Check.NuNStr(activityEntity.getOrderSn()) || Check.NuNStr(activityEntity.getAcFid())){
        	LogUtil.info(logger, "activityEntity  insertActivityRes param is : " + activityEntity);
            throw new BusinessException("activityEntity  insertActivityRes param is : " + activityEntity);
        }
		mybatisDaoContext.save(SQLID + "insertActivity", activityEntity);
	}
	
	/**
	 *
	 * 查询 资源记录
	 *
	 * @author liyingjie
	 * @created 2016年4月1日 
	 *
	 * @param orderSn acFid
	 */
	public List<OrderActivityEntity> findActivityByCondiction(Map<String,Object> paramMap ){
		if(Check.NuNMap(paramMap)){
			LogUtil.info(logger, "paramMap is null on findActivityByCondiction");
	        throw new BusinessException("paramMap is null on findActivityByCondiction");
	    }
		return mybatisDaoContext.findAll(SQLID + "selectByCondiction", OrderActivityEntity.class, paramMap);
	}
	
    /**
     * 获取当前订单的优惠券信息
     * @param orderSn
     * @return
     */
    public OrderActivityEntity findCouponByOrderSn(String orderSn){
        if(Check.NuNStr(orderSn)){
           return null;
        }
        return mybatisDaoContext.findOne(SQLID + "findCouponByOrderSn", OrderActivityEntity.class, orderSn);
    }

	/**
	 * 
	 * 根据订单编号 查询 订单活动信息
	 *
	 * @author yd
	 * @created 2017年4月12日 下午4:02:01
	 *
	 * @param paramMap
	 * @return
	 */
	public List<OrderActivityEntity> findOrderAcByOrderSn(String orderSn){
		if(Check.NuNStr(orderSn)){
			LogUtil.info(logger, "orderSn is null on findByOrderSn");
	        throw new BusinessException("orderSn is null on findByOrderSn");
	    }
		return mybatisDaoContext.findAll(SQLID + "findOrderAcByOrderSn", OrderActivityEntity.class, orderSn);
	}
	
	
	
	/**
	 * 查询订单使用的优惠券活动
	 * @author lishaochuan
	 * @create 2016年6月18日下午6:17:15
	 * @param paramMap
	 * @return
	 */
	public OrderActivityEntity selectCouponByOrderSn(Map<String,Object> paramMap ){
		if(Check.NuNMap(paramMap)){
			LogUtil.info(logger, "paramMap is null on selectCouponByOrderSn");
	        throw new BusinessException("paramMap is null on selectCouponByOrderSn");
	    }
		paramMap.put("acType", OrderAcTypeEnum.COUPON.getCode());
		return mybatisDaoContext.findOne(SQLID + "selectCouponByOrderSn", OrderActivityEntity.class, paramMap);
	}
	
	/**
	 * 查询订单使用的优惠券活动
	 * @author lishaochuan
	 * @create 2016年6月18日下午6:17:15
	 * @param paramMap
	 * @return
	 */
	public OrderActivityInfoVo selectActByOrderSnAndType(Map<String,Object> paramMap ){
		if(Check.NuNMap(paramMap)){
			LogUtil.info(logger, "paramMap is null on selectCouponByOrderSn");
	        throw new BusinessException("paramMap is null on selectOrderActivityByOrderSn");
	    }
		return mybatisDaoContext.findOne(SQLID + "selectActByOrderSnAndType", OrderActivityInfoVo.class, paramMap);
	}

	/**
	 * 获取列表
	 * @author jixd
	 * @created 2017年06月06日 14:23:56
	 * @param
	 * @return
	 */
	public List<OrderActivityInfoVo> listOrderActByOrderSnAndType(String orderSn,List<Integer> types){
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("orderSn",orderSn);
		paramMap.put("list",types);
		return mybatisDaoContext.findAll(SQLID + "listOrderActByOrderSnAndType",OrderActivityInfoVo.class,paramMap);
	}
	
	/**
	 * 更新活动表状态为已使用
	 * @author lishaochuan
	 * @create 2016年6月15日下午7:58:25
	 * @param orderSn
	 * @return
	 */
	public int updateUsedStatusByCoupon(String orderSn){
		if(Check.NuNStr(orderSn)){
			LogUtil.info(logger, "订单号为空");
	        throw new BusinessException("订单号为空");
	    }
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("acStatus", CouponStatusEnum.USED.getCode());
		map.put("orderSn", orderSn);
		map.put("acType", OrderAcTypeEnum.COUPON.getCode());
		return mybatisDaoContext.update(SQLID + "updateStatusByCoupon", map);
	}
	
	
	/**
	 * 恢复活动表状态为已领取
	 * @author lishaochuan
	 * @create 2016年6月15日下午7:58:25
	 * @param orderSn
	 * @return
	 */
	public int updateGetStatusByCoupon(String orderSn){
		if(Check.NuNStr(orderSn)){
			LogUtil.info(logger, "订单号为空");
	        throw new BusinessException("订单号为空");
	    }
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("acStatus", CouponStatusEnum.GET.getCode());
		map.put("orderSn", orderSn);
		map.put("acType", OrderAcTypeEnum.COUPON.getCode());
		return mybatisDaoContext.update(SQLID + "updateStatusByCoupon", map);
	}


    /**
     * 修改当前活动的金额
     * @author afi
     * @param orderSn
     * @param acCode
     * @param acMoney
     * @return
     */
    public int updateAcMoney(String orderSn,String acCode,int acMoney){
        if(Check.NuNStr(orderSn)){
            LogUtil.info(logger, "订单号为空");
            throw new BusinessException("订单号为空");
        }
        if(Check.NuNStr(acCode)){
            LogUtil.info(logger, "活动编号为空");
            throw new BusinessException("活动编号为空");
        }

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("acCode", acCode);
        map.put("orderSn", orderSn);
        map.put("acMoney", acMoney);
        return mybatisDaoContext.update(SQLID + "updateAcMoney", map);
    }

	
	/**
	 * 修改同步状态为已同步
	 * @author lishaochuan
	 * @create 2016年6月16日下午4:19:35
	 * @param id
	 * @return
	 */
	public int updateHasSync(OrderActivityEntity orderActivity){
		if(Check.NuNObj(orderActivity)){
			LogUtil.info(logger, "参数orderActivity为空");
	        throw new BusinessException("参数orderActivity为空");
	    }
		if(Check.NuNStr(orderActivity.getOrderSn())){
			LogUtil.info(logger, "参数orderSn为空");
	        throw new BusinessException("参数orderSn为空");
	    }
		if(Check.NuNObj(orderActivity.getAcType())){
			LogUtil.info(logger, "参数acType为空");
	        throw new BusinessException("参数acType为空");
	    }
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("orderSn", orderActivity.getOrderSn());
		map.put("acType", orderActivity.getAcType());
		return mybatisDaoContext.update(SQLID + "updateHasSync", map);
	}

    /**
     * 分页查询基本信息
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年07月10日 17:25:40
     */
    public PagingResult<OrderActivityEntity> listTodayCheckInOrderAndUseCouponPage(PageRequest pageRequest) {
        PageBounds pageBounds = new PageBounds();
        pageBounds.setPage(pageRequest.getPage());
        pageBounds.setLimit(pageRequest.getLimit());
        return mybatisDaoContext.findForPage(SQLID + "listTodayCheckInOrderAndUseCoupon", OrderActivityEntity.class, null, pageBounds);
    }
}
