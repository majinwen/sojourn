package com.ziroom.minsu.valenum.account;

/**
 * <p>company</p>
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
public enum CompanyCodeEnum {
	ZFKJ("bj_11","111111","北京"),
	BJS("bj_11","BJS","北京"),
	BJ("bj_11","BJ","北京"),
    SH("sh_50","SH","上海"),
    SZ("sz_24","SZ","深圳");

    CompanyCodeEnum(String code, String name,String clientName) {
        this.code = code;
        this.name = name;
        this.clientName = clientName;
    }

    public static CompanyCodeEnum getByName(String clientName){
    	for(final CompanyCodeEnum ose : CompanyCodeEnum.values()){
    		if(ose.getName().equals(clientName)){
    			return ose;
    		}
    	}
    	return null;
    }

    /** code */
    private String code;

    /** 名称 */
    private String name;
    
    /** 客户端传递名称 */
    private String clientName;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
    
    public String getClientName() {
        return clientName;
    }
}
