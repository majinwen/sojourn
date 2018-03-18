package com.ziroom.zrp.service.trading.valenum.finance;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>付款渠道：(receiptMethod)</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author wangxm113
 * @Date 2017年10月15日 15时49分
 * @Version 1.0
 * @Since 1.0
 */
public enum ReceiptMethodEnum {
    ZXZF("zxzf", "在线支付"),
    XXZF("xxzf", "线下支付"),
    YHQ("yhq", "优惠券"),
    NB("nb", "内部"),
    YE("ye", "余额"),
    ZXZF_BGZH("zxzf_bgzh", "在线支付-不过账户");

    private String code;
    private String name;

    ReceiptMethodEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
      * @description: 获取线上支付方式
      * @author: lusp
      * @date: 2017/11/21 下午 19:50
      * @params:
      * @return: Map<String,String>
      */
    public static Map<String,String> toMap(){
        Map<String,String> map = new HashMap<>();
        for(final ReceiptMethodEnum receiptMethodEnum : ReceiptMethodEnum.values()){
            if(receiptMethodEnum.getCode().equals("xxzf") ){
                continue;
            }
            map.put(receiptMethodEnum.getCode(),receiptMethodEnum.getName());
        }
        return map;
    }
}
