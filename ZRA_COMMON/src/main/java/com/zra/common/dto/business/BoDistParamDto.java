package com.zra.common.dto.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * <p>
 * 商机分配参数
 * </p>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class BoDistParamDto implements Serializable{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -6508329223475322649L;
	
	/**
	 * 商机业务id
	 */
	private String businessBid;
	
	/**
	 * ZO逻辑id
	 */
	private String zoId;
	
	/**
	 * zo管家姓名
	 */
	private String zoName;
	
	/**
	 * 操作人
	 */
	private String updaterId;
	
	/**
	 * 商机业务id集合
	 */
	private List<String> listBusinessBid = new ArrayList<String>();
	
	public String getBusinessBid() {
		return businessBid;
	}
	
	public void setBusinessBid(String businessBid) {
		this.businessBid = businessBid;
	}

	public String getZoId() {
		return zoId;
	}

	public void setZoId(String zoId) {
		this.zoId = zoId;
	}

	public String getZoName() {
		return zoName;
	}

	public void setZoName(String zoName) {
		this.zoName = zoName;
	}
	
	public String getUpdaterId() {
		return updaterId;
	}
	
	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}
	
	public List<String> getListBusinessBid() {
		return listBusinessBid;
	}
	
	public void setListBusinessBid(List<String> listBusinessBid) {
		this.listBusinessBid = listBusinessBid;
	}

}