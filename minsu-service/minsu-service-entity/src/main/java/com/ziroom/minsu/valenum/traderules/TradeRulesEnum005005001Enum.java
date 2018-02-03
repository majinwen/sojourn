package com.ziroom.minsu.valenum.traderules;

import com.asura.framework.base.util.Check;

/**
 * 
 * @author zl
 *
 */
public enum TradeRulesEnum005005001Enum {

	TradeRulesEnum005005001001("TradeRulesEnum005005001001","取消预订截止时间为房东设置的入住时间（例如：%d点），如果入住时间为%s，则入住日期前%d天取消预订即为%s%d点前;"),
	TradeRulesEnum005005001002("TradeRulesEnum005005001002","如果房客未入住，将退还清洁费;"),
	TradeRulesEnum005005001003("TradeRulesEnum005005001003","房费不足以扣除违约金时，将从押金中进行扣除;"),
	TradeRulesEnum005005001004("TradeRulesEnum005005001004","入住前取消预订，退还房客全部平台服务费，入住后提前退房，按照实际入住天数收取房客平台服务费;"),
	TradeRulesEnum005005001005("TradeRulesEnum005005001005","%d晚或更长时间的订单默认使用长期住宿退订政策。");

	private final String value;

	private final String name; 
 
	/**
	 * 获取
	 * @param value
	 * @return
	 */
	public static TradeRulesEnum005005001Enum getEmbyCode(String value) {
		for (final TradeRulesEnum005005001Enum em : TradeRulesEnum005005001Enum.values()) {
			if (em.getValue().equals(value)) {
				return em;
			}
		}
		return null;
	}


	TradeRulesEnum005005001Enum(String value, String name) {
		this.value = value;
		this.name = name;
	} 

	public String getValue() {
		return value;
	}

	public String getName() {
		return name;
	} 
	
	/**
	 * 获取退订政策默认说明
	 * @author zl
	 * @param 
	 * @return
	 */
	public static String getDefautExplain(Integer longTermDayLimit){
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(String.format(TradeRulesEnum005005001001.getName(), 14,"周五",1,"周四",14)).append("</br>");
		stringBuilder.append(TradeRulesEnum005005001004.getName()).append("</br>");
		stringBuilder.append(TradeRulesEnum005005001002.getName()).append("</br>");
		stringBuilder.append(TradeRulesEnum005005001003.getName()).append("</br>");
		if(!Check.NuNObj(longTermDayLimit) && longTermDayLimit>0 && longTermDayLimit<500){			
			stringBuilder.append(String.format(TradeRulesEnum005005001005.getName(),longTermDayLimit));
		}
		return stringBuilder.toString();
	} 
	
	
}
