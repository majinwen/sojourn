package com.ziroom.minsu.valenum.account;

/**
 * <p>消费类型</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liyingjie
 * @version 1.0
 * @since 1.0
 */
public enum CustomerTypeEnum {
    
	landlord("yz","业主",1,"YZ"),
	roomer("kh","房客",2,"KH");

    CustomerTypeEnum( String code, String name,int statusCode,String payName) {
        this.code = code; //账户系统
        this.name = name;
        this.statusCode = statusCode;
        this.payName = payName;//支付系统
    }

    public static CustomerTypeEnum getByCode(String code){
    	for(final CustomerTypeEnum ose : CustomerTypeEnum.values()){
    		if(ose.getCode().equals(code)){
    			return ose;
    		}
    	}
    	return null;
    }
    
    /**
     * 根据statusCode获取枚举
     * @author lishaochuan
     * @create 2016年5月15日下午7:18:23
     * @param statusCode
     * @return
     */
	public static CustomerTypeEnum getByStatusCode(Integer statusCode) {
		if(statusCode == null){
			return null;
		}
		for (final CustomerTypeEnum ose : CustomerTypeEnum.values()) {
			if (ose.getStatusCode() == statusCode) {
				return ose;
			}
		}
		return null;
	}
	
	/**
	 * 根据statusCode获取账户系统code
	 * @author lishaochuan
	 * @create 2016年5月15日下午7:18:35
	 * @param statusCode
	 * @return
	 */
	public static String getCodeByStatusCode(Integer statusCode) {
		if(statusCode == null){
			return null;
		}
		CustomerTypeEnum CustomerTypeEnum = getByStatusCode(statusCode);
		if(CustomerTypeEnum != null){
			return CustomerTypeEnum.getCode();
		}
		return null;
	}

    /** code */
    private String code;

    /** 名称 */
    private String name;
    
    /** statusCode */
    private int statusCode;
    
    /** 名称 */
    private String payName;
    
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
    
    public int getStatusCode(){
    	return statusCode;
    }
    
    public String getPayName() {
        return payName;
    }
}
