package com.ziroom.minsu.services.order.service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderActivityEntity;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.finance.entity.OrderActivityInfoVo;
import com.ziroom.minsu.services.order.dao.OrderActivityDao;
import com.ziroom.minsu.valenum.order.OrderAcTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>订单活动service</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年6月15日
 * @since 1.0
 * @version 1.0
 */
@Service("order.orderActivityServiceImpl")
public class OrderActivityServiceImpl {

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(OrderActivityDao.class);
	
	@Resource(name = "order.activityDao")
    private OrderActivityDao orderActivityDao;


    /**
     * 通过订单号获取当前的优惠券信息
     * @param orderSn
     * @return
     */
    public  String getCouponByOrderSn(String orderSn){
        String couponSn = "";
        if (Check.NuNStr(orderSn)){
            return couponSn;
        }
        OrderActivityEntity ac = orderActivityDao.findCouponByOrderSn(orderSn);
        if (!Check.NuNObj(ac)){
            couponSn = ac.getAcFid();
        }

        return couponSn;
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
		return this.orderActivityDao.findOrderAcByOrderSn(orderSn);
	}

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
        return orderActivityDao.checkCouponOk(couponSn);
    }
    
    
    /**
     * 查询订单使用的优惠券活动
     * @author lishaochuan
     * @create 2016年6月18日下午6:20:11
     * @param orderSn
     * @return
     */
    public OrderActivityEntity selectCouponByOrderSn(String orderSn){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderSn", orderSn);
		paramMap.put("acType", OrderAcTypeEnum.COUPON.getCode());
		return orderActivityDao.selectCouponByOrderSn(paramMap);
	}
    
    /**
     * 
     * 根据 订单编号 和 活动类型 查询活动信息
     *
     * @author yd
     * @created 2017年5月17日 上午11:15:31
     *
     * @param orderSn
     * @param type
     * @return
     */
    public OrderActivityInfoVo selectActByOrderSnAndType(String orderSn, int type){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderSn", orderSn);
		paramMap.put("acType", type);
		return orderActivityDao.selectActByOrderSnAndType(paramMap);
    }

	/**
	 * 查询活动列表
	 * @author jixd
	 * @created 2017年06月06日 14:28:06
	 * @param
	 * @return
	 */
	public List<OrderActivityInfoVo> listOrderActByOrderSnAndType(String orderSn,List<Integer> types){
		return orderActivityDao.listOrderActByOrderSnAndType(orderSn,types);
	}
	

	/**
	 * 查询未同步优惠券状态的count
	 * @author lishaochuan
	 * @create 2016年6月15日下午8:43:13
	 * @return
	 */
	public Long getNotSyncCount(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("acType", OrderAcTypeEnum.COUPON.getCode());
		return orderActivityDao.getNotSyncCount(map);
    }
	
	/**
	 * 查询未同步优惠券状态的list
	 * @author lishaochuan
	 * @create 2016年6月15日下午8:43:27
	 * @param map
	 * @return
	 */
	public List<OrderActivityEntity> getNotSyncList(int limit){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("acType", OrderAcTypeEnum.COUPON.getCode());
		map.put("limit", limit);
        return orderActivityDao.getNotSyncList(map);
    }
	
	/**
	 * 更新活动表状态为已使用
	 * @author lishaochuan
	 * @create 2016年6月15日下午8:09:44
	 * @param orderSn
	 * @return
	 */
	public int updateUsedStatusByCoupon(String orderSn){
		LogUtil.info(LOGGER, "更新活动表状态为已使用,orderSn:{}",orderSn);
		return orderActivityDao.updateUsedStatusByCoupon(orderSn);
	}
	
	/**
	 * 恢复活动表状态为已领取
	 * @author lishaochuan
	 * @create 2016年6月15日下午8:10:03
	 * @param orderSn
	 * @return
	 */
	public int updateGetStatusByCoupon(String orderSn){
		LogUtil.info(LOGGER, "恢复活动表状态为已领取,orderSn:{}",orderSn);
		return orderActivityDao.updateGetStatusByCoupon(orderSn);
	}
	
	/**
	 * 批量修改同步状态为已同步
	 * @author lishaochuan
	 * @create 2016年6月16日下午4:22:13
	 * @param orderActList
	 */
	public void updateHasSync(List<OrderActivityEntity> orderActList){
		for (OrderActivityEntity orderActivityEntity : orderActList) {
			orderActivityDao.updateHasSync(orderActivityEntity);
		}
	}

    /**
     * 查询活动列表
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年07月10日 17:35:16
     */
    public PagingResult<OrderActivityEntity> listTodayCheckInOrderAndUseCouponPage(PageRequest pageRequest) {
        return orderActivityDao.listTodayCheckInOrderAndUseCouponPage(pageRequest);
    }

}
