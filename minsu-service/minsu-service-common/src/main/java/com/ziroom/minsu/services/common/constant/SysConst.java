/**
 * @FileName: PayMessageConst.java
 * @Package: com.ziroom.sms.services.cleaning.constant
 * @author sence
 * @created 7/27/2015 11:53 AM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.ziroom.minsu.services.common.constant;

/**
 * 
 * <p>系统常量</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie
 * @since 1.0
 * @version 1.0
 */
public class SysConst {



	/**
     * 竖线
     */
    public static final String sys_line = "|";
    /**
     * 下划线
     */
    public static final String sys_under_line = "_";
    
    /**
     * 支付失败消息提示
     */
    public static final String pay_fail_account= "账户空间";
    
    /**
     * 下划线
     */
    public static final String pay_fail_card = "银行卡";
	
    /**
     * 转账
     */
    public static final String account_transfers_money = "transfersAccount";
    
    /**
     * 余额解冻
     */
    public static final String account_balance_thaw = "balanceThaw";
    
    
    /**
     * 消费  冻结金
     */
    public static final String account_consume_freeze = "consumeFreeze";
    
    /**
     * 消费  冻结金 ,虚拟消费
     */
    public static final String account_virtual_consume_freeze = "consumeFreeze.-1";
    
   
    /**
     * 余额冻结消费
     */
    public static final String account_balance_to_freeze = "balanceToFreeze";
    
    /**
     * 充值
     */
    public static final String account_fill_money = "fillMoney";
    
    /**
     * 消费余额
     */
    public static final String account_consume_balance = "consumeBalance";
    
    /**
     * 系统标识
     */
    public static final String sys_source = "dz";
    
    /**
     * 系统标识
     */
    public static final String sys_source_max = "DZ";



    
    /**
     * 业务代码  账户系统使用
     */
    public static final String account_dy006 = "8";
    
    public static final String account_xf004 = "0";
    
    public static final String account_th005 = "2";
    
    public static final String account_cz001 = "1";



    /**
     * 财务系统 付款单接口  付款类型
     */
    //public static final String panyment_type_code = "yhfk";
    
    /**
     * 财务系统 付款单接口  单据类型 
     * H：收房合同，R:出房合同 D:定金
     */
    public static final String bill_type = "D";
    
    /**
     * 财务系统 付款单接口 对方自如账号 
     * 即 客户手机号  如果没有取到客户的手机号，就默认传数字“1”，不能传空值 
     */
    public static final String recieved_account = "1";
    
    
    /**
     * 财务请求参数 固定值
     */
    public static final String request_type = "I";
    
    /**
     * 支付 默认有效时间值
     */
    public static final Integer time_limit = 30;
    
    /**
     * 支付平台业务类型
     */
    public static final String bize_type = "dz_minsu";
    
    /**
     * 财务 同步收入
     */
    public static final String invoice_content = "自如客-服务费";
    /**
     * 财务 同步收入
     */
    public static final String income_type  = "主营业务收入_短租服务费";
    
    /**
     * 财务回调url
     */
    public static final String pay_vouchers_callback_url = "/finance/ee5f86/financeCallBack";
    
    
    /**
     * 订单支付 回调url
     */
    public static final String pay_order_callback_url = "/pay/ee5f86/payCallBackNormal";
    
    /**
     * 账单支付 回调url
     */
    public static final String pay_punish_callback_url = "/pay/ee5f86/payCallBackPunish";
    
    /**
     * 账户优惠券充值 
     */
    public static final String cash_fill = "cash_coupon";
    
    
    /**
     * 是否删除(0:否,1:是)
     */
 	public static final int IS_NOT_DEL = 0; 
 	
 	/**
 	 * 是否删除(0:否,1:是)
 	 */
 	public static final int IS_DEL = 1; 
 	
 	/**
 	 * 是与否(0:否,1:是)
 	 */
 	public static final int IS_NOT_TRUE = 0; 
 	
 	/**
 	 * 是与否(0:否,1:是)
 	 */
 	public static final int IS_TRUE = 1; 
 	
 	/**
 	 * ios老版本版本号
 	 */
 	public static final String IOS_OLD_VERSION = "1.0.0"; 
 	

 	/**
 	 * 安卓老版本版本号
 	 */
 	public static final int AD_OLD_VERSION = 64;
 	
 	/**
 	 * 绑定优惠券 重试次数
 	 */
 	public static final int RETRIES = 2;
 	
 	/**
 	 * 定时任务拒绝value的code值
 	 */
 	public static final String order_task_refuse_code = "3000";
 	
 	/**
 	 * 预订类型，下单成功之后的提醒文案（替换值）
 	 * 
 	 */
 	public static final String apply_order_success_show = "12个小时";
 	
 	/**
 	 * google 接口返回状态
 	 */
	public static final String google_statu_ok = "OK";
	public static final String google_statu_zero_results = "ZERO_RESULTS";
	
	/**
	 * 中国编码
	 */
	public static final String nation_code  = "100000";

	/**
	 * 手机号 国际码
	 */
	public static final String MOBILE_NATION_CODE = "86";
	
	/**
	 * 手机号 国际码标识key
	 */
	public static final String MOBILE_NATION_CODE_KEY = "mobileNationCodeKey";

    /**
     * 公共的常亮信息
     */
    public static class Common {

        /** 自如科技公司 BU代码  【同步收入时】【同步业务账】 使用  */
        public static final String BU_CODE = "0605";

        /**  自如科技公司 BU名称  同步收入时 使用 */
        public static final String BU_NAME = "短租";


        /** 【同步收入】【同步业务账】【付款单】  */
        public static final String company_code = "8013";


        /** 账户使用、支付平台使用 */
        public static final String company_ziroom_minsu = "801300";

        /** 私有化其构造 */
        private Common() {

        }
    }



    /**   收入模块  */
    public static class Payment {


        /** 同步业务账  */
        public static final String remark = "同步收款单";


        /** 私有化其构造   */
        private Payment() {

        }

    }


    /**
     * 收入模块
     */
    public static class Income {

        /** 订单状态 */
        public static final Integer order_status = 0;


        /**  代表自如科技公司  同步收入时 使用  */
        public static final String company_minsu_name = "短租未注册";

        /** 私有化其构造  */
        private Income() {

        }
    }


    /**
     * 房源模块常量
     */
    public static class House {

        /** 超时未审核自动上架时限(单位:小时)  */
        public static final int OVER_AUDIT_DAY_LIMIT = 72;
        
        /**
         * 房源默认出租截止日期
         */
        public static final  String TILL_DATE = "2037-12-01 23:59:59";

        /** 私有化其构造   */
        private House() {

        }
    }


    /**
     * 智能锁模块常量
     */
    public static class SmartLock {
        /** 获取智能锁动态密码最大次数  */
        public static final int MAX_DYNAMIC_PSWD_NUM = 2;

        /** 私有化其构造  */
        private SmartLock() {

        }
    }
    
    /**
     * 押金规则常量
     */
    public static class Deposit {
    	
    	/**
    	 * 房东设置押金最小值
    	 */
    	public static final int LANDLORDUID_DEPOSIT_RULES_CODE_MIN = 0;
    	/**
    	 * 房东设置押金最大值
    	 */
    	public static final int LANDLORDUID_DEPOSIT_RULES_CODE_MAX = 99999;

        /** 私有化其构造  */
        private Deposit() {

        }
    }



    /**
     * 私有化其构造
     */
    protected SysConst() {
    }
}
