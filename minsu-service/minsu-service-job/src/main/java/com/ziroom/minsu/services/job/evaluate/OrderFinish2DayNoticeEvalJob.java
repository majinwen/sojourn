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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>订单状态变更为已完成后2天，如房客、房东仍然没有写评价，在晚上19:00给房客、房东发送提醒短信和系统通知。</p>
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
public class OrderFinish2DayNoticeEvalJob extends AsuraJob {
 
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderFinish2DayNoticeEvalJob.class);
	
	@Override
	public void run(JobExecutionContext jobExecutionContext) {
        
		Long t1 = System.currentTimeMillis();

        //订单的api
        OrderCommonService orderCommonService = (OrderCommonService) ApplicationContext.getContext().getBean("job.orderCommonService");

		EvaluateOrderService evaluateOrderService = (EvaluateOrderService) ApplicationContext.getContext().getBean("job.evaluateOrderService");
        
        LogUtil.info(LOGGER, "OrderFinish2DayNoticeEvalJob 开始执行.....");
        
        try {
        	
        	List<OrderInfoVo> orderDetailList = null;
        	int page = 1;
        	
        	OrderRequest orderRequest = new OrderRequest();
        	orderRequest.setLimit(20);
        	orderRequest.setRequestType(3);
        	
        	int day = 2;
        	Calendar calendar = Calendar.getInstance();
        	calendar.add(Calendar.DATE, -day);
        	orderRequest.setRealEndTimeStart(DateUtil.dateFormat(calendar.getTime(), "yyyy-MM-dd")+" 00:00:00");
        	orderRequest.setRealEndTimeEnd(DateUtil.dateFormat(calendar.getTime(), "yyyy-MM-dd")+" 23:59:59");
        	
        	List<Integer> listEvaStatus = new ArrayList<>();
        	listEvaStatus.add(EvaStatusEnum.TWO_NOT_EVA.getCode());
        	listEvaStatus.add(EvaStatusEnum.LANFLORD_HAVE_EVA.getCode());
        	listEvaStatus.add(EvaStatusEnum.TENANT_HAVE_EVA.getCode());
        	orderRequest.setListEvaStatus(listEvaStatus);
        	
        	for(;;){
    			orderRequest.setPage(page); 			
    			String resultJson = orderCommonService.getOrderListByCondiction(JsonEntityTransform.Object2Json(orderRequest));
    			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
				page++;
    			if (resultDto.getCode()!=DataTransferObject.SUCCESS) {
					continue;
				}
    			 
    			orderDetailList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "orderHouseList", OrderInfoVo.class);
    			if (Check.NuNCollection(orderDetailList)) {
					break;
				}

				evaluateOrderService.orderFinish2DayNoticeEva(JsonEntityTransform.Object2Json(orderDetailList));
        	} 

		} catch (Exception e) {
			LogUtil.error(LOGGER,"OrderFinish2DayNoticeEvalJob 执行失败：e={}",e);
		}
        
        LogUtil.info(LOGGER, "OrderFinish2DayNoticeEvalJob 执行结束,共耗时"+(new Date().getTime()-t1)+"ms");
		
	}

}
