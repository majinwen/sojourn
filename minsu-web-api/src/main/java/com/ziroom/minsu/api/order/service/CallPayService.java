
package com.ziroom.minsu.api.order.service;

import java.util.HashMap;
import java.util.Map;

import com.ziroom.minsu.services.common.constant.MessageConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.common.utils.CryptAES;
import com.ziroom.minsu.services.order.dto.ToPayRequest;
import com.ziroom.minsu.services.order.entity.OrderPayResultStatusVo;
import com.ziroom.minsu.services.order.entity.PayVo;
import com.ziroom.minsu.services.order.utils.PayUtil;
import com.ziroom.minsu.valenum.order.OrderPayTypeChannelEnum;
import com.ziroom.minsu.valenum.order.OrderPayTypeEnum;
import com.ziroom.minsu.valenum.order.OrderSourceEnum;
import com.ziroom.minsu.valenum.order.PlatformOrderTypeEnum;

/**
 * <p>调用支付接口</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/1.
 * @version 1.0
 * @since 1.0
 */
@Service("pay.callPayService")
public class CallPayService {


    private static final Logger LOGGER = LoggerFactory.getLogger(CallPayService.class);


    @Value(value = "${PAY.PRE_PAY_URL}")
    private String PRE_PAY_URL;

    @Value(value = "${PAY.PAY_KEY}")
    private String PAY_KEY;

    @Value(value = "${PAY.GEN_ORDER_URL}")
    private String GEN_ORDER_URL;

    @Value(value = "${PAY.SYSTEM_ID}")
    private String SYSTEM_ID;

    @Value(value = "${PAY.QUERY_STATE_URL}")
    private String QUERY_STATE_URL;

    @Value(value = "${PAY.ENCODING}")
    private String ENCODING;





    /**
     * 调用支付系统下单接口，获取支付单号
     * @param toPayRequest
     * @return
     * @throws Exception
     */
    public Map<String,String> createPayOrder(ToPayRequest toPayRequest) throws Exception{
    	
        JSONObject order = makeOrderJson(toPayRequest); //封装下单参数
        LogUtil.info(LOGGER, "【去支付】调用支付系统下单接口，获取支付单号，params:{}", JsonEntityTransform.Object2Json(order));
        Map<String, String> param = new HashMap<String, String>();
        param.put("encryption",CryptAES.AES_Encrypt(PAY_KEY, order.toString())); //加密
        param.put("systemId", SYSTEM_ID);
        String resultContent = postAndGetResult(GEN_ORDER_URL,param);
        LogUtil.info(LOGGER, "【去支付】调用支付系统下单接口，返回结果:{}", resultContent);
        return parseGetPayCodeReponse(resultContent);
    }

    /**
     * 拼调支付下单接口的请求json
     * @param toPayRequest
     * @return
     */
    private JSONObject makeOrderJson(ToPayRequest toPayRequest) {
        JSONArray array = new JSONArray();
        JSONObject detail = null;
        
        for (PayVo pay : toPayRequest.getPayList()) {
            detail = new JSONObject();
            detail.put("orderType", pay.getOrderType());
            detail.put("amount", pay.getPayMoney());
            array.add(detail);
        }
        
        JSONObject order = new JSONObject();
        order.put("bizCode", toPayRequest.getBizeCode());//业务编号
        order.put("uid", toPayRequest.getUserUid());//用户uid
        order.put("totalFee", toPayRequest.getTotalFee());//总金额
        order.put("notifyUrl",toPayRequest.getNotifyUrl());//支付回调url
        order.put("cityCode", SysConst.Common.company_ziroom_minsu);//城市code
        order.put("passAccount", toPayRequest.getPassAccount());//充值类型
        order.put("bizType", SysConst.bize_type);//民宿业务
        order.put("expiredDate", toPayRequest.getExpiredDate());//订单有效时间
        order.put("uidType", toPayRequest.getUidType());
        order.put("payDetail", array);
        String sourceType = OrderSourceEnum.getByClientName(toPayRequest.getSourceType()).getName();//传给支付平台的设备标识
        order.put("sourceType", sourceType);

        return order;

    }

    /**
     * 处理调用下单接口的出参
     * @param resultContent
     * @return
     * @throws Exception
     */
    private Map<String,String> parseGetPayCodeReponse(String resultContent) throws Exception {
        Map<String,String> resMap = new HashMap<String,String>(4);
        if (Check.NuNStr(resultContent)) {
            resMap.put("status", MessageConst.FAIL_CODE);
            resMap.put("message", "下单返回结果为空");
            LogUtil.error(LOGGER, "parseGetPayCodeReponse接口没有返回信息{}", resultContent);
            return resMap;
        }
        try {
            LogUtil.info(LOGGER, "parseGetPayCodeReponse:{}",resultContent);
            JSONObject resJson = JSONObject.parseObject(resultContent);
            resMap.put("status", resJson.getString("status"));
            resMap.put("code", resJson.getString("code"));
            resMap.put("message", resJson.getString("message"));
            resMap.put("data", resJson.getString("data"));
        }catch (Exception e){
            resMap.put("status",  MessageConst.FAIL_CODE);
            resMap.put("message", "下单返回结果非法");
            LogUtil.error(LOGGER, "parseGetPayCodeReponse接口没有返回信息{}", resultContent);
        }
        return resMap;
    }



    /**
     * 提交支付接口
     * @param payCode
     * @param toPayRequest
     * @return
     */
    public Map<String,Object> paySubmit(String payCode, ToPayRequest toPayRequest) {
        String clientPayTypeName = PayUtil.getClientPayType(toPayRequest.getPayType(), toPayRequest.getSourceType());//获取支付方式
        LogUtil.info(LOGGER, "【去支付】获取支付方式, clientPayTypeName:{}", clientPayTypeName);
        
        int payType = OrderPayTypeChannelEnum.getPayChannelByClientName(clientPayTypeName).getPayType();
        int payChannel = OrderPayTypeChannelEnum.getPayChannelByClientName(clientPayTypeName).getPayChannel();//获取支付渠道
        LogUtil.info(LOGGER, "【去支付】获取支付渠道, payChannel:{}", payChannel);

        int payMoney = 0;//计算支付金额
        for (PayVo pay : toPayRequest.getPayList()) {
            if(pay.getOrderType() == PlatformOrderTypeEnum.out_pay.getOrderType()){
                payMoney += pay.getPayMoney();
            }
        }

        String url = PRE_PAY_URL.replace("{cityCode}", SysConst.Common.company_ziroom_minsu).replace("{payChannel}", String.valueOf(payChannel));
        
        Map<String, String> param=new HashMap<String, String>();//封装支付参数
        param.put("orderCode", payCode);
        param.put("payAmount", String.valueOf(payMoney));
        
        LogUtil.info(LOGGER, "【去支付】提交支付接口, url:{}, param:{}", url, param);
        String resultContent = this.postAndGetResult(url, param);
        LogUtil.info(LOGGER, "【去支付】提交支付接口, resultContent{}", resultContent);

        Map<String,Object> resMap = parsePaySubmitReponse(resultContent, payType);
        return resMap;
    }

    /**
     * 处理提交支付接口的出参
     * @param resultContent
     * @return
     * @throws Exception
     */
    private Map<String,Object> parsePaySubmitReponse(String resultContent, int payType){
        //当前参数的打印
        LogUtil.info(LOGGER, "parsePaySubmitReponse resultContent:{}", resultContent);
        Map<String,Object> resMap = new HashMap<>(4);
        if (Check.NuNStr(resultContent)){
            resMap.put("status", MessageConst.FAIL_CODE);
            resMap.put("message", "获取支付链接失败");
            LogUtil.error(LOGGER, "parsePaySubmitReponse接口没有返回信息{}", resultContent);
            return resMap;
        }
        OrderPayTypeEnum payTypeEnum = OrderPayTypeEnum.getPayStatusByCode(payType);
        if (Check.NuNObj(payTypeEnum)){
            resMap.put("status", MessageConst.FAIL_CODE);
            resMap.put("message", "当前支付类型未支持");
            LogUtil.error(LOGGER, "parsePaySubmitReponse接口没有返回信息{}", resultContent);
            return resMap;
        }
        try {
            JSONObject resJson = JSONObject.parseObject(resultContent);
            resMap.put("status", resJson.getString("status"));
            String data = resJson.getString("data");
            //当前的返回的code信息
            resMap.put("code", resJson.getString("code"));
            resMap.put("message", resJson.getString("message"));
            resMap.put("data",payTypeEnum.getRstMap(data));
        }catch (Exception e){
            resMap.put("status", MessageConst.FAIL_CODE);
            resMap.put("message", "解析去支付返回参数异常");
            LogUtil.error(LOGGER, "parsePaySubmitReponse接口没有返回信息{}", resultContent);
        }
        return resMap;
    }





    /**
     * 查詢支付結果
     *
     *
     * @author liyingjie
     * @created 2016年4月8日
     *
     * @param payCode 支付单号
     * @return
     */
    public OrderPayResultStatusVo findPayResult(String payCode) {

        OrderPayResultStatusVo oprs = new OrderPayResultStatusVo();

        if(Check.NuNStr(payCode)){
            LogUtil.info(LOGGER, "findPayResult payCode{}", payCode);
            return oprs;
        }
        JSONObject queryOrder = new JSONObject();//封装查询参数
        queryOrder.put("orderCode", payCode);

        Map<String,String> param = new HashMap<String, String>();
        param.put("encryption", CryptAES.AES_Encrypt(PAY_KEY, queryOrder.toString()));
        param.put("systemId", SYSTEM_ID);

        String resJson = this.postAndGetResult(QUERY_STATE_URL, param);//执行请求、返回结果
        oprs = JsonEntityTransform.json2Entity(resJson, OrderPayResultStatusVo.class); //将结果串封装成对象
        return oprs;
    }


    /**
     * 发送post请求
     * @author liyingjie
     * @created 2016年4月8日
     *
     * @param url http请求路径 param请求参数
     * @return
     */
    private String postAndGetResult(String url,Map<String,String> param){
        String resJson = "";
        if(Check.NuNStr(url) || Check.NuNMap(param)){
            LogUtil.info(LOGGER, "postAndGetResult url:{},param:{}", url,param);
            return resJson;
        }
        try {
        	resJson = CloseableHttpUtil.sendFormPost(url, param);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "postAndGetResult resJson{}", resJson);
        }
        return resJson;
    }

}

