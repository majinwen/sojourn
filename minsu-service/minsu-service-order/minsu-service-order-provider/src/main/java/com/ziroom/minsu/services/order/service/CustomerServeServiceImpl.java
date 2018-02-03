package com.ziroom.minsu.services.order.service;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.message.MsgFirstAdvisoryEntity;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.finance.entity.RefuseOrderVo;
import com.ziroom.minsu.services.finance.entity.RemindOrderVo;
import com.ziroom.minsu.services.order.dao.OrderBaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>troy客服平台service</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年8月1日
 * @since 1.0
 * @version 1.0
 */
@Service("order.customerServeServiceImpl")
public class CustomerServeServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServeServiceImpl.class);
	
	@Resource(name = "order.orderBaseDao")
	private OrderBaseDao orderBaseDao;
	
	/**
	 * 申请预订且房东未回复的订单
	 * @author lishaochuan
	 * @create 2016年8月1日下午6:39:04
	 * @return
	 */
	public PagingResult<RemindOrderVo> getRemindOrderList(PageRequest pageRequest){
		return orderBaseDao.getRemindOrderList(pageRequest);
	}
	
	/**
	 * 查询房东拒绝的申请预定（12小时以内）
	 * @author lishaochuan
	 * @create 2016年8月3日下午2:29:06
	 * @param pageRequest
	 * @return
	 */
	public PagingResult<RefuseOrderVo> getRefuseOrderList(PageRequest pageRequest){
		return orderBaseDao.getRefuseOrderList(pageRequest);
	}

	/**
	 * @description: 获取用户咨询时有没有订单信息
	 * @author: lusp
	 * @date: 2017/8/11 21:40
	 * @params: param
	 * @return:
	 */
	public OrderEntity getAdvisoryOrderInfo(MsgFirstAdvisoryEntity msgFirstAdvisoryEntity){
		return orderBaseDao.getAdvisoryOrderInfo(msgFirstAdvisoryEntity);
	}
}
