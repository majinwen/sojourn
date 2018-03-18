package com.zra.common.dto.marketing;
import java.math.BigDecimal;
/**
 * 渠道统计数据Dto
 * @author tianxf9
 *
 */
public class MkReportExcelDto {
	
    //渠道名字
    private String channelName;
    //约看量
	private int yueKanCount;
	//客源量
	private int touristsCount; 
	//客源成交转换率
	private BigDecimal touristsToDealRate;
	//成交量
    private int dealCount;
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public int getYueKanCount() {
		return yueKanCount;
	}
	public void setYueKanCount(int yueKanCount) {
		this.yueKanCount = yueKanCount;
	}
	public int getTouristsCount() {
		return touristsCount;
	}
	public void setTouristsCount(int touristsCount) {
		this.touristsCount = touristsCount;
	}
	public BigDecimal getTouristsToDealRate() {
		return touristsToDealRate;
	}
	public void setTouristsToDealRate(BigDecimal touristsToDealRate) {
		this.touristsToDealRate = touristsToDealRate;
	}
	public int getDealCount() {
		return dealCount;
	}
	public void setDealCount(int dealCount) {
		this.dealCount = dealCount;
	}
    
}
