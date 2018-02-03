package com.ziroom.minsu.services.order.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.services.order.dto.*;
import com.ziroom.minsu.services.order.entity.OrderCashbackVo;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> 订单的统计操作 </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/12.
 * @version 1.0
 * @since 1.0
 */
@Repository("order.orderStaticsDao")
public class OrderStaticsDao {

	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(OrderStaticsDao.class);
	private String SQLID = "order.orderStaticsDao.";

	@Autowired
	@Qualifier("order.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;


	/**
	 * 获取当前定点杆的统计信息
	 * @param landlordUid
	 * @return
	 */
	public OrderLandlordStaticsDto staticsLandOrderCountInfo(String landlordUid){
		return mybatisDaoContext.findOne(SQLID + "staticsLandOrderCountInfo", OrderLandlordStaticsDto.class,landlordUid);
	}

}
