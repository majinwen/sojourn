package com.ziroom.minsu.report.board.dto;

import java.io.Serializable;

/**
 * 
 * <p>经营数据查询请求</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class LandlordRequest implements Serializable{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -1481228345648129159L;
	
	/**
	 * 国家编码
	 */
	private String nationCode;
	
	/**
	 * 大区逻辑id
	 */
	private String regionFid;
	
	/**
	 * 查询日期
	 */
	private String queryDate;
	
	public String getNationCode() {
		return nationCode;
	}
	
	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	public String getRegionFid() {
		return regionFid;
	}

	public void setRegionFid(String regionFid) {
		this.regionFid = regionFid;
	}

	public String getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}
}
