package com.ziroom.minsu.report.basedata.valenum;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author wangwentao 2017/5/27
 * @version 1.0
 * @since 1.0
 */
public enum MsgAdvisoryFollowEnum {

    NO_FOLLOW(10,"10", "未跟进"),
    FOLLOWING(20,"20", "跟进中"),
    FOLLOWED(30, "30", "跟进结束");

    private Integer code;
    private String codeStr;
    private String name;

    
    private MsgAdvisoryFollowEnum(Integer code, String codeStr, String name) {
		this.code = code;
		this.codeStr = codeStr;
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getCodeStr() {
		return codeStr;
	}

	public void setCodeStr(String codeStr) {
		this.codeStr = codeStr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static MsgAdvisoryFollowEnum getNameByCode(String code){
    	if(null != code && !"".equals(code)){
    		for (MsgAdvisoryFollowEnum temp : MsgAdvisoryFollowEnum.values()) {
    			if(code.equals(temp.getCodeStr())){
    				return temp;
    			}
			}
    	}
    	return null;
    }
}
