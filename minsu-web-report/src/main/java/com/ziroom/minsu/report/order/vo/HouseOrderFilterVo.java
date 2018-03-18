package com.ziroom.minsu.report.order.vo;

import com.ziroom.minsu.report.common.annotation.FieldMeta;
import com.ziroom.minsu.report.house.vo.HouseCommonVo;

/**
 * <p>HouseEvaluateVo</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/8/6.
 * @version 1.0
 * @since 1.0
 */

public class HouseOrderFilterVo extends HouseCommonVo {
   /**
	 * 
	 */
	@FieldMeta(skip = true)
	private static final long serialVersionUID = 1449723418011236882L;
	
	@FieldMeta(name="浏览量",order=31)
	private Integer browseNum;
	
	@FieldMeta(name="咨询量",order=31)
	private Integer IMAskNum;
	
	@FieldMeta(name="申请量",order=31)
	private Integer orderApplyNum;
	
	@FieldMeta(name="接受量",order=31)
	private Integer lanAcceptNum;
	
	@FieldMeta(name="订单量",order=31)
	private Integer orderSuccNum;

	public Integer getBrowseNum() {
		return browseNum;
	}

	public void setBrowseNum(Integer browseNum) {
		this.browseNum = browseNum;
	}

	public Integer getIMAskNum() {
		return IMAskNum;
	}

	public void setIMAskNum(Integer iMAskNum) {
		IMAskNum = iMAskNum;
	}

	public Integer getOrderApplyNum() {
		return orderApplyNum;
	}

	public void setOrderApplyNum(Integer orderApplyNum) {
		this.orderApplyNum = orderApplyNum;
	}

	public Integer getLanAcceptNum() {
		return lanAcceptNum;
	}

	public void setLanAcceptNum(Integer lanAcceptNum) {
		this.lanAcceptNum = lanAcceptNum;
	}

	public Integer getOrderSuccNum() {
		return orderSuccNum;
	}

	public void setOrderSuccNum(Integer orderSuccNum) {
		this.orderSuccNum = orderSuccNum;
	}

}
