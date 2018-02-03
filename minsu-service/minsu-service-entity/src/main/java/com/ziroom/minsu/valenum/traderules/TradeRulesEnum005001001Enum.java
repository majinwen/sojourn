package com.ziroom.minsu.valenum.traderules;

import java.text.DecimalFormat;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.valenum.order.CheckOutMoneyTypeEnum;

/**
 * <p>严格 按天收取</p>
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
public enum TradeRulesEnum005001001Enum {


	//获取取消订单前X日的退订政策描述
	TradeRulesEnum005001001001("TradeRulesEnum005001001001","免费提前退订最小天数","入住日期前x天取消预订，","提前x天取消订单，退还房客全部房费;"){
		@Override
		public void trans2Rull(CheckOutStrategy vo, Object value) {
			if(!Check.NuNObj(vo)){
				vo.setMoneyType(CheckOutMoneyTypeEnum.DAY.getCode());
				vo.setPreFreeDayCount(TradeRulesEnum005Enum.getintValue(value));
			}
		}

		@Override
		public String trans1ShowName(TradeRulesVo tradeRulesVo){

			if(!Check.NuNObj(tradeRulesVo)){

				String name = TradeRulesEnum005001001001.getCheckInPreNameM();
				String text4 = tradeRulesVo.getUnregText4();
				name = name.replace("x",String.valueOf(tradeRulesVo.getUnregText1()));
				
				if(Check.NuNStrStrict(text4)){
					text4 = "0";
				}
				if(text4.equals("0")){
					name = name +"退还房客全部房费;"; 
				}else if(text4.equals("1")){
					name = name +"未住宿天数的房费将不予退还;"; 
				}else{
					name = name +"退还房客"+formatTradeRulesF(text4)+"的房费;"; 
				}
				
				tradeRulesVo.setCheckInPreNameM(name);
			}

			return "";
		}
		
		@Override
		public String trans1ShowNameOld(TradeRulesVo tradeRulesVo){

			if(!Check.NuNObj(tradeRulesVo)){

				String name = TradeRulesEnum005001001001.getCheckInPreNameOldM();
				name = name.replace("x",String.valueOf(tradeRulesVo.getUnregText1()));
				tradeRulesVo.setCheckInPreNameM(name);
			}

			return "";
		}
	},
	//入住X日到入住前  退订政策 展示
	TradeRulesEnum005001001002("TradeRulesEnum005001001002","违约需扣房租天数","","入住前x天内取消订单，扣除房客y日房费;"){
		@Override
		public void trans2Rull(CheckOutStrategy vo, Object value) {
			if(!Check.NuNObj(vo)){
				vo.setMoneyType(CheckOutMoneyTypeEnum.DAY.getCode());
				vo.setPreCost(TradeRulesEnum005Enum.getintValue(value));
			}
		}
		
		@Override
		public String trans1ShowNameOld(TradeRulesVo tradeRulesVo){

			if(!Check.NuNObj(tradeRulesVo)){

				String name = TradeRulesEnum005001001002.getCheckInPreNameOldM();
				name = name.replace("x",String.valueOf(tradeRulesVo.getUnregText1())).replace("y", tradeRulesVo.getUnregText2());
				tradeRulesVo.setCheckInOnNameM(name);
			}

			return "";
		}
	},

	//提前退房  退订政策M站展示
	TradeRulesEnum005001001003("TradeRulesEnum005001001003","入住后提前退房需扣房租天数","如果提前退房，","入住后提前退房，扣除房客y日房费;"){
		@Override
		public void trans2Rull(CheckOutStrategy vo, Object value) {
			if(!Check.NuNObj(vo)){
				vo.setMoneyType(CheckOutMoneyTypeEnum.DAY.getCode());
				vo.setSuffixCost(TradeRulesEnum005Enum.getintValue(value));
			}
		}

		@Override
		public String trans1ShowName(TradeRulesVo tradeRulesVo){

			if(!Check.NuNObj(tradeRulesVo)){

				String name = TradeRulesEnum005001001003.getCheckInPreNameM();
				
				String text3 = tradeRulesVo.getUnregText3();
				String text6 = tradeRulesVo.getUnregText6();
				if(Check.NuNStrStrict(text3)){
					text3 = "0";
				}
				if(Check.NuNStrStrict(text6)){
					text6 = "0";
				}
				if(!text3.equals("0")){
					if(text3.equals("1")){
						name = name+"扣除房客未住宿的首晚房费，";
					}else{
						name = name+"扣除房客未住宿的前"+text3+"晚房费，";
					}
				}
				
				if(text6.equals("0")){
					if(!text3.equals("0")){
						name = name+"剩余";
					}
					name = name+"未住宿天数的房费将全部退还;";
				}else if(text6.equals("1")){
					if(!text3.equals("0")){
						name = name+"剩余";
					}
					name = name+"未住宿天数的房费将不予退还;";
				}else{
					if(!text3.equals("0")){
						name = name+"及剩余";
					}else{
						name = name+"扣除";
					}
					name = name+"未住宿天数"+formatTradeRulesZ(text6)+"的房费;";
				}
				tradeRulesVo.setCheckOutEarlyNameM(name);
			}

			return "";
		}
		
		@Override
		public String trans1ShowNameOld(TradeRulesVo tradeRulesVo){

			if(!Check.NuNObj(tradeRulesVo)){

				String name = TradeRulesEnum005001001003.getCheckInPreNameOldM();
				name = name.replace("y", tradeRulesVo.getUnregText3());
				tradeRulesVo.setCheckOutEarlyNameM(name);
			}

			return "";
		}
	},
	TradeRulesEnum005001001004("TradeRulesEnum005001001004","无责任退房扣除全部房租百分比","",""){
		@Override
		public void trans2Rull(CheckOutStrategy vo, Object value) {
			if(!Check.NuNObj(vo)){
				vo.setMoneyType(CheckOutMoneyTypeEnum.DAY.getCode());

				vo.setCancelFreePercent(TradeRulesEnum005Enum.getdoubleValue(value));
			}
		}
	},

	//取消订单 （入住前 到 入住天数X天之前 描述）
	TradeRulesEnum005001001005("TradeRulesEnum005001001005","取消订单剩余房租扣除百分比","如果未能在{1}天前取消预订，",""){
		@Override
		public void trans2Rull(CheckOutStrategy vo, Object value) {
			if(!Check.NuNObj(vo)){
				vo.setMoneyType(CheckOutMoneyTypeEnum.DAY.getCode());
				vo.setCancelLastPercent(TradeRulesEnum005Enum.getdoubleValue(value));
			}
		}

		@Override
		public String trans1ShowName(TradeRulesVo tradeRulesVo){

			if(!Check.NuNObj(tradeRulesVo)){
				String name = TradeRulesEnum005001001005.getCheckInPreNameM();
				name =  name.replace("{1}",String.valueOf(tradeRulesVo.getUnregText1()));
				String text2 = tradeRulesVo.getUnregText2();
				String text5 = tradeRulesVo.getUnregText5();
				if(Check.NuNStrStrict(text2)){
					text2 = "0";
				}
				if(Check.NuNStrStrict(text5)){
					text5 = "0";
				}
				if(!text2.equals("0")){
					if(text2.equals("1")){
						name = name+"扣除房客首晚房费，";
					}else{
						name = name+"扣除房客前"+text2+"晚房费，";
					}
				
				}
				
				if(text5.equals("0")){
					if(!text2.equals("0")){
						name = name+"剩余";
					}
					name = name+"未住宿天数的房费将全部退还;";
				}else if(text5.equals("1")){
					if(!text2.equals("0")){
						name = name+"剩余";
					}
					name = name+"未住宿天数的房费将不予退还;";
				}else{
					if(!text2.equals("0")){
						name = name+"及剩余";
					}else{
						name = name+"扣除";
					}
					name = name+"未住宿天数"+formatTradeRulesZ(text5)+"的房费;";
				}
				
				tradeRulesVo.setCheckInOnNameM(name);
			}

			return "";
		}
	},

	TradeRulesEnum005001001006("TradeRulesEnum005001001006","退房剩余房租扣除百分比","",""){
		@Override
		public void trans2Rull(CheckOutStrategy vo, Object value) {
			if(!Check.NuNObj(vo)){
				vo.setMoneyType(CheckOutMoneyTypeEnum.DAY.getCode());
				vo.setCheckOutLastPercent(TradeRulesEnum005Enum.getdoubleValue(value));
			}
		}
	},
	TradeRulesEnum005001001007("TradeRulesEnum005001001007","无责任退房需扣房租天数","",""){
		@Override
		public void trans2Rull(CheckOutStrategy vo, Object value) {
			if(!Check.NuNObj(vo)){
				vo.setMoneyType(CheckOutMoneyTypeEnum.DAY.getCode());
				vo.setFreeCost(TradeRulesEnum005Enum.getintValue(value));
			}
		}
	}
	;



	private final String value;

	private final String name;

	/**
	 * 房东端 退订政策显示用  新版
	 */
	private String checkInPreNameM;
	
	/**
	 * 房东端 退订政策显示用  旧版
	 */
	private String checkInPreNameOldM;
	

	/**
	 * @return the checkInPreNameOldM
	 */
	public String getCheckInPreNameOldM() {
		return checkInPreNameOldM;
	}


	/**
	 * @param checkInPreNameOldM the checkInPreNameOldM to set
	 */
	public void setCheckInPreNameOldM(String checkInPreNameOldM) {
		this.checkInPreNameOldM = checkInPreNameOldM;
	}


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


	TradeRulesEnum005001001Enum(String value, String name,String checkInPreNameM,String checkInPreNameOldM) {
		this.value = value;
		this.name = name;
		this.checkInPreNameM = checkInPreNameM;
		this.checkInPreNameOldM = checkInPreNameOldM;
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
	 * 
	 * M站 展示入住前的提示用户
	 *
	 * @author yd
	 * @created 2016年11月21日 上午11:03:55
	 *
	 * @return
	 */
	public String trans1ShowName(TradeRulesVo tradeRulesVo){
		return "";
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
	public String trans1ShowNameOld(TradeRulesVo tradeRulesVo){
		return "";
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
//			tradeRulesVo.setCommonShowName("退款中不包含平台服务费，如果房客未入住，将退还清洁费。");
		}
		return "";
	}

	/**
	 * 获取
	 * @param value
	 * @return
	 */
	public static TradeRulesEnum005001001Enum getEmbyCode(String value) {
		for (final TradeRulesEnum005001001Enum em : TradeRulesEnum005001001Enum.values()) {
			if (em.getValue().equals(value)) {
				return em;
			}
		}
		return null;
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
