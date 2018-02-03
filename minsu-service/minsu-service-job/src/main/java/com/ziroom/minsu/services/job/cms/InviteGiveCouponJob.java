package com.ziroom.minsu.services.job.cms;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActCouponEntity;
import com.ziroom.minsu.entity.cms.InviteEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.cms.api.inner.ActCouponService;
import com.ziroom.minsu.services.cms.api.inner.InviteService;
import com.ziroom.minsu.services.common.constant.ActivityConst;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.order.api.inner.OrderTaskOrderService;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ziroom.minsu.valenum.finance.PaymentSourceTypeEnum.customer;

/**
 * <p>受邀人入住后，给邀请人发送优惠券</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/11/2 18:24
 * @version 1.0
 * @since 1.0
 */
public class InviteGiveCouponJob extends AsuraJob {

    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(InviteGiveCouponJob.class);


    /**
     * 受邀人入住后，给邀请人发送优惠券job
     * 30分钟一次
     * 0 0/30 * * * ?
     */
    @Override
    public void run(JobExecutionContext jobExecutionContext) {
        LogUtil.info(LOGGER, "InviteGiveCouponJob 开始执行.....");
        try {
            InviteService inviteService = (InviteService) ApplicationContext.getContext().getBean("job.inviteService");
            OrderTaskOrderService orderTaskOrderService = (OrderTaskOrderService) ApplicationContext.getContext().getBean("job.orderTaskOrderService");
            ActCouponService actCouponService = (ActCouponService) ApplicationContext.getContext().getBean("job.actCouponService");
            CustomerInfoService customerInfoService = (CustomerInfoService) ApplicationContext.getContext().getBean("job.customerInfoService");
            SmsTemplateService smsTemplateService = (SmsTemplateService) ApplicationContext.getContext().getBean("job.smsTemplateService");

            // 校验给邀请人送券活动信息，必须有进行中的活动，才继续
            String actJson = actCouponService.getNoExchangeCountByGroupSn(ActivityConst.InviteConst.INVITER_GROUP_SN);
            Long count = SOAResParseUtil.getValueFromDataByKey(actJson, "count", Long.class);
            if (count == null || count <= 0) {
                LogUtil.info(LOGGER, "活动已结束，或券已领完，groupSn:{}", ActivityConst.InviteConst.INVITER_GROUP_SN);
                return;
            }

            String unCouponListJson = inviteService.getUnCouponList();
            List<InviteEntity> unCouponList = SOAResParseUtil.getListValueFromDataByKey(unCouponListJson, "unCouponList", InviteEntity.class);
            if(Check.NuNCollection(unCouponList)){
                LogUtil.info(LOGGER, "查询所有未给邀请人送券条数为空，unCouponList:{}", unCouponList);
                return;
            }
            LogUtil.info(LOGGER, "查询所有未给邀请人送券条数，size:{}", unCouponList.size());

            for (InviteEntity inviteEntity : unCouponList) {
                // 查询此uid,在inviteTime之后，是否有已入住订单
                String orderJson = orderTaskOrderService.checkIfInviteCheckInOrder(JsonEntityTransform.Object2Json(inviteEntity));
                Boolean haveFlag = SOAResParseUtil.getValueFromDataByKey(orderJson, "haveFlag", Boolean.class);
                if (!haveFlag) {
                    LogUtil.info(LOGGER, "此用户尚未有入住订单，uid:{}", inviteEntity.getUid());
                    continue;
                }

                // 如果有，给inviteUid，送券，并且修改t_invite表invite_status为3
                LogUtil.info(LOGGER, "此用户已有入住订单，开始给邀请人送券....,uid:{},invite_uid:{}", inviteEntity.getUid(), inviteEntity.getInviteUid());
                String inviteJson = inviteService.giveInviterCoupon(JsonEntityTransform.Object2Json(inviteEntity));
                LogUtil.info(LOGGER, "inviteJson:{}", inviteJson);

                try {
                    // 给邀请人发短信
                    DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(inviteJson);
                    if(dto.getCode() == DataTransferObject.SUCCESS){
                        ActCouponEntity actCouponEntity = SOAResParseUtil.getValueFromDataByKey(inviteJson, "coupon", ActCouponEntity.class);
                        if(Check.NuNObj(actCouponEntity)){
                            LogUtil.info(LOGGER, "给邀请人送的优惠券信息为空，inviteJson:{}", inviteJson);
                            continue;
                        }
                        LogUtil.info(LOGGER, "给邀请人送的优惠券信息，actCouponEntity:{}", JsonEntityTransform.Object2Json(actCouponEntity));

                        // 邀请人信息
                        String inviterCustomerJson = customerInfoService.getCustomerInfoByUid(inviteEntity.getInviteUid());
                        CustomerBaseMsgEntity inviterCustomer = SOAResParseUtil.getValueFromDataByKey(inviterCustomerJson, "customerBase", CustomerBaseMsgEntity.class);
                        LogUtil.info(LOGGER, "查询邀请人信息：{}", inviterCustomerJson);

                        // 受邀人信息
                        String inviteeCustomerJson = customerInfoService.getCustomerInfoByUid(inviteEntity.getUid());
                        CustomerBaseMsgEntity inviteeCustomer = SOAResParseUtil.getValueFromDataByKey(inviteeCustomerJson, "customerBase", CustomerBaseMsgEntity.class);
                        LogUtil.info(LOGGER, "受邀人信息：{}", inviteeCustomerJson);

                        if (!Check.NuNObj(inviterCustomer) && !Check.NuNObj(inviteeCustomer)) {
                            if(Check.NuNStr(inviterCustomer.getCustomerMobile())){
                                LogUtil.info(LOGGER, "此邀请人未绑定手机号，不发短信，customer:{}", JsonEntityTransform.Object2Json(customer));
                                continue;
                            }

                            // 受邀人名称
                            String name = "";
                            if(!Check.NuNStr(inviteeCustomer.getNickName())){
                                name = inviteeCustomer.getNickName();
                            }

                            SmsRequest smsRequest = new SmsRequest();
                            Map<String, String> paramsMap = new HashMap<>();
                            paramsMap.put("{1}", name);
                            paramsMap.put("{2}", ValueUtil.getStrValue(actCouponEntity.getActCut()/100));
                            smsRequest.setParamsMap(paramsMap);
                            smsRequest.setMobile(inviterCustomer.getCustomerMobile());
                            smsRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.ACTIVITY_YQRSQ.getCode()));

                            LogUtil.info(LOGGER, "发送短信参数:{}", JsonEntityTransform.Object2Json(smsRequest));
                            smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
                        }

                    }
                }catch (Exception e){
                    LogUtil.error(LOGGER,"给邀请人发送短信失败：e：{}",e);
                }
            }

            LogUtil.info(LOGGER, "InviteGiveCouponJob 执行结束");
        } catch (Exception e) {
            LogUtil.error(LOGGER, "e:{}", e);
        }

    }
}
