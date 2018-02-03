package com.ziroom.minsu.valenum.order;

/**
 * <p>付款单状态 类型</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/20.
 * @version 1.0
 * @since 1.0
 */
public enum PaymentStatusEnum {

	INEFFECTIVE(0,"未生效"),
	
    UN_PAY(1,"未付款"),
    
    HAS_CONSUME(2, "已消费"),
    
    HAS_REQUEST_PAY(3, "已申请打款"),

    HAS_PAY(4, "已打款"),
    
    HAVE_ACCOUNT(5, "已打余额"),
    
    HAVE_FREEZE(6, "已打冻结"),

    CHECKOUT_CANCEL(7, "提前退房取消"),
    
    NO_BANK_CARD(8, "未绑定银行卡"), //暂时无用
    
    FAILED(9, "失败"),

    BLACK(99, "黑名单不打款"),

    FAILED_PAY_UNDO(20, "打款失败待处理"),
    
    FAILED_PAY_DONE(21, "打款失败已处理"),

    OFFSET_CANCEL(30,"罚款冲抵取消"),

    OFFSET_REPRO_CANCEL(31,"罚款冲抵重新生成取消");

    PaymentStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }


    /** code */
    private int code;

    /** 名称 */
    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
