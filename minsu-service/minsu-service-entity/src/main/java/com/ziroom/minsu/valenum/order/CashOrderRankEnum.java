/**
 * @FileName: CashOrderRankEnum.java
 * @Package com.ziroom.minsu.valenum.order
 * 
 * @author yd
 * @created 2017年8月31日 下午2:11:30
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.order;

/**
 * <p>返现单 和 房东等级 对应关系</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public enum CashOrderRankEnum {


	LANLORD_RAND_FIRST(1,3,1,5000,"第一等级","一",6000,"白银"),
	LANLORD_RAND_SECOND(3,5,2,6000,"第二等级","三",7000,"黄金"),
	LANLORD_RAND_THIRD(5,10,3,7000,"第三等级","五",30000,"钻石"),
	LANLORD_RAND_FOUR(10,1000000,4,30000,"第四等级","十",500000,"王者归来");


	CashOrderRankEnum(int orderNumMin,int orderNumMax,int rank,int cashMoney,String desc,String orderNum,
			int nextCashMoney,String rankDesc){

		this.orderNumMin = orderNumMin;
		this.orderNumMax = orderNumMax;
		this.rank = rank;
		this.cashMoney = cashMoney;
		this.desc = desc;
		this.orderNum = orderNum;
		this.nextCashMoney = nextCashMoney;
		this.rankDesc = rankDesc;
	}


	/**
	 * 
	 * 跟根据 排名获取枚举
	 *
	 * @author yd
	 * @created 2017年8月31日 下午7:03:15
	 *
	 * @param rank
	 * @return
	 */
	public static CashOrderRankEnum getCashOrderRankEnumByRank(int rank){
		for (CashOrderRankEnum cashOrderRankEnum : CashOrderRankEnum.values()) {

			if(cashOrderRankEnum.getRank()== rank){
				return cashOrderRankEnum;
			}
		}
		return null;
	}
	/**
	 * 
	 * 跟进订单 数量返回 当前等级
	 *
	 * @author yd
	 * @created 2017年8月31日 下午5:08:25
	 *
	 * @param num
	 * @return
	 */
	public static CashOrderRankEnum getCashOrderRankEnumByNum(int num){

		if(num>=1&&num<3){
			return CashOrderRankEnum.LANLORD_RAND_FIRST;
		}

		if(num>=3&&num<5){
			return CashOrderRankEnum.LANLORD_RAND_SECOND;
		}

		if(num>=5&&num<10){
			return CashOrderRankEnum.LANLORD_RAND_THIRD;
		}

		if(num>=10){
			return CashOrderRankEnum.LANLORD_RAND_FOUR;
		}
		return null;
	}



	/**
	 * 订单数量 最小值 >=
	 */
	private int orderNumMin;

	/**
	 * 订单数量最大值  不包含 <
	 */
	private int orderNumMax;
	/**
	 * 排名
	 */
	private int rank;

	/**
	 * 返现金额  单位分  
	 */
	private int cashMoney;
	
	/**
	 * 等级 描述
	 */
	private String desc;
	
	/**
	 * 接单数
	 */
	private String orderNum;

	/**
	 * 下一个阶级 金额
	 */
	private  int nextCashMoney;
	
	/**
	 * 等级描述
	 */
	private  String rankDesc;
	
	
	/**
	 * @return the rankDesc
	 */
	public String getRankDesc() {
		return rankDesc;
	}


	/**
	 * @param rankDesc the rankDesc to set
	 */
	public void setRankDesc(String rankDesc) {
		this.rankDesc = rankDesc;
	}


	/**
	 * @return the orderNum
	 */
	public String getOrderNum() {
		return orderNum;
	}


	/**
	 * @param orderNum the orderNum to set
	 */
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}


	/**
	 * @return the nextCashMoney
	 */
	public int getNextCashMoney() {
		return nextCashMoney;
	}


	/**
	 * @param nextCashMoney the nextCashMoney to set
	 */
	public void setNextCashMoney(int nextCashMoney) {
		this.nextCashMoney = nextCashMoney;
	}


	/**
	 * @return the rank
	 */
	public int getRank() {
		return rank;
	}


	/**
	 * @param rank the rank to set
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}


	/**
	 * @return the orderNumMin
	 */
	public int getOrderNumMin() {
		return orderNumMin;
	}

	/**
	 * @param orderNumMin the orderNumMin to set
	 */
	public void setOrderNumMin(int orderNumMin) {
		this.orderNumMin = orderNumMin;
	}

	/**
	 * @return the orderNumMax
	 */
	public int getOrderNumMax() {
		return orderNumMax;
	}

	/**
	 * @param orderNumMax the orderNumMax to set
	 */
	public void setOrderNumMax(int orderNumMax) {
		this.orderNumMax = orderNumMax;
	}

	/**
	 * @return the cashMoney
	 */
	public int getCashMoney() {
		return cashMoney;
	}

	/**
	 * @param cashMoney the cashMoney to set
	 */
	public void setCashMoney(int cashMoney) {
		this.cashMoney = cashMoney;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}


}
