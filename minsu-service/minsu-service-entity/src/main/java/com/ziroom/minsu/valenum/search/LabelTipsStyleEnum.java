package com.ziroom.minsu.valenum.search;

/** 
 * 
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public enum LabelTipsStyleEnum {


    ONLY_WORDS("ONLY_WORDS","/search/img/label/{iconPath}/xinshang.png","纯文字的式样"),
    WORDS_WITH_APP_BUTTON("WORDS_WITH_APP_BUTTON","/search/img/label/{iconPath}/shandian.png","带边框的文字式样，边框由客户端定义");

    private final String code; 

    private final String backgroundImgSrc;
    
    private final String remark;

	private LabelTipsStyleEnum(String code, String backgroundImgSrc, String remark) {
		this.code = code;
		this.backgroundImgSrc = backgroundImgSrc;
		this.remark = remark;
	}

	public String getCode() {
		return code;
	}

	public String getBackgroundImgSrc() {
		return backgroundImgSrc;
	}

	public String getRemark() {
		return remark;
	}
   
}
