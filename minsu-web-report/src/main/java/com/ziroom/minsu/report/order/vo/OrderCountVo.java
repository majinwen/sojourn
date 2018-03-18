package com.ziroom.minsu.report.order.vo;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * <p>OrderCountVo</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2017/1/5.
 * @version 1.0
 * @since 1.0
 */

public class OrderCountVo extends BaseEntity {
   /**
	*
	*/
	@FieldMeta(skip = true)
	private static final long serialVersionUID = 1449722342236882L;
	
	@FieldMeta(name="城市编号",order=1)
	private String cityCode;
	
	@FieldMeta(name="总订单量",order=3)
	private Integer orderNum;

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
}
