package com.ziroom.minsu.services.job.order;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActCouponEntity;
import com.ziroom.minsu.entity.order.OrderActivityEntity;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.cms.api.inner.ActCouponService;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.common.sms.MessageUtils;
import com.ziroom.minsu.services.common.sms.base.SmsMessage;
import com.ziroom.minsu.services.common.utils.ZkUtil;
import com.ziroom.minsu.services.order.api.inner.OrderTaskOrderService;
import org.codehaus.jackson.type.TypeReference;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>当天到达入住时间的订单并且使用活动组为 FDTGYHJ 的优惠券，发短信提示需要拦截打款</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd on 2017/7/10.
 * @version 1.0
 * @since 1.0
 */
public class OrderRemindCheckInUseCouponJob extends AsuraJob {


    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRemindCheckInUseCouponJob.class);

    private static final String GROUP_SN = "FDTGYHJ";
    private static final String CONTENT = "使用优惠券码%s的订单%s已经到达入住时间，";

    /**
     * 定时任务执行时间  0 15 15 * * ?
     * 每天下午15点15分执行
     *
     * @param jobExecutionContext
     * @author jixd
     */
    @Override
    public void run(JobExecutionContext jobExecutionContext) {
        LogUtil.info(LOGGER, "OrderRemindCheckInUseCouponJob 开始执行.....");

        try {
            //到入住时间，更新订单状态为已入住
            OrderTaskOrderService orderTaskOrderService = (OrderTaskOrderService) ApplicationContext.getContext().getBean("job.orderTaskOrderService");
            ActCouponService actCouponService = (ActCouponService) ApplicationContext.getContext().getBean("job.actCouponService");
            SmsTemplateService smsTemplat = (SmsTemplateService) ApplicationContext.getContext().getBean("basedata.smsTemplateService");
            PageRequest pageRequest = new PageRequest();
            Map<String, String> map = new HashMap<>();
            int page = 1;
            for (; ; ) {
                pageRequest.setPage(page);
                String resultJson = orderTaskOrderService.listTodayCheckInOrderAndUseCouponPage(JsonEntityTransform.Object2Json(pageRequest));
                LogUtil.info(LOGGER, "查询结果result={}", resultJson);
                DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
                if (resultDto.getCode() == DataTransferObject.ERROR) {
                    break;
                }
                List<OrderActivityEntity> list = resultDto.parseData("list", new TypeReference<List<OrderActivityEntity>>() {
                });
                if (Check.NuNCollection(list)) {
                    break;
                }
                page++;

                for (OrderActivityEntity orderActivityEntity : list) {
                    DataTransferObject actDto = JsonEntityTransform.json2DataTransferObject(actCouponService.getCouponBySn(orderActivityEntity.getAcFid()));
                    if (actDto.getCode() == DataTransferObject.ERROR) {
                        continue;
                    }
                    ActCouponEntity actCouponEntity = actDto.parseData("obj", new TypeReference<ActCouponEntity>() {
                    });
                    if (GROUP_SN.equals(actCouponEntity.getGroupSn())) {
                        map.put(orderActivityEntity.getOrderSn(), orderActivityEntity.getAcFid());
                    }
                }
            }

            if (map.isEmpty()) {
                return;
            }
            String msg = "";
            for (String key : map.keySet()) {
                msg += String.format(CONTENT, map.get(key), key);
            }
            msg += "请及时拦截打款。";
            String mobile = ZkUtil.getZkSysValue(EnumMinsuConfig.minsu_order_checkin_coupon_mobile.getType(), EnumMinsuConfig.minsu_order_checkin_coupon_mobile.getCode());
            SmsMessage smsMessage = new SmsMessage(mobile, msg, "86");
            LogUtil.info(LOGGER, "发送短信参数msg={}", JsonEntityTransform.Object2Json(smsMessage));
            MessageUtils.sendSms(smsMessage, null);

        } catch (Exception e) {
            LogUtil.error(LOGGER, "e:{}", e);
        }

        LogUtil.info(LOGGER, "OrderRemindCheckInUseCouponJob 执行结束");
    }
}
