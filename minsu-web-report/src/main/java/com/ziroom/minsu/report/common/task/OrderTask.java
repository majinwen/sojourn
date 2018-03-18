/**
 * @FileName: OrderTask.java
 * @Package com.ziroom.minsu.report.common.task
 * 
 * @author bushujie
 * @created 2016年9月24日 下午3:00:44
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.common.task;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.ziroom.minsu.report.house.service.HouseDayOrderPayService;
import com.ziroom.minsu.report.order.service.OrderService;
import com.ziroom.minsu.report.report.service.ReportLogService;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.house.entity.HouseDayPayOrderEntity;
import com.ziroom.minsu.report.report.entity.ReportLogEntity;
import com.ziroom.minsu.report.order.valenum.LogTypeEnum;

/**
 * <p>订单相关计划任务</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
@Component("orderTask")
public class OrderTask {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderTask.class);
	
    @Resource(name = "report.orderService")
    private OrderService orderService;
    
    
    @Resource(name = "report.houseDayOrderPayService")
    private HouseDayOrderPayService houseDayOrderPayService;
    
    @Resource(name = "report.reportLogService")
    private ReportLogService reportLogService;
	
	public void createOrderDayNum(){
		orderService.createOrderDayNum(DateUtils.addDays(new Date(), -10));
		System.err.println("日创建订单量任务执行。。。。。。");
	}
	
	/**
	 * 统计前一天的 支付订单量
	 */
	public void getHouseDayOrderNum(){
		Date todayDate = com.asura.framework.base.util.DateUtil.getTime(0);
		Date yestordayDate = com.asura.framework.base.util.DateUtil.getTime(-1);
		LogUtil.info(LOGGER, "startTime:{},endTime:{}",JsonEntityTransform.Object2Json(yestordayDate),JsonEntityTransform.Object2Json(todayDate));
		dealPayOrderData(yestordayDate,todayDate);
		
	}
	

	/**
	 * 统计某一段时间的 支付订单量
	 * TODO：如何调用，把参数传递进来
	 */
	public void getHouseDayOrderNum(Date startTime,Date endTime){
		List<Date> dateList = com.asura.framework.base.util.DateUtil.getDatebetweenOfDays(startTime, endTime);
		Date startDate = null;
		for(Date date : dateList){
			startDate = date;
			Date endDate = com.asura.framework.base.util.DateUtil.getTime(1);
			dealPayOrderData(startDate,endDate);
		}
	}
	
	/**
	 * 1 具体调用获取数据，并插入数据库
	 * 2 数据插入失败 保存信息到日志文件
	 */
	private void dealPayOrderData(Date startTime,Date endTime){
        List<HouseDayPayOrderEntity> resultList = orderService.getHouseDayPayOrderNum(startTime,endTime);
		if(Check.NuNCollection(resultList)){
			LogUtil.info(LOGGER, "昨天{}日没有新支付的订单喔！",JsonEntityTransform.Object2Json(startTime));
		    return ;
		}
		int result = 0;
		ReportLogEntity hpay = null;
		for(HouseDayPayOrderEntity hd : resultList){
			hd.setStatisticsDate(startTime);
			result = houseDayOrderPayService.insertHouseDayPayOrderNum(hd);
			//插入失败，保存到日志文件
			if(result == 0){
				hpay = new ReportLogEntity();
				hpay.setFid(UUIDGenerator.hexUUID());
				hpay.setType(LogTypeEnum.DAY_INC_PAY_ORDER.getCode());
				hpay.setContent(JsonEntityTransform.Object2Json(hpay));
				reportLogService.insertHouseDayPayOrderNum(hpay);
			}
		}
		
	}
	
	
	/**
	 * 插入失败，做补偿
	 * @param failEntity
	 * @param num
	 */
	private void reDealPayOrderData(HouseDayPayOrderEntity failEntity,int num){
		if(num <= 0){
			LogUtil.error(LOGGER, "fail info:{}", JsonEntityTransform.Object2Json(failEntity));
    		return ;
    	}    
	    int result = houseDayOrderPayService.insertHouseDayPayOrderNum(failEntity);
    	if(result != 1){
    		reDealPayOrderData(failEntity,num-1);
    	}
	}

}
