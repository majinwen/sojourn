/**
 * @FileName: LandlordEvaluateVo.java
 * @Package com.ziroom.minsu.api.evaluate.entity
 * 
 * @author yd
 * @created 2017年1月20日 下午3:24:22
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.evaluate.entity;

import java.io.Serializable;

/**
 * <p>房东评价</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class LandlordEvaluateVo implements Serializable{

    /**
	 * 序列id
	 */
	private static final long serialVersionUID = 953325493065661412L;

	/**
     * 房东对房客的满意星级(1颗星 2颗星 3颗星 4颗星 5颗星 5是最满意 1是最不满意)
     */
    private Integer landlordSatisfied;

    /**
     * 评价内容
     */
    private String content;

	/**
	 * @return the landlordSatisfied
	 */
	public Integer getLandlordSatisfied() {
		return landlordSatisfied;
	}

	/**
	 * @param landlordSatisfied the landlordSatisfied to set
	 */
	public void setLandlordSatisfied(Integer landlordSatisfied) {
		this.landlordSatisfied = landlordSatisfied;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
    
    

   
}
