package com.ziroom.minsu.services.order.entity;


/**
 * <p>订单的主表 + 房源+ 金额+ 评价 </p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liujun
 * @version 1.0
 * @since 1.0
 */
public class OrderEvaVo extends OrderInfoVo {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 3468507316519023623L;
	
	/**
	 * 用户头像
	 */
	private String headPicUrl;

	public String getHeadPicUrl() {
		return headPicUrl;
	}

	public void setHeadPicUrl(String headPicUrl) {
		this.headPicUrl = headPicUrl;
	}

}
