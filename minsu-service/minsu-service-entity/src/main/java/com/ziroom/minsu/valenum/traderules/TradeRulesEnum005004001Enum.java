package com.ziroom.minsu.valenum.traderules;

import java.text.DecimalFormat;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.valenum.order.CheckOutMoneyTypeEnum;

/**
 * <p>长租 按天收取</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/3.
 * @version 1.0
 * @since 1.0
 */
public enum TradeRulesEnum005004001Enum {

	TradeRulesEnum005004001001("TradeRulesEnum005004001001","免费提前退订最小天数","入住前x天取消预订,"){
		@Override
		public void trans2Rull(CheckOutStrategy vo, Object value) {
			if(!Check.NuNObj(vo)){
				vo.setMoneyType(CheckOutMoneyTypeEnum.DAY.getCode());
				vo.setPreFreeDayCount(TradeRulesEnum005Enum.getintValue(value));
			}
		}
	},
	TradeRulesEnum005004001002("TradeRulesEnum005004001002","违约需扣房租天数",""){
		@Override
		public void trans2Rull(CheckOutStrategy vo, Object value) {
			if(!Check.NuNObj(vo)){
				vo.setMoneyType(CheckOutMoneyTypeEnum.DAY.getCode());
				vo.setPreCost(TradeRulesEnum005Enum.getintValue(value));
			}
		}

	},
	TradeRulesEnum005004001003("TradeRulesEnum005004001003","入住后提前退房需扣房租天数","提前退房，"){
		@Override
		public void trans2Rull(CheckOutStrategy vo, Object value) {
			if(!Check.NuNObj(vo)){
				vo.setMoneyType(CheckOutMoneyTypeEnum.DAY.getCode());
				vo.setSuffixCost(TradeRulesEnum005Enum.getintValue(value));
			}
		}

	},
	TradeRulesEnum005004001004("TradeRulesEnum005004001004","无责任退房扣除全部房租百分比",""){
		@Override
		public void trans2Rull(CheckOutStrategy vo, Object value) {
			if(!Check.NuNObj(vo)){
				vo.setMoneyType(CheckOutMoneyTypeEnum.DAY.getCode());
				vo.setCancelFreePercent(TradeRulesEnum005Enum.getdoubleValue(value));
			}
		}
	},
	TradeRulesEnum005004001005("TradeRulesEnum005004001005","取消订单剩余房租扣除百分比","如果未能在{1}天前取消预订，"){
		@Override
		public void trans2Rull(CheckOutStrategy vo, Object value) {
			if(!Check.NuNObj(vo)){
				vo.setMoneyType(CheckOutMoneyTypeEnum.DAY.getCode());
				vo.setCancelLastPercent(TradeRulesEnum005Enum.getdoubleValue(value));
			}
		}
	},
	TradeRulesEnum005004001006("TradeRulesEnum005004001006","退房剩余房租扣除百分比",""){
		@Override
		public void trans2Rull(CheckOutStrategy vo, Object value) {
			if(!Check.NuNObj(vo)){
				vo.setMoneyType(CheckOutMoneyTypeEnum.DAY.getCode());
				vo.setCheckOutLastPercent(TradeRulesEnum005Enum.getdoubleValue(value));
			}
		}
	},

	TradeRulesEnum005004001007("TradeRulesEnum005004001007","无责任退房需扣房租天数",""){
		@Override
		public void trans2Rull(CheckOutStrategy vo, Object value) {
			if(!Check.NuNObj(vo)){
				vo.setMoneyType(CheckOutMoneyTypeEnum.DAY.getCode());
				vo.setFreeCost(TradeRulesEnum005Enum.getintValue(value));
			}
		}
	};

	private final String value;

	private final String name;

	/**
	 * 房东端 退订政策显示用  新版
	 */
	private String checkInPreNameM;

	/**
	 * @return the checkInPreNameM
	 */
	public String getCheckInPreNameM() {
		return checkInPreNameM;
	}


	/**
	 * @param checkInPreNameM the checkInPreNameM to set
	 */
	public void setCheckInPreNameM(String checkInPreNameM) {
		this.checkInPreNameM = checkInPreNameM;
	}


	/**
	 * 将code值赋值
	 * @param vo
	 * @param value
	 */
	public void trans2Rull(CheckOutStrategy vo,Object value){
		return;
	}


	/**
	 * 获取
	 * @param value
	 * @return
	 */
	public static TradeRulesEnum005004001Enum getEmbyCode(String value) {
		for (final TradeRulesEnum005004001Enum em : TradeRulesEnum005004001Enum.values()) {
			if (em.getValue().equals(value)) {
				return em;
			}
		}
		return null;
	}


	TradeRulesEnum005004001Enum(String value, String name, String checkInPreNameM) {
		this.value = value;
		this.name = name;
		this.checkInPreNameM = checkInPreNameM;
	}

	/**
	 *
	 * M站 展示入住前的提示用户
	 *
	 * @author yd
	 * @created 2016年11月21日 上午11:03:55
	 *
	 * @return
	 */
	public void trans1ShowName(TradeRulesVo tradeRulesVo){
	}

	/**
	 *
	 * 公用信息展示
	 *
	 * @author yd
	 * @created 2016年11月21日 下午4:20:47
	 *
	 * @param tradeRulesVo
	 * @return
	 */
	public String commonShowName(TradeRulesVo tradeRulesVo){
		if(!Check.NuNObj(tradeRulesVo)){
			tradeRulesVo.setCommonShowName("退款中不包含服务费，如果房客未入住，将退还清洁费。");
		}
		return "";
	}



	public static void showContext(TradeRulesVo tradeRulesVo){
		if(Check.NuNObj(tradeRulesVo)){
			return;
		}
		StringBuilder context = new StringBuilder();
		Integer text0 = tradeRulesVo.getLongTermLimit();
		Integer text1 = tradeRulesVo.getUnregText1();
		String text2 = tradeRulesVo.getUnregText2();
		String text3 = tradeRulesVo.getUnregText3();
		String text4 = tradeRulesVo.getUnregText4();
		String text5 = tradeRulesVo.getUnregText5();
		String text6 = tradeRulesVo.getUnregText6();
		String text7 = tradeRulesVo.getUnregText7();

//		if(!Check.NuNObj(text0) && text0 != 0){
//			context.append(text0).append("晚或更长时间的订单默认使用长期住宿退订政策。");
//			context.append("\r\n");
//		}		
		
		if(!Check.NuNObj(text1) && text1 != 0){
			if(Check.NuNStrStrict(text7)){
				text7 = "0";
			}
			if(Check.NuNStrStrict(text4)){
				text4 = "0";
			}
			context.append("入住日期前" + text1 + "天取消预订，");
			if(text7.equals("0") && text4.equals("0")){
				context.append("退还房客全部房费");
			}else if(!text7.equals("0") && !text4.equals("0")){
				context.append("扣除房客前"+text7+"晚房费，退还房客"+formatTradeRulesF(text4)+"的房费;");
			}else if(!text7.equals("0") && text4.equals("0")){
				context.append("扣除房客前"+text7+"晚房费，剩余未住宿天数的房费将全部退还;");
			}else if(text7.equals("0") && !text4.equals("0")){
				context.append("退还房客"+formatTradeRulesF(text4)+"的房费;");
			}
			context.append("\r\n");
			context.append("如果未能在" + text1 + "天前取消预订，");
		}else {
			context.append("入住前取消预订，");
		}


		if(Check.NuNStrStrict(text2)){
			text2 = "0";
		}
		if(Check.NuNStrStrict(text5)){
			text5 = "0";
		}
		if(text2.equals("0") && text5.equals("0")){
			context.append("退还房客全部房费");
		}else if(!text2.equals("0") && !text5.equals("0")){
			context.append("扣除房客前"+text2+"晚房费，及剩余未住宿天数"+formatTradeRulesZ(text5)+"的房费;");
		}else if(!text2.equals("0") && text5.equals("0")){
			context.append("扣除房客前"+text2+"晚房费，剩余未住宿天数的房费将全部退还;");
		}else if(text2.equals("0") && !text5.equals("0")){
			context.append("退还房客"+formatTradeRulesF(text5)+"的房费;");
		}
		context.append("\r\n");


		if(Check.NuNStrStrict(text3)){
			text3 = "0";
		}
		if(Check.NuNStrStrict(text6)){
			text6 = "0";
		}
		context.append("如果提前退房，");
		if(text3.equals("0") && text6.equals("0")){
			context.append("退还房客全部房费;");
		}else if(!text3.equals("0") && !text6.equals("0")){
			context.append("扣除房客未住宿的前"+text3+"晚房费，及剩余未住宿天数"+formatTradeRulesZ(text6)+"的房费，如果未住宿天数少于"+text3+"晚，未住宿天数的房费将不予退还;");
		}else if(!text3.equals("0") && text6.equals("0")){
			context.append("扣除房客未住宿的前"+text3+"晚房费，如果未住宿天数少于"+text3+"晚，未住宿天数的房费将不予退还;");
		}else if(text3.equals("0") && !text6.equals("0")){
			context.append("退还房客"+formatTradeRulesF(text6)+"的房费;");
		}

//		context.append("退款中不包含服务费，如果房客未入住，将退还清洁费。");
//		context.append("\r\n");
		
		if(!Check.NuNObj(text0) && text0>0 && text0<500){
			
			if(context.toString().endsWith(";")){
				context = new StringBuilder(context.substring(0, context.length()-1));
				context.append("，");
			}
			
			context.append("最多扣除").append(text0).append("晚房费;");
		}

		tradeRulesVo.setCommonShowName(context.toString());
	}

	public String getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	/**
	 *
	 * 配置 字符小数转化成百分比
	 *
	 * @author yd
	 * @created 2016年11月18日 下午4:27:16
	 *
	 * @param price
	 * @return
	 */
	public static String formatTradeRulesZ(String price) {

		if(Check.NuNObj(price)){
			return "0%";
		}
		DecimalFormat format = new DecimalFormat();
		format.applyPattern("##,##0");
		double priceD = Double.valueOf(price) * 100;
		return format.format(priceD)+"%";
	}

	/**
	 *
	 * 配置 字符小数转化成百分比
	 *
	 * @author yd
	 * @created 2016年11月18日 下午4:27:16
	 *
	 * @param price
	 * @return
	 */
	public static String formatTradeRulesF(String price) {

		if(Check.NuNObj(price)){
			price ="0";
		}
		DecimalFormat format = new DecimalFormat();
		format.applyPattern("##,##0");
		double priceD = (1-Double.valueOf(price))* 100;
		return format.format(priceD)+"%";
	}
}
