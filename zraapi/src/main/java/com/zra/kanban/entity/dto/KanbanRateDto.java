package com.zra.kanban.entity.dto;

import java.math.BigDecimal;

/**
 * 商机及时根据率dto
 * @author tianxf9
 *
 */
public class KanbanRateDto {
	
	//项目id
	private String projectId;
	
	//率
    private BigDecimal rate;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	
	

}
