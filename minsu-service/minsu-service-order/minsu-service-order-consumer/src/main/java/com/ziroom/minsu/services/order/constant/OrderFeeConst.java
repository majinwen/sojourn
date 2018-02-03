package com.ziroom.minsu.services.order.constant;

/**
 * <p>支付金额文本常量</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年8月19日
 * @since 1.0
 * @version 1.0
 */
public enum OrderFeeConst {

	FEE_UNIT ("￥",0,0),

	NEED_PAY_COST("房费", 0, 1) {
		public String getTitle1() {
			return "房租费用（{1}晚）";
		}

		public String getDescForTenant() {
			return "您选择入住天数的房租费用";
		}
	},
    NEED_PAY_DEPOSIT("押金", 0, 2) {
        public String getDescForTenant() {
            return "正常退房后押金将退回";
        }
    },
    NEED_PAY_USER_COMMISSION ("平台服务费",0,3){
		public String getTitle1(){
			return "您的预订天数已达到{1}天，享受服务费优惠{2}元";
		}

		public String getDescForTenant() {
			return "服务费可以帮助我们维持平台的运行，同时为您的旅程提供支持与保障";
		}

	},
	NEED_PAY_CLEAN("清洁费", 0, 4) {
		public String getDescForTenant() {
			return "由房东收取的清洁费，可为您提供更清洁的入住环境";
		}
	},
	NEED_PAY_DISCOUNT("房租优惠金额", 0, 5) {
		public String getTitle1() {
			return "长租优惠";
		}

		public String getDescForTenant() {
			return "您预定的天数超过{1}天，可享受{2}优惠";
		}

		public String getSymbol() {
			return "-";
		}
	},
	NEED_PAY_COUPON ("优惠券金额",1,6){
		public String getSymbol(){
			return "-";
		}
	},

	
	ORDER_DETAIL_PRE_REFUND ("预计退款",0,1),
	ORDER_DETAIL_REFUND ("退款",0,2),
	ORDER_DETAIL_DEPOSIT ("押金",0,3),
	ORDER_DETAIL_RENTAL("房租", 0, 4) {
		public String getTitle1() {
			return "房租费用（{1}晚）";
		}
	},
    ORDER_DETAIL_USER_COMMISSION("平台服务费", 0, 5),
    ORDER_DETAIL_DISCOUNT ("房租优惠金额",0,6),
	ORDER_DETAIL_PENALTY ("违约金",0,7),
	ORDER_DETAIL_CLEAN ("清洁费",0,8),
	ORDER_DETAIL_COUPON ("优惠券金额",1,9),
	ORDER_DETAIL_OTHER ("其他消费",0,10),
	ORDER_DETAIL_FINE("罚款", 0, 11) {
		public String getSymbol() {
			return "-";
		}
	},
	NEED_PAY_LAN_COMMISSION ("平台服务费",0,11){
		public String getTitle1(){
			return "您的预订天数已达到{1}天，享受服务费优惠{2}元";
		}
		public String getSymbol(){
			return "-";
		}
	},
	NEED_PAY_DISCOUNT_LAN ("房租优惠金额",0,12){
		public String getSymbol(){
			return "-";
		}
	},
	FIRST_ORDER_REDUC("首单立减金额",1,13){
		public String getSymbol(){
			return "-";
		}
	};
	
	private String showName;
	private int colorType;
	private int index;

	OrderFeeConst(String showName, Integer colorType, Integer index) {
		this.showName = showName;
		this.colorType = colorType;
		this.index = index;
	}

	public String getShowName() {
		return showName;
	}


	public int getColorType() {
		return colorType;
	}

	public int getIndex() {
		return index;
	}


	/**
	 * 金额加减符号
	 * @return
     */
	public String getSymbol(){
		return "";
	}

	/**
	 * 小标题
	 * @return
     */
	public String getTitle1(){
		return "";
	}

	/**
	 * 房客金额描述信息
	 *
	 * @return
	 */
	public String getDescForTenant() {
		return "";
	}
	
}
