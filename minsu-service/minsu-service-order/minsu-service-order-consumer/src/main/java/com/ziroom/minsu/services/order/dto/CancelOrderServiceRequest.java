package com.ziroom.minsu.services.order.dto;

/**
 * <p>客服取消订单入参</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2017/1/4 10:16
 * @version 1.0
 * @since 1.0
 */
public class CancelOrderServiceRequest {


    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 操作人uid
     */
    private String operUid;

    /**
     * 取消原因类型
     * @see com.ziroom.minsu.valenum.order.CancelTypeEnum
     */
    private Integer cancelType;

    /**
     * 取消原因
     */
    private String cancelReason;


    /**
     * 违约金(元)
     */
    private Integer penaltyMoney = 0;

    /**
     * 是否收取房东服务费(违约金、清洁费)
     */
    private Integer isTakeLandlordComm = 0;

    /**
     * 是否退还清洁费(未入住才退)
     */
    private Integer isReturnCleanMoney = 0;

    /**
     * 是否退还今晚房租(如果今晚是最后一天则不用退)
     */
    private Integer isReturnTonightRental = 0;
    
//新加字段   为房东取消订单类型准备
    /**
     * 取消原因Code
     * 
     */
    private Integer cancelReasonCode;
    
    /**
     * 操作人名字
     */
    private String empCode;
    
    /**
     * 操作人名字
     */
    private String operName;
    
    /**
	 * 房东uid
	 */
	private String landlordUid;
	
	/**
	 * 是否有订单首夜罚款
	 */
	private Integer isTakeFirstNightMoney;
	
	/**
	 * 是否有100元罚款
	 */
	private Integer isTakeOneHundred;
	
	/**
	 * 是否有取消天使房东惩罚
	 */
	private Integer isCancelAngel;
	
	/**
	 * 是否有增加系统评价惩罚
	 */
	private Integer isAddSystemEval;
	
	/**
	 * 是否有更新排序因子惩罚
	 */
	private Integer isUpdateRankFactor;
	
	/**
	 * 是否有屏蔽日历惩罚
	 */
	private Integer isShieldCalendar;
	
	/**
	 * 是否有赠送客户优惠券惩罚
	 */
	private Integer isGiveCoupon;
	
	/**
	 * 订单首夜罚款是否完成
	 */
	private String isTakeFirstNightMoneyDone;
	
	/**
	 * 100元罚款是否完成
	 */
	private String isTakeOneHundredDone;
	
	/**
	 * 取消天使房东惩罚是否完成
	 */
	private String isCancelAngelDone;
	
	/**
	 * 增加系统评价惩罚是否完成
	 */
	private String isAddSystemEvalDone;
	
	/**
	 * 更新排序因子惩罚是否完成
	 */
	private String isUpdateRankFactorDone;
	
	/**
	 * 屏蔽日历惩罚是否完成
	 */
	private String isShieldCalendarDone;
	
	/**
	 * 赠送客户优惠券是否完成
	 */
	private String isGiveCouponDone;


	/**
	 * 从现在到往前6个月，房东取消订单的总数
	 */
	private long lanCancelOrderInSomeTime;
	
	 /**
     * 取消类型Str
     */
    private String cancelTypeName;
    
    /**
	 * 收取首晚房费Str
	 */
	private String takeFirstNightMoneyName;
	
	/**
	* 收取首晚房费Str
	*/
	private String takeOneHundredName;	
	
    /**
     * 取消天使房东Str
     */
    private String cancelAngelName;
    
    /**
     * 添加系统评价Str
     */
    private String addSystemEvalName;
    
    /**
     * 更新排序因子Str
     */
    private String updateRankFactorName;
    
    /**
     * 屏蔽日历Str
     */
    private String shieldCalendarName;
    
    /**
     * 赠送优惠券Str
     */
    private String giveCouponName;
    
    /**
     * 是否点击编辑按钮
     */
    private Integer isEdit;
    
    /**
	 * 100元罚款的值
	 */
	private Integer oneHondred;
    
    public String getTakeFirstNightMoneyName() {
		return takeFirstNightMoneyName;
	}

	public void setTakeFirstNightMoneyName(String takeFirstNightMoneyName) {
		this.takeFirstNightMoneyName = takeFirstNightMoneyName;
	}

	public String getTakeOneHundredName() {
		return takeOneHundredName;
	}

	public void setTakeOneHundredName(String takeOneHundredName) {
		this.takeOneHundredName = takeOneHundredName;
	}

	public String getCancelTypeName() {
		return cancelTypeName;
	}

	public void setCancelTypeName(String cancelTypeName) {
		this.cancelTypeName = cancelTypeName;
	}

	public String getCancelAngelName() {
		return cancelAngelName;
	}

	public void setCancelAngelName(String cancelAngelName) {
		this.cancelAngelName = cancelAngelName;
	}

	public String getAddSystemEvalName() {
		return addSystemEvalName;
	}

	public void setAddSystemEvalName(String addSystemEvalName) {
		this.addSystemEvalName = addSystemEvalName;
	}

	public String getUpdateRankFactorName() {
		return updateRankFactorName;
	}

	public void setUpdateRankFactorName(String updateRankFactorName) {
		this.updateRankFactorName = updateRankFactorName;
	}

	public String getShieldCalendarName() {
		return shieldCalendarName;
	}

	public void setShieldCalendarName(String shieldCalendarName) {
		this.shieldCalendarName = shieldCalendarName;
	}

	public String getGiveCouponName() {
		return giveCouponName;
	}

	public void setGiveCouponName(String giveCouponName) {
		this.giveCouponName = giveCouponName;
	}

	public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Integer getIsReturnTonightRental() {
        return isReturnTonightRental;
    }

    public void setIsReturnTonightRental(Integer isReturnTonightRental) {
        this.isReturnTonightRental = isReturnTonightRental;
    }

    public Integer getCancelType() {
        return cancelType;
    }

    public void setCancelType(Integer cancelType) {
        this.cancelType = cancelType;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public Integer getPenaltyMoney() {
        return penaltyMoney;
    }

    public void setPenaltyMoney(Integer penaltyMoney) {
        this.penaltyMoney = penaltyMoney;
    }

    public Integer getIsTakeLandlordComm() {
        return isTakeLandlordComm;
    }

    public void setIsTakeLandlordComm(Integer isTakeLandlordComm) {
        this.isTakeLandlordComm = isTakeLandlordComm;
    }

    public Integer getIsReturnCleanMoney() {
        return isReturnCleanMoney;
    }

    public void setIsReturnCleanMoney(Integer isReturnCleanMoney) {
        this.isReturnCleanMoney = isReturnCleanMoney;
    }

    public String getOperUid() {
        return operUid;
    }

    public void setOperUid(String operUid) {
        this.operUid = operUid;
    }

	public Integer getCancelReasonCode() {
		return cancelReasonCode;
	}

	public void setCancelReasonCode(Integer cancelReasonCode) {
		this.cancelReasonCode = cancelReasonCode;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	public Integer getIsTakeFirstNightMoney() {
		return isTakeFirstNightMoney;
	}

	public void setIsTakeFirstNightMoney(Integer isTakeFirstNightMoney) {
		this.isTakeFirstNightMoney = isTakeFirstNightMoney;
	}

	public Integer getIsTakeOneHundred() {
		return isTakeOneHundred;
	}

	public void setIsTakeOneHundred(Integer isTakeOneHundred) {
		this.isTakeOneHundred = isTakeOneHundred;
	}

	public Integer getIsCancelAngel() {
		return isCancelAngel;
	}

	public void setIsCancelAngel(Integer isCancelAngel) {
		this.isCancelAngel = isCancelAngel;
	}

	public Integer getIsAddSystemEval() {
		return isAddSystemEval;
	}

	public void setIsAddSystemEval(Integer isAddSystemEval) {
		this.isAddSystemEval = isAddSystemEval;
	}

	public Integer getIsUpdateRankFactor() {
		return isUpdateRankFactor;
	}

	public void setIsUpdateRankFactor(Integer isUpdateRankFactor) {
		this.isUpdateRankFactor = isUpdateRankFactor;
	}

	public Integer getIsShieldCalendar() {
		return isShieldCalendar;
	}

	public void setIsShieldCalendar(Integer isShieldCalendar) {
		this.isShieldCalendar = isShieldCalendar;
	}

	public Integer getIsGiveCoupon() {
		return isGiveCoupon;
	}

	public void setIsGiveCoupon(Integer isGiveCoupon) {
		this.isGiveCoupon = isGiveCoupon;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public long getLanCancelOrderInSomeTime() {
		return lanCancelOrderInSomeTime;
	}

	public void setLanCancelOrderInSomeTime(long lanCancelOrderInSomeTime) {
		this.lanCancelOrderInSomeTime = lanCancelOrderInSomeTime;
	}

	public Integer getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(Integer isEdit) {
		this.isEdit = isEdit;
	}

	public Integer getOneHondred() {
		return oneHondred;
	}

	public void setOneHondred(Integer oneHondred) {
		this.oneHondred = oneHondred;
	}

	public String getIsTakeFirstNightMoneyDone() {
		return isTakeFirstNightMoneyDone;
	}

	public void setIsTakeFirstNightMoneyDone(String isTakeFirstNightMoneyDone) {
		this.isTakeFirstNightMoneyDone = isTakeFirstNightMoneyDone;
	}

	public String getIsTakeOneHundredDone() {
		return isTakeOneHundredDone;
	}

	public void setIsTakeOneHundredDone(String isTakeOneHundredDone) {
		this.isTakeOneHundredDone = isTakeOneHundredDone;
	}

	public String getIsCancelAngelDone() {
		return isCancelAngelDone;
	}

	public void setIsCancelAngelDone(String isCancelAngelDone) {
		this.isCancelAngelDone = isCancelAngelDone;
	}

	public String getIsAddSystemEvalDone() {
		return isAddSystemEvalDone;
	}

	public void setIsAddSystemEvalDone(String isAddSystemEvalDone) {
		this.isAddSystemEvalDone = isAddSystemEvalDone;
	}

	public String getIsUpdateRankFactorDone() {
		return isUpdateRankFactorDone;
	}

	public void setIsUpdateRankFactorDone(String isUpdateRankFactorDone) {
		this.isUpdateRankFactorDone = isUpdateRankFactorDone;
	}

	public String getIsShieldCalendarDone() {
		return isShieldCalendarDone;
	}

	public void setIsShieldCalendarDone(String isShieldCalendarDone) {
		this.isShieldCalendarDone = isShieldCalendarDone;
	}

	public String getIsGiveCouponDone() {
		return isGiveCouponDone;
	}

	public void setIsGiveCouponDone(String isGiveCouponDone) {
		this.isGiveCouponDone = isGiveCouponDone;
	}

	
}
