package com.ziroom.minsu.valenum.finance;

/**
 * <p>财务付款金额明细类型</p>
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
public enum FeeCategoryEnum {
    
	khdj("khdj",1,"客户定金"),
	landlord("yjyz",2,"业主"),
	bussness("sh",3,"商户");

    FeeCategoryEnum( String code, int dzCode ,String name) {
        this.code = code;
        this.name = name;
       
    }

    public static FeeCategoryEnum getByCode(int code){
    	for(final FeeCategoryEnum ose : FeeCategoryEnum.values()){
    		if(ose.getDzCode() == code){
    			return ose;
    		}
    	}
    	return null;
    }

    /** code */
    private String code;

    /** 平台code */
    private int dzCode;
    
    /** 名称 */
    private String name;
    
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

	public int getDzCode() {
		return dzCode;
	}
    
}
