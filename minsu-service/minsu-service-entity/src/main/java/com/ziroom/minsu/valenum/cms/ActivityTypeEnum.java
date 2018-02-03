package com.ziroom.minsu.valenum.cms;

/**
 * <p>活动类型</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/6/15.
 * @version 1.0
 * @since 1.0
 */
public enum ActivityTypeEnum {
    //20：免佣金活动 21：满减活动 22：免天活动
    LAN_COM(20,"房东免佣金活动",0,6,3),
    CUT(21,"满减活动",0,5,0),
    FREE(22,"免天活动",0,4,0),
    BACK_CASH(23,"返现活动",0,3,0),
    GIFT_AC(30,"礼品活动",0,2,0),
    FIRST_ORDER_REDUC(25,"首单立减",1,1,7){
    	/* (non-Javadoc)
    	 * @see com.ziroom.minsu.valenum.cms.ActivityTypeEnum#getSymbol()
    	 */
    	@Override
    	public String getSymbol() {
    		return "-";
    	}
    };

    ActivityTypeEnum(int code, String name,int colorType,int index,int acType) {
        this.code = code;
        this.name = name;
        this.colorType = colorType;
        this.index = index;
        this.acType = acType;

    }

    /**
     * 获取优惠券活动类型
     * @param code
     * @return
     */
    public static ActivityTypeEnum getByCode(int code){
        for(final ActivityTypeEnum actTypeEnum : ActivityTypeEnum.values()){
            if(actTypeEnum.getCode()== code){
                return actTypeEnum;
            }
        }
        return null;
    }
    
    
    /**
     * 
     * 根据活动类型获取
     * 
     * 当前只有： 首单立减  其余几个都是0 不存在
     *
     * @author yd
     * @created 2017年6月8日 上午10:41:14
     *
     * @param code
     * @return
     */
    public static ActivityTypeEnum getByActivityType(int acType){
        for(final ActivityTypeEnum actTypeEnum : ActivityTypeEnum.values()){
            if(actTypeEnum.getAcType() == acType){
                return actTypeEnum;
            }
        }
        return null;
    }

	public String getShowName() {
		return name;
	}
	
	public int getColorType() {
		return colorType;
	}

	
    /** code */
    private int code;

    /** 名称 */
    private String name;
    
    private int colorType;
    
    private int index;
    
    private int acType;
    
    
    
    
	/**
	 * @return the acType
	 */
	public int getAcType() {
		return acType;
	}

	/**
	 * @param acType the acType to set
	 */
	public void setAcType(int acType) {
		this.acType = acType;
	}

	/**
	 * 金额加减符号
	 * @return
     */
	public String getSymbol(){
		return "";
	}

	
    /**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
	/**
	 * @param colorType the colorType to set
	 */
	public void setColorType(int colorType) {
		this.colorType = colorType;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
    
	
}
