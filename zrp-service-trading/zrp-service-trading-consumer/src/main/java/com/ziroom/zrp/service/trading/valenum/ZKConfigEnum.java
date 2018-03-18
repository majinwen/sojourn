package com.ziroom.zrp.service.trading.valenum;

/**
 * <p>ZK配置枚举</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2018年01月09日 11:02
 * @since 1.0
 */
public enum ZKConfigEnum {
	ZK_CONFIG_ENUM_001("zyu", "isOpenSmart",""),// 是否开启智能锁 开关
	ZK_CONFIG_ENUM_002("zyu", "isOpenAppUpgrade",""), // 是否开启强制升级开关
	ZK_CONFIG_ENUM_003("zyu", "jobMsgNotifyMobile","15010386533,15943047549,18811366591,15237171664,18515838768,18201616821,13521263178"), // 定时任务通知号码
	ZK_CONFIG_ENUM_004("zyu", "isValidAge", "");
	private String type;
	private String code;
	private String defaultValue;


	ZKConfigEnum(String type, String code,String defaultValue) {
		this.type = type;
		this.code = code;
		this.defaultValue = defaultValue;
	}

	public String getType() {
		return type;
	}

	public String getCode() {
		return code;
	}

	public String getDefaultValue() {
		return defaultValue;
	}
}
