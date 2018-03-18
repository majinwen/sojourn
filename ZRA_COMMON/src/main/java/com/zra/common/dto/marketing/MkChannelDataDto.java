package com.zra.common.dto.marketing;
import java.math.BigDecimal;
/**
 * 渠道统计数据Dto
 * @author tianxf9
 *
 */
public class MkChannelDataDto {
	
	//渠道bid
    private String channelBid;
    //渠道名字
    private String channelName;
    //约看量
	private int yueKanCount;
	//客源量
	private int touristsCount; 
	//成交量
	private int dealCount;
	//客源成交hz
	private BigDecimal touristsToDealRate;
	

	public String getChannelBid() {
		return channelBid;
	}
	public void setChannelBid(String channelBid) {
		this.channelBid = channelBid;
	}
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
	public int getDealCount() {
		return dealCount;
	}
	public void setDealCount(int dealCount) {
		this.dealCount = dealCount;
	}
	public BigDecimal getTouristsToDealRate() {
		return touristsToDealRate;
	}
	public void setTouristsToDealRate(BigDecimal touristsToDealRate) {
		this.touristsToDealRate = touristsToDealRate;
	}
	
}
