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
public enum InvoiceIncomeEnum {
    
	customer_service("房客服务费",1,"短租客户-服务费","3002"),
	customer_clean_service("房客服务费",6,"短租客户-服务费","3002"), //清洁费
	landlord_service("房东服务费",3,"短租业主-服务费","3001"),
    black("利得",7,"短租业主-利得","3007"),
	customer_breach_service("违约金服务费",2,"短租客户-违约金","3005"),
    penalty_service("罚款单",8,"短租业主-违约金","3006"),
	landlord_breach_service("违约金服务费",4,"短租业主-违约金","3006");

    InvoiceIncomeEnum( String code, int dzCode ,String name,String financeCode) {
        this.code = code;
        this.name = name;
        this.dzCode = dzCode;
        this.financeCode = financeCode;
       
    }

    public static InvoiceIncomeEnum getByCode(int code){
    	for(final InvoiceIncomeEnum ose : InvoiceIncomeEnum.values()){
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
    
    private String financeCode;
    
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

	public int getDzCode() {
		return dzCode;
	}
	
	public String getFinanceCode() {
        return financeCode;
    }
    
}
