package com.ziroom.zrp.service.trading.dto;

import com.ziroom.zrp.service.trading.dto.finance.ZRAReceiptBillDto;

import java.util.Date;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年11月17日 17:04
 * @since 1.0
 */

public class PaymentBillsDto extends ZRAReceiptBillDto {

    private static final long serialVersionUID = -3365943472000293943L;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 房间code
     */
    private String roomCode;

    /**
     * 出房合同号
     */
    private String conRentCode;

    /**
     * 父合同号
     */
    private String parentConRentCode;

    /**
     * 查询合同号的起始日
     */
    private Date startDate;

    /**
     * 查询合同号的终止日
     */
    private Date endDate;
    
    //add by xiangb 2018年1月1日
    private String state;//收款单状态

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getConRentCode() {
        return conRentCode;
    }

    public void setConRentCode(String conRentCode) {
        this.conRentCode = conRentCode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getParentConRentCode() {
        return parentConRentCode;
    }

    public void setParentConRentCode(String parentConRentCode) {
        this.parentConRentCode = parentConRentCode;
    }

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
