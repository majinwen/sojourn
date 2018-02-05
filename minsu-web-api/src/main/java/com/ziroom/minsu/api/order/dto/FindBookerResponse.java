package com.ziroom.minsu.api.order.dto;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/12/2 14:25
 * @version 1.0
 * @since 1.0
 */
public class FindBookerResponse {

    private Integer isNeedReplenish = 0;

    private String tips;

    private ContactDetailResponse bookerDetail;


    public Integer getIsNeedReplenish() {
        return isNeedReplenish;
    }

    public void setIsNeedReplenish(Integer isNeedReplenish) {
        this.isNeedReplenish = isNeedReplenish;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public ContactDetailResponse getBookerDetail() {
        return bookerDetail;
    }

    public void setBookerDetail(ContactDetailResponse bookerDetail) {
        this.bookerDetail = bookerDetail;
    }
}
