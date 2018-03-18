package com.zra.common.dto.pay;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>卡券消费入参</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年12月24日 16:42
 * @since 1.0
 */

public class CardCouponUseRequest {

    /**
     * 	用户唯一标识UID
     */
    private String uid;

    /**
     * 被使用的订单单号
     */
    private String order_id;

    /**
     * 租金卡/员工卡数据
     */
    private List<Card> card_list;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public List<Card> getCard_list() {
        return card_list;
    }

    public void setCard_list(List<Card> card_list) {
        this.card_list = card_list;
    }

    public static class Card{

        /**
         * 卡号列表 例如:"AAfdfeee，AAfdfeee，AAfdfeee"
         */
        private String code;

        /**
         * 卡类型
         */
        private Integer type;

        /**
         * 总金额 单位分
         */
        private Integer money;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getMoney() {
            return money;
        }

        public void setMoney(Integer money) {
            this.money = money;
        }
    }

}
