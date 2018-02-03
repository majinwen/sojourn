/**
 * @FileName: CheckInNoticeTenantEvalJob.java
 * @Package com.ziroom.minsu.services.job.evaluate
 * 
 * @author zl
 * @created 2017年2月4日 上午9:09:07
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.job.evaluate;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.evaluate.api.inner.EvaluateOrderService;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;
import com.ziroom.minsu.services.order.dto.OrderRequest;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.valenum.evaluate.EvaStatusEnum;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>房客入住当天晚20：00点，给房客发送短信和系统通知，提醒房客去写评价</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public class CheckInNoticeTenantEvalJob extends AsuraJob {
 
	private static final Logger LOGGER = LoggerFactory.getLogger(CheckInNoticeTenantEvalJob.class);
	
	@Override
	public void run(JobExecutionContext jobExecutionContext) {
        
		Long t1 = System.currentTimeMillis();

        //订单的api
        OrderCommonService orderCommonService = (OrderCommonService) ApplicationContext.getContext().getBean("job.orderCommonService");

		EvaluateOrderService evaluateOrderService = (EvaluateOrderService) ApplicationContext.getContext().getBean("job.evaluateOrderService");
        
        LogUtil.info(LOGGER, "CheckInNoticeTenantEvalJob 开始执行.....");
        
        try {
        	
        	List<OrderInfoVo> orderDetailList = null;
        	int page = 1;
        	
        	OrderRequest orderRequest = new OrderRequest();
        	orderRequest.setLimit(20);
        	orderRequest.setRequestType(3);
        	
        	Date now = new Date();    			
        	orderRequest.setCheckInTimeStart(DateUtil.dateFormat(now, "yyyy-MM-dd")+" 00:00:00");
        	orderRequest.setCheckInTimeEnd(DateUtil.dateFormat(now, "yyyy-MM-dd")+" 23:59:59");
        	
        	List<Integer> listEvaStatus = new ArrayList<>();
        	listEvaStatus.add(EvaStatusEnum.TWO_NOT_EVA.getCode());
        	listEvaStatus.add(EvaStatusEnum.LANFLORD_HAVE_EVA.getCode());
        	orderRequest.setListEvaStatus(listEvaStatus);
        	
        	for(;;) {
				orderRequest.setPage(page);

				String resultJson = orderCommonService.getOrderListByCondiction(JsonEntityTransform.Object2Json(orderRequest));
				DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
				page ++;
				if (resultDto.getCode() != DataTransferObject.SUCCESS) {
					continue;
				}

				orderDetailList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "orderHouseList", OrderInfoVo.class);
				if (Check.NuNCollection(orderDetailList)) {
					break;
				}

				evaluateOrderService.checkInNoticeTenantEva(JsonEntityTransform.Object2Json(orderDetailList));
			}
        	
		} catch (Exception e) {
			LogUtil.error(LOGGER,"CheckInNoticeTenantEvalJob 执行失败：e={}",e);
		}
        
        LogUtil.info(LOGGER, "CheckInNoticeTenantEvalJob 执行结束,共耗时"+(new Date().getTime()-t1)+"ms");
		
	}
	
	

}
