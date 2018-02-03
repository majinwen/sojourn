package com.ziroom.minsu.services.basedata.dto;

import java.util.List;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * 
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public class ConfTagRequest  extends PageRequest {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 6838599882916280921L;

	/**
     * 标签名称
     */
    private String tagName;

    /**
     * 标签类型
     */
    private Integer tagType;

    /**
     * 是否有效 0：未生效 1：已生效
     */
    private Integer isValid;
    
    /**
    * fids 集合
    */
    private List<String> fids;

	/**
	 * @return the fids
	 */
	public List<String> getFids() {
		return fids;
	}

	/**
	 * @param fids the fids to set
	 */
	public void setFids(List<String> fids) {
		this.fids = fids;
	}

	public String getTagName() {
		return tagName;
	}

	public Integer getTagType() {
		return tagType;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public void setTagType(Integer tagType) {
		this.tagType = tagType;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
 
}
