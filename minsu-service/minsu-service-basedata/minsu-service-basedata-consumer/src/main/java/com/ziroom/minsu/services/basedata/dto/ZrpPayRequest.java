package com.ziroom.minsu.services.basedata.dto;

/**
 * <p>支付方式请求类</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月12日 17:50
 * @since 1.0
 */
public class ZrpPayRequest {
    /**
     * 出租类型 1 年租 2 月租 3 日租
     */
    private Integer rentType;
    /**
     * 出租时间
     */
    private Integer rentTime;

    public Integer getRentType() {
        return rentType;
    }

    public void setRentType(Integer rentType) {
        this.rentType = rentType;
    }

    public Integer getRentTime() {
        return rentTime;
    }

    public void setRentTime(Integer rentTime) {
        this.rentTime = rentTime;
    }
}
