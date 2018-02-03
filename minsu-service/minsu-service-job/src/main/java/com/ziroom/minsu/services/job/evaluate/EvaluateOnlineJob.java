package com.ziroom.minsu.services.job.evaluate;

import java.util.List;

import org.codehaus.jackson.type.TypeReference;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.services.evaluate.api.inner.EvaluateOrderService;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;

/**
 * <p>评价上线的定时任务</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/7.
 * @version 1.0
 * @since 1.0
 */
public class EvaluateOnlineJob extends AsuraJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(EvaluateOnlineJob.class);
    /**
     * 评价上线的定时任务(半小时 扫秒一次  每次扫描  往前推一个小时状态为待审核的数据 )
     * 说明：扫描t_evaluate_order表， 创建时间 往前推hours小时，修改状态为 已发布
     * @author afi
     * time 超时时间(即评价创建时间 超过这个时间  就上线) 单位 ：小时
     */
    @Override
    public void run(JobExecutionContext jobExecutionContext) {
        // 0 0/30 * * * ?
    	
        try {
            LogUtil.info(LOGGER, "EvaluateOnlineJob 开始执行.....");
            String time = "30";
            //评价的api
            EvaluateOrderService evaluateOrderService = (EvaluateOrderService) ApplicationContext.getContext().getBean("job.evaluateOrderService");
            
            OrderCommonService orderCommonService = (OrderCommonService) ApplicationContext.getContext().getBean("job.orderCommonService");

            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(evaluateOrderService.evaluateOnline(time));
            if(dto.getCode() == 0){
            	List<EvaluateOrderEntity> listEvaluateOrder = dto.parseData("listEvaluateOrder", new TypeReference<List<EvaluateOrderEntity>>() {
				});
            	if(!Check.NuNCollection(listEvaluateOrder)){
            		
            		for (EvaluateOrderEntity evaluateOrderEntity : listEvaluateOrder) {
            			
            			dto = JsonEntityTransform.json2DataTransferObject(orderCommonService.getOrderByOrderSn(evaluateOrderEntity.getOrderSn()));
            			OrderEntity oe = dto.parseData("order", new TypeReference<OrderEntity>() {});
                        evaluateOrderService.sendOnlineEvaMsg(JsonEntityTransform.Object2Json(oe),String.valueOf(evaluateOrderEntity.getEvaUserType().intValue()));
            			//evaluateOrderService.statsEvaluateMessage(JsonEntityTransform.Object2Json(evaluateOrderEntity), JsonEntityTransform.Object2Json(oe));
					}
            	}
            }
            
            LogUtil.info(LOGGER, "EvaluateOnlineJob 结束");
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
        }
        
      
		
		
    }
}
