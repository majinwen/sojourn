/**
 * @FileName: UpdatePassSLVo.java
 * @Package com.ziroom.minsu.services.house.smartlock.vo
 * 
 * @author jixd
 * @created 2016年6月23日 下午12:29:11
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.smartlock.vo;

/**
 * <p>更新 删除密码返回</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class UDPassSLVo extends BaseSLVo{
	/**
	 * 服务序号，异步操作的序列号，一定时间内系统全局唯一。当密码异步操作完成后，异步操作回调会携带service_id到请求发起方。
	 */
	private String service_id;

	public String getService_id() {
		return service_id;
	}

	public void setService_id(String service_id) {
		this.service_id = service_id;
	}
	
	
}
