package com.ziroom.minsu.api.customer.constant;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/9/7.
 * @version 1.0
 * @since 1.0
 */
public class CustomerConstant {



    /**
     * 请求用户中心的成功
     */
    public static final String CUSTOMER_RST_SUCCESS = "success";




    /** 我的账户  */
    public static class Account {

        /**
         * 我的账户信息的提示信息
         */
        public static final String bank_msg = "提示：提现操作请登录自如网站->我的自如->我的钱包完成";


        /** 结算方式  */
        public static final String check_type_title = "结算方式";

        /** 订单结算的描述  */
        public static final String check_type_order_dis = "小于7天的订单每单结算1次\n大于7天的订单每7天结算一次";

        /** 天结算的描述  */
        public static final String check_type_day_dis = "每天结算一次";

        /** 同步业务账  */
        public static final String check_type_show = "结算方式:";

        /** 结算方式code  */
        public static final String check_type_code = "checkType";


        /** 收款方式  */
        public static final String receive_type_title = "收款方式";

        /** 到空间的描述  */
        public static final String receive_type_my_dis = "选择账户空间后，账户空间余额可体现到银行卡";

        /** 到银行卡的描述  */
        public static final String receive_type_card_dis = "绑定银行卡后，房费自动转账至您绑定的银行卡";

        /** 同步业务账  */
        public static final String receive_type_show = "收款方式:";

        /** 收款方式code  */
        public static final String receive_type_code = "receiveType";

        /** 私有化其构造   */
        private Account() {

        }

    }


}
