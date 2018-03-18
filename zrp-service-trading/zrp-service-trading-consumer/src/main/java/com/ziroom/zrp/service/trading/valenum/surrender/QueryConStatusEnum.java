package com.ziroom.zrp.service.trading.valenum.surrender;
/**
 * <p>解约状态枚举</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年11月9日
 * @since 1.0
 */
public enum QueryConStatusEnum {
	RUNNING(1,"RUNNING"),// 合同正在解约中
	QIANKUAN(2,"QIANKUAN"),// 有欠款
	APPLYEXPIRE(3,"APPLYEXPIRE"),//该申请已经逾期
	HAVEDELETE(4,"HAVEDELETE");//解约协议已删除
	
	private int status;
	private String name;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	QueryConStatusEnum(int status, String name) {
		this.status = status;
		this.name = name;
	}
}
