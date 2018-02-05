package com.ziroom.minsu.api.evaluate.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.*;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.abs.AbstractController;
import com.ziroom.minsu.api.common.constant.ApiMessageConst;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.constant.EvaluateConst;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.api.common.util.ApiDateUtil;
import com.ziroom.minsu.api.evaluate.dto.LanEvaApiRequest;
import com.ziroom.minsu.api.evaluate.entity.EvaluateDetailVo;
import com.ziroom.minsu.api.evaluate.service.EvalOrderService;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.evaluate.LandlordEvaluateEntity;
import com.ziroom.minsu.entity.evaluate.LandlordReplyEntity;
import com.ziroom.minsu.entity.evaluate.TenantEvaluateEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.dto.CustomerPicDto;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.evaluate.api.inner.EvaluateOrderService;
import com.ziroom.minsu.services.evaluate.dto.EvaluateRequest;
import com.ziroom.minsu.services.evaluate.entity.EvaluateOrderShowVo;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;
import com.ziroom.minsu.services.order.dto.OrderDetailRequest;
import com.ziroom.minsu.services.order.dto.OrderEvalRequest;
import com.ziroom.minsu.services.order.entity.OrderDetailVo;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.valenum.common.RequestTypeEnum;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.customer.PicTypeEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateClientBtnStatuEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateRulesEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateStatuEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateUserEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.order.OrderEvaStatusEnum;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2017/1/20 10:06
 * @version 1.0
 * @since 1.0
 */
@RequestMapping("/evaluate")
@Controller
public class EvaluateController extends AbstractController {

    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(EvaluateController.class);

    @Resource(name = "api.messageSource")
    private MessageSource messageSource;

    @Resource(name = "order.orderCommonService")
    private OrderCommonService orderCommonService;

    @Resource(name = "customer.customerMsgManagerService")
    private CustomerMsgManagerService customerMsgManagerService;

    @Resource(name = "evaluate.evaluateOrderService")
    private EvaluateOrderService evaluateOrderService;

    @Resource(name = "api.evalOrderService")
    private EvalOrderService evalOrderService;

    @Resource(name = "basedata.cityTemplateService")
    private CityTemplateService cityTemplateService;

    @Value("#{'${pic_base_addr_mona}'.trim()}")
    private String picBaseAddrMona;

    @Value("#{'${detail_big_pic}'.trim()}")
    private String detail_big_pic;

    @Value("#{'${default_head_size}'.trim()}")
    private String default_head_size;

    @Value("#{'${USER_DEFAULT_PIC_URL}'.trim()}")
    private String userDefaultUrl;

    @Value("#{'${MSIT_BASE_URL}'.trim()}")
    private String msitBaseUrl;

    private String detailUrl = "orderland/43e881/showDetail?orderSn=";


    /**
     * 查询 评价详情 房客与房东
     * 房东端展示逻辑
     * 1.房东房客均未评价，（并在不可评价限制时间内），展示评价入口
     * 2.房东未评价，房客评价，（并在不可评价限制时间内），展示评价入口，不展示房客评价
     * 3.房东评价 房客未评价，展示房东评价内容，评价入口关闭
     * 4.房东评价 房客评价 ，（在限制时间内）房东公开回复入口打开
     * 5.房东评价，房客评价，公开回复都已填写，全部展示
     * 6.房客评价，房东未评价（且时间到期，展示房客评价），房东可公开回复
     * <p>
     * 房客端展示逻辑
     * 1.房东房客均未评价，显示评价入口
     * 2.房东已评，房客未评，显示评价入口
     * 3.房东未评价，房客已评价，展示房客评价内容
     * 4.房东房客都已评价，展示互评内容
     *
     * @param request
     * @return
     * @author jixd
     * @created 2017年02月08日
     */
    @RequestMapping("${LOGIN_AUTH}/queryEvaInfo")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> queryEvaInfo(HttpServletRequest request) {
        try {
            String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
            if (Check.NuNStr(paramJson)) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(
                        MessageSourceUtil.getChinese(messageSource, ApiMessageConst.PARAM_NULL)), HttpStatus.OK);
            }
            LogUtil.info(LOGGER, "查询 评价详情-queryEvaInfo:{}", paramJson);

            LanEvaApiRequest lanEvaApiRequest = JsonEntityTransform.json2Object(paramJson, LanEvaApiRequest.class);
            Integer evaType = lanEvaApiRequest.getEvaUserType();
            String uid = (String) request.getAttribute("uid");

            EvaluateDetailVo evaluateDetailVo = new EvaluateDetailVo();

            //查询订单信息
            OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
            orderDetailRequest.setOrderSn(lanEvaApiRequest.getOrderSn());
            //取反
            orderDetailRequest.setRequestType(2 / evaType);
            if (evaType == UserTypeEnum.LANDLORD.getUserType()){
                orderDetailRequest.setLandlordUid(uid);
            }else if (evaType == UserTypeEnum.TENANT.getUserType()){
                orderDetailRequest.setUid(uid);
            }
            DataTransferObject orderDto = JsonEntityTransform.json2DataTransferObject(orderCommonService.queryOrderInfoBySn(JsonEntityTransform.Object2Json(orderDetailRequest)));
            //订单快照无
            if (orderDto.getCode() == DataTransferObject.ERROR) {
                LogUtil.info(LOGGER,"查询订单返回信息result={}",orderDto.toJsonString());
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(orderDto.getMsg()), HttpStatus.OK);
            }
            OrderDetailVo oe = SOAResParseUtil.getValueFromDataByKey(orderDto.toJsonString(), "orderDetailVo", OrderDetailVo.class);
            String userUid = "";
            evaluateDetailVo.setOrderSn(oe.getOrderSn());
            evaluateDetailVo.setUserName(oe.getUserName());
            evaluateDetailVo.setLandlordName(oe.getLandlordName());
            evaluateDetailVo.setHousingDay(oe.getHousingDay());
            evaluateDetailVo.setStartTimeStr(oe.getStartTimeStr());
            evaluateDetailVo.setEndTimeStr(oe.getEndTimeStr());
            evaluateDetailVo.setPeopleNum(oe.getPeopleNum());
            evaluateDetailVo.setEvaStatus(oe.getEvaStatus());
            evaluateDetailVo.setHouseName(oe.getHouseName());
            evaluateDetailVo.setOrderDetailUrl(msitBaseUrl + detailUrl + oe.getOrderSn());
            if (!Check.NuNObj(oe.getRentWay()) && oe.getRentWay().intValue() == RentWayEnum.ROOM.getCode()) {
                evaluateDetailVo.setHouseName(oe.getRoomName());
            }

            if (evaType == UserTypeEnum.LANDLORD.getUserType()) {
                //房东
                userUid = oe.getLandlordUid();
                evaluateDetailVo.setTitleName(String.format(EvaluateConst.LAN_EVA_INFO_TITLE_NAME,oe.getUserName()));
            } else if (evaType == UserTypeEnum.TENANT.getUserType()) {
                //房客
                userUid = oe.getUserUid();
                evaluateDetailVo.setTitleName(String.format(EvaluateConst.TEN_EVA_INFO_TITLE_NAME,oe.getCityName(),oe.getHousingDay()));
            }
            //获取房东信息,设置头像
            DataTransferObject customerVoDto = JsonEntityTransform.json2DataTransferObject(customerMsgManagerService.getCutomerVo(userUid));
            if (customerVoDto.getCode() == DataTransferObject.SUCCESS) {
                CustomerVo customerVo = customerVoDto.parseData("customerVo", new TypeReference<CustomerVo>() {});
                evaluateDetailVo.setPicUrl(customerVo.getUserPicUrl());
            }

            //查询评价实体
            EvaluateRequest evaluateRequest = new EvaluateRequest();
            evaluateRequest.setOrderSn(lanEvaApiRequest.getOrderSn());
            DataTransferObject evaDto = JsonEntityTransform.json2DataTransferObject(this.evaluateOrderService.queryEvaluateByOrderSn(JsonEntityTransform.Object2Json(evaluateRequest)));

            LogUtil.info(LOGGER,"评价数据请求结果={}",evaDto.toJsonString());

            if (evaDto.getCode() == DataTransferObject.SUCCESS) {
                Map<String, Object> evaluateMap = evaDto.parseData("evaluateMap", new TypeReference<Map<String, Object>>() {});
                List<EvaluateOrderShowVo> listOrderEvaluateOrderEntities = null;
                if (evaluateMap.get("listOrderEvaluateOrder") != null) {
                    listOrderEvaluateOrderEntities = JsonEntityTransform.json2ObjectList(JsonEntityTransform.Object2Json(evaluateMap.get("listOrderEvaluateOrder")), EvaluateOrderShowVo.class);
                }

                if (!Check.NuNCollection(listOrderEvaluateOrderEntities)) {
                    for (EvaluateOrderShowVo evaluateOrderShowVo : listOrderEvaluateOrderEntities) {
                        Integer evaUserType = evaluateOrderShowVo.getEvaUserType();
                        Integer evaStatus = evaluateOrderShowVo.getEvaStatu();
                        if (evaUserType == EvaluateUserEnum.LAN.getCode()) {
                            //房东评价
                            if (evaluateMap.get("landlordEvaluate") != null) {
                                LandlordEvaluateEntity landlordEvaluateEntity = JsonEntityTransform.json2Object(JsonEntityTransform.Object2Json(evaluateMap.get("landlordEvaluate")), LandlordEvaluateEntity.class);
                                EvaluateDetailVo.LanEvaInfo lanEvaInfo = evaluateDetailVo.new LanEvaInfo();
                                lanEvaInfo.setLandlordSatisfied(landlordEvaluateEntity.getLandlordSatisfied());
                                lanEvaInfo.setContent(landlordEvaluateEntity.getContent());
                                lanEvaInfo.setCreateTime(DateUtil.dateFormat(landlordEvaluateEntity.getCreateTime(),"yyyy年MM月"));
                                oe.setLandEvaStatu(evaStatus);
                                oe.setLandEvalTime(evaluateOrderShowVo.getLastModifyDate());
                                evaluateDetailVo.setLanEvaInfo(lanEvaInfo);
                            }
                        }

                        if (evaUserType == EvaluateUserEnum.TEN.getCode()) {
                            //房客评价
                            if (evaluateMap.get("tenantEvaluate") != null) {
                                TenantEvaluateEntity tenantEvaluate = JsonEntityTransform.json2Object(JsonEntityTransform.Object2Json(evaluateMap.get("tenantEvaluate")), TenantEvaluateEntity.class);
                                EvaluateDetailVo.UserEvaInfo userEvaInfo = evaluateDetailVo.new UserEvaInfo();
                                BeanUtils.copyProperties(tenantEvaluate,userEvaInfo);
                                userEvaInfo.setCreateTime(DateUtil.dateFormat(tenantEvaluate.getCreateTime(),"yyyy年MM月"));
                                oe.setTenantEvaStatu(evaStatus);
                                oe.setTenantEvalTime(evaluateOrderShowVo.getLastModifyDate());
                                evaluateDetailVo.setUserEvaInfo(userEvaInfo);
                            }
                        }

                        if (evaUserType == EvaluateUserEnum.LAN_REPLY.getCode()){
                            //房东回复
                            if (evaluateMap.get("landlordReplyEntity") != null) {
                                LandlordReplyEntity landlordReplyEntity = JsonEntityTransform.json2Object(JsonEntityTransform.Object2Json(evaluateMap.get("landlordReplyEntity")), LandlordReplyEntity.class);
                                EvaluateDetailVo.LanRepInfo lanRepInfo = evaluateDetailVo.new LanRepInfo();
                                lanRepInfo.setContent(landlordReplyEntity.getContent());
                                lanRepInfo.setCreateTime(DateUtil.dateFormat(landlordReplyEntity.getCreateDate(),"yyyy年MM月"));
                                oe.setLanReplStatu(evaStatus);
                                evaluateDetailVo.setLanRepInfo(lanRepInfo);
                            }
                        }
                    }
                }
            }
            LogUtil.info(LOGGER,"处理评价前信息={}",JsonEntityTransform.Object2Json(evaluateDetailVo));
            //LogUtil.info(LOGGER,"订单详情评价信息={}",JsonEntityTransform.Object2Json(oe));
            handleEvaluateDetail(evaluateDetailVo,evaType,oe);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(evaluateDetailVo), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, " 查询 评价详情error:{}", e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
        }

    }

    /**
     * 处理评价详情
     *
     * @author jixd
     * @created 2017年02月08日 15:20:49
     * @param
     * @return
     */
    private void handleEvaluateDetail(EvaluateDetailVo evaluateDetailVo,Integer type,OrderDetailVo orderVo) {
        EvaluateDetailVo.LanRepInfo lanRepInfo = evaluateDetailVo.getLanRepInfo();
        LogUtil.info(LOGGER,"开始处理评价详情");
        //设置默认不可评
        evaluateDetailVo.setPjStatus(EvaluateClientBtnStatuEnum.N_EVAL.getEvaStatuCode());
        Integer evaStatus = evaluateDetailVo.getEvaStatus();
        Integer orderStatus = orderVo.getOrderStatus();
        Date realEndTime = orderVo.getRealEndTime();
        //剩余评价天数
        String titleLastTime = null;
        //填写评价人的名字
        String evaWriteStr = null;

        //获取评价关闭时间
        int pjTime = getConfValue(EvaluateRulesEnum.EvaluateRulesEnum003.getValue());
        //获取回复关闭时间
        int hfTime = getConfValue(EvaluateRulesEnum.EvaluateRulesEnum004.getValue());
        if (type == UserTypeEnum.LANDLORD.getUserCode()) {
            //显示对方已写评价
            if (evaStatus == OrderEvaStatusEnum.UESR_HVA_EVA.getCode() && orderVo.getTenantEvaStatu() == EvaluateStatuEnum.ONLINE.getEvaStatuCode()
                && !Check.NuNObj(realEndTime) && !ApiDateUtil.isEvaExpire(realEndTime, pjTime)) {
                evaWriteStr = String.format(EvaluateConst.EVA_INFO_TITLE_TIPS,orderVo.getUserName());
            }
            //不展示房客评价
            if (Check.NuNObj(orderVo.getTenantEvaStatu()) || orderVo.getTenantEvaStatu() != EvaluateStatuEnum.ONLINE.getEvaStatuCode()){
                evaluateDetailVo.setUserEvaInfo(null);
            }
            //不显示房客评价
            if ((evaStatus == OrderEvaStatusEnum.UESR_HVA_EVA.getCode() && Check.NuNObj(realEndTime))){
                evaluateDetailVo.setUserEvaInfo(null);
            }
            if ((evaStatus == OrderEvaStatusEnum.UESR_HVA_EVA.getCode() && !Check.NuNObj(realEndTime) && !ApiDateUtil.isEvaExpire(realEndTime, pjTime))){
                evaluateDetailVo.setUserEvaInfo(null);
            }

            //判断是否可评价 及时间显示
            if (evaStatus == OrderEvaStatusEnum.WAITINT_EVA.getCode() || evaStatus == OrderEvaStatusEnum.UESR_HVA_EVA.getCode()) {
                //判断房东是否评价超时
                if (!Check.NuNObj(realEndTime) && ApiDateUtil.isEvaExpire(realEndTime, pjTime)){
                    titleLastTime = EvaluateConst.EVA_INFO_TIME_OUT;
                }else if(!Check.NuNObj(realEndTime) && !ApiDateUtil.isEvaExpire(realEndTime, pjTime)){
                    //房东可评价
                    evaluateDetailVo.setPjStatus(EvaluateClientBtnStatuEnum.C_EVAL.getEvaStatuCode());
                    evaluateDetailVo.setPjButton(EvaluateClientBtnStatuEnum.C_EVAL.getEvaStatuName());
                    LogUtil.info(LOGGER,"realEndTime={},time={}",DateUtil.dateFormat(realEndTime,"yyyy-MM-dd hh:mm:ss"),pjTime);
                    titleLastTime = String.format(EvaluateConst.EVA_INFO_TITLE_TIME, lastDayTime(realEndTime, pjTime));
                }
            }
            if (evaStatus != OrderEvaStatusEnum.NO_EVA.getCode() && !Check.NuNObj(realEndTime) && evalOrderService.isLandCanRepply(orderVo)) {
                //展示评价的时间点
                Date evaShowTime = evalOrderService.getEvaShowTime(orderVo);
                if (Check.NuNObj(lanRepInfo)){
                    //房东可回复
                    evaluateDetailVo.setPjStatus(EvaluateClientBtnStatuEnum.C_REPLAY.getEvaStatuCode());
                    evaluateDetailVo.setPjButton(EvaluateClientBtnStatuEnum.C_REPLAY.getEvaStatuName());
                    titleLastTime = String.format(EvaluateConst.LAN_INFO_TITME_REPL, lastDayTime(evaShowTime,hfTime));
                }
            }

        } else if (type == UserTypeEnum.TENANT.getUserCode()) {

            //显示对方的评价提示
            if (evaStatus == OrderEvaStatusEnum.LANLORD_EVA.getCode() && orderVo.getLandEvaStatu() == EvaluateStatuEnum.ONLINE.getEvaStatuCode()
                    && !Check.NuNObj(realEndTime) && !ApiDateUtil.isEvaExpire(realEndTime, pjTime)) {
                evaWriteStr = String.format(EvaluateConst.EVA_INFO_TITLE_TIPS,orderVo.getUserName());
            }

            //不展示房东评价
            if (Check.NuNObj(orderVo.getLandEvaStatu()) || orderVo.getLandEvaStatu() != EvaluateStatuEnum.ONLINE.getEvaStatuCode()){
                evaluateDetailVo.setLanEvaInfo(null);
            }
            //不展示房东回复
            if (Check.NuNObj(orderVo.getLanReplStatu()) || orderVo.getLanReplStatu() != EvaluateStatuEnum.ONLINE.getEvaStatuCode()){
                evaluateDetailVo.setLanRepInfo(null);
            }
            //不显示房东的评价内容
            if ((evaStatus == OrderEvaStatusEnum.LANLORD_EVA.getCode() && !ApiDateUtil.isEvaExpire(realEndTime, pjTime))){
                evaluateDetailVo.setLanEvaInfo(null);
            }
            //不显示回复内容
            if (!Check.NuNObj(lanRepInfo) && orderVo.getLanReplStatu() != EvaluateStatuEnum.ONLINE.getEvaStatuCode()){
                evaluateDetailVo.setLanRepInfo(null);
            }

            if (evaStatus == OrderEvaStatusEnum.WAITINT_EVA.getCode() || evaStatus == OrderEvaStatusEnum.LANLORD_EVA.getCode()) {
                //待评价  房东已评价状态
                if (!Check.NuNObj(realEndTime) && !ApiDateUtil.isEvaExpire(realEndTime, pjTime)){
                    //房客可评价
                    evaluateDetailVo.setPjStatus(EvaluateClientBtnStatuEnum.C_EVAL.getEvaStatuCode());
                    evaluateDetailVo.setPjButton(EvaluateClientBtnStatuEnum.C_EVAL.getEvaStatuName());
                    titleLastTime = String.format(EvaluateConst.EVA_INFO_TITLE_TIME, lastDayTime(realEndTime, pjTime));
                    if (evaStatus == OrderEvaStatusEnum.LANLORD_EVA.getCode()){
                        evaWriteStr = String.format(EvaluateConst.EVA_INFO_TITLE_TIPS,orderVo.getLandlordName());
                    }

                }
                if (orderStatus == OrderStatusEnum.CHECKED_IN.getOrderStatus() || orderStatus == OrderStatusEnum.CHECKED_IN_BILL.getOrderStatus()){
                    //房客可评价
                    evaluateDetailVo.setPjStatus(EvaluateClientBtnStatuEnum.C_EVAL.getEvaStatuCode());
                    evaluateDetailVo.setPjButton(EvaluateClientBtnStatuEnum.C_EVAL.getEvaStatuName());
                    if (evaStatus == OrderEvaStatusEnum.LANLORD_EVA.getCode()){
                        evaWriteStr = String.format(EvaluateConst.EVA_INFO_TITLE_TIPS,orderVo.getLandlordName());
                    }
                }

                if (!Check.NuNObj(realEndTime) && ApiDateUtil.isEvaExpire(realEndTime, pjTime)){
                    //里开始时间不为空  并且时间超时   房客不能评价
                    titleLastTime = EvaluateConst.EVA_INFO_TIME_OUT;
                }

            }
        }
        if (orderStatus == OrderStatusEnum.FINISH_LAN_APPLY.getOrderStatus() || orderStatus == OrderStatusEnum.FINISH_NEGOTIATE.getOrderStatus()
        		|| orderStatus == OrderStatusEnum.CANCEL_NEGOTIATE.getOrderStatus()){
            evaluateDetailVo.setPjStatus(EvaluateClientBtnStatuEnum.N_EVAL.getEvaStatuCode());
            evaluateDetailVo.setPjButton(EvaluateClientBtnStatuEnum.N_EVAL.getEvaStatuName());
            titleLastTime = "";
            evaWriteStr = "";
        }
        evaluateDetailVo.setTitleTips(evaWriteStr);
        evaluateDetailVo.setTitleTime(titleLastTime);

        LogUtil.info(LOGGER,"处理评价详情结果={}",JsonEntityTransform.Object2Json(evaluateDetailVo));
    }



    /**
     * 获取剩余天数
     * @author jixd
     * @created 2017年02月08日 17:38:30
     * @param
     * @return
     */
    public static int lastDayTime(Date lastDate,int dayNum){
        Date currentDate = new Date();
        Date time = DateUtil.getTime(lastDate, dayNum);
        return (int)Math.ceil((time.getTime()- currentDate.getTime())/(1000*24.0*3600));
    }



    /**
     * 获取配置
     * @param enumKey
     * @return
     */
    private int getConfValue(String enumKey){
        String timeStrJson = cityTemplateService.getTextValue(null, String.valueOf(enumKey));
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(timeStrJson);
        if(resultDto.getCode() != DataTransferObject.SUCCESS){
            LogUtil.error(LOGGER, "获取评价配置出错,timeStrJson:{}", timeStrJson);
            throw new BusinessException("获取评价时间出错");
        }
        return ValueUtil.getintValue(resultDto.getData().get("textValue"));
    }






	/**
	 * 房东和房客的评价列表
	 * TODO
	 *
	 * @author zl
	 * @created 2017年2月8日 上午11:36:04
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${LOGIN_AUTH}/queryEvaList")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> queryEvaList(HttpServletRequest request) {
		try {
			
		    DataTransferObject dto = new DataTransferObject();
		    
		    String uid = (String) request.getAttribute("uid");
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "参数：" + paramJson);
			OrderEvalRequest orderEvalRequest = JsonEntityTransform.json2Object(paramJson, OrderEvalRequest.class);
			orderEvalRequest.setUid(uid);			
			
			if (Check.NuNObj(orderEvalRequest.getRequestType())) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("请求类型不存在");
                return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
            }
			
			//以后客户端统一 1=房东 2=房客
			orderEvalRequest.setRequestType(2/orderEvalRequest.getRequestType());
			
			if (orderEvalRequest.getRequestType() == RequestTypeEnum.TENANT.getRequestType() && Check.NuNObj(orderEvalRequest.getOrderEvalType())) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("当前评价类型不存在");
                return new ResponseEntity<ResponseSecurityDto>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
            }
			
			//获取当前的评价列表
			dto = JsonEntityTransform.json2DataTransferObject(this.orderCommonService.getOrderEavlList(JsonEntityTransform.Object2Json(orderEvalRequest)));
			if (dto.getCode() == MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE)) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
			}
			
			List<OrderInfoVo> orderList = dto.parseData("list", new TypeReference<List<OrderInfoVo>>() {
			});
			Long total = Long.valueOf(dto.getData().get("total").toString());
			
			if(!Check.NuNCollection(orderList)){
				
				for(int i=0;i<orderList.size();i++){
					OrderInfoVo orderInfoVo = orderList.get(i);
					
					//查询房东照片
					CustomerPicDto picDto =  new CustomerPicDto();
					picDto.setPicType(PicTypeEnum.USER_PHOTO.getCode());
					picDto.setUid(uid);
					dto = JsonEntityTransform.json2DataTransferObject(this.customerMsgManagerService.getCustomerPicByType(JsonEntityTransform.Object2Json(picDto)));

					//此处查询不成功 不做处理
					LogUtil.debug(LOGGER, "查询房东头像返回dto={}", JsonEntityTransform.Object2Json(dto));
					if(dto.getCode() == 0){
						CustomerPicMsgEntity customerPicMsg  = dto.parseData("customerPicMsgEntity", new TypeReference<CustomerPicMsgEntity>() {
						});
						if(!Check.NuNObj(customerPicMsg)&&!Check.NuNStr(customerPicMsg.getPicBaseUrl())){
							if(customerPicMsg.getPicBaseUrl().contains("http")){
								orderInfoVo.setLandPicUrl(customerPicMsg.getPicBaseUrl());
							}else{
								orderInfoVo.setLandPicUrl(PicUtil.getFullPic(picBaseAddrMona, customerPicMsg.getPicBaseUrl(), customerPicMsg.getPicSuffix(), default_head_size));
							}

						}
					}
					
					picDto.setUid(orderInfoVo.getUserUid());
					dto = JsonEntityTransform.json2DataTransferObject(this.customerMsgManagerService.getCustomerPicByType(JsonEntityTransform.Object2Json(picDto)));

					//此处查询不成功 不做处理
					LogUtil.debug(LOGGER, "查询房客头像返回dto={}", JsonEntityTransform.Object2Json(dto));
					if(dto.getCode() == 0){
						CustomerPicMsgEntity customerPicMsg  = dto.parseData("customerPicMsgEntity", new TypeReference<CustomerPicMsgEntity>() {
						});
						if(!Check.NuNObj(customerPicMsg)&&!Check.NuNStr(customerPicMsg.getPicBaseUrl())){
							if(customerPicMsg.getPicBaseUrl().contains("http")){
								orderInfoVo.setTenantPicUrl(customerPicMsg.getPicBaseUrl());
							}else{
								orderInfoVo.setTenantPicUrl(PicUtil.getFullPic(picBaseAddrMona, customerPicMsg.getPicBaseUrl(), customerPicMsg.getPicSuffix(), default_head_size));
							}

						}
					} 
					
					EvaluateRequest evaluateRequest  = new EvaluateRequest();
					evaluateRequest.setOrderSn(orderInfoVo.getOrderSn());
					evaluateRequest.setEvaUserType(UserTypeEnum.All.getUserType());
					evaluateRequest.setEvaUserUid(uid);
					
					dto = JsonEntityTransform.json2DataTransferObject(this.evaluateOrderService.queryEvaluateByOrderSn(JsonEntityTransform.Object2Json(evaluateRequest)));

					if(dto.getCode()!=MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE)){
						Map<String, Object> map = dto.parseData("evaluateMap", new TypeReference<Map<String, Object>>() {
						});
						
						LogUtil.info(LOGGER, "评价查询结果：orderSn={},evalMap={}", orderInfoVo.getOrderSn(),JsonEntityTransform.Object2Json(map));
						
						List<EvaluateOrderEntity>  listOrderEvaluateOrderEntities = null;
						if(map.get("listOrderEvaluateOrder") !=null){
							listOrderEvaluateOrderEntities = JsonEntityTransform.json2ObjectList(JsonEntityTransform.Object2Json(map.get("listOrderEvaluateOrder")), EvaluateOrderEntity.class);
						}
						if(!Check.NuNCollection(listOrderEvaluateOrderEntities)){
							for (EvaluateOrderEntity evaluateOrderEntity : listOrderEvaluateOrderEntities) {
								
								if(evaluateOrderEntity.getEvaUserType().intValue() == EvaluateUserEnum.LAN.getCode()){
									orderInfoVo.setLandEvaStatu(evaluateOrderEntity.getEvaStatu());
									if(evaluateOrderEntity.getEvaStatu()==EvaluateStatuEnum.ONLINE.getEvaStatuCode()){
										orderInfoVo.setLandEvalTime(evaluateOrderEntity.getLastModifyDate());
									}
								}else if(evaluateOrderEntity.getEvaUserType().intValue() == EvaluateUserEnum.TEN.getCode()){
									orderInfoVo.setTenantEvaStatu(evaluateOrderEntity.getEvaStatu());
									if(evaluateOrderEntity.getEvaStatu()==EvaluateStatuEnum.ONLINE.getEvaStatuCode()){
										orderInfoVo.setTenantEvalTime(evaluateOrderEntity.getLastModifyDate());
									}
								}else if(evaluateOrderEntity.getEvaUserType().intValue() == EvaluateUserEnum.LAN_REPLY.getCode()){
									orderInfoVo.setLandReplyTime(evaluateOrderEntity.getCreateTime());
								}  
								
							}

						} 
						
						orderList.set(i, orderInfoVo);
					}
					
				}
				 
			}
			
			LogUtil.info(LOGGER, "待转化列表，list={}，parameter={}", JsonEntityTransform.Object2Json(orderList),JsonEntityTransform.Object2Json(orderEvalRequest));
			
			PageResult pageResult = new PageResult();
			pageResult.setRows(evalOrderService.tranOrder2Eval(orderList,orderEvalRequest));
			pageResult.setTotal(total);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(pageResult), HttpStatus.OK);

		} catch (Exception e) {
			LogUtil.error(LOGGER, "查询评价列表失败，e={}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误-查询异常"), HttpStatus.OK);
		}
	}



}
