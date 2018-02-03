
package com.ziroom.minsu.valenum.order;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>订单支付状态枚举</p>
 * <p/>
 *
 * 017.06.06 增加自定义 活动支付类型 add by jixd
 *
 * @author liyingjie 2016/4/9.
 * @version 1.0
 * @since 1.0
 */
 public enum OrderPayTypeEnum {

    yl_pay(1,"银联支付"),
    
    zfb_pay(2,"支付宝支付"){
        @Override
        public Map getRstMap(String data) {
            //支付宝参数特殊处理
            Map<String, String> zfbMap = new HashMap<String, String>();
            zfbMap.put("zfbUrl", data);
            return zfbMap;
        }
    },
    jd_pay(3,"京东支付"),
    jd_m_pay(31,"京东移动支付"),
    wx_ios_pay(41,"微信IOS支付"),
    wx_ad_pay(42,"微信Android支付"),
    cft_wx_pay(5,"威富通（微信公众号）支付"),
    cash_coupon_pay(106,"代金券"),
    card_pay(107,"积分卡支付"),
    account_pay(108,"账户余额支付(余额消费)"),
    coupon_pay(109,"优惠券"),
    act_pay(110,"优惠活动");


    /**
     * 解析当前的返回结果
     * @param data
     * @return
     */
    public Map getRstMap(String data){
        if (Check.NuNStr(data)){
            return null;
        }
        return JsonEntityTransform.json2Map(data);
    }
    
    OrderPayTypeEnum(int payType, String payTypeName) {
        this.payType = payType;
        this.payTypeName = payTypeName;
    }



    /**
     * 获取
     * @param payType
     * @return
     */
    public static OrderPayTypeEnum getPayStatusByCode(int payType) {
    	for (final OrderPayTypeEnum type : OrderPayTypeEnum.values()) {
            if (type.getPayType() == payType) {
                return type;
            }
        }
        return null;
    }



    /** 支付类型*/
    private int payType;

    /** 支付类型名称 */
    private String payTypeName;

    public int getPayType() {
        return payType;
    }

    public String getPayTypeName() {
        return payTypeName;
    }
}

