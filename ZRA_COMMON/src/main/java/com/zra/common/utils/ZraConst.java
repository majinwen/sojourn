package com.zra.common.utils;

public interface ZraConst {

    
    /**
     * 再次签约延长天数
     */
    public static final int DELAY_RENT_DAYS = 2;
    
    /**
     * 短租提前转预订提前天数
     */
    public static final int DELAY_SHORT_OPEN_ORDER_DAYS = 7;
    
    /**
     * 长租转预订提前天数
     */
    public static final int DELAY_LONG_OPEN_ORDER_DAYS = 14;


    /**
     * 延长房租缴纳天数
     */
    public static final int DELAY_RENT_REMIND_DAYS = 7;


    /*
        在线支付 收款渠道 created by cuigh6
     */
    String COLLECTION_CHANNEL = "在线支付";

    /*
        收款方式 created cuigh6
     */
    String COLLECTION_WAY = "在线支付";

    /**
     * 应收账单状态 1.部分收款2.已收款
     */
    Integer BILL_STATE_YSK=2;
    Integer BILL_STATE_BFSK=1;

    //未删除
    public final String NOT_DEL_STR = "0";

    public final int NOT_DEL_INT = Integer.valueOf(NOT_DEL_STR);

    //有效
    public final String VALID_STR = "1";

    public final int VALID_INT = Integer.valueOf(VALID_STR);
}
