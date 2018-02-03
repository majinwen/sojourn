package com.ziroom.minsu.valenum.common;


public enum ErrorCodeEnum {
	success(0, "成功","SUCCESS",1),
	fail(1, "失败","FAILED",0);

	ErrorCodeEnum(int code, String name,String codeEn,int sysCode) {
		this.code = code;
		this.name = name;
		this.codeEn = codeEn;
		this.sysCode = sysCode;
	}

	public static ErrorCodeEnum getCodeEn(String codeEn){
    	for(final ErrorCodeEnum ose : ErrorCodeEnum.values()){
    		if(ose.getCodeEn().equals(codeEn)){
    			return ose;
    		}
    	}
    	return null;
    }
	
	/** code */
	private int code;

	/** 名称 */
	private String name;
	
	/** codeEn */
	private String codeEn;
	
	/** sysCode */
	private int sysCode;

	
	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public String getCodeEn(){
		return codeEn;
	}
	
	public int getSysCode() {
		return sysCode;
	}
}
