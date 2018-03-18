package com.ziroom.minsu.report.board.dto;

import java.io.Serializable;
import java.util.Date;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;

/**
 * 
 * <p>目标看板查询请求</p>
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
public class RegionRequest implements Serializable{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -1741629794316184529L;
	
	public static final String MONTH_FORMAT_PATTERN = "yyyy-MM";
	
	/**
	 * 国家编码
	 */
	private String nationCode;
	
	/**
	 * 目标月份(查询)
	 */
	private String targetMonth;
	
	/**
	 * 目标月份(下载)-开始
	 */
	private String targetMonthStart;
	
	/**
	 * 目标月份(下载)-结束
	 */
	private String targetMonthEnd;
	
	/**
	 * 大区逻辑id
	 */
	private String regionFid;
	
	public String getNationCode() {
		return nationCode;
	}
	
	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	public String getTargetMonth() {
		return Check.NuNStrStrict(targetMonth) ? DateUtil.dateFormat(new Date(), MONTH_FORMAT_PATTERN) : targetMonth;
	}

	public void setTargetMonth(String targetMonth) {
		this.targetMonth = targetMonth;
	}

	public String getTargetMonthStart() {
		return targetMonthStart;
	}

	public void setTargetMonthStart(String targetMonthStart) {
		this.targetMonthStart = targetMonthStart;
	}

	public String getTargetMonthEnd() {
		return targetMonthEnd;
	}

	public void setTargetMonthEnd(String targetMonthEnd) {
		this.targetMonthEnd = targetMonthEnd;
	}

	public String getRegionFid() {
		return regionFid;
	}

	public void setRegionFid(String regionFid) {
		this.regionFid = regionFid;
	}
}
