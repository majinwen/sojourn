package com.ziroom.minsu.entity.search;

import com.asura.framework.base.entity.BaseEntity;


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
public  class LabelTipsEntity extends BaseEntity{ 

	/**
	 * 
	 */
	private static final long serialVersionUID = -4221719774516652349L;

	/**
	 * 标签类型
	 * @see com.ziroom.minsu.valenum.search.LabelTipsStyleEnum
	 */
	private String tipsType;
	
    /**
     *  标签名称 
     *  @see  com.ziroom.minsu.valenum.search.LabelTipsEnum
     */
    private String name; 
    
    /**  
     * 序号
     */
    private Integer index;

	public String getTipsType() {
		return tipsType;
	}

	public String getName() {
		return name;
	}

	public Integer getIndex() {
		return index;
	}

	public void setTipsType(String tipsType) {
		this.tipsType = tipsType;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIndex(Integer index) {
		this.index = index;
	} 
    
}
