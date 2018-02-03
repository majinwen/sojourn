package com.ziroom.minsu.services.basedata.dto;


import java.util.List;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * 
 * <p>静态资源查询参数</p>
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
public class StaticResourceRequest extends PageRequest {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -3116803538075433709L;
	
	/**
	 * 资源标题
	 */
	private String resTitle;
	
	/**
	 * 资源code
	 */
	private String resCode;
	
	/**
	 * 资源类型
	 */
	private Integer resType;
	
	/**
	 * 创建人
	 */
	private String creatorName;
	
	/**
	 * 创建人fid集合
	 */
	private List<String> createFidList;
	
	/**
	 * 开始创建时间
	 */
	private String createDateStart;
	
	/**
	 * 开始结束时间
	 */
	private String createDateEnd;

	public String getResTitle() {
		return resTitle;
	}

	public void setResTitle(String resTitle) {
		this.resTitle = resTitle;
	}

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public Integer getResType() {
		return resType;
	}

	public void setResType(Integer resType) {
		this.resType = resType;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public List<String> getCreateFidList() {
		return createFidList;
	}

	public void setCreateFidList(List<String> createFidList) {
		this.createFidList = createFidList;
	}

	public String getCreateDateStart() {
		return createDateStart;
	}

	public void setCreateDateStart(String createDateStart) {
		this.createDateStart = createDateStart;
	}

	public String getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(String createDateEnd) {
		this.createDateEnd = createDateEnd;
	}
}
