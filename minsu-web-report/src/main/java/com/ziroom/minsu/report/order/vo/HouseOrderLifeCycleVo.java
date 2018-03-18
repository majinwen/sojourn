package com.ziroom.minsu.report.order.vo;

import java.util.Date;

import com.ziroom.minsu.report.common.annotation.FieldMeta;
import com.ziroom.minsu.report.house.vo.HouseCommonVo;

/**
 * <p>HouseOrderLifeCycleVo</p>
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

public class HouseOrderLifeCycleVo extends HouseCommonVo {
   
	/**
	 * 
	 */
	@FieldMeta(skip = true)
	private static final long serialVersionUID = 1449723418011236882L;
	
	@FieldMeta(name="上架日期",order=13)
	private Date onLineDate;
	
	@FieldMeta(name="首个订单日期",order=14)
	private Date firstOrderTime;
	
	@FieldMeta(name="首个入住日期",order=15)
	private Date firstStartTime;
	
	@FieldMeta(name="首个评价日期",order=16)
	private Date firstEvaTime;

	
	public Date getOnLineDate() {
		return onLineDate;
	}

	public void setOnLineDate(Date onLineDate) {
		this.onLineDate = onLineDate;
	}

	public Date getFirstOrderTime() {
		return firstOrderTime;
	}

	public void setFirstOrderTime(Date firstOrderTime) {
		this.firstOrderTime = firstOrderTime;
	}

	public Date getFirstStartTime() {
		return firstStartTime;
	}

	public void setFirstStartTime(Date firstStartTime) {
		this.firstStartTime = firstStartTime;
	}

	public Date getFirstEvaTime() {
		return firstEvaTime;
	}

	public void setFirstEvaTime(Date firstEvaTime) {
		this.firstEvaTime = firstEvaTime;
	}

}
