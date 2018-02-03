package com.ziroom.minsu.valenum.order;

/**
 * <p>订单付款单状态枚举</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/1.
 * @version 1.0
 * @since 1.0
 */
public enum OrderAccountsStatusEnum {

    NO(0,"未结算"),
    ING(1,"结算中"),
    FINISH(2,"结算完成");

    OrderAccountsStatusEnum(int accountsStatus, String statusName) {
        this.accountsStatus = accountsStatus;
        this.statusName = statusName;
    }



    /**
     * 获取
     * @param accountsStatus
     * @return
     */
    public static OrderAccountsStatusEnum getAccountsStatusByCode(int accountsStatus) {

        for (final OrderAccountsStatusEnum status : OrderAccountsStatusEnum.values()) {
            if (status.getAccountsStatus() == accountsStatus) {
                return status;
            }
        }
        return null;
    }



    /** 订单支付状态 */
    private int accountsStatus;

    /** 支付状态名称 */
    private String statusName;

    public int getAccountsStatus() {
        return accountsStatus;
    }

    public String getStatusName() {
        return statusName;
    }
}
