package com.ziroom.minsu.services.order.dto;

/**
 * 罚款单取消
 * @author jixd
 * @created 2017年05月15日 15:00:38
 * @param
 * @return
 */
public class PenaltyCancelRequest {
    /**
     * 罚款单编号
     */
    private String penaltySn;
    /**
     * 备注
     */
    private String remark;
    /**
     * 员工编号
     */
    private String empCode;
    /**
     * 员工姓名
     */
    private String empName;

    public String getPenaltySn() {
        return penaltySn;
    }

    public void setPenaltySn(String penaltySn) {
        this.penaltySn = penaltySn;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }
}
