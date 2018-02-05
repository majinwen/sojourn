package com.ziroom.minsu.api.house.valueenum;


import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.constant.RulesConst;
import com.ziroom.minsu.services.house.entity.CheckRuleVo;
import com.ziroom.minsu.services.house.entity.HouseConfVo;
import com.ziroom.minsu.services.house.entity.HouseDetailNewVo;
import com.ziroom.minsu.services.house.entity.TenantHouseDetailVo;
import com.ziroom.minsu.valenum.house.ClickTypeEnum;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/12/6 14:53
 * @version 1.0
 * @since 1.0
 */
public enum RulesEnum {



	//可点击
	CREATE_ORDER_NOW(10, "立即预订","下单类型","1") {
		@Override
		public String getContent() {
			return RulesConst.CREATE_ORDER_NOW;
		}
	},
	CREATE_ORDER_APPLY(11, "申请预订","下单类型","2") {
		@Override
		public String getContent() {
			return RulesConst.CREATE_ORDER_APPLY;
		}
	},
	CLEAN_FEE(20, "清洁费用","清洁费用","20") {
		@Override
		public String getContent() {
			return RulesConst.CLEAN_FEE;
		}
	},
	DEPOSIT_FEE(30, "押金", "押金","30") {
		@Override
		public String getContent() {
			return RulesConst.DEPOSIT_FEE;
		}
	},
	/*房客押金*/
	DEPOSIT_FEE_TEN(31, "退还规则", "退还规则", "31") {
		@Override
		public String getContent() {
			return RulesConst.DEPOSIT_FEE_TEN;
		}
	},
	PHOTO_CONTENT(88, "预约须知", "预约须知","88") {
		@Override
		public String getContent() {
			return RulesConst.PHOTO_CONTENT;
		}
	},
	RETURN_STRICT(40, "严格退订政策", "严格","TradeRulesEnum005001"),
	RETURN_MEDIUM(41, "适中退订政策", "适中","TradeRulesEnum005002"),
	RETURN_FLEXIBLE(42, "灵活退订政策", "灵活","TradeRulesEnum005003"),
	RETURN_LONG(43, "长期住宿退订政策", "长期住宿","TradeRulesEnum005004"),
	HOUSE_RULES(50, "房屋守则", "房屋守则","50"),


    //不可点击
	CHECK_IN_TIME(60,"","入住时间","60"),
	CHECK_OUT_TIME(61,"","离开时间","61"),
	MIN_DAY(62,"","最短入住天数","62"),
	CHANGZU_DISCOUNT(63,"","优惠","63");
	/**
	 * 
	 * 封装入住规则
	 *
	 * @author yd
	 * @created 2016年12月7日 下午2:35:49
	 *
	 * @param tenantHouseDetailVo
	 */
	public void setCheckRuleVo(TenantHouseDetailVo tenantHouseDetailVo,HouseDetailNewVo houseDetailNewVo ){



		LinkedList<CheckRuleVo> listCheckRuleVo = null;
		if(!Check.NuNObj(tenantHouseDetailVo)){
			listCheckRuleVo = new LinkedList<CheckRuleVo>();

			String checkInTime = tenantHouseDetailVo.getCheckInTime();//入住时间
			String checkOutTime = tenantHouseDetailVo.getCheckOutTime();//离开时间
			Integer minDay = tenantHouseDetailVo.getMinDay();//最短入住天数
			Integer orderType = tenantHouseDetailVo.getOrderType();//下单类型
			Integer cleaningFees = tenantHouseDetailVo.getCleaningFees();//清洁费
			String depositRulesValue = tenantHouseDetailVo.getDepositRulesValue();//押金
			String  checkOutRulesCode  = tenantHouseDetailVo.getCheckOutRulesCode();//退订政策code

			try {
 				if(!Check.NuNStrStrict(checkInTime)){
					CheckRuleVo checkRuleVo = new CheckRuleVo();
					checkRuleVo.setClickType(ClickTypeEnum.NOT_CLICK.getCode());
					checkRuleVo.setName(CHECK_IN_TIME.getHouseDetailShowName());
					Date time24 = DateUtil.parseDate(checkInTime, "HH:mm");

					checkRuleVo.setType(String.valueOf(CHECK_IN_TIME.getCode()));
					checkRuleVo.setVal(DateUtil.dateFormat(time24, "HH:mm"));

					listCheckRuleVo.add(checkRuleVo);
				}


				if(!Check.NuNStrStrict(checkOutTime)){
					CheckRuleVo checkRuleVo = new CheckRuleVo();
					checkRuleVo.setClickType(ClickTypeEnum.NOT_CLICK.getCode());
					checkRuleVo.setName(CHECK_OUT_TIME.getHouseDetailShowName());
					Date time24 = DateUtil.parseDate(checkOutTime, "HH:mm");

					checkRuleVo.setType(String.valueOf(CHECK_OUT_TIME.getCode()));
					checkRuleVo.setVal(DateUtil.dateFormat(time24, "HH:mm"));

					listCheckRuleVo.add(checkRuleVo);
				}
			} catch (Exception e) {
				LogUtil.error(logger, "房源详情,时间格式错误");
			}


			//最短天数
			if(minDay == null) minDay = 1;

			CheckRuleVo checkRuleVo = new CheckRuleVo();
			checkRuleVo.setClickType(ClickTypeEnum.NOT_CLICK.getCode());
			checkRuleVo.setName(MIN_DAY.getHouseDetailShowName());
			checkRuleVo.setType(String.valueOf(MIN_DAY.getCode()));
			checkRuleVo.setVal(String.valueOf(minDay)+"天");
			listCheckRuleVo.add(checkRuleVo);

			//下单类型
			if(!Check.NuNObj(orderType)){
				RulesEnum rulesEnum  = getEnumByType(String.valueOf(orderType));
				if(!Check.NuNObj(rulesEnum)){
					checkRuleVo = new CheckRuleVo();
					checkRuleVo.setClickType(ClickTypeEnum.CLICK.getCode());
					checkRuleVo.setName(rulesEnum.getHouseDetailShowName());
					checkRuleVo.setType(String.valueOf(rulesEnum.getCode()));
					checkRuleVo.setVal(rulesEnum.getTitle());
					listCheckRuleVo.add(checkRuleVo);
				}
			}


			//清洁费
			if(Check.NuNObj(cleaningFees)) cleaningFees = 0;
			checkRuleVo = new CheckRuleVo();
			checkRuleVo.setClickType(ClickTypeEnum.CLICK.getCode());
			checkRuleVo.setName(CLEAN_FEE.getHouseDetailShowName());
			checkRuleVo.setType(String.valueOf(CLEAN_FEE.getCode()));
			checkRuleVo.setVal("￥"+String.valueOf(cleaningFees/100));
			listCheckRuleVo.add(checkRuleVo);

			//押金
			if(Check.NuNStrStrict(depositRulesValue)) depositRulesValue = "0";
			checkRuleVo = new CheckRuleVo();
			checkRuleVo.setClickType(ClickTypeEnum.CLICK.getCode());
			checkRuleVo.setName(DEPOSIT_FEE.getHouseDetailShowName());
			checkRuleVo.setType(String.valueOf(DEPOSIT_FEE.getCode()));
			checkRuleVo.setVal("￥"+depositRulesValue);
			listCheckRuleVo.add(checkRuleVo);

			//房间使用守则
			
			if(!Check.NuNStr(tenantHouseDetailVo.getHouseRules())){
				checkRuleVo = new CheckRuleVo();
				checkRuleVo.setClickType(ClickTypeEnum.CLICK.getCode());
				checkRuleVo.setName(HOUSE_RULES.getHouseDetailShowName());
				checkRuleVo.setType(String.valueOf(HOUSE_RULES.getCode()));
				checkRuleVo.setVal("查看");
				listCheckRuleVo.add(checkRuleVo);
			}			
			
			//长租参数设置

			if(Check.NuNObj(houseDetailNewVo)){
				houseDetailNewVo = new HouseDetailNewVo();
			}
			houseDetailNewVo.setLongTermName(RulesEnum.RETURN_LONG.getHouseDetailShowName());
			houseDetailNewVo.setLongTermType(String.valueOf(RulesEnum.RETURN_LONG.getCode()));
			houseDetailNewVo.setCheckOutRulesTitle("退订政策");

			//退订政策
			if(!Check.NuNStrStrict(checkOutRulesCode)){
				RulesEnum rulesEnum  = getEnumByType(checkOutRulesCode);
				if(!Check.NuNObj(rulesEnum)){
					checkRuleVo = new CheckRuleVo();
					checkRuleVo.setClickType(ClickTypeEnum.CLICK.getCode());
					checkRuleVo.setName("退订政策");
					checkRuleVo.setType(String.valueOf(rulesEnum.getCode()));
					checkRuleVo.setVal(rulesEnum.getHouseDetailShowName());
					listCheckRuleVo.add(checkRuleVo);
					houseDetailNewVo.setCurRulesType(String.valueOf(rulesEnum.getCode()));
				}
			}

			//优惠折扣   折扣天数 
			List<HouseConfVo> listHouseDiscount = tenantHouseDetailVo.getListHouseDiscount();
			if(!Check.NuNObj(listHouseDiscount)){
				StringBuffer discount = new StringBuffer();
				List<String> priceList = new LinkedList<String>();
				for (HouseConfVo houseConfVo : listHouseDiscount) {
					if(!Check.NuNStrStrict(houseConfVo.getDicDayNum())){
						String price = formatTradeRulesZ(houseConfVo.getDicValue());
						if(!Check.NuNStrStrict(price)){
							priceList.add(houseConfVo.getDicDayNum()+"天以上"+price);
						}
					}
				}
				if(!Check.NuNCollection(priceList)){
					int i = 0;
					for (String string : priceList) {
						discount.append(string);
						i++;
						if(i<priceList.size()){
							discount.append(",");
						}
					}
					checkRuleVo = new CheckRuleVo();
					checkRuleVo.setClickType(ClickTypeEnum.NOT_CLICK.getCode());
					checkRuleVo.setName(CHANGZU_DISCOUNT.getHouseDetailShowName());
					checkRuleVo.setType(String.valueOf(CHANGZU_DISCOUNT.getCode()));
					checkRuleVo.setVal(discount.toString());
					listCheckRuleVo.add(checkRuleVo);
				}
			}
			
			
			
			tenantHouseDetailVo.setListCheckRuleVo(listCheckRuleVo);
		}
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
			return "";
		}
		try {
			int disCount = Integer.valueOf(price);
			if(disCount <100&&disCount>=0){
				double priceD = Double.valueOf(price) / 10;
				return priceD+"折";
			}
		} catch (Exception e) {
			LogUtil.info(logger, "房源折扣错误price={}", price);
		}

		return "";

	}

	RulesEnum(int code, String title,String houseDetailShowName,String type) {
		this.code = code;
		this.title = title;
		this.houseDetailShowName = houseDetailShowName;
		this.type = type;
	}

	private static Logger logger = LoggerFactory.getLogger(RulesEnum.class);

	private int code;

	private String title;

	private String houseDetailShowName;

	/**
	 * 枚举类型
	 */
	private String type;

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the houseDetailShowName
	 */
	public String getHouseDetailShowName() {
		return houseDetailShowName;
	}

	/**
	 * @param houseDetailShowName the houseDetailShowName to set
	 */
	public void setHouseDetailShowName(String houseDetailShowName) {
		this.houseDetailShowName = houseDetailShowName;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return "";
	}

	public static RulesEnum getEnumByCode(int code) {
		for (final RulesEnum rulesEnum : RulesEnum.values()) {
			if (rulesEnum.getCode() == code) {
				return rulesEnum;
			}
		}
		return null;
	}

	/**
	 * 
	 * 此处类型也需要唯一
	 *
	 * @author yd
	 * @created 2016年12月7日 下午3:45:59
	 *
	 * @param type
	 * @return
	 */
	public static RulesEnum getEnumByType(String type) {
		for (final RulesEnum rulesEnum : RulesEnum.values()) {
			if (rulesEnum.getType().equals(type)) {
				return rulesEnum;
			}
		}
		return null;
	}
}
