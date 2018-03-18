package com.ziroom.zrp.service.trading.valenum.surrender;
/**
 * <p>审核类型枚举（财务/租务）</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年11月21日
 * @since 1.0
 */
public enum AuditTypeEnum {
	ZW(0,"租务"),
	CW(1,"财务");
	
	private int type;
	private String typeName;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	private AuditTypeEnum(int type, String typeName) {
		this.type = type;
		this.typeName = typeName;
	}
	
}
