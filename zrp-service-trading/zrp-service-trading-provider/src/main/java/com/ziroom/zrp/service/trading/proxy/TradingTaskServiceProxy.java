package com.ziroom.zrp.service.trading.proxy;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.JpushRequest;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.basedata.entity.entityenum.ServiceLineEnum;
import com.ziroom.minsu.services.common.constant.JpushConst;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.jpush.base.MessageTypeEnum;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.valenum.customer.JpushPersonType;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import com.ziroom.minsu.valenum.zrpenum.ContractTradingEnum;
import com.ziroom.minsu.valenum.zrpenum.ContractTradingEnum008;
import com.ziroom.zrp.houses.entity.EmployeeEntity;
import com.ziroom.zrp.service.houses.api.EmployeeService;
import com.ziroom.zrp.service.trading.api.TradingTaskService;
import com.ziroom.zrp.service.trading.dto.ContractPageDto;
import com.ziroom.zrp.service.trading.dto.ContractRoomDto;
import com.ziroom.zrp.service.trading.dto.finance.ModifyReceiptBillRequest;
import com.ziroom.zrp.service.trading.dto.finance.ReceiptBillRequest;
import com.ziroom.zrp.service.trading.entity.CloseContractNotifyVo;
import com.ziroom.zrp.service.trading.entity.DeliveryContractNotifyVo;
import com.ziroom.zrp.service.trading.entity.SyncContractVo;
import com.ziroom.zrp.service.trading.proxy.commonlogic.WaterClearingLogic;
import com.ziroom.zrp.service.trading.service.FinReceiBillServiceImpl;
import com.ziroom.zrp.trading.entity.FinReceiBillDetailEntity;
import com.ziroom.zrp.service.trading.proxy.commonlogic.FinanceBaseCall;
import com.ziroom.zrp.service.trading.proxy.commonlogic.RentContractLogic;
import com.ziroom.zrp.service.trading.service.FinReceiBillDetailServiceImpl;
import com.ziroom.zrp.service.trading.service.RentContractServiceImpl;
import com.ziroom.zrp.service.trading.service.RentItemDeliveryServiceImpl;
import com.ziroom.zrp.service.trading.valenum.ContractCloseTypeEnum;
import com.ziroom.zrp.service.trading.valenum.ContractDataVersionEnum;
import com.ziroom.zrp.service.trading.valenum.ContractStatusEnum;
import com.ziroom.zrp.service.trading.valenum.ZKConfigEnum;
import com.ziroom.zrp.service.trading.valenum.base.IsDelEnum;
import com.ziroom.zrp.service.trading.valenum.base.IsPayEnum;
import com.ziroom.zrp.service.trading.valenum.delivery.DeliveryLogMsgTimeTypeEnum;
import com.ziroom.zrp.service.trading.valenum.delivery.DeliveryLogMsgTypeEnum;
import com.ziroom.zrp.service.trading.valenum.finance.DocumentTypeEnum;
import com.ziroom.zrp.trading.entity.DeliveryNotifyLogEntity;
import com.ziroom.zrp.trading.entity.MeterDetailEntity;
import com.ziroom.zrp.trading.entity.RentContractEntity;
import com.ziroom.zrp.trading.entity.RentDetailEntity;
import com.zra.common.constant.ContractMsgConstant;
import com.zra.common.dto.base.BasePageParamDto;
import com.zra.common.enums.SmsTemplateCodeEnum;
import com.zra.common.utils.DateUtilFormate;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>交易服务相关定时任务</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月26日 15:46
 * @since 1.0
 */

@Component("trading.tradingTaskServiceProxy")
public class TradingTaskServiceProxy implements TradingTaskService{

    private static final Logger LOGGER = LoggerFactory.getLogger(TradingTaskServiceProxy.class);

    @Resource(name = "trading.rentContractServiceImpl")
    private RentContractServiceImpl rentContractServiceImpl;

    @Resource(name = "trading.rentItemDeliveryServiceImpl")
    private RentItemDeliveryServiceImpl rentItemDeliveryServiceImpl;

    @Resource(name = "houses.employeeService")
    private EmployeeService employeeService;

    @Resource(name = "trading.callFinanceServiceProxy")
    private CallFinanceServiceProxy callFinanceServiceProxy;

    @Resource(name = "trading.financeBaseCall")
    private FinanceBaseCall financeBaseCall;

    @Resource(name = "trading.rentContractLogic")
    private RentContractLogic rentContractLogic;

    @Resource(name = "trading.checkSignServiceProxy")
    private CheckSignServiceProxy checkSignServiceProxy;

    @Resource(name="basedata.smsTemplateService")
    private SmsTemplateService smsTemplateService;

    @Resource(name="trading.rentContractServiceProxy")
    private RentContractServiceProxy rentContractServiceProxy;

    @Resource(name = "basedata.cityTemplateService")
    private CityTemplateService cityTemplateService;

    @Resource(name = "trading.finReceiBillDetailServiceImpl")
    private FinReceiBillDetailServiceImpl finReceiBillDetailServiceImpl;

    @Resource(name = "trading.finReceiBillServiceImpl")
    private FinReceiBillServiceImpl finReceiBillServiceImpl;

    @Resource(name = "trading.waterClearingLogic")
    private WaterClearingLogic waterClearingLogic;

    @Value("#{'${METER_DELETE_TIME}'.trim()}")
    private String meterDeleteTime;


    /**
     * 物业交割提示信息  自动确认物业交割
     * @author jixd
     * @created 2017年10月09日 14:33:51
     * @param
     * @return
     */
    @Override
    public void notifyUserDelivery() {
        LogUtil.info(LOGGER,"【notifyUserDelivery】物业交割定时任务提示开始");
        long start = System.currentTimeMillis();
        try{
            DataTransferObject timeDto = JsonEntityTransform.json2DataTransferObject(cityTemplateService.getTextValueForCommon(ServiceLineEnum.ZRP.getCode(), ContractTradingEnum.ContractTradingEnum007.getValue()));
            if (timeDto.getCode() == DataTransferObject.ERROR){
                LogUtil.error(LOGGER,"【notifyUserDelivery】获取配置时间错误error={}",timeDto.toJsonString());
                return;
            }
            String time = timeDto.parseData("textValue", new TypeReference<String>() {});
            LogUtil.info(LOGGER,"查询获取得时间time={}",time);
            final int hour48 = Integer.parseInt(time);
            int page = 1;
            int rows = 50;
            ContractPageDto contractPageDto = new ContractPageDto();
            contractPageDto.setRows(rows);
            for (;;){
                LogUtil.info(LOGGER,"page={},rows={}",page,rows);
                contractPageDto.setPage(page);
                PagingResult<DeliveryContractNotifyVo> pagingResult = rentItemDeliveryServiceImpl.listUnDeliveryContract(contractPageDto);
                List<DeliveryContractNotifyVo> list = pagingResult.getRows();
                if (Check.NuNCollection(list)){
                    LogUtil.info(LOGGER,"查询结果为空直接返回");
                    break;
                }
                page++;
                for (DeliveryContractNotifyVo notifyVo : list){
                    String contractId = notifyVo.getContractId();
                    Date firstPayTime = notifyVo.getFirstPayTime();
                    LogUtil.info(LOGGER,"【定时任务】当前合同 contract={}",notifyVo.toString());
                    if (Check.NuNObj(firstPayTime)){
                        continue;
                    }
                    Date date24 = DateUtilFormate.addHours(firstPayTime,DeliveryLogMsgTimeTypeEnum.TIME_24.getHour());
                    Date date36 = DateUtilFormate.addHours(firstPayTime,DeliveryLogMsgTimeTypeEnum.TIME_36.getHour());
                    Date date48 = DateUtilFormate.addHours(firstPayTime,hour48);

                    String timeStr = DateUtilFormate.formatDateToString(date48,DateUtilFormate.DATEFORMAT_6);

                    Date now = new Date();
                    if (date24.after(now)){
                        continue;
                    }else if (date24.before(now) && date36.after(now)){
                        LogUtil.info(LOGGER,"【定时任务】合同支付时间在24小时到36小时之间 contractId={}",notifyVo.getContractId());
                        //超过24小时发送短信
                        sendTipMsg(notifyVo, DeliveryLogMsgTimeTypeEnum.TIME_24.getCode(),contractId,timeStr,hour48);
                    } else if (date36.before(now) && date48.after(now)){
                        LogUtil.info(LOGGER,"【定时任务】合同支付时间超过36小时 contractId={}",notifyVo.getContractId());
                        //超过36小时发送短信
                        sendTipMsg(notifyVo, DeliveryLogMsgTimeTypeEnum.TIME_36.getCode(),contractId,timeStr, hour48);
                    }else if ((date48.before(now))){
                        LogUtil.info(LOGGER,"【定时任务】开始确认物业交割,contractId={}",notifyVo.getContractId());
                        //48小时自动确认物业交割    如果有没有未支付账单   超过100小时 不在处理
                        confirmDelivery(notifyVo);
                    }
                }
            }
        }catch (Exception e){
            LogUtil.error(LOGGER,"【notifyUserDelivery】提示物业交割异常错误error={}",e);
        }
        LogUtil.info(LOGGER,"【notifyUserDelivery】物业交割结束，耗时 time={}",System.currentTimeMillis() - start);
    }

    /**
     * 发送短信
     * @author jixd
     * @created 2017年10月09日 14:05:44
     * @param
     * @param hour48
     * @return
     */
    private void sendTipMsg(DeliveryContractNotifyVo notifyVo, int timeType, String contractId, String timeStr, int hour48){
        List<DeliveryNotifyLogEntity> deliveryNotifyLogEntityList = rentItemDeliveryServiceImpl.listDeliveryNotifyLogByContractId(contractId);
        //查询管家电话
        DataTransferObject employeeDto = JsonEntityTransform.json2DataTransferObject(employeeService.findEmployeeByCode(notifyVo.getZoCode()));
        EmployeeEntity employeeEntity = employeeDto.parseData("employeeEntity", new TypeReference<EmployeeEntity>() {});
        if (!Check.NuNCollection(deliveryNotifyLogEntityList)){
            if (!deliveryNotifyLogEntityList.stream().anyMatch(d -> (d.getTimeType() == timeType && d.getMsgType() == DeliveryLogMsgTypeEnum.USER.getCode()))){
                sendDeliveryMsgUser(notifyVo, timeType, timeStr,hour48);
            }

            if (!deliveryNotifyLogEntityList.stream().anyMatch(d -> (d.getTimeType() == timeType && d.getMsgType() == DeliveryLogMsgTypeEnum.ZO.getCode()))){
                sendDeliveryMsgZo(notifyVo, timeType, employeeEntity,hour48);
            }
        }else{
            //直接发送短信给用户和管家
            sendDeliveryMsgUser(notifyVo, timeType, timeStr,hour48);
            sendDeliveryMsgZo(notifyVo, timeType, employeeEntity, hour48);

        }
    }

    private void sendDeliveryMsgZo(DeliveryContractNotifyVo notifyVo, int timeType, EmployeeEntity employeeEntity, int hour48) {
        String smsCode = ValueUtil.getStrValue(SmsTemplateCodeEnum.DELIVERY_ZO_MSG.getCode());
        int payLifeFee = isPayLifeFee(notifyVo);
        if (payLifeFee == IsPayEnum.NO.getCode() || payLifeFee == IsPayEnum.PART.getCode()){
            smsCode = ValueUtil.getStrValue(SmsTemplateCodeEnum.DELIVERY_ZO_FEE_MSG.getCode());
        }
        LogUtil.info(LOGGER,"发送物业交割提示信息给管家 contractino={},timeType={},employee={}",JsonEntityTransform.Object2Json(notifyVo),timeType,JsonEntityTransform.Object2Json(employeeEntity));
        //最后剩余小时数
        int lastHour = hour48 - DeliveryLogMsgTimeTypeEnum.getByCode(timeType).getHour();
        //发送短信给管家
        SmsRequest smsRequest = new SmsRequest();
        smsRequest.setMobile(employeeEntity.getFmobile());
        Map<String, String> paramsMap=new HashMap<>();
        paramsMap.put(SysConst.MOBILE_NATION_CODE_KEY, "86");
        paramsMap.put("{1}",notifyVo.getProjectName()+notifyVo.getRoomName());
        paramsMap.put("{2}",String.valueOf(lastHour));
        smsRequest.setParamsMap(paramsMap);
        smsRequest.setSmsCode(smsCode);
        String msgResult = smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
        LogUtil.info(LOGGER,"短信发送结果={}",msgResult);
        DataTransferObject msgDto = JsonEntityTransform.json2DataTransferObject(msgResult);
        if (msgDto.getCode() == DataTransferObject.SUCCESS){
            DeliveryNotifyLogEntity deliveryNotifyLogEntity = new DeliveryNotifyLogEntity();
            deliveryNotifyLogEntity.setContractId(notifyVo.getContractId());
            deliveryNotifyLogEntity.setUserName(employeeEntity.getFname());
            deliveryNotifyLogEntity.setPhoneNum(employeeEntity.getFmobile());
            deliveryNotifyLogEntity.setMsgType(DeliveryLogMsgTypeEnum.ZO.getCode());
            deliveryNotifyLogEntity.setTimeType(timeType);
            rentItemDeliveryServiceImpl.saveDeliveryNotifyLog(deliveryNotifyLogEntity);
        }
    }

    private void sendDeliveryMsgUser(DeliveryContractNotifyVo notifyVo, int timeType, String timeStr,int hour48) {
        String smsCode = ValueUtil.getStrValue(SmsTemplateCodeEnum.DELIVERY_USER_MSG.getCode());
        int payLifeFee = isPayLifeFee(notifyVo);
        if (payLifeFee == IsPayEnum.NO.getCode() || payLifeFee == IsPayEnum.PART.getCode()){
            smsCode = ValueUtil.getStrValue(SmsTemplateCodeEnum.DELIVERY_USER_FEE_MSG.getCode());
        }
        //最后剩余小时数
        int lastHour = hour48 - DeliveryLogMsgTimeTypeEnum.getByCode(timeType).getHour();
        //发送短信给用户
        SmsRequest smsRequest = new SmsRequest();
        smsRequest.setMobile(notifyVo.getCustomerMobile());
        Map<String, String> paramsMap=new HashMap<>();
        paramsMap.put(SysConst.MOBILE_NATION_CODE_KEY, "86");
        paramsMap.put("{1}",notifyVo.getProjectName()+notifyVo.getRoomName());
        paramsMap.put("{2}",timeStr);
        paramsMap.put("{3}",String.valueOf(lastHour));
        paramsMap.put("{4}",notifyVo.getProjectName());
        //替換内容
        if (payLifeFee == IsPayEnum.NO.getCode()){
            paramsMap.put("{3}",paramsMap.get("{4}"));
        }
        smsRequest.setParamsMap(paramsMap);
        smsRequest.setSmsCode(smsCode);
        LogUtil.info(LOGGER,"发送物业交割提示信息给用户 contractino={},timeType={},timeStr={}",JsonEntityTransform.Object2Json(notifyVo),timeType,timeStr);
        String msgResult = smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
        LogUtil.info(LOGGER,"短信发送结果={}",msgResult);
        DataTransferObject msgDto = JsonEntityTransform.json2DataTransferObject(msgResult);

        //发送系统消息给用户
        JpushRequest jpushRequest = new JpushRequest();
        jpushRequest.setParamsMap(paramsMap);
        jpushRequest.setJpushPersonType(JpushPersonType.ONE.getCode());
        jpushRequest.setMessageType(MessageTypeEnum.MESSAGE.getCode());
        jpushRequest.setSmsCode(smsCode);
        jpushRequest.setTitle(ContractMsgConstant.DELIVERY_CUSTOMER_PUSH_TITLE);
        jpushRequest.setUid(notifyVo.getCustomerUid());
        Map<String, String> extrasMap = new HashMap<>();
        extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.ZRP_MSG_BODY_TYPE_VALUE);
        extrasMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_ALL);
        extrasMap.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));
        extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY,JpushConst.MSG_SUB_TYPE_VALULE_4);
        jpushRequest.setExtrasMap(extrasMap);
        smsTemplateService.jpushByCode(JsonEntityTransform.Object2Json(jpushRequest));


        if (msgDto.getCode() == DataTransferObject.SUCCESS){
            DeliveryNotifyLogEntity deliveryNotifyLogEntity = new DeliveryNotifyLogEntity();
            deliveryNotifyLogEntity.setContractId(notifyVo.getContractId());
            deliveryNotifyLogEntity.setUserName(notifyVo.getCustomerName());
            deliveryNotifyLogEntity.setPhoneNum(notifyVo.getCustomerMobile());
            deliveryNotifyLogEntity.setMsgType(DeliveryLogMsgTypeEnum.USER.getCode());
            deliveryNotifyLogEntity.setTimeType(timeType);
            rentItemDeliveryServiceImpl.saveDeliveryNotifyLog(deliveryNotifyLogEntity);
        }
    }

    /**
     * 确认物业交割
     * @author jixd
     * @created 2017年10月09日 14:35:47
     * @param
     * @return
     */
    private void confirmDelivery(DeliveryContractNotifyVo notifyVo){
        try{
            if(isPayLifeFee(notifyVo) == IsPayEnum.YES.getCode()){
                LogUtil.info(LOGGER,"【物业交割定时任务】生活费用已支付,可以确认物业交割,contractId={}",notifyVo.getContractId());
                ContractRoomDto contractRoomDto = new ContractRoomDto();
                contractRoomDto.setContractId(notifyVo.getContractId());
                contractRoomDto.setRoomId(notifyVo.getRoomId());
                MeterDetailEntity meterDetailEntity = rentItemDeliveryServiceImpl.findMeterDetailById(contractRoomDto);
                if (Check.NuNObj(meterDetailEntity)){
                    LogUtil.info(LOGGER,"【物业交割定时任务】管家未录入物业交割,不能够自动确认,contractId={}",notifyVo.getContractId());
                    return;
                }
                //更新物业交割
                DataTransferObject resultDto = rentContractLogic.confirmDelivery(notifyVo.getContractId());
                if (resultDto.getCode() == DataTransferObject.ERROR){
                    LogUtil.info(LOGGER,"物业交割确认失败，不发短信,contractId={},msg={}",notifyVo.getContractId(),resultDto.toJsonString());
                    return;
                }

                LogUtil.info(LOGGER,"【物业交割定时任务】开始给用户发送自动交割提示信息,contractId={}",notifyVo.getContractId());
                String code = String.valueOf(SmsTemplateCodeEnum.DELIVERY_USER_DONE_FEE_MSG.getCode());
                SmsRequest smsRequest = new SmsRequest();
                smsRequest.setMobile(notifyVo.getCustomerMobile());
                Map<String, String> paramsMap=new HashMap<>();
                paramsMap.put(SysConst.MOBILE_NATION_CODE_KEY, "86");
                paramsMap.put("{1}",notifyVo.getProjectName()+notifyVo.getRoomName());
                smsRequest.setParamsMap(paramsMap);
                smsRequest.setSmsCode(code);
                LogUtil.info(LOGGER,"【物业交割定时任务】开始给用户发送自动交割短信,param={}",JSONObject.toJSONString(paramsMap));
                String result = smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
                LogUtil.info(LOGGER,"【物业交割定时任务】用户发送自动交割短信返回结果,result={}",result);
                //发送系统消息给用户
                JpushRequest jpushRequest = new JpushRequest();
                jpushRequest.setParamsMap(paramsMap);
                jpushRequest.setJpushPersonType(JpushPersonType.ONE.getCode());
                jpushRequest.setMessageType(MessageTypeEnum.MESSAGE.getCode());
                jpushRequest.setSmsCode(code);
                jpushRequest.setTitle(ContractMsgConstant.DELIVERY_CUSTOMER_DONE_PUSH_TITLE);
                jpushRequest.setUid(notifyVo.getCustomerUid());
                Map<String, String> extrasMap = new HashMap<>();
                extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.ZRP_MSG_BODY_TYPE_VALUE);
                extrasMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_ALL);
                extrasMap.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));
                extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY,JpushConst.MSG_SUB_TYPE_VALULE_4);
                jpushRequest.setExtrasMap(extrasMap);
                LogUtil.info(LOGGER,"【物业交割定时任务】开始给用户发送自动交割短信,param={}",JSONObject.toJSONString(jpushRequest));
                String pushResult = smsTemplateService.jpushByCode(JsonEntityTransform.Object2Json(jpushRequest));
                LogUtil.info(LOGGER,"【物业交割定时任务】用户发送自动交割推送返回结果,pushResult={}",pushResult);

            }
        }catch (Exception e){
            LogUtil.error(LOGGER,"【物业交割定时任务】自动确认物业交割异常e={}",e);
        }

    }

    /**
     * 物业交割费用是否支付完成
     * @author jixd
     * @created 2017年10月19日 15:32:13
     * @param
     * @return
     */
    private int isPayLifeFee(DeliveryContractNotifyVo notifyVo){
        //是否支付完成
        int isPay = IsPayEnum.NO.getCode();
        ReceiptBillRequest receiptBillRequest = new ReceiptBillRequest();
        receiptBillRequest.setOutContractCode(notifyVo.getContractCode());
        receiptBillRequest.setDocumentType(DocumentTypeEnum.LIFE_FEE.getCode());
        DataTransferObject billDto = JsonEntityTransform.json2DataTransferObject(callFinanceServiceProxy.getReceivableBillInfo(JSONObject.toJSONString(receiptBillRequest)));
        LogUtil.info(LOGGER,"【confirmDelivery】账单查询结果result={}",billDto.toJsonString());
        if (billDto.getCode() == DataTransferObject.ERROR){
            LogUtil.error(LOGGER,"【confirmDelivery】查询账单异常,result={}",billDto.toJsonString());
            return isPay;
        }

        isPay = (int)billDto.getData().get("isPay");
        return isPay;

    }

    @Override
    public void firstBillPayOvertimeCloseContract() {
        LogUtil.info(LOGGER,"【firstBillPayOvertimeCloseContract】未支付关闭合同定时任务开始");
        long start = System.currentTimeMillis();
        try {
            String moneyValueJson = cityTemplateService.getTextValueForCommon(ServiceLineEnum.ZRP.getCode(), ContractTradingEnum008.ContractTradingEnum008001.getValue());
            String lessHoursValueJson = cityTemplateService.getTextValueForCommon(ServiceLineEnum.ZRP.getCode(), ContractTradingEnum008.ContractTradingEnum008002.getValue());
            String moreHoursValueJson = cityTemplateService.getTextValueForCommon(ServiceLineEnum.ZRP.getCode(), ContractTradingEnum008.ContractTradingEnum008003.getValue());
            DataTransferObject moneyValueDto = JsonEntityTransform.json2DataTransferObject(moneyValueJson);
            DataTransferObject lessHoursValueDto = JsonEntityTransform.json2DataTransferObject(lessHoursValueJson);
            DataTransferObject moreHoursValueDto = JsonEntityTransform.json2DataTransferObject(moreHoursValueJson);
            if(moneyValueDto.getCode()==DataTransferObject.ERROR||lessHoursValueDto.getCode()==DataTransferObject.ERROR||moreHoursValueDto.getCode()==DataTransferObject.ERROR){
                LogUtil.error(LOGGER, "【firstBillPayOvertimeCloseContract】 调用民宿服务查询配置项失败");
                return;
            }
            String moneyValue = SOAResParseUtil.getStrFromDataByKey(moneyValueJson,"textValue");
            String lessHoursValue = SOAResParseUtil.getStrFromDataByKey(lessHoursValueJson,"textValue");
            String moreHoursValue = SOAResParseUtil.getStrFromDataByKey(moreHoursValueJson,"textValue");
            if(Check.NuNStrStrict(moneyValue)||Check.NuNStrStrict(lessHoursValue)||Check.NuNStrStrict(moreHoursValue)){
                LogUtil.error(LOGGER, "【firstBillPayOvertimeCloseContract】 调用民宿服务查询配置项字符串为空");
                return;
            }
            Map<String ,Object> paramMap = new HashMap<>();
            paramMap.put("payOvertimeConditionMoney",moneyValue);
            paramMap.put("lessMoneyOvertimeHours",lessHoursValue);
            paramMap.put("moreMoneyOvertimeHours",moreHoursValue);
            PagingResult<CloseContractNotifyVo> pagingResult = rentContractServiceImpl.findFirstBillPayOvertimeForPage(paramMap);
            Long total = pagingResult.getTotal();
            List<CloseContractNotifyVo> closeContractNotifyVos = pagingResult.getRows();
            if(total==0||total==0L||Check.NuNCollection(closeContractNotifyVos)){
                LogUtil.info(LOGGER, "【firstBillPayOvertimeCloseContract】 没有需要关闭的合同");
                return;
            }
            int limit = 50;//分页数
            int pageAll = ValueUtil.getPage(total.intValue(), limit);
            for(int page = 1;page <= pageAll;page++){
                paramMap.put("page", page);
                paramMap.put("limit", limit);
                pagingResult = rentContractServiceImpl.findFirstBillPayOvertimeForPage(paramMap);
                closeContractNotifyVos = pagingResult.getRows();
                if(Check.NuNCollection(closeContractNotifyVos)){
                    break;
                }
                String result = null;
                DataTransferObject dto = null;
                for(CloseContractNotifyVo closeContractNotifyVo:closeContractNotifyVos){
                    result = rentContractServiceProxy.closeContract(closeContractNotifyVo.getContractId(), ContractCloseTypeEnum.AUTO_CLOSE.getCode());
                    dto = JsonEntityTransform.json2DataTransferObject(result);
                    if(dto.getCode()==DataTransferObject.ERROR){
                        LogUtil.error(LOGGER, "【firstBillPayOvertimeCloseContract】 调用关闭合同方法失败,conttractId:{},errMsg:{}", closeContractNotifyVo.getContractId(),dto.getMsg());
                        continue;
                    }
                    LogUtil.info(LOGGER, "【firstBillPayOvertimeCloseContract】 支付超时发送消息，conttractId:{}", closeContractNotifyVo.getContractId());
                    sendPayOvertimeMsg(closeContractNotifyVo.getCustomerMobile(),closeContractNotifyVo.getCustomerUid(),closeContractNotifyVo.getProjectName(),closeContractNotifyVo.getRoomName());
                }
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【firstBillPayOvertimeCloseContract】 error:{}", e);
        }
        LogUtil.info(LOGGER,"【firstBillPayBeforeOvertimeRemind】未支付关闭合同定时任务结束，耗时 time={}",System.currentTimeMillis() - start);
    }

    @Override
    public void firstBillPayBeforeOvertimeRemind() {
        try {
            String beforeOverTimeHoursValueJson = cityTemplateService.getTextValueForCommon(ServiceLineEnum.ZRP.getCode(),ContractTradingEnum.ContractTradingEnum009.getValue());
            String moneyValueJson = cityTemplateService.getTextValueForCommon(ServiceLineEnum.ZRP.getCode(), ContractTradingEnum008.ContractTradingEnum008001.getValue());
            String lessHoursValueJson = cityTemplateService.getTextValueForCommon(ServiceLineEnum.ZRP.getCode(), ContractTradingEnum008.ContractTradingEnum008002.getValue());
            String moreHoursValueJson = cityTemplateService.getTextValueForCommon(ServiceLineEnum.ZRP.getCode(), ContractTradingEnum008.ContractTradingEnum008003.getValue());
            DataTransferObject beforeOverTimeHoursValueDto = JsonEntityTransform.json2DataTransferObject(beforeOverTimeHoursValueJson);
            DataTransferObject moneyValueDto = JsonEntityTransform.json2DataTransferObject(moneyValueJson);
            DataTransferObject lessHoursValueDto = JsonEntityTransform.json2DataTransferObject(lessHoursValueJson);
            DataTransferObject moreHoursValueDto = JsonEntityTransform.json2DataTransferObject(moreHoursValueJson);
            if(beforeOverTimeHoursValueDto.getCode()==DataTransferObject.ERROR||moneyValueDto.getCode()==DataTransferObject.ERROR||lessHoursValueDto.getCode()==DataTransferObject.ERROR||moreHoursValueDto.getCode()==DataTransferObject.ERROR){
                LogUtil.error(LOGGER, "【firstBillPayBeforeOvertimeRemind】 调用民宿服务查询配置项失败");
                return;
            }
            String beforeOverTimeHoursValue = SOAResParseUtil.getStrFromDataByKey(beforeOverTimeHoursValueJson,"textValue");
            String moneyValue = SOAResParseUtil.getStrFromDataByKey(moneyValueJson,"textValue");
            String lessHoursValue = SOAResParseUtil.getStrFromDataByKey(lessHoursValueJson,"textValue");
            String moreHoursValue = SOAResParseUtil.getStrFromDataByKey(moreHoursValueJson,"textValue");
            if(Check.NuNStrStrict(beforeOverTimeHoursValue)||Check.NuNStrStrict(moneyValue)||Check.NuNStrStrict(lessHoursValue)||Check.NuNStrStrict(moreHoursValue)){
                LogUtil.error(LOGGER, "【firstBillPayBeforeOvertimeRemind】 调用民宿服务查询配置项字符串为空");
                return;
            }
            Map<String ,Object> paramMap = new HashMap<>();
            LogUtil.info(LOGGER, "【firstBillPayBeforeOvertimeRemind】 几个配置参数,beforeOverTimeHours:{},payOvertimeConditionMoney:{},lessMoneyOvertimeHours:{},moreMoneyOvertimeHours:{}", beforeOverTimeHoursValue,moneyValue,lessHoursValue,moreHoursValue);
            paramMap.put("beforeOverTimeHours",Integer.valueOf(beforeOverTimeHoursValue));
            paramMap.put("payOvertimeConditionMoney",Integer.valueOf(moneyValue));
            paramMap.put("lessMoneyOvertimeHours",Integer.valueOf(lessHoursValue));
            paramMap.put("moreMoneyOvertimeHours",Integer.valueOf(moreHoursValue));
            PagingResult<CloseContractNotifyVo> pagingResult = rentContractServiceImpl.findFirstBillPayBeforeOvertimeForPage(paramMap);
            Long total = pagingResult.getTotal();
            LogUtil.info(LOGGER, "总共查询出需要发送提醒合同个数，:{}",total );
            List<CloseContractNotifyVo> closeContractNotifyVos = pagingResult.getRows();
            if(total==0||total==0L||Check.NuNCollection(closeContractNotifyVos)){
                LogUtil.info(LOGGER, "【firstBillPayBeforeOvertimeRemind】 没有支付超时{}小时需要提醒的合同",beforeOverTimeHoursValue);
                return;
            }
            int limit = 50;//分页数
            int pageAll = ValueUtil.getPage(total.intValue(), limit);
            for(int page = 1;page <= pageAll;page++){
                paramMap.put("page", page);
                paramMap.put("limit", limit);
                pagingResult = rentContractServiceImpl.findFirstBillPayBeforeOvertimeForPage(paramMap);
                closeContractNotifyVos = pagingResult.getRows();
                if(Check.NuNCollection(closeContractNotifyVos)){
                    break;
                }
                LogUtil.info(LOGGER, "【firstBillPayBeforeOvertimeRemind】 closeContractNotifyVos.size():{}", closeContractNotifyVos.size());
                for(CloseContractNotifyVo closeContractNotifyVo:closeContractNotifyVos){
                    LogUtil.info(LOGGER, "【firstBillPayBeforeOvertimeRemind】 支付超时前{}小时发送消息和系统消息，conttractId:{}", beforeOverTimeHoursValue,closeContractNotifyVo.getContractId());
                    sendBeforePayOvertimeRemindMsg(closeContractNotifyVo.getCustomerMobile(),closeContractNotifyVo.getCustomerUid(),closeContractNotifyVo.getProjectName(),closeContractNotifyVo.getRoomName(),closeContractNotifyVo.getCutOffTime());
                }
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【firstBillPayBeforeOvertimeRemind】 error:{}", e);
        }

    }

    @Override
    public void sameDayUnsignedCloseContract() {
        try {
            Map<String ,Object> paramMap = new HashMap<>();
            PagingResult<CloseContractNotifyVo> pagingResult = rentContractServiceImpl.findSameUnsignedForPage(paramMap);
            Long total = pagingResult.getTotal();
            List<CloseContractNotifyVo> closeContractNotifyVos = pagingResult.getRows();
            if(total==0||total==0L||Check.NuNCollection(closeContractNotifyVos)){
                LogUtil.info(LOGGER, "【sameDayUnsignedCloseContract】 没有需要关闭的合同");
                return;
            }
            int limit = 50;//分页数
            int pageAll = ValueUtil.getPage(total.intValue(), limit);
            for(int page = 1;page <= pageAll;page++){
                pagingResult = rentContractServiceImpl.findSameUnsignedForPage(paramMap);
                closeContractNotifyVos = pagingResult.getRows();
                if(Check.NuNCollection(closeContractNotifyVos)){
                    break;
                }
                String result = null;
                DataTransferObject dto = null;
                for(CloseContractNotifyVo closeContractNotifyVo:closeContractNotifyVos){
                    result = rentContractServiceProxy.closeContract(closeContractNotifyVo.getContractId(), ContractCloseTypeEnum.AUTO_CLOSE.getCode());
                    dto = JsonEntityTransform.json2DataTransferObject(result);
                    if(dto.getCode()==DataTransferObject.ERROR){
                        LogUtil.error(LOGGER, "【sameDayUnsignedCloseContract】 调用关闭合同方法失败,conttractId:{},errMsg:{}", closeContractNotifyVo.getContractId(),dto.getMsg());
                        continue;
                    }
                    LogUtil.info(LOGGER, "【sameDayUnsignedCloseContract】 当天未签约关闭合同，conttractId:{}", closeContractNotifyVo.getContractId());
                    sendUnsignedContractMsg(closeContractNotifyVo.getCustomerMobile(),closeContractNotifyVo.getCustomerUid(),closeContractNotifyVo.getProjectName(),closeContractNotifyVo.getRoomName());
                }
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【sameDayUnsignedCloseContract】 error:{}", e);
        }
    }

    @Override
    public void sameDayBeforeOvertimeUnsignedRemind() {
        LogUtil.info(LOGGER,"【sameDayBeforeOvertimeUnsignedRemind】距离签约截止时间n小时提醒定时任务");
        long start = System.currentTimeMillis();
        try {
            String beforeOverTimeHoursValueJson = cityTemplateService.getTextValueForCommon(ServiceLineEnum.ZRP.getCode(),ContractTradingEnum.ContractTradingEnum009.getValue());
            DataTransferObject beforeOverTimeHoursValueDto = JsonEntityTransform.json2DataTransferObject(beforeOverTimeHoursValueJson);
            if(beforeOverTimeHoursValueDto.getCode()==DataTransferObject.ERROR){
                LogUtil.error(LOGGER, "【sameDayBeforeOvertimeUnsignedRemind】 调用民宿服务查询配置项失败");
                return;
            }
            String beforeOverTimeHoursValue = SOAResParseUtil.getStrFromDataByKey(beforeOverTimeHoursValueJson,"textValue");
            Map<String ,Object> paramMap = new HashMap<>();
            paramMap.put("beforeOverTimeHours",beforeOverTimeHoursValue);
            LogUtil.info(LOGGER, "【sameDayBeforeOvertimeUnsignedRemind】 配置参数,beforeOverTimeHoursValue:{}", beforeOverTimeHoursValue);
            PagingResult<CloseContractNotifyVo> pagingResult = rentContractServiceImpl.findSameDayBeforeOvertimeForPage(paramMap);
            Long total = pagingResult.getTotal();
            LogUtil.info(LOGGER, "【sameDayBeforeOvertimeUnsignedRemind】总共查询出需要发送提醒合同个数，:{}",total );
            List<CloseContractNotifyVo> closeContractNotifyVos = pagingResult.getRows();
            if(total==0||total==0L||Check.NuNCollection(closeContractNotifyVos)){
                LogUtil.info(LOGGER, "【sameDayBeforeOvertimeUnsignedRemind】 没有需要发送提醒的合同");
                return;
            }
            int limit = 50;//分页数
            int pageAll = ValueUtil.getPage(total.intValue(), limit);
            for(int page = 1;page <= pageAll;page++){
                pagingResult = rentContractServiceImpl.findSameDayBeforeOvertimeForPage(paramMap);
                closeContractNotifyVos = pagingResult.getRows();
                if(Check.NuNCollection(closeContractNotifyVos)){
                    break;
                }
                for(CloseContractNotifyVo closeContractNotifyVo:closeContractNotifyVos){
                    LogUtil.info(LOGGER, "【sameDayBeforeOvertimeUnsignedRemind】 距离签约有效期截止时间n小时发送提醒，conttractId:{}", closeContractNotifyVo.getContractId());
                    sendBeforeSignOvertimeRemindMsg(closeContractNotifyVo.getCustomerMobile(),closeContractNotifyVo.getCustomerUid(),closeContractNotifyVo.getProjectName(),closeContractNotifyVo.getRoomName(),beforeOverTimeHoursValue);
                }
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【sameDayBeforeOvertimeUnsignedRemind】 error:{}", e);
        }
        try {
            String msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.JOB_NOTICE.getCode());
            SmsRequest smsRequest  = new SmsRequest();
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("{1}", "【自如寓】距离签约截止时间n小时提醒");
            paramsMap.put("{2}", "耗时："+(System.currentTimeMillis() - start)+"ms");
            smsRequest.setParamsMap(paramsMap);
            smsRequest.setMobile(ZKConfigEnum.ZK_CONFIG_ENUM_003.getDefaultValue());
            smsRequest.setSmsCode(msgCode);
            smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
        }catch (Exception e){
            LogUtil.error(LOGGER,"定时任务发送短信失败：e：{}",e);
        }
        LogUtil.info(LOGGER,"【syncExpireContractToFin】距离签约截止时间n小时提醒定时任务，耗时 time={}",System.currentTimeMillis() - start);
    }

    @Override
    public void sameDayUnrenewedCloseContract() {
        try {
            Map<String ,Object> paramMap = new HashMap<>();
            PagingResult<CloseContractNotifyVo> pagingResult = rentContractServiceImpl.findSameUnrenewedForPage(paramMap);
            Long total = pagingResult.getTotal();
            List<CloseContractNotifyVo> closeContractNotifyVos = pagingResult.getRows();
            if(total==0||total==0L||Check.NuNCollection(closeContractNotifyVos)){
                LogUtil.info(LOGGER, "【sameDayUnrenewedCloseContract】 没有需要关闭的合同");
                return;
            }
            int limit = 50;//分页数
            int pageAll = ValueUtil.getPage(total.intValue(), limit);
            for(int page = 1;page <= pageAll;page++){
                pagingResult = rentContractServiceImpl.findSameUnsignedForPage(paramMap);
                closeContractNotifyVos = pagingResult.getRows();
                if(Check.NuNCollection(closeContractNotifyVos)){
                    break;
                }
                String result = null;
                DataTransferObject dto = null;
                for(CloseContractNotifyVo closeContractNotifyVo:closeContractNotifyVos){
                    result = rentContractServiceProxy.closeContract(closeContractNotifyVo.getContractId(), ContractCloseTypeEnum.AUTO_CLOSE.getCode());
                    dto = JsonEntityTransform.json2DataTransferObject(result);
                    if(dto.getCode()==DataTransferObject.ERROR){
                        LogUtil.error(LOGGER, "【sameDayUnrenewedCloseContract】 调用关闭合同方法失败,conttractId:{},errMsg:{}", closeContractNotifyVo.getContractId(),dto.getMsg());
                        continue;
                    }
                    LogUtil.info(LOGGER, "【sameDayUnrenewedCloseContract】 续约有效期截止关闭合同发送信息，conttractId:{}", closeContractNotifyVo.getContractId());
                    sendUnsignedContractMsg(closeContractNotifyVo.getCustomerMobile(),closeContractNotifyVo.getCustomerUid(),closeContractNotifyVo.getProjectName(),closeContractNotifyVo.getRoomName());
                }
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【sameDayUnrenewedCloseContract】 error:{}", e);
        }
    }

    /**
     * @description: 首笔支付超时关闭合同发送短信、系统消息给用户
     * @author: lusp
     * @date: 2017/11/2 下午 13:58
     * @params: mobile、uid、projectName、roomName
     * @return:
     */
    private void sendPayOvertimeMsg(String mobile,String uid,String projectName,String roomName){
        //发送短信给用户
        SmsRequest smsRequest = new SmsRequest();
        smsRequest.setMobile(mobile);
        Map<String, String> paramsMap=new HashMap<>();
        paramsMap.put(SysConst.MOBILE_NATION_CODE_KEY, "86");
        paramsMap.put("{1}",projectName+roomName);
        smsRequest.setParamsMap(paramsMap);
        smsRequest.setSmsCode(ValueUtil.getStrValue(SmsTemplateCodeEnum.PAY_OVERTIME_MSG.getCode()));
        LogUtil.info(LOGGER, "【firstBillPayOvertimeCloseContract】发送系统消息...  mobile:{},uid:{},projectName:{},roomName:{}", mobile,uid,projectName,roomName);
        smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));

        //发送系统消息给用户
        JpushRequest jpushRequest = new JpushRequest();
        jpushRequest.setParamsMap(paramsMap);
        jpushRequest.setJpushPersonType(JpushPersonType.ONE.getCode());
        jpushRequest.setMessageType(MessageTypeEnum.MESSAGE.getCode());
        jpushRequest.setSmsCode(String.valueOf(SmsTemplateCodeEnum.PAY_OVERTIME_MSG.getCode()));
        jpushRequest.setTitle("自如寓首期款项支付有效期已失效");
        jpushRequest.setUid(uid);
        Map<String, String> extrasMap = new HashMap<>();
        extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.ZRP_MSG_BODY_TYPE_VALUE);
        extrasMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_ALL);
        extrasMap.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));
        extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY,JpushConst.MSG_SUB_TYPE_VALULE_4);
        jpushRequest.setExtrasMap(extrasMap);
        LogUtil.info(LOGGER, "【firstBillPayOvertimeCloseContract】发送短信...  mobile:{},uid:{},projectName:{},roomName:{}", mobile,uid,projectName,roomName);
        String pushResultJson = smsTemplateService.jpushByCode(JsonEntityTransform.Object2Json(jpushRequest));
        LogUtil.info(LOGGER, "【firstBillPayOvertimeCloseContract】调用民宿发送系统消息结果,  uid:{},pushResultJson:{}",uid, pushResultJson);
    }

    /**
     * @description: 距离首笔支付超时关闭合同前n小时发送短信、系统消息给用户
     * @author: lusp
     * @date: 2017/11/2 下午 13:58
     * @params: mobile、uid、projectName、roomName,beforeOverTimeHours
     * @return:
     */
    private void sendBeforePayOvertimeRemindMsg(String mobile,String uid,String projectName,String roomName,String cutOffTime){
        //发送短信给用户
        SmsRequest smsRequest = new SmsRequest();
        smsRequest.setMobile(mobile);
        Map<String, String> paramsMap=new HashMap<>();
        paramsMap.put(SysConst.MOBILE_NATION_CODE_KEY, "86");
        paramsMap.put("{1}",projectName+roomName);
        paramsMap.put("{2}",cutOffTime);
        smsRequest.setParamsMap(paramsMap);
        smsRequest.setSmsCode(ValueUtil.getStrValue(SmsTemplateCodeEnum.PAY_BEFORE_OVERTIME_REMIND_SMS_MSG.getCode()));
        LogUtil.info(LOGGER, "【firstBillPayBeforeOvertimeRemind】发送短信...  mobile:{},uid:{},projectName:{},roomName:{},cutOffTime:{}", mobile,uid,projectName,roomName,cutOffTime);
        smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));

        //发送系统消息给用户
        JpushRequest jpushRequest = new JpushRequest();
        jpushRequest.setParamsMap(paramsMap);
        jpushRequest.setJpushPersonType(JpushPersonType.ONE.getCode());
        jpushRequest.setMessageType(MessageTypeEnum.MESSAGE.getCode());
        jpushRequest.setSmsCode(String.valueOf(SmsTemplateCodeEnum.PAY_BEFORE_OVERTIME_REMIND_PUSH_MSG.getCode()));
        jpushRequest.setTitle("自如寓支付有效期即将失效");
        jpushRequest.setUid(uid);
        Map<String, String> extrasMap = new HashMap<>();
        extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.ZRP_MSG_BODY_TYPE_VALUE);
        extrasMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_ALL);
        extrasMap.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));
        extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY,JpushConst.MSG_SUB_TYPE_VALULE_5);
        extrasMap.put(JpushConst.MSG_HAS_RESPONSE,"1");
        jpushRequest.setExtrasMap(extrasMap);
        LogUtil.info(LOGGER, "【firstBillPayBeforeOvertimeRemind】发送系统消息...  mobile:{},uid:{},projectName:{},roomName:{}", mobile,uid,projectName,roomName);
        String pushResultJson = smsTemplateService.jpushByCode(JsonEntityTransform.Object2Json(jpushRequest));
        LogUtil.info(LOGGER, "【firstBillPayBeforeOvertimeRemind】调用民宿发送系统消息结果,  uid:{},pushResultJson:{}",uid, pushResultJson);
    }

    /**
     * @description: 距离签约超时关闭合同前n小时发送短信、系统消息给用户
     * @author: lusp
     * @date: 2017/11/2 下午 13:58
     * @params: mobile、uid、projectName、roomName
     * @return:
     */
    private void sendBeforeSignOvertimeRemindMsg(String mobile,String uid,String projectName,String roomName,String beforeOverTimeHoursValue){
        //发送短信给用户
        SmsRequest smsRequest = new SmsRequest();
        smsRequest.setMobile(mobile);
        Map<String, String> paramsMap=new HashMap<>();
        paramsMap.put(SysConst.MOBILE_NATION_CODE_KEY, "86");
        paramsMap.put("{1}",projectName+roomName);
        paramsMap.put("{2}",beforeOverTimeHoursValue);
        smsRequest.setParamsMap(paramsMap);
        smsRequest.setSmsCode(ValueUtil.getStrValue(SmsTemplateCodeEnum.UNSIGNED_BEFORE_OVERTIME_REMIND_SMS_MSG.getCode()));
        LogUtil.info(LOGGER, "【sameDayBeforeOvertimeUnsignedRemind】发送短信...  mobile:{},uid:{},projectName:{},roomName:{},beforeOverTimeHoursValue:{}", mobile,uid,projectName,roomName,beforeOverTimeHoursValue);
        smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));

        //发送系统消息给用户
        JpushRequest jpushRequest = new JpushRequest();
        jpushRequest.setParamsMap(paramsMap);
        jpushRequest.setJpushPersonType(JpushPersonType.ONE.getCode());
        jpushRequest.setMessageType(MessageTypeEnum.MESSAGE.getCode());
        jpushRequest.setSmsCode(String.valueOf(SmsTemplateCodeEnum.UNSIGNED_BEFORE_OVERTIME_REMIND_PUSH_MSG.getCode()));
        jpushRequest.setTitle("自如寓签约有效期即将失效");
        jpushRequest.setUid(uid);
        Map<String, String> extrasMap = new HashMap<>();
        extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.ZRP_MSG_BODY_TYPE_VALUE);
        extrasMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_ALL);
        extrasMap.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));
        extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY,JpushConst.MSG_SUB_TYPE_VALULE_4);
        extrasMap.put(JpushConst.MSG_HAS_RESPONSE,"1");
        jpushRequest.setExtrasMap(extrasMap);
        LogUtil.info(LOGGER, "【sameDayBeforeOvertimeUnsignedRemind】发送系统消息...  mobile:{},uid:{},projectName:{},roomName:{},beforeOverTimeHoursValue:{}", mobile,uid,projectName,roomName,beforeOverTimeHoursValue);
        String pushResultJson = smsTemplateService.jpushByCode(JsonEntityTransform.Object2Json(jpushRequest));
        LogUtil.info(LOGGER, "【sameDayBeforeOvertimeUnsignedRemind】调用民宿发送系统消息结果,  uid:{},pushResultJson:{}",uid, pushResultJson);
    }

    /**
     * @description: 当天未签约关闭合同发送短信、系统消息给用户
     * @author: lusp
     * @date: 2017/11/2 下午 14:02
     * @params: mobile、uid、projectName、roomName
     * @return:
     */
    private void sendUnsignedContractMsg(String mobile,String uid,String projectName,String roomName){
        //发送短信给用户
        SmsRequest smsRequest = new SmsRequest();
        smsRequest.setMobile(mobile);
        Map<String, String> paramsMap=new HashMap<>();
        paramsMap.put(SysConst.MOBILE_NATION_CODE_KEY, "86");
        paramsMap.put("{1}",projectName+roomName);
        smsRequest.setParamsMap(paramsMap);
        smsRequest.setSmsCode(ValueUtil.getStrValue(SmsTemplateCodeEnum.UNSIGNED_CLOSE_MSG.getCode()));
        LogUtil.info(LOGGER, "【sameDayUnrenewedCloseJob】发送系统消息...  mobile:{},uid:{},projectName:{},roomName:{}", mobile,uid,projectName,roomName);
        smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));

        //发送系统消息给用户
        JpushRequest jpushRequest = new JpushRequest();
        jpushRequest.setParamsMap(paramsMap);
        jpushRequest.setJpushPersonType(JpushPersonType.ONE.getCode());
        jpushRequest.setMessageType(MessageTypeEnum.MESSAGE.getCode());
        jpushRequest.setSmsCode(String.valueOf(SmsTemplateCodeEnum.UNSIGNED_CLOSE_MSG.getCode()));
        jpushRequest.setTitle("自如寓签约有效期已失效");
        jpushRequest.setUid(uid);
        Map<String, String> extrasMap = new HashMap<>();
        extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.ZRP_MSG_BODY_TYPE_VALUE);
        extrasMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_ALL);
        extrasMap.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));
        extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY,JpushConst.MSG_SUB_TYPE_VALULE_4);
        jpushRequest.setExtrasMap(extrasMap);
        LogUtil.info(LOGGER, "【sameDayUnrenewedCloseJob】发送短信...  mobile:{},uid:{},projectName:{},roomName:{}", mobile,uid,projectName,roomName);
        String pushResultJson = smsTemplateService.jpushByCode(JsonEntityTransform.Object2Json(jpushRequest));
        LogUtil.info(LOGGER, "【sameDayUnrenewedCloseJob】调用民宿发送系统消息结果,  uid:{},pushResultJson:{}",uid, pushResultJson);
    }


    @Override
    public void syncExpireContractToFin() {
        LogUtil.info(LOGGER,"【syncExpireContractToFin】更改过期状态定时任务开始");
        long start = System.currentTimeMillis();
        try{
            final int page = 1;
            final int rows = 30;
            BasePageParamDto basePageParamDto = new BasePageParamDto(){};
            basePageParamDto.setPage(page);
            basePageParamDto.setRows(rows);
            int forNum = 1;
            for (;;){
                LogUtil.info(LOGGER,"【定时任务-更新过期合同状态】循环次数forNum={}",forNum);
                forNum ++;
                PagingResult<RentContractEntity> pagingResult = rentContractServiceImpl.listExpireContractPage(basePageParamDto);
                List<RentContractEntity> rentContractEntities = pagingResult.getRows();
                if (Check.NuNCollection(rentContractEntities)){
                    break;
                }
                for (RentContractEntity rentContractEntity : rentContractEntities){
                    RentContractEntity updateRentcontract = new RentContractEntity();
                    updateRentcontract.setContractId(rentContractEntity.getContractId());
                    updateRentcontract.setPreConStatusCode(rentContractEntity.getConStatusCode());
                    updateRentcontract.setConStatusCode(ContractStatusEnum.YDQ.getStatus());
                    int count = rentContractServiceImpl.updateBaseContractById(updateRentcontract);
                    if (count > 0){
                        waterClearingLogic.clearYdqRenewContract(rentContractEntity);
                        SyncContractVo syncContractVo = new SyncContractVo();
                        syncContractVo.setCrmContractId(rentContractEntity.getContractId());
                        syncContractVo.setRentContractCode(rentContractEntity.getConRentCode());
                        syncContractVo.setStatusCode(ContractStatusEnum.YDQ.getStatus());
                        if (rentContractEntity.getDataVersion() == ContractDataVersionEnum.OLD.getCode()){
                            ContractRoomDto contractRoomDto = ContractRoomDto.builder().contractId(rentContractEntity.getContractId()).roomId(rentContractEntity.getRoomId()).build();
                            RentDetailEntity rentDetailEntity = rentContractServiceImpl.findRentDetailById(contractRoomDto);
                            syncContractVo.setCrmContractId(rentDetailEntity.getId());
                        }
                        //同步财务合同更新状态
                        financeBaseCall.updateContract(syncContractVo);
                    }
                    LogUtil.info(LOGGER,"【定时任务-更新过期合同状态】contractCode={}",rentContractEntity.getConRentCode());
                }
            }
        }catch (Exception e){
            LogUtil.info(LOGGER,"异常e={}",e);
        }

        try {
            String msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.JOB_NOTICE.getCode());
            SmsRequest smsRequest  = new SmsRequest();
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("{1}", "【自如寓】更改合同过期状态");
            paramsMap.put("{2}", "耗时："+(System.currentTimeMillis() - start)+"ms");
            smsRequest.setParamsMap(paramsMap);
            smsRequest.setMobile(ZKConfigEnum.ZK_CONFIG_ENUM_003.getDefaultValue());
            smsRequest.setSmsCode(msgCode);
            smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
        }catch (Exception e){
            LogUtil.error(LOGGER,"定时任务发送短信失败：e：{}",e);
        }

        LogUtil.info(LOGGER,"【syncExpireContractToFin】更改过期状态定时任务结束，耗时 time={}",System.currentTimeMillis() - start);
    }

    /**
     * 作废电费的应收账单
     * @author xiangb
     * @created 2018年2月6日16:39:31
     * @param
     * @return
     */
    public void invalidMeterFinReceiBill(){
        LogUtil.info(LOGGER,"【invalidMeterFinReceiBill】作废电费应收账单定时任务开始");
        long start = System.currentTimeMillis();
        try{
            List<FinReceiBillDetailEntity> finReceiBillDetailEntities = finReceiBillDetailServiceImpl.selectOutTimeFinReceiBillDetail();
            List<String> fids = null;
            List<String> billNums = null;
            Map<String,String> billNumMap = new HashMap<>();
            if(!Check.NuNCollection(finReceiBillDetailEntities)){
                fids = finReceiBillDetailEntities.stream().map(FinReceiBillDetailEntity::getBillFid).collect(Collectors.toList());
                billNums = finReceiBillDetailEntities.stream().map(FinReceiBillDetailEntity::getBillNum).collect(Collectors.toList());
                finReceiBillDetailEntities.forEach(p -> {
                    billNumMap.put(p.getBillNum(),p.getFid());
                });
            }
            if(!Check.NuNCollection(billNums)){
                //调用财务作废应收账单
                for(String billNum : billNums){
                    ModifyReceiptBillRequest modifyRequest = new ModifyReceiptBillRequest();
                    modifyRequest.setBillNum(billNum);
                    modifyRequest.setIsDel(IsDelEnum.YES.getCode());
                    String result = callFinanceServiceProxy.modifyReceiptBill(JsonEntityTransform.Object2Json(modifyRequest));
                    DataTransferObject resultData = JsonEntityTransform.json2DataTransferObject(result);
                    if(DataTransferObject.SUCCESS != resultData.getCode()){
                        LogUtil.info(LOGGER,"【invalidMeterFinReceiBill】应收账单号：{}，作废电费应收账单财务返回异常信息为：e:{}",billNum,resultData.getMsg());
                        //作废财务应收失败，不删除本地的应收
                        fids.remove(billNumMap.get(billNum));
                    }
                }
            }
            if(!Check.NuNCollection(fids)){
                //删除本地应收账单
                finReceiBillServiceImpl.deleteReceiptBillByFid(fids);
            }
        }catch (Exception e){
            LogUtil.error(LOGGER,"【invalidMeterFinReceiBill】作废电费应收账单定时任务异常：e:{}",e);
        }
        LogUtil.info(LOGGER,"【invalidMeterFinReceiBill】作废电费应收账单定时任务结束，耗时 time={}",System.currentTimeMillis() - start);
    }

}
