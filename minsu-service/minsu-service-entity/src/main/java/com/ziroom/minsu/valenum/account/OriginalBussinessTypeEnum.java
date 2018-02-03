package com.ziroom.minsu.valenum.account;

/**
 * <p>转账类型</p>
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
public enum OriginalBussinessTypeEnum {
    
	original_bussiness_type(2,"转账from业务类型");

    OriginalBussinessTypeEnum( int code, String name) {
        this.code = code;
        this.name = name;
       
    }

    public static OriginalBussinessTypeEnum getByCode(int code){
    	for(final OriginalBussinessTypeEnum ose : OriginalBussinessTypeEnum.values()){
    		if(ose.getCode() == code){
    			return ose;
    		}
    	}
    	return null;
    }

    /** code */
    private int code;

    /** 名称 */
    private String name;
    
    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
    
    
}
