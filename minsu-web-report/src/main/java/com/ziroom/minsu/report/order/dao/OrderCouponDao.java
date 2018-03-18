package com.ziroom.minsu.report.order.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.report.order.dto.OrderCouponRequest;
import com.ziroom.minsu.report.order.entity.OrderCouponEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>订单优惠券</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/5.
 * @version 1.0
 * @since 1.0
 */
@Repository("report.orderCouponDao")
public class OrderCouponDao {

    private String SQLID="report.orderCouponDao.";

    @Autowired
	@Qualifier("minsuReport.all.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;



	/**
	 * 获取当前的订单的统计信息
	 * @author afi
	 * @param orderCouponRequest
	 * @return
	 */
	public List<OrderCouponEntity> getOrderCouponByCity(OrderCouponRequest orderCouponRequest){

		return mybatisDaoContext.findAll(SQLID + "getOrderCouponByCity", OrderCouponEntity.class,orderCouponRequest);
	}



}
