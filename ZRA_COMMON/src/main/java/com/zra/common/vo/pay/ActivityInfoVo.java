package com.zra.common.vo.pay;

import com.zra.common.vo.base.BaseFieldVo;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>参与活动信息</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @Date Created in 2017年09月17日 17:01
 * @version 1.0
 * @since 1.0
 */
public class ActivityInfoVo extends BaseFieldVo {
	private List<BaseFieldVo> list;

	public List<BaseFieldVo> getList() {
		return list;
	}

	public void setList(List<BaseFieldVo> list) {
		this.list = list;
	}
}
