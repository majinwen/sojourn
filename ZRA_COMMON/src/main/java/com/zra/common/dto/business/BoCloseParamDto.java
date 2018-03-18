package com.zra.common.dto.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * <p>
 * 商机关闭参数
 * </p>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class BoCloseParamDto implements Serializable{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -4785419264084784593L;

	/**
	 * 商机业务id
	 */
	private String businessBid;

	/**
	 * 商机阶段  #BoBusinessStep
	 */
	private Byte step;
	
	/**
	 * 未成交原因
	 */
	private String handResultContent;

	/**
	 * 操作人 userAccount id
	 */
	private String updaterId;
	
	/**
	 * 商机业务id集合
	 */
	private List<String> listBusinessBid = new ArrayList<String>();
	
	/**
	 * 关闭商机发送短信模板
	 */
	private Integer closeMsgDescContent;

	public String getBusinessBid() {
		return businessBid;
	}

	public void setBusinessBid(String businessBid) {
		this.businessBid = businessBid;
	}

	public Byte getStep() {
		return step;
	}

	public void setStep(Byte step) {
		this.step = step;
	}

	public String getHandResultContent() {
		return handResultContent;
	}

	public void setHandResultContent(String handResultContent) {
		this.handResultContent = handResultContent;
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

	public Integer getCloseMsgDescContent() {
		return closeMsgDescContent;
	}

	public void setCloseMsgDescContent(Integer closeMsgDescContent) {
		this.closeMsgDescContent = closeMsgDescContent;
	}
	
	
}