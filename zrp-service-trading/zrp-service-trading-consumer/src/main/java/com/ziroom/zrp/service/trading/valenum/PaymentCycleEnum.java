package com.ziroom.zrp.service.trading.valenum;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>付款周期枚举：1 月付 3 季付 6 半年付 12 年付 9 一次性付清（短租）</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年9月27日
 * @since 1.0
 */
public enum PaymentCycleEnum {
	
	YF("1","月付","押一付一"){
		@Override
		public String getDesc(String money, String discount) {
			BigDecimal discountBig = BigDecimal.valueOf(Double.parseDouble(discount));
			int upRate = discountBig.multiply(BigDecimal.valueOf(100)).subtract(BigDecimal.valueOf(100)).setScale(0).intValue();
			int monthMoney = BigDecimal.valueOf(Double.parseDouble(money)).multiply(discountBig).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
			return String.format("￥%d/月    房租上涨%s",monthMoney,upRate+"%");
		}
	},
	JF("3","季付","押一付三"),
	BNF("6","半年付","押一付六"),
	NF("12","年付","押一付十二"),
	YCX("9","一次性付清","一次性付清");
	
	PaymentCycleEnum(String code, String name, String showName) {
		this.code = code;
		this.name = name;
		this.showName = showName;
	}
	private String code;
	private String name;
	private String showName;
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	public String getShowName() {
		return showName;
	}
	
	public static PaymentCycleEnum getPaymentCycleEnumByCode(String code){
		for(PaymentCycleEnum paymentCycleEnum : PaymentCycleEnum.values()){
			if(paymentCycleEnum.getCode().equals(code)){
				return paymentCycleEnum;
			}
		}
		return null;
	}




	/**
	 * 获取付款方式描述信息
	 * @author jixd
	 * @created 2017年10月19日 10:44:26
	 * @param
	 * @return
	 */
	public String getDesc(String money,String discount){
		if ("1".equals(discount)){
			return "";
		}
		double rate = BigDecimal.valueOf(Double.parseDouble(discount)).multiply(BigDecimal.valueOf(10)).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		return String.format("服务费%s折",rate);
	}


	public static Map<String,String> getMap(){
		Map<String,String> map = new LinkedHashMap<>();
		for (PaymentCycleEnum paymentCycleEnum : PaymentCycleEnum.values()){
			map.put(paymentCycleEnum.getCode(),paymentCycleEnum.getName());
		}
		return map;
	}


}
