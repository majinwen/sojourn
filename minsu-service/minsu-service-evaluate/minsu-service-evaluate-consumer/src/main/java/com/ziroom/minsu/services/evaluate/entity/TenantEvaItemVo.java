/**
 * @FileName: TenantEvaItemVo.java
 * @Package com.ziroom.minsu.api.evaluate.entity
 * 
 * @author jixd
 * @created 2016年5月28日 下午9:56:08
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.evaluate.entity;

import java.io.Serializable;

/**
 * <p>房客评价列表</p>
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
public class TenantEvaItemVo implements Serializable{
	
	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = -2449841391152448451L;
	/**
	 * 用户头像
	 */
	private String userHeadPic;
	/**
	 * 用户昵称
	 */
	private String nickName;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 评价内容
	 */
	private String content;
	
	
	
	public String getUserHeadPic() {
		return userHeadPic;
	}
	public void setUserHeadPic(String userHeadPic) {
		this.userHeadPic = userHeadPic;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
