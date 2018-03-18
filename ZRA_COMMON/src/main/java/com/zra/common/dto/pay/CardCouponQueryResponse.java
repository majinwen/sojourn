package com.zra.common.dto.pay;

import java.util.List;

/**
 * <p>查询客户卡券信息返回值</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年12月24日 18:03
 * @since 1.0
 */

public class CardCouponQueryResponse {

    private List<CardCouponS> coupons;

    public List<CardCouponS> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<CardCouponS> coupons) {
        this.coupons = coupons;
    }

    public static class CardCouponS{

        private String name;

        private Integer orderType;

        private List<CardCouponEntity> cards;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getOrderType() {
            return orderType;
        }

        public void setOrderType(Integer orderType) {
            this.orderType = orderType;
        }

        public List<CardCouponEntity> getCards() {
            return cards;
        }

        public void setCards(List<CardCouponEntity> cards) {
            this.cards = cards;
        }

        public static class CardCouponEntity{

            private String code;

            private String money;

            private Integer type;

            private String start;

            private String end;

            private String name;

            private String desc;

            private String rule;

            private Integer useStatus;

            private String superposition;

            private String service_line_id;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public Integer getType() {
                return type;
            }

            public void setType(Integer type) {
                this.type = type;
            }

            public String getStart() {
                return start;
            }

            public void setStart(String start) {
                this.start = start;
            }

            public String getEnd() {
                return end;
            }

            public void setEnd(String end) {
                this.end = end;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getRule() {
                return rule;
            }

            public void setRule(String rule) {
                this.rule = rule;
            }

            public Integer getUseStatus() {
                return useStatus;
            }

            public void setUseStatus(Integer useStatus) {
                this.useStatus = useStatus;
            }

            public String getSuperposition() {
                return superposition;
            }

            public void setSuperposition(String superposition) {
                this.superposition = superposition;
            }

            public String getService_line_id() {
                return service_line_id;
            }

            public void setService_line_id(String service_line_id) {
                this.service_line_id = service_line_id;
            }
        }

    }


}
