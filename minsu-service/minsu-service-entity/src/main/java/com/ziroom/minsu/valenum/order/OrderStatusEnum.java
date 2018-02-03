package com.ziroom.minsu.valenum.order;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.entity.order.OrderEntity;

/**
 * <p>订单状态的枚举</p>
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
public enum OrderStatusEnum {


    WAITING_CONFIRM(10,"待确认",1,"待确认"){
        @Override
        public OrderStatusEnum getCancleStatus(OrderEntity order) {
            return CANCLE_APPLY;
        }
    },

    WAITING_CHECK_IN(20,"待入住",3,"待入住"){
        @Override
        public String getShowName(OrderEntity order) {
            if(order.getPayStatus() == OrderPayStatusEnum.UN_PAY.getPayStatus()){
                return "待支付";
            }else {
                return getShowName();
            }
        }
        @Override
        public Integer getShowStatus(OrderEntity order) {
            if(order.getPayStatus() == OrderPayStatusEnum.UN_PAY.getPayStatus()){
                return 2;
            }else {
                return getShowStatus();
            }
        }
        @Override
        public OrderStatusEnum getCancleStatus(OrderEntity order) {
        	 if(Check.NuNObj(order)
        			 ||order.getPayStatus() == OrderPayStatusEnum.UN_PAY.getPayStatus()){
        		 return null;
        	 }
            return CANCLE_TENANT;
        }
    },

    CANCLE_FORCE(30,"强制取消",4,"已取消"){
        @Override
        public boolean isCancel() {
            return true;
        }
    },

    REFUSED(31,"房东已拒绝",5,"已拒绝"){

    },
    
    CANCLE_TENANT(32,"房客取消",4,"已取消"){
    	 @Override
         public String getShowName(OrderEntity order) {
             if(order.getAccountsStatus() == OrderAccountsStatusEnum.ING.getAccountsStatus()){
                 return "结算中";
             }else {
                 return getShowName();
             }
         }
         @Override
         public Integer getShowStatus(OrderEntity order) {
             if(order.getPayStatus() == OrderAccountsStatusEnum.ING.getAccountsStatus()){
                 return 10;
             }else {
                 return getShowStatus();
             }
         }
         
         @Override
         public int getComm(int plan, int real,OrderEntity order) {
        	 if(order.getPayStatus() == OrderPayStatusEnum.UN_PAY.getPayStatus()){
        		 return plan;
        	 }
             return real;
         }

        @Override
        public boolean isCancel() {
            return true;
        }

        @Override
        public String getIncomeName(OrderEntity order) {
            if(order.getAccountsStatus() == OrderAccountsStatusEnum.FINISH.getAccountsStatus()){
                return "实际收入";
            }else {
                return "预计收入";
            }
        }
    },

    CANCLE_TIME(33,"未支付超时取消",6,"已失效"){
        @Override
        public boolean isCancel() {
            return true;
        }
    },
    
    FORCED_CANCELLATION(34,"房东强制取消申请中",12,"房东强制取消申请中"),
    
    REFUSED_OVERTIME(35,"房东未确认超时取消",5,"已拒绝"){
        @Override
        public boolean isCancel() {
            return true;
        }
    },

    // 房客取消申请预定
    CANCLE_APPLY(36,"未确认取消",4,"已取消"){
        @Override
        public boolean isCancel() {
            return true;
        }
    },

    CANCEL_NEGOTIATE(37,"客服协商取消",4,"协商取消"){
        @Override
        public String getShowName(OrderEntity order) {
            if(order.getAccountsStatus() == OrderAccountsStatusEnum.ING.getAccountsStatus()){
                return "结算中";
            }else {
                return getShowName();
            }
        }

        @Override
        public boolean isCancel() {
            return true;
        }

        @Override
        public int getComm(int plan, int real,OrderEntity order) {
            return real;
        }

        @Override
        public String getIncomeName(OrderEntity order) {
            if(order.getAccountsStatus() == OrderAccountsStatusEnum.FINISH.getAccountsStatus()){
                return "实际收入";
            }else {
                return "预计收入";
            }
        }
    },

    CANCEL_LAN_APPLY(38,"房东申请取消",4,"房东取消"){
        @Override
        public String getShowName(OrderEntity order) {
            if(order.getAccountsStatus() == OrderAccountsStatusEnum.ING.getAccountsStatus()){
                return "结算中";
            }else {
                return getShowName();
            }
        }
        
        @Override
        public String getIncomeName(OrderEntity order) {
            if(order.getAccountsStatus() == OrderAccountsStatusEnum.FINISH.getAccountsStatus()){
                return "实际收入";
            }else {
                return "预计收入";
            }
        }
    },

    CHECKED_IN(40,"已入住未生成单据",7,"已入住"){
    },

    CHECKED_IN_BILL(41,"已入住已生成单据",7,"已入住"){
    },

    CHECKING_OUT(50,"正常退房中",8,"待房东确认其他消费"){


        @Override
        public OrderStatusEnum getOtherMoneyStatus(int money) {
            if(money > 0){
                return WAITING_EXT;
            }else {
                return FINISH_COMMON;
            }

        }
        @Override
        public int getComm(int plan, int real,OrderEntity order) {
            return real;
        }
    },

    CHECKING_OUT_PRE(51,"提前退房中",8,"待房东确认其他消费"){


        @Override
        public OrderStatusEnum getOtherMoneyStatus(int money) {
            if(money > 0){
                return WAITING_EXT_PRE;
            }else {
                return FINISH_PRE;
            }
        }
        @Override
        public int getComm(int plan, int real,OrderEntity order) {
            return real;
        }
    },
    
    WAITING_EXT(60,"待用户确认额外消费",9,"待用户确认额外消费"){

        @Override
        public OrderStatusEnum getConfirmOtherMoneyStatus() {
            return FINISH_COMMON;
        }
        @Override
        public int getComm(int plan, int real,OrderEntity order) {
            return real;
        }
    },

   
    WAITING_EXT_PRE(61,"提前退房待用户确认额外消费",9,"待用户确认额外消费"){

        @Override
        public OrderStatusEnum getConfirmOtherMoneyStatus() {
            return FINISH_PRE;
        }
        @Override
        public int getComm(int plan, int real,OrderEntity order) {
            return real;
        }
    },

    FINISH_COMMON(70,"正常退房完成",11,"已完成"){
        @Override
        public String getShowName(OrderEntity order) {
            if(order.getAccountsStatus() == OrderAccountsStatusEnum.ING.getAccountsStatus()){
                return "结算中";
            }else {
                return getShowName();
            }
        }
        @Override
        public Integer getShowStatus(OrderEntity order) {
        	//房客端退款中 房东端结算中
            if(order.getAccountsStatus() == OrderAccountsStatusEnum.ING.getAccountsStatus()){
                return 10;
            }else {
                return getShowStatus();
            }
        }
        @Override
        public int getComm(int plan, int real,OrderEntity order) {
            return real;
        }

        @Override
        public String getIncomeName(OrderEntity order) {
            if(order.getAccountsStatus() == OrderAccountsStatusEnum.FINISH.getAccountsStatus()){
                return "实际收入";
            }else {
                return "预计收入";
            }
        }

    },

    FINISH_PRE(71,"提前退房完成",11,"已完成"){
        @Override
        public String getShowName(OrderEntity order) {
            if(order.getAccountsStatus() == OrderAccountsStatusEnum.ING.getAccountsStatus()){
                return "结算中";
            }else {
                return getShowName();
            }
        }
        @Override
        public Integer getShowStatus(OrderEntity order) {
            if(order.getAccountsStatus() == OrderAccountsStatusEnum.ING.getAccountsStatus()){
                return 10;
            }else {
                return getShowStatus();
            }
        }
        @Override
        public int getComm(int plan, int real,OrderEntity order) {
            return real;
        }

        @Override
        public String getIncomeName(OrderEntity order) {
            if(order.getAccountsStatus() == OrderAccountsStatusEnum.FINISH.getAccountsStatus()){
                return "实际收入";
            }else {
                return "预计收入";
            }
        }
    },

    FINISH_NEGOTIATE(72,"客服协商退房",11,"协商取消"){
        @Override
        public String getShowName(OrderEntity order) {
            if(order.getAccountsStatus() == OrderAccountsStatusEnum.ING.getAccountsStatus()){
                return "结算中";
            }else {
                return getShowName();
            }
        }
        @Override
        public Integer getShowStatus(OrderEntity order) {
            if(order.getAccountsStatus() == OrderAccountsStatusEnum.ING.getAccountsStatus()){
                return 10;
            }else {
                return getShowStatus();
            }
        }

        @Override
        public int getComm(int plan, int real,OrderEntity order) {
            return real;
        }

        @Override
        public String getIncomeName(OrderEntity order) {
            if(order.getAccountsStatus() == OrderAccountsStatusEnum.FINISH.getAccountsStatus()){
                return "实际收入";
            }else {
                return "预计收入";
            }
        }
    },
    FINISH_LAN_APPLY(73,"房东申请退房",11,"协商取消"){
        @Override
        public String getShowName(OrderEntity order) {
            if(order.getAccountsStatus() == OrderAccountsStatusEnum.ING.getAccountsStatus()){
                return "结算中";
            }else {
                return getShowName();
            }
        }
      @Override
        public Integer getShowStatus(OrderEntity order) {
            if(order.getAccountsStatus() == OrderAccountsStatusEnum.ING.getAccountsStatus()){
                return 10;
            }else {
                return getShowStatus();
            }
        }

        @Override
        public int getComm(int plan, int real,OrderEntity order) {
            return real;
        }

        @Override
        public String getIncomeName(OrderEntity order) {
            if(order.getAccountsStatus() == OrderAccountsStatusEnum.FINISH.getAccountsStatus()){
                return "实际收入";
            }else {
                return "预计收入";
            }
        }
    }
    ;


    OrderStatusEnum(int orderStatus, String statusName,int showStatus,String showName) {
        this.orderStatus = orderStatus;
        this.statusName = statusName;
        this.showStatus = showStatus;
        this.showName = showName;
    }



    /**
     * 获取
     * @param orderStatus
     * @return
     */
    public static OrderStatusEnum getOrderStatusByCode(Integer orderStatus) {
        if (Check.NuNObj(orderStatus)){
            return null;
        }
        for (final OrderStatusEnum status : OrderStatusEnum.values()) {
            if (status.getOrderStatus() == orderStatus) {
                return status;
            }
        }
        return null;
    }


    public Integer getShowStatus(OrderEntity order) {
        if(Check.NuNObj(order)){
            return null;
        }
        return getShowStatus();
    }
    /**
     * 获取
     * @param order
     * @return
     */
    public String getShowName(OrderEntity order) {
        if(Check.NuNObj(order)){
            return null;
        }
        return getShowName();
    }

    /**
     * 获取当前订单状态取消之后的状态
     * @author afi
     * @return
     */
    public  OrderStatusEnum getCancleStatus(OrderEntity order){
        return null;
    }


    /**
     * 获取用户确认额外消费的之后状态
     * @author afi
     * @return
     */
    public  OrderStatusEnum getConfirmOtherMoneyStatus(){
        return null;
    }

    /**
     * 获取房东确认额外消费的之后状态
     * @author afi
     * @return
     */
    public  OrderStatusEnum getOtherMoneyStatus(int money){
        return null;
    }

    /** 订单状态 */
    private int orderStatus;

    /** 状态名称 */
    private String statusName;

    /** 前端显示状态 */
    private int showStatus;

    /** 前端显示名称 */
    private String showName;

    public int getOrderStatus() {
        return orderStatus;
    }

    public String getStatusName() {
        return statusName;
    }

    public int getShowStatus() {
        return showStatus;
    }

    public String getShowName() {
        return showName;
    }

    
    public static void main(String[] args) {
    	OrderEntity order = new OrderEntity();
    	order.setOrderStatus(OrderStatusEnum.WAITING_CONFIRM.getOrderStatus());
    	System.out.println(OrderStatusEnum.WAITING_CONFIRM.getShowStatus(order));
	}

    /**
     * 获取当前状态的佣金
     * @author afi
     * @param plan
     * @param real
     * @return
     */
    public int getComm(int plan,int real,OrderEntity order){
        return plan;
    }

    /**
     * 获取当前的收入类型
     * @author afi
     * @return
     */
    public String getIncomeName(OrderEntity order){
        return "预计收入";
    }
    
    
    /**
     * 是否可以进行订单状态结算
     * @author lishaochuan
     * @create 2016年8月31日下午5:12:10
     * @param orderStatus
     * @return
     */
    public static boolean canCheckAccount(Integer orderStatus){
    	if(orderStatus == null){
    		return false;
    	}
		if (orderStatus == OrderStatusEnum.CANCLE_TENANT.getOrderStatus() 
				|| orderStatus == OrderStatusEnum.FINISH_COMMON.getOrderStatus()
				|| orderStatus == OrderStatusEnum.FINISH_PRE.getOrderStatus()
                || orderStatus == OrderStatusEnum.FINISH_NEGOTIATE.getOrderStatus()
                || orderStatus == OrderStatusEnum.CANCEL_NEGOTIATE.getOrderStatus()
                || orderStatus == OrderStatusEnum.CANCEL_LAN_APPLY.getOrderStatus()
                || orderStatus == OrderStatusEnum.FINISH_LAN_APPLY.getOrderStatus()) {
			return true;
		}
		return false;
    }



    /**
     * 校验当前状态是不是取消状态
     * 默认不是
     * @return
     */
    public boolean isCancel(){
        return false;
    }
}
