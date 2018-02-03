/**
 * @FileName: QualityGradeEnum.java
 * @Package com.ziroom.minsu.valenum.house
 * 
 * @author zl
 * @created 2016年11月14日 上午9:41:12
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.valenum.house;

/**
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
public enum QualityGradeEnum {
	
	GRADE_A_PLUS("A+","A+", 5),
	GRADE_A("A","A等", 5),
	GRADE_B_PLUS("B+","B+", 4),
	GRADE_B("B","B等", 3),
	GRADE_B_SUBTRACT("B-","B-", 2),
	GRADE_C("C","C等", 1);
	
	
	/** code */
	private String code;
	
	/** 名称 */
	private String name;
	
	private Integer score;
	
	QualityGradeEnum(String code,String name, Integer score){
		this.code = code;
		this.name = name;
		this.score = score;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the score
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(Integer score) {
		this.score = score;
	}
	
}
