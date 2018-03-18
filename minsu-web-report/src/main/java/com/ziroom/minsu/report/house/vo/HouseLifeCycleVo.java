package com.ziroom.minsu.report.house.vo;

import java.util.Date;

import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * <p>HouseOrderInfoVo</p>
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

public class HouseLifeCycleVo extends HouseCommonVo {
   /**
	 * 
	 */
	@FieldMeta(skip = true)
	private static final long serialVersionUID = 1449723418011236882L;
	@FieldMeta(name="创建日期",order=13)
	private Date createDate;
	
	@FieldMeta(name="发布日期",order=14)
	private Date publishDate;
	
	@FieldMeta(name="管家审核通过日期",order=15)
	private Date grardAccessDate;
	
	@FieldMeta(name="上架日期",order=16)
	private Date onLineDate;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Date getGrardAccessDate() {
		return grardAccessDate;
	}

	public void setGrardAccessDate(Date grardAccessDate) {
		this.grardAccessDate = grardAccessDate;
	}

	public Date getOnLineDate() {
		return onLineDate;
	}

	public void setOnLineDate(Date onLineDate) {
		this.onLineDate = onLineDate;
	}

}
