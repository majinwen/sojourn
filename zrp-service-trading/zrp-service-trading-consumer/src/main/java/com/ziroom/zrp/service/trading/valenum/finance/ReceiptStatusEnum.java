package com.ziroom.zrp.service.trading.valenum.finance;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年11月10日 11:01
 * @since 1.0
 */

public enum ReceiptStatusEnum {

    YSK(0,"已收款"),
    WSK(1,"未收款"),
    DH(2,"打回");

    ReceiptStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    private int code;

    private String name;
    
    public static String getStatusNameByCode(int code){
    	for(final ReceiptStatusEnum receiptStatusEnum : ReceiptStatusEnum.values()){
            if(receiptStatusEnum.getCode() == code){
                return receiptStatusEnum.getName();
            }
        }
		return null;
    }
}
