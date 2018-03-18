package com.ziroom.minsu.report.board.dto;

/**
 * <p>查询</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd on 2017/1/11.
 * @version 1.0
 * @since 1.0
 */
public class EmpStatsRequest {
    /**
     * 开始日期
     */
    private String startTime;
    /**
     * 结束日期
     */
    private String endTime;
    /**
     * 员工号
     */
    private String empCode;
    /**
     * 房源状态
     */
    private Integer houseStatus;


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public Integer getHouseStatus() {
        return houseStatus;
    }

    public void setHouseStatus(Integer houseStatus) {
        this.houseStatus = houseStatus;
    }
}
