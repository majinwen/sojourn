package com.ziroom.minsu.valenum.order;

/**
 * <p>订单支付状态枚举</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liyingjie 2016/4/9.
 * @version 1.0
 * @since 1.0
 */
public enum OrderPayTypeChannelEnum {
	
	//银联信用卡
    yl_card_ios_pay(1,12,"yl_ios_pay","yl_card_ios_pay",16),
    yl_card_ad_pay(1,12,"yl_ad_pay","yl_card_ad_pay",16),
    
    
    //银联借记
	yljj_ios_pay(1,13,"yl_ios_pay","yljj_ios_pay",16),
	yljj_ad_pay(1,13,"yl_ad_pay","yljj_ad_pay",16),
	
	//银联贷记
	yldj_ios_pay(1,12,"yl_ios_pay","yldj_ios_pay",16),
	yldj_ad_pay(1,12,"yl_ad_pay","yldj_ad_pay",16),
	
	//支付宝
    zfb_ios_pay(2,32,"ali_mobile","zfb_ios_pay",8),
    zfb_ad_pay(2,32,"ali_mobile","zfb_ad_pay",8),
    
    //京东
    jd_ios_pay(3,20,"jd_pay","jd_ios_pay",19),
    jd_ad_pay(3,20,"jd_pay","jd_ad_pay",19),
    
    //京东移动
    jd_m_ios_pay(31,21,"jd_m_pay","jd_m_ios_pay",21),
    jd_m_ad_pay(31,21,"jd_m_pay","jd_m_ad_pay",21),
    
    //微信
    wx_ios_pay(41,42,"wx_ios_pay","wx_ios_pay",17),
    wx_ad_pay(42,42,"wx_ad_pay","wx_ad_pay",17),
    
    wxsm_ios_pay(42,43,"wx_pay","wxsm_ios_pay",17),
    wxsm_ad_pay(42,43,"wx_pay","wxsm_ad_pay",17),
    
    //财付通
    cft_wx_ios_pay(5,41,"cft_wx_pay","cft_wx_ios_pay",17),
    cft_wx_ad_pay(5,41,"cft_wx_pay","cft_wx_ad_pay",17),
    
    //优惠券
    coupon_pay(109,0,"cash_coupon","cash_coupon",70),

    act_pay(110,0,"cash_coupon","cash_coupon",70);
    
    
    /***
     * 参数对照
     * @param payType 系统中存储的支付方式(OrderPayTypeEnum)
     * @param payChannel  支付平台 支付接口时 支付渠道
     * @param name   支付平台回调时 支付方式
     * @param clientName  客户端 传过来 支付方式  机型  
     * @param receiptMethod  对应财务 同步收入字段 receiptMethod
     */
    OrderPayTypeChannelEnum(int payType, int payChannel,String name,String clientName,int receiptMethod) {
        this.payType = payType;
        this.payChannel = payChannel;
        this.plateFormName = name;
        this.clientName = clientName;
        this.receiptMethod = receiptMethod;
    }


    
    /**
     * 获取
     * @param payType
     * @return
     */
    public static OrderPayTypeChannelEnum getPayTypeByPlateFormName(String payName) {

        for (final OrderPayTypeChannelEnum type : OrderPayTypeChannelEnum.values()) {
            if (type.getPlateFormName().equals(payName) ) {
                return type;
            }
        }
        return null;
    }
    
    
    /**
     * 获取
     * @param payStatus
     * @return
     */
    public static OrderPayTypeChannelEnum getPayStatusByName(int payType) {

        for (final OrderPayTypeChannelEnum type : OrderPayTypeChannelEnum.values()) {
            if (type.getPayType() == payType) {
                return type;
            }
        }
        return null;
    }

    /**
     * 获取
     * @param payChannel
     * @return
     */
    public static OrderPayTypeChannelEnum getPayChannelByClientName(String clientName) {

        for (final OrderPayTypeChannelEnum type : OrderPayTypeChannelEnum.values()) {
            if (type.getClientName().equals(clientName) ) {
                return type;
            }
        }
        return null;
    }

    
    /** 支付类型 */
    private int payType;

    /** 支付渠道*/
    private int payChannel;
    
    /** 支付回调时 name*/
    private String plateFormName;
    
    /** 客户端传递 获取payChannel*/
    private String clientName;
    
    /** 同步收入  */
    private int receiptMethod;
    

    public int getPayType() {
        return payType;
    }

    public int getPayChannel() {
        return payChannel;
    }
    
    public String getPlateFormName() {
        return plateFormName;
    }
    
    public String getClientName() {
        return clientName;
    }
    
    public int getReceiptMethod() {
        return receiptMethod;
    }
    
    public static void main(String[] args) {
		System.out.println(OrderPayTypeChannelEnum.getPayTypeByPlateFormName("ali_mobile"));
	}

}
