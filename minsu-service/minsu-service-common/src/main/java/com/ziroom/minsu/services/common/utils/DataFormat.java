package com.ziroom.minsu.services.common.utils;

import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.util.BigDecimalUtil;
import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;


/**
 * <p>数据格式化处理</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年8月31日
 * @since 1.0
 * @version 1.0
 */
public class DataFormat {

	private static Logger logger = LoggerFactory.getLogger(DataFormat.class);

	private DataFormat() {}


	/**
	 * 金额除以100
	 * @author lishaochuan
	 * @create 2016年8月31日下午6:22:31
	 * @param price
	 * @return
	 */
	public static String formatHundredPrice(int price) {
		DecimalFormat format = new DecimalFormat();
		format.applyPattern("##,##0.00");
		double priceD = price / 100.00;
		return format.format(priceD);
	}


	/**
	 * 金额除以100，小数点后如有0砍掉。
	 * 12310 => 123.1
	 * @author lishaochuan
	 * @create 2017/1/4 18:18
	 * @param 
	 * @return 
	 */
	public static String formatHundredPriceNoZero(int price) {
		DecimalFormat format = new DecimalFormat();
		format.applyPattern("##,##0.##");
		double priceD = price / 100.00;
		return format.format(priceD);
	}

	/**
	 * 银行卡号保密
	 * @author lishaochuan
	 * @create 2016年8月31日下午6:25:39
	 * @param cardNo
	 * @return
	 */
	public static String formatBankCardStar(String cardNo){
		if (Check.NuNStr(cardNo)) {
			return "";
		} else {
			cardNo = cardNo.replaceAll(" ", "");
			return cardNo.replaceAll("(?<=\\d{5})\\d(?=\\d{4})", "*");
		}
	}

	/**
	 * 金额除以100
	 * @author lishaochuan
	 * @create 2016年8月31日下午6:22:31
	 * @param price
	 * @return
	 */
	public static String formatHundredPriceInt(int price) {
		DecimalFormat format = new DecimalFormat();
		format.applyPattern("##,##0");
		double priceD = price/100.00;
		return format.format(priceD);
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


	/**
	 * 
	 * 计算房源的夹心价格
	 *
	 * @author yd
	 * @created 2016年12月8日 上午10:55:14
	 *
	 * @param discount  折扣值
	 * @param price  当前日期价格
	 * @return
	 */
	public static int getPriorityPrice(String discount,int price){

		try {
			if(!Check.NuNStrStrict(discount)){
				double discountD  = Double.valueOf(discount);
				if(discountD>0&discountD<=1){
					Double priceD = BigDecimalUtil.mul(discountD, price);
					price=priceD.intValue()/100;
					return price*100;
				}
			}
		} catch (Exception e) {
			LogUtil.info(logger, "设置房源夹心价格失败discount={}", discount);
		}

		return price;
	}
	
	/**
	 * 
	 * 分 转化成元
	 *
	 * @author yd
	 * @created 2016年12月12日 下午2:13:33
	 *
	 * @param discount
	 * @param price
	 * @return
	 */
	public static int getPcHousePrice(String discount,int price){
		
		 price = getPriorityPrice(discount, price);
		 
		 Double  priceH = Math.ceil(price/100);
		
		return priceH.intValue();
	}
	
	/**
	 * 
	 * 对当前数 除以100 保存两位小数  第三位小数之后直接去掉
	 *
	 * @author yd
	 * @created 2017年4月12日 下午4:51:31
	 *
	 * @param val
	 * @return
	 */
	public static String formatIntegerVal(String val) {
		if(Check.NuNStr(val)){
			val = "0";
		}
		DecimalFormat format = new DecimalFormat();
		format.applyPattern("##,##0.##");
		double priceD = (Double.valueOf(val))/100;
		return format.format(priceD);
	}

	public static void main(String[] args) {

		System.out.println(formatHundredPriceNoZero(12090010));
	}
}
