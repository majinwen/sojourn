/**
 * @FileName: LandlordCancelOrderJob.java
 * @Package com.ziroom.minsu.services.job.order
 * @author loushuai
 * @created 2017年5月16日 下午5:08:19
 * <p>
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.job.order;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActivityFreeEntity;
import com.ziroom.minsu.entity.order.OrderConfigEntity;
import com.ziroom.minsu.entity.order.OrderCsrCancleEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.cms.api.inner.ActivityGiftService;
import com.ziroom.minsu.services.cms.api.inner.MobileCouponService;
import com.ziroom.minsu.services.cms.dto.MobileCouponRequest;
import com.ziroom.minsu.services.customer.api.inner.CustomerRoleService;
import com.ziroom.minsu.services.house.api.inner.TroyHouseMgtService;
import com.ziroom.minsu.services.order.api.inner.CancelOrderService;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.customer.CustomerRoleEnum;
import com.ziroom.minsu.valenum.order.PunishedStatusEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0025Enum;
import org.codehaus.jackson.type.TypeReference;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>房东取消订单保证数据一致性定时任务</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public class LandlordCancelOrderJob extends AsuraJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(LandlordCancelOrderJob.class);
    private static final String ACT_CODE = "FDQX2017";

    @Override
    public void run(JobExecutionContext jobExecutionContext) {
        CancelOrderService cancelOrderService = (CancelOrderService) ApplicationContext.getContext().getBean("order.cancelOrderService");
        CustomerRoleService customerRoleService = (CustomerRoleService) ApplicationContext.getContext().getBean("customer.customerRoleService");
        OrderCommonService orderCommonService = (OrderCommonService) ApplicationContext.getContext().getBean("order.orderCommonService");
        ActivityGiftService activityGiftService = (ActivityGiftService) ApplicationContext.getContext().getBean("job.activityGiftService");
        MobileCouponService mobileCouponService = (MobileCouponService) ApplicationContext.getContext().getBean("cms.mobileCouponService");
        TroyHouseMgtService troyHouseMgtService = (TroyHouseMgtService) ApplicationContext.getContext().getBean("house.troyHouseMgtService");
        CityTemplateService cityTemplateService= (CityTemplateService) ApplicationContext.getContext().getBean("job.cityTemplateService");
        LogUtil.info(LOGGER, "LandlordCancelOrderJob 开始执行.....");
        try {
            //从订单中获取
            String orderScrJson = cancelOrderService.getDoFailLandQXOrderPunish();
            DataTransferObject  orderScrDto = JsonEntityTransform.json2DataTransferObject(orderScrJson);
            List<OrderCsrCancleEntity> orderScrList = null;
            if(orderScrDto.getCode() == DataTransferObject.SUCCESS){
            	orderScrList = SOAResParseUtil.getListValueFromDataByKey(orderScrJson, "orderCsrList", OrderCsrCancleEntity .class);
            }
            
            if (Check.NuNCollection(orderScrList)){
                return;
            }
            for (OrderCsrCancleEntity orderCsrCancleEntity : orderScrList) {
                String orderSn = orderCsrCancleEntity.getOrderSn();
                DataTransferObject orderInfoDto = JsonEntityTransform.json2DataTransferObject(orderCommonService.getOrderInfoByOrderSn(orderSn));
                if (orderInfoDto.getCode() == DataTransferObject.ERROR){
                    continue;
                }
                OrderInfoVo orderInfoVo = orderInfoDto.parseData("orderInfoVo", new TypeReference<OrderInfoVo>() {});

                //根据orderSn获取orderConfigList
                DataTransferObject orderConfigListDto = JsonEntityTransform.json2DataTransferObject(cancelOrderService.getOrderConfigListByOrderSn(orderSn));
                if (orderConfigListDto.getCode() == DataTransferObject.ERROR){
                    LogUtil.error(LOGGER,"【LandlordCancelOrderJob】获取订单配置异常,dto={}",orderConfigListDto.toJsonString());
                    continue;
                }
                List<OrderConfigEntity> orderConfigList = orderConfigListDto.parseData("orderConfigList", new TypeReference<List<OrderConfigEntity>>() {});
                if (!Check.NuNCollection(orderConfigList)) {
                    for (OrderConfigEntity orderConfigEntity : orderConfigList) {
                        //天使房东
                        if (ProductRulesEnum0025Enum.ProductRulesEnum0025001003.getValue().equals(orderConfigEntity.getConfigCode())
                            && YesOrNoEnum.NO.getStr().equals(orderConfigEntity.getConfigValue())) {
                            //获取6个月枚举类
                            DataTransferObject selectEnumDto  = JsonEntityTransform.json2DataTransferObject(cityTemplateService.getTextValue(null, ProductRulesEnum0025Enum.ProductRulesEnum0025001.getValue()));
                            String inSomeMonth = selectEnumDto.parseData("textValue", new TypeReference<String>() {});

                            Date beforeSixMonth = getNowAroundMonths(new Date(), -Integer.valueOf(inSomeMonth));
                            DataTransferObject countTimesDto = JsonEntityTransform.json2DataTransferObject(cancelOrderService.getCountInTimes(orderInfoVo.getLandlordUid(), beforeSixMonth, new Date()));
                            int countInTimes = (int)countTimesDto.getData().get("countInTimes");

                            if (countInTimes >= 1){
                                //将天使房东免佣金记录删掉(逻辑删除)
                                ActivityFreeEntity activityFreeEntity =new ActivityFreeEntity();
                                activityFreeEntity.setUid(orderInfoVo.getLandlordUid());
                                activityFreeEntity.setIsDel(YesOrNoEnum.YES.getCode());
                                String paramJson = JsonEntityTransform.Object2Json(activityFreeEntity);
                                String cancelFreeCommissionJson = activityGiftService.cancelFreeCommission(paramJson);
                                DataTransferObject cancelFreeCommissionDto = JsonEntityTransform.json2DataTransferObject(cancelFreeCommissionJson);
                                if (cancelFreeCommissionDto.getCode() == DataTransferObject.SUCCESS){
                                    String cancelAngelJson = customerRoleService.cancelAngelLandlord(orderInfoVo.getLandlordUid(), CustomerRoleEnum.SEED.getStr());
                                    DataTransferObject cancelAngelDto = JsonEntityTransform.json2DataTransferObject(cancelAngelJson);
                                    if (cancelAngelDto.getCode() == DataTransferObject.SUCCESS) {
                                        cancelOrderService.updateOrderConfValue(orderSn, ProductRulesEnum0025Enum.ProductRulesEnum0025001003.getValue(), YesOrNoEnum.YES.getStr());
                                    }
                                }
                            }
                        }

                        //取消订单评价
                        /*if (ProductRulesEnum0025Enum.ProductRulesEnum0025001004.getValue().equals(orderConfigEntity.getConfigCode())
                                && YesOrNoEnum.NO.getStr().equals(orderConfigEntity.getConfigValue())) {
                            String orderEntityJson = JsonEntityTransform.Object2Json(orderEntity);
                            String snapshotEntityJson = JsonEntityTransform.Object2Json(snapshotEntity);
                            DataTransferObject saveSystemEvalDto = JsonEntityTransform.json2DataTransferObject(evaluateOrderService.saveSystemEval(orderEntityJson, snapshotEntityJson));
                            if (saveSystemEvalDto.getCode() == DataTransferObject.SUCCESS) {
                                cancelOrderService.updateOrderConfValue(orderSn, ProductRulesEnum0025Enum.ProductRulesEnum0025001004.getValue(), YesOrNoEnum.YES.getStr());
                            }
                        }*/

                        //更新排序，发送MQ
                        if (ProductRulesEnum0025Enum.ProductRulesEnum0025001005.getValue().equals(orderConfigEntity.getConfigCode())
                                && YesOrNoEnum.NO.getStr().equals(orderConfigEntity.getConfigValue())) {
                            try {
                                String sendRabbitMqJson = troyHouseMgtService.sendRabbitMq(orderInfoVo.getHouseFid(), orderInfoVo.getRentWay());
                                DataTransferObject sendRabbitMqDto = JsonEntityTransform.json2DataTransferObject(sendRabbitMqJson);
                                if (sendRabbitMqDto.getCode() == DataTransferObject.SUCCESS) {
                                    cancelOrderService.updateOrderConfValue(orderSn, ProductRulesEnum0025Enum.ProductRulesEnum0025001005.getValue(), YesOrNoEnum.YES.getStr());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        //锁定房源日历
                        if (ProductRulesEnum0025Enum.ProductRulesEnum0025001006.getValue().equals(orderConfigEntity.getConfigCode())
                                && YesOrNoEnum.NO.getStr().equals(orderConfigEntity.getConfigValue())) {
                            DataTransferObject upLockDto = JsonEntityTransform.json2DataTransferObject(cancelOrderService.updateOrderSysLock(JsonEntityTransform.Object2Json(orderInfoVo)));
                            if (upLockDto.getCode() == DataTransferObject.SUCCESS) {
                                    cancelOrderService.updateOrderConfValue(orderSn, ProductRulesEnum0025Enum.ProductRulesEnum0025001006.getValue(), YesOrNoEnum.YES.getStr());
                                }
                        }

                        //赠送优惠券
                        if (ProductRulesEnum0025Enum.ProductRulesEnum0025001007.getValue().equals(orderConfigEntity.getConfigCode())
                                && YesOrNoEnum.NO.getStr().equals(orderConfigEntity.getConfigValue())) {
                                MobileCouponRequest mobileCouponRequest = new MobileCouponRequest();
                                mobileCouponRequest.setUid(orderInfoVo.getUserUid());
                                mobileCouponRequest.setGroupSn(ACT_CODE);
                                String groupCouponJson = mobileCouponService.pullGroupCouponByUid(JsonEntityTransform.Object2Json(mobileCouponRequest));
                                DataTransferObject groupCouponDto = JsonEntityTransform.json2DataTransferObject(groupCouponJson);
                                if (groupCouponDto.getCode() == DataTransferObject.SUCCESS) {
                                    cancelOrderService.updateOrderConfValue(orderSn, ProductRulesEnum0025Enum.ProductRulesEnum0025001007.getValue(), YesOrNoEnum.YES.getStr());
                                }
                        }

                        //6: 当所有的措施都被执行成功==》修改t_order_csr_cancle表中的punish_statu
                        String isDoneAllPunishJson = cancelOrderService.getIsDoneAllPunish(orderSn);
                        DataTransferObject isDoneAllPunishDto = JsonEntityTransform.json2DataTransferObject(isDoneAllPunishJson);
                        if (isDoneAllPunishDto.getCode() == DataTransferObject.SUCCESS) {
                            int result = (int) isDoneAllPunishDto.getData().get("isDoneAllPunish");
                            if (result == YesOrNoEnum.YES.getCode()) {
                                cancelOrderService.updateOrderCsrCancle(orderSn, PunishedStatusEnum.DEALHAVEDONE.getCode());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【LandlordCancelOrderJob】异常e:{}", e);
        }
        LogUtil.info(LOGGER, "CancelOrderJob 执行结束");
    }

    /**
     * 获取日期
     * @author jixd
     * @created 2017年05月25日 18:17:28
     * @param
     * @return
     */
    public Date getNowAroundMonths(Date date, Integer i){
        if(!Check.NuNObj(date) && !Check.NuNObj(i)){
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH,i);
            Date beforeMonthsDate=calendar.getTime();
            return beforeMonthsDate;
        }
        return null;
    }

}
