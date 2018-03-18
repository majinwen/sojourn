package com.zra.common.dto.pay;

/**
 * <p>卡券查询入参</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年12月23日 16:42
 * @since 1.0
 */

public class CardCouponQueryRequest {

    /**
     * 业务线id
     * 长租：100001、100002、100003、100004
     * 自如寓：100005
     */
    private Integer system_id;

    /**
     * 	用户唯一标识UID
     */
    private String uid;

    /**
     * 期数（首期/非首期）
     * 首期传1，其他期数传对应整数，如3期传3,8期传8
     */
    private Integer periods;

    public Integer getSystem_id() {
        return system_id;
    }

    public void setSystem_id(Integer system_id) {
        this.system_id = system_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getPeriods() {
        return periods;
    }

    public void setPeriods(Integer periods) {
        this.periods = periods;
    }
}
