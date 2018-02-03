package com.ziroom.minsu.services.job.order;

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
import com.ziroom.minsu.entity.order.OrderActivityEntity;
import com.ziroom.minsu.services.cms.api.inner.ActCouponService;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.order.api.inner.OrderTaskSyncService;

/**
 * <p>优惠券状态同步job</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年6月16日
 * @since 1.0
 * @version 1.0
 */
public class CouponSyncStatusJob extends AsuraJob {
	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(CouponSyncStatusJob.class);
	

	/**
	 * 优惠券状态同步job
	 * 10分钟一次
	 * 0 0/10 * * * ?
	 */
	@Override
	public void run(JobExecutionContext jobExecutionContext) {
		LogUtil.info(LOGGER, "CouponSyncStatusJob 开始执行.....");
		try {
			OrderTaskSyncService orderTaskSyncService = (OrderTaskSyncService) ApplicationContext.getContext().getBean("job.orderTaskSyncService");
			ActCouponService actCouponService = (ActCouponService) ApplicationContext.getContext().getBean("job.actCouponService");
			
			
			String resultJson = orderTaskSyncService.getNotSyncActivityCount();
			LogUtil.info(LOGGER, "【同步优惠券状态Job】1、获取未同步优惠券状态的活动条数结果,resultJson:{}", resultJson);
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(dto.getCode() != DataTransferObject.SUCCESS){
            	LogUtil.error(LOGGER, "【同步优惠券状态Job】获取未同步优惠券状态的活动条数失败", dto.toJsonString());
            	return;
            }
			int limit = 150;
			Integer count = (Integer)dto.getData().get("count");
			if (Check.NuNObj(count) || count == 0) {
				return;
			}
			int pageAll = ValueUtil.getPage(count.intValue(), limit);
			for (int page = 1; page <= pageAll; page++) {
				resultJson = orderTaskSyncService.getNotSyncActivityList(limit);
				LogUtil.info(LOGGER, "【同步优惠券状态Job】2、获取未同步状态的活动list完毕,resultJson:{}", resultJson);
				dto = JsonEntityTransform.json2DataTransferObject(resultJson);
				if(dto.getCode() != DataTransferObject.SUCCESS){
					continue;
				}
				List<OrderActivityEntity> orderActList = dto.parseData("orderActList", new TypeReference<List<OrderActivityEntity>>() {});
				resultJson = actCouponService.syncCouponStatus(JsonEntityTransform.Object2Json(orderActList));
				LogUtil.info(LOGGER, "【同步优惠券状态Job】3、批量同步cms库状态完毕,resultJson:{}", resultJson);
				dto = JsonEntityTransform.json2DataTransferObject(resultJson);
				if (dto.getCode() != DataTransferObject.SUCCESS) {
					LogUtil.error(LOGGER, "【同步优惠券状态Job】批量同步cms库状态返回失败");
					continue;
				}
				LogUtil.info(LOGGER, "【同步优惠券状态Job】4、批量活动表为已同步完毕");
				resultJson = orderTaskSyncService.updateActivityHasSync(JsonEntityTransform.Object2Json(orderActList));
				dto = JsonEntityTransform.json2DataTransferObject(resultJson);
				if (dto.getCode() != DataTransferObject.SUCCESS) {
					LogUtil.error(LOGGER, "【同步优惠券状态Job】批量活动表为已同步失败");
					continue;
				}
			}
			
			
			
			LogUtil.info(LOGGER, "CouponSyncStatusJob 执行结束");
		} catch (Exception e) {
			LogUtil.error(LOGGER, "e:{}", e);
		}

	}

}
