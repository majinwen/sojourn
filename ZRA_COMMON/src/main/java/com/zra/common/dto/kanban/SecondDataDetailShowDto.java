package com.zra.common.dto.kanban;

import java.util.List;

/**
 * 二级详情数据返回给前台的数据
 * @author tianxf9
 *
 */
public class SecondDataDetailShowDto {
	
	//核心数据
	private SummaryShowDto summaryDto;
	
	//二级数据项目总体
	private SecondDataShowDto secondDto;
	
	private List<SecondDataDetailDto> secondDetailDtoList;

	public SummaryShowDto getSummaryDto() {
		return summaryDto;
	}

	public void setSummaryDto(SummaryShowDto summaryDto) {
		this.summaryDto = summaryDto;
	}

	public SecondDataShowDto getSecondDto() {
		return secondDto;
	}

	public void setSecondDto(SecondDataShowDto secondDto) {
		this.secondDto = secondDto;
	}

	public List<SecondDataDetailDto> getSecondDetailDtoList() {
		return secondDetailDtoList;
	}

	public void setSecondDetailDtoList(List<SecondDataDetailDto> secondDetailDtoList) {
		this.secondDetailDtoList = secondDetailDtoList;
	}
}
