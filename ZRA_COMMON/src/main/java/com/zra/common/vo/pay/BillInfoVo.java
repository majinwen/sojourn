package com.zra.common.vo.pay;

import com.zra.common.vo.base.BaseFieldVo;

/**
 * <p>账单信息</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @Date Created in 2017年09月17日 16:55
 * @version 1.0
 * @since 1.0
 */
public class BillInfoVo extends BaseFieldVo {

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
