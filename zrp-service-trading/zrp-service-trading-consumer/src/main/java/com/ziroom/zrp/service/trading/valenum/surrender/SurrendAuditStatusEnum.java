package com.ziroom.zrp.service.trading.valenum.surrender;
/**
 * <p>退租审核状态枚举</p>
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
public enum SurrendAuditStatusEnum {
	
	DSH("0","待审核"),
	SHBH("1","审核驳回"),
	SHTG("2", "审核通过");


	private String status;
	private String name;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private SurrendAuditStatusEnum(String status, String name) {
		this.status = status;
		this.name = name;
	}
	
	public static String getNameByStatus(String status){
		for(final SurrendAuditStatusEnum contractStatusEnum : SurrendAuditStatusEnum.values()){
            if(contractStatusEnum.getStatus().equals(status)){
                return contractStatusEnum.getName();
            }
        }
		return null;
	}

}
