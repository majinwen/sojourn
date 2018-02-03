package com.ziroom.minsu.services.job.customer;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBehaviorEntity;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerBehaviorService;
import com.ziroom.minsu.services.customer.dto.CustomerBehaviorRequest;
import com.ziroom.minsu.services.evaluate.api.inner.EvaluateOrderService;
import com.ziroom.minsu.services.evaluate.dto.EvaluateRequest;
import com.ziroom.minsu.services.evaluate.entity.TenantEvaluateVo;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;
import com.ziroom.minsu.services.order.dto.OrderRequest;
import com.ziroom.minsu.valenum.customer.CustomerBehaviorRoleEnum;
import com.ziroom.minsu.valenum.customer.LandlordBehaviorEnum;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;
import org.codehaus.jackson.type.TypeReference;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * <p>刷新自如驿索引</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author zhangyl
 * @version 1.0
 * @since 1.0
 */
public class CustomerBehaviorJob extends AsuraJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerBehaviorJob.class);

    @Override
    public void run(JobExecutionContext jobExecutionContext) {

        String logPreStr = "[定时任务补偿用户行为Job]-";
        try {

            CustomerBehaviorService customerBehaviorService = (CustomerBehaviorService) ApplicationContext.getContext().getBean("job.customerBehaviorService");

            //处理评价用户行为
            LogUtil.info(LOGGER, logPreStr + "处理评价用户行为--开始--");
            delEvaluateBehavior(customerBehaviorService);
            LogUtil.info(LOGGER, logPreStr + "处理评价用户行为--结束--");

            //处理拒绝订单行为
            LogUtil.info(LOGGER, logPreStr + "处理拒绝订单行为--开始--");
            delRefuseOrderBehavior(customerBehaviorService);
            LogUtil.info(LOGGER, logPreStr + "处理拒绝订单行为--结束--");

            //处理取消订单行为
            LogUtil.info(LOGGER, logPreStr + "处理取消订单行为--开始--");
            delCancleOrderBehavior(customerBehaviorService);
            LogUtil.info(LOGGER, logPreStr + "处理取消订单行为--结束--");

        } catch (Exception e) {
            LogUtil.error(LOGGER, logPreStr + "ERROR e={}", e);
        }
    }

    /**
     * 分页处理评价用户行为
     *
     * @param
     * @return
     * @author zhangyl2
     * @created 2017年10月12日 16:17
     */
    private void delEvaluateBehavior(CustomerBehaviorService customerBehaviorService) throws Exception {

        //查询昨天的评价行为
        LandlordBehaviorEnum behaviorEnum = LandlordBehaviorEnum.ORDER_SINGLE_EVALUATE;
        List<String> proveFidList = queryYesterdayBehavior(customerBehaviorService, behaviorEnum);

        //排除proveFidList查询昨天遗漏的评价行为
        EvaluateOrderService evaluateOrderService = (EvaluateOrderService) ApplicationContext.getContext().getBean("evaluate.evaluateOrderService");
        EvaluateRequest evaluateRequest = new EvaluateRequest();
        Date now = new Date();
        evaluateRequest.setListFid(proveFidList);
        evaluateRequest.setStartTime(DateUtil.dateFormat(DateSplitUtil.getYesterday(now), "yyyy-MM-dd 00:00:00"));
        evaluateRequest.setEndTime(DateUtil.dateFormat(DateSplitUtil.getYesterday(now), "yyyy-MM-dd 23:59:59"));
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(evaluateOrderService.queryTenantEvaluateForCustomerBehaviorJob(evaluateRequest.toJsonStr()));
        List<TenantEvaluateVo> tenantEvaluateVoList = resultDto.parseData("tenantEvaluateList", new TypeReference<List<TenantEvaluateVo>>() {
        });

        //筛选处理生成行为
        if (!Check.NuNCollection(tenantEvaluateVoList)) {
            for (TenantEvaluateVo tenantEvaluateVo : tenantEvaluateVoList) {
                int eva = 2;
                // 评价存在单一评价维度评分≤2分/条
                if (tenantEvaluateVo.getHouseClean() <= eva
                        || tenantEvaluateVo.getDescriptionMatch() <= eva
                        || tenantEvaluateVo.getSafetyDegree() <= eva
                        || tenantEvaluateVo.getTrafficPosition() <= eva
                        || tenantEvaluateVo.getCostPerformance() <= eva) {

                    String logPreStr = "[定时任务补偿用户行为]" + behaviorEnum.getDesc();

                    LogUtil.info(LOGGER, logPreStr + "-评价信息={}", tenantEvaluateVo);

                    CustomerBehaviorEntity customerBehaviorEntity = new CustomerBehaviorEntity();
                    customerBehaviorEntity.setProveFid(tenantEvaluateVo.getFid());
                    customerBehaviorEntity.setProveParam("orderSn=" + tenantEvaluateVo.getOrderSn());
                    customerBehaviorEntity.setAttribute(behaviorEnum.getAttribute());
                    customerBehaviorEntity.setRole(CustomerBehaviorRoleEnum.LANDLORD.getCode());
                    customerBehaviorEntity.setUid(tenantEvaluateVo.getRatedUserUid());
                    customerBehaviorEntity.setType(behaviorEnum.getType());
                    customerBehaviorEntity.setScore(behaviorEnum.getScore());
                    customerBehaviorEntity.setCreateFid(tenantEvaluateVo.getRatedUserUid());
                    customerBehaviorEntity.setCreateType(behaviorEnum.getCreateType());

                    customerBehaviorEntity.setRemark(String.format("行为说明={%s}, 房客uid={%s}, 房东uid={%s}, 订单编号={%s}",
                            behaviorEnum.getDesc(),
                            tenantEvaluateVo.getEvaUserUid(),
                            tenantEvaluateVo.getRatedUserUid(),
                            tenantEvaluateVo.getOrderSn()));

                    customerBehaviorEntity.setCreateDate(tenantEvaluateVo.getLastModifyDate());

                    LogUtil.info(LOGGER, logPreStr + "-行为信息={}", customerBehaviorEntity);

                    customerBehaviorService.saveCustomerBehavior(JsonEntityTransform.Object2Json(customerBehaviorEntity));
                }
            }
        }
    }

    /**
     * 处理拒绝订单行为
     *
     * @param
     * @return
     * @author zhangyl2
     * @created 2017年10月12日 20:13
     */
    private void delRefuseOrderBehavior(CustomerBehaviorService customerBehaviorService) throws Exception {

        //查询昨天的评价行为
        LandlordBehaviorEnum behaviorEnum = LandlordBehaviorEnum.REFUSE_APPLY;
        List<String> proveFidList = queryYesterdayBehavior(customerBehaviorService, behaviorEnum);


        OrderCommonService orderCommonService = (OrderCommonService) ApplicationContext.getContext().getBean("job.orderCommonService");
        OrderRequest orderRequest = new OrderRequest();
        Date now = new Date();
        orderRequest.setOrderStatus(OrderStatusEnum.REFUSED.getOrderStatus());
        orderRequest.setStartTime(DateUtil.dateFormat(DateSplitUtil.getYesterday(now), "yyyy-MM-dd 00:00:00"));
        orderRequest.setEndTime(DateUtil.dateFormat(DateSplitUtil.getYesterday(now), "yyyy-MM-dd 23:59:59"));
        orderRequest.setListOrderSn(proveFidList);
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(orderCommonService.queryOrderForCustomerBehaviorJob(orderRequest.toJsonStr()));
        List<OrderEntity> orderList = resultDto.parseData("listOrder", new TypeReference<List<OrderEntity>>() {
        });

        if (!Check.NuNCollection(orderList)) {
            for (OrderEntity orderEntity : orderList) {

                String logPreStr = "[定时任务补偿用户行为]" + behaviorEnum.getDesc();

                LogUtil.info(LOGGER, logPreStr + "-订单信息={}", orderEntity);

                CustomerBehaviorEntity customerBehaviorEntity = new CustomerBehaviorEntity();
                customerBehaviorEntity.setProveFid(orderEntity.getOrderSn());
                customerBehaviorEntity.setAttribute(behaviorEnum.getAttribute());
                customerBehaviorEntity.setRole(CustomerBehaviorRoleEnum.LANDLORD.getCode());
                customerBehaviorEntity.setUid(orderEntity.getLandlordUid());
                customerBehaviorEntity.setType(behaviorEnum.getType());
                customerBehaviorEntity.setScore(behaviorEnum.getScore());
                customerBehaviorEntity.setCreateFid(orderEntity.getLandlordUid());
                customerBehaviorEntity.setCreateType(behaviorEnum.getCreateType());

                customerBehaviorEntity.setRemark(String.format("行为说明={%s}, 订单编号={%s}, 房东uid={%s}",
                        behaviorEnum.getDesc(),
                        orderEntity.getOrderSn(),
                        orderEntity.getLandlordUid()));

                customerBehaviorEntity.setCreateDate(orderEntity.getLastModifyDate());

                LogUtil.info(LOGGER, logPreStr + "-行为信息={}", customerBehaviorEntity);

                customerBehaviorService.saveCustomerBehavior(JsonEntityTransform.Object2Json(customerBehaviorEntity));

            }
        }
    }

    /**
     * 处理取消订单行为
     *
     * @param
     * @return
     * @author zhangyl2
     * @created 2017年10月12日 20:14
     */
    private void delCancleOrderBehavior(CustomerBehaviorService customerBehaviorService) throws Exception {
        //查询昨天的评价行为
        LandlordBehaviorEnum behaviorEnum = LandlordBehaviorEnum.APPLY_CANCEL_ORDER;
        List<String> proveFidList = queryYesterdayBehavior(customerBehaviorService, behaviorEnum);


        OrderCommonService orderCommonService = (OrderCommonService) ApplicationContext.getContext().getBean("job.orderCommonService");
        OrderRequest orderRequest = new OrderRequest();
        Date now = new Date();
        orderRequest.setOrderStatus(OrderStatusEnum.CANCEL_LAN_APPLY.getOrderStatus());
        orderRequest.setStartTime(DateUtil.dateFormat(DateSplitUtil.getYesterday(now), "yyyy-MM-dd 00:00:00"));
        orderRequest.setEndTime(DateUtil.dateFormat(DateSplitUtil.getYesterday(now), "yyyy-MM-dd 23:59:59"));
        orderRequest.setListOrderSn(proveFidList);
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(orderCommonService.queryOrderForCustomerBehaviorJob(orderRequest.toJsonStr()));
        List<OrderEntity> orderList = resultDto.parseData("listOrder", new TypeReference<List<OrderEntity>>() {
        });

        if (!Check.NuNCollection(orderList)) {
            for (OrderEntity orderEntity : orderList) {

                String logPreStr = "[定时任务补偿用户行为]" + behaviorEnum.getDesc();

                LogUtil.info(LOGGER, logPreStr + "-订单信息={}", orderEntity);

                CustomerBehaviorEntity customerBehaviorEntity = new CustomerBehaviorEntity();
                customerBehaviorEntity.setProveFid(orderEntity.getOrderSn());
                customerBehaviorEntity.setAttribute(behaviorEnum.getAttribute());
                customerBehaviorEntity.setRole(CustomerBehaviorRoleEnum.LANDLORD.getCode());
                customerBehaviorEntity.setUid(orderEntity.getLandlordUid());
                customerBehaviorEntity.setType(behaviorEnum.getType());
                customerBehaviorEntity.setScore(behaviorEnum.getScore());
                customerBehaviorEntity.setCreateFid(orderEntity.getLandlordUid());
                customerBehaviorEntity.setCreateType(behaviorEnum.getCreateType());

                customerBehaviorEntity.setRemark(String.format("行为说明={%s}, 订单编号={%s}, 房东uid={%s}",
                        behaviorEnum.getDesc(),
                        orderEntity.getOrderSn(),
                        orderEntity.getLandlordUid()));

                customerBehaviorEntity.setCreateDate(orderEntity.getLastModifyDate());

                LogUtil.info(LOGGER, logPreStr + "-行为信息={}", customerBehaviorEntity);

                customerBehaviorService.saveCustomerBehavior(JsonEntityTransform.Object2Json(customerBehaviorEntity));

            }
        }
    }

    /**
     * 查询昨天的行为
     *
     * @param
     * @return
     * @author zhangyl2
     * @created 2017年10月13日 11:49
     */
    private List<String> queryYesterdayBehavior(CustomerBehaviorService customerBehaviorService, LandlordBehaviorEnum behaviorEnum) {
        //查询昨天的行为
        CustomerBehaviorRequest customerBehaviorRequest = new CustomerBehaviorRequest();
        customerBehaviorRequest.setType(behaviorEnum.getType());
        Date now = new Date();
        customerBehaviorRequest.setStartTime(DateUtil.dateFormat(DateSplitUtil.getYesterday(now), "yyyy-MM-dd 00:00:00"));
        customerBehaviorRequest.setEndTime(DateUtil.dateFormat(DateSplitUtil.getYesterday(now), "yyyy-MM-dd 23:59:59"));

        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(customerBehaviorService.queryCustomerBehaviorProveFidsForJob(customerBehaviorRequest.toJsonStr()));
        return resultDto.parseData("proveFidList", new TypeReference<List<String>>() {
        });
    }

}
