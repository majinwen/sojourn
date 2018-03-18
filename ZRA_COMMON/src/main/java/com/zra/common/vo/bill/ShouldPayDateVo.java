package com.zra.common.vo.bill;

import com.zra.common.vo.base.BaseItemDescVo;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年10月10日 19:45
 * @since 1.0
 */
public class ShouldPayDateVo extends BaseItemDescVo {
	private Integer penaltyWarn;// 是否有逾期 0-未 1-有
	private String time;// 时间
	private String value;// 应缴费日期

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getPenaltyWarn() {
		return penaltyWarn;
	}

	public void setPenaltyWarn(Integer penaltyWarn) {
		this.penaltyWarn = penaltyWarn;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
