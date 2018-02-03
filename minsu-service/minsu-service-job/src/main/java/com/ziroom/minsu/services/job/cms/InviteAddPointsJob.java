package com.ziroom.minsu.services.job.cms;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.cms.api.inner.ActivityService;
import com.ziroom.minsu.services.cms.api.inner.InviteCreateOrderCmsService;
import com.ziroom.minsu.services.cms.dto.InviteAddPointsSmsVo;
import com.ziroom.minsu.services.cms.dto.InviteOrderRequest;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;
import com.ziroom.minsu.services.order.entity.OrderInviteVo;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 * 新版邀请好友下单的入住后增加积分定时任务
 * @author yanb
 * @version 1.0
 * @Date Created in 2017年12月13日 14:21
 * @since 1.0
 */
public class InviteAddPointsJob extends AsuraJob {
    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(InviteAddPointsJob.class);


    /**
     * 2个小时执行一次
     * @param jobExecutionContext
     */
    @Override
    public void run(JobExecutionContext jobExecutionContext) {

        LogUtil.info(LOGGER, "【InviteAddPointsJob】增加积分定时任务 开始执行.....");
        long startTime = System.currentTimeMillis();
        try {
            /**五个service类*/
            OrderCommonService orderCommonService= (OrderCommonService) ApplicationContext.getContext().getBean("job.orderCommonService");
            ActivityService activityService=(ActivityService) ApplicationContext.getContext().getBean("job.activityService");
            InviteCreateOrderCmsService inviteCreateOrderCmsService=(InviteCreateOrderCmsService) ApplicationContext.getContext().getBean("job.inviteCreateOrderCmsService");
            SmsTemplateService smsTemplateService = (SmsTemplateService) ApplicationContext.getContext().getBean("job.smsTemplateService");

            /**扫描4个小时内的状态>40的订单*/
            String orderDtoJson = orderCommonService.queryOrder4Hour();
            List<OrderInviteVo> orderList = SOAResParseUtil.getListValueFromDataByKey(orderDtoJson, "orderList", OrderInviteVo.class);
            if (Check.NuNCollection(orderList)) {
                return;
            }
            LogUtil.info(LOGGER, "扫描4个小时内的已结算订单【queryOrder4Hour】={}",orderDtoJson);

            /**遍历查询是否订单的uid符合增加积分的要求*/
            String inviteDtoJson = activityService.checkUserInviteStateByList(JsonEntityTransform.Object2Json(orderList));
            List<InviteOrderRequest> inviteList = SOAResParseUtil.getListValueFromDataByKey(inviteDtoJson, "inviteOrderList", InviteOrderRequest.class);
            if (Check.NuNCollection(inviteList)) {
                return;
            }
            LogUtil.info(LOGGER, "遍历查询是否订单的uid符合增加积分的要求【checkUserInviteStateByList】={}",inviteDtoJson);

            /**遍历增加积分以及更新相关表信息*/
            String resultDto = inviteCreateOrderCmsService.addPointsByList(JsonEntityTransform.Object2Json(inviteList));
            LogUtil.info(LOGGER, "遍历增加积分以及更新相关表信息【addPointsByList】执行结果={}",resultDto);

            /**遍历返回的uid list*/
            List<InviteAddPointsSmsVo> resultSmsList =SOAResParseUtil.getListValueFromDataByKey(resultDto, "resultSmsList", InviteAddPointsSmsVo.class);
            if (Check.NuNCollection(resultSmsList)) {
                return;
            }
            /**遍历发送短信*/
            for (InviteAddPointsSmsVo inviteAddPointsSmsVo : resultSmsList) {
                try {
                    LogUtil.info(LOGGER, "遍历发送短信,当前参数inviteAddPointsSmsVo={}", inviteAddPointsSmsVo.toJsonStr());

                    String yaoQingRenUid = inviteAddPointsSmsVo.getInviteUid();
                    String beiYaoQingUid = inviteAddPointsSmsVo.getUid();

                    //查询邀请人,new出来防止非空判断的空指针
                    Map<String, String> yaoQingRenMsg = getCustomerDetailByUid(yaoQingRenUid);
                    if (Check.NuNMap(yaoQingRenMsg) || Check.NuNStr(yaoQingRenMsg.get("mobile"))) {
                        LogUtil.info(LOGGER, "无法给邀请人发送短信，customer:{}", yaoQingRenMsg.toString());
                        continue;
                    }
                    LogUtil.info(LOGGER, "查询邀请人信息 结果={}", yaoQingRenMsg.toString());

                    //查询被邀请人
                    Map<String, String> beiYaoQingMsg = getCustomerDetailByUid(beiYaoQingUid);
                    LogUtil.info(LOGGER, "查询被邀请人信息 结果={}", beiYaoQingMsg.toString());

                    String beiYaoQingName;
                    if (!Check.NuNMap(beiYaoQingMsg) && !Check.NuNObj(beiYaoQingMsg.get("nick_name"))) {
                        beiYaoQingName = beiYaoQingMsg.get("nick_name");
                    } else {
                        beiYaoQingName = "自小如";
                    }
                    Integer points = inviteAddPointsSmsVo.getPoints();
                    Integer sumPoints = inviteAddPointsSmsVo.getSumPoints();
                    String mobile = yaoQingRenMsg.get("mobile");

                    String msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.INVITEE_CREATE_ORDER_SMS_TO_INVITER.getCode());
                    ResourceBundle resource = ResourceBundle.getBundle("job");
                    String JUMP_TO_INVITE_CMS_PAGE_SHORT_CHAIN = resource.getString("JUMP_TO_INVITE_CMS_PAGE_SHORT_CHAIN");
                    SmsRequest smsRequest = new SmsRequest();
                    Map<String, String> paramsMap = new HashMap<String, String>();
                    paramsMap.put("{1}", beiYaoQingName);
                    paramsMap.put("{2}", points.toString());
                    paramsMap.put("{3}", sumPoints.toString());
                    paramsMap.put("{4}", JUMP_TO_INVITE_CMS_PAGE_SHORT_CHAIN);

                    smsRequest.setParamsMap(paramsMap);
                    smsRequest.setMobile(mobile);
                    smsRequest.setSmsCode(msgCode);
                    LogUtil.info(LOGGER, "[发送短信] 参数smsRequest={}", JsonEntityTransform.Object2Json(smsRequest));
                    smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
                } catch (Exception e) {
                    LogUtil.error(LOGGER, "addPointsByList 发短信异常:{}", e);
                    continue;
                }
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【InviteAddPointsJob】新版邀请好友下单增加积分Job异常 e:{}", e);
        }finally {
            LogUtil.info(LOGGER, "【InviteAddPointsJob】 执行结束,花费时间time={}秒", (System.currentTimeMillis() - startTime) / 1000);
        }
    }

    /**
     * 调用友家接口,查询用户信息
     * @param uid
     * @return
     * @author yanb
     */
    private Map<String, String> getCustomerDetailByUid(String uid) {
        Map<String, String> customerDetail = null;
        try {
            StringBuffer url = new StringBuffer();
            ResourceBundle resource = ResourceBundle.getBundle("job");
            String CUSTOMER_DETAIL_URL = resource.getString("CUSTOMER_DETAIL_URL");
            url.append(CUSTOMER_DETAIL_URL).append(uid);
            String getResult = CloseableHttpUtil.sendGet(url.toString(), null);
            LogUtil.info(LOGGER, "查询用户信息,调用接口：{}，返回用户信息：{}", url.toString(), getResult);
            if (Check.NuNStrStrict(getResult)) {
                LogUtil.error(LOGGER, "CUSTOMER_ERROR:根据用户uid={},获取用户信息失败", uid);
            }
            Map<String, String> resultMap = new HashMap<String, String>();
            try {
                resultMap = (Map<String, String>) JsonEntityTransform.json2Map(getResult);
                customerDetail = (Map<String, String>) JsonEntityTransform.json2Map(JsonEntityTransform.Object2Json(resultMap.get("data")));
            } catch (Exception e) {
                LogUtil.info(LOGGER, "用户信息转化错误，请求url={}，返回结果result={}，e={}", url.toString(), getResult, e);
            }
            Object code = resultMap.get("error_code");
            if (Check.NuNObj(code)) {
                LogUtil.error(LOGGER, "【查询用户信息】获取用户信息错误code={}，请求url={}，返回结果result={}", code, url.toString(), getResult);
            }
        } catch (BusinessException e) {
            LogUtil.error(LOGGER, "【查询用户信息】失败,错误={}", e);
        }
        return customerDetail;
    }
}
