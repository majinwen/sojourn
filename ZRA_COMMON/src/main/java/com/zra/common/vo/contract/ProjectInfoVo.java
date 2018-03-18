package com.zra.common.vo.contract;
/**
 * <p>APP端合同基础信息</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年9月14日
 * @since 1.0
 */
public class ProjectInfoVo {
	
    /**
 	 * 项目名称
 	 */
    private String proName;
    /**
 	 * 项目地址
 	 */
    private String proAddress;
    /**
     * 项目头图地址
     */
    private String proHeadFigureUrl;
    /**
     * 房间价格
     */
    private String roomSalesPrice;
    
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getProAddress() {
		return proAddress;
	}
	public void setProAddress(String proAddress) {
		this.proAddress = proAddress;
	}
	public String getProHeadFigureUrl() {
		return proHeadFigureUrl;
	}
	public void setProHeadFigureUrl(String proHeadFigureUrl) {
		this.proHeadFigureUrl = proHeadFigureUrl;
	}
	public String getRoomSalesPrice() {
		return roomSalesPrice;
	}
	public void setRoomSalesPrice(String roomSalesPrice) {
		this.roomSalesPrice = roomSalesPrice;
	}
    
}
