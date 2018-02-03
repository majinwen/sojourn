package com.ziroom.minsu.services.job.evaluate;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.common.utils.ZkUtil;
import com.ziroom.minsu.services.evaluate.api.inner.EvaluateOrderService;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;
import com.ziroom.minsu.services.order.dto.EvalSynRequest;
import com.ziroom.minsu.services.order.dto.OrderRequest;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.evaluate.EvaStatusEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateUserEnum;
import com.ziroom.minsu.valenum.evaluate.OrderEvaFlagEnum;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import org.codehaus.jackson.type.TypeReference;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

/**
 * <p>定时检查订单的评价状态</p>
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
public class OrderEvaStatuJob extends AsuraJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEvaStatuJob.class);

    /**
     * 定时任务时间为每天 24:00:00 扫描
     * 定时检查订单的评价状态，
     * 说明：按条件扫描评价表中，订单未修改成已评价的评价记录，获取订单编号，查询订单，分情况处理
     */
    @Override
    public void run(JobExecutionContext jobExecutionContext) {

        Long t1 = System.currentTimeMillis();

        SmsTemplateService smsTemplat = (SmsTemplateService)ApplicationContext.getContext().getBean("basedata.smsTemplateService");

        String msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.JOB_NOTICE.getCode());

        LogUtil.info(LOGGER, "OrderEvaStatuJob 开始执行.....");
        // 59 59 23 * * ?
        //订单的api
        OrderCommonService orderCommonService = (OrderCommonService) ApplicationContext.getContext().getBean("job.orderCommonService");
        //评价的api
        EvaluateOrderService evaluateOrderService = (EvaluateOrderService) ApplicationContext.getContext().getBean("job.evaluateOrderService");
        try {
            String evaluteListJson = evaluateOrderService.getEvaluteList();

            DataTransferObject evaluteDto =  JsonEntityTransform.json2DataTransferObject(evaluteListJson);

            List<EvaluateOrderEntity> listOrderEntities =  evaluteDto.parseData("list", new TypeReference<List<EvaluateOrderEntity>>() {
            });
            Map<String, String> landlordOrderSn = new HashMap<String, String>();//房东订单map
            Map<String, String> tenantOrderSn = new HashMap<String, String>();//房客订单map
            Set<String> orderSnSet = new HashSet<String>();
            if(!Check.NuNCollection(listOrderEntities)){
                LogUtil.info(LOGGER, "评价记录中，订单评价标识为0的记录listOrderEntities={}", listOrderEntities);

                //房东和房客评价区分
                for (EvaluateOrderEntity evaluateOrder : listOrderEntities) {
                    if(evaluateOrder.getEvaUserType().intValue() == UserTypeEnum.LANDLORD.getUserType()){
                        landlordOrderSn.put( evaluateOrder.getOrderSn(),evaluateOrder.getFid());
                    }else{
                        tenantOrderSn.put( evaluateOrder.getOrderSn(),evaluateOrder.getFid());
                    }
                    orderSnSet.add(evaluateOrder.getOrderSn());
                }
            }

            OrderRequest orderRequest = new OrderRequest();
            orderRequest.setListOrderSn(new ArrayList<String>(orderSnSet));
            DataTransferObject resultDto =  JsonEntityTransform.json2DataTransferObject(orderCommonService.queryOrderByCondition(JsonEntityTransform.Object2Json(orderRequest)));

            List<OrderEntity> lisOrderEntities =  resultDto.parseData("listOrder", new TypeReference<List<OrderEntity>>() {
            });

            if(!Check.NuNCollection(lisOrderEntities)){
                List<String> landlordOrderSnList = new ArrayList<String>();//房东评价待修改订单110
                List<String> tenantOrderSnList = new ArrayList<String>();//房客评价待修改订单  101
                List<String> twoOrderSnList = new ArrayList<String>();//房东和房客都已评价  评价状态修改为 111
                LogUtil.info(LOGGER, "当前评价状态未修改的订单lisOrderEntities={}", lisOrderEntities);
                for (OrderEntity order : lisOrderEntities) {
                    String landlordValue = landlordOrderSn.get(order.getOrderSn());
                    String tenantValue = tenantOrderSn.get(order.getOrderSn());
                    if(!Check.NuNStr(landlordValue)&&!Check.NuNStr(tenantValue)){
                        twoOrderSnList.add(order.getOrderSn());
                    }else{
                        if(!Check.NuNStr(landlordValue)){
                            landlordOrderSnList.add(order.getOrderSn());
                        }
                        if(!Check.NuNStr(tenantValue)){
                            tenantOrderSnList.add(order.getOrderSn());
                        }
                    }
                }
                LogUtil.info(LOGGER, "待修改评价状态的订单编号，房东评价landlordOrderSnList={}，房客评价tenantOrderSnList={}，都评价的twoOrderSnList={}", landlordOrderSnList,tenantOrderSnList,twoOrderSnList);
                try {
                    //修改房东评价状态为110
                    if(!Check.NuNCollection(landlordOrderSnList)){
                        orderRequest.setListOrderSn(landlordOrderSnList);
                        orderRequest.setEvaStatus(EvaStatusEnum.LANFLORD_HAVE_EVA.getCode());
                        DataTransferObject landlordDto =  JsonEntityTransform.json2DataTransferObject(orderCommonService.updateEvaStatuByOrderSn(JsonEntityTransform.Object2Json(orderRequest)));
                        Object	object = landlordDto.getData().get("result");
                        if(!Check.NuNObj(object)){
                            int result = Integer.parseInt(object.toString());
                            if(result>0){
                                evaluateOrderService.updateOrderEvaFlag(landlordOrderSnList, OrderEvaFlagEnum.ORDE_HAVE_EVA.getCode(), UserTypeEnum.LANDLORD.getUserType());
                            }
                        }
                    }
                    //修改房客已评价 状态为101
                    if(!Check.NuNCollection(tenantOrderSnList)){
                        orderRequest.setListOrderSn(tenantOrderSnList);
                        orderRequest.setEvaStatus(EvaStatusEnum.TENANT_HAVE_EVA.getCode());
                        DataTransferObject tenantDto =  JsonEntityTransform.json2DataTransferObject(orderCommonService.updateEvaStatuByOrderSn(JsonEntityTransform.Object2Json(orderRequest)));
                        Object	object = tenantDto.getData().get("result");
                        if(!Check.NuNObj(object)){
                            int result = Integer.parseInt(object.toString());
                            if(result>0){
                                evaluateOrderService.updateOrderEvaFlag(tenantOrderSnList, OrderEvaFlagEnum.ORDE_HAVE_EVA.getCode(), UserTypeEnum.TENANT.getUserType());
                            }
                        }
                    }
                    //订单修改成都已评价 111
                    if(!Check.NuNCollection(twoOrderSnList)){
                        orderRequest.setListOrderSn(twoOrderSnList);
                        orderRequest.setEvaStatus(EvaStatusEnum.TWO_HAVA_EVA.getCode());
                        DataTransferObject twoDto =  JsonEntityTransform.json2DataTransferObject(orderCommonService.updateEvaStatuByOrderSn(JsonEntityTransform.Object2Json(orderRequest)));
                        Object	object = twoDto.getData().get("result");
                        if(!Check.NuNObj(object)){
                            int result = Integer.parseInt(object.toString());
                            if(result>0){
                                evaluateOrderService.updateOrderEvaFlag(twoOrderSnList, OrderEvaFlagEnum.ORDE_HAVE_EVA.getCode(),null);
                            }
                        }
                    }
                    LogUtil.info(LOGGER, "OrderEvaStatuJob 执行结束");
                } catch (Exception e) {
                    msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.JOB_NOTICE_F.getCode());
                    LogUtil.error(LOGGER, "修改评价表中订单评价标识={}", e);
                }
            }
        }catch (Exception e){
            msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.JOB_NOTICE_F.getCode());
            LogUtil.error(LOGGER, "error:{}", e);
        }

        Long t2 = System.currentTimeMillis();
        try {
            SmsRequest smsRequest  = new SmsRequest();
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("{1}", "同步订单的评价状态");
            paramsMap.put("{2}", "耗时"+(t2-t1)+"ms");
            smsRequest.setParamsMap(paramsMap);
            smsRequest.setMobile(ZkUtil.getZkSysValue(EnumMinsuConfig.minsu_mobileList.getType(),EnumMinsuConfig.minsu_mobileList.getCode()));
            smsRequest.setSmsCode(msgCode);
            smsTemplat.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
        }catch (Exception e){
            LogUtil.error(LOGGER,"定时任务发送短信失败：e：{}",e);
        }
    }


    /**
     * 获取当前的未同步评价的列表
     * @author afi
     * @return
     */
    private List<EvaluateOrderEntity> getListOrderEntities(){
        //评价的api
        EvaluateOrderService evaluateOrderService = (EvaluateOrderService) ApplicationContext.getContext().getBean("job.evaluateOrderService");
        String evaluteListJson = evaluateOrderService.getEvaluteList();
        DataTransferObject evaluteDto =  JsonEntityTransform.json2DataTransferObject(evaluteListJson);
        List<EvaluateOrderEntity> listOrderEntities =  evaluteDto.parseData("list", new TypeReference<List<EvaluateOrderEntity>>() {
        });
        return listOrderEntities;
    }


    /**
     * 获取订单map
     * @author afi
     * @param orderSnSet
     * @return
     */
    private Map<String,OrderEntity> getOrderMap(Set<String> orderSnSet){
        Map<String,OrderEntity> map = new HashMap<>();
        if (Check.NuNCollection(orderSnSet)){
            return map;
        }
        //订单的api
        OrderCommonService orderCommonService = (OrderCommonService) ApplicationContext.getContext().getBean("job.orderCommonService");
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setListOrderSn(new ArrayList<String>(orderSnSet));
        DataTransferObject resultDto =  JsonEntityTransform.json2DataTransferObject(orderCommonService.queryOrderByCondition(JsonEntityTransform.Object2Json(orderRequest)));

        List<OrderEntity> listOrder =  resultDto.parseData("listOrder", new TypeReference<List<OrderEntity>>() {
        });
        if (Check.NuNCollection(listOrder)){
            for (OrderEntity orderEntity : listOrder) {
                map.put(orderEntity.getOrderSn(),orderEntity);
            }
        }
        return map;
    }
    /**
     * 同步评价状态到订单
     */
    private void sysEvaluateStatus(){
        try {
            //获取当前未同步的订单信息
            List<EvaluateOrderEntity> listOrderEntities = getListOrderEntities();
            if (Check.NuNCollection(listOrderEntities)){
                LogUtil.info(LOGGER, "当前评价未同步的条数为0，直接返回");
                //当前同步列表为空直接返回
                return;
            }
            Set<String> orderSnSet = new HashSet<String>();
            //房东和房客评价区分
            for (EvaluateOrderEntity evaluateOrder : listOrderEntities) {
                orderSnSet.add(evaluateOrder.getOrderSn());
            }
            //获取当前的订单列表
            Map<String,OrderEntity>  orderMap = getOrderMap(orderSnSet);

            //获取当前的评价map
            Map<String,EvalStatus>  orderPjStatusMap = new HashMap<>();
            //获取当前的初见map
            Map<String,EvalStatus>  orderCjStatusMap = new HashMap<>();
            //房东和房客评价区分
            for (EvaluateOrderEntity evaluateOrder : listOrderEntities) {
                String orderSn = evaluateOrder.getOrderSn();
                OrderEntity orderEntity = orderMap.get(orderSn);
                if (Check.NuNObj(orderEntity)){
                    LogUtil.error(LOGGER, "同步评价状态 订单信息不存在 orderSn={}", orderSn);
                    continue;
                }
                EvaluateUserEnum userEnum = EvaluateUserEnum.getEvaluateUserByCode(evaluateOrder.getEvaUserType());
                if (userEnum.getCode() == EvaluateUserEnum.LAN.getCode()
                        || userEnum.getCode() == EvaluateUserEnum.TEN.getCode() ){
                    //处理评价
                    EvaStatusEnum evaStatusEnum = EvaStatusEnum.getEvaStatusByCode(orderEntity.getEvaStatus());
                    if (Check.NuNObj(evaStatusEnum)){
                        LogUtil.error(LOGGER, "异常的评价状态 orderSn={}", orderSn);
                        continue;
                    }
                    Integer nextStatus = evaStatusEnum.getNextStatus(userEnum);
                    if (Check.NuNObj(nextStatus)){
                        LogUtil.error(LOGGER, "异常的评价状态 orderSn={}", orderSn);
                        continue;
                    }else {
                        orderEntity.setEvaStatus(nextStatus);
                    }
                    EvalStatus evalStatus = new EvalStatus();
                    evalStatus.setFid(evaluateOrder.getFid());
                    evalStatus.setNextStatus(nextStatus);
                    evalStatus.setOrderSn(orderSn);
                    orderPjStatusMap.put(orderSn,evalStatus);
                }
            }
            //同步评价状态
            dealStatusSyn(orderPjStatusMap,false);
            //同步初见
            //dealStatusSyn(orderCjStatusMap,true);
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
        }
    }


    /**
     * 同步评价状态
     * @author afi
     * @param orderEvalStatusMap
     * @param isCj
     */
    private void dealStatusSyn(Map<String,EvalStatus>  orderEvalStatusMap,boolean isCj){

        LogUtil.error(LOGGER,"需要同步的信息：{}",JsonEntityTransform.Object2Json(orderEvalStatusMap));
        if (Check.NuNObj(orderEvalStatusMap)){
            //同步的评价信息为空直接返回
            return;
        }
        //将当前的同步状态分组
        Map<Integer,List<EvalStatus> > map = new HashMap<>();
        for (Map.Entry<String, EvalStatus> entry : orderEvalStatusMap.entrySet()) {
            EvalStatus evalStatus = entry.getValue();
            Integer nextStatus = evalStatus.getNextStatus();
            if (!map.containsKey(nextStatus)){
                map.put(nextStatus,new ArrayList<EvalStatus>());
            }
            map.get(nextStatus).add(evalStatus);
        }
        for (Integer st : map.keySet()) {
            List<String> orderSnList = new ArrayList<>();
            List<String> fidList = new ArrayList<>();
            List<EvalStatus> list = map.get(st);
            for (EvalStatus evalStatus : list) {
                orderSnList.add(evalStatus.getOrderSn());
                fidList.add(evalStatus.getFid());
            }
            this.updateStatus(orderSnList,fidList,st,isCj);
        }
    }

    /**
     * 直接更新同步状态
     * @author afi
     * @param orderSnList
     * @param fidList
     * @param nextStatus
     * @param isCj
     */
    private void updateStatus(List<String>  orderSnList,List<String>  fidList,Integer nextStatus,boolean isCj){

        LogUtil.error(LOGGER,"更新订单状态：",JsonEntityTransform.Object2Json(orderSnList));

        if (Check.NuNCollection(orderSnList)){
            return;
        }
        EvalSynRequest evalSynRequest = new EvalSynRequest();
        //修改房东评价状态为110
        if(!Check.NuNCollection(orderSnList)){
            evalSynRequest.setListOrderSn(orderSnList);
            if (isCj){
                evalSynRequest.setCjStatus(nextStatus);
            }else {
                evalSynRequest.setEvlStatus(nextStatus);
            }
            //订单的api
            OrderCommonService orderCommonService = (OrderCommonService) ApplicationContext.getContext().getBean("job.orderCommonService");
            //评价的api
            EvaluateOrderService evaluateOrderService = (EvaluateOrderService) ApplicationContext.getContext().getBean("job.evaluateOrderService");
            DataTransferObject landlordDto =  JsonEntityTransform.json2DataTransferObject(orderCommonService.updateStatuByOrderSn(JsonEntityTransform.Object2Json(evalSynRequest)));
            LogUtil.error(LOGGER,"更新订单结果：",landlordDto.toJsonString());
            Object	object = landlordDto.getData().get("result");
            if(!Check.NuNObj(object)){
                int result = Integer.parseInt(object.toString());
                if(result>0){
                    //将当前的评价状态标记为已同步
                    evaluateOrderService.updateSynOrderEvaFlagByFid(fidList);
                }
            }
        }
    }



    /**
     * 评价状态
     */
    private  class EvalStatus{

        private String fid;

        private String orderSn;

        private Integer nextStatus;

        public String getFid() {
            return fid;
        }

        public void setFid(String fid) {
            this.fid = fid;
        }

        public Integer getNextStatus() {
            return nextStatus;
        }

        public void setNextStatus(Integer nextStatus) {
            this.nextStatus = nextStatus;
        }

        public String getOrderSn() {
            return orderSn;
        }

        public void setOrderSn(String orderSn) {
            this.orderSn = orderSn;
        }
    }
}
