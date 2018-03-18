package com.ziroom.zrp.service.trading.valenum;


/**
 * <p>签约主体类型枚举</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年10月23日 10:02
 * @since 1.0
 */
public enum SyncToFinEnum {
	NO(0,"未同步"),
	SUCCESS(1,"成功"),
	FAIL(2, "失败");

	private int code;

	private String name;

	private SyncToFinEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
