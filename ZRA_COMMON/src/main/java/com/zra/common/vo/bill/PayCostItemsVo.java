package com.zra.common.vo.bill;

import com.zra.common.vo.base.BaseFieldColorVo;
import com.zra.common.vo.base.BaseFieldVo;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>支付费用项Vo</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年10月11日 09:51
 * @since 1.0
 */
public class PayCostItemsVo extends BaseFieldVo {
	private List<BaseFieldColorVo> list;

	public List<BaseFieldColorVo> getList() {
		return list;
	}

	public void setList(List<BaseFieldColorVo> list) {
		this.list = list;
	}
}
