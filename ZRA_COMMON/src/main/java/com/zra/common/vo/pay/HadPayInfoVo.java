package com.zra.common.vo.pay;

import com.zra.common.vo.base.BaseFieldVo;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>已经支付信息</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @Date Created in 2017年09月17日 16:57
 * @version 1.0
 * @since 1.0
 */
public class HadPayInfoVo extends BaseFieldVo {
	private List<PayRecordVo> list;

	public List<PayRecordVo> getList() {
		return list;
	}

	public void setList(List<PayRecordVo> list) {
		this.list = list;
	}
}
