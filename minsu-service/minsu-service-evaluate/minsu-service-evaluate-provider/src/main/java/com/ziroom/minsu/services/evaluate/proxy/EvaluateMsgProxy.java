package com.ziroom.minsu.services.evaluate.proxy;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.SmsTemplateEntity;
import com.ziroom.minsu.entity.order.OrderEntity;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.cms.api.inner.ShortChainMapService;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.common.constant.JpushConst;
import com.ziroom.minsu.services.common.jpush.JpushUtils;
import com.ziroom.minsu.services.common.jpush.base.JpushConfig;
import com.ziroom.minsu.services.common.jpush.base.MessageTypeEnum;
import com.ziroom.minsu.services.common.sms.MessageUtils;
import com.ziroom.minsu.services.common.sms.base.SmsMessage;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.common.utils.ZkUtil;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.valenum.common.JumpOpenAppEnum;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import com.ziroom.minsu.valenum.order.OrderEvaStatusEnum;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;

/**
 * <p>评价消息接口</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @since 1.0
 */
@Component("evaluate.evaluateMsgProxy")
public class EvaluateMsgProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(EvaluateMsgProxy.class);

    @Resource(name = "basedata.smsTemplateService")
    private SmsTemplateService smsTemplateService;

    @Resource(name = "cms.shortChainMapService")
    private ShortChainMapService shortChainMapService;

    @Resource(name ="basedata.confCityService")
    private ConfCityService confCityService;

    @Value("#{'${JPUSH_APP_M_HOUSE}'.trim()}")
    private String JPUSH_APP_M_HOUSE;

    private String smsAppJumpUrl = "common/ee5f86/goToApp?param=";
    //app 短信通过连接 跳转评价详情
    //private String smsAppJumpUrl = "appminsu://ziroom.app/openeva?orderSn=%s&userType=%d";


    /**
     * 房东或者房客离入住结束2天后 如果没有评价  给房客 或者房客发送消息
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年02月13日 11:58:12
     */
    public void semdMsg2DayNoticeEva(List<OrderInfoVo> list) {
        if (Check.NuNCollection(list)) {
            return;
        }
        for (OrderInfoVo orderInfoVo : list) {
            int evaStatus = orderInfoVo.getEvaStatus().intValue();
            if (evaStatus == OrderEvaStatusEnum.WAITINT_EVA.getCode() || evaStatus == OrderEvaStatusEnum.UESR_HVA_EVA.getCode()) {
                //给房东发送消息
                sendMsgToLan2DayNoticeEva(orderInfoVo);
            }
            if (evaStatus == OrderEvaStatusEnum.WAITINT_EVA.getCode() || evaStatus == OrderEvaStatusEnum.LANLORD_EVA.getCode()) {
                //给房客发送消息
                sendMsgToTen2DayNoticeEva(orderInfoVo);
            }
        }
    }


    /**
     * 离开提示 房客填写评价
     * <p>
     * 短信内容 ： 您在xxxx市的旅行已结束两天啦，是否已经安全到家了呢？别忘了评价您此次入住的体验，XXXX(链接地址)。
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年02月10日 16:02:51
     */
    public void sendMsgToTen2DayNoticeEva(OrderInfoVo info) {
        LogUtil.info(LOGGER, "房客未评价 给房客发送消息 orderSn:{}", info.getOrderSn());

        DataTransferObject nameDto = JsonEntityTransform.json2DataTransferObject(confCityService.getCityNameByCode(info.getCityCode()));
        if (nameDto.getCode() == DataTransferObject.SUCCESS){
            info.setCityName(nameDto.parseData("cityName", new TypeReference<String>() {}));
        }
        //短信
        Map<String, String> smsPar = new HashMap<>();
        smsPar.put("phone", info.getUserTel());
        smsPar.put("{1}", info.getCityName());
        //推送
        Map<String, String> pushPar = new HashMap<>();
        pushPar.put("uid", info.getUserUid());
        pushPar.put("{1}", info.getCityName());

        Map<String, String> bizPar = new HashMap<>();
        bizPar.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALUE_7);
        bizPar.put(JpushConst.MSG_HAS_RESPONSE, "1");
        bizPar.put(JpushConst.MSG_TAG_TYPE, JpushConst.MSG_TARGET_TENANT);
        bizPar.put("orderSn", info.getOrderSn());
        bizPar.put("userType", String.valueOf(UserTypeEnum.TENANT.getUserCode()));
        this.sendSmsAndPushMsg(MessageTemplateCodeEnum.ORDER_FINISH_2DAY_NOTICE_TENANT_EVAL.getCode(), smsPar, pushPar, bizPar, getContentJump(info.getOrderSn(), UserTypeEnum.TENANT.getUserCode()));
    }

    /**
     * 房东已填写完成，提示房客写评价
     * 短信内容 ： （房东）xxx已经对您的此次入住进行了评价，您可以在完成评价后查看Ta对您的评价哦！
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年02月10日 16:04:09
     */
    public void sendMsgToTenAndLanFinish(OrderEntity order) {
        LogUtil.info(LOGGER, "房东已填写完成，提示房客写评价 orderSn:{}", order.getOrderSn());
        //短信
        Map<String, String> smsPar = new HashMap<>();
        smsPar.put("phone", order.getUserTel());
        smsPar.put("{1}", order.getLandlordName());
        //推送
        Map<String, String> pushPar = new HashMap<>();
        pushPar.put("uid", order.getUserUid());
        pushPar.put("{1}", order.getLandlordName());

        Map<String, String> bizPar = new HashMap<>();
        bizPar.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALUE_7);
        bizPar.put(JpushConst.MSG_HAS_RESPONSE, "1");
        bizPar.put(JpushConst.MSG_TAG_TYPE, JpushConst.MSG_TARGET_TENANT);
        bizPar.put("orderSn", order.getOrderSn());
        bizPar.put("userType", String.valueOf(UserTypeEnum.TENANT.getUserCode()));

        this.sendSmsAndPushMsg(MessageTemplateCodeEnum.LANLORD_EVALUATE.getCode(), smsPar, pushPar, bizPar, getContentJump(order.getOrderSn(), UserTypeEnum.TENANT.getUserCode()));

    }

    /**
     * 提示房东待评价短信和消息
     * <p>
     * 短信内容：（房客）xxx已退房两天，您还没有对Ta进行评价，及时互评可以提升您的搜索排名，同时也可以帮助我们更快速的进步，XXXX(链接地址)
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年02月10日 16:05:32
     */
    public void sendMsgToLan2DayNoticeEva(OrderInfoVo info) {
        LogUtil.info(LOGGER, "房东未评价给房东发送消息 orderSn:{}", info.getOrderSn());
        //短信
        Map<String, String> smsPar = new HashMap<>();
        smsPar.put("phone", info.getLandlordTel());
        smsPar.put("{1}", info.getUserName());
        //推送
        Map<String, String> pushPar = new HashMap<>();
        pushPar.put("uid", info.getLandlordUid());
        pushPar.put("{1}", info.getUserName());

        Map<String, String> bizPar = new HashMap<>();
        bizPar.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALUE_7);
        bizPar.put(JpushConst.MSG_HAS_RESPONSE, "1");
        bizPar.put(JpushConst.MSG_TAG_TYPE, JpushConst.MSG_TARGET_LAN);
        bizPar.put("orderSn", info.getOrderSn());
        bizPar.put("userType", String.valueOf(UserTypeEnum.LANDLORD.getUserCode()));
        this.sendSmsAndPushMsg(MessageTemplateCodeEnum.ORDER_FINISH_2DAY_NOTICE_LANLORD_EVAL.getCode(), smsPar, pushPar, bizPar, getContentJump(info.getOrderSn(), UserTypeEnum.LANDLORD.getUserCode()));
    }

    /**
     * 房客已完成评价  提示房东去评价
     * <p>
     * 短信内容 ： （房客）xxx已经对Ta的此次入住进行了评价，您可以在完成评价后查看Ta的入住体验哦！
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年02月10日 16:07:04
     */
    public void sendMsgToLanAndTenFinish(OrderEntity order) {
        LogUtil.info(LOGGER, "房客写完评价，提示房东写评价 orderSn:{}", order.getOrderSn());
        if (order.getOrderStatus() == OrderStatusEnum.CHECKED_IN.getOrderStatus() || order.getOrderStatus() == OrderStatusEnum.CHECKED_IN_BILL.getOrderStatus()){
            //房客已评价  房东未到评价时间
            return;
        }
        //短信
        Map<String, String> smsPar = new HashMap<>();
        smsPar.put("phone", order.getLandlordTel());
        smsPar.put("{1}", order.getUserName());
        //推送
        Map<String, String> pushPar = new HashMap<>();
        pushPar.put("uid", order.getLandlordUid());
        pushPar.put("{1}", order.getUserName());

        Map<String, String> bizPar = new HashMap<>();
        bizPar.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALUE_7);
        bizPar.put(JpushConst.MSG_HAS_RESPONSE, "1");
        bizPar.put(JpushConst.MSG_TAG_TYPE, JpushConst.MSG_TARGET_LAN);
        bizPar.put("orderSn", order.getOrderSn());
        bizPar.put("userType", String.valueOf(UserTypeEnum.LANDLORD.getUserCode()));

        this.sendSmsAndPushMsg(MessageTemplateCodeEnum.TENANT_EVALUATE.getCode(), smsPar, pushPar, bizPar, getContentJump(order.getOrderSn(), UserTypeEnum.LANDLORD.getUserCode()));
    }

    /**
     * 房客入住当天晚上8点给房客发短信通知写评价
     * <p>
     * 短信内容： 您已成功入住xxxx（房东）的xxxx（房源）了吗？写一条真诚的评价来分享您的此次入住体验，XXXX(链接地址)
     *
     * @param
     * @return
     * @author jixd
     * @created 2017年02月13日 10:23:10
     */
    public void sendMsgToTenCheckIn(List<OrderInfoVo> list) {
        if (Check.NuNCollection(list)){
            return;
        }
        for (OrderInfoVo orderInfoVo : list) {
        	if( orderInfoVo.getOrderStatus() == OrderStatusEnum.FINISH_NEGOTIATE.getOrderStatus() || orderInfoVo.getOrderStatus() == OrderStatusEnum.CANCEL_NEGOTIATE.getOrderStatus()){
        		continue;
        	}
            LogUtil.info(LOGGER, "房客入住当天晚上8点给房客发短信通知写评价 orderSn:{}", orderInfoVo.getOrderSn());
            Map<String, String> smsPar = new HashMap<>();
            smsPar.put("phone", orderInfoVo.getUserTel());
            smsPar.put("{1}", orderInfoVo.getLandlordName());
            String name = orderInfoVo.getRentWay().intValue() == 0 ? orderInfoVo.getHouseName() : orderInfoVo.getRoomName();
            smsPar.put("{2}", name);
            //推送
            Map<String, String> pushPar = new HashMap<>();
            pushPar.put("uid", orderInfoVo.getUserUid());
            pushPar.put("{1}", orderInfoVo.getLandlordName());
            pushPar.put("{2}", name);

            Map<String, String> bizPar = new HashMap<>();
            bizPar.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALUE_7);
            bizPar.put(JpushConst.MSG_HAS_RESPONSE, "1");
            bizPar.put(JpushConst.MSG_TAG_TYPE, JpushConst.MSG_TARGET_LAN);

            bizPar.put("orderSn", orderInfoVo.getOrderSn());
            bizPar.put("userType", String.valueOf(UserTypeEnum.TENANT.getUserCode()));

            sendSmsAndPushMsg(MessageTemplateCodeEnum.CHECKIN_NOTICE_TENANT_EVAL.getCode(), smsPar, pushPar, bizPar, getContentJump(orderInfoVo.getOrderSn(), UserTypeEnum.TENANT.getUserCode()));
        }


    }


    //发送短信和推送
    private void sendSmsAndPushMsg(int smsCode,Map<String, String> smsPar,Map<String, String> pushPar, Map<String, String> bizPar,String jumpUrl) {
        if (!bizPar.containsKey(JpushConst.MSG_BODY_TYPE_KEY)) {
            bizPar.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.MSG_BODY_TYPE_VALUE);
            bizPar.put(JpushConst.MSG_PUSH_TIME, String.valueOf(System.currentTimeMillis()));
        }
        String isAdd = ZkUtil.getZkSysValue(EnumMinsuConfig.minsu_isAddUrl.getType(), EnumMinsuConfig.minsu_isAddUrl.getCode());
        if ("1".equals(isAdd)){
            //不可点击
            bizPar.put(JpushConst.MSG_HAS_RESPONSE, "0");
        }
        
        SendSmsAndPushMsgTasker tasker = new SendSmsAndPushMsgTasker(smsCode, jumpUrl, isAdd, pushPar, smsPar, bizPar);
         
        SendThreadPool.execute(tasker);
    }


    //获取连接详情
    private String getContentJump(String orderSn, Integer type) {
        JSONObject object = new JSONObject();
        object.put("orderSn",orderSn);
        object.put("type",type);
        object.put("jumpType", JumpOpenAppEnum.EVALUATE_INFO.getCode());
        String param = "";
        try {
            param = URLEncoder.encode(object.toString(),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = JPUSH_APP_M_HOUSE + smsAppJumpUrl + param;
        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(shortChainMapService.generateShortLink(url, "001"));
        if (dto.getCode() == DataTransferObject.SUCCESS) {
            String shortLink = (String) dto.getData().get("shortLink");
            return shortLink;
        }
        return null;
    }
    
    
    
    /**
     * <p>评价消息接口</p>
     * <p>
     * <PRE>
     * <BR>	修改记录
     * <BR>-----------------------------------------------
     * <BR>	修改日期			修改人			修改内容
     * </PRE>
     *
     * @author zl
     * @version 1.0
     * @since 1.0
     */
    class SendSmsAndPushMsgTasker implements Runnable{

    	private int smsCode;
    	private String jumpUrl;
    	private String isAdd;
    	private Map<String, String> pushPar;
    	private Map<String, String> smsPar;
    	private Map<String, String> bizPar;
    	
    	public SendSmsAndPushMsgTasker(int smsCode, String jumpUrl, String isAdd, Map<String, String> pushPar,
				Map<String, String> smsPar, Map<String, String> bizPar) {
			super();
			this.smsCode = smsCode;
			this.jumpUrl = jumpUrl;
			this.isAdd = isAdd;
			this.pushPar = pushPar;
			this.smsPar = smsPar;
			this.bizPar = bizPar;
		}

		@Override
        public void run() {
            LogUtil.info(LOGGER,"评价发送消息smsCode={}",smsCode);
            //短信模板
            DataTransferObject templateJsonDto = JsonEntityTransform.json2DataTransferObject(smsTemplateService.getTemplateByCode(ValueUtil.getStrValue(smsCode)));
            SmsTemplateEntity smsTemplateEntity = templateJsonDto.parseData("template", new TypeReference<SmsTemplateEntity>() {
            });
            String smsContent = smsTemplateEntity.getSmsComment();
            String shortSmsContent = smsContent;
            if (!Check.NuNStr(jumpUrl) && "0".equals(isAdd)) {
                shortSmsContent += "（"+jumpUrl+"）";
            }
            JpushConfig jpushConfig = new JpushConfig();
            jpushConfig.setMessageType(MessageTypeEnum.MESSAGE.getCode());
            jpushConfig.setExtrasMap(bizPar);
            jpushConfig.setContent(smsContent);
            LogUtil.info(LOGGER,"短信内容content={}",shortSmsContent);

            try{
                MessageUtils.sendSms(new SmsMessage(smsPar.get("phone"), shortSmsContent), ValueUtil.cleanMap(smsPar));
            }catch (Exception e){
                LogUtil.info(LOGGER,"评价发送短信异常e={}",e);
            }
            try{
                JpushUtils.sendPushOne(pushPar.get("uid"), jpushConfig, ValueUtil.cleanMap(pushPar));
            }catch (Exception e){
                LogUtil.info(LOGGER,"评价发送推送异常e={}",e);
            }
        }
    	
    }


}
