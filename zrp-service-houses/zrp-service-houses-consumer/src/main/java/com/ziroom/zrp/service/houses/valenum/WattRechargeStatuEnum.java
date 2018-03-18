package com.ziroom.zrp.service.houses.valenum;

/**
 * <p>充值状态 10=待充值 （a. 充值接口直接返回失败 b. 本身业务异常造成） 11=充值中 12=充值成功 13=充值失败</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @Date Created in 2018年01月25日 21:13
 * @since 1.0
 */
public enum  WattRechargeStatuEnum {

    WAITTING_RECHARGE(10,"待充值"),
    RECHARGEING(11,"充值中"),
    RECHARGE_SUCCESS(12,"充值成功"),
    RECHARGE_FAILED(13,"充值失败");


    WattRechargeStatuEnum(int code,String val){
        this.code = code;
        this.val = val;
    }
    private  int code;


    private  String val;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
