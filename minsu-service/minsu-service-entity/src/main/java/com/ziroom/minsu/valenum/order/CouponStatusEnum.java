package com.ziroom.minsu.valenum.order;

/**
 * <p>优惠券状态</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/25.
 * @version 1.0
 * @since 1.0
 */
public enum CouponStatusEnum {

    WAIT(1,1,"未领取"),
    SEND(6,2,"已发送"),
    GET(2,1,"已领取"){
        @Override
        public boolean checkStatus() {
            return true;
        }
    },
    FROZEN(3,2,"已冻结"),
    USED(4,2,"已使用"),
    OVER_TIME(5,2,"已过期");

    CouponStatusEnum( int code,int outCode, String name) {
        this.code = code;
        this.outCode = outCode;
        this.name = name;
    }

    public static CouponStatusEnum getByCode(int code){
        for(final CouponStatusEnum ose : CouponStatusEnum.values()){
            if(ose.getCode() == code){
                return ose;
            }
        }
        return null;
    }

    /** code */
    private int code;
    /** 对外展示code */
    private int outCode;

    /** 名称 */
    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getOutCode() {
        return outCode;
    }

    /**
     * 校验当前优惠券状态是否可用
     * @return
     */
    public boolean checkStatus(){
        return false;
    }
}
